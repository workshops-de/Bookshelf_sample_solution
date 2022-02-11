package de.workshops.bookshelf;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles({"prod"})
class MailSenderTest {

    @Autowired
    JavaMailSender javaMailSender;

    @Test
    void mailsSenderAvailable() {
        assertThat(javaMailSender).isNotNull();
        var host = ((JavaMailSenderImpl) javaMailSender).getHost();

        assertThat(host).isEqualTo("prod.example.com");
    }
}
