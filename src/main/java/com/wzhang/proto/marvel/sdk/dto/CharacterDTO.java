package com.wzhang.proto.marvel.sdk.dto;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * the character POJO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CharacterDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "id of the character", example = "1")
	private String id;

	@Schema(description = "name of the character", example = "Spider-Man")
	private String name;

	@Schema(description = "description of the character", example = "this is a desc")
	private String description;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ssZ")
	private LocalDateTime modified;

	@Schema(description = "thumbnail of the character")
	private ImageDTO thumbnail;

	@Schema(description = "resourceURI of the character")
	private String resourceURI;

	@Schema(description = "list of comics related to the character")
	private SummaryDTO comics;

	@Schema(description = "list of events related to the character")
	private SummaryDTO events;

	@Schema(description = "list of series related to the character")
	private SummaryDTO series;

	@Schema(description = "list of stories related to the character")
	private SummaryDTO stories;

	@ArraySchema(schema = @Schema(implementation = UrlDTO.class, description = "urls"))
	private List<UrlDTO> urls;

}
