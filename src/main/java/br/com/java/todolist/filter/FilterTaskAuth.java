package br.com.java.todolist.filter;

import java.io.IOException;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import at.favre.lib.crypto.bcrypt.BCrypt;
import br.com.java.todolist.user.IUserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class FilterTaskAuth extends OncePerRequestFilter {

  @Autowired
  private IUserRepository userRepository;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    // pegar autenticacao do usuario e converter
    var auth = request.getHeader("Authorization");
    var authEncoded = auth.substring("Basic".length()).trim();

    byte[] authDecode = Base64.getDecoder().decode(authEncoded);
    var authString = new String(authDecode);
    String[] credentials = authString.split(":");
    String username = credentials[0];
    String password = credentials[1];

    // validar usuario
    var isUser = this.userRepository.findByUsername(username);
    if (isUser == null) {
      response.sendError(401);
    } else {
      // validar senha
      var passwordVerify = BCrypt.verifyer().verify(password.toCharArray(), isUser.getPassword());
      if (passwordVerify.verified) {
        filterChain.doFilter(request, response);
      }else{
        response.sendError(401);
      }
    }

  }

}