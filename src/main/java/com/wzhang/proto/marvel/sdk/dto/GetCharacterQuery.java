package com.wzhang.proto.marvel.sdk.dto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import com.wzhang.proto.marvel.sdk.enumeration.SortDirection;
import com.wzhang.proto.marvel.sdk.util.HashingUtil;
import com.wzhang.proto.marvel.sdk.util.StringUtil;

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

	/* name of the character */
	private String name;

	/* characters that the name starts with */
	private String nameStartsWith;

	/* modified start day (inclusive) */
	private String modifiedSinceDateStr;

	/* issue of the comic that the character belongs to */
	private Integer comics;

	/* issue of the series that the character belongs to */
	private Integer series;

	/* issue of the event that the character belongs to */
	private Integer events;

	/* issue of the story that the character belongs to */
	private Integer stories;

	/* list of sort by params */
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

	public Map<String, String> toParamsMap() {
		final Map<String, String> params = new HashMap<>();
		params.put("limit", String.valueOf(getLimit()));
		params.put("offset", String.valueOf(getOffset()));

		if (StringUtil.isNotEmpty(name))
			params.put("name", name);

		if (StringUtil.isNotEmpty(nameStartsWith))
			params.put("nameStartsWith", nameStartsWith);

		if (StringUtil.isNotEmpty(modifiedSinceDateStr))
			params.put("modifiedSince", modifiedSinceDateStr);

		if (null != comics)
			params.put("comics", String.valueOf(comics));

		if (null != series)
			params.put("series", String.valueOf(series));

		if (null != events)
			params.put("events", String.valueOf(events));

		if (null != stories)
			params.put("stories", String.valueOf(stories));

		if (null != sortBys && !sortBys.isEmpty())
			params.put("orderBy",
					sortBys.stream().filter(Objects::nonNull).map(s -> String.format("%s%s",
							SortDirection.DESC.equals(s.getSortDirection()) ? "-" : "", s.getOrderBy().getFieldName()))
							.collect(Collectors.joining(",")));
		return params;
	}

	public int getHash() {
		return HashingUtil.getHashCode(this, toParamsMap().toString());
	}
}
