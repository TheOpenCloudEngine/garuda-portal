package org.uengine.codi.mw3.model;

import org.metaworks.EventContext;
import org.metaworks.Refresh;
import org.metaworks.ServiceMethodContext;
import org.metaworks.ToEvent;
import org.metaworks.annotation.ServiceMethod;
import org.springframework.stereotype.Component;
import org.uengine.codi.mw3.marketplace.AppMapping;
import org.uengine.codi.mw3.marketplace.Marketplace;

import java.util.ArrayList;

/**
 * Created by hoo.lim on 6/19/2015.
 */
@Component
public class GarudaAllAppList extends AllAppList{

    ArrayList<AppMapping> myAppsList;
    public ArrayList<AppMapping> getMyAppsList() {
        return myAppsList;
    }
    public void setMyAppsList(ArrayList<AppMapping> myAppsList) {
        this.myAppsList = myAppsList;
    }

    @ServiceMethod(callByContent = true, target= ServiceMethodContext.TARGET_APPEND)
    public Object[] goAppStore() throws Exception{
        Marketplace marketplace = new Marketplace(session);

        return new Object[]{new Refresh(marketplace), new ToEvent(ServiceMethodContext.TARGET_SELF, EventContext.EVENT_CLOSE)};
    }
}
