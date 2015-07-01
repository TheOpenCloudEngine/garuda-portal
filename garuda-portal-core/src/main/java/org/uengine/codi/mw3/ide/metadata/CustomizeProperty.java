package org.uengine.codi.mw3.ide.metadata;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import org.metaworks.annotation.Face;
import org.metaworks.annotation.Hidden;
import org.metaworks.annotation.ServiceMethod;
import org.metaworks.metadata.MetadataProperty;
import org.metaworks.metadata.MetadataXML;
import org.uengine.codi.mw3.ide.Project;
import org.uengine.codi.mw3.ide.ResourceNode;
import org.uengine.codi.mw3.resource.ResourceManager;
import org.uengine.kernel.GlobalContext;

@Face(ejsPath="dwr/metaworks/genericfaces/CleanObjectFace.ejs")
public class CustomizeProperty {
	
	public CustomizeProperty() {
		// TODO Auto-generated constructor stub
	}
	
	MetadataXML metadataXML;
		public MetadataXML getMetadataXML() {
			return metadataXML;
		}
		public void setMetadataXML(MetadataXML metadataXML) {
			this.metadataXML = metadataXML;
		}

	ResourceNode resourceNode;
		@Hidden
		public ResourceNode getResourceNode() {
			return resourceNode;
		}
		public void setResourceNode(ResourceNode resourceNode) {
			this.resourceNode = resourceNode;
		}
		
		
	@Hidden
	@ServiceMethod(callByContent=true)
	public void load() throws Exception {
		
		try{
		
			MetadataXML metadataXML = (MetadataXML) ResourceManager.getAppResource(getResourceNode().getProjectId(), Project.METADATA_FILENAME);
			metadataXML.init(getResourceNode());
			setMetadataXML(metadataXML);
		}catch(FileNotFoundException e){
			
			MetadataXML metadata = new MetadataXML();
			//metadata.setFilePath(ResourceManager.getResourcePath(getResourceNode().getProjectId(), null, Project.METADATA_FILENAME));
			
			
			metadata.init(getResourceNode());

			setMetadataXML(metadata);
//			ResourceManager.setTenantResource(getResourceNode().getProjectId(), Project.METADATA_FILENAME, new MetadataXML());
		}
//		try{
//			setMetadataXML((MetadataXML)GlobalContext.deserialize(new FileInputStream(this.getResourceNode().getPath()), MetadataXML.class));
//		}catch(Exception e){
//			// 메타데이터 파일 생성
//			MetadataXML metadata = new MetadataXML();
//			metadata.setProperties(new ArrayList<MetadataProperty>());
//		
//			metadata.setFilePath(resourceNode.getPath());
//			metadata.setProjectId(resourceNode.getProjectId());
//
//		}
	//	setMetadataXML(metadata.loadWithResourceNode(this.getResourceNode()));
	}

}
