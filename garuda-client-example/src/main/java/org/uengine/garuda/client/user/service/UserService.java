package org.uengine.garuda.client.user.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import org.uengine.garuda.client.tenant.dao.TenantMapper;
import org.uengine.garuda.client.user.dao.UserMapper;
import org.uengine.garuda.client.user.entity.User;

@Service
public class UserService {
	@Autowired
	UserMapper userMapper;
	
	@Autowired
	TenantMapper tenantMapper; 
	
	public List<User> selectAllUser(){
		return userMapper.selectAllUser();
	}
	
	public User selectUser(String userId, String password){
		return userMapper.selectUser(userId, password);
	}
	
	public void signUp(String userId, String password, String userName, String tenantId, String tenantName, String planId, String domain, String appId) throws ClientProtocolException, IOException{
		userMapper.insertUser(userId, password, userName, tenantId);
		tenantMapper.insertTenant(tenantId, tenantName);
		String returnCode = tenantSubscribe(domain, appId, planId, tenantName);
		
		if(!returnCode.equals("SUCCESS"))
			throw new ClientProtocolException();
	}
	
	public String tenantSubscribe(String domain, String appId, String planId, String tenantName) throws IllegalStateException, IOException{
		String retunCode = "";
		String url = domain + "/services/app/" + appId + "/subscribe";
		HttpClient client = HttpClientBuilder.create().build();
		HttpPost request = new HttpPost(url);
		
		request.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
        nameValuePairs.add(new BasicNameValuePair("accountId",tenantName));
        nameValuePairs.add(new BasicNameValuePair("planId",planId));
        request.setEntity(new UrlEncodedFormEntity(nameValuePairs)); 
		
        HttpResponse response = client.execute(request);			
		BufferedReader rd = new BufferedReader (new InputStreamReader(response.getEntity().getContent()));
		
		StringBuffer result = new StringBuffer();			
		String buffer = "";
		while ((buffer = rd.readLine()) != null) 				
			result.append(buffer);
		
		retunCode = result.toString();
		System.out.println(retunCode);
		
		return retunCode;
	}
}
