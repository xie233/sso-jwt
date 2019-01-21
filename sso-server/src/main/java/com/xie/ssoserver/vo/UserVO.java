package com.xie.ssoserver.vo;

import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.springframework.beans.factory.annotation.Value;

import javax.validation.constraints.NotNull;


@Data
public class UserVO {


    @NotNull
    private String username;

    @NotNull
    @Length(min = 6)
    private String password;

    @Value("$words")
    private String words;

    @Override
    public String toString() {
        return "UserVO[" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", words='" + words + '\'' +
                ']';
    }
}
