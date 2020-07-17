package com.example.demo.repository;

import org.springframework.data.repository.CrudRepository;

import com.example.demo.domain.User;

/*
 * User Repository 接口
 * 
 * */
public interface UserRepository extends CrudRepository<User,Long>{

}
