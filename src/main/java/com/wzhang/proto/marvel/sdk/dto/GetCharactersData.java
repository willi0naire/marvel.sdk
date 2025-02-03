package com.wzhang.proto.marvel.sdk.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * the character data POJO containing the characters data and pagination info
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCharactersData implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "page number")
	private int offset;

	@Schema(description = "page size")
	private int limit;

	@Schema(description = "total")
	private int total;

	@Schema(description = "page count")
	private int count;

	@ArraySchema(schema = @Schema(implementation = CharacterDTO.class, description = "characters"))
	private List<CharacterDTO> results;
}
