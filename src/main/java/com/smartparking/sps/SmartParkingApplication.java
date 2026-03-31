package com.smartparking.sps;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Entry point for the Smart Parking System.
 *
 * scanBasePackages ensures all components under com.smartparking
 * (domain, application, infrastructure, presentation) are picked up,
 * even though this class lives in the com.smartparking.sps sub-package.
 */
@SpringBootApplication(scanBasePackages = "com.smartparking")
public class SmartParkingApplication {
    public static void main(String[] args) {
        SpringApplication.run(SmartParkingApplication.class, args);
    }
}