import React, {useState} from 'react';
import PropTypes from 'prop-types';
import { modalConfig } from './modalConfig';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { style, theme } from "./style"
import BasicButton from '../button';
import BasicInput from "../input"
import { useModal } from '../../../context/ModalContext';
import { IconWrapper } from '../iconWrapper';
import {Close} from "./Close"
import { ThemeProvider, createTheme } from '@mui/material/styles';


export const ForgotModal = ({ id }) => {
    const {setOpenForgot, setOpenSendCode} = useModal()
    const [credential, setCredential] = useState("")
    const { text,
        title,
        buttonText,
        placeholder,
        iconStatus,
        inputType,
    typeButton } = modalConfig[id]
        const handleSubmit = (e) => {
            e.preventDefault()
            const formData = {credential}
            console.log(formData)
            setOpenForgot(false);
            setOpenSendCode(true)
        }
    return (
        <ThemeProvider theme={theme}>
        <Box sx={style}>
            <Close onClick={() => setOpenForgot(false)} />
            <IconWrapper/>
            <Typography id="modal-modal-title" variant="h6" component="h2">
                {title}
            </Typography>
            <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                {text}
            </Typography>

            <Box
                component="form"
                sx={{
                    '& .MuiTextField-root': { m: 1, width: '25ch' },
                }}
                noValidate
                autoComplete="off"
                onSubmit={handleSubmit}
            >
                <BasicInput type={inputType} iconStatus={iconStatus} placeholder={placeholder} value={credential} onChange={setCredential}/>
                <BasicButton text={buttonText} type={typeButton} />

            </Box>
        </Box>
        </ThemeProvider>
    )
}
ForgotModal.propTypes = {
    id: PropTypes.string
};