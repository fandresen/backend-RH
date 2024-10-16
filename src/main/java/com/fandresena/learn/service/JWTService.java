package com.fandresena.learn.service;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.fandresena.learn.controller.AbsenceController;
import com.fandresena.learn.dao.AccessTokenDAO;
import com.fandresena.learn.dao.RefreshTokenDAO;
import com.fandresena.learn.dao.SuperUserDAO;
import com.fandresena.learn.dao.UserDAO;
import com.fandresena.learn.model.AccessTokenModel;
import com.fandresena.learn.model.RefreshTokenModel;
import com.fandresena.learn.model.SuperUserModel;
import com.fandresena.learn.model.UserModel;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.transaction.Transactional;

@Service
public class JWTService {

    private static final Logger logger = LoggerFactory.getLogger(AbsenceController.class);
    private AccessTokenDAO accessTokenDAO;
    private RefreshTokenDAO refreshTokenDAO;
    private UserDAO userDAO;
    private SuperUserDAO superUserDAO;

    RefreshTokenModel refreshTokenModel;
    AccessTokenModel accessTokenModel;

    final String SECRET_KEY = "AVQ445IUvbf563HHd584XgHTIKdfjxhVV2h27nCBkTGb7NK3QEghlB1lldhgzlsqsedfHHjhJhlsYYoNlXsEzKTv8YAXWdBp6cH4yc";
    final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public JWTService(AccessTokenDAO accessTokenDAO, RefreshTokenDAO refreshTokenDAO, UserDAO userDAO,
            SuperUserDAO superUserDAO) {
        this.accessTokenDAO = accessTokenDAO;
        this.refreshTokenDAO = refreshTokenDAO;
        this.userDAO = userDAO;
        this.superUserDAO = superUserDAO;
    }

    public String generateAccessToken(String userName) {
        final Date now = new Date();
        final Date expirationDate = new Date(now.getTime() + 60 * 60 * 1000); // 1 h

        logger.info("NOW :" + now);
        logger.info("EXPIRATION :" + expirationDate);

        // get user or superUser role
        UserModel user = userDAO.findByEmail(userName);
        Map<String, Object> claims = new HashMap<String, Object>();
        if (user != null) {
            claims.put("role", user.getRole());
            claims.put("entreprise_id", user.getEntreprise_id());
            claims.put("userName", user.getLast_name());
            Integer departementId = user.getDepartement_id();
            if (departementId != null) {
                claims.put("departement_id", departementId);
            }

        } else {
            SuperUserModel superUser = superUserDAO.findByEmail(userName);
            claims.put("role", superUser.getAuthorities());
            claims.put("userName", superUser.getUsername());
        }

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setSubject(userName)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Save Accesstoken in DB
        accessTokenModel = new AccessTokenModel(accessToken, this.getExpiredDate(accessToken));
        accessTokenDAO.putAccessToken(accessTokenModel);
        return accessToken;
    }

    public String generateRefreshToken(String username) {
        final Date now = new Date();
        final Date expirationDate = new Date(now.getTime() + 60 * 60 * 1000); // 30 minutes
        String refreshToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(now)
                .setExpiration(expirationDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        // Save Refreshtoken in DB
        refreshTokenModel = new RefreshTokenModel(refreshToken, this.getExpiredDate(refreshToken));
        refreshTokenDAO.putRefreshToken(refreshTokenModel);
        return refreshToken;
    }

    private boolean isRefreshTokenInDBNow(RefreshTokenModel refreshToken) {
        return refreshTokenDAO.getRefreshTokenByToken(refreshToken.getToken()) != null;
    }

    private boolean isAccessTokenInDBNow(AccessTokenModel accessToken) {
        return accessTokenDAO.getAccessTokenByToken(accessToken.getToken()) != null;
    }

    @Transactional
    public boolean isTokenValid(String token, UserDetails userDetails) {
        accessTokenDAO.deleteExpiredAccessToken();
        refreshTokenDAO.deleteExpiredRefreshToken();
        String username = extractUserEmail(token);

        // the username in token should match username , and the token is not expired
        if (!isAccessTokenInDBNow(new AccessTokenModel(token, null)) && !isRefreshTokenInDBNow(refreshTokenModel))
            return false;

        // check if the user details match the username in the token
        if (!username.equals(userDetails.getUsername())) {
            return false;
        }
        return true;
    }

    public boolean isAccessvalid(String token, UserModel userDetails) {
        boolean inDb = !isAccessTokenInDBNow(new AccessTokenModel(token, null));
        String username = userDetails.getUsername();
        if (!username.equals(userDetails.getUsername()) && !inDb) {
            return false;
        }
        return true;
    }

    public boolean isRefreshValid(String token) {
        if (!isRefreshTokenInDBNow(refreshTokenModel)) {
            return false;
        }
        return true;
    }

    // Avoir la date d'expiration du token
    public LocalDateTime getExpiredDate(String token) {
        LocalDateTime expirationDate = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime();

        return expirationDate;
    }

    public boolean isTokenExpired(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getExpiration()
                .before(new Date());

    }

    public String extractUserEmail(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public int extractEntrepriseId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("entreprise_id", Integer.class);
    }

    public int extractDepartementId(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .get("departement_id", Integer.class);
    }

    public Map<String, String> generateAccessTFromRefreshT(Cookie[] cookies) {
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    String refreshToken = cookie.getValue(); // Extract Token from cookies
                    System.err.println(refreshToken);
                    if (refreshToken != null || this.isRefreshValid(refreshToken)) {
                        // generate new accessToken
                        String token = this.generateAccessToken(this.extractUserEmail(refreshToken));
                        Map<String, String> accessToken = new HashMap();
                        accessToken.put("accessToken", token);
                        return accessToken;
                    } else
                        return Map.of("error", "Invalid refresh token");
                }
            }

        }
        return Map.of("error", "Invalid refresh token");
    }

    public int extractUserId(String token) {
        String email = this.extractUserEmail(token);
        UserModel user = userDAO.findByEmail(email);
        if(user==null){
            SuperUserModel superUserModel = superUserDAO.findByEmail(email);
            return superUserModel.getId();
        }
        return user.getId();
    }

}
