import React, {useState} from "react";
import BasicButton from "../components/common/button"
import BasicModal from "../components/common/modal"

export function HomePage() {
    const [open, setOpen] = useState(false);
    const handleOpen = () => setOpen(true);
    const handleClose = () => setOpen(false);
  
  return (
        <>
       <div>Home Page</div>
       <BasicButton onClick={handleOpen} text="Forgot password"/>
       <BasicModal handleClose={handleClose} open = {open} id="forgot"/>
       </>
    );
}