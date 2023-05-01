package ru.clevertec.ecl.integration.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.ecl.dto.read.TagReadDto;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.testUtils.builder.impl.TagTestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class TagControllerIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Value("${app.tagController.path}")
    private String path;

    @Test
    @SneakyThrows
    void createShouldReturnReadDto() {

        String actual = objectMapper
                .writeValueAsString(TagTestBuilder.createDto( TagTestBuilder.defaultValues().build() ));

        mockMvc.perform(post(String.format("/%s", path))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(actual))

                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),

                        jsonPath("$." + camelToSnake(TagReadDto.Fields.id)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(TagReadDto.Fields.createDate)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(TagReadDto.Fields.updateDate)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(TagReadDto.Fields.name)).isNotEmpty()
                );

    }

    @Test
    @SneakyThrows
    void findByIdShouldReturnCorrectReadDto() {

        mockMvc.perform(get(String.format("/%s/{id}", path), LAST_ENTITY_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),

                        jsonPath("$." + camelToSnake(TagReadDto.Fields.id)).value(LAST_ENTITY_ID),
                        jsonPath("$." + camelToSnake(TagReadDto.Fields.createDate)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(TagReadDto.Fields.updateDate)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(TagReadDto.Fields.name)).isNotEmpty()
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

    @Test
    @SneakyThrows
    void findMostPopularByUserIdShouldReturnTagReadDto() {

        mockMvc.perform(get(String.format("/%s/popular/{userId}", path), LAST_ENTITY_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),

                        jsonPath("$." + camelToSnake(TagReadDto.Fields.id)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(TagReadDto.Fields.createDate)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(TagReadDto.Fields.updateDate)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(TagReadDto.Fields.name)).isNotEmpty()
                );

    }

    @Test
    @SneakyThrows
    void updateByIdShouldReturnCorrectUpdatedReadDto(){

        String actual = objectMapper
                .writeValueAsString(TagTestBuilder.createDto( TagTestBuilder.defaultValues().build() ));

        mockMvc.perform(put(String.format("/%s/{id}", path), LAST_ENTITY_ID)
                        .content(actual)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),

                        jsonPath("$." + camelToSnake(TagReadDto.Fields.id)).value(LAST_ENTITY_ID),
                        jsonPath("$." + camelToSnake(TagReadDto.Fields.createDate)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(TagReadDto.Fields.updateDate)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(TagReadDto.Fields.name)).isNotEmpty()

                );

    }

    @Test
    @SneakyThrows
    void deleteByIdShouldReturn2xxStatus() {

        mockMvc.perform(delete(String.format("/%s/{id}", path), LAST_ENTITY_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpect(status().is2xxSuccessful());

    }
}