package com.example.SaveItBackend.Store;


import com.example.SaveItBackend.Order.Order;
import com.example.SaveItBackend.Security.JwtUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(path = "api/v1/store")
public class StoreController {

    private final StoreService storeService;

    private final JwtUtils jwtUtils;

    @Autowired
    public StoreController(StoreService storeService, JwtUtils jwtUtils){
        this.storeService = storeService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping(path = "/all")
    public List<Store> getStores(){
        return storeService.getStores();
    }

    @GetMapping(path = "{store_id}")
    public Store getStoreWithId(@PathVariable("store_id") Long store_id){
        return storeService.getStore(store_id);
    }

    @GetMapping
    public Store getStoreWithJWT(@RequestHeader ("AUTHORIZATION") String authHeader){
        String email = jwtUtils.extractUserName(authHeader.substring(7));
        return storeService.getStoreByEmail(email);
    }

    @GetMapping(path = "/{store_id}/logoImage")
    public ResponseEntity<Resource> getLogoImage(@PathVariable("store_id") Long store_id){
        byte[] data = storeService.getStore(store_id).getLogoImage();
        return storeService.getResourceImage(data);
    }

    @GetMapping(path = "/{store_id}/coverImage")
    public ResponseEntity<Resource> getCoverImage(@PathVariable("store_id") Long store_id) {
        byte[] data = storeService.getStore(store_id).getCoverImage();
        return storeService.getResourceImage(data);
    }

    @GetMapping(path = "/logoImage")
    public ResponseEntity<Resource> getLogoImage(@RequestHeader ("AUTHORIZATION") String authHeader){
        String email = jwtUtils.extractUserName(authHeader.substring(7));
        byte[] data = storeService.getStoreByEmail(email).getLogoImage();
        return storeService.getResourceImage(data);
    }

    @GetMapping(path = "/coverImage")
    public ResponseEntity<Resource> getCoverImage(@RequestHeader ("AUTHORIZATION") String authHeader) {
        String email = jwtUtils.extractUserName(authHeader.substring(7));
        byte[] data = storeService.getStoreByEmail(email).getCoverImage();
        return storeService.getResourceImage(data);
    }

    @PostMapping(path = "/registerStore")
    public void registerStore(@RequestBody Store store){
        storeService.addNewStore(store);
    }

    @PostMapping(path = "/uploadLogoImage")
    public void uploadLogoImage(
            @RequestHeader("AUTHORIZATION") String authHeader,
            @RequestBody String base64Image) throws JsonProcessingException {
        String email = jwtUtils.extractUserName(authHeader.substring(7));
        storeService.saveLogoImage(base64Image, email);
    }

    @PostMapping(path = "/uploadCoverImage")
    public void uploadCoverImage(
            @RequestHeader("AUTHORIZATION") String authHeader,
            @RequestBody String base64Image) throws JsonProcessingException {
        String email = jwtUtils.extractUserName(authHeader.substring(7));
        storeService.saveCoverImage(base64Image, email);
    }

    @DeleteMapping(path = "{store_id}")
    public void deleteStore(@PathVariable("store_id") Long store_id){
        storeService.deleteStore(store_id);
    }

    @PutMapping(path = "{store_id}")
    public void updateStore(
            @PathVariable("store_id") Long store_id,
            @RequestParam String email,
            @RequestParam String address){
        storeService.updateStore(store_id, email, address);
    }

    @GetMapping(path = "dayHistory")
    public List<Order> getOrderDayHistory(
            @RequestHeader("AUTHORIZATION") String authHeader,
            @RequestParam(required = false) String date){
        String email = jwtUtils.extractUserName(authHeader.substring(7));
        return storeService.getOrderDayHistory(email, date);
    }


}
