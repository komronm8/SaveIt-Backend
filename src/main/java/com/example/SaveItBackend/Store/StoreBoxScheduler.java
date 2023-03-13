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
    @Scheduled(cron = "0 0 0 * * *" )
    public void updateBoxAmounts(){
        Integer numberOfStores = storeRepository.getNumberOfStores();
        for(int i = 1; i <= numberOfStores; i++){
            Store store = storeRepository.getReferenceById((long) i);
            store.setCurrentBoxesAmount(store.getDefaultBoxesAmount());
        }
    }

}
