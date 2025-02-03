package com.wzhang.proto.marvel.sdk.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * image POJO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ImageDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	@Schema(description = "path of the image")
	private String path;

	@Schema(description = "extension of the image")
	private String extension;
}
