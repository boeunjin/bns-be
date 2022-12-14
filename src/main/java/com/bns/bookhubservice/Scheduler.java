package com.bns.bookhubservice;

import com.bns.bookhubservice.entity.RentalEntity;
import com.bns.bookhubservice.service.RentalService;
import com.bns.bookhubservice.service.slack.RentalReturnService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class Scheduler {
    @Autowired
    private RentalService rentalService;
    @Autowired
    private RentalReturnService rentalReturnService;

    //@Scheduled(fixedRate = 1000000) //10초
    @Scheduled(cron = "0 0 9 * * *", zone = "Asia/Seoul") //UST 23:00는 한국시간 08:00
    public void cronJobSch(){
        try {
            List<RentalEntity> result = rentalService.getRentalDate(LocalDate.now());

            for (RentalEntity rentalEntity : result) {
                    if (!rentalEntity.isReturn()){
                        rentalReturnService.returnMessage(rentalEntity.getId(),rentalEntity.getChannelId(),rentalEntity.getBookId(), rentalEntity.getEndDate(),rentalEntity.getMemberId());
                    }
                }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
