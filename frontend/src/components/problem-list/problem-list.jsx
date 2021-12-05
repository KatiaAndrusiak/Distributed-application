import './problem-list.scss';
import { DataGrid } from '@mui/x-data-grid';

const ProblemList = (props) => {
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