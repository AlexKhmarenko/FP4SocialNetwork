import * as React from 'react';
import PropTypes from 'prop-types';
import { modalConfig } from './modalConfig';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { style, theme } from "./style"
import BasicButton from '../button';
import {Link} from "react-router-dom"
import { useModal } from '../../../context/ModalContext';
import { changeEmail } from '../../../util/util';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
import { IconWrapper } from '../iconWrapper';
import {Close} from "./Close"
import { ThemeProvider, createTheme } from '@mui/material/styles';


export const SendCodeModal = ({ id }) => {
    const email = "demo@gmail.com"
    const updatedEmail = changeEmail(email)
    console.log(updatedEmail)
    const {setOpenSendCode, setOpenWeSend} = useModal()
    const { title,
        text,
        secondText,
        buttonText,
        secondaryButtonText,
        link,
        linkText,
        secondLinkText,
        boldText } = modalConfig[id]
        const handleClick = () => {
            setOpenSendCode(false);
            setOpenWeSend(true)
        }
        const handleClose = () => {
            setOpenSendCode(false)
        }
    return (
        <ThemeProvider theme={theme}>
        <Box sx={style}>
            <Close onClick={() => setOpenSendCode(false)} />
            <IconWrapper/>
            <Typography id="modal-modal-title" variant="h6" component="h2">
                {title}
            </Typography>

            <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                {text}
            </Typography>
            <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                {secondText}
            </Typography>

            <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                {boldText} {updatedEmail}
                <CheckCircleIcon/>
            </Typography>

            <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                {linkText}
                <Link path="/support">{link}</Link>
                {secondLinkText}
            </Typography>

            <BasicButton text={buttonText} onClick={handleClick} />
            <BasicButton variant="secondarybtn" text={secondaryButtonText} onClick={handleClose} />
        </Box>
        </ThemeProvider>
    )
}
SendCodeModal.propTypes = {
    id: PropTypes.string
};