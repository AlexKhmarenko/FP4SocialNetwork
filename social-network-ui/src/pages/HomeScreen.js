import React, { useState, useCallback } from "react";
import { useDispatch, useSelector } from "react-redux";
import { Button, Box } from "@mui/material";
import { CloudUploadOutlined } from "@mui/icons-material";
import { Formik, Form, Field } from "formik";
import * as Yup from "yup";

import { setUserPostToPostsArr, sendPost } from "../store/actions";
import { SidebarLogOutButton } from "../components/NavigationComponents/NavigationStyles";
import { CapybaraSvgPhoto } from "../components/SvgIcons/CapybaraSvgPhoto";
import {
    NameOfUser,
    SvgWrapper,
    WrittenPostWrapper,
    HomeScreenWrapper,
    PostWrapper,
    SendingPostButtonsContainer
} from "./pagesStyles/HomeScreenStyles";
import { PostsDisplaying } from "../components/Posts/PostsDisplaying";
import { SendPostInput } from "../components/Posts/SendPostInput";
import { CharactersTextWrapper, PostImgWrapper, PostsWrapper, SendPostField } from "../components/Posts/PostStyles";

export function HomeScreen() {
    const [postText, setPostText] = useState("");
    const [postImage, setPostImage] = useState(null);
    const userId = useSelector(state => state.userData.userData.userId);
    const dispatch = useDispatch();

    const handlePostImageChange = useCallback((event) => {
        const file = event.target.files[0];
        setPostImage(file);
    }, []);

    const handlePostSubmit = useCallback(async (values, setSubmitting) => {
        if (values.postText.trim() !== "" || postImage) {
            setSubmitting(true);

            let photoFileByteArray = [];
            if (postImage) {
                const reader = new FileReader();

                reader.onloadend = async () => {
                    const imageArrayBuffer = new Uint8Array(reader.result);
                    photoFileByteArray = Array.from(imageArrayBuffer);

                    const postObject = {
                        writtenText: values.postText,
                        photoFileByteArray: photoFileByteArray,
                        userId: userId
                    };

                    await dispatch(sendPost(postObject, setSubmitting));
                };

                reader.readAsArrayBuffer(postImage);
            } else {
                const postObject = {
                    writtenText: values.postText,
                    photoFileByteArray: [],
                    userId: userId
                };
                await dispatch(sendPost(postObject, setSubmitting));
            }

            setPostImage(null);
            setPostText("");
        }
    }, [postImage, postText, userId]);

    return (
        <Formik
            initialValues={{ postText: "" }}
            validationSchema={
                Yup.object({
                    postText: Yup.string().max(280, "Must be 280 characters or less"),
                })}
            onSubmit={(values, { resetForm, setSubmitting }) => {
                setSubmitting(true);
                handlePostSubmit(values, setSubmitting);
                resetForm();
            }}
        >
            {({ values, errors, touched, isSubmitting }) => (
                <Form>
                    <div style={HomeScreenWrapper}>
                        <div style={PostWrapper}>
                            <div style={SvgWrapper}>
                                <CapybaraSvgPhoto/>
                            </div>
                            <div style={WrittenPostWrapper}>
                                <h2 style={NameOfUser}>Capybara name</h2>
                                <Field
                                    values={postText}
                                    component={SendPostInput}
                                    name="postText"
                                    className={errors.postText && touched.postText ? "error" : ""}
                                    style={SendPostField}
                                    id="postText"
                                    placeholder="What's happening?"
                                />
                                <div style={CharactersTextWrapper}>
                                    {
                                        280 - values.postText.length >= 0 ?
                                            (280 - values.postText.length + " characters") : ("maximum number of characters 280")
                                    }
                                </div>
                                <Box sx={PostImgWrapper}>
                                    {postImage && (
                                        <img
                                            src={URL.createObjectURL(postImage)}
                                            alt="Post Image"
                                            style={{ maxWidth: "100%", height: "auto" }}
                                        />
                                    )}
                                    <input
                                        type="file"
                                        accept="image/*"
                                        id="post-image-input"
                                        onChange={handlePostImageChange}
                                        style={{ display: "none" }}
                                    />
                                    <label htmlFor="post-image-input">
                                        <div style={SendingPostButtonsContainer}>
                                            <Button
                                                component="span"
                                                variant="contained"
                                                color="primary"
                                                sx={SidebarLogOutButton}
                                                startIcon={<CloudUploadOutlined/>}
                                                disabled={!!postImage}
                                            >image</Button>
                                            <Button
                                                type="submit"
                                                variant="contained"
                                                sx={SidebarLogOutButton}
                                                fullWidth={true}
                                                disabled={isSubmitting}
                                            >
                                                {isSubmitting ? "Posting..." : "Post"}
                                            </Button>
                                        </div>
                                    </label>
                                </Box>
                            </div>
                        </div>
                    </div>
                    <div style={PostsWrapper}>
                        <PostsDisplaying/>
                    </div>
                </Form>
            )}
        </Formik>
    );
}



