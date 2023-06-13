package com.danit.socialnetwork.websocket;

import com.danit.socialnetwork.dto.NotificationRequest;
import com.danit.socialnetwork.dto.user.UserFollowDtoResponse;
import com.danit.socialnetwork.model.Notification;
import com.danit.socialnetwork.service.NotificationService;
import com.danit.socialnetwork.service.UserFollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class WebSocketController {
    NotificationService notificationService;
    UserFollowService userFollowService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    //    @MessageMapping("/message") // app/message
//    @SendTo("/chatroom/public")
//    public Message receivePublicMessage(@Payload Message message) {
//        return message;
//    }
//    @MessageMapping("/message") // app/message
//    @SendTo("/message-to/{userId}")
//    public WebSocketMessageDtoResponse receivePublicMessage(
//            @Payload WebSocketMessageDtoResponse message) {
//        String userId = message.getReceiverId();
//        return message;
//    }


//    @MessageMapping("/message")
//    @SendTo("/chatroom/public")
//    public Message receiveMessage(@Payload Message message){
//        return message;
//    }

//@PostMapping(path="")



    @MessageMapping("/post") // app/post
    public void receivePrivateMessage(
            @Payload NotificationRequest notificationRequest) {
        System.out.println("==================WEBSOCKET !!!==========================");

        List<UserFollowDtoResponse> followers = userFollowService.
                getAllUsersByUserFollowingId(notificationRequest.getUserId());

        for (UserFollowDtoResponse follower : followers) {
            Integer followerId = follower.getUserId();
            String notificationText = "User " +
                    follower.getUsername() +
                    "has a new post";
            Notification newNotification = new Notification(
                    followerId, notificationRequest.getUserId(),
                    notificationText);
            notificationService.saveNotification(newNotification);

            notificationRequest.setNotificationText(notificationText);
            simpMessagingTemplate.convertAndSendToUser(followerId.toString()
                    , "/api/notifications", notificationRequest); // /user/67/notifications
        }
    }
}