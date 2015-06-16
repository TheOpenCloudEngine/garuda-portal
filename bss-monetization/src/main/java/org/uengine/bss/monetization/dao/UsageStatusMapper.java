package org.uengine.bss.monetization.dao;

import org.apache.ibatis.annotations.Param;
import org.uengine.bss.monetization.UsageStatus;

import java.util.List;

public interface UsageStatusMapper {

	public List<UsageStatus> selectByPlan(@Param("monthId") String monthId, @Param("appId") String appId, @Param("accountId") String accountId, @Param("planId") String planId);

	public UsageStatus select(@Param("monthId") String monthId, @Param("appId") String appId, @Param("accountId") String accountId, @Param("planId") String planId, @Param("serviceId") String serviceId);
	
	public void insert(UsageStatus usageStatus);

    public void truncate(String appId);

    public void update(UsageStatus usageStatus);
}
