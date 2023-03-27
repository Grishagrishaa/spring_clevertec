package ru.clevertec.ecl.utils;

import lombok.Data;
import ru.clevertec.ecl.dto.create.TagCreateDto;
import ru.clevertec.ecl.dto.read.TagReadDto;
import ru.clevertec.ecl.repository.entity.Tag;

import java.time.LocalDateTime;

import static ru.clevertec.ecl.utils.TestUtils.*;
import static ru.clevertec.ecl.utils.TestUtils.getRandomInt;

@Data
public class TagTestBuilder {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String name;

    public TagTestBuilder() {
        this.id = 1L;
        this.createdDate = LocalDateTime.MIN;
        this.updatedDate = LocalDateTime.MIN;
        this.name = "Default";
    }

    public static TagTestBuilder randomValues(){
        TagTestBuilder tagTestBuilder = new TagTestBuilder();

        tagTestBuilder.setId(getRandomLong());
        tagTestBuilder.setCreatedDate(LocalDateTime.now());
        tagTestBuilder.setUpdatedDate(LocalDateTime.now());
        tagTestBuilder.setName(getRandomString());

        return tagTestBuilder;
    }

    public static TagCreateDto createDto(Tag tag){
        return TagCreateDto.builder()
                .name(tag.getName())
                .build();
    }

    public static TagReadDto readDto(Tag tag){
        return TagReadDto.builder()
                .id(tag.getId())
                .createDate(tag.getCreateDate())
                .updateDate(tag.getUpdateDate())
                .name(tag.getName())
                .build();
    }

    public Tag build(){
        return Tag.builder()
                .id(id)
                .createDate(createdDate)
                .updateDate(updatedDate)
                .name(name)
                .build();

    }

}
