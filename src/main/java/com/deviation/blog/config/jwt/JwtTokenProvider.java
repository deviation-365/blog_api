package com.deviation.blog.config.jwt;

import com.deviation.blog.common.ApiResultStatus;
import com.deviation.blog.common.BusinessException;
import com.deviation.blog.dto.UsersDto;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Base64;
import java.util.Date;

@Slf4j
@Component
public class JwtTokenProvider {

    @Value("${jwt.token.secret}")
    private String secretKey;

    @Value("${jwt.token.length}")
    private long validityInHours; // 10h

    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createToken(UsersDto.LoginUserInfo userInfo) {

        Claims claims = Jwts.claims();
        claims.put("id", userInfo.getId());

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInHours * 3600 * 1000);

        return Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS512, secretKey)//
                .compact();
    }

    public Long getUserId(String token) {
        if (token.startsWith("Bearer ")) {
            token =  token.replace("Bearer ","");
        }
        return Long.valueOf(Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token)
                .getBody().get("userId").toString()) ;
    }

    public String getBodyValue(String token, String field) {
        if(this.validateJwtToken(token)) {
            return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get(field).toString();
        }
        return null;
    }

    public boolean validateJwtToken(String authToken) throws BusinessException {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;

        } catch (SignatureException e) {
            log.error("Invalid JWT signature -> Message: {} ", e.getMessage());
            throw new BusinessException(ApiResultStatus.TOKEN_INVALID);

        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token -> Message: {}", e.getMessage());
            throw new BusinessException(ApiResultStatus.TOKEN_INVALID);

        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token -> Message: {}", e.getMessage());
            throw new BusinessException(ApiResultStatus.TOKEN_DATE_EXPIRED);

        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token -> Message: {}", e.getMessage());
            throw new BusinessException(ApiResultStatus.TOKEN_INVALID);

        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty -> Message: {}", e.getMessage());
            throw new BusinessException(ApiResultStatus.TOKEN_INVALID);

        }

    }

}