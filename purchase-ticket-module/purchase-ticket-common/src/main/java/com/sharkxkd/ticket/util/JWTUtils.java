package com.sharkxkd.ticket.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.sharkxkd.ticket.entity.TokenMsg;

import com.sharkxkd.ticket.exception.ServiceException;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;


import java.util.Calendar;

import static com.sharkxkd.ticket.constant.JWTConstants.*;

import static com.sharkxkd.ticket.enums.errorEnum.UserStatusEnum.TOKEN_PARSE_ERROR;

/**
 * JWT工具类
 */
@Slf4j
public class JWTUtils {
    private static final String HEADER_AUTH = "";
    private static final String SECRET = "xass";
    /**
     * jwt过期时间：60s * 60 * 24 = 1 day
     */
    private static final long EXPIRATION = 60 * 60 * 24L;
    private static final String TOKEN = "token";
    private static final String TOKEN_VOUCHERS = "token_vouchers";

    /**
     * 生成token，
     * TODO：1.考虑使用用户信息进行加密，保证每个用户的加密方式有所区别。
     *       2.存储在jwt中的数据要保证长时间不可变，并且这些数据需要频繁被使用，不需存储在对应的redis占用内存
     *       3.目前存储的信息包括用户名，用户真实姓名，用户的id
     * @param tokenMsg   传入payload
     * @return  token
     */
    public static String createToken(TokenMsg tokenMsg) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, (int) EXPIRATION);
        JWTCreator.Builder builder = JWT.create();
        builder.withClaim(UID,tokenMsg.getId())
                .withClaim(REALNAME,tokenMsg.getRealName())
                .withClaim(USERNAME,tokenMsg.getUsername());
        builder.withExpiresAt(calendar.getTime());
        return builder.sign(Algorithm.HMAC256(SECRET));
    }

    /**
     * 解析token中的信息存入tokenMsg对象中
     * @param request
     * @return
     */
    public static TokenMsg parseTokenMsg(HttpServletRequest request){
        String token = request.getHeader(TOKEN);
        if (token == null) {
            throw new ServiceException(TOKEN_PARSE_ERROR);
        }
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
        TokenMsg tokenMsg = new TokenMsg();
        tokenMsg.setId(decodedJWT.getClaim(UID).asInt());
        tokenMsg.setRealName(decodedJWT.getClaim(REALNAME).asString());
        tokenMsg.setUsername(decodedJWT.getClaim(USERNAME).asString());
        return tokenMsg;
    }
}
