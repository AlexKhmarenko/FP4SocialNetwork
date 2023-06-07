import React, { useEffect, useState, useMemo, useCallback } from "react";
import PropTypes from "prop-types";
import { formatDistanceToNow, differenceInDays, format } from "date-fns";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

import { Card, CardContent, Avatar, Typography, CardActions, IconButton, Paper, Box } from "@mui/material";
import { FavoriteBorder, ChatBubbleOutline, Repeat, Favorite } from "@mui/icons-material";
import { Comments } from "./Comments.js";

import {
    PostCard,
    PostText,
    PostTextWrapper,
    ProfileImgStyles,
    ShowMoreLinkStyles,
    userLikeCount,
    userNameParagraph,
    UserPhoto,
    UserPhotoWrapper,
    PostPaper,
    LikesCircular,
    LikeBox,
    CardContentPost,
    EmptyLikesUserArrParagraph
} from "./PostStyles";
import { openLoginModal, setComments, setSearchId } from "../../store/actions";
import CircularProgress from "@mui/material/CircularProgress";
import { apiUrl } from "../../apiConfig";
import { UsersLikes } from "./UsersLikes";

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
                let dataAboutUsersWhoLike = await fetch(`${apiUrl}/users/likes?postId=${postId}&page=0`);
                let usersWhoLike2 = await dataAboutUsersWhoLike.json();
                setUsersWhoLike(usersWhoLike2);
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
                    const activeLikesResponse = await fetch(`${apiUrl}/likes/active?postId=${postId}&userId=${userId}`);
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
            dispatch(setSearchId(String(userIdWhoSendPost)));
            navigate("/view");
        } else {
            dispatch(openLoginModal());
        }
    };

    const sendRepost = async () => {
        if (userId) {
            setIsReposted(true);
            await fetch(`${apiUrl}/reposts`, {
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
                let commentsResponse = await fetch(`${apiUrl}/comments?postId=${postId}`);
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
            setLike(!like);
            if (!like) {
                setLikeCount(likeCount + 1);
                setLikeArr([...likeArr, { postId: postId, userId: userId }]);
                await fetch(`${apiUrl}/likes`, {
                    method: "POST",
                    body: JSON.stringify({
                        postId: postId,
                        userId: userId,
                    }),
                    headers: {
                        "Content-Type": "application/json"
                    }
                });
            } else {
                setLikeCount(likeCount - 1);
                setLikeArr(likeArr.filter(item => item.userId !== userId));
                await fetch(`${apiUrl}/likes?postId=${postId}&userId=${userId}`, {
                    method: "DELETE",
                    headers: {
                        "Content-Type": "application/json"
                    }
                });
            }
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
        <Card sx={PostCard}>
            <CardContent sx={CardContentPost}>
                {profileImage ? <img src={profileImage ? `data:image/png;base64,${profileImage}` : ""}
                                     style={ProfileImgStyles} alt=""/> :
                    <Avatar alt={userName} src="#"/>}
                <div style={PostTextWrapper}>
                    <Typography variant="subtitle1" component="div"
                                sx={userNameParagraph}
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
                photo ? (<div style={UserPhotoWrapper}>
                    <img src={photo ? `data:image/png;base64,${photo}` : ""}
                         style={UserPhoto} alt=""/>
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
                <Typography onClick={ShowUsersWhoLike} variant="body2" sx={userLikeCount}>{likeCount}</Typography>
                <UsersLikes  showLike={showLike} likesIsLoading={likesIsLoading} usersWhoLike={usersWhoLike} toAnotherUserPage={toAnotherUserPage}/>
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
    scrollPosition: PropTypes.string,
};


