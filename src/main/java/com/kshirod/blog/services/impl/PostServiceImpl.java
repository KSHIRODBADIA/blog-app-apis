package com.kshirod.blog.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.kshirod.blog.entities.*;
import com.kshirod.blog.exceptions.ResourceNotFoundException;
import com.kshirod.blog.payloads.PostDto;
import com.kshirod.blog.payloads.PostResponse;
import com.kshirod.blog.payloads.UserDto;
import com.kshirod.blog.repositories.CategoryRepo;
import com.kshirod.blog.repositories.PostRepo;
import com.kshirod.blog.repositories.UserRepo;
import com.kshirod.blog.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private UserRepo userRepo;

	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User", "Id", userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(()-> new ResourceNotFoundException("Category","Id", categoryId));
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post save = this.postRepo.save(post);
		return this.modelMapper.map(save, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		Post save = this.postRepo.save(post);
		return this.modelMapper.map(save, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId)
				.orElseThrow(()-> new ResourceNotFoundException("Post", "postId", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber,Integer pageSize, String sortBy, String sortDir) {
		Sort sort = null;
		if(sortDir.equalsIgnoreCase("asc")) {
			sort = Sort.by(sortBy).ascending();
		} else {
			sort = Sort.by(sortBy).descending();
		}
		Pageable p = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(p);
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream().map(post->this.modelMapper
				.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(()->new ResourceNotFoundException("Post","Id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		Category cat = this.categoryRepo.findById(categoryId).orElseThrow(()-> 
		new ResourceNotFoundException("Category", "Id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(cat);
		List<PostDto> postDtos = posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->
		new ResourceNotFoundException("User", "userId", userId));
		
		List<Post> userPosts = this.postRepo.findByUser(user);
		//below stream api mapping is very important
		List<PostDto> userPostDtos = userPosts.stream().map((post)->
		this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return userPostDtos;
	}

	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.postRepo.findByTitleContaining(keyword);
		return posts.stream().map((post)->
		this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
	}

}
