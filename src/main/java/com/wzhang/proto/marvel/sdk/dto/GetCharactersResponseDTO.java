package com.wzhang.proto.marvel.sdk.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * the get characters v1 response POJO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCharactersResponseDTO implements Serializable {
	private static final long serialVersionUID = -3179286202528622086L;

	/* code of the search */
	private String code;

	/* status of the search */
	private String status;

	/* copyright */
	private String copyright;

	/* attribution text */
	private String attributionText;

	/* attribution html */
	private String attributionHTML;

	/* string used to identify the query */
	private String etag;

	/* the query data */
	private GetCharactersData data;
}
