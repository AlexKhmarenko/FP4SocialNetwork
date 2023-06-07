import React, { useState } from "react";

import { Button, FormControl, Typography, SvgIcon } from "@mui/material";
import { Field, Form, Formik } from "formik";
import * as Yup from "yup";
import { useDispatch } from "react-redux";
import { closeLoginModal } from "../../store/actions";

import { setUserEmail } from "../../store/actions";
import { InputFieldWithError } from "./InputFieldWithError";
import {
    EnterEmailModalLink,
    StyledBlackButton,
    StyledFormControl,
    StyledHeaderModalText,
    StyledSpanElement,
    StyledWhiteButton
} from "./loginModalStyles";
import PropTypes from "prop-types";
import { useModal } from "../../context/ModalContext";
import { GoogleSvgIcon } from "./GoogleSvgIcon";

export function EnterEmailModal() {
    const dispatch = useDispatch();
    const [isSubmitting, setIsSubmitting] = useState(false);
    const { openForgot, setOpenForgot } = useModal();

    const handleForgot = () => {
        setOpenForgot(!openForgot);
        dispatch(closeLoginModal());
    };

    return (
        <>
            <Typography sx={StyledHeaderModalText}>Sign in to Capitweet</Typography>
            <a href="http://localhost:8080/oauth2/authorization/google" style={{
                ...StyledBlackButton,
               ...EnterEmailModalLink,
            }}><GoogleSvgIcon/> Sign in with Google</a>
            <Typography component="span" sx={StyledSpanElement}
            >or</Typography>
            <Formik initialValues={{
                email: "",
            }} validationSchema={
                Yup.object(
                    {
                        email: Yup.string().email("Please enter a correct email").required("email is required")
                    }
                )} onSubmit={async (values, { setErrors, setSubmitting }) => {
                setIsSubmitting(true);
                try {
                    const response = await fetch("http://localhost:8080/checkEmail", {
                        method: "POST",
                        body: JSON.stringify(values),
                        headers: { "Content-Type": "application/json" }
                    });
                    if (response.status !== 302) {
                        setErrors({ email: "User doesn't exist, please check your email" });
                    } else {
                        dispatch(setUserEmail(values));
                    }
                } catch (error) {
                    console.error("An error occurred:", error);
                    setErrors({ email: "An error occurred, please try again" });
                } finally {
                    setIsSubmitting(false);
                    setSubmitting(false);
                }
            }}>

                <Form>
                    <FormControl sx={StyledFormControl}>
                        <Field as={InputFieldWithError} sx={{ width: "400px" }} name={"email"}
                               id="email"
                               label="Email" disabled={isSubmitting} type="text"/>
                        <Button type="submit"
                                variant="contained" sx={StyledBlackButton} disabled={isSubmitting}
                                fullWidth={true}>Next</Button>
                        <Button variant="contained" sx={StyledWhiteButton} fullWidth={true} onClick={handleForgot}>Forgot
                            password?</Button>
                    </FormControl>
                </Form>
            </Formik>
        </>
    );
}

EnterEmailModal.propTypes = {
    userData: PropTypes.object,
};