package ru.clevertec.ecl.controler;

import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.service.dto.create.TagCreateDto;
import ru.clevertec.ecl.service.dto.read.TagReadDto;
import ru.clevertec.ecl.service.TagService;

import java.util.List;

@RestController
@RequestMapping(value = "${app.tagController.path}", produces = MediaType.APPLICATION_JSON_VALUE)
public class TagController {
    private final TagService service;

    public TagController(TagService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<TagReadDto> create(@RequestBody TagCreateDto createDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(createDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TagReadDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }


    @GetMapping
    public ResponseEntity<List<TagReadDto>> findAllByPageable(@PageableDefault Pageable pageable) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAll(pageable));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TagReadDto> updateById(@RequestBody TagCreateDto updateDataCreateDto, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(updateDataCreateDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
