package ru.clevertec.ecl.controler.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

public interface IController<C, R> {
    @PostMapping
    ResponseEntity<R> create(@RequestBody C createDto);

    @GetMapping("/{id}")
    ResponseEntity<R> get(@PathVariable Long id);

    @GetMapping
    ResponseEntity<List<R>> getAll(@RequestParam(required = false, defaultValue = "1", name = "page") Integer page,
                                   @RequestParam(required = false, defaultValue = "5", name = "size") Integer size);

    @PutMapping("/{id}/updateDate/{updateDate}")
    ResponseEntity<R> update(@RequestBody C updateDataCreateDto, @PathVariable Long id, @PathVariable LocalDateTime updateDate);

    @DeleteMapping("/{id}/updateDate/{updateDate}")
    ResponseEntity delete(@PathVariable Long id, @PathVariable LocalDateTime updateDate);

}
