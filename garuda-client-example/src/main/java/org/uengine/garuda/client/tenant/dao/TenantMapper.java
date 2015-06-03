package org.uengine.garuda.client.tenant.dao;

import org.apache.ibatis.annotations.Param;

public interface TenantMapper {
	public void insertTenant(@Param("id") String id, @Param("name") String name);
}
