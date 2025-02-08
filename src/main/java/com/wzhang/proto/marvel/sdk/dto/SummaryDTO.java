package com.wzhang.proto.marvel.sdk.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * summary POJO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SummaryDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/* total count */
	private int available;

	/* returned count */
	private int returned;

	/* uri of the collection */
	private String collectionURI;

	/* list of summary items */
	private List<SummaryItemDTO> items;
}
