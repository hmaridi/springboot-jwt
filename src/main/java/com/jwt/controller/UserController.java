package com.jwt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//import com.jwt.common.AccessTokenUtils;
//import com.jwt.common.PermitUtils;
import com.jwt.model.ApiResponse;
import com.jwt.model.UserDto;
import com.jwt.service.UserService;

//import io.permit.sdk.api.PermitApiError;
//import io.permit.sdk.enforcement.Resource;
//import io.permit.sdk.enforcement.User;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/users")
public class UserController {
		

	public static final String USER_SAVE = "User saved successfully";
	public static final String USER_LIST = "User list fetched successfully";
	public static final String USER_FETCH = "User fetched successfully";
	public static final String USER_UPDATE = "User updated successfully";
	public static final String USER_DELETE = "User deleted successfully";
	public static final String FAILURE = "failure";
	public static final String NOT_ACCESS = "You don't have permission to ";

	@Autowired
	private UserService userService;
	
	
	@PostMapping
	public ApiResponse saveUser(@RequestBody UserDto user) {
		return new ApiResponse(HttpStatus.OK.value(), USER_SAVE, userService.save(user));
	}

	@GetMapping
	public ApiResponse listUser() {
		return new ApiResponse(HttpStatus.OK.value(), USER_LIST, userService.findAll());
	}

	@GetMapping("/{id}")
	public ApiResponse getOne(@PathVariable int id) {
		return new ApiResponse(HttpStatus.OK.value(), USER_FETCH, userService.findById(id));
	}

	@PutMapping("/{id}")
	public ApiResponse update(@PathVariable("id") Long id, @RequestBody UserDto userDto) {
		return new ApiResponse(HttpStatus.OK.value(), USER_UPDATE, userService.update(id, userDto));
	}

	@DeleteMapping("/{id}")
	public ApiResponse delete(@PathVariable int id) {
		userService.delete(id);
		return new ApiResponse(HttpStatus.OK.value(), USER_DELETE, FAILURE);
	}
	
	/*PermitUtils permitUtils = new PermitUtils();

	@PostMapping
	public ApiResponse create(@RequestBody UserDto user) throws Exception, PermitApiError {
		String userKey = AccessTokenUtils.getUserIdFromToken();
		permitUtils.PermitUtils(userKey);
		User createUser = User.fromString(userKey);
		String action = "create";
		Resource resource = new Resource.Builder("UserManagement").build();
		boolean permitted = permitUtils.permitCheck(createUser, action, resource);
		if (permitted) {
			return new ApiResponse(HttpStatus.OK.value(), USER_SAVE, userService.save(user));
		} else {
			return new ApiResponse(HttpStatus.FORBIDDEN.value(), NOT_ACCESS + action, FAILURE);
		}
	}

	@GetMapping
	public ApiResponse listUser() throws Exception, PermitApiError {
		String userKey = AccessTokenUtils.getUserIdFromToken();
		permitUtils.PermitUtils(userKey);
		io.permit.sdk.enforcement.User getUsers = io.permit.sdk.enforcement.User.fromString(userKey);
		String action = "read";
		Resource resource = new Resource.Builder("UserManagement").build();
		boolean permitted = permitUtils.permitCheck(getUsers, action, resource);
		if (permitted) {
			return new ApiResponse(HttpStatus.OK.value(), USER_LIST, userService.findAll());
		} else {
			return new ApiResponse(HttpStatus.FORBIDDEN.value(), NOT_ACCESS + action, FAILURE);
		}
	}

	@GetMapping(value = "/{id}")
	public ApiResponse getUser(@PathVariable long id) throws Exception, PermitApiError {
		String userKey = AccessTokenUtils.getUserIdFromToken();
		permitUtils.PermitUtils(userKey);
		User getUserId = User.fromString(userKey);
		String action = "read";
		Resource resource = new Resource.Builder("UserManagement").build();
		boolean permitted = permitUtils.permitCheck(getUserId, action, resource);
		if (permitted) {
			return new ApiResponse(HttpStatus.OK.value(), USER_FETCH, userService.findById(id));
		} else {
			return new ApiResponse(HttpStatus.FORBIDDEN.value(), NOT_ACCESS + action, FAILURE);
		}
	}

	@PutMapping("/{id}")
	public ApiResponse update(@PathVariable("id") Long id, @RequestBody UserDto userDto)
			throws Exception, PermitApiError {
		String userKey = AccessTokenUtils.getUserIdFromToken();
		permitUtils.PermitUtils(userKey);
		User updateUser = User.fromString(userKey);
		String action = "update";
		Resource resource = new Resource.Builder("UserManagement").build();
		boolean permitted = permitUtils.permitCheck(updateUser, action, resource);
		if (permitted) {
			return new ApiResponse(HttpStatus.OK.value(), USER_UPDATE, userService.update(id, userDto));
		} else {
			return new ApiResponse(HttpStatus.FORBIDDEN.value(), NOT_ACCESS + action, FAILURE);
		}
	}

	@DeleteMapping("/{id}")
	public ApiResponse delete(@PathVariable(value = "id") Long id) throws Exception, PermitApiError {
		String userKey = AccessTokenUtils.getUserIdFromToken();
		permitUtils.PermitUtils(userKey);
		User deleteUser = User.fromString(userKey);
		String action = "delete";
		Resource resource = new Resource.Builder("UserManagement").build();
		boolean permitted = permitUtils.permitCheck(deleteUser, action, resource);
		if (permitted) {
			return new ApiResponse(HttpStatus.OK.value(), USER_DELETE, userService.delete(id));
		} else {
			return new ApiResponse(HttpStatus.FORBIDDEN.value(), NOT_ACCESS + action, FAILURE);
		}
	}*/
	
}
