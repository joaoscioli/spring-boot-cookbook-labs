package com.packt.footballresource;

import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.oauth2.core.OAuth2AuthenticatedPrincipal;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

public class CustomClaimValidator implements OAuth2TokenValidator<Jwt> {
    OAuth2Error error = new OAuth2Error("custom_code", "This feature is only for special football fans",
            null);

    @Override
    public OAuth2TokenValidatorResult validate(Jwt jwt) {
        if (jwt.getClaims().containsKey("specialFan")){
            return OAuth2TokenValidatorResult.success();
        }
        else{
            return
                    OAuth2TokenValidatorResult.failure(error);
        }
    }

}
