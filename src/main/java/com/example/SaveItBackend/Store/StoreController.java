package com.example.SaveItBackend.Store;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeName;
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

    @Autowired
    public StoreController(StoreService storeService){
        this.storeService = storeService;
    }

    @GetMapping
    public List<Store> getStores(){
        return storeService.getStores();
    }

    @GetMapping(path = "{store_id}")
    public Store getStore(@PathVariable("store_id") Long store_id){
        return storeService.getStore(store_id);
    }

    @GetMapping(path = "{store_id}/logoImage")
    public ResponseEntity<Resource> getLogoImage(@PathVariable("store_id") Long store_id) throws IOException {
        byte[] data = storeService.getLogoImage(store_id);
        ByteArrayResource resource = new ByteArrayResource(data);
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .contentLength(resource.contentLength())
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("logoImage.jpg")
                                .build().toString())
                .body(resource);
    }

    @PostMapping
    public void registerStore(@RequestBody Store store){
        storeService.addNewStore(store);
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


}
