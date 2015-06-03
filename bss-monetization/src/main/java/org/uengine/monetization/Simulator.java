package org.uengine.monetization;

import org.metaworks.ContextAware;
import org.metaworks.MetaworksContext;
import org.metaworks.widget.layout.Layout;
import org.uengine.application.App;

public class Simulator extends Layout implements ContextAware{

    MetaworksContext metaworksContext;
        @Override
        public MetaworksContext getMetaworksContext() {
            return metaworksContext;
        }
        @Override
        public void setMetaworksContext(MetaworksContext metaworksContext) {
            this.metaworksContext = metaworksContext;
        }


    public Simulator(){
        setWest(new App());
//        setCenter(new Invoice());

        getMetaworksContext().setWhen(MetaworksContext.WHEN_EDIT);
    }

}
