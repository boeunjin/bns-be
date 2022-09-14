package com.bns.bookhubservice.service.slack;

import com.bns.bookhubservice.dto.BookDto;
import com.bns.bookhubservice.entity.json.CompleteBlokitBuilder;
import com.bns.bookhubservice.entity.json.ReturnBlokitBuilder;
import com.bns.bookhubservice.service.BookService;
import lombok.extern.slf4j.Slf4j;
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
import java.time.LocalDate;
import java.util.ArrayList;

@Slf4j
@Service("rental-return")
public class RentalReturnService {
    @Value("${slack.bot_token}")
    private String bot_token;
    @Autowired
    private BookService bookService;


    public ReturnBlokitBuilder returnBlokitBuilder = new ReturnBlokitBuilder();

    public void returnMessage(Long rentalId, String channel_id, Long bookId, LocalDate end) {

        URL url1 = null;
        try {
            BookDto bookDto = bookService.getBookById(bookId);
            String bookTitle = bookDto.getTitle();
            url1 = new URL("https://slack.com/api/chat.postMessage");
            HttpURLConnection http1 = (HttpURLConnection)url1.openConnection();
            http1.setRequestMethod("POST");
            http1.setDoOutput(true);
            http1.setRequestProperty("Accept", "application/json");
            http1.setRequestProperty("Authorization", "Bearer "+bot_token);
            http1.setRequestProperty("Content-Type", "application/json");

            String data = messageForm(rentalId,channel_id, bookTitle, end);
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

    public String messageForm(Long rentalId, String channel_id, String bookTitle, LocalDate end){
        JSONObject message1 = new JSONObject();
        //blockit builder 설정
        ArrayList<Object> blocks = returnBlokitBuilder.blockit(String.valueOf(rentalId),bookTitle, String.valueOf(end));

        message1.put("channel", channel_id);
        message1.put("username", "Book Hub");
        message1.put("blocks", blocks);
        System.out.println(message1.toString());

        return message1.toString();
    }
}
