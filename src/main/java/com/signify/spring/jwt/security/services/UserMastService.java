package com.signify.spring.jwt.security.services;

import java.util.List;

import com.signify.spring.jwt.models.UserDTO;

public interface UserMastService  {
	public UserDTO createUser(UserDTO userDTO);
	public List<UserDTO> getAllUsers();
	public UserDTO findByUserId(long userId);
	public UserDTO findByUserAuthCode(String userAuthCode);
	public boolean findByUserName(String userName);
	public boolean findByEmail(String userEmail);
	public int updateUser(UserDTO userDTO);
	public int deleteUserById(long userId);
}
