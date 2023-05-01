package ru.clevertec.ecl.integration.controler;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.ecl.dto.read.UserReadDto;
import ru.clevertec.ecl.integration.BaseIntegrationTest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class UserControllerIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;

    @Value("${app.userController.path}")
    private String path;

    @Test
    void findById() {
    }

    @Test
    void findAll() {
    }

    @Test
    @SneakyThrows
    void findByIdShouldReturnCorrectReadDto() {

        mockMvc.perform(get(String.format("/%s/{id}", path), LAST_ENTITY_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),

                        jsonPath("$." + camelToSnake(UserReadDto.Fields.id)).value(LAST_ENTITY_ID),
                        jsonPath("$." + camelToSnake(UserReadDto.Fields.createDate)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(UserReadDto.Fields.updateDate)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(UserReadDto.Fields.mail)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(UserReadDto.Fields.nick)).isNotEmpty()
                        );

    }

    @Test
    @SneakyThrows
    void findAllByPageableShouldReturnCorrectSizePage() {

        int expectedSize = 5;

        mockMvc.perform(get(String.format("/%s", path))
                        .param("page", "0")
                        .param("size", Integer.toString(expectedSize))
                        .contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.content").isNotEmpty(),
                        jsonPath("$.content.length()").value(expectedSize)
                );

    }
}