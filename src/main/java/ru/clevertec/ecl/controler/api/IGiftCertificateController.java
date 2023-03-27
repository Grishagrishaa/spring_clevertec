package ru.clevertec.ecl.controler.api;

import jakarta.validation.constraints.Max;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.clevertec.ecl.dto.create.GiftCertificateCreateDto;
import ru.clevertec.ecl.dto.read.GiftCertificateReadDto;

import java.util.List;

public interface IGiftCertificateController extends IController<GiftCertificateCreateDto, GiftCertificateReadDto>  {
    @GetMapping("/filtered")
    ResponseEntity<List<GiftCertificateReadDto>> getAllFiltered(@RequestParam(required = false, defaultValue = "1", name = "page") Integer page,
                                                                @RequestParam(required = false, defaultValue = "5", name = "size") Integer size,

                                                                @RequestParam(required = false, defaultValue = "", name = "tagName")@Max(10) String tagName,
                                                                @RequestParam(required = false, defaultValue = "", name = "certificateName")@Max(20) String certificateName,
                                                                @RequestParam(required = false, defaultValue = "", name = "description")@Max(40) String description);
}
