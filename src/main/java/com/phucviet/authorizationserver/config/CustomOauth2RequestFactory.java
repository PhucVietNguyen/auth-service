package com.phucviet.authorizationserver.config;

import com.phucviet.authorizationserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CustomOauth2RequestFactory extends DefaultOAuth2RequestFactory {

  @Autowired private TokenStore tokenStore;

  @Autowired private UserService userService;

  public CustomOauth2RequestFactory(ClientDetailsService clientDetailsService) {
    super(clientDetailsService);
  }

  @Override
  public TokenRequest createTokenRequest(
      Map<String, String> requestParameters, ClientDetails authenticatedClient) {
    if (requestParameters.get("grant_type").equals("refresh_token")) {
      OAuth2Authentication authentication =
          tokenStore.readAuthenticationForRefreshToken(
              tokenStore.readRefreshToken(requestParameters.get("refresh_token")));
      SecurityContextHolder.getContext()
          .setAuthentication(
              new UsernamePasswordAuthenticationToken(
                  authentication.getName(),
                  null,
                  userService.loadUserByUsername(authentication.getName()).getAuthorities()));
    }
    return super.createTokenRequest(requestParameters, authenticatedClient);
  }
}
