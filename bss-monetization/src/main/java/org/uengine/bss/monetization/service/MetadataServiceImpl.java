package org.uengine.bss.monetization.service;

import org.springframework.stereotype.Component;
import org.uengine.bss.application.App;
import org.uengine.bss.application.MetadataProperty;
import org.uengine.bss.application.TenantApp;
import org.uengine.bss.application.TextMetadataProperty;
import org.uengine.bss.monetization.api.MetadataService;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Objects;

/**
 * Created by hoo.lim on 6/29/2015.
 */
@Component
public class MetadataServiceImpl implements MetadataService{

    @Override
    public Response getTextMetadata(String appId, String tenantId, String key) throws FileNotFoundException {
        TenantApp app = TenantApp.load(appId, tenantId);
        List<MetadataProperty> metadataPropertyList= app.getMetadataPropertyList();

        String returnMsg = "";

        for(MetadataProperty metadataProperty : metadataPropertyList){
            if( metadataProperty instanceof TextMetadataProperty && Objects.equals(key, metadataProperty.getKey())){
                returnMsg = (String) metadataProperty.getDefaultValue();
            }
        }

        return Response.ok()
                .entity(returnMsg)
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .build();
    }

    @Override
    public Response getImageMetadata(String appId, String tenantId, String key) throws FileNotFoundException {
        return Response.ok()
                .entity("")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .build();
    }
}
