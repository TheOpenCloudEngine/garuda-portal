package org.uengine.codi.mw3.ide;

import org.metaworks.*;
import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.ServiceMethod;
import org.metaworks.component.TreeNode;
import org.metaworks.metadata.MetadataProperty;
import org.oce.garuda.multitenancy.TenantContext;
import org.springframework.beans.factory.annotation.Configurable;
import org.uengine.codi.mw3.model.Popup;
import org.uengine.codi.mw3.model.Session;
import org.uengine.codi.mw3.resource.ResourceManager;
import org.uengine.codi.util.CodiStringUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by soo on 2015. 6. 30..
 */
@Configurable
@Face(
        ejsPath = "dwr/metaworks/org/metaworks/component/TreeNode.ejs",
        ejsPathMappingByContext = {
                "{how:	'tree', face: 'dwr/metaworks/org/metaworks/component/TreeNode.ejs'}",
                "{how:	'resourcePicker', face: 'dwr/metaworks/org/metaworks/metadata/ResourceNodePicker.ejs'}"
        })
public class ResourceNode extends TreeNode implements ContextAware, Cloneable {

    public final static String TYPE_ACTIVITY 	= "activity";
    public final static String TYPE_ROLE 		= "role";
    public final static String TYPE_PROJECT	= "project";

    @AutowiredFromClient
    public Session session;

    @AutowiredFromClient
    public MetadataProperty metadataProperty;


    MetaworksContext metaworksContext;
    public MetaworksContext getMetaworksContext() {
        return metaworksContext;
    }
    public void setMetaworksContext(MetaworksContext metaworksContext) {
        this.metaworksContext = metaworksContext;
    }
    String projectId;
    public String getProjectId() {
        return projectId;
    }
    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }
    String path;
    public String getPath() {
        return path;
    }
    public void setPath(String path) {
        this.path = path;
    }
    boolean hasPick;
    public boolean isHasPick() {
        return hasPick;
    }
    public void setHasPick(boolean hasPick) {
        this.hasPick = hasPick;
    }
    boolean conferenceMode;
    public boolean isConferenceMode() {
        return conferenceMode;
    }
    public void setConferenceMode(boolean conferenceMode) {
        this.conferenceMode = conferenceMode;
    }

    boolean tenantScope;

    public boolean isTenantScope() {
        return tenantScope;
    }
    public void setTenantScope(boolean tenantScope) {
        this.tenantScope = tenantScope;
    }


    String alias;
    public String getAlias() {
        return alias;
    }
    public void setAlias(String alias) {
        this.alias = alias;
    }
    String editorInstanceId;
    public String getEditorInstanceId() {
        return editorInstanceId;
    }
    public void setEditorInstanceId(String editorInstanceId) {
        this.editorInstanceId = editorInstanceId;
    }

    String artifactId;
    public String getArtifactId() {
        return artifactId;
    }
    public void setArtifactId(String artifactId) {
        this.artifactId = artifactId;
    }

    public ResourceNode(){
        setMetaworksContext(new MetaworksContext());
    }

    public ResourceNode(Project project){
//		this.setId(project.getId());

        this.setType(TYPE_PROJECT);
        this.setFolder(true);
        this.setAlias("");
        this.setId(project.getName());
        this.setName(project.getName());
        this.setPath(project.getPath());
        this.setProjectId(project.getName());//TODO: setProjectId --> setProjectName;

//		String relativePath = this.getArtifactId().replace('.', File.separatorChar);
//		File file = new File(this.getPath() + File.separatorChar + relativePath);
        File file = new File(this.getPath());
        if(!file.exists()){
            file.mkdirs();
            System.out.println("mkdirs");

//			NewClass activator = new NewClass();

        }
//		File activatorFile = new File(file.getPath() + File.separatorChar + "Activator.java");
//		try {
//			activatorFile.createNewFile();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

        setMetaworksContext(new MetaworksContext());
    }

    public ArrayList<TreeNode> loadChild() throws Exception {
        ArrayList<TreeNode> child = new ArrayList<TreeNode>();

//		String path = ResourceManager.getResourcePath(
//				getProjectId(),
//				isTenantScope() ? TenantContext.getThreadLocalInstance().getTenantId() : null,
//				getId()
//		);

//        child.addAll(loadChild(this.getPath(), true));
//        child.addAll(loadChild(this.getPath(), false));

        return child;
    }

