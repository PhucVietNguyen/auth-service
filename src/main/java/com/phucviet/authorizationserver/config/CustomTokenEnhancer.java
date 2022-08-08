package com.phucviet.authorizationserver.config;

import com.phucviet.authorizationserver.model.entity.UserEntity;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.LinkedHashMap;
import java.util.Map;

public class CustomTokenEnhancer extends JwtAccessTokenConverter {

  @Override
  public OAuth2AccessToken enhance(
      OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
    UserEntity user = (UserEntity) authentication.getPrincipal();

    Map<String, Object> info =
        new LinkedHashMap<String, Object>(accessToken.getAdditionalInformation());

    info.put("email", user.getEmail());
    info.put("userId", user.getId());

    DefaultOAuth2AccessToken customAccessToken = new DefaultOAuth2AccessToken(accessToken);
    customAccessToken.setAdditionalInformation(info);

    return super.enhance(customAccessToken, authentication);
  }
}
