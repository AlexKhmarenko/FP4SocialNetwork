import React, { useEffect, useCallback, useContext, useRef, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import SendIcon from '@mui/icons-material/Send';
import { apiUrl } from "../apiConfig";
import { InboxMessage } from "../components/Messages/Inbox/InboxMessage";
import { Button, TextField } from "@mui/material";
import { TextingMessage } from "../components/Messages/FullTexting/TextingMessage";
import {MessageSearch} from "../components/Messages/Inbox/MessageSearch";
import { leftBlockInboxAndSearch, inboxContainerStyle,
  textingContainerWithInputStyle, leftBlockAndRightBlockContainer,
  textingContainerWithScroll, textingConatinerScrollFromBottom,
  textingConatinerScrollFromTop } from "./pagesStyles/MessageStyles";
import { setMessages, setPageForMessage } from "../store/actions";
import PropTypes from 'prop-types';
import { fetchTextsByPage } from "../store/actions";

function LoadAllMessages({ userId, handleSelectMessage }) {
  LoadAllMessages.propTypes = {
    userId: PropTypes.string.isRequired,
    handleSelectMessage: PropTypes.func.isRequired,
  };
  const [inboxMessages, setInboxMessages] = useState([]);
  const page = useSelector(state => state.pageCountMessage.page);
  const dispatch = useDispatch();
  const fetchMessages = async () => {
    const response1 = await fetch(`${apiUrl}/api/inbox/${userId}`);
    const userData = await response1.json();

    if (userData[0]) {
      const formattedMessages = userData.map((item) => {
        const dateString = item.createdAt;
        const date = new Date(dateString);
        const options = { month: 'long', day: 'numeric', year: 'numeric' };
        const formattedDate = date.toLocaleDateString('en-US', options);
        return (
          <InboxMessage
            key={item.userId}
            senderName={item.name}
            sender={item.inboxUid}
            receiver={item.userId}
            message={item.message}
            date={formattedDate}
            handleClick={(event) => {
              event.preventDefault();
              handleSelectMessage(item);
              dispatch(fetchTextsByPage(item.inboxUid, userId, page))
            }
            }
          />
        );
      });
      setInboxMessages(formattedMessages);
    } else {
      setInboxMessages([]);
    }
  };

  useEffect(() => {
    fetchMessages();
    console.log("Message useEffect");
  }, []); // Зміна тут: передавання порожнього масиву залежностей

  return (
    <div>
      {inboxMessages.length > 0 ? (
        inboxMessages
      ) : (
        <>
          <div>Немає повідомлень</div>
          <Button variant="contained">Написати повідомлення</Button>
        </>
      )}
    </div>
  );
}

export function Message() {
  const dispatch = useDispatch();
  const messages = useSelector((state) => state.messages.messages);
  const page = useSelector(state => state.pageCountMessage.page);
  const [selectedMessage, setSelectedMessage] = useState(null);
  const [isFetchingTexts, setIsFetchingTexts] = useState(false);
  const [texts, setTexts] = useState([]);
  const [allTextsLoaded, setAllTextsLoaded] = useState(false);
  const [inputValue, setInputValue] = useState('');

  const textingContainerRef = useRef(null);

  useEffect(() => {
    if (textingContainerRef.current) {
      textingContainerRef.current.scrollTop = textingContainerRef.current.scrollHeight;
    }
    console.log("Message useEffect REF");
  }, [selectedMessage]);

  function handleSelectMessage(message) {
    setSelectedMessage(message);
  }

  const userId = useSelector((state) => state.userData.userData.userId);

  const handleScroll = useCallback(async (event) => {
    if (isFetchingTexts || allTextsLoaded) {
        return;
    }
    setIsFetchingTexts(true);
    try {
        const { scrollTop, clientHeight, scrollHeight } = event.currentTarget;
        if (scrollTop === 0) {
            let newTexts = [...selectedMessage];
            if (newTexts.length > 0) {
                dispatch(setPageForMessage());
                setTexts([...texts, ...newTexts]);
                dispatch(fetchTextsByPage(selectedMessage.inboxUid, useId, page));
            } else {
                setAllTextsLoaded(true);
            }
        }
    } finally {
        
        setIsFetchingTexts(false);
    }
  }, [dispatch, location.pathname, page, userId]);

  return (
    <div style={leftBlockAndRightBlockContainer}>
      <div style={leftBlockInboxAndSearch}>
        <MessageSearch />
        <div style={inboxContainerStyle}>
          <LoadAllMessages userId={userId} handleSelectMessage={handleSelectMessage}/>
        </div>
      </div>
      <div style={textingContainerWithInputStyle}>
        
          {selectedMessage === null ? (
            <div style={textingConatinerScrollFromTop} ref={textingContainerRef}>
              <div>No texting</div>
            </div>
          ) : (
            <div style={textingConatinerScrollFromBottom} ref={textingContainerRef}>
              <TextingMessage
                onScroll={handleScroll}
                sender={selectedMessage.inboxUid}
                receiver={selectedMessage.userId}
                selectedMessage={messages}
                key={selectedMessage.inboxUid}
              />
            </div>
          )}
        
        <div style={textingContainerWithScroll}>
          {selectedMessage && (
            <TextField
              id="outlined-basic"
              variant="outlined"
              placeholder="Input message"
              size="small"
              value={inputValue}
              onChange={(event) => {
                event.preventDefault();
                setInputValue(event.target.value.toString());
              }}
              InputProps={{
                endAdornment: (
                  <SendIcon
                    style = {{ cursor: "pointer", }}
                    onClick={(event) => {
                      event.preventDefault();
                      const fetchPostTexting = async () => {
                        const response = await fetch(`${apiUrl}/api/addMessage`, {
                          method: "POST",
                          body: JSON.stringify({
                            inboxUid: selectedMessage.userId,
                            userId: selectedMessage.inboxUid,
                            writtenMessage: inputValue,
                          }),
                          headers: { "Content-Type": "application/json" }
                        });
                      }
                      fetchPostTexting();
                      setInputValue("");
                    }}
                  />
                )
              }}
              style={{
                flex: "1",
              }}
            />
          )}
        </div>
      </div>
    </div>
  );
}
