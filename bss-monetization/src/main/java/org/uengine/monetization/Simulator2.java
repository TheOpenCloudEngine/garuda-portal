package org.uengine.monetization;

import org.metaworks.ContextAware;
import org.metaworks.MetaworksContext;
import org.uengine.application.App;

public class Simulator2 implements ContextAware{

    MetaworksContext metaworksContext;
        @Override
        public MetaworksContext getMetaworksContext() {
            return metaworksContext;
        }
        @Override
        public void setMetaworksContext(MetaworksContext metaworksContext) {
            this.metaworksContext = metaworksContext;
        }


    public Simulator2(){
        setApp(new App());
       // setInvoice(new Invoice());
        setMetaworksContext(new MetaworksContext());
        getMetaworksContext().setWhen(MetaworksContext.WHEN_EDIT);
    }

    App app;

        public App getApp() {
            return app;
        }

        public void setApp(App app) {
            this.app = app;
        }

//    Invoice invoice;
//
//        public Invoice getInvoice() {
//            return invoice;
//        }
//
//        public void setInvoice(Invoice invoice) {
//            this.invoice = invoice;
//        }



}
