package ru.clevertec.ecl.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.read.UserReadDto;

public interface UserService {

    UserReadDto findById(Long id);

    Page<UserReadDto> findAll(Pageable pageable);

}
