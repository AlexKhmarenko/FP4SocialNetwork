import React, { useState } from "react";
import PropTypes from "prop-types";
import { formatDistanceToNow, differenceInDays, format } from "date-fns";

import { Card, CardContent, Avatar, Typography, CardActions, IconButton, Box, Button, TextField } from "@mui/material";
import { FavoriteBorder, ChatBubbleOutline, Repeat } from "@mui/icons-material";
import { PostCard, PostText, ShowMoreLinkStyles } from "./PostStyles";
import { useSelector } from "react-redux";
import { StyledBlackButton } from "../LoginModal/loginModalStyles";

export const Post = ({ userName, name, photo, postComments, postLikes, text, dataTime, postId }) => {
    const userId = useSelector(state => state.userData.userData.userId);
    const [showMore, setShowMore] = useState(false);
    const [isCommentOpen, setIsCommentOpen] = useState(false);
    const [comments, setComments] = useState([]);
    const [newComment, setNewComment] = useState("");

    const handleCommentToggle = () => {
        setIsCommentOpen(!isCommentOpen);
    };

    const handleAddComment = () => {
        setComments([...comments, newComment]);
        setNewComment("");
    };

    const handleCommentChange = (e) => {
        setNewComment(e.target.value);
    };

    async function addLikeHandle() {
        let a = await fetch("http://localhost:8080/likes", {
            method: "POST",
            body: JSON.stringify({
                postId: postId,
                userId: userId,
            }),
            headers: {
                "Content-Type": "application/json"
            }
        });
        let b = await a.json();
        console.log(" b ", b);
    }

    const handleShowMore = () => {
        setShowMore(!showMore);
    };

    function postDate() {
        const date = new Date(dataTime);
        const diffDays = differenceInDays(new Date(), date);

        if (diffDays < 1) {
            return formatDistanceToNow(date, { addSuffix: true });
        } else if (diffDays < 365) {
            return format(date, "MMM d");
        } else {
            return format(date, "MMM d, yyyy");
        }
    }

    const renderText = () => {
        const words = text.split(" ");
        if (showMore || words.length <= 10) {
            return text;
        } else {
            const truncatedWords = words.slice(0, 10);
            return truncatedWords.join(" ") + "...";
        }
    };

    return (
        <Card sx={PostCard}>
            <CardContent sx={{ display: "flex", paddingBottom: 0 }}>
                <Avatar alt={"asy"} src="#"/>
                <div style={{ marginLeft: 16, flex: 1 }}>
                    <Typography variant="subtitle1" component="div">
                        {name} <span style={{ color: "#5b7083" }}>@{userName}</span> · {postDate()}
                    </Typography>
                    <Typography variant="body1" component="div" mt={1}
                                sx={{ ...PostText, maxHeight: showMore ? "none" : "90px", }}>{renderText()}
                    </Typography>
                    {text.split(" ").length > 10 && (
                        <a style={ShowMoreLinkStyles} onClick={handleShowMore} href="#">
                            {showMore ? "hight text" : "see more"}
                        </a>
                    )}
                </div>
            </CardContent>
            {
                photo ? (<div style={{
                    maxWidth: "600px",
                    width: "600px",
                    margin: "0,auto",
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center"
                }}>
                    <img src={`data:image/png;base64,${photo}`} style={{ width: "450px", margin: "0,auto" }} alt=""/>
                </div>) : null
            }

            <CardActions sx={{ padding: "20px 20px" }}>
                <IconButton onClick={handleCommentToggle}>
                    <ChatBubbleOutline fontSize="small"/>
                </IconButton>
                <IconButton>
                    <Repeat fontSize="small"/>
                </IconButton>
                <IconButton onClick={addLikeHandle}>
                    <FavoriteBorder fontSize="small"/>
                </IconButton>
            </CardActions>


            {isCommentOpen && (
                <Box style={{ padding: "10px 20px", borderTop: "1px solid #ddd", overflow: "scroll", height: "50xp" }}>
                    <Typography variant="h6" sx={{ marginBottom: "10px" }}>Comments:</Typography>
                    {comments.map((comment, index) => (
                        <Box key={index} style={{ marginTop: "10px", padding: "5px 0", borderTop: "1px solid #eee" }}>
                            <Typography>{comment}</Typography>
                        </Box>
                    ))}
                    <Box style={{
                        padding: "5px 0",
                        borderTop: "1px solid #eee",
                        display: "flex",
                        alignItems: "center",
                        height:"60px"
                    }}>
                        <div style={{ width: "40px", height: "40px", backgroundColor: "blue", borderRadius: "30px" }}/>
                        <Typography sx={{ marginLeft: "30px" }}>fk;lsdjf;lgm;slmg;rslgm</Typography>
                    </Box>
                    <Box style={{
                        padding: "5px 0",
                        borderTop: "1px solid #eee",
                        display: "flex",
                        alignItems: "center",
                        height:"60px"
                    }}>
                        <div style={{ width: "40px", height: "40px", backgroundColor: "blue", borderRadius: "30px" }}/>
                        <Typography sx={{ marginLeft: "30px" }}>fk;lsdjf;lgm;slmg;rslgm</Typography>
                    </Box>
                    <Box style={{
                        padding: "5px 0",
                        borderTop: "1px solid #eee",
                        display: "flex",
                        alignItems: "center",
                        height:"60px"
                    }}>
                        <div style={{ width: "40px", height: "40px", backgroundColor: "blue", borderRadius: "30px" }}/>
                        <Typography sx={{ marginLeft: "30px" }}>fk;lsdjf;lgm;slmg;rslgm</Typography>
                    </Box>
                    <TextField
                        value={newComment}
                        onChange={handleCommentChange}
                        sx={{ "& .MuiOutlinedInput-root": { borderRadius: "40px" }, marginTop:"10px" }}
                        fullWidth
                        margin="normal"
                        variant="outlined"
                        label="Please enter your comment"
                        multiline
                    />
                    <Button onClick={handleAddComment} color="primary" variant="contained"
                            style={{ ...StyledBlackButton, maxWidth: "140px", marginTop:"0", fontSize: "12px"}}>Add comment</Button>
                </Box>
            )}
        </Card>
    );
};

Post.propTypes = {
    postId: PropTypes.number,
    dataTime: PropTypes.string,
    userName: PropTypes.string,
    name: PropTypes.string,
    photo: PropTypes.string,
    postComments: PropTypes.array,
    postLikes: PropTypes.array,
    text: PropTypes.string,
};


