package com.kshirod.blog.payloads;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CommentDto {

	private Integer commentId;

	private String content;
}
