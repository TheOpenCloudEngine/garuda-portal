package org.uengine.monetization.face;


import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.widget.ListFace;
import org.uengine.application.App;
import org.uengine.monetization.ServiceAndRate;

public class ServiceAndRateListFace extends ListFace<ServiceAndRate> {

    @AutowiredFromClient
    public App app;
}
