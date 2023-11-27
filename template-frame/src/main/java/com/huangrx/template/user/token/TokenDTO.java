package com.huangrx.template.user.token;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Builder;
import lombok.Data;


/**
 * TokenDTO
 *
 * @author   huangrx
 * @since   2023-04-25 22:57
 */
@Data
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class TokenDTO {

    private String accessToken;

    private String refreshToken;

}
