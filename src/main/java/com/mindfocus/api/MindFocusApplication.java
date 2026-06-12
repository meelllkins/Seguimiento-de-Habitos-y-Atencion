package com.mindfocus.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * MindFocus API - Habit tracking and attention analysis.
 * Built with Hexagonal Architecture (Ports & Adapters).
 */
@SpringBootApplication
public class MindFocusApplication {

    public static void main(String[] args) {
        SpringApplication.run(MindFocusApplication.class, args);
    }
}
