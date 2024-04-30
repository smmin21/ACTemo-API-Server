package actemo.server.config;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.messaging.FirebaseMessaging;
import jakarta.annotation.PostConstruct;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;

//@Configuration
//public class FirebaseConfiguration {
//
////    @Value("${firebase.config.path}")
////    private String firebaseConfigPath;
//    private FirebaseApp firebaseApp;
//
//    @PostConstruct
//    public FirebaseApp initialize() throws IOException {
//        FileInputStream serviceAccount = new FileInputStream("src/main/resources/firebase/actemo-flutter-firebase.json");
//
//        FirebaseOptions options = FirebaseOptions.builder()
//                .setCredentials(GoogleCredentials.fromStream(serviceAccount))
//                .build();
//
//        firebaseApp = FirebaseApp.initializeApp(options);
//        return firebaseApp;
//    }
//
//    @Bean
//    public FirebaseAuth firebaseAuth() {
//        return FirebaseAuth.getInstance(firebaseApp);
//    }
//
//    @Bean
//    public FirebaseMessaging firebaseMessaging() {
//        return FirebaseMessaging.getInstance(firebaseApp);
//    }
//}