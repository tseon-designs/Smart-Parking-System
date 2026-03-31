package com.smartparking.infrastructure.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Explicit JPA configuration to ensure Spring Boot scans entities
 * and repositories from the correct infrastructure packages.
 *
 * Note: With @SpringBootApplication scanning from com.smartparking.sps,
 * we need to explicitly declare these paths.
 */
@Configuration
@EntityScan(basePackages = "com.smartparking.infrastructure.persistence.entity")
@EnableJpaRepositories(basePackages = "com.smartparking.infrastructure.persistence.repository")
public class DatabaseConfig {
    // JPA auto-configuration picks up application.properties settings.
    // This bean simply ensures entity and repository base packages are registered.
}