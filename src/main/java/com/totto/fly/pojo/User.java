package com.totto.fly.pojo;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModelProperty;

public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@ApiModelProperty(value = "用户ID", required = false)
	private Integer id;

	@ApiModelProperty(value = "用户名", required = false)
    private String username;

	@ApiModelProperty(value = "用户邮件", required = false)
    private String email;

	@ApiModelProperty(value = "用户密码", required = false)
    private String password;

	@ApiModelProperty(value = "添加用户时间", required = false)
	@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
    private Date createtime;

	@ApiModelProperty(value = "用户手机号", required = false)
    private String phone;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public Date getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }
}