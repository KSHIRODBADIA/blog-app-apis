package com.kshirod.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kshirod.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

}
