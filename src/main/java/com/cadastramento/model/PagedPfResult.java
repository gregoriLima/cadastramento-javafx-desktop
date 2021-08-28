package com.cadastramento.model;

import java.util.ArrayList;

public class PagedPfResult {

	public ArrayList<PessoaFisica> content;
	public Pageable pageable;
	public boolean last;
	public int totalPages;
	public int totalElements;
	public long size;
	public int number;
	public Sort sort;
	public boolean first;
	public int numberOfElements;

	public PagedPfResult(ArrayList<PessoaFisica> content, Pageable pageable, boolean last, int totalPages,
						 int totalElements, long size, int number, Sort sort, boolean first, int numberOfElements, boolean empty) {
		super();
		this.content = content;
		this.pageable = pageable;
		this.last = last;
		this.totalPages = totalPages;
		this.totalElements = totalElements;
		this.size = size;
		this.number = number;
		this.sort = sort;
		this.first = first;
		this.numberOfElements = numberOfElements;
		this.empty = empty;
	}

	public boolean empty;

	public ArrayList<PessoaFisica> getContent() {
		return content;
	}

	public void setContent(ArrayList<PessoaFisica> content) {
		this.content = content;
	}

	public Pageable getPageable() {
		return pageable;
	}

	public void setPageable(Pageable pageable) {
		this.pageable = pageable;
	}

	public boolean isLast() {
		return last;
	}

	public void setLast(boolean last) {
		this.last = last;
	}

	public int getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(int totalPages) {
		this.totalPages = totalPages;
	}

	public int getTotalElements() {
		return totalElements;
	}

	public void setTotalElements(int totalElements) {
		this.totalElements = totalElements;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public boolean isFirst() {
		return first;
	}

	public void setFirst(boolean first) {
		this.first = first;
	}

	public int getNumberOfElements() {
		return numberOfElements;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

}

class Sort {
	public boolean unsorted;
	public boolean sorted;
	public boolean empty;

	public Sort(boolean unsorted, boolean sorted, boolean empty) {
		super();
		this.unsorted = unsorted;
		this.sorted = sorted;
		this.empty = empty;
	}
}

class Pageable {
	public Sort sort;
	public int offset;
	public long pageSize;

	public Pageable(Sort sort, int offset, long pageSize, int pageNumber, boolean paged, boolean unpaged) {
		super();
		this.sort = sort;
		this.offset = offset;
		this.pageSize = pageSize;
		this.pageNumber = pageNumber;
		this.paged = paged;
		this.unpaged = unpaged;
	}

	public int pageNumber;
	public boolean paged;
	public boolean unpaged;

	public Sort getSort() {
		return sort;
	}

	public void setSort(Sort sort) {
		this.sort = sort;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public long getPageSize() {
		return pageSize;
	}

	public void setPageSize(long pageSize) {
		this.pageSize = pageSize;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public boolean isPaged() {
		return paged;
	}

	public void setPaged(boolean paged) {
		this.paged = paged;
	}

	public boolean isUnpaged() {
		return unpaged;
	}

	public void setUnpaged(boolean unpaged) {
		this.unpaged = unpaged;
	}
}