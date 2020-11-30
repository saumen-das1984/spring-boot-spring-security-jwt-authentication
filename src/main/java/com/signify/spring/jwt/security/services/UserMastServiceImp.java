package com.signify.spring.jwt.security.services;

import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.signify.spring.jwt.mapper.UserMastMapper;
import com.signify.spring.jwt.models.UserDTO;
import com.signify.spring.jwt.security.UserMastQueryConfig;

@Service
@Transactional
public class UserMastServiceImp implements UserMastService {
	
	@Autowired
	@Qualifier("jdbcTemplate")
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	UserMastQueryConfig userMastQueryConfig;
 
	@Override
	public UserDTO createUser(UserDTO userDTO) {
		KeyHolder holder = new GeneratedKeyHolder();
		String createUserQuery = userMastQueryConfig.getInsert();
		System.out.println("createUserQuery : "+createUserQuery);
		Timestamp date = new Timestamp(new Date().getTime());
		System.out.println("date : "+date);
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
				PreparedStatement ps = connection.prepareStatement(createUserQuery, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, userDTO.getUserName());
				ps.setString(2, userDTO.getUserAuthCode());
				ps.setString(3, userDTO.getUserAuthPassword());
				ps.setString(4, userDTO.getUserEmail());
				ps.setString(5, userDTO.getUserStatus());
				ps.setString(6, userDTO.getUserType());
				ps.setString(7, userDTO.getAuthToken());
				ps.setString(8, userDTO.getAuthTokenStatus());
				ps.setTimestamp(9, date);
				ps.setString(10, userDTO.getUserName());
				return ps;
			}
		}, holder);

		int newUser_Id = holder.getKey().intValue();
		userDTO.setUser_Id(newUser_Id);
		return userDTO;
	}

	@Override
	public List<UserDTO> getAllUsers() {
		String fetchAllUserQuery = userMastQueryConfig.getSelect();
		System.out.println("fetchAllUserQuery : "+fetchAllUserQuery);
		List<UserDTO> user_list = jdbcTemplate.query(fetchAllUserQuery,  new UserMastMapper());
		return user_list;
	}

	@Override
	public UserDTO findByUserId(long userId) {
		String fetchByUserIdQuery = userMastQueryConfig.getSelectbyId();
		System.out.println("fetchByUserIdQuery : "+fetchByUserIdQuery);
		UserDTO userDTO = jdbcTemplate.queryForObject(fetchByUserIdQuery, new Object[]{userId}, new UserMastMapper());
		return userDTO;
	}

	@Override
	public boolean findByUserName(String userName) {
		boolean existsUserFlag = true;
		String fetchByUserNameQuery = userMastQueryConfig.getSelectbyName();
		System.out.println("fetchByUserNameQuery : "+fetchByUserNameQuery);
		List<UserDTO> user_list = jdbcTemplate.query(fetchByUserNameQuery, new Object[]{userName}, new UserMastMapper());
		if(user_list.isEmpty())
		{
			existsUserFlag = false;
		}
		return existsUserFlag;
	}

	@Override
	public UserDTO findByUserAuthCode(String userAuthCode){
		String fetchByUserAuthCodeQuery = userMastQueryConfig.getSelectbyAuthCode();
		System.out.println("fetchByUserAuthCodeQuery : "+fetchByUserAuthCodeQuery);
		UserDTO userDTO = jdbcTemplate.queryForObject(fetchByUserAuthCodeQuery, new Object[]{userAuthCode}, new UserMastMapper());
		return userDTO;
	}
	
	@Override
	public int updateUser(UserDTO userDTO) {
		int updCpunt = 0;
		String updateUserQuery = userMastQueryConfig.getUpdate();
		System.out.println("updateUserQuery : "+updateUserQuery);
		updCpunt =  jdbcTemplate.update(updateUserQuery,new Object[]{userDTO.getUserName(),userDTO.getUserAuthCode(), userDTO.getUserAuthPassword(), userDTO.getUserEmail(), userDTO.getUserStatus(), userDTO.getUserType(), userDTO.getAuthToken(), userDTO.getAuthTokenStatus(), userDTO.getCreateDate(), userDTO.getCreateBy(), userDTO.getUpdateDate(), userDTO.getUpdateBy(), userDTO.getUser_Id()});
		return updCpunt;
	}

	@Override
	public int deleteUserById(long userId) {
		int updCpunt = 0;
		String deleteUserQuery = userMastQueryConfig.getDelete();
		System.out.println("deleteUserQuery : "+deleteUserQuery);
		updCpunt =  jdbcTemplate.update(deleteUserQuery,new Object[]{userId});
		return updCpunt;
	}

	@Override
	public boolean findByEmail(String userEmail) {
		boolean existsUserFlag = true;
		String fetchByUserEmailQuery = userMastQueryConfig.getSelectbyEmail();
		System.out.println("fetchByUserEmailQuery : "+fetchByUserEmailQuery);
		List<UserDTO> user_list = jdbcTemplate.query(fetchByUserEmailQuery, new Object[]{userEmail}, new UserMastMapper());
		if(user_list.isEmpty())
		{
			existsUserFlag = false;
		}
		return existsUserFlag;
	}

}
