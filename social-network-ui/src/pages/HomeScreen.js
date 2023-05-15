import React, { useState } from "react";
import { Button, TextField, Box } from "@mui/material";
import { styled } from "@mui/system";
import { SidebarLogOutButton } from "../components/NavigationComponents/NavigationStyles";
import { CapybaraSvgPhoto } from "../components/SvgIcons/CapybaraSvgPhoto";
import { CloudUploadOutlined } from "@mui/icons-material";
import {
    NameOfUser,
    PostWrittenInput,
    SvgWrapper,
    WrittenPostWrapper,
    HomeScreenWrapper,
    PostWrapper
} from "./pagesStyles/HomeScreenStyles";
import { PostsDisplaying } from "../components/Posts/PostsDisplaying";

export function HomeScreen() {
    const [postText, setPostText] = useState("");
    const [postImage, setPostImage] = useState(null);

    const handlePostTextChange = (event) => {
        setPostText(event.target.value);
    };

    const handlePostImageChange = (event) => {
        const file = event.target.files[0];
        setPostImage(file);
    };

    const handlePostSubmit = () => {
        // Ваш код для отправки поста, включая текст и изображение
        console.log("Text:", postText);
        console.log("Image:", postImage);
        // Очистить поля после отправки
        setPostText("");
        setPostImage(null);
    };

    return (
        <>
            <div style={HomeScreenWrapper}>
                <div style={PostWrapper}>
                    <div style={SvgWrapper}>
                        <CapybaraSvgPhoto />
                    </div>
                    <div style={WrittenPostWrapper}>
                        <h2 style={NameOfUser}>Capybara name</h2>
                        <TextField
                            multiline
                            value={postText}
                            onChange={handlePostTextChange}
                            InputLabelProps={{
                                sx: {
                                    fontSize: "1.3rem",
                                    fontFamily: "'Lato', sans-serif",
                                },
                            }}
                            sx={{ width: "400px", maxWidth: "600px" }}
                            id="standard-basic"
                            label="What's happening?"
                            variant="standard"
                        />
                        <Box sx={{ display: "flex", flexDirection: "column", alignItems: "center", marginTop: "10px" }}>
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
                                <div style={{display:"flex", justifyContent:"space-between", maxWidth:"400px",width:"400px"}}>
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
                                    startIcon={<CloudUploadOutlined />}
                                >
                                   image
                                </Button>
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
                                    onClick={handlePostSubmit}
                                >
                                    Post
                                </Button>
                                </div>
                            </label>


                        </Box>
                    </div>
                </div>


            </div>
            <div style={{ display: "flex", flexDirection: "column", alignItems: "center", maxWidth: "100%" }}>
                <PostsDisplaying />
            </div>
        </>
    );
}