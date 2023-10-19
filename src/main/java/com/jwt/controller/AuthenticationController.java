package com.jwt.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.common.PermitUtils;
import com.jwt.config.JwtTokenUtil;
import com.jwt.dao.UserDao;
import com.jwt.model.ApiResponse;
import com.jwt.model.AuthToken;
import com.jwt.model.LoginUser;
import com.jwt.model.User;
import com.jwt.service.UserService;

import io.permit.sdk.api.PermitApiError;
import io.permit.sdk.api.PermitContextError;
import io.permit.sdk.openapi.models.RoleRead;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/token")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserDao userDao;
    
    PermitUtils permitUtils = new PermitUtils();

   /* @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ApiResponse<AuthToken> generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        final User user = userService.findOne(loginUser.getUsername());
        final String token = jwtTokenUtil.generateToken(user);
        return new ApiResponse<>(200, "success",new AuthToken(token, user.getUsername()));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ApiResponse<Void> logout() throws AuthenticationException {
        return new ApiResponse<>(200, "success",null);
    }*/
    
    @RequestMapping(value = "/generate-token", method = RequestMethod.POST)
    public ApiResponse generateToken(@RequestBody LoginUser loginUser) throws AuthenticationException, PermitContextError, IOException, PermitApiError {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUser.getUsername(), loginUser.getPassword()));
        User user = userService.findOne(loginUser.getUsername());
        Object roleName=userDao.findByRoleName(user.getUsername());
        String token = jwtTokenUtil.generateToken(user);
        RoleRead read=permitUtils.userRole(roleName.toString());
		List<String> permissionsList = new ArrayList<String>();
		List<String> permission=read.permissions;
		for (String permison : permission) {
			String  role=permison.split(":")[1];
			permissionsList.add(role);
		}
        return new ApiResponse(200, "success",new AuthToken(token, user.getUsername(),roleName.toString(),permissionsList));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public ApiResponse logout() throws AuthenticationException {
        return new ApiResponse(200, "success",null);
    }
}
