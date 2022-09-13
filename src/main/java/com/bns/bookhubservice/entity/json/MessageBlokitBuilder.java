package com.bns.bookhubservice.entity.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class MessageBlokitBuilder {

    public ArrayList<Object> blockit(String bookId, String bookTitle){
        ArrayList<Object> blocks =new ArrayList<Object>();
        //Header
        JSONObject ji  =new JSONObject();
        ji.put("emoji",true);
        ji.put("text","책 주인에게 "+bookTitle+" 책을 수령한 후 '수령 완료' 버튼틀 눌러주세요!");
        ji.put("type","plain_text");


        JSONObject jo = new JSONObject();
        jo.put("type","header");
        jo.put("text",ji);
        blocks.add(jo);

        //Section
        JSONObject jl1 = new JSONObject();
        jl1.put("text","책을 받을 장소와 시간을 대화를 통해 정해주세요");
        jl1.put("type","plain_text");
        jl1.put("emoji",true);

        JSONObject jo1 = new JSONObject();
        jo1.put("type","section");
        jo1.put("text",jl1);
        blocks.add(jo1);

        //Button
        JSONObject jio31 = new JSONObject();
        jio31.put("type","plain_text");
        jio31.put("emoji",true);
        jio31.put("text","수령 완료");

        JSONObject ji31 = new JSONObject();
        ji31.put("type","button");
        ji31.put("text",jio31);
        ji31.put("style","primary");
        ji31.put("value","rental_success "+bookId);

        JSONArray jl3 = new JSONArray();
        jl3.add(ji31);


        JSONObject jo3 = new JSONObject();
        jo3.put("type","actions");
        jo3.put("elements",jl3);
        blocks.add(jo3);


        return blocks;
    }

}
