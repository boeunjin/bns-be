package com.bns.bookhubservice.service.slack;

import com.bns.bookhubservice.dto.BookDto;
import com.bns.bookhubservice.entity.json.MessageBlokitBuilder;
import com.bns.bookhubservice.service.BookService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

@Service("rental_success")
public class RentalSuccessService {

    @Value("${slack.bot_token}")
    private String bot_token;
    @Autowired
    private BookService bookService;

    public MessageBlokitBuilder messageBlokitBuilder = new MessageBlokitBuilder();

    public void successMessage(String channel_id, Long bookId){

        URL url1 = null;
        try {
            BookDto bookDto = bookService.getBookById(bookId);
            url1 = new URL("https://slack.com/api/chat.postMessage");
            HttpURLConnection http1 = (HttpURLConnection)url1.openConnection();
            http1.setRequestMethod("POST");
            http1.setDoOutput(true);
            http1.setRequestProperty("Accept", "application/json");
            http1.setRequestProperty("Authorization", "Bearer "+bot_token);
            http1.setRequestProperty("Content-Type", "application/json");

            String data = messageForm(channel_id, String.valueOf(bookId), bookDto.getTitle());
            byte[] out = data.getBytes(StandardCharsets.UTF_8);

            OutputStream stream = http1.getOutputStream();
            stream.write(out);

            System.out.println(http1.getResponseCode() + " " + http1.getResponseMessage());
            http1.disconnect();

        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (ProtocolException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    public String messageForm(String channel_id, String bookId, String bookTitle){
        JSONObject message = new JSONObject();
        //blockit builder 설정
        ArrayList<Object> blocks = messageBlokitBuilder.blockit(bookId, bookTitle);

        message.put("channel", channel_id);
        message.put("username", "Book Hub");
        message.put("blocks", blocks);
        System.out.println(message.toString());

        return message.toString();
    }


}
