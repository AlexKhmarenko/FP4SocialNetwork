import React from "react";
import { Route, createBrowserRouter, createRoutesFromElements, RouterProvider } from "react-router-dom";

import { Layout } from "./components/Layout";
import { RegistrationPage} from "./pages/RegistrationPage";
import { NotFoundPage } from "./pages/NotFoundPage";

const router = createBrowserRouter(
    createRoutesFromElements(
        <Route path="/" element={<Layout/>}>
            <Route path="/" element={<RegistrationPage/>}/>
            <Route path="*" element={<NotFoundPage/>}/>
            <Route path="/explore" element={<RegistrationPage/>}/>
        </Route>
    )
);

export function App() {
    return (
        <RouterProvider router={router}/>
    );
}