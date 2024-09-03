// package com.fandresena.learn.service;

// import java.util.Date;

// import java.util.HashMap;
// import java.util.Map;

// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.security.core.userdetails.UserDetails;
// import org.springframework.stereotype.Service;

// import com.fandresena.learn.dao.AccessTokenDAO;
// import com.fandresena.learn.dao.RefreshTokenDAO;
// import com.fandresena.learn.model.AccessTokenModel;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import jakarta.servlet.http.Cookie;
// import lombok.AllArgsConstructor;

// @Service
// @AllArgsConstructor
// public class AccessTokenService extends JWTService {
    
//     private AccessTokenDAO accessTokenDAO;
//     AccessTokenModel accessTokenModel;
 



//     public String generateAccessToken(String username) {
//         final Date now = new Date();
//         final Date expirationDate = new Date(now.getTime() + 60 * 1 * 1000); // 1 minutes
//         String accessToken = Jwts.builder()
//                 .setSubject(username)
//                 .setIssuedAt(now)
//                 .setExpiration(expirationDate)
//                 .signWith(key, SignatureAlgorithm.HS256)
//                 .compact();

//         // Save Accesstoken in DB
//         this.accessTokenModel = new AccessTokenModel(accessToken, this.getExpiredDate(accessToken));
//         accessTokenDAO.putAccessToken(accessTokenModel);
//         return accessToken;
//     }



//     private boolean isAccessTokenInDBNow(AccessTokenModel accessToken) {
//         return accessTokenDAO.getAccessTokenByToken(accessToken.getToken()) != null;
//     }



//     public boolean isAccessTokenValid(String token, UserDetails userDetails) {
//         String username = extractUserEmail(token);

//         // the username in token should match username , and the token is not expired
//         if (!isAccessTokenInDBNow(new AccessTokenModel(token, null)))
//             return false;

//         // check if the user details match the username in the token
//         if (!username.equals(userDetails.getUsername())) {
//             return false;
//         }
//         return true;
//     }

// }
