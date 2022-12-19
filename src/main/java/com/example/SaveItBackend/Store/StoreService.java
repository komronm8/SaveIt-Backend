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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
    public void addNewStore(Store store, String base64LogoImage, String base64CoverImage) throws JsonProcessingException {
        Optional<Store> storeOptional = storeRepository.
                findStoreByEmail(store.getEmail());
        if(storeOptional.isPresent()){
            throw new IllegalStateException("Email taken");
        }
        store.setLogoImage(getByteArray(base64LogoImage));
        store.setCoverImage(getByteArray(base64CoverImage));
        store.setPassword(new BCryptPasswordEncoder().encode(store.getPassword()));
        storeRepository.save(store);
    }

    public void deleteStore(Long store_id) {
        boolean exists = storeRepository.existsById(store_id);
        if(!exists){
            throw new IllegalStateException("Customer with id " + store_id + " does not exist");
        }
        storeRepository.deleteById(store_id);
    }

    @Transactional
    public void updateStore(Store store){
        Store str = getStore(store.getId());
        storeRepository.save(store);
        store.setCoverImage(str.getCoverImage());
        store.setLogoImage(str.getLogoImage());
        store.setPassword(str.getPassword());
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
}
