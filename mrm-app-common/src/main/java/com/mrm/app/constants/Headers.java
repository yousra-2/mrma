package com.mrm.app.constants;

import static java.util.Objects.nonNull;

public class Headers {

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String CONTENT_TYPE = "Content-Type";
    public static final String APPLICATION_JSON = "application/json";

    private static final int AUTHORIZATION_TYPE_LENGTH = 7; /* "Bearer ".length() */

    public static boolean isBearerToken(String authorizationHeader){
        return nonNull(authorizationHeader) && authorizationHeader.startsWith("Bearer ");
    }

    public static String extractJwt(String authorizationHeader) {
        return authorizationHeader.substring(AUTHORIZATION_TYPE_LENGTH);
    }
}
