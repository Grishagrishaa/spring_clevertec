package ru.clevertec.ecl.integration.controler;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.clevertec.ecl.dto.create.OrderDetails;
import ru.clevertec.ecl.integration.BaseIntegrationTest;
import ru.clevertec.ecl.repository.entity.Order;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@RequiredArgsConstructor
class OrderControllerIntegrationTest extends BaseIntegrationTest {

    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    @Value("${app.orderController.path}")
    private String path;

    @Test
    @SneakyThrows
    void findAllByUserIdShouldReturnCorrectSizePage() {

        int expectedSize = 1;

        mockMvc.perform(get(String.format("/%s/{userId}", path), LAST_ENTITY_ID)
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
    void findByUserIdAndOrderIdShouldReturnCorrectOrder() {

        mockMvc.perform(get(String.format("/%s/{userId}/order_id/{orderId}", path), LAST_ENTITY_ID, LAST_ENTITY_ID)
                        .contentType(MediaType.APPLICATION_JSON_VALUE))

                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),

                        jsonPath("$." + camelToSnake(Order.Fields.id)).value(LAST_ENTITY_ID),
                        jsonPath("$." + camelToSnake(Order.Fields.createDate)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(Order.Fields.updateDate)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(Order.OrderFields.user) + ".id").value(LAST_ENTITY_ID),
                        jsonPath("$." + camelToSnake(Order.OrderFields.cost)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(Order.OrderFields.giftCertificate)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(Order.OrderFields.purchaseTime)).isNotEmpty()
                );
    }

    @Test
    @SneakyThrows
    void makeOrder() {

        long entityId = LAST_ENTITY_ID - 1;
        String orderDetails = objectMapper
                .writeValueAsString(OrderDetails.of(entityId, entityId));

        mockMvc.perform(post(String.format("/%s", path))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(orderDetails))

                .andExpectAll(
                        status().isCreated(),
                        content().contentType(MediaType.APPLICATION_JSON),

                        jsonPath("$." + camelToSnake(Order.Fields.id)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(Order.Fields.createDate)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(Order.Fields.updateDate)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(Order.OrderFields.user) + ".id").value(entityId),
                        jsonPath("$." + camelToSnake(Order.OrderFields.cost)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(Order.OrderFields.giftCertificate)).isNotEmpty(),
                        jsonPath("$." + camelToSnake(Order.OrderFields.purchaseTime)).isNotEmpty()
                );
    }
}