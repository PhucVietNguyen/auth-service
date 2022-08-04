package com.phucviet.authorizationserver.util;

import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.stereotype.Component;

import java.security.KeyPair;

@Component
@Log4j2
public class KeyUtils {

  public KeyPair getKeyPair() {
    return new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"), "password".toCharArray())
        .getKeyPair("jwt");
  }
}
