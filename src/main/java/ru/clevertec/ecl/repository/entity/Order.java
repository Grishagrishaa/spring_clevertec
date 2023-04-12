package ru.clevertec.ecl.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import ru.clevertec.ecl.repository.listeners.OrderListener;

import java.time.Instant;

@Entity
@Table(name = "orders", schema = "clev", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"gift_certificate_id", "user_id"}, name = "order_unique")
})
@EntityListeners(OrderListener.class)
@Builder(setterPrefix = "set")
@Data @EqualsAndHashCode(callSuper = true)
@AllArgsConstructor @NoArgsConstructor
public class Order extends BaseEntity{

    private Double cost;
    private Instant purchaseTime;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "gift_certificate_id")
    private GiftCertificate giftCertificate;

}
