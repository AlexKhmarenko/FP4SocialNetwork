import React, { useEffect, useContext, useState, useCallback } from "react";
import { PostsDisplaying } from "../components/Posts/PostsDisplaying";
import { useSelector } from "react-redux";
import PropTypes from "prop-types";
import { useParams } from "react-router-dom";
import { apiUrl } from "../apiConfig";
import { SvgIcon } from "@mui/material";

export function PostPage() {
    const [post, setPost] = useState([]);
    const [isLoading, setIsLoading] = useState(false);
    const [notFoundPost, setNotFoundPost] = useState(false);
    const userId = useSelector(state => state.userData.userData.userId);
    const { postId } = useParams();

    useEffect(() => {
        async function getData() {
            try {
                setIsLoading(true);
                let postData = await fetch(`${apiUrl}/api/post/${postId}?userId=${userId}`);
                let postInform = await postData.json();
                if (postData.ok) {
                    setPost([postInform]);
                } else {
                    setNotFoundPost(true);
                }
            } catch (e) {
                console.warn(e, "can't load the post!")
            } finally {
                setIsLoading(false);
            }
        }

        getData();
    }, []);

    return (
        <div style={{ display: "flex", justifyContent: "center", alignItems: "center", width: "50vw" }} data-testid={"notifications_post_wrapper_view"}>
            {notFoundPost ?
                <div style={{
                    display: "flex",
                    flexDirection: "column",
                    justifyContent: "center",
                    alignItems: "center"
                }}>
                    <div style={{
                        fontFamily: "'Lato', sans-serif",
                        fontSize: "32px", textAlign: "center", margin: "3em 0"
                    }}>Sorry, post was deleted.
                    </div>
                    <SvgIcon xmlns="http://www.w3.org/2000/svg" style={{ width: "200px", height: "200px" }}
                             data-name="Layer 1" viewBox="0 0 847.92 408.23749999999995" x="0px" y="0px">
                        <path
                            d="m833.68,163.2c-20.72-17.86-57.7-38.95-93.47-59.34-11.66-6.65-22.68-12.93-32.89-18.97-3.76-2.22-76.86-44.7-159.9-42.75,2.23-5.61,2.23-10.99-.02-16.02-7.09-15.88-34.74-23.16-43.83-25.55-3.92-1.03-6.88-.7-9.06,1.01-4.25,3.35-4.11,10.49-3.87,22.31.06,2.91.12,6.09.12,9.52,0,8.49,1.23,13.83,3.78,16.56-1.75.5-3.5,1.03-5.24,1.58-3.05-2.73-6.61-5.55-10.78-8.36-4.04-2.73-7.42-5.18-10.42-7.34-12.82-9.26-18.66-13.49-30.44-5.25-8.47,5.93-21.16,33.62-16.96,57.48.19,1.07.44,2.18.71,3.29-.22.11-.44.21-.63.39-8.18,7.64-15.92,16.3-23.1,25.71-6.11-8.29-25.11-31.44-55.41-44.88-70-31.07-114.3-30.93-164.97-27.51-48.12,3.24-117.79,21.75-121.93,44.52-4.69,25.78,7.3,44.39,34.68,53.8,21.71,7.46,50.62,12.49,87.22,18.86,14.3,2.49,30.51,5.31,47.69,8.53,32.64,6.12,57.2,7.55,73.03,7.55,9.29,0,15.56-.49,18.7-.82,4.23,11.31,32.18,56.21,209.33,56.2,3.2,0,6.48-.01,9.77-.04,95.82-.87,156.42,1.87,196.55,3.68,18.42.83,32.62,1.47,44,1.47,17.76,0,28.65-1.56,37.98-6.39,19.19-9.92,31.66-24.46,33.35-38.87,1.28-10.86-3.58-21.36-14.03-30.38ZM503.76,47c-.87-1.17-2.53-1.41-3.71-.57,0,0-.77.51-1.24.27-.28-.14-2.71-1.73-2.71-13.27,0-3.46-.07-6.68-.12-9.63-.18-8.75-.33-16.31,1.84-18.02.76-.6,2.24-.62,4.4-.05,22.74,5.98,36.31,13.58,40.32,22.57,1.93,4.31,1.59,8.89-1.02,14,0,.02,0,.04-.02.05-12.38.61-24.94,2.25-37.5,5.17-.08-.18-.13-.37-.25-.53Zm-277.77,118.56c-17.22-3.23-33.44-6.05-47.76-8.54-36.37-6.32-65.1-11.32-86.4-18.65-24.9-8.56-35.39-24.65-31.18-47.81,2.69-14.8,55.5-36.01,117.05-40.16,49.91-3.36,93.56-3.51,162.45,27.06,29.61,13.14,48.44,36.61,53.72,43.8l-76.18,50.78c-4.55.55-36.78,3.8-91.7-6.5Zm616.44,27.4c-1.47,12.47-13.16,25.79-30.51,34.76-14.05,7.27-33.16,6.4-79.3,4.32-40.18-1.81-100.9-4.55-196.83-3.68-182.61,1.66-210.33-43.66-214-52.64l77.69-51.79.28-.38c7.22-9.79,15.11-18.73,23.42-26.65,3.96,10.15,12.49,20.67,30.47,25.49.23.06.46.09.69.09,1.18,0,2.25-.79,2.57-1.97.38-1.42-.46-2.88-1.88-3.26-16.56-4.44-26.34-14.56-29.07-30.08-4.02-22.8,8.44-47.76,14.77-52.19,8.66-6.07,11.66-3.91,24.27,5.2,3.02,2.18,6.44,4.66,10.55,7.43,20.3,13.73,25.41,27.52,25.47,27.69.49,1.38,2.01,2.12,3.39,1.63,1.39-.48,2.12-2,1.64-3.39-.15-.44-2.93-8.06-12.32-17.73,80.67-24.13,172.86,11.2,210.88,33.66,10.25,6.06,21.28,12.35,32.96,19.01,35.55,20.27,72.3,41.23,92.63,58.75,9.19,7.92,13.3,16.57,12.22,25.72Z"/>
                        <path
                            d="m612.8,120.57c-.54-.53-.92-.85-.98-.9l-.45-.38-1.37-.36c-11.54-6.36-21.58-8.25-29.88-7.93l-1.19-.32-.9.45s0,0,0,0c-15.66,1.35-24.54,10.48-24.71,10.65-1,1.07-.95,2.75.12,3.76,1.07,1.01,2.75.96,3.76-.11.42-.44,4.66-4.73,12.28-7.35-2.34,3.03-3.84,6.91-2.22,11.46,3.47,9.73,15.51,16.27,24.58,16.27.8,0,1.71.01,2.71.03,1.27.02,2.53.03,3.77.03,8.48,0,16.03-.64,19.8-4.35,1.46-1.44,2.24-3.28,2.24-5.31,0-1.55-.24-3.02-.62-4.41,2.44,1.91,4.94,4.02,7.5,6.37.51.47,1.15.7,1.8.7.72,0,1.44-.29,1.96-.86,1-1.08.92-2.77-.16-3.76-6.34-5.82-12.35-10.27-18.03-13.69Z"/>
                        <path
                            d="m809.2,194.33c-1.86-.82-11.39-4.76-16.58-.98-1.6,1.16-3.4,3.51-2.92,8.11.14,1.37,1.3,2.38,2.64,2.38.09,0,.19,0,.28-.01,1.46-.15,2.53-1.47,2.37-2.93-.02-.17-.01-.3-.02-.46l8.72-2.44c1.22.36,2.39.77,3.34,1.2,1.35.6,2.92,0,3.51-1.35.6-1.34-.01-2.92-1.35-3.51Z"/>
                        <path
                            d="m26.41,151.04c.19.04.38.06.56.06,1.23,0,2.33-.85,2.6-2.1.31-1.44-.6-2.86-2.04-3.17-18.11-3.91-16.38-18.14-12.06-41.02,1.73-9.21,3.53-18.72,3.27-27.7-.05-1.47-1.39-2.62-2.74-2.58-1.47.04-2.63,1.27-2.59,2.74.25,8.4-1.49,17.63-3.18,26.56-3.89,20.65-7.92,42.01,16.18,47.22Z"/>
                        <path
                            d="m131.27,217.91c-5.78,1.11-12.59-8.18-19.16-17.14-6.17-8.43-12.56-17.14-20.19-21.16-8.68-4.57-14.31-4.29-20.83-3.96-6.24.31-13.33.67-25.77-2.95-1.43-.41-2.89.4-3.3,1.82-.41,1.41.4,2.89,1.81,3.3,13.3,3.86,21.19,3.47,27.53,3.15,6.2-.31,10.69-.53,18.08,3.36,6.54,3.44,12.55,11.65,18.37,19.59,7.3,9.97,14.23,19.42,22.43,19.42.66,0,1.33-.06,2.01-.19,1.45-.27,2.4-1.66,2.12-3.11-.27-1.44-1.63-2.4-3.11-2.13Z"/>
                        <path
                            d="m124.56,266.65c-8.77,1.66-18.11-11.05-27.96-24.49-8.6-11.73-17.48-23.86-27.97-29.38-11.93-6.28-19.69-5.89-28.69-5.44-8.86.44-18.89.94-36.54-4.18-1.41-.41-2.89.4-3.3,1.82-.41,1.41.4,2.89,1.82,3.3,18.5,5.37,29.47,4.82,38.29,4.38,8.89-.45,15.3-.77,25.94,4.83,9.4,4.95,17.92,16.57,26.15,27.82,10.09,13.76,19.65,26.82,30.65,26.82.86,0,1.72-.08,2.59-.24,1.45-.27,2.4-1.66,2.12-3.11-.27-1.44-1.65-2.4-3.11-2.13Z"/>
                        <path
                            d="m485.82,294.64c6.66,0,13.56-.07,20.7-.2,94.63-1.79,129.5-6.93,146.26-9.41,14.59-2.16,15.48-2.29,31.35,3.38,1.38.49,2.91-.23,3.4-1.61.5-1.38-.23-2.91-1.61-3.4-17.12-6.12-18.72-5.88-33.92-3.64-16.65,2.46-51.3,7.57-145.58,9.35-172.6,3.26-201.04-35.13-201.43-35.69-.8-1.22-2.44-1.57-3.67-.77-1.24.8-1.59,2.45-.79,3.68,1.05,1.63,26.63,38.3,185.29,38.3Z"/>
                        <path
                            d="m695.13,314.15c-20.37-7.28-22.27-7-40.37-4.33-19.95,2.94-61.47,9.07-174.43,11.2-207.73,3.9-241.39-42.51-241.71-42.98-.81-1.22-2.44-1.56-3.67-.77-1.24.8-1.59,2.45-.79,3.68,1.25,1.94,31.72,45.64,221.42,45.64,7.99,0,16.28-.08,24.84-.24,113.31-2.14,155.05-8.3,175.11-11.26,17.61-2.6,18.67-2.76,37.8,4.08,1.38.49,2.91-.23,3.4-1.61.5-1.38-.23-2.91-1.61-3.4Z"/>
                        <path
                            d="m602.19,101.34c-.06,0-.12,0-.19,0-1.47.02-2.64,1.23-2.62,2.7.02,1.46,1.21,2.62,2.66,2.62.06,0,.12,0,.18,0,9.74,0,34.4,15.09,38.99,23.91.48.91,1.4,1.43,2.36,1.43.41,0,.83-.1,1.23-.3,1.31-.68,1.81-2.29,1.13-3.59-5.54-10.63-31.97-26.77-43.74-26.77Z"/>
                    </SvgIcon>
                </div> : <PostsDisplaying userPosts={post} isLoading={isLoading}/>
            }
        </div>
    );
}

PostPage.propTypes = {
    props: PropTypes.object,
    match: PropTypes.object,
};