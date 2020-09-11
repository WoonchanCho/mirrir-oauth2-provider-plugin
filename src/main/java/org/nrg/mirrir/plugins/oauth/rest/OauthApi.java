package org.nrg.mirrir.plugins.oauth.rest;

import static org.nrg.xdat.security.helpers.AccessLevel.Admin;
import static org.nrg.xdat.security.helpers.AccessLevel.Authenticated;
import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.DELETE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.nrg.framework.annotations.XapiRestController;
import org.nrg.mirrir.plugins.oauth.dao.UserInfo;
import org.nrg.xapi.authorization.UserResourceXapiAuthorization;
import org.nrg.xapi.exceptions.NotFoundException;
import org.nrg.xapi.rest.AbstractXapiRestController;
import org.nrg.xapi.rest.AuthDelegate;
import org.nrg.xapi.rest.XapiRequestMapping;
import org.nrg.xdat.security.services.RoleHolder;
import org.nrg.xdat.security.services.UserManagementServiceI;
import org.nrg.xft.security.UserI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Api("Oauth API")
@XapiRestController
@RequestMapping(value = "/oauth")
@Slf4j
public class OauthApi extends AbstractXapiRestController {
	@Autowired
	private TokenStore tokenStore;
	
	@Autowired
    public OauthApi(final UserManagementServiceI userManagementService,
                    final RoleHolder roleHolder) {
        super(userManagementService, roleHolder);
    }

    @ApiOperation(value = "About Me", notes = "About Me", response = UserInfo.class)
    @ApiResponses({@ApiResponse(code = 200, message = "User Info"),
                   @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
                   @ApiResponse(code = 403, message = "You do not have sufficient permissions."),
                   @ApiResponse(code = 500, message = "An unexpected error occurred.")})
    @XapiRequestMapping(value = "user/me", produces = APPLICATION_JSON_VALUE, method = GET, restrictTo = Authenticated)
    @AuthDelegate(UserResourceXapiAuthorization.class)
    @ResponseBody
    public ResponseEntity<UserInfo> getUserInfo() {
    	final UserI user = getSessionUser();
    	log.debug("about-me has been called" + user.getUsername());
    	UserInfo userInfo = UserInfo.builder()
    			.name(user.getUsername())
    			.email(user.getEmail())
    			.build();
        return new ResponseEntity<UserInfo>(userInfo, OK);
    }
    
    @ApiOperation(value = "Active Token", notes = "Active Token", responseContainer = "Map")
    @ApiResponses({@ApiResponse(code = 200, message = "Active Token"),
                   @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
                   @ApiResponse(code = 403, message = "You do not have sufficient permissions."),
                   @ApiResponse(code = 500, message = "An unexpected error occurred.")})
    @XapiRequestMapping(value = "clients/{clientId}/access-tokens/active", produces = APPLICATION_JSON_VALUE, method = GET, restrictTo = Admin)
    @AuthDelegate(UserResourceXapiAuthorization.class)
    @ResponseBody
    public Map<OAuth2AccessToken, String> getActiveAccessTokens (@ApiParam(value = "Client ID", required = true) @PathVariable("clientId") final String clientId) {
    	Collection<OAuth2AccessToken> accessTokens = tokenStore.findTokensByClientId(clientId);
    	Iterator<OAuth2AccessToken> iterator = accessTokens.iterator();
    	Map<OAuth2AccessToken, String> res = new HashMap<>();
    	
        while(iterator.hasNext()) {
        	OAuth2AccessToken accessToken = iterator.next();
        	OAuth2Authentication authentication = tokenStore.readAuthentication(accessToken);
        	res.put(accessToken, authentication.getName());
    	}
    	return res;
    }
    
    @ApiOperation(value = "Active Token by username", notes = "Active Token by username", response = OAuth2AccessToken.class, responseContainer = "List")
    @ApiResponses({@ApiResponse(code = 200, message = "Active Token by username"),
                   @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
                   @ApiResponse(code = 403, message = "You do not have sufficient permissions."),
                   @ApiResponse(code = 500, message = "An unexpected error occurred.")})
    @XapiRequestMapping(value = "clients/{clientId}/access-tokens/usernames/{userName}", produces = APPLICATION_JSON_VALUE, method = GET, restrictTo = Admin)
    @AuthDelegate(UserResourceXapiAuthorization.class)
    @ResponseBody
    public List<OAuth2AccessToken> getActiveAccessTokensByUsername (
    		@ApiParam(value = "Client ID", required = true) @PathVariable("clientId") final String clientId,
    		@ApiParam(value = "Username", required = true) @PathVariable("userName") final String userName) {
    	Collection<OAuth2AccessToken> accessTokens = tokenStore.findTokensByClientIdAndUserName(clientId, userName);
    	List<OAuth2AccessToken> res = new ArrayList<>(accessTokens);
    	return res;
    }
    
    @ApiOperation(value = "Remove Access Token", notes = "Remove Access Token", response = OAuth2AccessToken.class, responseContainer = "List")
    @ApiResponses({@ApiResponse(code = 200, message = "Access Token removed"),
                   @ApiResponse(code = 401, message = "Must be authenticated to access the XNAT REST API."),
                   @ApiResponse(code = 403, message = "You do not have sufficient permissions."),
                   @ApiResponse(code = 500, message = "An unexpected error occurred.")})
    @XapiRequestMapping(value = "clients/{clientId}/access-tokens/{tokenValue}", produces = APPLICATION_JSON_VALUE, method = DELETE, restrictTo = Admin)
    @AuthDelegate(UserResourceXapiAuthorization.class)
    @ResponseBody
    public void removeAccessToken (
    		@ApiParam(value = "Client ID", required = true) @PathVariable("clientId") final String clientId,
    		@ApiParam(value = "Token Value", required = true) @PathVariable("tokenValue") final String tokenValue) throws NotFoundException {
    	
    	OAuth2AccessToken accessToken = tokenStore.readAccessToken(tokenValue);
    	
    	if (accessToken == null) {
            throw new NotFoundException("Couldn't find access token with ID " + tokenValue);
        }
    	
    	tokenStore.removeAccessToken(accessToken);
    }
}
