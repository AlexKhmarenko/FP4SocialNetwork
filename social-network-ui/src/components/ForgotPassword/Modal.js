import * as React from 'react';
import PropTypes from 'prop-types';
import Modal from '@mui/material/Modal';
import { ForgotModal } from './ForgotModal';
import { SendCodeModal } from './SendCodeModal';
import { WeSent } from './WeSent';
import { Choose } from './Choose';
import { AllSet } from './AllSet';
import {StyledModal} from "./style.js"
import { useModal } from '../../context/ModalContext';


export const ForgotPasswordModal = ({id}) => {
  const {openForgot, setOpenForgot,
    openSendCode, setOpenSendCode,
    openWeSend, setOpenWeSend,
    openChoose, setOpenChoose,
    openAllSet, setOpenAllSet} = useModal()
const isOpen = openForgot ||
openSendCode ||
openWeSend ||
openChoose ||
openAllSet
console.log(isOpen, openForgot, openSendCode)
    let content = null
    if(id==="forgot"){
    content = <ForgotModal id={id}/>
    }
    if(id==="sendCode"){
        content = <SendCodeModal id={id}/>
    }
    if(id==="weSent"){
        content = <WeSent id={id}/>
    }
    if(id==="choose"){
        content = <Choose id={id}/>
    }
    if(id==="allSet"){
        content = <AllSet id={id}/>
    }
  return (
    <div>
      <Modal
        open={isOpen}
        // onClose={handleClose}
        aria-labelledby="modal-modal-title"
        aria-describedby="modal-modal-description"
        sx={StyledModal}>
        {content}
      </Modal>
    </div>
  );
}

ForgotPasswordModal.propTypes = {
    id: PropTypes.string
}