package org.uengine.codi.mw3.ide.editor;

import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.*;
import org.metaworks.common.MetaworksUtil;
import org.uengine.codi.mw3.ide.ResourceNode;
import org.uengine.codi.mw3.model.Session;
import org.uengine.codi.mw3.resource.ResourceManager;
import org.uengine.kernel.GlobalContext;
import org.uengine.util.UEngineUtil;

import java.io.*;

public class Editor {
	
	public final static String TYPE_JAVA = "java";
	
	@AutowiredFromClient
	public Session session;
	
	ResourceNode resourceNode;
		@Hidden
		public ResourceNode getResourceNode() {
			return resourceNode;
		}
		public void setResourceNode(ResourceNode resourceNode) {
			this.resourceNode = resourceNode;
		}

	String id;
		@Id
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}

	String name;
		@Name
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}

	String type;
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}

	String content;
		public String getContent() {
			return content;
		}
		public void setContent(String content) {
			this.content = content;
		}
		
	boolean loaded;
		public boolean isLoaded() {
			return loaded;
		}
		public void setLoaded(boolean loaded) {
			this.loaded = loaded;
		}
		
	boolean useClassLoader;
		public boolean isUseClassLoader() {
			return useClassLoader;
		}
		public void setUseClassLoader(boolean useClassLoader) {
			this.useClassLoader = useClassLoader;
		}
		
	public Editor(){
		
	}
	
	public Editor(ResourceNode resourceNode){
		this.setResourceNode(resourceNode);
		
		this.setId(resourceNode.getId());
		this.setName(resourceNode.getName());
		this.setType(resourceNode.getType());
	}
	
	public Editor(String id){
		this(id, null);
	}
	
	public Editor(String id, String type){
		if(id == null)
			return;
		
		char separatorChar = File.separatorChar;

		this.setId(id);
		this.setType(type);
		
		if(id.lastIndexOf(separatorChar) > -1){
			this.setName(id.substring(id.lastIndexOf(separatorChar)+1));
		}		
	}
	
	
	@ServiceMethod(payload={"resourceNode", "processNode" , "type"}, target=ServiceMethodContext.TARGET_NONE)
	public String load() {
		InputStream is = null;
		ByteArrayOutputStream bao = null;
		
		try {
			bao = new ByteArrayOutputStream();
			
			if(this.isUseClassLoader()){
				try {	
					is = Thread.currentThread().getContextClassLoader().getResourceAsStream(this.getId().substring(this.getResourceNode().getProjectId().length()+1));
				} catch (Exception e) {
					e.printStackTrace();
			}
			}else{
//				String path = ResourceManager.getResourcePath(getResourceNode().getProjectId(), getResourceNode().isTenantScope() ?  TenantContext.getThreadLocalInstance().getTenantId() : null, getResourceNode().getId());
				String path = getResourceNode().getPath();
				
				File file = new File(path);
//				if(ResourceNode.TYPE_FILE_PROCESS.equals(this.getType())){
//				}else{
//				}
				if(file.exists()){					
					try {
						is = new FileInputStream(file);
					} catch (Exception e) {
						e.printStackTrace();
					}			
				}
			}
				
			MetaworksUtil.copyStream(is, bao);
			
			this.setContent(bao.toString(GlobalContext.ENCODING));

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(is != null){
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				
			if(bao != null){
				try {
					bao.close();
					bao = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		return this.getContent();
	}
	
	public static String loadByPath(String path) {
		InputStream is = null;
		ByteArrayOutputStream bao = null;
		String contents = null;
		try {
			bao = new ByteArrayOutputStream();
			
			//if(TYPE_FILE.equals(this.getType())){
			File file = new File(path);
			if(file.exists()){					
				try {
					is = new FileInputStream(file);
				} catch (Exception e) {
					e.printStackTrace();
				}			
			}
			MetaworksUtil.copyStream(is, bao);
			
			contents = bao.toString(GlobalContext.ENCODING);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(is != null){
				try {
					is.close();
					is = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(bao != null){
				try {
					bao.close();
					bao = null;
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
		
		return contents;
	}
	
	@ServiceMethod(payload={"resourceNode", "content"}, keyBinding="Ctrl+S")
	public Object save() throws Exception {
		OutputStream fos = null;
		try{
			if(this.getResourceNode().isTenantScope())
				fos = ResourceManager.getTenantResourceAsOutputStream(this.getResourceNode().getProjectId(), this.getResourceNode().getName());
			else
				fos = ResourceManager.getAppResourceAsOutputStream(this.getResourceNode().getProjectId(), this.getResourceNode().getName());
			
			String definitionInString = this.getContent();
			ByteArrayInputStream bai = new ByteArrayInputStream(definitionInString.getBytes(GlobalContext.ENCODING));
			UEngineUtil.copyStream(bai, fos);
		} catch (Exception e) {
			throw e;//e.printStackTrace();
		} finally{
			if(fos!=null)
				fos.close();
		}
		
		
//		FileWriter writer = null;
//
//		try {
//			String path = this.getResourceNode().getPath();
//			
//			File file = new File(path);
//			if(!file.exists()){
//				file.getParentFile().mkdirs();
//				file.createNewFile();
//			}
//
//			writer = new FileWriter(file);
//			writer.write(this.getContent()!=null?this.getContent():"");
//
//		} catch (IOException e) {
//			throw e;
//		} finally{
//			if(writer != null)
//				try {
//					writer.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//		}
		
		return null;
	}
	
	@ServiceMethod(payload={"content"}, target=ServiceMethodContext.TARGET_NONE)
	public Object parsing() {
//		JavaParser javaParser = new JavaParser();
//
//		javaParser.setContent(this.getContent());
//		javaParser.create();
//
//		return javaParser;
		return null;
	}
	
	@ServiceMethod(payload={"resourceNode"})
	public Object run() throws Exception {
		return null;
	}
}
