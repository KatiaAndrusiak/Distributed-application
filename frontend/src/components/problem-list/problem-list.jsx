import './problem-list.scss';
import Stack from '@mui/material/Stack';
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
                components={{
                    NoRowsOverlay: () => (
                      <Stack height="100%" alignItems="center" justifyContent="center">
                        Brak problemów
                      </Stack>
                    )
                  }}
            />
        </div>
    )
}

export default ProblemList;