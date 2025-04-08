package com.grannsnack.GrannSnack.Controller;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.io.InputStream;

@Configuration
public class FireBaseConfig {
    @Bean
    FirebaseApp firebaseApp() throws IOException {
        ClassPathResource resource = new ClassPathResource("grannsnack-firebase-adminsdk-fbsvc-b7ba9ab0a3.json");
        InputStream serviceAccount = resource.getInputStream();

        FirebaseOptions options = FirebaseOptions.builder()
                .setProjectId("grannsnack")
                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                .setDatabaseUrl("https://console.firebase.google.com/project/grannsnack/database/grannsnack-default-rtdb/data/~2F")
                .build();

        return FirebaseApp.initializeApp(options);
    }
}
