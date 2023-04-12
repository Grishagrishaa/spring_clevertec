package ru.clevertec.ecl.testUtils.builder.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ecl.dto.create.UserCreateDto;
import ru.clevertec.ecl.dto.read.UserReadDto;
import ru.clevertec.ecl.repository.entity.User;
import ru.clevertec.ecl.testUtils.builder.TestBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static ru.clevertec.ecl.testUtils.TestUtils.getRandomLong;
import static ru.clevertec.ecl.testUtils.TestUtils.getRandomString;

@With
@Data
@AllArgsConstructor
@NoArgsConstructor(staticName = "with")
public class UserTestBuilder implements TestBuilder<User> {

    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String mail;
    private String nick;


    public static UserTestBuilder defaultValues(){
        UserTestBuilder userTestBuilder = new UserTestBuilder();

        userTestBuilder.setId(1L);
        userTestBuilder.setCreatedDate(LocalDateTime.MAX);
        userTestBuilder.setUpdatedDate(LocalDateTime.MIN);
        userTestBuilder.setMail("ABOBA@");
        userTestBuilder.setNick("ABOBA");

        return userTestBuilder;
    }

    public static UserTestBuilder randomValues(){
        UserTestBuilder userTestBuilder = new UserTestBuilder();

        userTestBuilder.setId(getRandomLong());
        userTestBuilder.setCreatedDate(LocalDateTime.now());
        userTestBuilder.setUpdatedDate(LocalDateTime.now());
        userTestBuilder.setMail(getRandomString());
        userTestBuilder.setNick(getRandomString());

        return userTestBuilder;
    }

    public static UserCreateDto createDto(User user){
        UserCreateDto userCreateDto = new UserCreateDto();
        userCreateDto.setMail(user.getMail());
        userCreateDto.setNick(user.getNick());

        return userCreateDto;
    }

    public static UserReadDto readDto(User user){
        return UserReadDto.builder()
                .id(user.getId())
                .createDate(user.getCreateDate())
                .updateDate(user.getUpdateDate())
                .mail(user.getMail())
                .nick(user.getNick())
                .build();
    }

    @Override
    public User build(){
        User user = new User();
        user.setId(id);
        user.setCreateDate(Instant.ofEpochSecond(createdDate.toEpochSecond(ZoneOffset.UTC)));
        user.setUpdateDate(Instant.ofEpochSecond(updatedDate.toEpochSecond(ZoneOffset.UTC)));
        user.setMail(mail);
        user.setNick(nick);

        return user;
    }

}
