package com.example.booking.service;

import com.example.booking.constants.Constants;
import org.apache.log4j.Logger;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PasswordEncoder {
    private static final Logger LOGGER = Logger.getLogger(PasswordEncoder.class);

    public static String encode(String password) {
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance(Constants.PASSWORD_ENCRYPTION_ALGORITHM);
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error(String.format("Algorithm %s is not supported", Constants.PASSWORD_ENCRYPTION_ALGORITHM));
            throw new IllegalArgumentException();
        }

        byte[] encodedBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        return IntStream
                .range(0, encodedBytes.length)
                .mapToObj(i -> encodedBytes[i])
                .map(b -> String.format("%02x", b))
                .collect(Collectors.joining());
    }
}
