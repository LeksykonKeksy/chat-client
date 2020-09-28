package pl.leksy.krzysztof.chat.client.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PasswordHasher {
    private final static BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hashPassword(String pwd) {
        return encoder.encode(pwd);
    }
}
