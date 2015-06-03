package org.uengine.garuda.client.user.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import org.uengine.garuda.client.user.entity.User;

public interface UserMapper {
	public List<User> selectAllUser();
	
	public User selectUser(@Param("id") String id, @Param("password") String password);
	
	public void insertUser(@Param("id") String Id, @Param("password") String password, @Param("name") String name, @Param("tenantId") String tenantId);
}
