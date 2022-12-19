package com.example.SaveItBackend.Store;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class StoreRequest {

    private Store store;
    private String base64LogoImage;
    private String base64CoverImage;

}
