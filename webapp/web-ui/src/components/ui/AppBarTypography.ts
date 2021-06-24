import {Theme, Typography, withStyles} from "@material-ui/core";

const AppBarTypography = withStyles((theme: Theme) => ({
    root: {
        flexGrow: 1,
    },
}))(Typography)

export default AppBarTypography