package ru.clevertec.ecl.repository.util;

import jakarta.persistence.criteria.Join;
import lombok.experimental.UtilityClass;
import org.springframework.data.jpa.domain.Specification;
import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;

import java.util.List;

@UtilityClass
public class CertificateSpec {

    public static Specification<GiftCertificate> like(String fieldName, String fieldValue){
        return (root, query, criteriaBuilder)
                -> {
            if(fieldValue == null){
                return criteriaBuilder.isTrue(criteriaBuilder.literal(true));
            }

            return criteriaBuilder.like(root.get(fieldName), "%"+fieldValue+"%");
        };
    }

    public static Specification<GiftCertificate> tagNameIn(List<String> tagNames) {
        return (root, query, builder) -> {
            if (tagNames == null){
                return builder.isTrue(builder.literal(true));
            }
            Join<Tag, GiftCertificate> join = root.join("tags");
            return join.get("name").in(tagNames);
        };
    }
}
