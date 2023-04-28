package com.szmengran.authorization.web;

import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.szmengran.authorization.domain.config.JksProperties;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyPair;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

/** 
 * @description: 获取RSA公钥接口
 * @author MaoYuan.Li
 * @date: 2023/4/28 15:43
 */
@RestController
@Tag(name = "获取RSA公钥接口", description = "获取RSA公钥接口")
@RequestMapping("/rsa")
public class KeyPairController {
    
    @Resource
    private JksProperties jksProperties;
    
    @Resource
    private KeyPair keyPair;

    @GetMapping("/publicKey")
    public Map<String, Object> getKey() {
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAKey key = new RSAKey.Builder(publicKey).keyID(jksProperties.getKid()).build();
        return new JWKSet(key).toJSONObject();
    }

}
