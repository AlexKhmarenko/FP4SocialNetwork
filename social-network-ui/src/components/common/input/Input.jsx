import React, { useState } from "react";

import { useFormikContext } from "formik";
import { Typography, TextField, InputAdornment } from "@mui/material";
import PropTypes from "prop-types";

function InputFieldWithError({
                                        label,
                                        name,
                                        type,
                                        ...props
                                    }) {
    const { errors, touched } = useFormikContext();
    const hasError = errors[name] && touched[name];

    const [passwordView, setPasswordView] = useState("password");

    function handlePasswordViewChange() {
        passwordView === "password" ? setPasswordView("text") : setPasswordView("password");
    }

    return (
        type === "password" ? (<>
            <TextField
                label={label}
                variant="outlined"
                error={hasError}
                name={name}
                type={passwordView}
                sx={{ marginBottom: "5px", width: "100%", fontFamily: "'Lato', sans-serif",
                    fontSize: "19px",
                    lineHeight: "23px" }}
                InputLabelProps={{
                    sx: { color: hasError ? "error.main" : "text.primary",  fontFamily: "'Lato', sans-serif",
                        fontSize: "19px",
                        lineHeight: "23px" },
                    shrink: true,
                }}
                autoComplete={`username-${Math.random().toString(36).substr(2, 8)}`}
                {...props}
                InputProps={{
                    endAdornment: (
                        <InputAdornment position="end" onClick={handlePasswordViewChange}>
                            <svg
                                fill="#000000"
                                height="22px"
                                width="22px"
                                version="1.1"
                                id="Layer_1"
                                viewBox="0 0 512 512"
                            >
                                <g>
                                    <g>
                                        <g>
                                            <path d="M511.074,250.323c-81.746-243.856-428.361-243.98-510.148,0c-1.234,3.684-1.234,7.669,0,11.353
                                            c81.746,243.856,428.362,243.98,510.148,0C512.308,257.994,512.308,254.007,511.074,250.323z M36.778,256
                                            c23.559-64.753,73.805-114.34,135.81-138.035C125.926,146.268,94.681,197.553,94.681,256s31.245,109.732,77.909,138.035
                                            C110.584,370.341,60.339,320.753,36.778,256z M130.402,256c0-69.255,56.343-125.597,125.597-125.597S381.597,186.746,381.597,256
                                            S325.255,381.598,256,381.598S130.402,325.255,130.402,256z M339.411,394.034c46.662-28.303,77.909-79.587,77.909-138.035
                                            c0-58.448-31.245-109.732-77.909-138.035c62.006,23.694,112.251,73.282,135.811,138.035
                                            C451.661,320.753,401.415,370.341,339.411,394.034z"/>
                                            <circle cx="256" cy="256" r="15.979"/>
                                            <circle cx="199.882" cy="256" r="15.979"/>
                                            <circle cx="312.118" cy="256" r="15.979"/>
                                        </g>
                                    </g>
                                </g>
                            </svg>
                        </InputAdornment>)
                }}
            />
            {hasError && (
                <Typography color="error" sx={{ fontFamily: "'Lato', sans-serif",
                    fontSize: "19px",
                    lineHeight: "23px"}} variant="caption">
                    {errors[name]}
                </Typography>
            )}
        </>) : (
            <>
                <TextField
                    label={label}
                    variant="outlined"
                    error={hasError}
                    name={name}
                    sx={{ marginBottom: "5px", width: "100%" , fontFamily: "'Lato', sans-serif",
                        fontSize: "19px",
                        lineHeight: "23px"}}
                    InputLabelProps={{
                        sx: { color: hasError ? "error.main" : "text.primary",  fontFamily: "'Lato', sans-serif",
                            fontSize: "19px",
                            lineHeight: "23px" },
                        shrink: true,
                    }}
                    autoComplete={`username-${Math.random().toString(36).substr(2, 8)}`}
                    {...props}
                />
                {hasError && (
                    <Typography color="error" variant="caption">
                        {errors[name]}
                    </Typography>
                )}
            </>
        )
    );
}

InputFieldWithError.propTypes = {
    name: PropTypes.string.isRequired,
    label: PropTypes.string.isRequired,
    type: PropTypes.string,
};




// import * as React from 'react';
// import { useState } from 'react'
// import IconButton from '@mui/material/IconButton';
// import PropTypes from 'prop-types';
// import InputLabel from '@mui/material/InputLabel';
// import InputAdornment from '@mui/material/InputAdornment';
// import FormControl from '@mui/material/FormControl';
// import Visibility from '@mui/icons-material/Visibility';
// import VisibilityOff from '@mui/icons-material/VisibilityOff';
// import { CssTextField } from "./styled"


// const BasicInput = (props) => {
//     const { type, iconStatus, placeholder, value, onChange } = props
//     const [showPassword, setShowPassword] = useState(false);
//     const inputType = type === "password" ? (showPassword ? 'text' : 'password') : type
//     return (
//         <>
//             <div>
//                 <FormControl sx={{ m: 1, width: '50ch' }} variant="filled" onChange={(e)=> onChange(e.target.value)}>
//                     <InputLabel htmlFor="filled-adornment-password">{placeholder}</InputLabel>
//                     <CssTextField
//                         id="filled-adornment-password"
//                         type= {inputType}
//                         endAdornment={
//                             <InputAdornment position="end" value={value} >
//                                 <IconButton
//                                     aria-label="toggle password visibility"
//                                     onClick={() => setShowPassword(!showPassword)}
//                                     edge="end"
//                                 >
//                                     {iconStatus ? (showPassword ? <VisibilityOff /> : <Visibility />): null}
//                                 </IconButton>
//                             </InputAdornment>
//                         }
//                     />
//                 </FormControl>
//             </div>
//         </>

//     )
// }

// BasicInput.propTypes = {
//     type: PropTypes.string,
//     iconStatus: PropTypes.bool,
//     placeholder: PropTypes.string,
//     value: PropTypes.string,
//     onChange: PropTypes.func,
// };

// export default BasicInput


export default InputFieldWithError