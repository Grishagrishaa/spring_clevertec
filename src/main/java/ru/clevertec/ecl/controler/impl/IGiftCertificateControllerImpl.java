package ru.clevertec.ecl.controler.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.clevertec.ecl.controler.api.IGiftCertificateController;
import ru.clevertec.ecl.controler.pagination.FilteredPageRequest;
import ru.clevertec.ecl.service.api.IService;
import ru.clevertec.ecl.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.read.GiftCertificateReadDto;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("${app.giftController.path}")
public class IGiftCertificateControllerImpl implements IGiftCertificateController {
    private final IService<GiftCertificateCreateDto, GiftCertificateReadDto> service;

    public IGiftCertificateControllerImpl(IService<GiftCertificateCreateDto, GiftCertificateReadDto> service) {
        this.service = service;
    }


    @Override
    public ResponseEntity<GiftCertificateReadDto> create(GiftCertificateCreateDto createDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.create(createDto));
    }

    @Override
    public ResponseEntity<GiftCertificateReadDto> get(Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(service.get(id));
    }

    @Override
    @GetMapping
    public ResponseEntity<List<GiftCertificateReadDto>> getAll(Integer page, Integer size){
        return getAllFiltered(page, size, "", "", "");
    }

    @Override
    public ResponseEntity<List<GiftCertificateReadDto>> getAllFiltered(Integer page, Integer size, String tagName, String certificateName, String description) {
        return ResponseEntity.status(HttpStatus.OK).body(service.getAll(FilteredPageRequest.of(page, size, tagName, certificateName, description)));
    }

    @Override
    public ResponseEntity<GiftCertificateReadDto> update(GiftCertificateCreateDto updateDataCreateDto, Long id, LocalDateTime updateDate) {
        return ResponseEntity.status(HttpStatus.OK).body(service.update(updateDataCreateDto, id, updateDate));
    }

    @Override
    public ResponseEntity<GiftCertificateReadDto> delete(Long id, LocalDateTime updateDate) {
        service.deleteById(id);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
