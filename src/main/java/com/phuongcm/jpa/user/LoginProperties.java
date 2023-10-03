package com.phuongcm.jpa.user;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("login")
public class LoginProperties {
    private final int maxConsPassFaulty =3;
    private final long consPassFaultyBlock = 1800L;
    private final int maxConsOtpFaulty =3;
    private final long consOtpFaultyBlock = 1800L;
    private final boolean needTwoStep =false;
    private final int vertificationWindow = 2;
}
