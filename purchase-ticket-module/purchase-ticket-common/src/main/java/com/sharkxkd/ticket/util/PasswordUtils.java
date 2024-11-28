package com.sharkxkd.ticket.util;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * 密码工具类
 * 用于生成随机盐、加密密码、验证密码
 */
public class PasswordUtils {
    /**
     * 生成随机盐
     * @return  随机盐
     */
    public static String generateSalt(){
        byte[] salt = new byte[16];
        SecureRandom random = new SecureRandom();
        // 将生成的随机字节填充到 salt 数组中
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * 加密密码,使用md5算法加密密码，并对密码进行哈希散列
     * @param password  明文密码
     * @param salt      盐
     * @return          加密后的密码
     * @throws NoSuchAlgorithmException   算法异常
     */
    public static String encryptPassword(String password, String salt) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        // 使用盐更新摘要
        md.update(Base64.getDecoder().decode(salt));
        int cnt = 3;
        byte[] encryptPassword = password.getBytes(StandardCharsets.UTF_8);
        while (cnt-- > 0) {
            // 使用密码更新摘要
            encryptPassword = md.digest(encryptPassword);
        }
        return Base64.getEncoder().encodeToString(encryptPassword);
    }

    /**
     * 验证密码,给定明文密码、盐和加密后的密码，验证密码是否正确
     * @param password  明文密码
     * @param salt      盐
     * @param hashedPassword    加密后的密码
     * @return  是否验证通过
     * @throws NoSuchAlgorithmException  算法异常
     */
    public static boolean verifyPassword(String password, String salt, String hashedPassword) throws NoSuchAlgorithmException {
        return encryptPassword(password, salt).equals(hashedPassword);
    }
}
