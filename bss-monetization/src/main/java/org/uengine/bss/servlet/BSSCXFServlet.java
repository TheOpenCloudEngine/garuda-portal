package org.uengine.bss.servlet;

import org.apache.cxf.transport.servlet.CXFServlet;

import javax.servlet.annotation.WebServlet;

/**
 * Created by hoo.lim on 6/22/2015.
 */
@WebServlet(name = "CXFServlet", urlPatterns = "/services/*", loadOnStartup = 1)
public class BSSCXFServlet extends CXFServlet{
}
