package org.uengine.monetization.dao;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AppMappingMapper {

	public List<Map<String, String>> selectAll();

    public List<String> select(@Param("appId") String appId, @Param("accountId") String accountId);

    public void truncate(String appId);

    public void truncateAccount();

}
