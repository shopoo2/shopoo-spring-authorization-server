package com.szmengran.authorization.dto;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.SneakyThrows;

import java.io.Serializable;

/**
 * @Description:
 * @Auther: limy66
 * @Date: 2021/08/31/16:10
 */
@Data
public class UserDetailsCO implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = -2638346706733390711L;
    private String userId;
    private String username;
    private Short status;
   

    public boolean isEnabled() {
        return status == 1;
    }
    
    @SneakyThrows
    @Override
    public String toString() {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(this);
    }
}
