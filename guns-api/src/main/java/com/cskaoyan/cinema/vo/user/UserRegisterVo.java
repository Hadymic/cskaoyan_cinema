package com.cskaoyan.cinema.vo.user;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * 用户注册vo
 *
 * @author hadymic
 */
@Data
public class UserRegisterVo implements Serializable {
    private static final long serialVersionUID = 2088352005394880453L;

    @NotBlank(message = "用户名不能为空")
    @Size(min = 5, max = 12, message = "用户名长度应为5-12位")
    private String username;
    @NotBlank(message = "密码不能为空")
    @Size(min = 6, max = 12, message = "密码长度应为6-12位")
    private String password;
    @Pattern(regexp = "^[a-zA-Z0-9_-]+@[a-zA-Z0-9_-]+(\\.[a-zA-Z0-9_-]+)+$", message = "")
    private String email;
    @Pattern(regexp = "^[1](([3][0-9])|([4][5-9])|([5][0-3,5-9])|([6][5,6])|([7][0-8])|([8][0-9])|([9][1,8,9]))[0-9]{8}$", message = "手机号格式不正确")
    private String mobile;
    @NotBlank(message = "地址不能为空")
    private String address;
}
