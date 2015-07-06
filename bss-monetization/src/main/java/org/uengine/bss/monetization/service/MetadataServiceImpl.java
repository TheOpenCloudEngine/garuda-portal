package org.uengine.bss.monetization.service;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Component;
import org.uengine.bss.application.*;
import org.uengine.bss.monetization.api.MetadataService;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.Response;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

/**
 * Created by hoo.lim on 6/29/2015.
 */
@Component
public class MetadataServiceImpl implements MetadataService{

    @Override
    public Response getTextMetadata(String appId, String key) throws FileNotFoundException {
        TenantApp app = TenantApp.load(appId);
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
    public Response getImageMetadata(String appId, String key) throws FileNotFoundException {
        TenantApp app = TenantApp.load(appId);
        File imageFile = null;

        for(MetadataProperty metadataProperty : app.getMetadataPropertyList()){
            if(metadataProperty instanceof FileMetadataProperty && Objects.equals(key, metadataProperty.getKey())){
                String fileName = ((MetadataFile) metadataProperty.getDefaultValue()).getUploadedPath();
                imageFile = TenantApp.getFileOfGarudaApp(appId, TenantApp.getTenantId(), fileName);
                if(!imageFile.exists()){
                    imageFile = TenantApp.getDefaultFileOfGarudaApp(appId, fileName);
                }

                break;
            }
        }

        return Response.ok(new FileInputStream(imageFile)).build();
    }
}