//    private List<TreeNode> loadChild(String path, boolean isDirectory){
//        List<TreeNode> child = new ArrayList<TreeNode>();
//
//        File file = new File(path);
//
//        String[] childFilePaths = file.list();
//
//        for(int i=0; i<childFilePaths.length; i++){
//            File childFile = new File(file.getAbsolutePath() + File.separatorChar + childFilePaths[i]);
//
//            ResourceNode node = new ResourceNode();
//
//            if(isDirectory != childFile.isDirectory())
//                continue;
//
//            if(isDirectory){
//                node.setType(TreeNode.TYPE_FOLDER);
//                node.setFolder(true);
//            }else{
//                String type = ResourceNode.findNodeType(childFile.getAbsolutePath());
//
//                if(type.equals(TreeNode.TYPE_FILE_PROCESS))
//                    node= new ProcessNode();
//
//                node.setType(type);
//            }
//
//            node.setProjectId(this.getProjectId());
//            node.setParentId(this.getId());
//            node.setName(childFile.getName());
//            node.setId(this.getId() + File.separatorChar + node.getName());
//            node.setAlias(this.getAlias() + File.separatorChar + node.getName());
//            node.setPath(this.getPath() + File.separatorChar + node.getName());
//
//            node.setMetaworksContext(getMetaworksContext());
//
//            child.add(node);
//        }
//
//        return child;
//    }

    @Override
    @ServiceMethod(callByContent=true, except="child", target= ServiceMethodContext.TARGET_APPEND)
    public Object expand() throws Exception {
        return new ToAppend(this, this.loadChild());
    }

    @AutowiredFromClient
    public Project project;

    @ServiceMethod(callByContent=true, except="child", target=ServiceMethodContext.TARGET_POPUP)
    public Object findResource(){

//        Navigator navigator = new Navigator();
//        navigator.setMetaworksContext(new MetaworksContext());
//        navigator.getMetaworksContext().setWhere("resource");
////		Project project = new Project(); //load 가 따로 존재해야 하는가?
////		project.setUseDefault(true); //load 에 아규먼트로 가야하는거 아닌가?
////		project.setId(getProjectId());
//        try {
//            project.load();
//            navigator.load(project);
//            navigator.getResourceTree().getMetaworksContext().setWhere("resource");
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }

//		ResourceTree resourceTree = new ResourceTree();
//		resourceTree.setId(workspace.getId());
//		resourceTree.setNode(workspaceNode);
//
//		navigator.setResourceTree(resourceTree);
//		navigator.setId("popupTree");


        Popup popup = new Popup();
//        popup.setPanel(navigator);
        popup.setName("Project Resource");

        return popup;
    }

