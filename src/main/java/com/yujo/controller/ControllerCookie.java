package com.yujo.controller;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cookie")
public class ControllerCookie {

    @GetMapping()
    public String readAllCookies( HttpServletRequest request) {

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            return Arrays.stream(cookies)
                    .map(c -> c.getName() + "=" + c.getValue()).collect( Collectors.joining(","));
        }

        return null;
    }

  @GetMapping(value = "/{providedSessionId}")
  public String getUserAndSessionId(UsernamePasswordAuthenticationToken activeUser,
                                    HttpServletRequest httpServletRequest, @PathVariable("providedSessionId") String sessionID) {
      // Session ID
      String sessionId = httpServletRequest.getRequestedSessionId();

      if(sessionId.equals(sessionID)) {
          // Username
          return activeUser.getName();
      }
      return null;

  }

}
