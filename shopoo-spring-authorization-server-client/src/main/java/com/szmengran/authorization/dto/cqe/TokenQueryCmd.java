package com.szmengran.authorization.dto.cqe;

import com.szmengran.cola.dto.Query;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *
 * @Author <a href="mailto:android_li@sina.cn">MaoYuan.Li</a>
 * @Date 2023/6/8 23:53
 */
@Data
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TokenQueryCmd extends Query {

	@Schema(description = "授权类型")
	private String grant_type;

	@Schema(description = "用户名")
	private String username;

	@Schema(description = "密码")
	private String password;

	@Hidden
	@Schema(description = "授权范围")
	private String scope;


	@Schema(description = "客户端ID")
	@NotNull(message = "客户端ID不能为空")
	private String clientId;

	@Schema(description = "客户端密钥")
	@NotNull(message = "客户端密钥不能为空")
	private String clientSecret;
}
