package org.uengine.bss.monetization.service;

import org.metaworks.dao.IDAO;
import org.metaworks.dao.MetaworksDAO;
import org.metaworks.spring.SpringConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.uengine.bss.monetization.Plan;
import org.uengine.bss.monetization.api.TenantService;

import javax.ws.rs.FormParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by hoo.lim on 7/7/2015.
 */
@Component
public class TenantServiceImpl implements TenantService{
    @Autowired
    public SpringConnectionFactory springConnectionFactoryForMetaworks;

    @Override
    @Transactional
    public Response tenantSignUp(String tenantId, String tenantName, String userId, String userName, String password) {
        try {
            // Tenant 데이타를 테이블에 넣음.
            String sqlStatement = "SELECT * FROM tenant WHERE id=?id ";

            IDAO tenantDao = MetaworksDAO.createDAOImpl(springConnectionFactoryForMetaworks, sqlStatement, IDAO.class);

            tenantDao.set("id", tenantId);
            tenantDao.select();

            if(tenantDao.size() == 0){
                sqlStatement = "INSERT INTO tenant(id, name) VALUES(?id, ?name)";

                tenantDao = MetaworksDAO.createDAOImpl(springConnectionFactoryForMetaworks, sqlStatement, IDAO.class);

                tenantDao.set("id", tenantId);
                tenantDao.set("name", tenantName);

                tenantDao.insert();
            }

            sqlStatement = "SELECT * FROM user WHERE id=?id";

            IDAO userDao = MetaworksDAO.createDAOImpl(springConnectionFactoryForMetaworks, sqlStatement, IDAO.class);

            userDao.set("id", userId);
            userDao.select();

            if(userDao.size() == 0){
                sqlStatement = "INSERT INTO user(id, name, password, tenantId) VALUES(?id, ?name, ?password, ?tenantId)";

                userDao = MetaworksDAO.createDAOImpl(springConnectionFactoryForMetaworks, sqlStatement, IDAO.class);

                userDao.set("id", userId);
                userDao.set("name", userName);
                userDao.set("password", password);
                userDao.set("tenantId", tenantId);
                userDao.insert();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.serverError().entity(e.getMessage())
                    .header("Access-Control-Allow-Origin", "*")
                    .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                    .build();
        }

        return Response.ok()
                .entity("SUCCESS")
                .header("Access-Control-Allow-Origin", "*")
                .header("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT")
                .build();
    }
}
