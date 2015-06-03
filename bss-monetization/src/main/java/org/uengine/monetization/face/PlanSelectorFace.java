package org.uengine.monetization.face;


import org.metaworks.annotation.ServiceMethod;
import org.uengine.application.App;
import org.metaworks.Face;
import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.component.SelectBox;
import org.uengine.monetization.Plan;

import java.util.ArrayList;

public class PlanSelectorFace extends SelectBox implements Face<Plan> {

    @AutowiredFromClient
    public App app;

    @Override
    public void setValueToFace(Plan value) {
        if(value == null) return;

        ArrayList<String> options = new ArrayList<String>();
        ArrayList<String> values = new ArrayList<String>();

        if(app!=null){


            for(Plan plan : app.getPlanList()){
                options.add(plan.getName());
                values.add(plan.getId());
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

    @ServiceMethod(callByContent = true)
    public void refresh(){
        setValueToFace(createValueFromFace());
    }

    @Override
    public Plan createValueFromFace() {
        Plan plan = new Plan();
        plan.setId(getSelected()); //only contains the reference id

        return plan;
    }
}
