import './problem-page.scss';

import { RenderCellExpand } from '../../services/renderCellExpand';
import { useState, useEffect } from 'react';
import {closeModal, createModalContent, getWithAuthorization, fetchWithAuthorization, calcDistance} from '../../services/services';
import { updateProblems } from '../../redux/user/user-action';
import {useSelector, useDispatch} from 'react-redux';

import { v4 as uuidv4 } from 'uuid';

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck } from "@fortawesome/free-solid-svg-icons";

import CustomButton from '../../components/button/custom-button';
import ProblemList from '../../components/problem-list/problem-list';
import AcceptedProblemList from '../../components/accepted-problem-list/accepted-problem-list';
import Spinner from '../../components/spinner/Spinner';
import Modal from '../../components/modal/modal';
import RadioButtons from '../../components/radio-group/radio-group';

const ProblemPage = () => {

    const radioButtons = {
        buttons: [
            {
                value: 60000,
                label: "Co minutę"
            },
            {
                value: 9000000,
                label: "Co 15 minut"
            },
            {
                value: 18000000,
                label: "Co 30 minut"
            }
        ]
    }
     
    const {user, acceptedProblems} = useSelector(state => state);
    const dispatch = useDispatch();

    const [loading, setLoading] = useState(false);
    const [isModal, setIsModal] = useState(false);
    const [isModalTemp, setIsModalTemp] = useState(false);
    const [modalError, setModalError] = useState(false);
    const [modalContent, setModalContent] = useState({});


    const [selectedRows, setSelectedRows] = useState([]);
    const [problemRows, setProblemRows] = useState([]);
    const [requestTime, setRequestTime] = useState(60000);


    const handleSelectionModel = (ids) => {
        const selectedIDs = new Set(ids);
        const selected = problemRows.filter((row) =>
          selectedIDs.has(row.id),
        );

        if (selected.length !== 0) {
            setSelectedRows(selected);
            setModalError(false);
        }
    }

    const fetchProblems = () => {
        setLoading(true);
        const query = user.categories.map(category => `category=${category}`).join('&')
        getWithAuthorization(`${process.env.REACT_APP_API_ROOT_URL}/request/problems?${query}`, user.accessToken)
            .then(problems => {
                if (problems.length > 0) {
                    const filteredProblems = problems.map(({date, address, category, problem, description, latitude, longitude}) => {
                        const distance = calcDistance(user.latitude, user.longitude, latitude, longitude).toFixed(2) + ' km'
                        return {id: uuidv4(), date, address, distance, category, problem, description}
                    })
                    setLoading(false);
                    setProblemRows(filteredProblems)
                } else {
                    setLoading(false);
                    setProblemRows([])
                }

            });
    }

    useEffect(() => {
        fetchProblems();
    }, [])

    useEffect(() => {
        const intervalId = setInterval(() => {
            fetchProblems();
        }, requestTime);
        return () => clearInterval(intervalId); //This is important
    }, [problemRows])


    useEffect(() => {
        const messages = [];

        if (selectedRows.length !== 0 && isModalTemp) {
            setTimeout(() => {
                setProblemRows((prevRows) => prevRows.filter(problem => problem.id !== selectedRows[0]['id']));
            })
            
            // setProblemRows(problemRows.filter(problem => problem.id !== selectedRows[0]['id']));
            const {address, category, date, description, problem} = selectedRows[0];
            const data = {
                category,
                problem,
                description,
                date,
                address,
                latitude: null,
                longitude: null
            }
            fetchWithAuthorization(`${process.env.REACT_APP_API_ROOT_URL}/request/problems`, JSON.stringify(data), user.accessToken, "DELETE")
                .then(res => {
                    const {status} = res;
                    if (status === 200) {
                        dispatch(updateProblems(selectedRows[0]))
                        const newArr = acceptedProblems.concat(selectedRows[0])
                        localStorage.setItem('acceptedRows', JSON.stringify(newArr))
                        setModalError(false);
                        for (const key in selectedRows[0]) {
                            if (key !== 'id') {
                                 messages.push(selectedRows[0][key]);
                            }
                        }
                        setModalContent(createModalContent("Problem został zaakceptowany", messages));
                        setIsModal(true); 
                        setIsModalTemp(false);
                    } else if (status === 400) {
                        setModalError(true);
                        messages.push("Problem został zaakceptowany przez inego użytkownika, wybierz inny problem!");
                        setModalContent(createModalContent("Info", messages));
                        setIsModal(true);
                        setIsModalTemp(false); 
                    }
                })
        }
    }, [selectedRows]);

    const acceptProblem = () => {
        setIsModalTemp(true);
    }

    const handleRequestTime = (event) => {
        setRequestTime(event.target.value);
    };

    const modal = isModal ? <Modal 
                                modalContent = {modalContent}
                                modalError={modalError}
                                close={() => closeModal(setIsModal)}/> : 
                            null

    const check = <FontAwesomeIcon icon={faCheck} />;

    const problemColumns = [
        { 
            field: 'date',
            headerName: 'Data', 
            width: 163, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell',
            headerAlign: 'center',
            align: 'center'
        },
        { 
            field: 'address', 
            headerName: 'Lokacja', 
            sortable: false, 
            width: 185, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell',
            renderCell: RenderCellExpand,
            headerAlign: 'center',
            align: 'center'
        },
        { 
            field: 'distance', 
            headerName: 'Odległość', 
            width: 140, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell',
            headerAlign: 'center'
        },
        { 
            field: 'category', 
            headerName: 'Kategoria', 
            width: 185, 
            headerClassName: 'column-header',
            renderCell: RenderCellExpand, 
            cellClassName: 'row-cell',
            headerAlign: 'center'
        },
        { 
            field: 'problem', 
            headerName: 'Problem',
            width: 243, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell', 
            renderCell: RenderCellExpand,
            headerAlign: 'center'
        },
        { 
            field: 'description', 
            headerName: 'Opis', 
            sortable: false, 
            width: 180, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell', 
            renderCell: RenderCellExpand, 
            headerAlign: 'center'
        },
        { 
            field: 'done', 
            headerName: 'Gotowe', 
            width: 95, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell', 
            sortable: false, 
            renderCell: () => {
                return (
                <CustomButton
                    type="button"
                    additionalClass="accept"
                >
                    <i onClick={acceptProblem}>{check}</i>
                </CustomButton>
                );
            }, 
            align: 'center'
        }
      ];

      const acceptedProblemColumns = [
        { 
            field: 'date',
            headerName: 'Data', 
            width: 172, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell',
            headerAlign: 'center', 
            align: 'center'
        },
        { 
            field: 'address', 
            headerName: 'Lokacja', 
            sortable: false, 
            width: 250, 
            headerClassName: 'column-header',
            renderCell: RenderCellExpand, 
            cellClassName: 'row-cell', 
            headerAlign: 'center', 
            align: 'center'
        },
        { 
            field: 'distance', 
            headerName: 'Odległość', 
            width: 138, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell',
            headerAlign: 'center'
        },
        { 
            field: 'category', 
            headerName: 'Kategoria', 
            width: 187, 
            headerClassName: 'column-header',
            renderCell: RenderCellExpand, 
            cellClassName: 'row-cell',
            headerAlign: 'center'
        },
        { 
            field: 'problem', 
            headerName: 'Problem',
            width: 251, 
            headerClassName: 'column-header', 
            renderCell: RenderCellExpand,
            cellClassName: 'row-cell', 
            headerAlign: 'center'
        },
        { 
            field: 'description', 
            headerName: 'Opis', 
            sortable: false, 
            width: 200, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell', 
            renderCell: RenderCellExpand, 
            headerAlign: 'center'
        },
      ];

    return (
        <div className="problem-page">
            <h2 className="problem-title">Lista problemów</h2>
            {loading ? <Spinner/> :
            <ProblemList
                problemRows={problemRows}
                problemColumns={problemColumns}  
                pageSize={7}
                onSelectionModelChange={handleSelectionModel}
                disableColumnSelector
                hideFooterSelectedRowCount
                disableColumnMenu
            />}
            {loading ? null :
            <RadioButtons 
                legend="Wyświetlenie nowych zgłoszeń:"
                ariaLabel="request-time"
                value={requestTime}
                handle={handleRequestTime}
                radioButtons={radioButtons}/> }
            <h2 className="problem-title">Lista zaakceptowanych problemów</h2>
            <AcceptedProblemList
                acceptedRows={acceptedProblems}
                acceptedProblemColumns={acceptedProblemColumns}
                pageSize={7}
                disableColumnSelector
                hideFooterSelectedRowCount
                disableColumnMenu
            />
            {modal}
        </div>

    )
}

export default ProblemPage;