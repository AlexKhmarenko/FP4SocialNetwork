import * as React from 'react';
import PropTypes from 'prop-types';
import { modalConfig } from './modalConfig';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { style } from "./style"
import BasicButton from '../button';
import BasicInput from "../input"
import {Link} from "react-router-dom"
import { useModal } from '../../../context/ModalContext';
import { IconWrapper } from '../iconWrapper';
import {Close} from "./Close"

export const Choose = ({ id }) => {
    const {setOpenChoose, setOpenAllSet} = useModal()
    const { title,
        text,
        buttonText,
        placeholder,
        iconStatus,
        inputType,
        link,
        linkText} = modalConfig[id]
        const handleClick = () => {
            setOpenChoose(false);
            setOpenAllSet(true)
        }
    return (
        <Box sx={style}>
            <Close onClick={() => setOpenChoose(false)} />
            <IconWrapper/>
            <Typography id="modal-modal-title" variant="h6" component="h2">
                {title}
            </Typography>
            <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                {text}
            </Typography>

            <Box
                component="form"
                sx={{
                    '& .MuiTextField-root': { m: 1, width: '25ch' },
                }}
                noValidate
                autoComplete="off"
            >
                <BasicInput type={inputType} iconStatus={iconStatus} placeholder={placeholder} />
            </Box>

            <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                {linkText}
                <Link path="/support">{link}</Link>
            </Typography>

            <BasicButton text={buttonText} onClick={handleClick} />
        </Box>
    )
}
Choose.propTypes = {
    id: PropTypes.string
};