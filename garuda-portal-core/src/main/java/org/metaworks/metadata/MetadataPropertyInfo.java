package org.metaworks.metadata;

import org.metaworks.annotation.Face;
import org.metaworks.annotation.Hidden;
import org.uengine.codi.mw3.ide.ResourceNode;

@Face(ejsPath="dwr/metaworks/genericfaces/CleanObjectFace.ejs"  , options={"disableHeight"}, values={"true"})
public class MetadataPropertyInfo {
	
	MetadataProperty newMetadataProperty;
		public MetadataProperty getNewMetadataProperty() {
			return newMetadataProperty;
		}
		public void setNewMetadataProperty(MetadataProperty newMetadataProperty) {
			this.newMetadataProperty = newMetadataProperty;
		}
		
	ResourceNode resourceNode;
		@Hidden
		public ResourceNode getResourceNode() {
			return resourceNode;
		}
		public void setResourceNode(ResourceNode resourceNode) {
			this.resourceNode = resourceNode;
		}
}
