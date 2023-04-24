package com.eco.common.pojo;

import lombok.Data;

/**
 * JWT载体
 */
@Data
public class JwtPayload {

    private String jti;

    private Long exp;
}
