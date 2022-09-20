package com.bns.bookhubservice.entity.json;

import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ReturnCompleteBlokitBuilder {
    public ArrayList<Object> blockit(String bookTitle, String end, String state){
        ArrayList<Object> blocks =new ArrayList<Object>();
        //Header
        JSONObject ji  =new JSONObject();
        ji.put("emoji",true);
        ji.put("text",bookTitle+" 책 반납이 "+state+" 되었습니다!");
        ji.put("type","plain_text");


        JSONObject jo = new JSONObject();
        jo.put("type","header");
        jo.put("text",ji);
        blocks.add(jo);

        //Section
        JSONObject jl1 = new JSONObject();
        if(state.equals("완료")){
            jl1.put("text",end+"로 책 반납 되었습니다.");
        }
        else{
            jl1.put("text",end+"로 책 반납일자가 연장 되었습니다.");
        }

        jl1.put("type","plain_text");
        jl1.put("emoji",true);

        JSONObject jo1 = new JSONObject();
        jo1.put("type","section");
        jo1.put("text",jl1);
        blocks.add(jo1);


        return blocks;
    }
}
