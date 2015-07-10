package org.uengine.garuda.selfservice;

import org.oce.garuda.multitenancy.TenantContext;
import org.oce.garuda.multitenancy.TenantSpecific;
import org.restlet.data.Form;
import org.restlet.resource.ClientResource;
import org.restlet.resource.ResourceException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.IOException;
import java.net.URL;

/**
 * Created by hoo.lim on 7/8/2015.
 */
public class BasicServlet extends HttpServlet{

    private final static String APP_ID = "demo";

    private final static String GARUDA_SERVER = "http://www.processcodi.com:8080";

    private final static String TEXT_METADATA_KEY = "companyName";

    private final static String FILE_METADATA_KEY = "companyLogo";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // TenantAwareFilter를 통하여 도메인(예 gms.garuda.com에서 gms)에서 태넌트를 가져옴.
        TenantContext tenantContext = TenantContext.getThreadLocalInstance();
        String tenantId = tenantContext.getTenantId();


        // 테넌트가 널값이 아니면 GARUDA서버 도메인을 수정(http://gms.processcodi.com)
        if(tenantId != null){
            request.setAttribute("GARUDA_SERVER", GARUDA_SERVER.replace("www", tenantId));
        }else{
            request.setAttribute("GARUDA_SERVER", GARUDA_SERVER);
        }
        request.setAttribute("TENANT_ID", tenantId);
        request.setAttribute("APP_ID", APP_ID);


        // 웹 서비스를 호출하여 텍스트 메타데이타를 가져온다.
        URL wsdlURL = new URL(GARUDA_SERVER + "/services/TenantSpecific?wsdl");
        QName SERVICE_NAME = new QName("http://multitenancy.garuda.uengine.org/", "TenantSpecificImplService");
        Service service = Service.create(wsdlURL, SERVICE_NAME);
        TenantSpecific client = service.getPort(TenantSpecific.class);
        String result = client.getMetadata(tenantId, APP_ID, TEXT_METADATA_KEY);

        request.setAttribute("TEXT_METADATA", result);
        request.setAttribute("FILE_METADATA", FILE_METADATA_KEY);


        // url에서 테넌트 인식해서 가입 안된 사용자면 자동적으로 가입하게 처리
        if(tenantId != null){
            ClientResource resource = new ClientResource("http://www.processcodi.com:8080/services/tenant/signUp");

            Form form = new Form();
            form.add("tenantId", tenantId);
            form.add("tenantName", tenantId);
            form.add("userId", tenantId);
            form.add("userName", tenantId);
            form.add("password", tenantId);

            // Write the response entity on the console
            try {
                resource.post(form).write(System.out);
            } catch (ResourceException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // selfservice.jsp 페이지로 이동
        RequestDispatcher requestDispatcher = getServletContext().getRequestDispatcher("/selfservice.jsp");
        requestDispatcher.forward(request,response);
    }
}
