package com.kshirod.blog.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	private Integer categoryId;
	@NotEmpty
	@Size(min = 4, message ="Title must be of 4 charcters !!")
	private String categoryTitle;
	
	@NotEmpty
	@Size(min = 5, message ="Category Desc must be of 4 charcters !!")
	private String categoryDescription;

}
