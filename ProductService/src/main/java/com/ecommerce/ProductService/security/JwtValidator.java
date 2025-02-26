package com.ecommerce.ProductService.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;


@Component
public class JwtValidator {
    private final String secretKey = "afafasfafafasfasfasfafacasdasfasxASFACASDFACASDFASFASFDAFASFASDAADSCSDFADCVSGCFVADXCcadwavfsfarvf";

    public Claims extractAllClaims(String token) {
            return Jwts.parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token)
                    .getBody();
    }

    public String extractRole(String token) {

            return extractAllClaims(token).get("role", String.class);
        }


    }





