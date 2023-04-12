package ru.clevertec.ecl.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tags", schema = "clev", uniqueConstraints = {
        @UniqueConstraint(columnNames = "name", name = "tag_name_unique")
})
@Data @EqualsAndHashCode(callSuper = true)
@AllArgsConstructor @NoArgsConstructor
public class Tag extends BaseEntity{

    private String name;

}
