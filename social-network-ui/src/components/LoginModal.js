import React from "react";
import { Modal, Typography, Box, FormControl, InputLabel, Input, Button, Link, SvgIcon } from "@mui/material";
import { Formik, Form, Field, ErrorMessage, useFormikContext } from "formik";
import PropTypes from "prop-types";
import * as Yup from "yup";

export function LoginModal() {
    const userData = {};

    const ErrorText = ({ children }) => (
        <Typography color="error" variant="caption">
            {children}
        </Typography>
    );

    ErrorText.propTypes = {
        children: PropTypes.string.isRequired,
    };

    return (
        <Modal
            open={true}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
            sx={{
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                outline: "none",
            }}>
            <Box sx={{
                width: 500,
                height: 600,
                background: "#ffff",
                outline: "none",
                display: "flex",
                justifyContent: "center",
                alignItems: "center",
                flexDirection: "column",
                borderRadius: "10px",
                overflow: "visible",
                position: "relative",
            }}>
                <SvgIcon sx={{
                    position: "absolute", top: "20px",
                    right: "20px",
                }} width="30px" height="30px" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
                    <path fillRule="evenodd" clipRule="evenodd"
                          d="M19.207 6.207a1 1 0 0 0-1.414-1.414L12 10.586 6.207 4.793a1 1 0 0 0-1.414 1.414L10.586 12l-5.793 5.793a1 1 0 1 0 1.414 1.414L12 13.414l5.793 5.793a1 1 0 0 0 1.414-1.414L13.414 12l5.793-5.793z"
                          fill="#000000"/>
                </SvgIcon>
                <SvgIcon viewBox="0 0 24 24" sx={{ width: "40px", height: "40px", marginBottom: "20px" }}>
                    <path
                        d="M23.643 4.937c-.835.37-1.732.62-2.675.733.962-.576 1.7-1.49 2.048-2.578-.9.534-1.897.922-2.958 1.13-.85-.904-2.06-1.47-3.4-1.47-2.572 0-4.658 2.086-4.658 4.66 0 .364.042.718.12 1.06-3.873-.195-7.304-2.05-9.602-4.868-.4.69-.63 1.49-.63 2.342 0 1.616.823 3.043 2.072 3.878-.764-.025-1.482-.234-2.11-.583v.06c0 2.257 1.605 4.14 3.737 4.568-.392.106-.803.162-1.227.162-.3 0-.593-.028-.877-.082.593 1.85 2.313 3.198 4.352 3.234-1.595 1.25-3.604 1.995-5.786 1.995-.376 0-.747-.022-1.112-.065 2.062 1.323 4.51 2.093 7.14 2.093 8.57 0 13.255-7.098 13.255-13.254 0-.2-.005-.402-.014-.602.91-.658 1.7-1.477 2.323-2.41z"></path>
                </SvgIcon>
                <Typography sx={{ marginBottom: "30px", fontWeight: 700 }}>Sign in to Twitter</Typography>
                <Button variant="contained" sx={{
                    height: "45px",
                    marginTop: "0", width: "400px", background: "#000000",
                    transition: "0.7s", "&:hover": {
                        transition: "0.7s",
                        backgroundColor: "#ffffff",
                        color: "#000000"
                    },
                    fontWeight: 700,
                    borderRadius: "20px",
                }} fullWidth={true}>With Google!!</Button>
                <Typography component="span" sx={{
                    position: "relative",
                    marginTop: "30px",
                    color: "#808080",
                    marginBottom: "30px",
                    "&::before": {
                        content: "\"\"",
                        position: "absolute",
                        width: "100px",
                        height: "1px",
                        backgroundColor: "#808080",
                        left: "30px",
                        top: "50%",
                        transform: "translateY(-50%)",
                    },
                    "&::after": {
                        content: "\"\"",
                        position: "absolute",
                        width: "100px",
                        height: "1px",
                        backgroundColor: "#808080",
                        right: "30px",
                        top: "50%",
                        transform: "translateY(-50%)",
                    },
                }}
                >or</Typography>
                <Formik initialValues={{
                    userName: "",
                }} validationSchema={
                    Yup.object(
                        {
                            userName: Yup.string().required("Username is required")
                        }
                    )} onSubmit={(values, { setSubmitting }) => {
                    // Обработка отправки формы
                }}>
                    {formikProps => (
                        <>
                            <FormControl>
                                <InputLabel htmlFor="userName">username</InputLabel>
                                <Input sx={{ width: "400px" }} id="userName" type="text"/>
                                <ErrorMessage name="userName" component={<Input
                                    color={formikProps.touched.name && formikProps.errors.name && "error"}/>}/>
                            </FormControl>
                            <Button type="submit"
                                    disabled={formikProps.isSubmitting} variant="contained" sx={{
                                height: "45px",
                                marginTop: "40px", width: "400px", background: "#000000",
                                transition: "0.7s", "&:hover": {
                                    transition: "0.7s",
                                    backgroundColor: "#ffffff",
                                    color: "#000000"
                                },
                                fontWeight: 700,
                                borderRadius: "20px",
                            }} fullWidth={true}>Next</Button>
                            <Button variant="contained" sx={{
                                height: "45px",
                                marginTop: "20px", width: "400px", background: "#ffffff", color: "#000000",
                                transition: "0.7s", "&:hover": {
                                    transition: "0.7s",
                                    backgroundColor: "#000000",
                                    color: "#ffffff"
                                },
                                fontWeight: 700,
                                borderRadius: "20px",
                            }} fullWidth={true}>Forgot password?</Button>
                        </>
                    )
                    }
                </Formik>

                <Typography sx={{ marginTop: "30px" }}>Don`t have an account? <Link href="#">Sign Up</Link></Typography>
            </Box>
        </Modal>
    );
}