import * as React from 'react';
import PropTypes from 'prop-types';
import { modalConfig } from './modalConfig';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import { style } from "./style"
import BasicButton from '../button';
import { useModal } from '../../../context/ModalContext';
import { IconWrapper } from '../iconWrapper';
import {Close} from "./Close"

export const AllSet = ({ id }) => {
    const {setOpenAllSet} = useModal()
    const { text,
        boldText,
        buttonText } = modalConfig[id]
        const handleClick = () => {
            setOpenAllSet(false)
        }
    return (
        <Box sx={style}>
            <Close onClick={() => setOpenAllSet(false)} />
            <IconWrapper/>
            <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                {text}
            </Typography>

            <Typography id="modal-modal-description" sx={{ mt: 2 }}>
                {boldText}
            </Typography>

            <BasicButton text={buttonText} onClick={handleClick} />
        </Box>
    )
}
AllSet.propTypes = {
    id: PropTypes.string
};