import React from "react";
import { Outlet } from "react-router-dom";

<<<<<<< HEAD
import { LoginModal } from "./common/modal/LoginModal/LoginModal"
=======
import { Container, } from "@mui/material";

import { HeaderInformation } from "./NavigationComponents/HeaderInformation";
import { UsersSearch } from "./NavigationComponents/UsersSearch";
import { SideBar } from "./NavigationComponents/SideBar";
import { ContainerStyled, ContentContainer, OutletContainer, OutletWrapper } from "./LayoutStyles";
import { EnterUserNameModal } from "./LoginModal/EnterUserNameModal";
import { LoginModal } from "./LoginModal/LoginModal";
>>>>>>> main

export function Layout() {

    return (
<<<<<<< HEAD
        <>
            <Outlet/>
            {/* <LoginModal/> */}
            {/* <CreateAccountModal/> */}
            {/* <Link to="*" variant="contained">To not found page 404</Link> */}
        </>
=======
        <Container maxWidth="false" sx={ContainerStyled}>
            <div style={ContentContainer}>
                <SideBar/>
                <div style={{ display: "flex", flexDirection: "column", width: "600px" }}>
                    <HeaderInformation/>
                    <div style={OutletContainer}>
                        <div style={OutletWrapper}>
                            <Outlet/>
                        </div>
                    </div>
                </div>
                <UsersSearch/>
            </div>
        </Container>
>>>>>>> main
    );
}

