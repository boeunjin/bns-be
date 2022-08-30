package com.bns.bookhubservice.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
public class RentalJson {
    private String owner = null;
    private String myself = null;

    private String bookName = null;
    private String author = null;
    private String image = null;
    private String json = null;
    private Map<String, String> result = new HashMap<>();

    public RentalJson(Map<String, Object> data){
        owner = String.valueOf(data.get("owner"));
        //"type" : owner (usr id 가 있는지 여기서 확인
        myself = String.valueOf(data.get("myself"));
        //
        bookName = String.valueOf(data.get("bookName"));
        author = String.valueOf(data.get("author"));
        image = String.valueOf(data.get("image"));
        result.put("owner",owner);
        result.put("myself",myself);
        result.put("bookName",bookName);
        result.put("author",author);
        result.put("image",image);

    }
    public void setResultText(String responseText) { result.put("text", responseText);}

    public void setJson() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.json = objectMapper.writeValueAsString(result);

    }
}