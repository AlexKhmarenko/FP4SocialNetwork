import * as React from 'react';
import PropTypes from 'prop-types';
import Box from '@mui/material/Box';
import Typography from '@mui/material/Typography';
import Modal from '@mui/material/Modal';
import { style } from "./style"
import BasicButton from '../button';
import {modalConfig} from "./modalConfig.js"
import BasicInput from "../input"


const BasicModal = ({handleClose, open, id}) => {
    const {text, title, buttonText, placeholder} = modalConfig[id]
  return (
    <div>
      <Modal
        open={open}
        onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
      >
        <Box sx={style}>
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
    <BasicInput/>
    </Box>
         
      <BasicButton text={buttonText} onClick={handleClose}/>
       </Box>
      </Modal>
    </div>
  );
}

BasicModal.propTypes = {
    handleClose: PropTypes.func,
    open: PropTypes.bool,
    id: PropTypes.string
};

export default BasicModal