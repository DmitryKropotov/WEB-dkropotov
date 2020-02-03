/*
 * Copyright 2004-2019 H2 Group. Multiple-Licensed under the MPL 2.0,
 * and the EPL 1.0 (https://h2database.com/html/license.html).
 * Initial Developer: Alessandro Ventura
 */
package org.h2.security.auth.impl;

import org.h2.api.CredentialsValidator;
import org.h2.security.auth.AuthenticationInfo;
import org.h2.security.auth.ConfigProperties;

import javax.security.auth.login.LoginContext;
import java.io.IOException;

/**
 * Validate credentials by using standard Java Authentication and Authorization Service
 *
 * <p>
 * Configuration parameters:
 * </p>
 * <ul>
 *    <li>appName inside the JAAS configuration (by default h2)</li>
 * </ul>
 *
 */
public class JaasCredentialsValidator implements CredentialsValidator {

    public static final String DEFAULT_APPNAME="h2";

    private String appName;

    public JaasCredentialsValidator() {
        this(DEFAULT_APPNAME);
    }

    /**
     * Create the validator with the given name of JAAS configuration
     * @param appName = name of JAAS configuration
     */
    public JaasCredentialsValidator(String appName) {
        this.appName=appName;
    }

    @Override
    public void configure(ConfigProperties configProperties) {
        appName=configProperties.getStringValue("appName",appName);
    }

    class AuthenticationInfoCallbackHandler implements CallbackHandler {

        AuthenticationInfo authenticationInfo;

        AuthenticationInfoCallbackHandler(AuthenticationInfo authenticationInfo) {
            this.authenticationInfo = authenticationInfo;
        }

        @Override
        public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
            for (int i = 0; i < callbacks.length; i++) {
                if (callbacks[i] instanceof NameCallback) {
                    ((NameCallback) callbacks[i]).setName(authenticationInfo.getUserName());
                } else if (callbacks[i] instanceof PasswordCallback) {
                    ((PasswordCallback) callbacks[i]).setPassword(authenticationInfo.getPassword().toCharArray());
                }
            }
        }

    }

    @Override
    public boolean validateCredentials(AuthenticationInfo authenticationInfo) throws Exception {
        LoginContext loginContext = new LoginContext(appName,
                new AuthenticationInfoCallbackHandler(authenticationInfo));
        loginContext.login();
        authenticationInfo.setNestedIdentity(loginContext.getSubject());
        return true;
    }

}
