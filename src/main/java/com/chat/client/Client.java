package com.chat.client;

import com.chat.domain.LoginMessage;
import com.chat.domain.Message;
import com.chat.domain.User;
import org.glassfish.tyrus.client.ClientManager;

import javax.websocket.DeploymentException;
import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws URISyntaxException, DeploymentException, IOException, EncodeException {
        ClientManager clientManager = ClientManager.createClient();
        String messageContent;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to my Chat App!");
        System.out.println("What's your name?");
        String user = scanner.nextLine();
        System.out.println("feature");
        String feature = scanner.nextLine();
        System.out.println("criterion");
        String criterion = scanner.nextLine();

        LoginMessage loginMessage = new LoginMessage();
        loginMessage.setLoggedInUser(new User(user, feature, criterion));

        // Connect to the server endpoint
        Session session = clientManager.connectToServer(ChatClientEndpoint.class, new URI("ws://localhost:8010/chat/test"));
        //Thread.sleep(3000L);
        session.getBasicRemote().sendObject(loginMessage);
        System.out.println("You are logged in as: " + user);

        while (true) {
            messageContent = scanner.nextLine();
            if (!messageContent.isEmpty()) {
                if (messageContent.equalsIgnoreCase("quit")) {
                    session.close();
                    break;
                } else {
                    Message message = new Message();
                    message.setContent(messageContent);
                    message.setSender(user);
                    session.getBasicRemote().sendObject(message);
                }
            }
        }
    }
}
