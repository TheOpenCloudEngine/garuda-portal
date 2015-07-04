package org.uengine.garuda.client.user.entity;

import java.io.Serializable;
import java.util.HashMap;

/**
 * Created by hoo.lim on 7/3/2015.
 */
public class GarudaResponseBody implements Serializable {
    String returnCode;

    HashMap returnMap = new HashMap();

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public HashMap getReturnMap() {
        return returnMap;
    }

    public void setReturnMap(HashMap returnMap) {
        this.returnMap = returnMap;
    }
}
