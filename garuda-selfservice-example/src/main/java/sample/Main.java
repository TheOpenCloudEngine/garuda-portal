package sample;

import org.oce.garuda.multitenancy.TenantSpecificUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Main{

  @Autowired
  TenantSpecificUtil tenantSpecificUtil;

  public String getAppSubscribePlanURL(){
    return "http://" + tenantSpecificUtil.getMetadataServer() + "/services/app/" + tenantSpecificUtil.getAppKey() + "/plan";
  }

  public String getTenantSignupURL(){
    return "http://" + tenantSpecificUtil.getMetadataServer() + "/services/tenant/signUp";
  }

  public String getSelfServicePortalURL(){
    return tenantSpecificUtil.getSelfServicePortalURL();
  }

  public String getCompanyName(){
    return tenantSpecificUtil.getMetadata("companyName");
  }

  public String getCompanyLogoURL(){
    return tenantSpecificUtil.getIamageURL("companyLogo");
  }

  public String callRule(String parameter) {
    return tenantSpecificUtil.runRule("calculatePrice", new String[]{parameter});
  }
}
