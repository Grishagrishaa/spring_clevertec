package ru.clevertec.ecl.integration;

import jakarta.transaction.Transactional;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import ru.clevertec.ecl.integration.annotation.IT;

@IT
@Transactional
public abstract class BaseIntegrationTest {

    public static final Long LAST_ENTITY_ID = 9L;

    private static final PostgreSQLContainer<?> container = new PostgreSQLContainer<>("postgres:14.7");

    @BeforeAll
    static void runContainer(){
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry){
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }
}
