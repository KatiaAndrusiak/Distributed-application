import './accepted-problem-list.scss';
import Stack from '@mui/material/Stack';
import { DataGrid } from '@mui/x-data-grid';

const AcceptedProblemList = (props) => {
    const {acceptedRows, acceptedProblemColumns, pageSize, ...otherProps} = props;

    return (
        <div className="accepted-problem-list">
            <DataGrid
                rows={acceptedRows}
                columns={acceptedProblemColumns}
                pageSize={pageSize}
                rowsPerPageOptions={[pageSize]}
                {...otherProps}
                components={{
                    NoRowsOverlay: () => (
                      <Stack height="100%" alignItems="center" justifyContent="center">
                        Brak zaakceptowanych problemów
                      </Stack>
                    )
                  }}
            />
        </div>
    )
}

export default AcceptedProblemList;