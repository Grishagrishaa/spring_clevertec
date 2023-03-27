package ru.clevertec.ecl.controler.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.controler.api.ITagController;
import ru.clevertec.ecl.controler.pagination.FilteredPageRequest;
import ru.clevertec.ecl.service.api.IService;
import ru.clevertec.ecl.dto.create.TagCreateDto;
import ru.clevertec.ecl.dto.read.TagReadDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("${app.tagController.path}")
public class ITagControllerImpl implements ITagController {
    private final IService<TagCreateDto, TagReadDto> service;

    public ITagControllerImpl(IService<TagCreateDto, TagReadDto> service) {
        this.service = service;
    }

    @Override
    public ResponseEntity<TagReadDto> create(TagCreateDto createDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(createDto));
    }

    @Override
    public ResponseEntity<TagReadDto> get(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.get(id));
    }

    @Override
    public ResponseEntity<List<TagReadDto>> getAll(Integer page, Integer size) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll(FilteredPageRequest.of(page, size)));
    }

    @Override
    public ResponseEntity<TagReadDto> update(TagCreateDto updateDataCreateDto, Long id, LocalDateTime updateDate) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(updateDataCreateDto, id, updateDate));
    }

    @Override
    public ResponseEntity<TagReadDto> delete(Long id, LocalDateTime updateDate) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
