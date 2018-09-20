package com.chat.transformers;

import com.chat.domain.GenericMessage;
import com.chat.domain.LoginMessage;
import com.chat.domain.Message;
import com.google.gson.Gson;

import javax.websocket.Decoder;
import javax.websocket.EndpointConfig;
import java.util.Date;

public class GenericMessageDecoder implements Decoder.Text<GenericMessage> {
    private static Gson GSON = new Gson();

    @Override
    public GenericMessage decode(String s) {
        //System.out.println("Inside decode");
        GenericMessage genericMessage;
        genericMessage = GSON.fromJson(s, Message.class);
        if (genericMessage.getType() == GenericMessage.MsgType.LOGIN_MSG) {
            genericMessage = GSON.fromJson(s, LoginMessage.class);
        } else {
            ((Message) genericMessage).setReceived(new Date());
        }
        return genericMessage;
    }

    @Override
    public boolean willDecode(String s) {
        return true;
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
