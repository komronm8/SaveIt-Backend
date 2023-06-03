package com.example.SaveItBackend.Store;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@EnableScheduling
public class StoreBoxScheduler {

    private final StoreRepository storeRepository;

    public StoreBoxScheduler(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    @Transactional
    @Scheduled(cron = "0 0 0 * * *", zone = "Asia/Almaty" )
    public void updateBoxAmounts(){
        for(Store store: storeRepository.findAll()){
            store.setCurrentBoxesAmount(store.getDefaultBoxesAmount());
            storeRepository.save(store);
        }
    }

}
