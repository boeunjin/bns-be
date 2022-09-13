package com.bns.bookhubservice.entity.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.time.LocalDate;
import java.util.ArrayList;

public class CompleteBlokitBuilder {
    public ArrayList<Object> blockit(String bookTitle, String end){
        ArrayList<Object> blocks =new ArrayList<Object>();
        //Header
        JSONObject ji  =new JSONObject();
        ji.put("emoji",true);
        ji.put("text",bookTitle+" 책 대여가 완료 되었습니다!");
        ji.put("type","plain_text");


        JSONObject jo = new JSONObject();
        jo.put("type","header");
        jo.put("text",ji);
        blocks.add(jo);

        //Section
        JSONObject jl1 = new JSONObject();
        jl1.put("text",end+"까지 책 주인에게 반납 해주시길 바랍니다.");
        jl1.put("type","plain_text");
        jl1.put("emoji",true);

        JSONObject jo1 = new JSONObject();
        jo1.put("type","section");
        jo1.put("text",jl1);
        blocks.add(jo1);


        return blocks;
    }
}
