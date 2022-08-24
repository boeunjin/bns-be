package com.bns.bookhubservice.entity.json;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class BlokitBuilder {

    public ArrayList<Object> blockit(String owner){
        ArrayList<Object> blocks =new ArrayList<Object>();
        //Header
        JSONObject ji  =new JSONObject();
        ji.put("emoji",true);
        ji.put("text","안녕하세요, 책을 빌리고 싶어요");
        ji.put("type","plain_text");


        JSONObject jo = new JSONObject();
        jo.put("type","header");
        jo.put("text",ji);
        blocks.add(jo);

        //divider
        JSONObject jo5 = new JSONObject();
        jo5.put("type","divider");
        blocks.add(jo5);

        //Section 1
        JSONObject ji11 = new JSONObject();
        ji11.put("text","*책 제목:*\n마이크로서비스 패턴");
        ji11.put("type","mrkdwn");


        JSONObject ji12 = new JSONObject();
        ji12.put("text","*저자 :*\n크리스 리처드슨");
        ji12.put("type","mrkdwn");


        JSONArray jl1 = new JSONArray();
        jl1.add(ji11);
        jl1.add(ji12);

        JSONObject jo1 = new JSONObject();
        jo1.put("type","section");
        jo1.put("fields",jl1);
        blocks.add(jo1);

        //Section2
        JSONObject ji21 = new JSONObject();
        ji21.put("type","mrkdwn");
        ji21.put("text","*요청자 근무지 :*\n마곡사옥/8층");

        JSONObject ji22 = new JSONObject();
        ji22.put("type","mrkdwn");
        ji22.put("text","*요청자 :*\n<@"+owner+"> 님");

        JSONArray jl2 = new JSONArray();
        jl2.add(ji22);
        jl2.add(ji21);

        JSONObject jo2 = new JSONObject();
        jo2.put("type","section");
        jo2.put("fields",jl2);
        blocks.add(jo2);

        //Book Image
        JSONObject jo4 = new JSONObject();
        jo4.put("type","image");
        jo4.put("image_url","https://books.google.co.kr/books/content?id=dt_QDwAAQBAJ&printsec=frontcover&img=1&zoom=1&h=160&stbn=1");
        jo4.put("alt_text","inspiration");
        blocks.add(jo4);


        //Button Action
        JSONObject jio31 = new JSONObject();
        jio31.put("type","plain_text");
        jio31.put("emoji",true);
        jio31.put("text","빌려준다");

        JSONObject ji31 = new JSONObject();
        ji31.put("type","button");
        ji31.put("text",jio31);
        ji31.put("style","primary");
        ji31.put("value","success");
        //ji31.put("url","https://www.google.com");
        //ji31.put("url","http://localhost:5000/slack/v1/updateRentalBook");

        JSONObject jio32 = new JSONObject();
        jio32.put("type","plain_text");
        jio32.put("emoji",true);
        jio32.put("text","빌려줄 수 없다");

        JSONObject ji32 = new JSONObject();
        ji32.put("type","button");
        ji32.put("text",jio32);
        ji32.put("style","danger");
        ji32.put("value","fail");


        JSONArray jl3 = new JSONArray();
        jl3.add(ji31);
        jl3.add(ji32);

        JSONObject jo3 = new JSONObject();
        jo3.put("type","actions");
        jo3.put("elements",jl3);
        blocks.add(jo3);

        return blocks;
    }
}
