package com.bns.bookhubservice.service;

import com.bns.bookhubservice.entity.json.BlokitBuilder;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Service("groupChatService")
public class GroupChatService {
    @Value("${slack.bot_id}")
    private String id;
    @Value("${slack.user_token}")
    private String token;
    @Value("${slack.bot_token}")
    private String bot_token;

    public BlokitBuilder blokitBuilder = new BlokitBuilder();
    @Autowired
    private MemberService memberService;



    public void groupChat(String owner, String myself , String bookName, String author, String image){
        URL url = null;

        String users = owner+"%2C"+myself+"%2C"+id;
        String bearer_token = "Bearer "+token;
        String bearer_bot_token = "Bearer "+bot_token;



        try {
            String place = memberService.getMemberLocation(myself);
            //API 연결
            String uri = "https://slack.com/api/conversations.open?users="+users+"&pretty=1";
            url = new URL(uri);
            HttpURLConnection http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.setRequestProperty("Accept", "application/json");
            http.setRequestProperty("Authorization", bearer_token);

            //결과값 읽기
            Charset charset = Charset.forName("UTF-8");
            BufferedReader in = new BufferedReader(new InputStreamReader(http.getInputStream(),charset));
            String inputLine;
            StringBuffer response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            System.out.println("조회결과 : " + response.toString());

            //String to Json
            String jsonStr = response.toString();
            JSONParser parser = new JSONParser();
            Object obj = parser.parse(jsonStr);
            JSONObject jsonObj = (JSONObject) obj;

            //생성된 그룹 채널 이름
            Object channel = jsonObj.get("channel");
            JSONObject channel_json = (JSONObject) channel;
            String channel_id = String.valueOf(channel_json.get("id"));
            http.disconnect();

            //메세지 보내기
            URL url1 = new URL("https://slack.com/api/chat.postMessage");
            HttpURLConnection http1 = (HttpURLConnection)url1.openConnection();
            http1.setRequestMethod("POST");
            http1.setDoOutput(true);
            http1.setRequestProperty("Accept", "application/json");
            http1.setRequestProperty("Authorization", "Bearer xoxb-3392925850004-3925308931427-dCvYfpGYXemjs7k25xKqiS8K");
            http1.setRequestProperty("Content-Type", "application/json");

            String data = messageForm(channel_id,owner,myself,bookName,author,image, place);
            byte[] out = data.getBytes(StandardCharsets.UTF_8);

            OutputStream stream = http1.getOutputStream();
            stream.write(out);

            System.out.println(http1.getResponseCode() + " " + http1.getResponseMessage());
            http1.disconnect();


        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e){
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String messageForm(String channel_id, String owner,String myself , String bookName, String author, String image, String place){
        JSONObject message = new JSONObject();
        //blockit builder 설정
        ArrayList<Object> blocks = blokitBuilder.blockit(owner, myself, bookName, author, image, place);

        message.put("channel", channel_id);
        message.put("username", "Book Hub");
        message.put("blocks", blocks);
        System.out.println(message.toString());

        return message.toString();
    }



}
