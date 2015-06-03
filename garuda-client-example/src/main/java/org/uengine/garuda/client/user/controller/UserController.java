package org.uengine.garuda.client.user.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import org.uengine.garuda.client.user.entity.User;
import org.uengine.garuda.client.user.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {
	@Autowired
	UserService userService;
	
    @Value("${service.appId}")  
    private String appId;  
    
    @Value("${service.domain}")  
    private String domain;  
	
	@RequestMapping("/test")
	public String test(){
		List<User> users = userService.selectAllUser();
		System.out.println(users);
		return "test";
	}
	
	@RequestMapping("/login")
	public String login(){
		return "login";
	}
	
	@ResponseBody
	@RequestMapping(value = "/loginCheck", method=RequestMethod.POST)
	public String loginCheck(String userId, String password, HttpSession session){
		User user = userService.selectUser(userId, password);
		String result = null;
		if(user != null){
			result = "SUCCESS";
			session.setAttribute("user", user);
		}else{
			result = "FAILED";
		}
		return result;
	}
	
	@RequestMapping("/main")
	public String main(){
		return "main";
	}
	
	@RequestMapping("/subscribe1")
	public String subscribe1(Model model){
		model.addAttribute("appId", appId);
		model.addAttribute("domain", domain);
		return "subscribe1";
	}
	
	@RequestMapping("/subscribe2")
	public String subscribe2(String planId, Model model){
		model.addAttribute("appId", appId);
		model.addAttribute("domain", domain);
		model.addAttribute("planId", planId);
		return "subscribe2";
	}
	
	@ResponseBody
	@RequestMapping("/signin")
	public String signin(String tenantId, String userId, String userName, String password, String tenantName, String planId) throws ClientProtocolException, IOException{
		userService.signIn(userId, password, userName, tenantId, tenantName, planId, domain, appId);
		return "SUCCESS";
	}
	
	@RequestMapping("/logout")
	public String logout(HttpSession session){
		session.invalidate();
		System.out.println("hello");
		return "redirect:login";
	}
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	@ResponseStatus(HttpStatus.OK)
	public String intertalExceptionHandler(Exception ex){
		System.out.println(ex.getMessage());
	    return ex.getMessage();
	}
}
