package com.szmengran.authorization.domain.admin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (OauthUser)实体类
 *
 * @author makejava
 * @since 2021-05-14 17:14:04
 */
@Data
public class Oauth2User implements Serializable {
    private static final long serialVersionUID = 334445053987223826L;
    /**
     * 用户主键
     */
    @TableId
    private String userId;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 状态
     */
    private Short status;
    /**
     * 性别 0-女 1-男
     */
    private Short sex;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
