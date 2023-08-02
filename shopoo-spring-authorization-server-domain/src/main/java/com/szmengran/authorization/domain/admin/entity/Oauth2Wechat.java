package com.szmengran.authorization.domain.admin.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * (Oauth2Wechat)实体类
 *
 * @author Joe
 * @since 2023-08-02 17:14:04
 */
@Data
public class Oauth2Wechat implements Serializable {
    private static final long serialVersionUID = 334445053987223826L;
    /**
     * 用户主键
     */
    @TableId
    private String userId;
    /**
     * 开放ID
     */
    private String openid;
    /**
     * 统一ID
     */
    private String unionid;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 电话号码
     */
    private String phone;
    /**
     * 城市
     */
    private String city;
    /**
     * 国家
     */
    private String country;
    /**
     * 性别
     */
    private String gender;
    /**
     * 语言
     */
    private String language;
    /**
     * 省份
     */
    private String province;
    /**
     * 状态 1-启用 0-禁用
     */
    private Short status;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
