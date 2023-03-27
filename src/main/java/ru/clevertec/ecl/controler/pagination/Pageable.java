package ru.clevertec.ecl.controler.pagination;

import ru.clevertec.ecl.controler.pagination.filter.GiftCertificateIFilter;

public interface Pageable {
    GiftCertificateIFilter getFilter();

    int getPageNumber();

    int getPageSize();

    long getOffset();

    long getLimit();
}
