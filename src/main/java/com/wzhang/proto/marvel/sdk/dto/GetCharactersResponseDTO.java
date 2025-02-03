package com.wzhang.proto.marvel.sdk.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * the get characters v1 response POJO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCharactersResponseDTO implements Serializable {
	private static final long serialVersionUID = -3179286202528622086L;

	@Schema(description = "code of the search")
	private String code;

	@Schema(description = "status of the search")
	private String status;

	@Schema(description = "copyright")
	private String copyright;

	@Schema(description = "attribution text")
	private String attributionText;

	@Schema(description = "attribution html")
	private String attributionHTML;

	@Schema(description = "etag that points to the same search")
	private String etag;

	@Schema(description = "the character data")
	private GetCharactersData data;
}
