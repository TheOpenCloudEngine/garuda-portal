package org.metaworks.metadata;

import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.ServiceMethod;

@Face(ejsPath = "dwr/metaworks/org/metaworks/metadata/MetadataProperty.ejs",
		ejsPathMappingByContext={
	"{where: 'ide', face: 'dwr/metaworks/org/metaworks/metadata/FormProperty.ejs'}",
	"{where: 'ssp', face: 'dwr/metaworks/org/metaworks/metadata/FormProperty.ejs'}"
})
//@XStreamAlias("MetadataProperty")
public class FormProperty extends MetadataProperty{
	
	public FormProperty() {
		setType(MetadataProperty.FORM_PROP);
	}
	
	@Override
	public MetadataProperty showPropertyDetail() throws Exception{
//		String formName = value.split("\\.")[0];
//
//		this.getResourceNode().setTenantScope(true);
//		CodiClassLoader cl = CodiClassLoader.createClassLoader(this.getResourceNode().getProjectId(), this.getResourceNode().isTenantScope() ? TenantContext.getThreadLocalInstance().getTenantId() : null);
//		Thread.currentThread().setContextClassLoader(cl);
//
//		MetaworksRemoteService.getInstance().getMetaworksType(formName);
//		Object previewForm = cl.loadClass(formName).newInstance();
//		setFilePreview(previewForm);
//
//		this.getResourceNode().setId(this.getResourceNode().getProjectId() + "/" + value);
//		this.getResourceNode().setName(value);
//
//		return super.showPropertyDetail();
		return null;
	}
	
	@Override
	@ServiceMethod(payload = {"resourceNode"}, target=ServiceMethodContext.TARGET_POPUP)
	public Object modify() throws Exception {
		
//		FormEditor formEditor = new FormEditor(this.getResourceNode());
//		formEditor.load();
//		formEditor.getResourceNode().setTenantScope(true);
//		formEditor.getResourceNode().setName(this.getResourceNode().getName());
//
//		ModalWindow modalWindow = new ModalWindow(formEditor, 0, 0, "폼 편집");
//
//		modalWindow.getButtons().put("$Save", "save");
//		modalWindow.getButtons().put("$Cancel", null);
//		modalWindow.getCallback().put("$Save", "changeFile");
//
//		return modalWindow;
		return null;
	}
	
	@ServiceMethod(payload = {"resourceNode", "value"})
	public void changeFile() throws Exception {
//		String formName = value.split("\\.")[0];
//		CodiClassLoader cl = CodiClassLoader.createClassLoader(this.getResourceNode().getProjectId(), this.getResourceNode().isTenantScope() ? TenantContext.getThreadLocalInstance().getTenantId() : null);
//		Thread.currentThread().setContextClassLoader(cl);
//
//		MetaworksRemoteService.getInstance().getMetaworksType(formName);
//		Object previewForm = cl.loadClass(formName).newInstance();
//		setFilePreview(previewForm);
		
	}

}
