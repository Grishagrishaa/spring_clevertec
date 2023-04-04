package ru.clevertec.ecl.testUtils.builder.impl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import ru.clevertec.ecl.service.dto.create.TagCreateDto;
import ru.clevertec.ecl.service.dto.read.TagReadDto;
import ru.clevertec.ecl.repository.entity.Tag;
import ru.clevertec.ecl.testUtils.builder.TestBuilder;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import static ru.clevertec.ecl.testUtils.TestUtils.*;

@With
@Data
@AllArgsConstructor
@NoArgsConstructor(staticName = "with")
public class TagTestBuilder implements TestBuilder<Tag> {
    private Long id;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String name;

    public static TagTestBuilder randomValues(){
        TagTestBuilder tagTestBuilder = new TagTestBuilder();

        tagTestBuilder.setId(getRandomLong());
        tagTestBuilder.setCreatedDate(LocalDateTime.now());
        tagTestBuilder.setUpdatedDate(LocalDateTime.now());
        tagTestBuilder.setName(getRandomString());

        return tagTestBuilder;
    }

    public static TagCreateDto createDto(Tag tag){
        TagCreateDto tagCreateDto = new TagCreateDto();
        tagCreateDto.setName(tag.getName());

        return tagCreateDto;
    }

    public static TagReadDto readDto(Tag tag){
        return TagReadDto.builder()
                .id(tag.getId())
                .createDate(tag.getCreateDate())
                .updateDate(tag.getUpdateDate())
                .name(tag.getName())
                .build();
    }

    @Override
    public Tag build(){
        Tag tag = new Tag();
        tag.setId(id);
        tag.setCreateDate(Instant.ofEpochSecond(createdDate.toEpochSecond(ZoneOffset.UTC)));
        tag.setUpdateDate(Instant.ofEpochSecond(updatedDate.toEpochSecond(ZoneOffset.UTC)));
        tag.setName(name);
        return tag;
    }

}
