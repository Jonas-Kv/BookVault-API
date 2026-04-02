package me.jonas.kviring.bookvault_api.Config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor //Erstellt automatisch einen Konstruktor für alle Final Felder
public class JwtAuthenticationFilter extends OncePerRequestFilter{

  private final JwtService jwtService;
  private final UserDetailsService userDetailsService;

  @Override
  protected void doFilterInternal(
    @NonNull HttpServletRequest request,  //Contains the JWT
    @NonNull HttpServletResponse response,
    @NonNull FilterChain filterChain)
    throws ServletException, IOException {


      final String authHeader = request.getHeader("Authorization");
      final String jwt; //Ist der token mit den ganzen Infos
      final String userEmail;

      //When no header is present or wrong JWT format
      if(authHeader== null || !authHeader.startsWith("Bearer ")) {
        filterChain.doFilter(request, response);
        return;
      }
      jwt = authHeader.substring(7); //7, because "Bearer " has 7 characters"
      userEmail= jwtService.extractUsername(jwt); //extracts the email from the token

      //Loads the user details, if the email is present and there is no authentication yet
      if(userEmail != null  && SecurityContextHolder.getContext().getAuthentication() == null) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(userEmail);
        if(jwtService.isTokenValid(jwt, userDetails)){
          UsernamePasswordAuthenticationToken authToken= new UsernamePasswordAuthenticationToken(
            userDetails,
            null,
            userDetails.getAuthorities());
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext().setAuthentication(authToken);
        }
      }
      filterChain.doFilter(request, response);
  }
}

