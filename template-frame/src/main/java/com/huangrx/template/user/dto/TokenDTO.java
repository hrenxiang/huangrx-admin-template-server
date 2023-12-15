package com.huangrx.template.user.dto;

import cn.hutool.core.date.DatePattern;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
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

    @JsonFormat(pattern = DatePattern.NORM_DATETIME_PATTERN)
    private Date expire;

}
