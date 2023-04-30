import * as React from 'react';
import PropTypes from 'prop-types';
import { modalConfig } from './modalConfig';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { style } from "./style"
import BasicButton from '../button';
import BasicInput from "../input"
import { useModal } from '../../../context/ModalContext';
import { IconWrapper } from '../iconWrapper';
import {Close} from "./Close"

export const WeSent = ({ id }) => {
    const {setOpenWeSend, setOpenChoose} = useModal()
    const { text,
        title,
        buttonText,
        placeholder,
        iconStatus,
        inputType } = modalConfig[id]
        const handleClick = () => {
            setOpenWeSend(false);
            setOpenChoose(true)
        }
    return (
        <Box sx={style}>
            <Close onClick={() => setOpenWeSend(false)} />
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

            <BasicButton text={buttonText} onClick={handleClick} />
        </Box>
    )
}
WeSent.propTypes = {
    id: PropTypes.string
};