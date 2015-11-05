package org.uengine.codi.mw3.marketplace;

import org.metaworks.MetaworksContext;
import org.metaworks.ServiceMethodContext;
import org.metaworks.annotation.AutowiredFromClient;
import org.metaworks.annotation.ServiceMethod;
import org.metaworks.dwr.MetaworksRemoteService;
import org.metaworks.widget.ModalPanel;
import org.metaworks.widget.ModalWindow;
import org.uengine.bss.application.MetadataPropertyListFace;
import org.uengine.codi.mw3.model.Session;

import static org.metaworks.dwr.MetaworksRemoteService.*;

public class MarketplaceMenu {
	
	@AutowiredFromClient
	public Session session;

	@ServiceMethod(target=ServiceMethodContext.TARGET_POPUP)
	public Object registerApp() throws Exception {
		App app = getComponent(App.class);
		autowire(app);

		app.load();
		app.getMetaworksContext().setWhen(MetaworksContext.WHEN_NEW);
		
		return new ModalWindow(new ModalPanel(app), 1050, 550, "앱 등록");
	}

	@ServiceMethod(target=ServiceMethodContext.TARGET_POPUP)
	public Object manageApp() throws Exception {
		MyVendor myVendor = new MyVendor();
		myVendor.load(session);
		
		return new ModalWindow(new ModalPanel(myVendor), 0, 0, "앱 관리");
	}
}
