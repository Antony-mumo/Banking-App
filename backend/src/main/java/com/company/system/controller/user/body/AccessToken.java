package com.company.system.controller.user.body;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessToken {
    @JsonProperty("access_token")
    private String token;
    @JsonProperty("expires_in")
    private Long expiresIn;
}