package com.wzhang.proto.marvel.sdk.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * the character POJO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacterDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/* id of the character */
	private String id;

	/* name of the character */
	private String name;

	/* description of the character */
	private String description;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
	private LocalDateTime modified;

	/* thumbnail of the character */
	private ImageDTO thumbnail;

	/* resourceURI of the character */
	private String resourceURI;

	/* list of comics related to the character */
	private SummaryDTO comics;

	/* list of events related to the character */
	private SummaryDTO events;

	/* list of series related to the character */
	private SummaryDTO series;

	/* list of stories related to the character */
	private SummaryDTO stories;

	/* list of URLs */
	private List<UrlDTO> urls;

}
