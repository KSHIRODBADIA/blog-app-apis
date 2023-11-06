package com.kshirod.blog.payloads;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class JwtAuthResponse {
	private String token;
	private String username;
	private UserDto user;
}
