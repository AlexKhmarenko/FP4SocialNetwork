import React, {useState} from 'react';
import PropTypes from 'prop-types';
import { modalConfig } from '../modalConfig';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { StyledBox, StyledHeaderModalText, StyledModalText, StyledFormControl, StyledSpanElement, StyledWhiteButton  } from "../style"
import BasicButton from '../../button';
// import BasicInput from "../../input"
import InputFieldWithError from "../../input"
import { useModal } from '../../../../context/ModalContext';

import { Field, Form, Formik } from "formik";
import * as Yup from "yup";


// import { ThemeProvider } from '@mui/material/styles';

import Logo from "../../icon/Logo";
import CloseIcon from '../../icon/CloseIcon';


export const ForgotModal = ({ id }) => {
    const {setOpenForgot, setOpenSendCode} = useModal()
    const [credential, setCredential] = useState("")
    // const userDataState = useSelector(state => state.loginUserData.userData);
    const userDataState = {};
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
        <Formik validate={async (values) => {
            // const url = new URL("http://localhost:8080/login");
            // url.searchParams.append("username", values.userName);
            // url.searchParams.append("password", values.password);
            // url.searchParams.append("rememberMe", userDataState.rememberMe);
            // const userPassword = await fetch(url.toString());
            // const userToken = await userExist.json();
            const userToken = true;
            if (!userToken) {
                return { password: "wrong password" };
            }else{
                localStorage.setItem('userToken', JSON.stringify(userToken));
            }
        }}
                initialValues={{
                    userName: userDataState.userName || "",
                    password: "",
                }} validationSchema={
            Yup.object(
                {
                    password: Yup.string().required("Password is required")
                }
            )} onSubmit={(values) => {
            // dispatch(setUserPassword(values));
        }}>
        <Box sx={StyledBox}>
            <CloseIcon onClick={() => setOpenForgot(false)}/>
            <Logo/>
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
                onSubmit={handleSubmit}
            >
                                        <Field as={InputFieldWithError} sx={{ width: "400px" }} name={placeholder}
                               id="userName"
                               label={placeholder} type="text"/>

                {/* <BasicInput type={inputType} iconStatus={iconStatus} placeholder={placeholder} value={credential} onChange={setCredential}/> */}
                <BasicButton text={buttonText} color="black" type={typeButton} />

            </Box>
        </Box>
        </Formik>
    )
}
ForgotModal.propTypes = {
    id: PropTypes.string
};