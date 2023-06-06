import React, { useEffect, useState, useMemo, useCallback } from "react";
import PropTypes from "prop-types";
import { formatDistanceToNow, differenceInDays, format } from "date-fns";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

import { Card, CardContent, Avatar, Typography, CardActions, IconButton, Paper, Box } from "@mui/material";
import { FavoriteBorder, ChatBubbleOutline, Repeat, Favorite } from "@mui/icons-material";
import { Comments } from "./Comments.js";

import { PostCard, PostText, ShowMoreLinkStyles } from "./PostStyles";
import { openLoginModal, setComments, setSearchId } from "../../store/actions";
import CircularProgress from "@mui/material/CircularProgress";

export const Post = ({
                         userName,
                         name,
                         photo,
                         text,
                         dataTime,
                         postId,
                         postLikes,
                         postComments,
                         userIdWhoSendPost,
                         profileImage,
                         reposted
                     }) => {
    const userId = useSelector(state => state.userData.userData.userId);
    const dispatch = useDispatch();
    const navigate = useNavigate();
    const [showMore, setShowMore] = useState(false);
    const [isCommentOpen, setIsCommentOpen] = useState(false);
    const [postCommentCount, setPostCommentCount] = useState(postComments);
    const comments = useSelector(state => state.comments.comments);
    const [like, setLike] = useState(false);
    const [likeArr, setLikeArr] = useState([]);
    const [isReposted, setIsReposted] = useState(reposted);
    const [likeCount, setLikeCount] = useState(postLikes);
    const [showLike, setShowLike] = useState(false);
    const [usersWhoLike, setUsersWhoLike] = useState([]);
    const [likesIsLoading, setLikesIsLoading] = useState(false);
    const [isLoadingComments, setIsLoadingComments] = useState(false);

    const ShowUsersWhoLike = async () => {
        if (userId) {
            setShowLike(!showLike);
        } else {
            dispatch(openLoginModal());
        }

    };

    useEffect(() => {
        const fetchLikes = async () => {
            try {
                setLikesIsLoading(true);
                let dataAboutUsersWhoLike = await fetch(`http://localhost:8080/users/likes?postId=${postId}&page=0`);
                let usersWhoLike2 = await dataAboutUsersWhoLike.json();
                setUsersWhoLike(usersWhoLike2);
                console.log("usersWhoLike", usersWhoLike2);
            } catch (err) {
                console.log(err);
            } finally {
                setLikesIsLoading(false);
            }
        };

        if (showLike) {
            fetchLikes();
        }
    }, [showLike]);

    useEffect(() => {
        const fetchData = async () => {
            if (userId) {
                try {
                    const activeLikesResponse = await fetch(`http://localhost:8080/likes/active?postId=${postId}&userId=${userId}`);
                    const activeLikes = await activeLikesResponse.json();
                    setLike(activeLikes);
                } catch (error) {
                    console.error("Ошибка при получении данных:", error);
                }
            }
        };
        fetchData();
    }, [userId, postId]);

    const toAnotherUserPage = (userIdWhoSendPost) => {
        if (userId) {
            console.log();
            dispatch(setSearchId(String(userIdWhoSendPost)));
            navigate("/view");
        } else {
            console.log(userId);
            dispatch(openLoginModal());
        }
    };

    const sendRepost = async () => {
        if (userId) {
            setIsReposted(true);
            await fetch(`http://localhost:8080/reposts`, {
                method: "POST",
                body: JSON.stringify({
                    postId: postId,
                    userId: userId,
                }),
                headers: {
                    "Content-Type": "application/json"
                },
            });
        } else {
            dispatch(openLoginModal());
        }
    };


    const handleCommentToggle = async () => {
        if (userId) {
            try {
                setIsCommentOpen(!isCommentOpen);
                setIsLoadingComments(true);
                let commentsResponse = await fetch(`http://localhost:8080/comments?postId=${postId}`);
                let dataComments = await commentsResponse.json();
                dispatch(setComments(dataComments));
            } catch (err) {
                console.log(err);
            } finally {
                setIsLoadingComments(false);
            }
        } else {
            dispatch(openLoginModal());
        }
    };

    const addLikeHandle = useCallback(async () => {
        if (userId) {
            if (!like) {
                await fetch("http://localhost:8080/likes", {
                    method: "POST",
                    body: JSON.stringify({
                        postId: postId,
                        userId: userId,
                    }),
                    headers: {
                        "Content-Type": "application/json"
                    }
                });
                setLikeCount(likeCount + 1);
                setLikeArr([...likeArr, { postId: postId, userId: userId }]);
            } else {
                await fetch(`http://localhost:8080/likes?postId=${postId}&userId=${userId}`, {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json"
                    }
                });
                setLikeCount(likeCount - 1);
                setLikeArr(likeArr.filter(item => item.userId !== userId));
            }

            setLike(!like);
        } else {
            dispatch(openLoginModal());
        }
    }, [like, userId, postId, likeArr, dispatch]);

    const handleShowMore = async () => {
        setShowMore(!showMore);
    };

    const postDate = useMemo(() => {
        const date = new Date(dataTime);
        const diffDays = differenceInDays(new Date(), date);

        if (diffDays < 1) {
            return formatDistanceToNow(date, { addSuffix: true });
        } else if (diffDays < 365) {
            return format(date, "MMM d");
        } else {
            return format(date, "MMM d, yyyy");
        }
    }, [dataTime]);

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
        <Card  sx={{ ...PostCard, position: "relative", overflowAnchor: "none"  }}>
            <CardContent sx={{ display: "flex", paddingBottom: 0 }}>
                {profileImage ? <img src={profileImage ? `data:image/png;base64,${profileImage}` : ""}
                                     style={{ width: "50px", height: "50px", borderRadius: "50px" }} alt=""/> :
                    <Avatar alt={userName} src="#"/>}
                <div style={{ marginLeft: 16, flex: 1 }}>
                    <Typography variant="subtitle1" component="div"
                                sx={{ textDecoration: "underline", cursor: "pointer" }}
                                onClick={() => toAnotherUserPage(userIdWhoSendPost)}>
                        {name} <span style={{ color: "#5b7083" }}>@{userName}</span> · {postDate}
                    </Typography>
                    <Typography variant="body1" component="div" mt={1}
                                sx={{ ...PostText, maxHeight: showMore ? "none" : "90px", }}>{renderText()}
                    </Typography>
                    {text.split(" ").length > 10 && (
                        <a href="#" style={ShowMoreLinkStyles} onClick={handleShowMore}>
                            {showMore ? "hight text" : "see more"}
                        </a>
                    )}
                </div>
            </CardContent>
            {
                photo ? (<div style={{
                    maxWidth: "600px",
                    width: "600px",
                    margin: "10px auto",
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center"
                }}>
                    <img src={photo ? `data:image/png;base64,${photo}` : ""}
                         style={{ width: "450px", margin: "0 auto" }} alt=""/>

                </div>) : null
            }
            <CardActions sx={{ padding: "20px 20px" }}>
                <IconButton onClick={handleCommentToggle}>
                    <ChatBubbleOutline fontSize="small"/>
                    <Typography variant="body2" sx={{ marginLeft: "5px" }}>{postCommentCount}</Typography>
                </IconButton>
                <IconButton onClick={sendRepost}>
                    <Repeat fontSize="small" htmlColor={isReposted ? "blue" : "inherit"}/>
                </IconButton>
                <IconButton onClick={addLikeHandle}>
                    {like ? <Favorite fontSize="small" sx={{ color: "red" }}/> : <FavoriteBorder fontSize="small"/>}

                </IconButton>
                <Typography onClick={ShowUsersWhoLike} variant="body2" sx={{
                    marginLeft: "0px",
                    textDecoration: "underline",
                    cursor: "pointer"
                }}>{likeCount}</Typography>
                <Paper elevation={3} sx={{
                    width: "200px",
                    marginLeft: "10px",
                    maxHeight: "70px",
                    position: "absolute",
                    left: "170px",
                    overflow: "scroll",
                }}>
                    {likesIsLoading ?
                        <CircularProgress sx={{ marginLeft: "40%", width: "5px", height: "5px" }}/>
                        :
                        showLike ?
                            usersWhoLike.length > 0 ?
                                usersWhoLike.map(user => (
                                    <Box key={user.userId} onClick={() => toAnotherUserPage(user.userId)} sx={{
                                        display: "flex",
                                        cursor: "pointer",
                                        borderBottom: "1px solid rgba(0, 0, 0, 0.1)",
                                        padding: "5px 10px",
                                        "&:hover": {
                                            backgroundColor: "rgba(128, 128, 128, 0.1)",
                                        },
                                    }}>
                                        <Typography>@{user.username}</Typography>
                                        <Typography sx={{ marginLeft: "10px" }}>{user.name}</Typography>
                                    </Box>
                                ))
                                :
                                <Typography sx={{
                                    fontSize: "0.9rem",
                                    fontFamily: "'Lato', sans-serif",
                                }}>Лайків ще немає!Будь першим</Typography>
                            :
                            null}
                </Paper>
            </CardActions>
            {isCommentOpen &&
                <Comments comments={comments} isLoadingComments={isLoadingComments} postCommentCount={postCommentCount}
                          setPostCommentCount={setPostCommentCount} postId={postId} userId={userId}/>}
        </Card>
    );
};

Post.propTypes = {
    reposted: PropTypes.bool,
    profileImage: PropTypes.string,
    postId: PropTypes.number,
    dataTime: PropTypes.string,
    userName: PropTypes.string,
    name: PropTypes.string,
    photo: PropTypes.string,
    postComments: PropTypes.number,
    postLikes: PropTypes.number,
    text: PropTypes.string,
    userIdWhoSendPost: PropTypes.number,
    scrollPosition:PropTypes.string,
};


