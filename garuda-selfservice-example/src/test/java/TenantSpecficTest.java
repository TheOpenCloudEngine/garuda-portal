import org.junit.Test;
import org.oce.garuda.multitenancy.TenantSpecific;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.URL;

/**
 * Created by hoo.lim on 7/8/2015.
 */

public class TenantSpecficTest {
    @Test
    public void testGetMetadata(){
            try{
                URL wsdlURL = new URL("http://www.processcodi.com:8080/services/TenantSpecific?wsdl");
                QName SERVICE_NAME = new QName("http://multitenancy.garuda.uengine.org/", "TenantSpecificImplService");
                Service service = Service.create(wsdlURL, SERVICE_NAME);
                TenantSpecific client = service.getPort(TenantSpecific.class);
                String result = client.getMetadata("gms","HelloApp","companyName");
                System.out.println(result);
            }
            catch (Exception ex){
                ex.printStackTrace();
            }
    }
}
