package org.metaworks.metadata;

import com.thoughtworks.xstream.annotations.XStreamOmitField;
import org.metaworks.MetaworksContext;
import org.metaworks.annotation.Face;
import org.metaworks.annotation.Hidden;

import java.io.FileNotFoundException;
import java.io.IOException;

//@XStreamAlias("MetadataProperty")
@Face(ejsPath = "dwr/metaworks/org/metaworks/metadata/MetadataProperty.ejs",
ejsPathMappingByContext={
"{where: 'ide', face: 'dwr/metaworks/org/metaworks/metadata/StringProperty.ejs'}",
"{where: 'ssp', face: 'dwr/metaworks/org/metaworks/metadata/StringProperty.ejs'}"
})public class StringProperty extends MetadataProperty{

	public StringProperty() {
		setType(MetadataProperty.STRING_PROP);
	}
	
	@XStreamOmitField
	MetadataFile file;
		@Hidden
		public MetadataFile getFile() {
			return file;
		}
		public void setFile(MetadataFile file) {
			this.file = file;
		}
	
	@Override
	public Object[] save() throws FileNotFoundException, IOException, Exception {
		return super.save();
	}
	
	
	@Override
	public MetadataProperty showPropertyDetail() throws Exception {
		
		MetadataProperty superValue = super.showPropertyDetail();

		superValue.getMetaworksContext().setWhen(MetaworksContext.WHEN_EDIT);
		
		return superValue;
	}
	
	
	@Override
	protected Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
	
	
}