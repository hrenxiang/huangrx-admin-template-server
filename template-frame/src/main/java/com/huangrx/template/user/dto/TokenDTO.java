package com.huangrx.template.user.dto;

import lombok.Builder;
import lombok.Data;

import java.util.Date;


/**
 * TokenDTO
 *
 * @author   huangrx
 * @since   2023-04-25 22:57
 */
@Data
@Builder
public class TokenDTO {

    private String accessToken;

    private String refreshToken;

    private Date expire;

}
