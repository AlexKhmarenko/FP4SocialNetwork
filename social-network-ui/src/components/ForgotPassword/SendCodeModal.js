import * as React from 'react';
import { useDispatch, useSelector } from "react-redux"
import PropTypes from 'prop-types';
import { modalConfig } from './modalConfig';
import {
    Button,
    FormControl,
    Typography,
    Box,
    FormControlLabel,
} from "@mui/material";
import { checkEmail } from "../../store/actions"
import {
    StyledHeaderModalText,
    StyledFormControl,
    StyledBlackButton,
    StyledWhiteButton,
    StyledCheckbox
} from "../LoginModal/loginModalStyles";

import { StyledBox } from "./style"
import BasicButton from '../common/button';
import { Link } from "react-router-dom"
import { useModal } from '../../context/ModalContext';
import { changeEmail } from '../../util/util';
import CheckCircleIcon from '@mui/icons-material/CheckCircle';
// import { ThemeProvider, createTheme } from '@mui/material/styles';

import Logo from "../common/icon/Logo";
import CloseIcon from '../common/icon/CloseIcon';

export const SendCodeModal = ({ id }) => {
    const dispatch = useDispatch()
    const email = useSelector(state => state.forgot.forgotPasswordEmail)
    console.log(email)
    const updatedEmail = changeEmail(email)

    const { setOpenSendCode, setOpenWeSend } = useModal()
    const { title,
        text,
        secondText,
        buttonText,
        secondaryButtonText,
        link,
        linkText,
        secondLinkText,
        boldText } = modalConfig[id]
    const handleClick = async (event) => {
        event.preventDefault()
        try {
            const res = await fetch("http://localhost:8080/api/changepassword", {
                method: "POST",
                headers: { "Content-Type": "application/json" },
                body: JSON.stringify({
                    email: email,
                })
            })
            if (res.ok) {
                const data = await res.json()
                dispatch(checkEmail(data.email))
                setOpenSendCode(false);
                setOpenWeSend(true)
            }
        }
        catch (error) {
            console.error("An error occurred:", error);
            setErrors({ email: "An error occurred, please try again" });
        }
    }
    const handleClose = () => {
        setOpenSendCode(false)
    }
    return (
        <Box sx={StyledBox}>
            <CloseIcon onClick={handleClose} />
            <Logo />
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
                <CheckCircleIcon />
            </Typography>

            <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                {linkText}
                <Link path="/support">{link}</Link>
                {secondLinkText}
            </Typography>
            <Button type="submit"
                variant="contained" sx={StyledBlackButton} onClick={handleClick} fullWidth={true}>{buttonText}
            </Button>
            <Button variant="contained" sx={StyledWhiteButton} fullWidth={true} onClick={handleClose} >{secondaryButtonText}</Button>
        </Box>
    )
}
SendCodeModal.propTypes = {
    id: PropTypes.string
};