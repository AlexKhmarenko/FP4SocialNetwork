package com.danit.socialnetwork.model.dto;

import lombok.Data;
import lombok.extern.log4j.Log4j2;

@Log4j2
@Data
public class JwtRequest {
	private String username;
	private String password;
	private String rememberMe;

}