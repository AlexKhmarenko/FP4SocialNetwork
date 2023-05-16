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
import { useSelector } from "react-redux";

export function HomeScreen() {
    const [postText, setPostText] = useState("");
    const [postImage, setPostImage] = useState(null);
    const userId = useSelector(state => state.userData.userId);

    const handlePostTextChange = (event) => {
        setPostText(event.target.value);
        console.log(postText)
    };

    const handlePostImageChange = (event) => {
        const file = event.target.files[0];
        console.log(file)
        setPostImage(file);
    };


    const handlePostSubmit = () => {
        const formData = new FormData();
        formData.append("writtenText", postText);
        formData.append("userId", userId);

        if (postImage) {
            const reader = new FileReader();

            reader.onloadend = () => {
                const imageArrayBuffer = new Uint8Array(reader.result);
                const imageBlob = new Blob([imageArrayBuffer], { type: "application/octet-stream" });
                formData.append("photoFile", imageBlob, postImage.name);
                console.log(FormData)
                console.log(formData.get("writtenText"));
                console.log(formData.get("photoFile"));

                fetch("http://localhost:8080/posts", {
                    method: "POST",
                    body: formData,
                })
                    .then((response) => response.json())
                    .then((data) => {
                        // Обработка ответа от сервера
                        console.log(data);
                        // Очистить поля после отправки
                        setPostText("");
                        setPostImage(null);
                    })
                    .catch((error) => {
                        // Обработка ошибки
                        console.error(error);
                    });
            };

            reader.readAsArrayBuffer(postImage);
        }
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