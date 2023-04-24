import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import { Modal, Typography, Box, Link, SvgIcon } from "@mui/material";

import { EnterPasswordModal } from "./EnterPasswordModal";
import { EnterUserNameModal } from "./EnterUserNameModal";
import { StyledModal, StyledBox, StyledTwitSvgIcon, StyledCloseSvgIcon } from "./loginModalStyles";

export function LoginModal() {
    const userDataState = useSelector(state => state.loginUserData.userData);

    useEffect(() => {
        console.log(userDataState);
    }, [userDataState]);

    return (
        <Modal
            open={true}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
            sx={StyledModal}>
            <Box sx={StyledBox}>
                <SvgIcon sx={StyledCloseSvgIcon} width="30px" height="30px" viewBox="0 0 24 24" fill="none"
                         xmlns="http://www.w3.org/2000/svg">
                    <path fillRule="evenodd" clipRule="evenodd"
                          d="M19.207 6.207a1 1 0 0 0-1.414-1.414L12 10.586 6.207 4.793a1 1 0 0 0-1.414 1.414L10.586 12l-5.793 5.793a1 1 0 1 0 1.414 1.414L12 13.414l5.793 5.793a1 1 0 0 0 1.414-1.414L13.414 12l5.793-5.793z"
                          fill="#000000"/>
                </SvgIcon>
                <SvgIcon viewBox="0 0 24 24" sx={StyledTwitSvgIcon}>
                    <path
                        d="M23.643 4.937c-.835.37-1.732.62-2.675.733.962-.576 1.7-1.49 2.048-2.578-.9.534-1.897.922-2.958 1.13-.85-.904-2.06-1.47-3.4-1.47-2.572 0-4.658 2.086-4.658 4.66 0 .364.042.718.12 1.06-3.873-.195-7.304-2.05-9.602-4.868-.4.69-.63 1.49-.63 2.342 0 1.616.823 3.043 2.072 3.878-.764-.025-1.482-.234-2.11-.583v.06c0 2.257 1.605 4.14 3.737 4.568-.392.106-.803.162-1.227.162-.3 0-.593-.028-.877-.082.593 1.85 2.313 3.198 4.352 3.234-1.595 1.25-3.604 1.995-5.786 1.995-.376 0-.747-.022-1.112-.065 2.062 1.323 4.51 2.093 7.14 2.093 8.57 0 13.255-7.098 13.255-13.254 0-.2-.005-.402-.014-.602.91-.658 1.7-1.477 2.323-2.41z"/>
                </SvgIcon>
                {userDataState.userName ? (<EnterPasswordModal/>) : (<EnterUserNameModal/>)}
                <Typography sx={{ marginTop: "30px" }}>Don`t have an account? <Link href="#">Sign Up</Link></Typography>
            </Box>
        </Modal>
    );
}