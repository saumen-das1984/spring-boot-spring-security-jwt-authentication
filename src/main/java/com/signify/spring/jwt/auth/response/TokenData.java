
package com.signify.spring.jwt.auth.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "authorization",
    "issuedAt",
    "expirationAt",
    "status"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenData {

    @JsonProperty("authorization")
    public String authorization;
    @JsonProperty("issuedAt")
    public String issuedAt;
    @JsonProperty("expirationAt")
    public String expirationAt;
    @JsonProperty("status")
    public String status;

}
