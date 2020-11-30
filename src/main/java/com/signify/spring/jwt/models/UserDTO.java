package com.signify.spring.jwt.models;

import java.sql.Timestamp;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
	@NotBlank
	private int user_Id;
	
	

	@NotBlank
    @Size(min = 5, max = 500)
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
    
    @NotBlank
    @Size(min = 6, max = 200)
    private String authToken;
    
    @NotBlank
    @Size(max = 10)
    @Pattern(regexp = "ACTIVE|INACTIVE", message = "Value has to be ACTIVE or INACTIVE")
    private String authTokenStatus;
    
    @NotBlank
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Kolkata")
    private Timestamp createDate;
    
    @NotBlank
    @Size(max = 20)
    private String createBy;
    
    @NotBlank
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd HH:mm:ss", timezone="Asia/Kolkata")
    private Timestamp updateDate;
    
    @NotBlank
    @Size(max = 20)
    private String updateBy;
    
    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
