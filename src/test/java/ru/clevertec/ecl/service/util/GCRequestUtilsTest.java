package ru.clevertec.ecl.service.util;

import org.junit.jupiter.api.Test;
import ru.clevertec.ecl.controler.pagination.filter.GiftCertificateFilter;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertAll;

class GCRequestUtilsTest {



    @Test
    void shouldReturnJoinRequestIfTagsProvided() {
        GiftCertificateFilter filter = GiftCertificateFilter.of( "da", "da", "da");
        assertThat(GCRequestUtils.getFilteredRequest(filter)).contains(GCRequestUtils.FILTERED_JOIN_REQUEST);
    }

    @Test
    void shouldReturnNotJoinRequestIfTagsIsNotProvided() {
        GiftCertificateFilter filter = GiftCertificateFilter.of( "", "da", "da");
        assertThat(GCRequestUtils.getFilteredRequest(filter)).contains(GCRequestUtils.FILTERED_REQUEST);
    }
}