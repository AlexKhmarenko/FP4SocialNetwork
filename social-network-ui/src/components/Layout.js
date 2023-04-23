import React from "react";
import { Outlet } from "react-router-dom";
import { Link } from "react-router-dom";
import { LoginModal } from "./LoginModal";
// here will be all components for example it  should  look like this:
// export function Layout() {
//     return (
//         <Wrapper>
//                 <Header/>
//                 <Outlet/>
//                 <Footer/>
//         </Wrapper>
//     );
// }



export function Layout() {

    return (

        <>
            <Outlet/>
            <LoginModal/>
            <Link to="*" variant="contained">To not found page 404</Link>
        </>
    );
}