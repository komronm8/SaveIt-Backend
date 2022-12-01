package com.example.SaveItBackend.Store;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
