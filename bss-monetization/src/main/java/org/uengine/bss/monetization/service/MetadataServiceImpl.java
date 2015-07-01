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
    public Response getImageMetadata(String appId, String tenantId, String key, String modified) throws FileNotFoundException {
        TenantApp app = TenantApp.load(appId,tenantId);
        File imageFile = null;

        for(MetadataProperty metadataProperty : app.getMetadataPropertyList()){
            if(metadataProperty instanceof FileMetadataProperty && Objects.equals(key, metadataProperty.getKey())){
                imageFile = TenantApp.getFileOfGarudaApp(appId, tenantId, ((MetadataFile) metadataProperty.getDefaultValue()).getUploadedPath());
                break;
            }
        }

        return returnFile(imageFile, modified);
//        return Response.ok()
//                .entity("")
//                .header("Access-Control-Allow-Origin", "*")
//                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
//                .build();
    }

    /**
     *
     * Sends the file if modified and "not modified" if not modified
     * future work may put each file with a unique id in a separate folder in tomcat
     *   * use that static URL for each file
     *   * if file is modified, URL of file changes
     *   * -> client always fetches correct file
     *
     *     method header for calling method public Response getXY(@HeaderParam("If-Modified-Since") String modified) {
     *
     * @param file to send
     * @param modified - HeaderField "If-Modified-Since" - may be "null"
     * @return Response to be sent to the client
     */
    public static Response returnFile(File file, String modified) {
        if (!file.exists()) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        // do we really need to send the file or can send "not modified"?
        if (modified != null) {
            Date modifiedDate = null;

            // we have to switch the locale to ENGLISH as parseDate parses in the default locale
            Locale old = Locale.getDefault();
            Locale.setDefault(Locale.ENGLISH);
            try {
                modifiedDate = DateUtils.parseDate(modified, DEFAULT_PATTERNS);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            Locale.setDefault(old);

            if (modifiedDate != null) {
                // modifiedDate does not carry milliseconds, but fileDate does
                // therefore we have to do a range-based comparison
                // 1000 milliseconds = 1 second
                if (file.lastModified()-modifiedDate.getTime() < DateUtils.MILLIS_PER_SECOND) {
                    return Response.status(Response.Status.NOT_MODIFIED).build();
                }
            }
        }
        // we really need to send the file

        try {
            Date fileDate = new Date(file.lastModified());
            return Response.ok(new FileInputStream(file)).lastModified(fileDate).build();
        } catch (FileNotFoundException e) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    public static final String[] DEFAULT_PATTERNS = new String[] {
            org.apache.http.impl.cookie.DateUtils.PATTERN_RFC1036,
            org.apache.http.impl.cookie.DateUtils.PATTERN_RFC1123,
            org.apache.http.impl.cookie.DateUtils.PATTERN_ASCTIME
    };
}
