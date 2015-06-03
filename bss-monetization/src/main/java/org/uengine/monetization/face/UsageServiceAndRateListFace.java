package org.uengine.monetization.face;


import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.widget.ListFace;
import org.uengine.application.App;
import org.uengine.monetization.UsageServiceAndRate;

public class UsageServiceAndRateListFace extends ListFace<UsageServiceAndRate> {
    @AutowiredFromClient
    public App app;

}
