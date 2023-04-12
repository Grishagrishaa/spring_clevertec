package ru.clevertec.ecl.controler;

import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.controler.pagination.filter.GiftCertificateFilter;
import ru.clevertec.ecl.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.read.GiftCertificateReadDto;
import ru.clevertec.ecl.service.GiftCertificateService;

@RestController
@RequestMapping(value = "${app.giftController.path}", produces = MediaType.APPLICATION_JSON_VALUE)
public class GiftCertificateController {

    private final GiftCertificateService service;

    public GiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }


    @PostMapping
    public ResponseEntity<GiftCertificateReadDto> create(@RequestBody GiftCertificateCreateDto createDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(createDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GiftCertificateReadDto> findById(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findById(id));
    }

    @GetMapping()
    public ResponseEntity<Page<GiftCertificateReadDto>> findAllByPageableAndGiftCertificateFilter(@PageableDefault Pageable pageable,
                                                                                                  @Valid GiftCertificateFilter filter) {
        return ResponseEntity.status(HttpStatus.OK).body(service.findAllByGiftCertificateFilter(pageable, filter));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GiftCertificateReadDto> updateById(@RequestBody GiftCertificateCreateDto updateDataCreateDto, @PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(updateDataCreateDto, id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
