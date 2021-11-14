import './problem-list.scss';
import { DataGrid } from '@mui/x-data-grid';

const ProblemList = (props) => {
    console.log("render porblem list")
    const {problemRows, problemColumns, pageSize, ...otherProps} = props; 

    return (
        <div className="problem-list">
            <DataGrid
                rows={problemRows}
                columns={problemColumns}
                pageSize={pageSize}
                rowsPerPageOptions={[pageSize]}
                {...otherProps}
            />
        </div>
    )
}

export default ProblemList;