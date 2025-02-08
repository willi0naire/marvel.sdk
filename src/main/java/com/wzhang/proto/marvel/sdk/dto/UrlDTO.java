package com.wzhang.proto.marvel.sdk.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

/**
 * URL POJO
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UrlDTO implements Serializable {
	private static final long serialVersionUID = 1L;

	/* type of the url */
	private String type;

	/* the url */
	private String url;
}
