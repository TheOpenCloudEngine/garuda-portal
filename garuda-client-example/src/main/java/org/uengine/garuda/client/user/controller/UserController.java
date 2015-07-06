package org.uengine.garuda.client.user.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.http.client.ClientProtocolException;
import org.oce.garuda.multitenancy.TenantContext;
import org.oce.garuda.multitenancy.TenantSpecificUtil;
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
import org.uengine.garuda.client.user.rest.client.MetadataServiceClient;
import org.uengine.garuda.client.user.service.UserService;

@RequestMapping("/user")
@Controller
public class UserController {
	@Autowired
	TenantSpecificUtil tenantSpecificUtil;

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
	public String main(Model model, HttpSession session ) throws IOException {
		model.addAttribute("appId", appId);
		model.addAttribute("domain", getDomain());
		model.addAttribute("companyName", tenantSpecificUtil.getMetadata("companyName"));
		return "main";
	}
	
	@RequestMapping("/signUpStep1")
	public String signUpStep1(Model model){
		model.addAttribute("appId", appId);
		model.addAttribute("domain", domain);
		return "signUpStep1";
	}
	
	@RequestMapping("/signUpStep2")
	public String signUpStep2(String planId, Model model){
		model.addAttribute("appId", appId);
		model.addAttribute("domain", domain);
		model.addAttribute("planId", planId);
		return "signUpStep2";
	}
	
	@ResponseBody
	@RequestMapping(value = "/signUpStep3",method = RequestMethod.POST)
	public String signUpStep3(String tenantId, String userId, String userName, String password, String tenantName, String planId) throws ClientProtocolException, IOException{
		userService.signUp(userId, password, userName, tenantId, tenantName, planId, domain, appId);
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

	private String getTenantId(){
		TenantContext tenantContext = TenantContext.getThreadLocalInstance();
		return tenantContext.getTenantId();
	}

	private String getDomain(){
		return domain.replace("www", getTenantId());
	}
}
