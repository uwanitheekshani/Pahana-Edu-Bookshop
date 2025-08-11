package com.example.pahanabillingsystem.utill;

import com.example.pahanabillingsystem.model.User;
import com.example.pahanabillingsystem.service.UserService;
import com.example.pahanabillingsystem.service.impl.UserServiceImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


import java.util.Date;

public class JwtUtil {

    private static final String SECRET_KEY = "7e133d5dc2713b2382dcc979f37869aaf126550ae7fe54302d312411a54fb9d607065a8fb165c07887c60f941a097ccfe6dec8ade69665b0625205e2bae309e616d60480a0f7ac25f446093891c353b3c0c95fffe1b6ae5cf6a6a38634c46ff3c8381ab4703aaf74058963cf19226d867e6cee750f404b981aa37c183e441660f31524a30ac2b07da3f8afad8d8e80a404fa813a4f093d278a92744e0d1812fe142a62f54b10340f2ee825fe8995a8b3efa39334bbf4b9cba1815284b36ee94ab48d1e8cba47743ea083353a4e17d2286c03c30766d59f8cc115a4d5884be74ab2de86db5a1bedad30a11094a62eb83a4a46117ebb2f316233e7ab24ddffc1b8";  // Change to a secure key
    private static final long EXPIRATION_TIME = 86400000;  // 1 day in milliseconds
    private static UserService userService = new UserServiceImpl();






    public static String generateToken(String useremail, String role) {
        User user =userService.getUserByEmail(useremail);
        int id= 0;
        if(user!=null){
            id=user.getId();
        }
        long now = System.currentTimeMillis();
        Date expirationDate = new Date(now + 3600000);  // Token expires in 1 hour


        return Jwts.builder()
                .setSubject(useremail)
                .claim("role", role)
                .claim("userId",id)
                .setIssuedAt(new Date(now))
                .setExpiration(expirationDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }



    //    // Verify JWT Token
    public static Claims verifyToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getUsernameFromToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();  // The username (email)
    }

    // for get profile

    public static String extractEmail(String token) {
        return extractClaims(token).getSubject();  // Extract email from JWT
    }
    private static Claims extractClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .parseClaimsJws(token)
                .getBody();
    }
}