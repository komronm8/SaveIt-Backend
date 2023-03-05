package com.example.SaveItBackend.Store;

import com.example.SaveItBackend.Order.Order;
import com.example.SaveItBackend.Order.OrderRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.concurrent.ScheduledFuture;


@Service
public class StoreService {

    private final StoreRepository storeRepository;

    private final OrderRepository orderRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository, OrderRepository orderRepository) {
        this.storeRepository = storeRepository;
        this.orderRepository = orderRepository;
    }

    public List<Store> getStores(){
        return storeRepository.findAll();
    }

    public byte[] getLogoImage(Long store_id){
        return storeRepository.findById(store_id).get().getLogoImage();
    }

    public byte[] getCoverImage(Long store_id){ return storeRepository.findById(store_id).get().getCoverImage();}

    @Transactional
    public void addNewStore(Store store){
        Optional<Store> storeOptional = storeRepository.
                findStoreByEmail(store.getEmail());
        if(storeOptional.isPresent()){
            throw new IllegalStateException("Email taken");
        }
        store.setPassword(new BCryptPasswordEncoder().encode(store.getPassword()));
        store.setCreationDate(LocalDate.now());
        store.setCurrentBoxesAmount(store.getDefaultBoxesAmount());
        storeRepository.save(store);
    }

    public void deleteStore(String email) {
        Store store = getStoreByEmail(email);
        storeRepository.deleteById(store.getId());
    }

    @Transactional
    public void updateStore(Store givenStore, String email){
        Store currentStore = getStoreByEmail(email);
        currentStore.setName(givenStore.getName());
        currentStore.setEmail(givenStore.getEmail());
        currentStore.setAddress(givenStore.getAddress());
        currentStore.setAddressURL(givenStore.getAddressURL());
        currentStore.setPrice(givenStore.getPrice());
        currentStore.setPriceWithoutDiscount(givenStore.getPriceWithoutDiscount());
        currentStore.setCollectionTimeStart(givenStore.getCollectionTimeStart());
        currentStore.setCollectionTimeEnd(givenStore.getCollectionTimeEnd());
        currentStore.setDefaultBoxesAmount(givenStore.getDefaultBoxesAmount());
        currentStore.setCurrentBoxesAmount(givenStore.getCurrentBoxesAmount());
        currentStore.setTags(givenStore.getTags());
        currentStore.setDescription(givenStore.getDescription());
    }

    public Store getStore(Long store_id) {
        return storeRepository.findById(store_id)
                .orElseThrow(() -> new IllegalStateException("Store does not exist"));
    }

    @Transactional
    public Store getStoreByEmail(String email){
        return storeRepository.findStoreByEmail(email)
                .orElseThrow(() -> new IllegalStateException("Store does not exist"));
    }

    @Transactional
    public void saveLogoImage(String base64Image, String email) throws JsonProcessingException {
        Store store = getStoreByEmail(email);
        store.setLogoImage(getByteArray(base64Image));
    }

    @Transactional
    public void saveCoverImage(String base64Image, String email) throws JsonProcessingException {
        Store store = getStoreByEmail(email);
        store.setCoverImage(getByteArray(base64Image));
    }

    public byte[] getByteArray(String encodedImage) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(encodedImage);
        encodedImage = jsonNode.get("base64Image").asText();
        String encodeImg = encodedImage.split(",")[1];
        return Base64.getDecoder().decode(encodeImg.getBytes(StandardCharsets.UTF_8));
    }

    public ResponseEntity<Resource> getResourceImage(byte[] data){
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("fileImage.png")
                                .build().toString())
                .body(resource);
    }

    @Transactional
    public List<Order> getOrderDayHistory(String email, String date) {
        Store store = getStoreByEmail(email);
        if (date.equals("")) {
            return orderRepository.findStoreOrders(
                    store.getId(),
                    LocalDate.now().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT))
            );
        } else {
            return orderRepository.findStoreOrders(store.getId(), date);
        }
    }

    @Transactional
    public Double calculatePageAmount(String email) {
        Store store = getStoreByEmail(email);
        double days = (double) ChronoUnit.DAYS.between(store.getCreationDate(), LocalDate.now().plusDays(1));
        return Math.ceil(days/10);
    }

    @Transactional
    public String calculateHistory(String email, Integer page) {
        Store store = getStoreByEmail(email);
        LocalDate cDate = LocalDate.now();
        if(page <= 0){
            throw new IllegalStateException("Page cannot be negative");
        }
        else{
            return getDayHistoryContainer(store, cDate.minusDays((10L * page) - 1),
                    cDate.plusDays(1 ).minusDays((10L * page) - 10));
        }
    }

    public String getDayHistoryContainer(Store store, LocalDate start, LocalDate end){
        StringBuilder result = new StringBuilder();
        Long id = store.getId();
        for(LocalDate date = start; date.isBefore(end); date = date.plusDays(1)){
            String convertedDate = date.format(DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT));
            result.append(date).append(",").append(orderRepository.getDayHistoryDetails(id, convertedDate)).append("; ");
        }
        return result.toString();
    }

}
