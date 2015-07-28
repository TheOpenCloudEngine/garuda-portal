package org.uengine.garuda.multitenancy;

/**
 * Created by hoo.lim on 7/5/2015.
 */
import java.io.FileNotFoundException;
import java.lang.reflect.InvocationTargetException;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import org.codehaus.janino.CompileException;
import org.codehaus.janino.Parser;
import org.codehaus.janino.Scanner;
import org.codehaus.janino.ScriptEvaluator;
import org.metaworks.spring.SpringConnectionFactory;
import org.oce.garuda.multitenancy.AppDbRepositorySimple;
import org.oce.garuda.multitenancy.TenantContext;
import org.oce.garuda.multitenancy.TenantSpecific;
import org.springframework.beans.factory.annotation.Autowired;
import org.uengine.bss.application.App;
import org.uengine.bss.application.MetadataProperty;
import org.uengine.bss.application.TenantApp;
import org.uengine.bss.application.TextMetadataProperty;
import org.uengine.processmanager.ProcessManagerRemote;

@WebService(endpointInterface = "org.oce.garuda.multitenancy.TenantSpecific")
public class TenantSpecificImpl implements TenantSpecific {

    @Autowired
    ProcessManagerRemote processManager;

    @Autowired
    public SpringConnectionFactory connectionFactory;

    HashMap<String, Map> metadataCache = new HashMap<String, Map>();

    @WebMethod(action = "getMetadata")
    public String getMetadata(@WebParam String tenantId, @WebParam String appKey, @WebParam String metaDataKey) {
        // TODO Auto-generated method stub
        TenantContext tenantContext = new TenantContext(tenantId);

        App app = null;
        try {
            app = TenantApp.load(appKey);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }

        for(MetadataProperty metadataProperty : app.getMetadataPropertyList()){
            if(metadataProperty instanceof TextMetadataProperty && Objects.equals(metaDataKey,
                    ((TextMetadataProperty) metadataProperty).getKey())){
                return ((TextMetadataProperty) metadataProperty).getDefaultValue();
            }
        }

        return null;
    }

    @WebMethod(action = "runRule")
    public String runRule(@WebParam String tenantId, @WebParam String appKey, @WebParam String ruleKey, @WebParam String[] parameters) {
        String ruleString = getMetadata(tenantId, appKey, ruleKey);

        String[] parameterNames = new String[parameters.length];
        Class[] parameterTypes = new Class[parameters.length];
        for(int i=0; i<parameters.length; i++){
            parameterNames[i] = "arg" + (i+1);
            parameterTypes[i] = String.class;
        }

        ScriptEvaluator se = null;
        try {
            se = new ScriptEvaluator(ruleString, Object.class, parameterNames, parameterTypes);

            return se.evaluate(parameters).toString();

        } catch (Exception e){

            return e.toString();

        }


    }

    @Override
    public String initProcess(String tenantId, String appKey, String metaDataKey) throws RemoteException {
        return null;
    }

    @Override
    public void executeProcess(String instanceId) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setProcessVariable(String instanceId, String variableKey,
                                   String value) {
        // TODO Auto-generated method stub

    }

    @Override
    public String getProcessVariable(String instanceId, String variableKey) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public AppDbRepositorySimple getAppDbRepository(String tenantId,
                                                    String appName) throws Exception {

        return null;
    }

}