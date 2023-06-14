import React, { useEffect, useContext, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import SearchIcon from '@mui/icons-material/Search';
import SendIcon from '@mui/icons-material/Send';
import { apiUrl } from "../apiConfig";
import { InboxMessage } from "../components/Messages/Inbox/InboxMessage";
import { Button, TextField } from "@mui/material";
import { GenerateBlueMessage } from "../components/Messages/FullTexting/BlueMessage";
import { GenerateWhiteMessage } from "../components/Messages/FullTexting/WhiteMessage";
import PropTypes from "prop-types";
import { height } from "@mui/system";

function TextingMessage({ sender, receiver, selectedMessage }) {
  const [inboxMessagesClick, setInboxMessagesClick] = useState(null);

  const fetchTexting = async () => {
    const response = await fetch(`${apiUrl}/api/getMessages`, {
      method: "POST",
      body: JSON.stringify({
        inboxUid: sender,
        userId: receiver,
      }),
      headers: { "Content-Type": "application/json" }
    });
    const textingData = await response.json();
    if (textingData[0]) {
      console.log(textingData);
      const formattedMessages = textingData.map((item) => {
        console.log(item);
        const dateString = item.createdAt;
        const date = new Date(dateString);
        const options = { month: 'long', day: 'numeric', year: 'numeric' };
        const formattedDate = date.toLocaleDateString('en-US', options);
        if (item.userId === 66) {
          return (
            <GenerateBlueMessage
              key={item.userUid}
              text={item.message}
              timestampText={formattedDate}
            />
          );
        } else {
          console.log("this " + selectedMessage);
          return (
            <GenerateWhiteMessage
              key={item.userUid}
              text={item.message}
              timestampText={formattedDate}
            />
          );
        }
      });
      setInboxMessagesClick(formattedMessages);
    } else {
      setInboxMessagesClick(null);
    }
  };

  useEffect(() => {
    fetchTexting();
  }, [sender, receiver]);

  return (
    <div>
      {inboxMessagesClick != null ? (
        inboxMessagesClick
      ) : (
        <div>No texting</div>
      )}
    </div>
  );
}

TextingMessage.propTypes = {
  sender: PropTypes.number.isRequired,
  receiver: PropTypes.number.isRequired,
  selectedMessage: PropTypes.object.isRequired,
};

export function Message() {
  const inboxContainerStyle = {
    width: "100%",
    margin: "0 auto",
  };
  
  const sortContainerStyle = {
    display: "flex",
    alignItems: "center",
    padding: "10px",
    marginBottom: "10px",
  };
  
  const searchInputContainerStyle = {
    position: "relative",
    flex: "1",
  };
  
  const searchIconStyle = {
    position: "absolute",
    top: "50%",
    right: "8px",
    transform: "translateY(-50%)",
    color: "#657786",
  };
  
  const searchInputStyle = {
    boxSizing: "border-box",
    width: "100%",
    border: "none",
    borderRadius: "20px",
    padding: "8px 12px",
    paddingLeft: "32px",
    backgroundColor: "#E6ECF0",
    color: "#14171A",
    fontSize: "14px",
  };
  
  function SortBlock() {
    return (
      <div style={sortContainerStyle}>
        <div style={searchInputContainerStyle}>
          <input type="text" placeholder="Пошук" style={searchInputStyle} />
          <SearchIcon style={searchIconStyle} />
        </div>
      </div>
    );
  }
const [selectedMessage, setSelectedMessage] = useState(null);
  function LoadAllMessages() {
    
    const userId = useSelector(state => state.userData.userData.userId);
    const [inboxMessages, setInboxMessages] = useState([]);
    
    const fetchMessages = async () => {
      const response1 = await fetch(`${apiUrl}/api/inbox/66`);
      const userData = await response1.json();

      if (userData[0]) {
        const formattedMessages = userData.map((item) => {
          console.log(item);
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
              handleClick={() => {
                setSelectedMessage(item);
                // return(
                //   <TextingMessage sender={item.inboxUid} receiver={item.userId} />
                // )
              }}
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
    }, [userId]);

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

  return (
    <div style={{ display: "flex", paddingBottom: "20px" }}>
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          overflowY: "scroll",
          maxHeight: "calc(100vh - 120px)",
          height: "calc(100vh - 120px)",
          padding: "20px 20px",
          boxSizing: "border-box",
          width: "600px",
        }}
      >
        <SortBlock />
        <div style={inboxContainerStyle}>
          <LoadAllMessages />
        </div>
      </div>
      <div
        style={{
          display: "flex",
          flexDirection: "column",
          boxSizing: "border-box",
          borderTop: "1px solid rgba(0, 0, 0, 0.1)",
          maxHeight: "calc(100vh - 120px)",
          height: "calc(100vh - 120px)",
          width: "600px",
          position: "relative",
        }}
      >
        <div
          style={{
            display: "flex",
            flexDirection: "column",
            overflowY: "scroll",
            maxHeight: "calc(100vh - 160px)",
            height: "calc(100vh - 160px)",
            flexGrow: "1",
            padding: "20px 20px 0 20px",
            boxSizing: "border-box",
          }}
        >
          {selectedMessage === null ? (
            <div>No texting</div>
          ) : (
            <>
              <TextingMessage
                sender={selectedMessage.inboxUid}
                receiver={selectedMessage.userId}
                selectedMessage={selectedMessage}
              />
            </>
          )}
        </div>
        <div
          style={{
            display: "flex",
            alignItems: "center",
            backgroundColor: "white",
            padding: "0 20px",
            boxSizing: "border-box",
            height: "40px",
          }}
        >
          <TextField
            id="outlined-basic"
            variant="outlined"
            placeholder="Input message"
            size="small"
            InputProps={{ endAdornment: <SendIcon />,}}
            style={{
              
              flex: "1",
            }}
          />
        </div>
      </div>

    </div>
  );
}
