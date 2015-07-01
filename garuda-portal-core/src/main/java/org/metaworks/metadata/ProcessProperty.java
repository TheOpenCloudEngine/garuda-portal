package org.metaworks.metadata;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.metaworks.MetaworksContext;
import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.annotation.Available;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.ServiceMethod;
import org.uengine.codi.mw3.ide.Project;
import org.uengine.codi.mw3.ide.libraries.ProcessNode;

@Face(ejsPath = "dwr/metaworks/org/metaworks/metadata/MetadataProperty.ejs",
		ejsPathMappingByContext={
	"{where: 'ide', face: 'dwr/metaworks/org/metaworks/metadata/ProcessProperty.ejs'}",
	"{where: 'ssp', face: 'dwr/metaworks/org/metaworks/metadata/ProcessProperty.ejs'}"
})
//@XStreamAlias("MetadataProperty")
public class ProcessProperty extends MetadataProperty{
	
	@Override
	public MetadataProperty showPropertyDetail() throws Exception {
//		ProcessViewPanel processViewPanel = new ProcessViewPanel();
//		processViewPanel.setDefId(this.getValue());
//		processViewPanel.setAlias(this.getValue());
//		processViewPanel.setViewType("definitionView");
//		processViewPanel.setProjectId(getResourceNode().getProjectId());
//		processViewPanel.load();
//
//		setFilePreview(processViewPanel);// TODO: change name to setPreview(...)
//		getFile().setTypeDir(this.getType());
//
//		return super.showPropertyDetail();
		return null;
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	public ProcessProperty() {
		setType(MetadataProperty.PROCESS_PROP);
	}
	
	@AutowiredFromClient
	@XStreamOmitField
	public Project project;

	@Available(when = MetaworksContext.WHEN_VIEW)
	@ServiceMethod(callByContent = true, target=ServiceMethodContext.TARGET_POPUP)
	public Object modify() throws Exception {
		
		// TODO : 살려야함 cjw
		Project project = workspace.findProject(this.getResourceNode().getProjectId());

		ProcessNode node = new ProcessNode();
		node.setTenantScope(true);
		node.setId(this.getValue());
		node.setName(this.getValue());  //TODO: this.getValue() ? 
		node.setProjectId(this.getResourceNode().getProjectId());
		
//		ResourceManager.getTenantResource(project.getId(), this.getValue()); //create new one for tenant if don't exist ever
//
//		ProcessEditor processEditor = new ProcessEditor(node);
//		processEditor.setUseClassLoader(true);
//		processEditor.load();
//
//		ModalWindow modalWindow = new ModalWindow(processEditor, 0, 0, "$metadata.process.edit");
//
//		modalWindow.getButtons().put("$Save", "save");
//		modalWindow.getButtons().put("$Cancel", null);
//		modalWindow.getCallback().put("$Save", "changeFile");
//
//		return modalWindow;
		return null;
		
		//return null;
	}		
	
	@ServiceMethod(callByContent=true, bindingHidden=true, bindingFor="file", eventBinding={"uploaded"})
	public void changeFile() throws Exception {
		
		//파일첨부 클릭 시 메타데이터 파일 바로 수정
//		if(this.getFile().getUploadedPath() != null){
//
//			int index = metadataXML.properties.indexOf(this);
//			if( index > 0 ){
//				String metadataFilePath = metadataXML.getFilePath() ;
//
//				MetadataProperty editProperty = metadataXML.properties.get(index);
//				editProperty.setName(this.getName());
//				editProperty.setChange(true);
//				editProperty.setType(this.getType());
//
//
//				MetadataFile file = new MetadataFile();
//				file.setTypeDir(this.getType());
//				file.setUploadedPath(this.getFile().getUploadedPath());
//				file.getMetaworksContext().setWhen(MetaworksContext.WHEN_EDIT);
//				file.setMimeType(ResourceNode.findNodeType(this.getFile().getFilename()));
//				file.setFileTransfer(this.getFile().getFileTransfer());
//
//				editProperty.setFile(file);
//				editProperty.setValue(file.getUploadedPath());
//				editProperty.getFile().getMetaworksContext().setWhen(MetaworksContext.WHEN_EDIT);
//
//
//				MetadataEditor metadataEditor = new MetadataEditor();
//				metadataEditor.setResourceNode(new ResourceNode());
//				metadataEditor.getResourceNode().setPath(metadataFilePath);
//				metadataEditor.setContent(metadataXML.toXmlXStream());
//				metadataEditor.save();
//			}
//		}else {
//			ProcessViewPanel processViewPanel = new ProcessViewPanel();
//			processViewPanel.setDefId(this.getValue());
//			processViewPanel.setAlias(this.getValue());
//			processViewPanel.setViewType("definitionView");
//			processViewPanel.setProjectId(this.getResourceNode().getProjectId());
//			processViewPanel.load();
//			setFilePreview(processViewPanel);
//		}
	}
}
