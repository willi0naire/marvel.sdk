package com.wzhang.proto.marvel.sdk.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * the character data POJO containing the characters data and pagination info
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCharactersData implements Serializable {
	private static final long serialVersionUID = 1L;

	/* current page number */
	private int offset;

	/* page size */
	private int limit;

	/* total count of characters found by the query */
	private int total;

	/* page count */
	private int count;

	/* list of characters on the current page */
	private List<CharacterDTO> results;
}
