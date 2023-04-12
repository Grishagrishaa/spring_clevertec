package ru.clevertec.ecl.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.clevertec.ecl.repository.entity.Tag;

import java.util.Optional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

    Optional<Tag> findByName(String name);

    @Query(value =  "SELECT tags.id, tags.create_date, tags.update_date, tags.name FROM clev.tags \n" +
                    "INNER JOIN clev.gift_certificates_tags ON tags.id = gift_certificates_tags.tag_id \n" +
                    "INNER JOIN clev.gift_certificates ON gift_certificates_tags.gift_certificate_id  = clev.gift_certificates.id\n" +
                    "INNER JOIN clev.orders ON gift_certificates.id = orders.gift_certificate_id\n" +
                    "INNER JOIN clev.users ON orders.user_id = users.id\n" +
                    "WHERE users.id = :userId " +
                    "GROUP BY tags.id, orders.cost\n" +
                    "ORDER BY SUM(orders.cost) DESC\n" +
                    "FETCH FIRST ROWS ONLY", nativeQuery = true)
    Optional<Tag> findMostPopularWithHighestCostByUserId(@Param("userId") Long userId);

}