//    public Editor beforeAction(){
//        return beforeAction(true);
//    }
//    public Editor beforeAction(boolean isLoad){
//
//        Editor editor = null;
//
//        if(!this.isFolder()){
//            String type = ResourceNode.findNodeType(this.getName());
//
//            this.setType(type);
//
//            if(type.equals(TreeNode.TYPE_FILE_JAVA)){
//                if(this.getMetaworksContext()!=null && "code".equals(this.getMetaworksContext().getWhen())){
//                    editor = new JavaCodeEditor(this);
//                }else{
//                    //editor = new JavaCodeEditor(this);
//                    editor = new FormEditor(this);
//                }
//            }else if(type.equals(TreeNode.TYPE_FILE_PROCESS)){
//                editor = new ProcessEditor(this);
//            }else if(type.equals(TreeNode.TYPE_FILE_VALUECHAIN)){
//                editor = new ValueChainEditor(this);
//            }else if(type.equals(TreeNode.TYPE_FILE_METADATA)){
//                editor = new MetadataEditor(this);
//                try {
//                    ((MetadataEditor)editor).loadPage();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }else{
//                editor = new Editor(this);
//            }
//        }
//
//        if(isLoad)
//            editor.load();
//
//        return editor;
//    }

    @Override
    @ServiceMethod(callByContent=true, except="child", target=ServiceMethodContext.TARGET_APPEND)
    public Object action(){

//        if(this.getMetaworksContext() != null && "resource".equals(this.getMetaworksContext().getWhere())){
//            metadataProperty.setResourceNode(this);
//
//            //픽업되는 순간  xml에 저장하고 load 해서 미리보기
//            this.getMetaworksContext().setHow("resourcePicker");
//            this.getMetaworksContext().setWhen(MetaworksContext.WHEN_VIEW);
//            //this.getMetaworksContext().setWhen("code");
//
//            return new Object[]{new ToOpener(this), new Remover(new Popup())};
//        }else{
//            Editor editor = this.beforeAction();
//
////			InstanceViewThreadPanel instanceViewThreadPanel = new InstanceViewThreadPanel();
////			if(  editor instanceof ProcessEditor ){
////				if( ((ProcessEditor)editor).getProcessDesignerInstanceId() != null ){
////					instanceViewThreadPanel.session = session;
////					instanceViewThreadPanel.setInstanceId(((ProcessEditor)editor).getProcessDesignerInstanceId());
////					instanceViewThreadPanel.load();
////				}
////			}
////			CloudInstanceWindow cloudInstanceWindow = new CloudInstanceWindow();
////			cloudInstanceWindow.setPanel(instanceViewThreadPanel);
//
//            CurrentlySelectedResourceNode currentlySelectedResourceNode = new CurrentlySelectedResourceNode();
//            currentlySelectedResourceNode.setResourceNode(this);
//
//            return new Object[]{ new ToAppend(new CloudWindow("editor"), editor), currentlySelectedResourceNode };
//			return new Object[]{new ToAppend(new CloudWindow("editor"), editor) , new Refresh(cloudInstanceWindow, true) };
//        }
        return null;
    }


    @ServiceMethod(callByContent=true, except="child", mouseBinding="right", target=ServiceMethodContext.TARGET_POPUP)
    public Object[] showContextMenu() {
        session.setClipboard(this);

//        return new Object[]{new ResourceContextMenu(this, session) , new Refresh(session)};
        return null;
    }

    public static String findNodeType(String name){
        String nodeType = TreeNode.TYPE_FILE_TEXT;

        int pos = name.lastIndexOf('.');
        if(pos > -1){
            String ext = name.substring(pos);

            if(".html".equals(ext)){
                nodeType = TreeNode.TYPE_FILE_HTML;
            }else if(".java".equals(ext)){
                nodeType = TreeNode.TYPE_FILE_JAVA;
            }else if(".ejs".equals(ext)){
                nodeType = TreeNode.TYPE_FILE_EJS;
            }else if(".js".equals(ext)){
                nodeType = TreeNode.TYPE_FILE_JS;
            }else if(".form".equals(ext)){
                nodeType = TreeNode.TYPE_FILE_FORM;
            }else if(".wpd".equals(ext) || ".process2".equals(ext) || ".process".equals(ext)){
                nodeType = TreeNode.TYPE_FILE_PROCESS;
            }else if(".valuechain".equals(ext)){
                nodeType = TreeNode.TYPE_FILE_VALUECHAIN;
            }else if(".rule".equals(ext)){
                nodeType = TreeNode.TYPE_FILE_RULE;
            }else if(".css".equals(ext)){
                nodeType = TreeNode.TYPE_FILE_CSS;
            }else if(".jpeg".equals(ext) || ".jpg".equals(ext) || ".gif".equals(ext) || ".png".equals(ext)){
                nodeType = TreeNode.TYPE_FILE_IMAGE;
            }else if(".metadata".equals(ext)){
                nodeType = TreeNode.TYPE_FILE_METADATA;
            }
        }

        return nodeType;
    }

    @ServiceMethod(callByContent=true, except="child", mouseBinding="drag")
    public Object drag() {
        System.out.println("drag : " + this.getId());

        //Project project = workspace.findProject(this.getProjectId());

//		this.setAlias(project.getBuildPath().makeFullClassName(this.getId()));;
        if( this.getProjectId() != null ){
            String id = this.getId();
//			String alias = id.substring(this.getProjectId().length()+1);

            if(ResourceNode.TYPE_FILE_JAVA.equals(this.getType())){
                alias = ResourceNode.makeFullClassName(id);
            }else{
                alias = ResourceNode.makeResourcePath(id);
            }

            this.setAlias(alias);
        }
        session.setClipboard(this);

        return session;
    }


    @ServiceMethod(callByContent=true, mouseBinding="drop", target=ServiceMethodContext.TARGET_APPEND)
    public Object[] drop(){
//
//        if(this.isFolder() && !(this instanceof ProcessNode)){
//            Object clipboard = session.getClipboard();
//
//            if(clipboard instanceof ResourceNode){
//                ResourceNode resourceNode = (ResourceNode)clipboard;
//                if( !this.getId().equals(resourceNode.getParentId()) && !this.getId().equals(resourceNode.getId())){
//                    File file = new File(resourceNode.getPath());
//
//                    File dstFile = new File(this.getPath() + File.separatorChar + resourceNode.getName());
//
//                    if(dstFile.exists())
//                        return null;
//
//                    if(file.exists()){
//                        file.renameTo(dstFile);
//                    }
//
//                    try {
//                        ResourceNode appendResourceNode = (ResourceNode)resourceNode.clone();
//
//                        appendResourceNode.setId(CodiStringUtil.lastLastFileSeparatorChar(this.getId()) + resourceNode.getName());
//                        appendResourceNode.setPath(CodiStringUtil.lastLastFileSeparatorChar(this.getPath()) + resourceNode.getName());
//                        appendResourceNode.setParentId(this.getId());
//
//                        return new Object[]{new Remover(resourceNode), new ToAppend(this, appendResourceNode)};
//                    } catch (Exception e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }

        return null;
    }

    @ServiceMethod
    public void deleteResourcePicker(){
        this.setId(null);
    }

    public static String makeResourcePath(String resourceName){
        if(resourceName != null){
            if(resourceName.indexOf(File.separatorChar) > -1)
                resourceName = resourceName.substring(resourceName.indexOf(File.separatorChar)+1);
            else
                resourceName = null;
        }

        return resourceName;
    }
    public static String makePackageName(String packageName){

        if(packageName != null){
            if(packageName.endsWith(".java")){
                packageName = packageName.substring(0, packageName.length()-5);

                if(packageName.lastIndexOf(File.separatorChar) > -1)
                    packageName = packageName.substring(0, packageName.lastIndexOf(File.separatorChar));
                else
                    packageName = null;
            }

            if(packageName.indexOf(File.separatorChar) > -1)
                packageName = packageName.substring(packageName.indexOf(File.separatorChar)+1);
            else
                packageName = null;

        }

        if(packageName != null)
            packageName = packageName.replace(File.separatorChar, '.');


        return packageName;
    }

    public static String makeResourceName(String resourceName){
        if(resourceName != null){
            if(resourceName.lastIndexOf('.') > -1)
                resourceName = resourceName.substring(0, resourceName.lastIndexOf('.'));

            if(resourceName.indexOf(File.separatorChar) > -1)
                resourceName = resourceName.substring(resourceName.lastIndexOf(File.separatorChar)+1);
        }

        return resourceName;
    }

    public static String makeClassName(String className){
        className = ResourceNode.makeResourceName(className);
        if(className != null){
            className = className.replace(File.separatorChar, '.');
        }

        return className;
    }

    public static String makeFullClassName(String id){
        String packageName = ResourceNode.makePackageName(id);
        String className = ResourceNode.makeClassName(id);

        if(packageName == null)
            return className;
        else
            return packageName + "." + className;

    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        ResourceNode cloneObject = (ResourceNode)super.clone();

        return cloneObject;
    }

    public Object retrieveResource() throws Exception{
        if(tenantScope){
            return ResourceManager.getTenantResource(getProjectId(), getId());
        }else{
            return ResourceManager.getAppResource(getProjectId(), getId());
        }
    }


    public String resourcePath() throws Exception{
        return ResourceManager.getResourcePath(getProjectId(), tenantScope ? TenantContext.getThreadLocalInstance().getTenantId() : null, getId());

    }


    public Object[] run() {

//        Runner runner = null;
//
//        if(!this.isFolder()){
//            String type = ResourceNode.findNodeType(this.getName());
//
//            this.setType(type);
//
//            if(type.equals(TreeNode.TYPE_FILE_PROCESS)){
//                runner = new ProcessRunner(this);
//
//                try {
//                    autowire(runner);
//                } catch (IllegalAccessException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        }
//
//        if(runner!=null){
//            return runner.run();
//        }

        return null;
    }
}