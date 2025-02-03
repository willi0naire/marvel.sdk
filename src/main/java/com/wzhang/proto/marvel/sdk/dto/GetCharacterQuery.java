package com.wzhang.proto.marvel.sdk.dto;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.util.CollectionUtils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import com.wzhang.proto.marvel.sdk.enumeration.SortDirection;
import com.wzhang.proto.marvel.sdk.util.HashingUtil;
import com.wzhang.proto.marvel.sdk.util.StringUtil;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * the query builder used to search characters
 */
@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetCharacterQuery {

	@Schema(description = "name of the character")
	private String name;

	@Schema(description = "characters that the name starts with")
	private String nameStartsWith;

	@Schema(description = "modified start day (inclusive)")
	private String modifiedSinceDateStr;

	@Schema(description = "issue of the comic that the character belongs to")
	private Integer comics;

	@Schema(description = "issue of the series that the character belongs to")
	private Integer series;

	@Schema(description = "issue of the event that the character belongs to")
	private Integer events;

	@Schema(description = "issue of the story that the character belongs to")
	private Integer stories;

	@ArraySchema(schema = @Schema(implementation = GetCharactersSortBy.class, description = "list of sort by params"))
	private List<GetCharactersSortBy> sortBys;

	@Getter(AccessLevel.NONE)
	private Integer limit;

	@Getter(AccessLevel.NONE)
	private Integer offset;

	public Integer getLimit() {
		return null != limit ? limit : 10;
	}

	public Integer getOffset() {
		return null != offset ? offset : 0;
	}

	public MultiValueMap<String, String> toParamsMap() {
		final MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("limit", String.valueOf(getLimit()));
		params.add("offset", String.valueOf(getOffset()));

		if (StringUtil.isNotEmpty(name))
			params.add("name", name);

		if (StringUtil.isNotEmpty(nameStartsWith))
			params.add("nameStartsWith", nameStartsWith);

		if (StringUtil.isNotEmpty(modifiedSinceDateStr))
			params.add("modifiedSince", modifiedSinceDateStr);

		if (null != comics)
			params.add("comics", String.valueOf(comics));

		if (null != series)
			params.add("series", String.valueOf(series));

		if (null != events)
			params.add("events", String.valueOf(events));

		if (null != stories)
			params.add("stories", String.valueOf(stories));

		if (!CollectionUtils.isEmpty(sortBys))
			params.add("orderBy",
					sortBys.stream().filter(Objects::nonNull).map(s -> String.format("%s%s",
							SortDirection.DESC.equals(s.getSortDirection()) ? "-" : "", s.getOrderBy().getFieldName()))
							.collect(Collectors.joining(",")));
		return params;
	}

	public int getHash() {
		return HashingUtil.getHashCode(this, toParamsMap().toString());
	}
}
