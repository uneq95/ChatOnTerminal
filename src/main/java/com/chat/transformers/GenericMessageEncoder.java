package com.chat.transformers;

import com.chat.domain.GenericMessage;
import com.google.gson.Gson;

import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class GenericMessageEncoder implements Encoder.Text<GenericMessage> {
    private static Gson GSON = new Gson();

    @Override
    public String encode(GenericMessage genericMessage) {
        //System.out.println("Inside encode");
        return GSON.toJson(genericMessage);
    }

    @Override
    public void init(EndpointConfig endpointConfig) {

    }

    @Override
    public void destroy() {

    }
}
