package br.com.emiliosanches.jobs_management.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.emiliosanches.jobs_management.providers.CandidateJWTProvider;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CandidateSecurityFilter extends OncePerRequestFilter {
  @Autowired
  CandidateJWTProvider jwtProvider;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    if (!request.getRequestURI().startsWith("/candidates")) {
      filterChain.doFilter(request, response);
      return;
    }

    SecurityContextHolder.getContext().setAuthentication(null);
    var header = request.getHeader("Authorization");

    if (header == null) {
      filterChain.doFilter(request, response);
      return;
    }

    var token = this.jwtProvider.validateToken(header);

    if (token == null) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      return;
    }

    request.setAttribute("candidateId", token.getSubject());
    var roles = token.getClaim("roles").asList(String.class);

    var grants = roles.stream().map(
        role -> new SimpleGrantedAuthority("ROLE_" + role)).toList();

    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(token.getSubject(), null,
        grants);

    SecurityContextHolder.getContext().setAuthentication(auth);

    filterChain.doFilter(request, response);
  }
}
