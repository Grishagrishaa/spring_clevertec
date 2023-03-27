package ru.clevertec.ecl.controler.pagination;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.clevertec.ecl.controler.pagination.filter.GiftCertificateIFilter;

@Data
@Builder
@AllArgsConstructor
public class FilteredPageRequest implements Pageable{
	private final int pageNumber;
	private final int pageSize;

	private GiftCertificateIFilter filter;


	public static FilteredPageRequest of(int pageNumber, int size) {
		return of(pageNumber, size, "", "", "");
	}

	public static FilteredPageRequest of(int pageNumber, int size, String tagName, String certificateName, String certificateDescription) {
		return new FilteredPageRequest(pageNumber, size, GiftCertificateIFilter.of(tagName, certificateName, certificateDescription));
	}

	public static FilteredPageRequest ofSize(int pageSize) {
		return FilteredPageRequest.of(0, pageSize);
	}

	@Override
	public long getOffset() {
		return (long) (pageNumber -1) * pageSize;
	}

	@Override
	public long getLimit() {
		return (long) pageNumber * pageSize ;
	}
}
