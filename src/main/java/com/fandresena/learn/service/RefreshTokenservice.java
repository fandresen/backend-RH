// package com.fandresena.learn.service;

// import java.util.Date;

// import java.util.HashMap;
// import java.util.Map;
// import org.springframework.stereotype.Service;


// import com.fandresena.learn.dao.RefreshTokenDAO;
// import com.fandresena.learn.model.RefreshTokenModel;

// import io.jsonwebtoken.Jwts;
// import io.jsonwebtoken.SignatureAlgorithm;
// import jakarta.servlet.http.Cookie;

// @Service
// public class RefreshTokenservice extends JWTService {

//     private RefreshTokenDAO refreshTokenDAO;
//     private AccessTokenService accessTokenService;

    

//     public String generateRefreshToken(String username) {

//         final Date now = new Date();

//         final Date expirationDate = new Date(now.getTime() + 60 * 30 * 1000); // 30 minutes
//         String refreshToken = Jwts.builder()
//                 .setSubject(username)
//                 .setIssuedAt(now)
//                 .setExpiration(expirationDate)
//                 .signWith(this.key, SignatureAlgorithm.HS256)
//                 .compact();

//         // Save Refreshtoken in DB
//         this.refreshTokenModel = new RefreshTokenModel(refreshToken, this.getExpiredDate(refreshToken));
//         refreshTokenDAO.putRefreshToken(refreshTokenModel);
//         return refreshToken;
//     }

//     private boolean isRefreshTokenInDBNow(RefreshTokenModel refreshToken) {
//         return refreshTokenDAO.getRefreshTokenByToken(refreshToken.getToken()) != null;
//     }

//     public boolean isRefreshValid(String token) {
//         if (!isRefreshTokenInDBNow(refreshTokenModel)) {
//             return false;
//         }
//         return true;
//     }

//      public Map<String, String> generateAccessTFromRefreshT(Cookie[] cookies) {
//         if (cookies != null) {
//             for (Cookie cookie : cookies) {
//                 if ("refreshToken".equals(cookie.getName())) {
//                     String refreshToken = cookie.getValue(); // Extract Token from cookies
//                     if (refreshToken != null && isRefreshTokenInDBNow(refreshTokenModel)) {
//                         // generate new accessToken
//                         String token =  accessTokenService.generateAccessToken(this.extractUserEmail(refreshToken));
//                         Map<String, String> accessToken = new HashMap<String, String>();
//                         accessToken.put("accessToken", token);
//                         return accessToken;
//                     } 
//                     else return Map.of( "error", "Invalid refresh token");    
//                 }
//             }
          
//     }
//     return Map.of("error", "Invalid refresh token");
// }
// }
