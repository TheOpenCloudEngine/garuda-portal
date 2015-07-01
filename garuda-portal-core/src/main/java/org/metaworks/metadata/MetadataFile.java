package org.metaworks.metadata;


import org.metaworks.website.MetaworksFile;

import java.io.*;


//@Face(ejsPath="org/metaworks/website/MetaworksFile.ejs") //only reuse the ejs not ejs.js too
public class MetadataFile extends MetaworksFile {   //TODO should be renamed to 'MetadataUploadFile' or something. (ambuiguos with MetadataXML)
	
	public MetadataFile() {
		setAuto(false); //means auto upload
	}
	
	String baseDir;
		public String getBaseDir() {
			return baseDir;
		}
		public void setBaseDir(String baseDir) {
			this.baseDir = baseDir;
		}
		
	String typeDir;
		public String getTypeDir() {
			return typeDir;
		}
		public void setTypeDir(String typeDir) {
			this.typeDir = typeDir;
		}
		
	@Override
	public String overrideUploadPathPrefix() {
		return baseDir;
	}
	
	
	@Override
	public Object[] upload() throws FileNotFoundException, IOException, Exception{
		if(this.getFileTransfer() == null || this.getFileTransfer().getFilename()==null || this.getFileTransfer().getFilename().length() <= 0) 
			throw new Exception("No file attached");
		
		String prefix = "";
		String uploadPrefix = overrideUploadPathPrefix();
		if( uploadPrefix != null ){
			String[] tempStr = uploadPrefix.split(",");
			if( tempStr.length >0 ){
				prefix = tempStr[tempStr.length - 1];
			}else{
				prefix = uploadPrefix;
			}
		}
		
		String filePath = this.getFileTransfer().getFilename();
		String uploadPath = prefix + File.separatorChar + filePath;
		new File(uploadPath).getParentFile().mkdirs();
		
		InputStream is = null;
		FileOutputStream os = null;
		
		try {						
			is = this.getFileTransfer().getInputStream();
			os = new FileOutputStream(uploadPath);
			
			MetaworksFile.copyStream(is, os);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(os != null)
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			if(is != null)
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
		}
		
		
		setUploadedPath(filePath); //only when the file has been successfully uploaded, this value is set, that means your can download later
		setMimeType(this.getFileTransfer().getMimeType());
		
		this.setFileTransfer(null); //ensure to clear the data

		return new Object[0];
	}
}
