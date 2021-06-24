import {Table, TableCell, TableRow, Theme, withStyles} from "@material-ui/core";

const AlternateColorTableRow = withStyles((theme: Theme) => ({
    root: {
        '&:nth-of-type(odd)': {
            backgroundColor: theme.palette.action.hover,
        },
    },
}))(TableRow);

const AlternateColorCell = withStyles((theme: Theme) => ({
    head: {
        backgroundColor: theme.palette.common.black,
        color: theme.palette.common.white,
    },
    body: {
        fontSize: 14,
    },
}))(TableCell);

const AlternateColorTable = withStyles((theme: Theme) => ({
    root: {
        minWidth: 700,
    },
}))(Table);

export {AlternateColorTable, AlternateColorTableRow, AlternateColorCell}