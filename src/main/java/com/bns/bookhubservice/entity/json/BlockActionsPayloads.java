package com.bns.bookhubservice.entity.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.util.MultiValueMap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Getter
public class BlockActionsPayloads {

    private String EventType = "N";
    private String user_id = null;
    private String button_value = null;
    private String channel_id = null;


    private Map<String, String> result = new HashMap<>();
    private String json = null;


    public BlockActionsPayloads(MultiValueMap<String, String> data) throws ParseException {
        List<String> payload_list = data.get("payload");
        String payload = payload_list.get(0);

        JSONParser parser = new JSONParser();
        Object obj = parser.parse(payload);
        JSONObject json = (JSONObject) obj;
        this.EventType = String.valueOf(json.get("type"));

        if (EventType.equals("block_actions")) {
            JSONObject user = (JSONObject) json.get("user");
            this.user_id = (String) user.get("id");

            JSONObject channel = (JSONObject) json.get("channel");
            this.channel_id = (String) channel.get("id");

            List<Object> actions_list = (List<Object>) json.get("actions");
            JSONObject actions = (JSONObject) actions_list.get(0);
            this.button_value = (String) actions.get("value");

            result.put("type", EventType);
            result.put("user", user_id);
            result.put("value", button_value);


        }
    }
    public void setJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.json = objectMapper.writeValueAsString(result);

    }

}
