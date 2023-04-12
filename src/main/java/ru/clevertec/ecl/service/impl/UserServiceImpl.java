package ru.clevertec.ecl.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.clevertec.ecl.dto.read.UserReadDto;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.repository.entity.User;
import ru.clevertec.ecl.service.UserService;
import ru.clevertec.ecl.service.mappers.api.UserMapper;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository repository;
    private final UserMapper mapper;


    @Override
    public UserReadDto findById(Long id) {
        return mapper.entityToReadDto(repository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Override
    public Page<UserReadDto> findAll(Pageable pageable) {
        Page<User> page = repository.findAll(pageable);

        return page.map(mapper::entityToReadDto);
    }
}
