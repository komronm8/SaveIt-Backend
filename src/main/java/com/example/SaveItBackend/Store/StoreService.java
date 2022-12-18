package com.example.SaveItBackend.Store;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class StoreService {

    private final StoreRepository storeRepository;

    @Autowired
    public StoreService(StoreRepository storeRepository) {
        this.storeRepository = storeRepository;
    }

    public List<Store> getStores(){
        return storeRepository.findAll();
    }

    public byte[] getLogoImage(Long store_id){
        return storeRepository.findById(store_id).get().getLogoImage();
    }

    public byte[] getCoverImage(Long store_id){ return storeRepository.findById(store_id).get().getCoverImage();}

    @Transactional
    public void addNewStore(Store store) {
        Optional<Store> storeOptional = storeRepository.
                findStoreByEmail(store.getEmail());
        if(storeOptional.isPresent()){
            throw new IllegalStateException("Email taken");
        }
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
    public void updateStore(Long store_id, String email, String address) {
        Store store = storeRepository.findById(store_id)
                .orElseThrow(() -> new IllegalStateException("Store does not exist"));

        if(email != null && email.length() > 0 && !Objects.equals(store.getEmail(), email)){
            Optional<Store> storeOptional = storeRepository.findStoreByEmail(email);
            if(storeOptional.isPresent()){
                throw new IllegalStateException("Email taken");
            }
            store.setEmail(email);
        }

        if(address != null && address.length() > 0 && !Objects.equals(store.getAddress(), address)){
            store.setAddress(address);
        }

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

}
