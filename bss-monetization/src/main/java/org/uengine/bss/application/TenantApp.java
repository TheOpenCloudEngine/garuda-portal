package org.uengine.bss.application;

import com.thoughtworks.xstream.XStream;
import org.metaworks.Remover;
import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.Payload;
import org.metaworks.annotation.ServiceMethod;
import org.metaworks.dao.TransactionContext;
import org.metaworks.widget.ModalWindow;
import org.oce.garuda.multitenancy.TenantContext;
import org.uengine.bss.monetization.*;
import org.uengine.kernel.GlobalContext;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * Created by hoo.lim on 6/30/2015.
 */
public class TenantApp extends App{


    @Override
    protected OutputStream getResourceAsOutputStream(String appKey, String alias) throws FileNotFoundException {
        return _getResourceAsOutputStream(appKey, alias);
    }

    protected static InputStream getResourceAsStream(String appKey, String alias) throws Exception {
        return _getResourceAsStream(appKey, alias);
    }


}