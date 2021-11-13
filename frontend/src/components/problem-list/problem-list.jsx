import './problem-list.scss';

import { RenderCellExpand } from '../../services/renderCellExpand';
import { useState, useEffect } from 'react';
import {closeModal} from '../../services/services';

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faCheck } from "@fortawesome/free-solid-svg-icons";

import CustomButton from './../button/custom-button';
import Modal from '../modal/modal';

import { DataGrid } from '@mui/x-data-grid';

const ProblemList = () => {


    const [isModal, setIsModal] = useState(false);
    const [modalContent, setModalContent] = useState({});
    const [selectedRows, setSelectedRows] = useState([]);


    const handleSelectionModel = (ids) => {
        console.log("selekcja");
        const selectedIDs = new Set(ids);
        const selectedRows = rows.filter((row) =>
          selectedIDs.has(row.id),
        );

        setSelectedRows(selectedRows);
        console.log(selectedRows);
    }

    useEffect(() => {
        console.log("akceptacja");
        const tempModalContent = {};
        const messages = [];
        tempModalContent.header = "Problem został zaakceptowany";
        console.log(selectedRows);
        for (const key in selectedRows[0]) {
            if (key !== 'id') {
                messages.push(selectedRows[0][key]);
            }
        }
        tempModalContent.messages = messages;
        setModalContent(tempModalContent);
    }, [selectedRows]);

    const acceptProblem = () => {
        setIsModal(true);  
    }

    const modal = isModal ? <Modal 
    modalContent = {modalContent}
    modalError={false}
    close={() => closeModal(setIsModal)}/> : null

    const check = <FontAwesomeIcon icon={faCheck} />;


    const columns = [
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
            headerName: 'Problema',
             width: 175, 
            headerClassName: 'column-header', 
            cellClassName: 'row-cell', 
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
                    onClick={acceptProblem}>
                    <i>{check}</i>
                </CustomButton>
                );
            }, 
            align: 'center'
        }
      ];
      
      const rows = [
        {id: 1, date: "13.10.2021", address: "Reymonta, 17", category: "Woda", problem: "Brak ciepłej wody", description: "help me please!!!!!help me please!!!!!help me please!!!!!help me please!!!!!help me please!!!!!help me please!!!!!help me please!!!!!help me please!!!!!help me please!!!!!help me please!!!!!"},
        {id: 2, date: "15.10.2021", address: "Reymonta, 17", category: "Woda", problem: "Brak ciepłej wody", description: "help me please!!!!!"},
        {id: 3, date: "15.10.2021", address: "Reymonta, 17", category: "Woda", problem: "Brak ciepłej wody", description: "help me please!!!!!"},
        {id: 4, date: "15.10.2021", address: "Reymonta, 17", category: "Woda", problem: "Brak ciepłej wody", description: "help me please!!!!!"},
        {id: 5, date: "15.10.2021", address: "Reymonta, 17", category: "Woda", problem: "Brak ciepłej wody", description: "help me please!!!!!"},
        {id: 6, date: "15.10.2021", address: "Reymonta, 17", category: "Woda", problem: "Brak ciepłej wody", description: "help me please!!!!!"},
        {id: 7, date: "15.10.2021", address: "Reymonta, 17", category: "Woda", problem: "Brak ciepłej wody", description: "help me please!!!!!"},
        {id: 8, date: "15.10.2021", address: "Reymonta, 17", category: "Woda", problem: "Brak ciepłej wody", description: "help me please!!!!!"}
      ];

    return (
        <div className="problem-page">
            <h2 className="problem-title">Lista problemów</h2>
            <div className="problem-list">
                <DataGrid
                    rows={rows}
                    columns={columns}
                    pageSize={7}
                    // rowsPerPageOptions={[5]}
                    onSelectionModelChange={handleSelectionModel}
                    disableColumnSelector
                    hideFooterSelectedRowCount
                    disableColumnMenu
                />
            </div>
            <h2 className="problem-title">Lista zaakceptowanych problemów</h2>

            {modal}
        </div>

    )
}

export default ProblemList;