import React from "react";

import {AppBar} from "@mui/material";

import { UserSearchAppBar, UserSearchContentWrapper, UserSearchWrapper } from "../NavigationStyles";
import * as Yup from "yup";
import {Field, Form, Formik} from "formik";

import {InputSearch} from "./InputSearch";
import {setUserName} from "../../../store/actions";



export function UsersSearch() {

    // fetch("http://localhost:8080/search", {
    //     method: "POST",
    //     body: JSON.stringify({
    //         "userSearch": "dya"
    //     }),
    //     headers: { "Content-Type": "application/json" }
    // }).then(r => r.json()).then(data => console.log(data))

    return (
        <div style={UserSearchWrapper}>
            <AppBar position="sticky" style={UserSearchAppBar}>
                {/*<Formik validate={async (values) => {*/}
                {/*    const url = new URL("http://localhost:8080/search");*/}
                {/*    url.searchParams.append("username", values.userName);*/}
                {/*    console.log(url)*/}
                {/*    const userExist = await fetch(url.toString())*/}
                {/*    const userExistData = await userExist.json();*/}
                {/*    if (!userExistData) {*/}
                {/*        return { userName: "User doesn't exist, please check your username" };*/}
                {/*    }*/}
                {/*}} initialValues={{*/}
                {/*    userName: "",*/}
                {/*}} validationSchema={*/}
                {/*    Yup.object(*/}
                {/*        {*/}
                {/*            userName: Yup.string().required("Username is required")*/}
                {/*        }*/}
                {/*    )}>*/}
                {/*    <Form>*/}

                {/*        <Field as={InputSearch} sx={{ width: "400px" }}*/}
                {/*               name={"userName"} id="userName"*/}
                {/*               label="Username" type="text" />*/}

                {/*    </Form>*/}
                {/*</Formik>*/}




                <Formik initialValues={{
                    userName: "",
                }} validationSchema={
                    Yup.object(
                        {
                            userName: Yup.string().required("Username is required")
                        }
                    )} validate={async (values, { setErrors, setSubmitting }) => {
                    try {
                        const response = await fetch("http://localhost:8080/search", {
                            method: "POST",
                            body: JSON.stringify({ username: values.userName }),
                            headers: { "Content-Type": "application/json" }
                        });

                        if (!response.ok) {
                            setErrors({ userName: "User doesn't exist, please check your username" });
                        } else {
                            const userExistData = await response.json();
                        }
                    } catch (error) {
                        console.error("An error occurred:", error);
                        setErrors({ userName: "An error occurred, please try again" });
                    } finally {
                        setSubmitting(false); // This will trigger a re-render
                    }
                }}>
                    <Form>

                        <Field as={InputSearch} sx={{ width: "400px" }}
                               name={"userName"} id="userName"
                               label="Username" type="text" />

                    </Form>
                </Formik>
            </AppBar>
            <div style={UserSearchContentWrapper}></div>
        </div>

    );
}
