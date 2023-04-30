import React from "react";
import BasicButton from "../components/common/button"
import BasicModal from "../components/common/modal"
import { useModal } from "../context/ModalContext";

export function HomePage() {
  const {openForgot, openSendCode, openWeSend, openChoose, openAllSet, setOpenForgot} = useModal()
  return (
        <>
       <div>Home Page</div>
       <BasicButton onClick={() => setOpenForgot(!openForgot)} text="Forgot password"/>
       <BasicModal open = {openForgot} id="forgot"/>
       <BasicModal open = {openSendCode} id="sendCode"/>
       <BasicModal open = {openWeSend} id="weSent"/>
       <BasicModal open = {openChoose} id="choose"/>
        <BasicModal open = {openAllSet} id="allSet"/>
       </>
    );
}