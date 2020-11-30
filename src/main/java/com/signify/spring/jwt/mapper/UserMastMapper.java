package com.signify.spring.jwt.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.signify.spring.jwt.models.UserDTO;

public class UserMastMapper implements RowMapper<UserDTO> {
	@Override
	public UserDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
		UserDTO userDTO = new UserDTO();
		userDTO.setUser_Id(rs.getInt("user_Id"));
		userDTO.setUserName(rs.getString("userName"));
		userDTO.setUserAuthCode(rs.getString("userAuthCode"));
		userDTO.setUserAuthPassword(rs.getString("userAuthPassword"));
		userDTO.setUserStatus(rs.getString("userStatus"));
		userDTO.setUserEmail(rs.getString("userEmail"));
		userDTO.setUserType(rs.getString("userType"));
		userDTO.setAuthToken(rs.getString("authToken"));
		userDTO.setAuthTokenStatus(rs.getString("authTokenStatus"));
		userDTO.setCreateBy(rs.getString("createBy"));
		userDTO.setCreateDate(rs.getTimestamp("createDate"));
		userDTO.setUpdateBy(rs.getString("updateBy")!=null ? rs.getString("updateBy") :"NA");
		userDTO.setUpdateDate(rs.getTimestamp("updateDate"));
	    return userDTO;
	}
}
