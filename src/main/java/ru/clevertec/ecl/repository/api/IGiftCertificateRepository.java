package ru.clevertec.ecl.repository.api;


import ru.clevertec.ecl.repository.entity.GiftCertificate;
import ru.clevertec.ecl.repository.entity.Tag;

import java.util.List;

public interface IGiftCertificateRepository extends IRepository<GiftCertificate> {
    void addTagsAssociation(long certificateId, List<Long> tagIds);

    List<Tag> getAssociatedTags(long certificateId);
}
