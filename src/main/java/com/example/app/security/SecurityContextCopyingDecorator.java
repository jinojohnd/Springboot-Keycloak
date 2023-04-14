package com.example.app.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskDecorator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

@Configuration
public class SecurityContextCopyingDecorator implements TaskDecorator {

  @Override
  public Runnable decorate(Runnable runnable) {
    final Authentication a = SecurityContextHolder.getContext().getAuthentication();
    return () -> {
      try {
        SecurityContext ctx = SecurityContextHolder.createEmptyContext();
        ctx.setAuthentication(a);
        SecurityContextHolder.setContext(ctx);
        runnable.run();
      } finally {
        SecurityContextHolder.clearContext();
      }
    };
  }
}