/*
 * xnat-template: org.nrg.xnat.plugins.template.plugin.XnatTemplatePlugin
 * XNAT http://www.xnat.org
 * Copyright (c) 2020, Washington University School of Medicine
 * All Rights Reserved
 *
 * Released under the Simplified BSD.
 */

package org.nrg.mirrir.plugins.oauth.plugin;

import org.nrg.framework.annotations.XnatPlugin;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;

@XnatPlugin(value = "oAuth2ProviderPlugin", name = "XNAT OAuth2 Provider Plugin", logConfigurationFile="org/nrg/mirrir/plugins/oauth/plugin-logback.xml")
@ComponentScan({"org.nrg.mirrir.plugins.oauth.config",
				"org.nrg.mirrir.plugins.oauth.rest"})
@Slf4j
public class XnatOAuth2ProviderPlugin {
    
	public XnatOAuth2ProviderPlugin() {
        log.info("Creating the XnatOAuth2ProviderPlugin configuration class");
    }
}
