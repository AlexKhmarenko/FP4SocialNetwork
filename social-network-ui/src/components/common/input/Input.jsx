import * as React from 'react';
import { useState } from 'react'
import IconButton from '@mui/material/IconButton';
import PropTypes from 'prop-types';
import InputLabel from '@mui/material/InputLabel';
import InputAdornment from '@mui/material/InputAdornment';
import FormControl from '@mui/material/FormControl';
import Visibility from '@mui/icons-material/Visibility';
import VisibilityOff from '@mui/icons-material/VisibilityOff';
import { CssTextField } from "./styled"


const BasicInput = (props) => {
    const { type, iconStatus, placeholder, value, onChange } = props
    const [showPassword, setShowPassword] = useState(false);
    const inputType = type === "password" ? (showPassword ? 'text' : 'password') : type
    return (
        <>
            <div>
                <FormControl sx={{ m: 1, width: '50ch' }} variant="filled" onChange={(e)=> onChange(e.target.value)}>
                    <InputLabel htmlFor="filled-adornment-password">{placeholder}</InputLabel>
                    <CssTextField
                        id="filled-adornment-password"
                        type= {inputType}
                        endAdornment={
                            <InputAdornment position="end" value={value} >
                                <IconButton
                                    aria-label="toggle password visibility"
                                    onClick={() => setShowPassword(!showPassword)}
                                    edge="end"
                                >
                                    {iconStatus ? (showPassword ? <VisibilityOff /> : <Visibility />): null}
                                </IconButton>
                            </InputAdornment>
                        }
                    />
                </FormControl>
            </div>
        </>

    )
}

BasicInput.propTypes = {
    type: PropTypes.string,
    iconStatus: PropTypes.bool,
    placeholder: PropTypes.string,
    value: PropTypes.string,
    onChange: PropTypes.func,
};

export default BasicInput