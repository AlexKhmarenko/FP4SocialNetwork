import React from "react";
import { Button, FormControl, Input, InputLabel, Typography } from "@mui/material";
import { Field, Form, Formik } from "formik";
import * as Yup from "yup";
import { useDispatch, useSelector } from "react-redux";

import { setUserData } from "../../store/actions";
import { InputFieldWithError } from "./InputFieldWithError";
import { StyledHeaderModalText, StyledFormControl, StyledBlackButton } from "./loginModalStyles";

export function EnterPasswordModal() {
    const dispatch = useDispatch();
    const userDataState = useSelector(state => state.loginUserData.userData);

    return (
        <>
            <Typography sx={StyledHeaderModalText}>Enter your password</Typography>
            <Formik initialValues={{
                userName: userDataState.userName || "",
                password: "",
            }} validationSchema={
                Yup.object(
                    {
                        password: Yup.string().required("Username is required")
                    }
                )} onSubmit={(values) => {
                console.log(values);
                dispatch(setUserData(values));
            }}>
                <Form>
                    <FormControl sx={StyledFormControl}>
                        <InputLabel htmlFor="userName">username</InputLabel>
                        <Input sx={{ width: "400px" }} name={"userName"} id="userName"
                               disabled value={userDataState.userName} type="text"/>
                        <FormControl sx={{
                            marginTop: "10px",
                            ...StyledFormControl,
                        }}>
                            <Field as={InputFieldWithError} sx={{ width: "400px", marginTop: "40px" }}
                                   label="password" name={"password"} id="password"
                                   type="text"/>
                        </FormControl>
                        <Button type="submit"
                                variant="contained" sx={StyledBlackButton} fullWidth={true}>Log in</Button>
                    </FormControl>
                </Form>
            </Formik>
        </>
    );
}