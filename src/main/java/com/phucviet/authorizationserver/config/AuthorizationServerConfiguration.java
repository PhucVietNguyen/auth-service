package com.phucviet.authorizationserver.config;

import com.phucviet.authorizationserver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpointAuthenticationFilter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;

@Configuration
public class AuthorizationServerConfiguration implements AuthorizationServerConfigurer {

  @Value("${check-user-scopes}")
  private Boolean checkUserScopes;

  @Autowired private PasswordEncoder passwordEncoder;

  @Autowired private DataSource dataSource;

  @Autowired private AuthenticationManager authenticationManager;

  @Autowired private ClientDetailsService clientDetailsService;

  @Autowired private UserService userService;

  @Bean
  public OAuth2RequestFactory requestFactory() {
    CustomOauth2RequestFactory requestFactory =
        new CustomOauth2RequestFactory(clientDetailsService);
    requestFactory.setCheckUserScopes(true);
    return requestFactory;
  }

  @Bean
  public TokenEndpointAuthenticationFilter tokenEndpointAuthenticationFilter() {
    return new TokenEndpointAuthenticationFilter(authenticationManager, requestFactory());
  }

  @Bean
  public TokenStore tokenStore() {
    return new JwtTokenStore(jwtAccessTokenConverter());
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    JwtAccessTokenConverter converter = new CustomTokenEnhancer();
    converter.setKeyPair(
        new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "password".toCharArray())
            .getKeyPair("jwt"));
    return converter;
  }

  @Override
  public void configure(AuthorizationServerSecurityConfigurer authorizationServerSecurityConfigurer)
      throws Exception {
    authorizationServerSecurityConfigurer
        .checkTokenAccess("isAuthenticated()")
        .tokenKeyAccess("permitAll()");
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clientDetailsServiceConfigurer)
      throws Exception {
    clientDetailsServiceConfigurer.jdbc(dataSource).passwordEncoder(passwordEncoder);
  }

  @Override
  public void configure(
      AuthorizationServerEndpointsConfigurer authorizationServerEndpointsConfigurer)
      throws Exception {
    authorizationServerEndpointsConfigurer
        .tokenStore(tokenStore())
        .accessTokenConverter(jwtAccessTokenConverter());
    authorizationServerEndpointsConfigurer
        .authenticationManager(authenticationManager)
        .userDetailsService(userService);
    if (checkUserScopes) authorizationServerEndpointsConfigurer.requestFactory(requestFactory());
  }
}
