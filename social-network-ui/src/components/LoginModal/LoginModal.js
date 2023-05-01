import React, { useEffect } from "react";
import { useSelector } from "react-redux";
import { Modal, Typography, Box, Link, SvgIcon } from "@mui/material";

import { EnterPasswordModal } from "./EnterPasswordModal";
import { EnterUserNameModal } from "./EnterUserNameModal";
import { StyledModal, StyledBox, StyledTwitSvgIcon, StyledCloseSvgIcon } from "./loginModalStyles";

export function LoginModal() {
    const userDataState = useSelector(state => state.loginUserData.userData);

    useEffect(() => {
        console.log(userDataState);
    }, [userDataState]);

    return (
        <Modal
            open={true}
            aria-labelledby="modal-modal-title"
            aria-describedby="modal-modal-description"
            sx={StyledModal}>
            <Box sx={StyledBox}>
                <SvgIcon sx={StyledCloseSvgIcon} width="30px" height="30px" viewBox="0 0 24 24" fill="none"
                         xmlns="http://www.w3.org/2000/svg">
                    <path fillRule="evenodd" clipRule="evenodd"
                          d="M19.207 6.207a1 1 0 0 0-1.414-1.414L12 10.586 6.207 4.793a1 1 0 0 0-1.414 1.414L10.586 12l-5.793 5.793a1 1 0 1 0 1.414 1.414L12 13.414l5.793 5.793a1 1 0 0 0 1.414-1.414L13.414 12l5.793-5.793z"
                          fill="#000000"/>
                </SvgIcon>
                <SvgIcon sx={StyledTwitSvgIcon} height="50px" width="50px" version="1.1" id="Layer_1"
                         viewBox="0 0 512 512">
                    <g>
                        <path style={{fill:"white"}} d="M22.777,401.568l-8.014-56.122l0.776-0.781c7.63-7.674,25.377-27.488,25.377-45.704
		c0-10.337-5.59-19.335-11.507-28.862c-7.391-11.879-15.033-24.158-15.033-41.911c0-20.741,10.471-45.665,27.326-65.048
		c19.65-22.589,45.487-35.029,72.752-35.029c32.76,0,71.394,8.112,105.478,15.27l0.285,0.06
		c28.569,6.016,53.241,11.211,71.17,11.211c2.184,0,8.83,0,36.793-17.887c10.474-6.697,21.229-14.261,29.507-20.755l2.192-1.72
		l1.177,2.525c2.497,5.353,5.023,8.784,5.048,8.818c1.159,1.578,2.949,2.478,4.913,2.478c1.298,0,2.54-0.407,3.594-1.177
		c2.698-1.988,3.282-5.805,1.294-8.503c-0.77-1.1-7.124-10.481-7.638-20.865l-0.127-2.56l2.551,0.25
		c5.753,0.563,17.794,6.347,21.772,12.412c1.13,1.727,3.035,2.757,5.098,2.757c1.179,0,2.327-0.344,3.318-0.994
		c0.78-0.516,1.412-1.178,1.874-1.961l0.595-1.009l1.168-0.075c2.297-0.149,4.653-0.224,7.004-0.224
		c38.889,0,74.348,20.489,84.29,26.769c1.152,0.728,1.816,1.937,1.816,3.319v30.012c0,7.37-20.012,15.305-63.95,15.305
		c-24.674,0-32.362-4.466-41.275-11.281c-1.072-0.821-2.347-1.254-3.69-1.254c-1.914,0-3.68,0.871-4.844,2.39
		c-0.983,1.288-1.409,2.887-1.195,4.499c0.213,1.609,1.041,3.038,2.329,4.021c5.235,4.001,11.15,8.125,20.75,10.75l2.748,0.751
		l-1.409,2.476c-37.85,66.52-78.946,84.167-86.801,87.029c-10.515,3.811-17.969,13.205-19.454,24.517
		c-3.049,23.124-6.683,54.577-6.683,73.118c0,10.02,2.056,18.771,6.11,26.012l1.843,3.292h-3.917c-6.527,0-12.201-4.391-13.8-10.679
		c-4.501-17.658-12.235-50.5-13.467-72.043c-0.041-0.652-0.187-1.3-0.432-1.915l-17.693-44.233
		c-0.924-2.328-3.139-3.828-5.645-3.828c-0.774,0-1.535,0.147-2.261,0.438c-3.107,1.246-4.625,4.794-3.379,7.907l9.593,24.001
		l-2.217,0.745c-10.288,3.458-24.813,5.139-44.404,5.139c-16.204,0-33.503-1.163-47.405-2.099c-9.63-0.65-18.726-1.265-26.373-1.466
		l-4.28-0.113l2.572-3.424c3.28-4.374,6.151-8.864,8.529-13.345c0.76-1.433,0.916-3.079,0.437-4.631
		c-0.479-1.554-1.533-2.829-2.969-3.591c-0.89-0.472-1.847-0.711-2.845-0.711c-2.26,0-4.32,1.239-5.378,3.231
		c-17.401,32.84-56.606,52.582-75.449,62.07c-10.703,5.391-13.243,6.816-13.691,10.896c-2.092,19.038-1.672,35.012,1.248,47.479
		l1.277,5.453L22.777,401.568z M415.24,124.793c-3.354,0-6.082,2.728-6.082,6.082c0,3.354,2.728,6.082,6.082,6.082h8.847
		c3.354,0,6.082-2.728,6.082-6.082c0-3.354-2.728-6.082-6.082-6.082H415.24z"/>
                        <path style={{fill:"white"}}  d="M495.413,136.249v30.012c0,4.28-15.095,13.093-61.739,13.093c-23.985,0-31.384-4.291-39.932-10.826
		c-3.638-2.787-8.847-2.09-11.633,1.548c-2.776,3.638-2.09,8.847,1.548,11.622c5.861,4.479,11.921,8.504,21.508,11.125
		c-37.432,65.786-77.685,83.147-85.636,86.045c-11.291,4.092-19.297,14.177-20.889,26.308c-3.063,23.234-6.701,54.827-6.701,73.405
		c0,11.821,2.776,20.635,6.392,27.093h-0.144c-5.518,0-10.306-3.705-11.655-9.013c-4.49-17.616-12.186-50.36-13.403-71.625
		c-0.055-0.896-0.254-1.78-0.586-2.61l-17.693-44.233c-1.692-4.257-6.524-6.325-10.782-4.622c-4.246,1.703-6.314,6.524-4.611,10.782
		l8.725,21.829c-22.371,7.52-61.694,4.899-90.955,2.93c-9.986-0.675-18.921-1.272-26.463-1.471
		c3.218-4.291,6.159-8.825,8.714-13.635c2.145-4.047,0.597-9.068-3.45-11.213s-9.068-0.608-11.213,3.439
		c-17.063,32.202-55.844,51.742-74.489,61.13c-10.693,5.385-14.298,7.199-14.896,12.629c-2.444,22.238-1.183,37.654,1.294,48.225
		l-11.899-7.94l-7.719-54.053c7.796-7.84,26.02-28.177,26.02-47.263c0-10.97-5.75-20.226-11.843-30.034
		c-7.232-11.622-14.697-23.643-14.697-40.739c0-20.226,10.262-44.598,26.783-63.596c19.219-22.094,44.465-34.27,71.083-34.27
		c32.622,0,71.238,8.128,105.308,15.283c28.73,6.049,53.544,11.257,71.625,11.257c2.588,0,9.477,0,37.985-18.235
		c10.793-6.9,21.575-14.52,29.681-20.878c2.521,5.408,5.054,8.902,5.275,9.201c1.626,2.212,4.136,3.373,6.69,3.373
		c1.703,0,3.417-0.52,4.899-1.603c3.693-2.72,4.49-7.907,1.769-11.6c-0.066-0.1-6.701-9.389-7.21-19.662
		c5.197,0.509,16.576,5.994,20.137,11.423c2.51,3.837,7.652,4.91,11.479,2.4c1.084-0.719,1.946-1.637,2.566-2.687
		c40.684-2.632,78.802,19.153,89.97,26.208C495.136,135.121,495.413,135.641,495.413,136.249z M432.38,130.875
		c0-4.578-3.716-8.294-8.294-8.294h-8.847c-4.578,0-8.294,3.716-8.294,8.294c0,4.589,3.716,8.294,8.294,8.294h8.847
		C428.665,139.169,432.38,135.464,432.38,130.875z"/>
                    </g>
                    <g>
                        <path  d="M512,136.249v30.012c0,19.695-26.352,29.681-78.326,29.681c-4.025,0-7.697-0.133-11.058-0.365
		c-15.327,28.077-33.075,51.521-52.815,69.701c-20.248,18.644-36.647,26.286-44.609,29.172c-5.452,1.979-9.322,6.923-10.107,12.883
		c-2.444,18.622-6.558,52.638-6.558,71.249c0,21.873,12.463,27.679,12.584,27.734c3.649,1.526,5.728,5.408,4.943,9.289
		c-0.774,3.87-4.169,6.657-8.128,6.657h-19.739c-13.104,0-24.516-8.836-27.734-21.508c-5.54-21.763-12.363-51.941-13.801-73.405
		l-2.3-5.739c-13.602,4.744-31.295,6.215-49.796,6.215c-16.278,0-33.164-1.139-48.435-2.156c-17.14-1.15-33.33-2.234-40.02-0.885
		c-0.354,0.077-0.708,0.122-1.062,0.144c-19.927,18.434-43.581,30.366-57.249,37.244c-2.178,1.106-4.545,2.289-6.226,3.196
		c-2.908,30.864,2.344,44.421,5.043,49.154h4.766c4.578,0,8.294,3.716,8.294,8.294c0,4.589-3.716,8.294-8.294,8.294H43.68
		c-1.637,0-3.24-0.475-4.6-1.393l-26.54-17.693c-1.968-1.305-3.273-3.384-3.605-5.717l-8.847-61.927
		c-0.387-2.72,0.586-5.441,2.599-7.287c9.223-8.482,23.853-26.33,23.853-38.129c0-6.237-4.335-13.204-9.344-21.276
		C9.532,265.355,0,250.017,0,228.188c0-46.666,44.587-114.454,114.454-114.454c34.347,0,73.858,8.316,108.714,15.636
		c26.595,5.596,51.709,10.87,68.064,10.904c7.376-1.039,45.328-24.693,62.844-39.843c-0.177-1.681-0.254-3.406-0.21-5.153
		c0.177-6.79,4.058-11.91,10.373-13.701c7.907-2.245,17.505,1.537,22.515,3.992c2.333,1.15,6.812,3.55,11.014,7
		c46.556-5.374,89.34,17.848,105.729,28.221C508.815,124.152,512,129.935,512,136.249z M495.413,166.262v-30.012
		c0-0.608-0.276-1.128-0.785-1.449c-11.169-7.055-49.287-28.84-89.97-26.208c-0.619,1.051-1.482,1.968-2.566,2.687
		c-3.826,2.51-8.968,1.438-11.479-2.4c-3.561-5.43-14.94-10.915-20.137-11.423c0.509,10.273,7.144,19.562,7.21,19.662
		c2.72,3.693,1.924,8.88-1.769,11.6c-1.482,1.084-3.196,1.603-4.899,1.603c-2.554,0-5.065-1.161-6.69-3.373
		c-0.221-0.299-2.754-3.793-5.275-9.201c-8.106,6.359-18.888,13.978-29.681,20.878c-28.508,18.235-35.398,18.235-37.985,18.235
		c-18.08,0-42.895-5.208-71.625-11.257c-34.071-7.155-72.686-15.283-105.308-15.283c-26.617,0-51.863,12.175-71.083,34.27
		c-16.521,18.998-26.783,43.371-26.783,63.596c0,17.096,7.464,29.117,14.697,40.739c6.093,9.809,11.843,19.065,11.843,30.034
		c0,19.087-18.224,39.423-26.02,47.263l7.719,54.053l11.899,7.94c-2.477-10.572-3.738-25.987-1.294-48.225
		c0.597-5.43,4.202-7.243,14.896-12.629c18.644-9.388,57.426-28.929,74.489-61.13c2.145-4.047,7.166-5.584,11.213-3.439
		c4.047,2.145,5.596,7.166,3.45,11.213c-2.554,4.81-5.496,9.344-8.714,13.635c7.542,0.199,16.477,0.796,26.463,1.471
		c29.26,1.968,68.584,4.589,90.955-2.93l-8.725-21.829c-1.703-4.257,0.365-9.079,4.611-10.782c4.257-1.703,9.09,0.365,10.782,4.622
		l17.693,44.233c0.332,0.829,0.531,1.714,0.586,2.61c1.216,21.265,8.913,54.009,13.403,71.625c1.349,5.308,6.137,9.013,11.655,9.013
		h0.144c-3.616-6.458-6.392-15.272-6.392-27.093c0-18.578,3.638-50.172,6.701-73.405c1.592-12.131,9.599-22.216,20.889-26.308
		c7.951-2.897,48.203-20.259,85.636-86.045c-9.588-2.621-15.648-6.646-21.508-11.125c-3.638-2.776-4.324-7.984-1.548-11.622
		c2.787-3.638,7.995-4.335,11.633-1.548c8.548,6.535,15.946,10.826,39.932,10.826C480.318,179.355,495.413,170.541,495.413,166.262z
		"/>
                        <path  d="M424.086,122.581c4.578,0,8.294,3.716,8.294,8.294c0,4.589-3.716,8.294-8.294,8.294h-8.847
		c-4.578,0-8.294-3.705-8.294-8.294c0-4.578,3.716-8.294,8.294-8.294H424.086z"/>
                    </g>
                </SvgIcon>
                {userDataState.userName ? (<EnterPasswordModal userData={userDataState}/>) : (
                    <EnterUserNameModal userData={userDataState}/>)}
                <Typography sx={{ marginTop: "30px" }}>Don`t have an account? <Link href="#">Sign Up</Link></Typography>
            </Box>
        </Modal>
    );
}