import React, { useState } from "react";
import { useSelector } from "react-redux";
import { Button, TextField, Box } from "@mui/material";
import { CloudUploadOutlined } from "@mui/icons-material";
import { Formik, Form, Field, ErrorMessage } from "formik";
import * as Yup from "yup";

import { SidebarLogOutButton } from "../components/NavigationComponents/NavigationStyles";
import { CapybaraSvgPhoto } from "../components/SvgIcons/CapybaraSvgPhoto";
import {
    NameOfUser,
    SvgWrapper,
    WrittenPostWrapper,
    HomeScreenWrapper,
    PostWrapper
} from "./pagesStyles/HomeScreenStyles";
import { PostsDisplaying } from "../components/Posts/PostsDisplaying";
import PropTypes from "prop-types";

export function HomeScreen() {
    const [postText, setPostText] = useState("");
    const [postImage, setPostImage] = useState(null);
    const userId = useSelector(state => state.userData.userData.userId);

    const handlePostImageChange = (event) => {
        const file = event.target.files[0];
        setPostImage(file);
    };

    const validationSchema = Yup.object({
        postText: Yup.string().max(280, "Must be 280 characters or less"),
    });

    const handlePostSubmit = (values, setSubmitting) => {
        console.log(values);
        if (postImage) {
            const reader = new FileReader();
            const formData = new FormData();
            formData.append("writtenText", postText);
            formData.append("userId", userId);

            reader.onloadend = () => {
                const imageArrayBuffer = new Uint8Array(reader.result);
                const photoFileByteArray = Array.from(imageArrayBuffer);

                const postObject = {
                    writtenText: values.postText,
                    photoFileByteArray: photoFileByteArray,
                    userId: userId
                };

                console.log(postObject);
                setSubmitting(true);
                fetch("http://localhost:8080/posts", {
                    method: "POST",
                    body: JSON.stringify(postObject),
                    headers: {
                        "Content-Type": "application/json"
                    }
                }).then((response) => {
                    console.log("vse gud");
                    setPostText("");
                    setPostImage(null);
                }).catch((error) => {
                    console.log("Pizda");
                    console.error(error);
                }).finally(() => {
                    setSubmitting(false);
                });
            };
            reader.readAsArrayBuffer(postImage);
        }
    };

    const CustomTextField = ({ field, form, ...props }) => {
        return (
            <TextField
                inputProps={{
                    maxLength: 280,
                }}
                {...field}
                {...props}
                variant="standard"
                multiline
                helperText={form.errors[field.name] && form.touched[field.name] ? form.errors[field.name] : ""}
            />
        );
    };

    return (
        <Formik
            initialValues={{ postText: "" }}
            validationSchema={validationSchema}
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
                                    component={CustomTextField}
                                    name="postText"
                                    className={errors.postText && touched.postText ? "error" : ""}
                                    style={{
                                        fontSize: "1.3rem",
                                        fontFamily: "'Lato', sans-serif",
                                        width: "400px",
                                        maxWidth: "600px",
                                        marginTop: "20px",
                                    }}
                                    id="postText"
                                    placeholder="What's happening?"
                                />
                                <div style={{
                                    marginTop: "10px", fontSize: "1rem",
                                    fontFamily: "'Lato', sans-serif",
                                }}>
                                    {
                                        280 - values.postText.length >= 0 ?
                                            (280 - values.postText.length + " characters") : ("maximum number of characters 280")
                                    }
                                </div>
                                <Box sx={{
                                    display: "flex",
                                    flexDirection: "column",
                                    alignItems: "center",
                                    marginTop: "10px",
                                }}>
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
                                        <div
                                            style={{
                                                display: "flex",
                                                justifyContent: "space-between",
                                                maxWidth: "400px",
                                                width: "400px",
                                            }}
                                        >
                                            <Button
                                                component="span"
                                                variant="contained"
                                                color="primary"
                                                sx={{
                                                    ...SidebarLogOutButton,
                                                    color: "white",
                                                    marginRight: "10px",
                                                    alignSelf: "end",
                                                    marginTop: "30px",
                                                    fontFamily: "'Lato', sans-serif",
                                                    fontSize: "14px",
                                                    cursor: "pointer",
                                                }}
                                                startIcon={<CloudUploadOutlined/>}
                                                disabled={!!postImage}
                                            >image</Button>
                                            <Button
                                                type="submit"
                                                variant="contained"
                                                sx={{
                                                    ...SidebarLogOutButton,
                                                    alignSelf: "end",
                                                    marginTop: "30px",
                                                    fontFamily: "'Lato', sans-serif",
                                                }}
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
                    <div style={{
                        display: "flex",
                        flexDirection: "column",
                        alignItems: "center",
                        maxWidth: "100%",
                    }}>
                        <PostsDisplaying/>
                    </div>
                </Form>
            )}
        </Formik>

    );
}

HomeScreen.propTypes = {
    field: PropTypes.string,
    form: PropTypes.string,
    type: PropTypes.string,
};


