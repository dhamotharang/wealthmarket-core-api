/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.wm.core.controller;

import com.wm.core.model.response.BaseResponse;
import com.wm.core.service.authmanager.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author gol
 */
@RestController
@RequestMapping("${app.title}")
public class AuthController {


	@Autowired
	AuthenticationService authenticationService;

	@PostMapping("/auth/login")
	ResponseEntity<?> userLogin(@RequestParam String username_email, @RequestParam String password) {
		BaseResponse response = authenticationService.userLogin(username_email, password);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/auth/refresh")
	ResponseEntity<?> refreshUserAuthentication(@RequestParam String token) {
		BaseResponse response = authenticationService.refreshToken(token);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
