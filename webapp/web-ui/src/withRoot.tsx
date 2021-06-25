import React from "react";
import {createMuiTheme, MuiThemeProvider} from "@material-ui/core/styles";
import purple from "@material-ui/core/colors/purple";
import green from "@material-ui/core/colors/green";
import CssBaseline from "@material-ui/core/CssBaseline";

const webAppTheme = createMuiTheme({
    palette: {
        primary: {
            light: purple[300],
            main: purple[500],
            dark: purple[700]
        },
        secondary: {
            light: green[300],
            main: green[500],
            dark: green[700]
        }
    }
});

function withRoot(WrappedComponent: () => JSX.Element) {
    const WithRoot = (props: Readonly<any>) => (
        <MuiThemeProvider theme={webAppTheme}>
            <CssBaseline/>
            <WrappedComponent {...props} />
        </MuiThemeProvider>
    )
    return WithRoot;
}

export default withRoot;
