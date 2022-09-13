package com.bl.bookstore.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Verification;
import com.bl.bookstore.exception.BookStoreException;
import org.springframework.stereotype.Component;

@Component
public class TokenUtility {
    private final String TOKEN_SECRET="Bridgelabz";
    public String generateToken(long id){
        try{
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            return JWT.create().withClaim("id",id).sign(algorithm);
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public long decodeToken(String token){
        Verification verification = null;
        try {
            verification = JWT.require(Algorithm.HMAC256(TOKEN_SECRET));
        assert verification != null;
        long userId= verification.build().verify(token).getClaim("id").asInt();
        return userId;
        } catch (Exception e){
            e.printStackTrace();
            throw new BookStoreException("Incorrect token " + token);
        }
    }
}
