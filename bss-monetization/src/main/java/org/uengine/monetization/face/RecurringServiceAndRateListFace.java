package org.uengine.monetization.face;

import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.widget.ListFace;
import org.uengine.application.App;
import org.uengine.monetization.RecurringServiceAndRate;

public class RecurringServiceAndRateListFace extends ListFace<RecurringServiceAndRate> {
    @AutowiredFromClient
    public App app;

}
