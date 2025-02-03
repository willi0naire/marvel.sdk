package com.wzhang.proto.marvel.sdk.dto;

import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * summary POJO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SummaryDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "total count")
	private int available;

	@Schema(description = "returned count")
	private int returned;

	@Schema(description = "uri of the collection")
	private String collectionURI;

	@ArraySchema(schema = @Schema(implementation = SummaryItemDTO.class, description = "list of summary items"))
	private List<SummaryItemDTO> items;
}
