package ru.clevertec.ecl.repository.api;

import ru.clevertec.ecl.repository.entity.Tag;

public interface ITagRepository extends IRepository<Tag>{
    boolean exists(Tag entity);
}
