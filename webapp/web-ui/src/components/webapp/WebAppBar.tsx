import {AppBar, Button, Toolbar} from "@material-ui/core";
import AppBarIconButton from "../ui/AppBarIconButton";
import MenuIcon from "@material-ui/icons/Menu";
import AppBarTypography from "../ui/AppBarTypography";
import React from "react";

const WebAppBar = ({
                       userName,
                       loginButtonText,
                       loginButtonAction
                   }: {
                       userName: String,
                       loginButtonText: String,
                       loginButtonAction: () => void
                   }
) => (
    <AppBar position="static">
        <Toolbar>
            <AppBarIconButton edge="start" color="inherit" aria-label="menu">
                <MenuIcon/>
            </AppBarIconButton>
            <AppBarTypography variant="h6">
                Hello {userName}
            </AppBarTypography>
            <Button color="inherit" onClick={loginButtonAction}>
                {loginButtonText}
            </Button>
        </Toolbar>
    </AppBar>
)

export default WebAppBar