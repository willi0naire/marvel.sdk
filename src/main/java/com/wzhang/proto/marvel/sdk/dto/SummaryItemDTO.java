package com.wzhang.proto.marvel.sdk.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wzhang.proto.marvel.sdk.enumeration.StorySummaryType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * summary item POJO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SummaryItemDTO implements Serializable {

	private static final long serialVersionUID = 1L;

	@Schema(description = "uri of the resource")
	private String resourceURI;

	@Schema(description = "name of the resource")
	private String name;

	@Schema(description = "type of the story, used by stories")
	private StorySummaryType type;
}
