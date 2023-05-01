package ru.clevertec.ecl.testUtils.builder.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import org.mapstruct.factory.Mappers;
import ru.clevertec.ecl.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.create.TagCreateDto;
import ru.clevertec.ecl.dto.read.GiftCertificateReadDto;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Order;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.repository.entity.User;
import ru.clevertec.ecl.service.mappers.api.TagMapper;
import ru.clevertec.ecl.testUtils.builder.TestBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import static ru.clevertec.ecl.testUtils.TestUtils.*;

@With
@Data
@AllArgsConstructor
@NoArgsConstructor(staticName = "with")
public class OrderTestBuilder implements TestBuilder<Order> {
    private Long id;
    private Instant createdDate;
    private Instant updatedDate;
    private Double cost;
    private Instant purchaseTime;
    private User user;
    private GiftCertificate giftCertificate;


    public static OrderTestBuilder defaultValues(){
        OrderTestBuilder orderTestBuilder = new OrderTestBuilder();

        orderTestBuilder.setId(null);
        orderTestBuilder.setCreatedDate(null);
        orderTestBuilder.setUpdatedDate(null);
        orderTestBuilder.setCost(20.0);
        orderTestBuilder.setPurchaseTime(Instant.MAX);
        orderTestBuilder.setUser(UserTestBuilder.defaultValues().withId(8L).build());
        orderTestBuilder.setGiftCertificate(GiftCertificateTestBuilder.defaultValues().withId(8L).build());

        return orderTestBuilder;
    }


    public static OrderTestBuilder randomValues(){
        OrderTestBuilder orderTestBuilder = new OrderTestBuilder();

        orderTestBuilder.setId(getRandomLong());
        orderTestBuilder.setCreatedDate(Instant.now());
        orderTestBuilder.setUpdatedDate(Instant.now());
        orderTestBuilder.setCost(getRandomDouble());
        orderTestBuilder.setPurchaseTime(Instant.MAX);
        orderTestBuilder.setUser(UserTestBuilder.randomValues().build());
        orderTestBuilder.setGiftCertificate(GiftCertificateTestBuilder.randomValues().build());

        return orderTestBuilder;
    }

    @Override
    public Order build(){
        Order order = new Order();
        order.setId(id);
        order.setCreateDate(createdDate);
        order.setUpdateDate(updatedDate);
        order.setCost(cost);
        order.setPurchaseTime(purchaseTime);
        order.setUser(user);
        order.setGiftCertificate(giftCertificate);
        return order;
    }

}