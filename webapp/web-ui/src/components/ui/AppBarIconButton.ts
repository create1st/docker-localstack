import {IconButton, Theme, withStyles} from "@material-ui/core";

const AppBarIconButton = withStyles((theme: Theme) => ({
    root: {
        marginRight: theme.spacing(2),
    },
}))(IconButton)

export default AppBarIconButton