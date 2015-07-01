package org.metaworks.metadata;

import org.metaworks.MetaworksContext;
import org.metaworks.Refresh;
import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.*;
import org.uengine.codi.mw3.ide.ResourceNode;
import org.uengine.codi.mw3.resource.ResourceManager;
import org.uengine.util.UEngineUtil;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

@Face(ejsPath = "dwr/metaworks/org/metaworks/metadata/MetadataProperty.ejs",
		ejsPathMappingByContext={
	"{where: 'ide', face: 'dwr/metaworks/org/metaworks/metadata/ImageProperty.ejs'}",
	"{where: 'ssp', face: 'dwr/metaworks/org/metaworks/metadata/ImageProperty.ejs'}"
})
//@XStreamAlias("MetadataProperty")
public class ImageProperty extends MetadataProperty{
	
	public ImageProperty() {
		setType(MetadataProperty.IMAGE_PROP);
	}

	String value;
		@Override
		@Hidden
		@Available(when=MetaworksContext.WHEN_VIEW)
		@NonEditable(when={MetaworksContext.WHEN_EDIT})
		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		
	
		@Override
		protected Object clone() throws CloneNotSupportedException {
			// TODO Auto-generated method stub
			return super.clone();
		}
		
		
		
	
	@Override
	public Object edit() throws Exception {
		if(getFile().getFileTransfer()==null) throw new Exception("파일이 첨부되어야 합니다.");
		
		getFile().upload();
		
		setValue(getFile().getUploadedPath());
		
		return super.edit();
	}
	
	
	
	
	@Override
	@ServiceMethod(callByContent=true, target=ServiceMethodContext.TARGET_POPUP)
	public Object[] save() throws FileNotFoundException, IOException, Exception {
		
		if(getFile().getFileTransfer()==null) throw new Exception("파일이 첨부되어야 합니다.");
		
		getFile().setBaseDir(calculateTenantImageBasePath());
		
		String[] existingFileNames = new File(getFile().getBaseDir()).list();
		getFile().upload();
		
		
		setValue(getFile().getUploadedPath());
		
		Object[] returnValues = super.save();
		
//		for (int i=0; i < existingFileNames.length; i++){
//			new File(getFile().getBaseDir() + File.separator + existingFileNames[i]).delete();
//		}

		return returnValues;
	}
	private String calculateTenantImageBasePath() {
//			return ResourceManager.getResourcePath(getResourceNode().getProjectId(), TenantContext.getThreadLocalInstance().getTenantId(), null);
		return null;
	}


	@ServiceMethod(callByContent=true, bindingHidden=true, bindingFor="file", eventBinding={"uploaded"} ,target=ServiceMethodContext.TARGET_APPEND)
	public Object[] changeFile() throws FileNotFoundException, IOException, Exception{
		// TODO 여기를 안타서.. 셀프 서비스 포탈에서 이미지가 변경이 안됨
		this.getFile().getMetaworksContext().setWhen(MetaworksContext.WHEN_NEW);
		setFilePreview(this.getFile());
		
		return new Object[]{new Refresh(this.getFile() , true) ,  new Refresh(this.getFilePreview() , true)};
		
	}
	
	@ServiceMethod(callByContent=true, bindingHidden=true, bindingFor="resourceNode", eventBinding={"toOpener"})
	public void changeResourceFile(){
		
		MetadataFile resourceFile = new MetadataFile();
		
		resourceFile.setFilename(this.getResourceNode().getName());
		resourceFile.setUploadedPath(this.getResourceNode().getId());
		resourceFile.getMetaworksContext().setWhen(MetaworksContext.WHEN_VIEW);
		resourceFile.setMimeType(ResourceNode.findNodeType(this.getName()));
		
		this.getFile().getMetaworksContext().setWhen(MetaworksContext.WHEN_EDIT);
		setFilePreview(resourceFile);
		
		
	}
	
	@Override
	public MetadataProperty showPropertyDetail() throws Exception {
		
		
		setFile(createMetaworksFileObject());		
		setFilePreview(createMetaworksFileObject()); //create another for preview only
		this.getResourceNode();
		return super.showPropertyDetail();
	}
	private MetadataFile createMetaworksFileObject() {
		MetadataFile file = new MetadataFile();
		if(new File(calculateTenantImageBasePath() + "/" + this.getValue()).exists())
			file.setBaseDir(calculateTenantImageBasePath());
		else
			file.setBaseDir(ResourceManager.getResourcePath(getResourceNode().getProjectId(), null, null));
		file.setTypeDir(this.getType());
		
		file.getMetaworksContext().setWhen(MetaworksContext.WHEN_EDIT);
		file.setUploadedPath(this.getValue());
		file.setMimeType(ResourceNode.findNodeType(this.getValue()));
		return file;
	}
	
	
	public static void handleServlet(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String pathInfo = request.getPathInfo();
		if(pathInfo == null)
			return;
		
		String[] projectIdAndPropertyName;
		
		
		Exception requestException = new Exception("Request query string should have two arguments like '/projectId/propertyName'.");{
			try{
				projectIdAndPropertyName = pathInfo.substring(1).split("/");	
			}catch(Exception e){
				throw requestException;			
			}
			if(projectIdAndPropertyName.length < 2) throw requestException;			
		}
		
		ImageProperty imageProperty = new ImageProperty();
		
		ResourceNode resourceNode = new ResourceNode();
		resourceNode.setProjectId(projectIdAndPropertyName[0]);
		resourceNode.setTenantScope(true);
		imageProperty.setResourceNode(resourceNode);
		imageProperty.setName(projectIdAndPropertyName[1]);
		
		String tenantId = null;{

			//TODO:  [warn] You however need to realize that this is a client-controlled value and can thus be spoofed to something entirely different or even removed. Thus, whatever value it returns, you should not use it for any critical business processes in the backend, but only for presentation control (e.g. hiding/showing/changing certain pure layout parts) and/or statistics.
			String referer = request.getHeader("referer"); 
			if(!UEngineUtil.isNotEmpty(referer))
				referer = request.getRequestURL().toString();

			int tenantIdPos = referer.indexOf(".");
			int protocolPos = referer.indexOf("/");
			if(tenantIdPos > 0){
				tenantId = referer.substring(protocolPos > 0 ? protocolPos + 2 : 0, tenantIdPos);				
				
			}
	
			if("www".equals(tenantId) || "processcodi".equals(tenantId))
				tenantId = null;
			
//			new TenantContext(tenantId); //create unique tenant context for the requested thread.
		}
		
		String imagePath; {
			imagePath = imageProperty.calculateTenantImageBasePath() + File.separator + "image";
			File file = new File(imagePath);
			String filesInDir[] = file.list();
			if(filesInDir.length == 0)
				throw new ServletException("No image file exists.");
			
			imagePath = imagePath + File.separator + filesInDir[0];
		}
		

				
//		
//		for(int i=2; i<projectIdAndPropertyNameAndFileName.length; i++){
//			 imagePath = imagePath + File.separator + projectIdAndPropertyNameAndFileName[i];
//		}
		
		File imageFile = new File(imagePath);
		String extensionName = imagePath.substring(imagePath.lastIndexOf('.')+1);
		
		if(imageFile.exists() && imageFile.isFile()){
			OutputStream out = null;
			try{
				out = response.getOutputStream();
			
				BufferedImage bi = ImageIO.read(imageFile);
				response.setContentType("image/" + extensionName);
				ImageIO.write(bi, extensionName, out);
			}finally{
				if(out != null){
					out.close();
					out = null;
				}
			}
		}

		
	}

}
