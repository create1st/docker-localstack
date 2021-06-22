import {useAuth0} from '@auth0/auth0-react';
import {User} from '@auth0/auth0-spa-js';
import React from 'react';
import {LoginWithRedirect, Logout} from '../../common/auth/auth0';
import OrdersView from '../orders/OrdersView';
import {
    AppBar,
    Button,
    CircularProgress,
    IconButton,
    makeStyles,
    Paper,
    Theme,
    Toolbar,
    Typography
} from "@material-ui/core";
import MenuIcon from '@material-ui/icons/Menu';
import {ClassNameMap} from "@material-ui/core/styles/withStyles";

const Webapp = () => {
    const {
        isAuthenticated,
        isLoading,
        loginWithRedirect,
        user,
        logout,
    } = useAuth0();
    const classes = useStyles();

    if (isLoading) return renderLoading(classes)
    if (!isAuthenticated) return renderLogin(loginWithRedirect, classes)
    return renderComponent(user!, logout, classes)
}

const useStyles = makeStyles((theme: Theme) => ({
    root: {
        flexGrow: 1,
    },
    menuButton: {
        marginRight: theme.spacing(2),
    },
    title: {
        flexGrow: 1,
    },
}));

type Styling = ClassNameMap<"root" | "menuButton" | "title">;

const renderLoading = (classes: Styling) => (
    <div className={classes.root}>
        <CircularProgress />
    </div>
)

const renderLogin = (loginWithRedirect: LoginWithRedirect, classes: Styling) => (
    <div className={classes.root}>
        <AppBar position="static">
            <Toolbar>
                <IconButton edge="start" className={classes.menuButton} color="inherit" aria-label="menu">
                    <MenuIcon/>
                </IconButton>
                <Typography variant="h6" className={classes.title}>Please login</Typography>
                <Button color="inherit"  onClick={loginWithRedirect}>Log in</Button>
            </Toolbar>
        </AppBar>
    </div>
)

const renderComponent = (user: User, logout: Logout, classes: Styling) => (
    <div className={classes.root}>
        <AppBar position="static">
            <Toolbar>
                <IconButton edge="start" className={classes.menuButton} color="inherit" aria-label="menu">
                    <MenuIcon/>
                </IconButton>
                <Typography variant="h6" className={classes.title}>
                    Hello {user ? user.name : "guest"}
                </Typography>
                <Button color="inherit" onClick={() => logout({returnTo: window.location.origin})}>Log out</Button>
            </Toolbar>
        </AppBar>
        <Paper>
            <OrdersView/>
        </Paper>
    </div>
)

export default Webapp;