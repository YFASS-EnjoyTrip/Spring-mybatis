package com.ssafy.enjoytrip.global.dto;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FileDto {
	private String fileId;
	private String type;
	private String size;
	private String saveFolder;
	private String originalFile;
	private String saveFile;
}
