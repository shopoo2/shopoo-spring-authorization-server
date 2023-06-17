package com.szmengran.authorization.dto.cqe;

import com.szmengran.cola.dto.Command;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @Author <a href="mailto:android_li@sina.cn">MaoYuan.Li</a>
 * @Date 2023/6/17 11:39
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateCmd extends Command {

	/**
	 * 用户名
	 */
	@Schema(description = "用户名")
	private String username;
	/**
	 * 昵称
	 */
	@Schema(description = "昵称")
	private String nickname;
	/**
	 * 性别 0-女 1-男
	 */
	@Schema(description = "性别 0-女 1-男")
	private Short sex;
}
