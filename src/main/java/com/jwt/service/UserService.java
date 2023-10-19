package com.jwt.service;

import com.jwt.model.User;
import com.jwt.model.UserDto;

public interface UserService {

	UserDto save(UserDto user);

	Iterable<User> findAll();

	long delete(long id);

	User findOne(String username);

	User findById(long id);

	UserDto update(long id,UserDto userDto);
}
