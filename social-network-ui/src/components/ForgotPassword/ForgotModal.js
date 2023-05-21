import React, { useState } from 'react';
import PropTypes from 'prop-types';
import {useDispatch} from "react-redux"
import { modalConfig } from './modalConfig';
import { Button, Typography, Box, FormControl } from "@mui/material";
import { StyledBox, StyledModalText } from "./style"
import InputFieldWithError from "../common/input"
import { useModal } from '../../context/ModalContext';
import { Field, Form, Formik } from "formik";
import * as Yup from "yup";
import { checkEmail } from "../../store/actions"
import {
    StyledBlackButton,
    StyledFormControl,
    StyledHeaderModalText
} from "../LoginModal/loginModalStyles"

import Logo from "../common/icon/Logo";
import CloseIcon from '../common/icon/CloseIcon';


export const ForgotModal = ({ id }) => {
 const dispatch = useDispatch()
    const { setOpenForgot, setOpenSendCode } = useModal()
    const [isSubmitting, setIsSubmitting] = useState(false);
    const { text,
        title,
        buttonText,
        name,
        inputType,
        typeButton } = modalConfig[id]
    return (
        <Box sx={StyledBox}>
            <CloseIcon onClick={() => setOpenForgot(false)} />
            <Logo />
            <Typography sx={StyledHeaderModalText} variant="h6" component="h2">
                {title}
            </Typography>
            <Typography id="modal-modal-description" sx={StyledModalText}>
                {text}
            </Typography>
            <Formik initialValues={{
                email: "",
            }} validationSchema={
                Yup.object({
                    email: Yup.string().email("Please enter a correct email").required("email is required")
                })} onSubmit={async (values, { setErrors, setSubmitting }) => {
                    setIsSubmitting(true);
                    try {
                        const res = await fetch("http://localhost:8080/api/changepassword", {
                            method: "POST",
                            headers: { "Content-Type": "application/json" },
                            body: JSON.stringify({
                                email: values.email,
                            })
                        })
                        console.log(res)
                        if (res.ok) {
                            const data = await res.text()
const array = data.split(" ")
const email = array[array.length -1]
                            dispatch(checkEmail(email))
                            setOpenForgot(false)
                            setOpenSendCode(true)
                        }
                    }
                    catch (error) {
                        console.error("An error occurred:", error);
                        setErrors({ email: "An error occurred, please try again" });
                    } finally {
                        setIsSubmitting(false);
                        setSubmitting(false);
                    }
                }
                }>
                <Form>
                    <FormControl sx={StyledFormControl}>
                        <Field as={InputFieldWithError} sx={{ width: "400px" }} name={name}
                            id={name}
                            label="Email" disabled={isSubmitting} type={inputType} />
                        <Button type={typeButton}
                            variant="contained" disabled={isSubmitting}
                            sx={{ ...StyledBlackButton, marginTop: "20px" }}
                            fullWidth={true}>{buttonText}</Button>
                    </FormControl>
                </Form>
            </Formik>
        </Box>
    )
}
ForgotModal.propTypes = {
    id: PropTypes.string
};