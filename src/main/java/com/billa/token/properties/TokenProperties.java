package com.billa.token.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Configuration
@Data
public class TokenProperties {
    @Value("${jwt.secret}")
    private String secret;

}
