package ru.clevertec.ecl.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users", schema = "clev", uniqueConstraints = {
        @UniqueConstraint(columnNames = "nick", name = "nick_constraint"),
        @UniqueConstraint(columnNames = "mail", name = "mail_constraint")
})
@Data @EqualsAndHashCode(callSuper = true)
@AllArgsConstructor @NoArgsConstructor
public class User extends BaseEntity{

    private String mail;
    private String nick;

}
