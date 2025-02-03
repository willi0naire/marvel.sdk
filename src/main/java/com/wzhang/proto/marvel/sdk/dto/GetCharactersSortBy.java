package com.wzhang.proto.marvel.sdk.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.wzhang.proto.marvel.sdk.enumeration.GetCharactersSortField;
import com.wzhang.proto.marvel.sdk.enumeration.SortDirection;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * the sort by POJO used by the {@link GetCharacterQuery}
 */
@Data
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetCharactersSortBy {

	@Schema(description = "sort by field used for search")
	private GetCharactersSortField orderBy;

	@Schema(description = "sort direction")
	private SortDirection sortDirection;
}
