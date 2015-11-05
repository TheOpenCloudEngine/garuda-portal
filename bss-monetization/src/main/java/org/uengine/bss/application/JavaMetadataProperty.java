package org.uengine.bss.application;


import org.metaworks.MetaworksContext;
import org.metaworks.annotation.Face;
import org.metaworks.example.ide.SourceCode;
import org.uengine.kernel.NeedArrangementToSerialize;

public class JavaMetadataProperty extends TextMetadataProperty implements NeedArrangementToSerialize {

    public JavaMetadataProperty(){
        setDefaultValue("");
        setSourceCode(new SourceCode());
        this.setMetaworksContext(new MetaworksContext());
        this.getMetaworksContext().setWhen(MetaworksContext.WHEN_NEW);
    }

    @Override
    //@Face(faceClassName="org.uengine.codi.mw3.widget.SourceCodeFace")
    @Face(ejsPath = "genericfaces/richText.ejs")
    public String getDefaultValue() {
        return super.getDefaultValue();
    }

    SourceCode sourceCode;
        public SourceCode getSourceCode() {
            return sourceCode;
        }
        public void setSourceCode(SourceCode sourceCode) {
            this.sourceCode = sourceCode;
        }


    @Override
    public void beforeSerialization() {
        //setDefaultValue(getSourceCode() != null ? getSourceCode().getCode() : null);
    }

    @Override
    public void afterDeserialization() {

    }
}
