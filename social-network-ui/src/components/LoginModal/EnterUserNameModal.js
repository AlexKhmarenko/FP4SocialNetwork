import React from "react";
import { Button, FormControl, Typography } from "@mui/material";
import { Field, Form, Formik } from "formik";
import * as Yup from "yup";
import { useDispatch } from "react-redux";

import { setUserData } from "../../store/actions";
import { InputFieldWithError } from "./InputFieldWithError";
import {
    StyledBlackButton,
    StyledFormControl,
    StyledHeaderModalText,
    StyledSpanElement,
    StyledWhiteButton
} from "./loginModalStyles";

export function EnterUserNameModal() {
    const dispatch = useDispatch();

    return (
        <>
            <Typography sx={StyledHeaderModalText}>Sign in to Twitter</Typography>
            <Button variant="contained" sx={{ ...StyledBlackButton, marginTop: "0px" }} fullWidth={true}>With
                Google!!</Button>
            <Typography component="span" sx={StyledSpanElement}
            >or</Typography>
            <Formik initialValues={{
                userName: "",
            }} validationSchema={
                Yup.object(
                    {
                        userName: Yup.string().required("Username is required")
                    }
                )} onSubmit={(values) => {
                dispatch(setUserData(values));
            }}>
                <Form>
                    <FormControl sx={StyledFormControl}>
                        <Field as={InputFieldWithError} sx={{ width: "400px" }} name={"userName"}
                               id="userName"
                               label="userName" type="text"/>
                        <Button type="submit"
                                variant="contained" sx={StyledBlackButton} fullWidth={true}>Next</Button>
                        <Button variant="contained" sx={StyledWhiteButton} fullWidth={true}>Forgot password?</Button>
                    </FormControl>
                </Form>
            </Formik>
        </>
    );
}