package com.example.SaveItBackend.Store;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Configuration
public class StoreConfig {

    public byte[] getDataWithURL(String imageURL) throws IOException {
        BufferedImage bImage = ImageIO.read(new URL(imageURL));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write( bImage, "png", bos);
        return bos.toByteArray();
    }

    public byte[] getDataWithPath(String path) throws IOException{
        File file = new File(path);
        byte[] picInBytes = new byte[(int) file.length()];
        FileInputStream fileInputStream = new FileInputStream(file);
        fileInputStream.read(picInBytes);
        fileInputStream.close();
        return picInBytes;
    }

    @Bean
    CommandLineRunner storeCommandLineRunner(StoreRepository repository){

        return args -> {
            Store Starbucks = new Store(
                    "Starbucks",
                    "info@starbucks.com",
                    "проспект Aль-Фараби 77",
                    "https://goo.gl/maps/hB1Mhc5mvyyyNbhGA",
                    LocalDate.now(),
                    2000.0,
                    2500.0,
                    LocalTime.of(23, 55),
                    LocalTime.of(23, 56),
                    23,
                    23,
                    "bakery,grocery,meal",
                    new BCryptPasswordEncoder().encode("klia0494"),
                    "Seattle-based coffeehouse chain known for its signature roasts, light bites and WiFi availability.",
                    getDataWithURL("https://www.bundesverband-systemgastronomie.de/files/public/start/Logo_Banner/Starbucks.jpg"),
                    getDataWithURL("https://images.unsplash.com/photo-1503481766315-7a586b20f66d?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2053&q=80")
            );
            Store Coffeeboom = new Store(
                    "Coffeeboom",
                    "info@coffeeboom.com",
                    "проспект Aль-Фараби 140а/3",
                    "https://goo.gl/maps/BEa4kQZNSMuAshWB8",
                    LocalDate.now(),
                    1500.0,
                    2000.0,
                    LocalTime.of(21, 30),
                    LocalTime.of(22, 00),
                    5,
                    5,
                    "bakery,meal",
                    new BCryptPasswordEncoder().encode("klia0494"),
                    "Lorem ipsum, dolor sit amet consectetur adipisicing elit. Ratione odit eum minus amet molestiae animi aliquid quisquam iusto harum dolorum qui repellendus laborum cupiditate, voluptatem magni modi perspiciatis a libero doloremque quam ab! Asperiores voluptatum, quaerat cumque nulla quod accusantium.",
                    getDataWithURL("https://coffeeboom.kz/storage/app/uploads/public/5f7/e76/7e1/5f7e767e17f3d169479667.png"),
                    getDataWithURL("https://images.unsplash.com/photo-1623334044303-241021148842?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80")
                    );
            Store LaTartine = new Store(
                    "La Tartine",
                    "info@la_tartine.com",
                    "ул. Розыбакиева, 263",
                    "https://goo.gl/maps/GT48kmETmFGzgRmN6",
                    LocalDate.now(),
                    2000.0,
                    2300.0,
                    LocalTime.of(20, 30),
                    LocalTime.of(21, 00),
                    3,
                    3,
                    "bakery,meal",
                    new BCryptPasswordEncoder().encode("klia0494"),
                    "Lorem ipsum, dolor sit amet consectetur adipisicing elit. Ratione odit eum minus amet molestiae animi aliquid quisquam iusto harum dolorum qui repellendus laborum cupiditate, voluptatem magni modi perspiciatis a libero doloremque quam ab! Asperiores voluptatum, quaerat cumque nulla quod accusantium.",
                    getDataWithURL("https://www.latartine.kz/assets/images/promo.jpg"),
                    getDataWithURL("https://images.unsplash.com/photo-1569864358642-9d1684040f43?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80")
            );
            Store Paul = new Store(
                    "Paul",
                    "info@paul.com",
                    "проспект Aль-Фараби 77",
                    "https://goo.gl/maps/7kk6Z26w6dxhoUpCA",
                    LocalDate.now(),
                    1500.0,
                    2100.0,
                    LocalTime.of(21, 45),
                    LocalTime.of(22, 00),
                    1,
                    1,
                    "bakery",
                    new BCryptPasswordEncoder().encode("klia0494"),
                    "Lorem ipsum, dolor sit amet consectetur adipisicing elit. Ratione odit eum minus amet molestiae animi aliquid quisquam iusto harum dolorum qui repellendus laborum cupiditate, voluptatem magni modi perspiciatis a libero doloremque quam ab! Asperiores voluptatum, quaerat cumque nulla quod accusantium.",
                    getDataWithURL("https://upload.wikimedia.org/wikipedia/commons/0/0f/Logo_Paul.png"),
                    getDataWithURL("https://images.unsplash.com/photo-1568254183919-78a4f43a2877?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2069&q=80")
            );
            Store Costa = new Store(
                    "Costa Coffee",
                    "info@costa.com",
                    "проспект Aль-Фараби 140",
                    "https://goo.gl/maps/gCmBKA7sHQEd4rqQ7",
                    LocalDate.now(),
                    1500.0,
                    1650.0,
                    LocalTime.of(22, 45),
                    LocalTime.of(23, 00),
                    1,
                    1,
                    "bakery",
                    new BCryptPasswordEncoder().encode("klia0494"),
                    "Lorem ipsum, dolor sit amet consectetur adipisicing elit. Ratione odit eum minus amet molestiae animi aliquid quisquam iusto harum dolorum qui repellendus laborum cupiditate, voluptatem magni modi perspiciatis a libero doloremque quam ab! Asperiores voluptatum, quaerat cumque nulla quod accusantium.",
                    getDataWithURL("https://upload.wikimedia.org/wikipedia/de/thumb/5/52/Costa_Coffee.svg/1200px-Costa_Coffee.svg.png"),
                    getDataWithURL("https://images.unsplash.com/photo-1447933601403-0c6688de566e?ixlib=rb-4.0.3&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1661&q=80")
            );

            repository.saveAll(
                    List.of(Starbucks, Coffeeboom, LaTartine, Costa, Paul)
            );

        };
    }
}
