import React from "react";
import PropTypes from 'prop-types';
import { SvgIcon } from "@mui/material";
import { StyledCloseSvgIcon2 } from "./style"

const CloseIcon = ({onClick}) => {
    return (
        <>
            <SvgIcon sx={StyledCloseSvgIcon2} onClick={onClick} width="30px" height="30px" viewBox="0 0 24 24" fill="none"
                xmlns="http://www.w3.org/2000/svg">
                <path fillRule="evenodd" clipRule="evenodd"
                    d="M19.207 6.207a1 1 0 0 0-1.414-1.414L12 10.586 6.207 4.793a1 1 0 0 0-1.414 1.414L10.586 12l-5.793 5.793a1 1 0 1 0 1.414 1.414L12 13.414l5.793 5.793a1 1 0 0 0 1.414-1.414L13.414 12l5.793-5.793z"
                    fill="#000000" />
            </SvgIcon>

        </>
    )
}

CloseIcon.propTypes = {
    onClick: PropTypes.func
}

export default CloseIcon