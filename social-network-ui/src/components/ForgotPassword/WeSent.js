import * as React from 'react';
import { useDispatch, useSelector } from "react-redux"
import PropTypes from 'prop-types';
import { modalConfig } from './modalConfig';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { StyledBox, StyledHeaderModalText, StyledModalText, StyledBlackButton, StyledFormControl, StyledSpanElement, StyledWhiteButton } from "./style"
import BasicButton from '../common/button';
// import BasicInput from "../../input"
import InputFieldWithError from "../common/input"
import { useModal } from '../../context/ModalContext';
import {useState} from "react"

import Logo from "../common/icon/Logo";
import CloseIcon from '../common/icon/CloseIcon';

import { Field, Form, Formik } from "formik";
import * as Yup from "yup";

export const WeSent = ({ id }) => {
    const email = useSelector(state => state.forgot.forgotPasswordEmail)
    const { setOpenWeSend, setOpenChoose } = useModal()
    const [isSubmitting, setIsSubmitting] = useState(false);

    const { text,
        title,
        buttonText,
        placeholder,
        iconStatus,
        inputType } = modalConfig[id]
    const handleClick = () => {
    }
    return (
        <Formik
            initialValues={{
                password: "",
            }} validationSchema={
                Yup.object(
                    {
                        password: Yup.string().required("Password is required")
                    }
                )} onSubmit= {async (values, { setErrors, setSubmitting }) => {

                
                    try {
                        const res = await fetch("http://localhost:8080/api/codecheck", {
                            method: "POST",
                            headers: { "Content-Type": "application/json" },
                            body: JSON.stringify({
                                email: email,
                            })
                        })
                        if (res.ok) {
                            const data = await res.json()
                            console.log(data)
                            // setOpenWeSend(false);
                            // setOpenChoose(true)
                    
                        }
                    }
                    catch (error) {
                        console.error("An error occurred:", error);
                        setErrors({ email: "An error occurred, please try again" });
                    }
            

                    // dispatch(setUserPassword(values));
                }}>
            <Box sx={StyledBox}>
                <CloseIcon onClick={() => setOpenForgot(false)} />
                <Logo />
                <Typography sx={StyledHeaderModalText} variant="h6" component="h2">
                    {title}
                </Typography>
                <Typography id="modal-modal-description" sx={StyledModalText}>
                    {text}
                </Typography>

                <Box
                    component="form"
                    sx={StyledFormControl}
                    noValidate
                    autoComplete="off"
                >
                    {/* <InputFieldWithError /> */}
                    <Field as={InputFieldWithError} sx={{ width: "400px" }} name={placeholder}
                        id="userName"
                        label={placeholder} type="text" />
                    {/* <BasicInput type={inputType} iconStatus={iconStatus} placeholder={placeholder} /> */}
                </Box>

                <BasicButton text={buttonText} onClick={handleClick} />
            </Box>
        </Formik>
    )
}
WeSent.propTypes = {
    id: PropTypes.string
};