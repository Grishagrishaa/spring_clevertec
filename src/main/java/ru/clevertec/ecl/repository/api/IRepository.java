package ru.clevertec.ecl.repository.api;

import ru.clevertec.ecl.controler.pagination.Pageable;
import ru.clevertec.ecl.exceptions.IncorrectParameterException;

import java.util.List;

public interface IRepository <E>{

    E create(E entity) throws IncorrectParameterException;

    E get(Long id) throws IncorrectParameterException;

    List<E> getAll(Pageable pageable);

    E update(E updateDataEntity);

    void deleteById(Long id);
}
