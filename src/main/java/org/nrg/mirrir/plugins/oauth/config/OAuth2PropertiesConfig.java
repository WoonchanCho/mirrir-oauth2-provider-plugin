package org.nrg.mirrir.plugins.oauth.config;

import org.nrg.xnat.initialization.PropertiesConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySources({@PropertySource(value = OAuth2PropertiesConfig.OAUTH_HOME_URL, ignoreResourceNotFound = false)})
public class OAuth2PropertiesConfig {
	public static final String OAUTH_CONF_FILE        = "oauth2-plugin.properties";
	public static final String OAUTH_HOME_URL         = "file:${" + PropertiesConfig.JAVA_XNAT_HOME + "}/" + PropertiesConfig.BASE_CONF_FOLDER + "/" + OAUTH_CONF_FILE;
    
    @Value( "${oauth.client.clientId}" )
    private String clientId;
    
    @Value( "${oauth.client.grantTypes}" )
    private String[] grantTypes;

    @Value( "${oauth.client.authorities}" )
    private String[] authorities;

    @Value( "${oauth.client.scopes}" )
    private String[] scopes;

    @Value( "${oauth.client.secret}" )
    private String secret;

    @Value( "${oauth.client.redirectUris}" )
    private String[] redirectUris;

    @Value( "${oauth.resource.antMatchers}" )
    private String[] resourceAntMatchers;

    @Value( "${oauth.resource.scopes}" )
    private String[] resourceScopes;
    
	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String[] getGrantTypes() {
		return grantTypes;
	}

	public void setGrantTypes(String[] grantTypes) {
		this.grantTypes = grantTypes;
	}

	public String[] getAuthorities() {
		return authorities;
	}

	public void setAuthorities(String[] authorities) {
		this.authorities = authorities;
	}

	public String[] getScopes() {
		return scopes;
	}

	public void setScopes(String[] scopes) {
		this.scopes = scopes;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String[] getRedirectUris() {
		return redirectUris;
	}

	public void setRedirectUris(String[] redirectUris) {
		this.redirectUris = redirectUris;
	}

	public String[] getResourceAntMatchers() {
		return resourceAntMatchers;
	}

	public void setResourceAntMatchers(String[] resourceAntMatchers) {
		this.resourceAntMatchers = resourceAntMatchers;
	}

	public String[] getResourceScopes() {
		return resourceScopes;
	}

	public void setResourceScopes(String[] resourceScopes) {
		this.resourceScopes = resourceScopes;
	}
	
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    	final PropertySourcesPlaceholderConfigurer configurer = new PropertySourcesPlaceholderConfigurer();
        configurer.setIgnoreUnresolvablePlaceholders(true);
        return configurer;
    }
}
