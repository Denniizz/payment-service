package com.deniz.payment_service.config;

import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.KeyUse;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

public class Jwks {

    private Jwks() {}

    public static RSAKey generateRsa() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                    .privateKey(keyPair.getPrivate())
                    .keyUse(KeyUse.SIGNATURE)
                    .keyID(UUID.randomUUID().toString())
                    .build();

        } catch (Exception ex) {
            throw new IllegalStateException("Failed to generate RSA key", ex);
        }
    }
}
