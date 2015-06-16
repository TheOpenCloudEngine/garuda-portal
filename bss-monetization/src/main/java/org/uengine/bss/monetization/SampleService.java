package org.uengine.bss.monetization;


import org.uengine.bss.metering.Metered;

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
