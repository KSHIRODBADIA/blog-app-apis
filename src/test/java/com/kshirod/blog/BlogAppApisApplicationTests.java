package com.kshirod.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kshirod.blog.repositories.UserRepo;

@SpringBootTest
class BlogAppApisApplicationTests {
	
	@Autowired
	private UserRepo userRepo;

	@Test
	void contextLoads() {
		//reflectionAPI
		String className = this.userRepo.getClass().getName();
		String packName = this.userRepo.getClass().getPackageName();
		System.out.println(className);
		System.out.println(packName);
	}

}
