package com.signify.spring.jwt.payload.request;

import javax.validation.constraints.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterationRequest {
    @NotBlank
    @Size(min = 3, max = 500)
    private String userName;
 
    @NotBlank
    @Size(max = 500)
    @Email
    private String userEmail;
    
    @NotBlank
    @Size(max = 500)
    @Email
    private String userAuthCode;
    
    @NotBlank
    @Size(max = 1)
    @Pattern(regexp = "C|I|A", message = "Value has to be one of C/I/A")
    private String userType;
    
    @NotBlank
    @Size(max = 10)
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "Value has to be ACTIVE or INACTIVE")
    private String userStatus;
    
    @NotBlank
    @Size(min = 6, max = 40)
    private String userAuthPassword;

}
