package com.huangrx.template.security.vo;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Getter;


/**
 * TokenVO
 *
 * @author   huangrx
 * @since   2023-04-25 22:57
 */
@Getter
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TokenVO {
    private String accessToken;

    private String refreshToken;

}