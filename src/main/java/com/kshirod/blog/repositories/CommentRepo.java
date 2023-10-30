package com.kshirod.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kshirod.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
