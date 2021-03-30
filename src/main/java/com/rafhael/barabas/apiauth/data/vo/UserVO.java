package com.rafhael.barabas.apiauth.data.vo;

import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UserVO implements Serializable {

    public static final long serialVersionUID = 4178403056811617569L;

    private String username;
    private String password;

}
