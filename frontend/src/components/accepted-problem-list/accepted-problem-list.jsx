import './accepted-problem-list.scss';
import { DataGrid } from '@mui/x-data-grid';

const AcceptedProblemList = (props) => {
    console.log("render accepted porblem list")
    const {acceptedRows, acceptedProblemColumns, pageSize, ...otherProps} = props; 

    return (
        <div className="accepted-problem-list">
            <DataGrid
                rows={acceptedRows}
                columns={acceptedProblemColumns}
                pageSize={pageSize}
                rowsPerPageOptions={[pageSize]}
                {...otherProps}
            />
        </div>
    )
}

export default AcceptedProblemList;