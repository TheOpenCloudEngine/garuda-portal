package org.uengine.monetization.face;

import org.metaworks.Face;
import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.component.SelectBox;
import org.metaworks.dao.TransactionContext;
import org.uengine.application.App;
import org.uengine.monetization.Service;

import java.util.ArrayList;

public class ServiceSelectorFace extends SelectBox implements Face<Service> {

    @AutowiredFromClient
    public App app;

    @Override
    public void setValueToFace(Service value) {
        ArrayList<String> options = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();


        App appInLoadingTime = (App)TransactionContext.getThreadLocalInstance().getSharedContext("app");

        if(appInLoadingTime!=null)
            app = appInLoadingTime;


        if(app!=null && app.getServiceList()!=null && app.getServiceList().size() > 0){


            for(Service service : app.getServiceList()){
                String displayName = service.getName()!=null ? (service.getName().trim().length() > 0 ?  service.getName() : service.getId()) : service.getId();

                options.add(displayName);
                values.add(service.getId());
            }

        }else{
            if(value!=null){
                options.add(value.getId());
                values.add(value.getId());
            }

        }

        setOptionNames(options);
        setOptionValues(values);

        setSelectedValue(value.getId());
    }

    @Override
    public Service createValueFromFace() {
        Service service = new Service();
        service.setId(getSelected()); //only contains the reference id

        return service;
    }

//    @ServiceMethod(eventBinding = "onload")
//    public void refreshOptions(){
//        setValueToFace(createValueFromFace());
//    }
}
