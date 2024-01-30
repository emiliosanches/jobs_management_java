package br.com.emiliosanches.jobs_management.security;

import java.io.IOException;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.emiliosanches.jobs_management.providers.CompanyJWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CompanySecurityFilter extends OncePerRequestFilter {
  @Autowired
  CompanyJWTProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (!request.getRequestURI().startsWith("/companies")) {
      filterChain.doFilter(request, response);
      return;
    }

    SecurityContextHolder.getContext().setAuthentication(null);

    String header = request.getHeader("Authorization");

    if (header == null) {
      filterChain.doFilter(request, response);
      return;
    }

    var tokenSubject = this.jwtProvider.validateToken(header);

    if (tokenSubject == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    request.setAttribute("companyId", tokenSubject);

    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(tokenSubject, null,
        Collections.emptyList());

    SecurityContextHolder.getContext().setAuthentication(auth);

    filterChain.doFilter(request, response);
  }

}
