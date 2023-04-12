package ru.clevertec.ecl.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import ru.clevertec.ecl.dto.read.UserReadDto;
import ru.clevertec.ecl.repository.UserRepository;
import ru.clevertec.ecl.repository.entity.User;
import ru.clevertec.ecl.service.mappers.api.TagMapper;
import ru.clevertec.ecl.service.mappers.api.UserMapper;
import ru.clevertec.ecl.testUtils.builder.impl.UserTestBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Spy
    private UserMapper mapper = Mappers.getMapper(UserMapper.class);
    @InjectMocks
    private UserServiceImpl userService;



    private static final Long ID = 9L;

    @ParameterizedTest
    @MethodSource("provideUserAndPageable")
    void checkFindAllByPageableShouldReturnExpectedResult(List<User> users, Pageable pageable) {
        Page<User> fromDb = new PageImpl<>(users);

        doReturn(fromDb)
                .when(userRepository).findAll(pageable);


        Page<UserReadDto> expected = fromDb.map(mapper::entityToReadDto);
        Page<UserReadDto> actual = userService.findAll(pageable);

        verify(userRepository).findAll(pageable);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkFindByIdShouldReturnExpectedResult() {
        User user = UserTestBuilder.defaultValues().build();
        doReturn(Optional.of(user)).when(userRepository).findById(ID);

        UserReadDto expected = UserTestBuilder.readDto(user);

        UserReadDto actual = userService.findById(ID);

        verify(userRepository).findById(ID);
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void checkFindByIdShouldEntityNotFoundException() {
        doReturn(Optional.empty()).when(userRepository).findById(ID);

        assertThatThrownBy(() -> userService.findById(ID))
                .isInstanceOf(EntityNotFoundException.class);
    }

    private static Stream<Arguments> provideUserAndPageable() {
        return Stream.of(
                Arguments.of(List.of(UserTestBuilder.defaultValues().build()), PageRequest.of(0, 2)),
                Arguments.of(List.of(UserTestBuilder.defaultValues().build()), PageRequest.of(0, 2)),
                Arguments.of(List.of(UserTestBuilder.defaultValues().build()), PageRequest.of(0, 2)),
                Arguments.of(List.of(UserTestBuilder.defaultValues().build()), PageRequest.of(0, 2)),
                Arguments.of(List.of(UserTestBuilder.defaultValues().build()), PageRequest.of(0, 2))
        );
    }
}