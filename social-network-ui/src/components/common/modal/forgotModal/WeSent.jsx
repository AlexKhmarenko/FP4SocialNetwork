import * as React from 'react';
import PropTypes from 'prop-types';
import { modalConfig } from '../modalConfig';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { StyledBox, StyledHeaderModalText, StyledModalText, StyledBlackButton, StyledFormControl, StyledSpanElement, StyledWhiteButton  } from "../style"
import BasicButton from '../../button';
// import BasicInput from "../../input"
import InputFieldWithError from "../../input"
import { useModal } from '../../../../context/ModalContext';

import Logo from "../../icon/Logo";
import CloseIcon from '../../icon/CloseIcon';

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
            >
                <InputFieldWithError/>
                {/* <BasicInput type={inputType} iconStatus={iconStatus} placeholder={placeholder} /> */}
            </Box>

            <BasicButton text={buttonText} onClick={handleClick} />
        </Box>
    )
}
WeSent.propTypes = {
    id: PropTypes.string
};