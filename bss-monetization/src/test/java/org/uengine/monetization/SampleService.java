package org.uengine.monetization;

import org.uengine.metering.Metered;

public class SampleService{

    @Metered("serviceA")
    public void serviceAMethod(){

    }

    @Metered("serviceB")
    public void serviceBMethod(){
        throw new RuntimeException("error");
    }

    @Metered("serviceC")
    public void serviceCMethod(){

    }

}
