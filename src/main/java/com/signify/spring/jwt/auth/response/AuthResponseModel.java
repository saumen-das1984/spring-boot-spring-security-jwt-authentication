
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
    "responseCode",
    "responseType",
    "apiResponseMessage",
    "response",
    "status"
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponseModel {

    @JsonProperty("responseCode")
    public String responseCode;
    @JsonProperty("responseType")
    public String responseType;
    @JsonProperty("apiResponseMessage")
    public String apiResponseMessage;
    @JsonProperty("response")
    public TokenData response;
    @JsonProperty("status")
    public String status;

}
