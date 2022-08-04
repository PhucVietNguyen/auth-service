package com.phucviet.authorizationserver.util;

import com.phucviet.authorizationserver.config.AppProperties;
import com.phucviet.authorizationserver.model.entity.UserDetailsImpl;
import io.jsonwebtoken.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Log4j2
public class JwtUtils {

  @Autowired private AppProperties appProperties;

  @Autowired private KeyUtils keyUtils;

  private static final String AUTHORITIES_KEY = "authorities";

  public String generateJwtTokenSocial(Authentication authentication) {
    UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
    return Jwts.builder()
        .setSubject(String.valueOf((userPrincipal.getId())))
        .claim(
            AUTHORITIES_KEY,
            userPrincipal.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()))
        .setIssuedAt(new Date())
        .setExpiration(
            new Date(new Date().getTime() + appProperties.getAuth().getTokenExpiration()))
        .signWith(SignatureAlgorithm.RS256, keyUtils.getKeyPair().getPrivate())
        .compact();
  }

  public String getUserIdFromJwtTokenSocial(String token) {
    return Jwts.parser()
        .setSigningKey(keyUtils.getKeyPair().getPublic())
        .parseClaimsJws(token)
        .getBody()
        .getSubject();
  }

  public String parseJwt(HttpServletRequest request) {
    final String headerAuth = request.getHeader("Authorization");

    if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
      return headerAuth.substring(7);
    }
    return null;
  }

  public boolean validateJwtTokenSocial(String authToken) {
    try {
      Jwts.parser().setSigningKey(keyUtils.getKeyPair().getPublic()).parseClaimsJws(authToken);
      return true;
    } catch (SignatureException e) {
      log.error("Invalid JWT signature: {}", e.getMessage());
    } catch (MalformedJwtException e) {
      log.error("Invalid JWT token: {}", e.getMessage());
    } catch (ExpiredJwtException e) {
      log.error("JWT token is expired: {}", e.getMessage());
    } catch (UnsupportedJwtException e) {
      log.error("JWT token is unsupported: {}", e.getMessage());
    } catch (IllegalArgumentException e) {
      log.error("JWT claims string is empty: {}", e.getMessage());
    }
    return false;
  }
}
