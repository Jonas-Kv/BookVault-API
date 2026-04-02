package me.jonas.kviring.bookvault_api.Config;

import java.security.Key;
import java.util.function.Function;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

  //256 bit encryption Key
  @Value("${SECRET_KEY}")
  private String secretKey;
  

  //Erzeugt einen kryptographischen Key der zum Signieren von Tokens benutz wird
  private Key getSignInKey() {
    byte[] keyBytes= Decoders.BASE64.decode(secretKey);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  //Erstellt nen token ohne extra claims
  public String generateToken(UserDetails userDetails){
    return generateToken(new HashMap<>(), userDetails);
  }

  // generates Token that contains all claims, we need
  // UserDetails-> Interface, das alle wichtigen Infos, für Login und Authentifizierung enthält
  // zb: Username, Passwort, Rollen/Rechte, Status(aktiv/ gesperrt)
  public String generateToken(Map<String, Object> extraClaims, UserDetails userDetails){
    return Jwts
           .builder()
           .setClaims(extraClaims)
           .setSubject(userDetails.getUsername())
           .setIssuedAt(new Date(System.currentTimeMillis()))
           .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60* 24))
           .signWith(getSignInKey(), SignatureAlgorithm.HS256)
           .compact();
  }

  //Method um einen Token zu validieren
  //Man schaut ob der Token zum richtigen User gehört
  public boolean isTokenValid(String token, UserDetails userDetails){
    final String username= extractUsername(token);
    return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
  }

  //Gibt den Username des Tokens zurück
  public String extractUsername(String token){
    return extractClaim(token, Claims::getSubject); 
  }
  
  //Guckt ob der Token abgelaufen ist
  private boolean isTokenExpired(String token){
    return extractExpiration(token).before(new Date());
  }

  //Gibt die Expiration des Tokens zurück
  private Date extractExpiration(String token){
    return extractClaim(token, Claims::getExpiration); 
  }

  //Universelle Schablone um einen Claim zu extrahieren
  private <T> T extractClaim(String token, Function <Claims,T> claimsResolver ){
    final Claims claims= extractAllClaims(token);
    return claimsResolver.apply(claims);
  }


  //Packt alle Claims aus dem Token in ein Claims Objekt
  private Claims extractAllClaims(String token){
    return Jwts.parserBuilder()
          .setSigningKey(getSignInKey())
          .build()
          .parseClaimsJws(token)
          .getBody();
  }
}
