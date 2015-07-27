package sample;

@Component
public class Main{

  @Autowired
  TenantSpecificUtil tenantSpecificUtil;
  
  public String getCompanyName(){
    return tenantSpecificUtil.getMetadata("companyName");
  }

//TODO: getCompanyLogoURL()

}
