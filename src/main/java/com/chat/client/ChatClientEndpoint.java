package com.chat.client;

import com.chat.domain.GenericMessage;
import com.chat.domain.Message;
import com.chat.transformers.GenericMessageDecoder;
import com.chat.transformers.GenericMessageEncoder;

import javax.websocket.ClientEndpoint;
import javax.websocket.OnMessage;
import java.text.SimpleDateFormat;

@ClientEndpoint(decoders = GenericMessageDecoder.class,
        encoders = GenericMessageEncoder.class)
public class ChatClientEndpoint {

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat();

    @OnMessage
    public void onMessage(GenericMessage genericMessage) {
        //System.out.println("Inside OnMessage of ClientEndpoint");
        Message message = (Message) genericMessage;
        if (message != null)
            System.out.println(String.format("[%s:%s] %s",
                    simpleDateFormat.format(message.getReceived()), message.getSender(), message.getContent()));
    }


}