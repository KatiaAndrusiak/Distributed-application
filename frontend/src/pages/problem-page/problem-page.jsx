import './problem-page.scss';

import { RenderCellExpand } from '../../services/renderCellExpand';
import { useState, useEffect } from 'react';
import {closeModal, getResource, createModalContent} from '../../services/services';

import { v4 as uuidv4 } from 'uuid';

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck } from "@fortawesome/free-solid-svg-icons";

import CustomButton from '../../components/button/custom-button';
import ProblemList from '../../components/problem-list/problem-list';
import AcceptedProblemList from '../../components/accepted-problem-list/accepted-problem-list';
import Modal from '../../components/modal/modal';

const ProblemPage = () => {
    const [isModal, setIsModal] = useState(false);
    const [modalContent, setModalContent] = useState({});
    const [selectedRows, setSelectedRows] = useState([]);
    const [problemRows, setProblemRows] = useState([]);
    const [acceptedRows, setAcceptedRows] = useState([]);


    const handleSelectionModel = (ids) => {
        console.log("selekcja");
        const selectedIDs = new Set(ids);
        const selected = problemRows.filter((row) =>
          selectedIDs.has(row.id),
        );

        if (selected.length !== 0) {
            setSelectedRows(selected);
        }
        
        console.log(selectedRows);
    }

    useEffect(() => {
        console.log("Fetching problems");
        getResource("http://localhost:8080/problems")
            .then(problems => {
                const filteredProblems = problems.map(({date, address, category, problem, description}) => {
                    return {id: uuidv4(), date, address, category, problem, description}
                })
                setProblemRows(filteredProblems)
            });
    }, [])

    useEffect(() => {
        console.log("akceptacja");
        const messages = [];
        console.log(selectedRows);

        for (const key in selectedRows[0]) {
            if (key !== 'id') {
                messages.push(selectedRows[0][key]);
            }
        }

        if (selectedRows.length !== 0 && isModal) {
            setProblemRows(problemRows.filter(problem => problem.id !== selectedRows[0]['id']));
            setAcceptedRows(prevRows => [...prevRows, selectedRows[0]])
        }

        setModalContent(createModalContent("Problem został zaakceptowany", messages));
    }, [selectedRows]);

    const acceptProblem = () => {
        setIsModal(true);  
    }

    const modal = isModal ? <Modal 
    modalContent = {modalContent}
    modalError={false}
    close={() => closeModal(setIsModal)}/> : null

    const check = <FontAwesomeIcon icon={faCheck} />;

    const problemColumns = [
        { 
            field: 'date',
            headerName: 'Data', 
            width: 175, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell',
            headerAlign: 'center', 
            align: 'center'
        },
        { 
            field: 'address', 
            headerName: 'Lokacja', 
            sortable: false, 
            width: 175, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell', 
            headerAlign: 'center', 
            align: 'center'},
        { 
            field: 'category', 
            headerName: 'Kategoria', 
            width: 155, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell', 
            headerAlign: 'center'},
        { 
            field: 'problem', 
            headerName: 'Problem',
             width: 175, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell', 
            renderCell: RenderCellExpand, 
            headerAlign: 'center'},
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
        { 
            field: 'done', 
            headerName: 'Gotowe', 
            width: 110, 
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
            width: 197, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell',
            headerAlign: 'center', 
            align: 'center'
        },
        { 
            field: 'address', 
            headerName: 'Lokacja', 
            sortable: false, 
            width: 197, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell', 
            headerAlign: 'center', 
            align: 'center'},
        { 
            field: 'category', 
            headerName: 'Kategoria', 
            width: 177, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell', 
            headerAlign: 'center'},
        { 
            field: 'problem', 
            headerName: 'Problem',
             width: 197, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell', 
            headerAlign: 'center'},
        { 
            field: 'description', 
            headerName: 'Opis', 
            sortable: false, 
            width: 222, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell', 
            renderCell: RenderCellExpand, 
            headerAlign: 'center'
        },
      ];
      
    // const Rows = [
    //     {id: 1, date: "13.10.2021", address: "Reymonta, 17", category: "Woda", problem: "Brak ciepłej wody", description: "help me please!!!!!help me please!!!!!help me please!!!!!help me please!!!!!help me please!!!!!help me please!!!!!help me please!!!!!help me please!!!!!help me please!!!!!help me please!!!!!"},
    //     {id: 2, date: "15.10.2021", address: "Reymonta, 17", category: "Woda", problem: "Brak ciepłej wody", description: "help me please!!!!!"},
    //     {id: 3, date: "15.10.2021", address: "Reymonta, 17", category: "Woda", problem: "Brak ciepłej wody", description: "help me please!!!!!"},
    //     {id: 4, date: "15.10.2021", address: "Reymonta, 17", category: "Woda", problem: "Brak ciepłej wody", description: "help me please!!!!!"},
    //     {id: 5, date: "15.10.2021", address: "Reymonta, 17", category: "Woda", problem: "Brak ciepłej wody", description: "help me please!!!!!"},
    //     {id: 6, date: "15.10.2021", address: "Reymonta, 17", category: "Woda", problem: "Brak ciepłej wody", description: "help me please!!!!!"},
    //     {id: 7, date: "15.10.2021", address: "Reymonta, 17", category: "Woda", problem: "Brak ciepłej wody", description: "help me please!!!!!"},
    //     {id: 8, date: "15.10.2021", address: "Reymonta, 17", category: "Woda", problem: "Brak ciepłej wody", description: "help me please!!!!!"}
    // ];

    return (
        <div className="problem-page">
            <h2 className="problem-title">Lista problemów</h2>
            <ProblemList
                problemRows={problemRows}
                problemColumns={problemColumns}  
                pageSize={7}
                onSelectionModelChange={handleSelectionModel}
                disableColumnSelector
                hideFooterSelectedRowCount
                disableColumnMenu
            />
                {/* <DataGrid
                    rows={problemRows}
                    columns={problemColumns}
                    pageSize={7}
                    rowsPerPageOptions={[7]}
                    onSelectionModelChange={handleSelectionModel}
                    disableColumnSelector
                    hideFooterSelectedRowCount
                    disableColumnMenu
                /> */}
            <h2 className="problem-title">Lista zaakceptowanych problemów</h2>
            <AcceptedProblemList
                acceptedRows={acceptedRows}
                acceptedProblemColumns={acceptedProblemColumns}
                pageSize={7}
                disableColumnSelector
                hideFooterSelectedRowCount
                disableColumnMenu
            />
            {/* <DataGrid
                    rows={acceptedRows}
                    columns={acceptedProblemColumns}
                    pageSize={7}
                    rowsPerPageOptions={[7]}
                    onSelectionModelChange={handleSelectionModel}
                    disableColumnSelector
                    hideFooterSelectedRowCount
                    disableColumnMenu
                />*/}

            {modal}
        </div>

    )
}

export default ProblemPage;