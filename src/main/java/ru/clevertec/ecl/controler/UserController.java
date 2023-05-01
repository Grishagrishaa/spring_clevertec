package ru.clevertec.ecl.controler;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.dto.read.UserReadDto;
import ru.clevertec.ecl.service.UserService;

@RestController
@RequestMapping(value = "${app.userController.path}", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @GetMapping("/{id}")
    public ResponseEntity<UserReadDto> findById(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @GetMapping
    public ResponseEntity<Page<UserReadDto>> findAll(@PageableDefault Pageable pageable){
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllByPageable(pageable));
    }

}
