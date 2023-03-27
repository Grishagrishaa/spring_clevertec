package ru.clevertec.ecl.service.api;

import jakarta.validation.Valid;
import ru.clevertec.ecl.controler.pagination.Pageable;
import ru.clevertec.ecl.exceptions.IncorrectParameterException;

import java.time.LocalDateTime;
import java.util.List;

public interface IService<C, R> {//C -> CreateDto, R -> ReadDto
    R create(@Valid C createDto) throws IncorrectParameterException;

    R get(Long id) throws IncorrectParameterException;

    List<R> getAll(Pageable pageable);

    R update(@Valid C updateDataEntity, Long id, LocalDateTime updateDate) throws IncorrectParameterException;

    void deleteById(Long id);
}
