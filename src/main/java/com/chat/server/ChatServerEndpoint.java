package com.chat.server;

import com.chat.domain.GenericMessage;
import com.chat.domain.LoginMessage;
import com.chat.domain.Message;
import com.chat.domain.User;
import com.chat.transformers.GenericMessageDecoder;
import com.chat.transformers.GenericMessageEncoder;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static java.lang.String.format;

@ServerEndpoint(value = "/test",
        decoders = GenericMessageDecoder.class,
        encoders = GenericMessageEncoder.class)
public class ChatServerEndpoint {

    private static Map<User, Session> userSessionMap = Collections.synchronizedMap(new HashMap<User, Session>());
    private static Map<User, User> matchMap = Collections.synchronizedMap(new HashMap<User, User>());

    @OnOpen
    public void onOpen(Session session) {
        System.out.println("Inside OnOpen of ServerEndpoint");
        System.out.println(format("%s joined the chat", getLoggedInUserFromSession(session).getUsername()));
    }

    @OnMessage
    public void onMessage(GenericMessage msg, Session session) throws IOException, EncodeException {
        if (msg instanceof LoginMessage) {
            LoginMessage loginMessage = (LoginMessage) msg;
            System.out.println("Inside OnLoginMessage of ServerEndpoint");
            session.getUserProperties().put("loggedInUser", loginMessage.getLoggedInUser());
            userSessionMap.put(loginMessage.getLoggedInUser(), session);
        } else if (msg instanceof Message) {
            Message message = (Message) msg;
            System.out.println("Inside OnMessage of ServerEndpoint");
            User matchedUser = findMatch(session);
            Session matchedUserSession = userSessionMap.get(matchedUser);
            if (matchedUserSession != null)
                matchedUserSession.getBasicRemote().sendObject(message);
        }
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        System.out.println("Inside OnClose of ServerEndpoint");

        Message message = new Message();
        message.setSender("admin");
        message.setContent(String.format("%s has left the chat. Wait for another match.", getLoggedInUserFromSession(session).getUsername()));
        User matchedUser = findMatch(session);
        if (userSessionMap.containsKey(matchedUser)) {
            userSessionMap.get(matchedUser).getBasicRemote().sendObject(message);
        }
        deleteMatch(session);
    }

    /**
     * This methods finds a match for the logged in user amongst the other online users
     */
    private User findMatch(Session session) {

        User matchedUser = null;
        User loggedInUser = getLoggedInUserFromSession(session);
        if (matchMap.containsKey(loggedInUser)) {
            matchedUser = matchMap.get(loggedInUser);
        } else {
            for (User user : userSessionMap.keySet()) {
                if (loggedInUser.getCriterion().equals(user.getFeature()) && !matchMap.containsKey(user)) {
                    matchedUser = user;
                    matchMap.put(loggedInUser, matchedUser);
                    matchMap.put(matchedUser, loggedInUser);
                }
            }

        }
        return matchedUser;
    }

    /**
     * To remove the bi-directional link of loggedInUser-matchedUser from the map
     */
    private void deleteMatch(Session session) {
        User loggedInUser = getLoggedInUserFromSession(session);
        if (matchMap.containsKey(loggedInUser)) {
            User matchedUser = matchMap.get(loggedInUser);
            matchMap.remove(matchedUser);
            matchMap.remove(loggedInUser);
        }
    }

    /**
     * To retrieve logged in user info from the session properties
     */
    private User getLoggedInUserFromSession(Session session) {
        return (User) session.getUserProperties().get("loggedInUser");
    }

}
