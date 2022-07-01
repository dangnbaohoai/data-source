package com.c1se44.school_connect.security.JWT;

import com.c1se44.school_connect.security.userprincal.UserPrinciple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import io.jsonwebtoken.*;

import java.util.Date;

// nơi tạo ra token
@Component
public class JwtProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
	//  từ khóa của token
	private String jwtSecret="SConA";
	// thời gian sống của token =1 ngày = 86400s
	private int jwtExpiration = 86400;
	 // hàm tạo token
	public String CreateToken(Authentication authentication){
		UserPrinciple userPrinciple=(UserPrinciple) authentication.getPrincipal();
		// build the token .setExpiration() set thời gian sống setIssuedAt() set tại thời gian tạo nào
		//.signWith(tiêu chuẩn mã hóa,từ khóa)
		return Jwts.builder().setSubject(userPrinciple.getUsername())
					.setIssuedAt(new Date())
					.setExpiration(new Date(new Date().getTime()+jwtExpiration*1000))
					.signWith(SignatureAlgorithm.HS512,jwtSecret)
					.compact();
	}
	// kiểm tra token có đúng ko
	public boolean validationToken(String token){
		try {
			Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token);
			return  true;
		} catch (SignatureException e){
			logger.error("Invalid JWT signature -> MessageResponse: {}", e);
		} catch (MalformedJwtException e){
			logger.error("Invalid Token format -> MessageResponse: {}",e);
		} catch (UnsupportedJwtException e){
			logger.error("Unsupported JWT token -> MessageResponse: {}",e);
		} catch (IllegalArgumentException e){
			logger.error("JWT claims string is empty -MessageResponse {}",e);
		} catch (ExpiredJwtException e){
			logger.error("JWT Token is expired -> MessageResponse: {}",e);
		}
		return false;
	}
	// lấy username từ token
	public String getUserNameFromToken(String token){
		String userName = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject();
		return userName;
	}
	// lấy userid từ token
//	public Long getUserIdFromToken(String token){
//		Long userid = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject().;
//		return userid;
//	}
}
