package org.uengine.bss.monetization.face;

import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.widget.ListFace;
import org.uengine.bss.application.App;
import org.uengine.bss.monetization.OneTimeServiceAndRate;

public class OneTimeServiceAndRateListFace extends ListFace<OneTimeServiceAndRate> {
    @AutowiredFromClient
    public App app;
}
