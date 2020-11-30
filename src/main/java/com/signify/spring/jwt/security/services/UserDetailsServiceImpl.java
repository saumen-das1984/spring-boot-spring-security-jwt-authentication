package com.signify.spring.jwt.security.services;

import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.signify.spring.jwt.models.ERole;
import com.signify.spring.jwt.models.User;
import com.signify.spring.jwt.models.UserDTO;
//import com.signify.spring.jwt.repository.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	UserMastService userMastService;
	
	@Autowired
	PasswordEncoder encoder;

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserDTO userDTO = userMastService.findByUserAuthCode(username);
//				.orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + username));
		System.out.println("userDTO "+userDTO.toString());
		ModelMapper modelMapper = new ModelMapper();
		User user = modelMapper.map(userDTO, User.class);

		Set<String> roles = new HashSet<>();
		
		String role = null ;
		String userType = userDTO.getUserType();
		System.out.println("userType "+userType);
		switch (userType) {
			case "A" :
				System.out.println("userType1 "+userType);
				role = String.valueOf(ERole.ROLE_ADMIN);
				break;
			case "C" :
				System.out.println("userType2 "+userType);
				role = String.valueOf(ERole.ROLE_USER);
				break;
			default:
				break;
		}
		System.out.println("role "+role);
		roles.add(role);
		user.setRoles(roles);
		user.setPassword(userDTO.getUserAuthPassword());
		System.out.println("user "+user);
		return UserDetailsImpl.build(user);
	}

}
