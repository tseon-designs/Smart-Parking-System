package com.smartparking.infrastructure.external.camera;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class LicensePlateRecognizer {

    public String recognizePlate(byte[] imageData) {
        // Simulate license plate recognition
        return "ABC-" + (new Random().nextInt(9000) + 1000);
    }
}