package com.bl.bookstore.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Verification;
import org.springframework.stereotype.Component;

@Component
public class TokenUtility {
    public String generateToken(long id, String password){
        try{
            Algorithm algorithm = Algorithm.HMAC256(password);
            return JWT.create().withClaim("id",id).sign(algorithm);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public long decodeToken(String token, String password){
        Verification verification = null;
        try {
            verification = JWT.require(Algorithm.HMAC256(password));

        } catch (Exception e){
            e.printStackTrace();
        }
        assert verification != null;
        long userId= verification.build().verify(token).getClaim("id").asInt();
        return userId;
    }
}
