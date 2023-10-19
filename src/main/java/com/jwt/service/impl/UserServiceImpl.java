package com.jwt.service.impl;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.dao.RoleDao;
import com.jwt.dao.UserDao;
import com.jwt.model.Role;
import com.jwt.model.User;
import com.jwt.model.UserDto;
import com.jwt.service.UserService;


@Service(value = "userService")
public class UserServiceImpl implements UserDetailsService, UserService {
	
	 private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
	
	@Autowired
	private UserDao userDao;
	
	 @Autowired
	 private RoleDao roleDao;

	@Autowired
	private BCryptPasswordEncoder bcryptEncoder;
	
	 public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	        User user = userDao.findByUsername(username);
	        if(user == null){
	            log.error("Invalid username or password.");
	            throw new UsernameNotFoundException("Invalid username or password.");
	        }
	        Set<GrantedAuthority> grantedAuthorities = getAuthorities(user);
	        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
	    }

	    private Set<GrantedAuthority> getAuthorities(User user) {
	        Set<Role> roleByUserId = user.getRoles();
	        Set<GrantedAuthority> authorities = roleByUserId.stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.getName().toString().toUpperCase())).collect(Collectors.toSet());
	        return authorities;
	    }

	    
	    public Iterable<User> findAll() {
	        return userDao.findAll();
	    }

	    @Override
		public long delete(long id) {
			return userDao.deleteByUserId(id);
		}

	@Override
	public User findOne(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public User findById(long id) {
		Optional<User> optionalUser = userDao.findById(id);
		return optionalUser.isPresent() ? optionalUser.get() : null;
	}

    @Override
    public UserDto update(long id,UserDto userDto) {
        User user = findById(id);
        if(user != null) {
            BeanUtils.copyProperties(userDto, user, "password", "username");
            userDao.save(user);
        }
        return userDto;
    }

    @Override
    public UserDto save(UserDto userDto) {
        User userWithDuplicateUsername = userDao.findByUsername(userDto.getUsername());
        if(userWithDuplicateUsername != null && userDto.getId() != userWithDuplicateUsername.getId()) {
            log.error(String.format("Duplicate username %", userDto.getUsername()));
            throw new RuntimeException("Duplicate username.");
        }
        User user = new User();
        user.setEmail(userDto.getEmail());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setUsername(userDto.getUsername());
        user.setPassword(bcryptEncoder.encode(userDto.getPassword()));
        user.setRoles(roleDao.find(userDto.getRoleName()));
        userDao.save(user);
        return userDto;
    }
}
