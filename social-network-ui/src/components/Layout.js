import React from "react";
import { Outlet} from "react-router-dom";
import { useSelector } from "react-redux";

import { Container, } from "@mui/material";

import { HeaderInformation } from "./NavigationComponents/HeaderInformation";
import { UsersSearch } from "./NavigationComponents/UsersSearch/UsersSearch";
import { SideBar } from "./NavigationComponents/SideBar";
import { ContainerStyled, ContentContainer, OutletContainer, OutletWrapper } from "./LayoutStyles";
import { RegistrationPage } from "../pages/RegistrationPage";

export function Layout() {
    const userToken = useSelector(state => state.saveUserToken.userToken);

    return (
        userToken ? (<Container maxWidth="false" sx={ContainerStyled}>
            <div style={ContentContainer}>
                <SideBar/>
                <div
                    style={{
                        display: "flex",
                        flexDirection: "column",
                        maxWidth: "700px",
                        position: "relative",
                        alignItems: "center",
                    }}
                >
                    <div
                        style={{
                            display: "flex",
                            flexDirection: "column",
                            width: "600px",
                        }}
                    >
                        <HeaderInformation/>
                        <div style={OutletContainer}>
                            <div style={OutletWrapper}>
                                <Outlet/>
                            </div>
                        </div>
                    </div>
                </div>
                <UsersSearch/>
            </div>
        </Container>) : (<RegistrationPage/>)
    );
}

