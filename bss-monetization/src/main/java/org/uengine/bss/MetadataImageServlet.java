package org.uengine.bss;

import org.uengine.bss.application.*;

import javax.imageio.ImageIO;
import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;

/**
 * Created by hoo.lim on 7/7/2015.
 */
public class MetadataImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException {
        File imageFile = null;

        String[] pathSplitArray = request.getPathInfo().split("/");
        if (pathSplitArray.length > 2) {
            String appId = pathSplitArray[1];
            String metadataKey = pathSplitArray[2];
            App app = TenantApp.load(appId);

            for (MetadataProperty metadataProperty : app.getMetadataPropertyList()) {
                if (metadataProperty instanceof FileMetadataProperty && Objects.equals(metadataKey, metadataProperty.getKey())) {
                    MetadataFile metadataFile = (MetadataFile) metadataProperty.getDefaultValue();
                    String fileName = metadataFile.getUploadedPath();

                    imageFile = new File(TenantApp.getResourcePath(appId,fileName));
                    if (!imageFile.exists()) {
                        imageFile = new File(App.getResourcePath(appId,fileName));
                        response.setContentType(metadataFile.getMimeType());
                    }
                    break;
                }
            }
        }

        if(imageFile == null){
            String pathToWeb = getServletContext().getRealPath(File.separator);
            imageFile = new File(pathToWeb + "images" + File.separator + "404.jpg");
            response.setContentType("image/jpg");
        }

        String extension = imageFile.getName().substring(imageFile.getName().lastIndexOf(".") + 1);

        BufferedImage bi = ImageIO.read(imageFile);
        OutputStream out = response.getOutputStream();
        ImageIO.write(bi, extension, out);
        out.close();
    }
}
