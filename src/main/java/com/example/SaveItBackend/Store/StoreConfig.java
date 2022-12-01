package com.example.SaveItBackend.Store;

import com.example.SaveItBackend.Order.Order;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Configuration
public class StoreConfig {

    public byte[] imageData(String imageURL) throws IOException {
        BufferedImage bImage = ImageIO.read(new URL(imageURL));
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write( bImage, "jpg", bos);
        return bos.toByteArray();
    }


    @Bean
    CommandLineRunner storeCommandLineRunner(StoreRepository repository) throws IOException {

        return args -> {
            Store Starbucks = new Store(
                    "Starbucks",
                    "info@starbucks.com",
                    "Thier Galerie, Westenhellweg 102-106",
                    "https://www.google.com/maps/place/Starbucks/@51.5140214,7.458997,15z/data=!4m5!3m4!1s0x0:0x518690fb56f0bbca!8m2!3d51.5140214!4d7.458997",
                    1500.7,
                    LocalTime.of(21, 30),
                    LocalTime.of(23, 45),
                    23,
                    "Seattle-based coffeehouse chain known for its signature roasts, light bites and WiFi availability. But among these early practitioners there was one exception, a genius of vast intellect and prodigious memory who came to be known as the Invoker. In his youth, the precocious wizard mastered not four, not five, not even seven incantations: He could command no fewer than ten spells, and cast them instantly. Many more he learned but found useless, and would practice once then purge from his mind forever, to make room for more practical invocations. One such spell was the Sempiternal Cantrap—a longevity spell of such power that those who cast it in the world's first days are among us still (unless they have been crushed to atoms).\n",
                    imageData("https://www.bundesverband-systemgastronomie.de/files/public/start/Logo_Banner/Starbucks.jpg")
            );


            repository.saveAll(
                    List.of(Starbucks)
            );

        };
    }
}
