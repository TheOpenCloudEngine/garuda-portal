import org.junit.Test;
import org.restlet.data.Form;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import java.io.IOException;

/**
 * Created by hoo.lim on 7/8/2015.
 */
public class TenantServiceTest {
    @Test
    public void testTenantSignUp(){
        // TODO Auto-generated method stub

        // Create the client resource
        ClientResource resource = new ClientResource("http://www.processcodi.com:8080/services/tenant/signUp");

        Form form = new Form();
        form.add("tenantId", "1234");
        form.add("tenantName", "1234");
        form.add("userId", "John");
        form.add("userName", "John");
        form.add("password", "John");

        // Write the response entity on the console
        try {
            resource.post(form).write(System.out);
        } catch (ResourceException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
