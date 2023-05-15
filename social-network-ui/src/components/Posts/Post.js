import React, { useState } from "react";
import PropTypes from "prop-types";

import { Card, CardContent, Avatar, Typography, CardActions, IconButton } from "@mui/material";
import { FavoriteBorder, ChatBubbleOutline, Repeat } from "@mui/icons-material";

export const Post = ({ userName, name, photo, postComments, postLikes, text }) => {
    const [showMore, setShowMore] = useState(false);

    const handleShowMore = () => {
        setShowMore(!showMore);
    };

    const renderText = () => {
        if (showMore) {
            return text;
        } else {
            const words = text.split(" ");
            const truncatedWords = words.slice(0, 25);
            return truncatedWords.join(" ") + "...";
        }
    };

    return (
        <Card sx={{
            width: "100%",
            maxWidth: "100%",
            borderRadius: 0,
            mb: 1,
            margin: "0",
            padding: "0",
            boxShadow: "none",
            borderBottom: "1px solid rgba(0, 0, 0, 0.1)"
        }}>
            <CardContent sx={{ display: "flex", paddingBottom: 0 }}>
                <Avatar alt={"asy"} src="#"/>
                <div style={{ marginLeft: 16, flex: 1 }}>
                    <Typography variant="subtitle1" component="div">
                        {name} <span style={{ color: "#5b7083" }}>@{userName}</span> · 24.11.2000
                    </Typography>
                    <Typography variant="body1" component="div" mt={1} sx={{
                        maxHeight: showMore ? "none" : "90px",
                        overflowY: "hidden",
                        padding: "0, 20px",
                        wordWrap: "break-word", maxWidth: "500px",
                        marginBottom: "12px"
                    }}>{renderText()}
                    </Typography>
                    <a style={{
                        fontFamily: "'Lato', sans-serif",
                        fontSize: "15px", fontWeight: "700", textDecoration: "none", color: "blue"
                    }} onClick={handleShowMore} href="#">{showMore ? "hight text" : "see more"}</a>
                </div>
            </CardContent>
            <CardActions sx={{ padding: "20px 20px" }}>
                <IconButton>
                    <ChatBubbleOutline fontSize="small"/>
                </IconButton>
                <IconButton>
                    <Repeat fontSize="small"/>
                </IconButton>
                <IconButton>
                    <FavoriteBorder fontSize="small"/>
                </IconButton>
            </CardActions>
        </Card>
    );
};

Post.propTypes = {
    userName: PropTypes.string,
    name: PropTypes.string,
    photo: PropTypes.string,
    postComments: PropTypes.array,
    postLikes: PropTypes.array,
    text: PropTypes.string,
};


