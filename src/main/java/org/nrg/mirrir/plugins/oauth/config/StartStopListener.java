package org.nrg.mirrir.plugins.oauth.config;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.ServletRegistration;
import javax.servlet.annotation.WebListener;

import org.nrg.xnat.initialization.XnatWebAppInitializer;

import lombok.extern.slf4j.Slf4j;

@WebListener
@Slf4j
public class StartStopListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        log.info("Adding mappings for oauth endpoints");
    	ServletContext servletContext = servletContextEvent.getServletContext();
        ServletRegistration reg = servletContext.getServletRegistration(XnatWebAppInitializer.DEFAULT_SERVLET_NAME);
        if (reg == null) {
        	log.warn("Cannot find dispatcher servlet (" + XnatWebAppInitializer.DEFAULT_SERVLET_NAME + ")");
        } else {
        	reg.addMapping("/oauth/authorize", "/oauth/confirm_access", "/oauth/check_token", "/oauth/token", "/oauth/error");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
    }
}