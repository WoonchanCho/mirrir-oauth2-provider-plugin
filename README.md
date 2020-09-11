# XNAT OAuth2 Provider Plugin #

This is an Oauth2 Provider Plugin for XNAT. It provides a basic OAuth2 provider functionalities
(both authorization server and resource server) to XNAT.

> **Note:** This is not a production-ready plugin. This is an exemple solution to test OAuth2 provider
functionality.

# Building #

To build the XNAT 1.7 template plugin:

1. Build the plugin: `./gradlew jar` (on Windows, you can use the batch file: `gradlew.bat jar`). This should build the plugin in the file **build/libs/mirrir-oauth2-provider-plugin-0.0.1-SNAPSHOT.jar** (the version may differ based on updates to the code).
2. Copy the plugin jar to your plugins folder: `cp build/libs/mirrir-oauth2-provider-plugin-0.0.1-SNAPSHOT.jar ${xnat.home}/plugins`
3. Place the oauth2-plugin.properties file to the **/${xnat.home}configs**

## Configuring ##

You need to add an initial configuration file in **/${xnat.home}configs**. Create a file named **oauth2-plugin**
there. In this file, define the following properties:
Please refer to the sample properties files in /rc/main/resources/org/nrg/mirrir/plugins/oauth/sample-oauth2-plugin.properties.

```properties
oauth.client.clientId=
oauth.client.grantTypes=
oauth.client.authorities=
oauth.client.scopes=
oauth.client.secret=
oauth.client.redirectUris=

oauth.resource.antMatchers=/xapi/**
oauth.resource.scopes=read

```
