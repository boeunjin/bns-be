package com.bns.bookhubservice.entity.json;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;

public class ReturnBlokitBuilder {

    public ArrayList<Object> blockit(String rentalId,String bookTitle, String end){
        ArrayList<Object> blocks =new ArrayList<Object>();
        //Header
        JSONObject ji  =new JSONObject();
        ji.put("emoji",true);
        ji.put("text",bookTitle+" 책을 주인에게 돌려주세요");
        ji.put("type","plain_text");


        JSONObject jo = new JSONObject();
        jo.put("type","header");
        jo.put("text",ji);
        blocks.add(jo);

        //Section
        JSONObject jl1 = new JSONObject();
        jl1.put("text","반납일은 "+end+"입니다, 반납이 완료 되었다면 책 주인은 '반납 완료' 버튼을 눌러주세요");
        jl1.put("type","plain_text");
        jl1.put("emoji",true);

        JSONObject jo1 = new JSONObject();
        jo1.put("type","section");
        jo1.put("text",jl1);
        blocks.add(jo1);

        //Divider
        JSONObject jo3 = new JSONObject();
        jo3.put("type","divider");
        blocks.add(jo3);

        //Section2
        JSONObject jl2 = new JSONObject();
        jl2.put("text","반납일자 1주일 연기를 원하시면 책 주인분이 '연기 하기' 버튼을 눌러주세요");
        jl2.put("type","plain_text");
        jl2.put("emoji",true);

        JSONObject jo2 = new JSONObject();
        jo2.put("type","section");
        jo2.put("text",jl2);
        blocks.add(jo2);

        //Button
        JSONObject jio31 = new JSONObject();
        jio31.put("type","plain_text");
        jio31.put("emoji",true);
        jio31.put("text","반납 완료");


        JSONObject ji31 = new JSONObject();
        ji31.put("type","button");
        ji31.put("text",jio31);
        ji31.put("style","primary");
        ji31.put("value","return success "+rentalId);

        JSONObject jio32 = new JSONObject();
        jio32.put("type","plain_text");
        jio32.put("emoji",true);
        jio32.put("text","연기 하기");

        JSONObject ji32 = new JSONObject();
        ji32.put("type","button");
        ji32.put("text",jio32);
        ji32.put("style","danger");
        ji32.put("value","extend "+rentalId);


        JSONArray jl4 = new JSONArray();
        jl4.add(ji31);
        jl4.add(ji32);

        JSONObject jo4 = new JSONObject();
        jo4.put("type","actions");
        jo4.put("elements",jl4);
        blocks.add(jo4);


        return blocks;
    }
}
