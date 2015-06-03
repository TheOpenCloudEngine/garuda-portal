package org.uengine.monetization.face;

import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.widget.ListFace;
import org.uengine.application.App;
import org.uengine.monetization.OneTimeServiceAndRate;

public class OneTimeServiceAndRateListFace extends ListFace<OneTimeServiceAndRate> {
    @AutowiredFromClient
    public App app;
}
