package com.totto.fly.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.totto.fly.pojo.User;
import com.totto.fly.service.UserService;

@Controller
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	private UserService userService;
	
	@RequestMapping("/logout")
	public String logout(HttpSession session) {
		userService.logout(session);
		return "redirect:/index";
	}
	
	/**
	 * 修改用户密码
	 * @param user
	 * @param code
	 * @param session
	 * @param request
	 * @return String
	 * @author Flynn
	 * @date 2018年8月25日下午4:48:56
	 */
	@RequestMapping("/updatePassword")
	public String updatePassword(User user,String code,HttpSession session,HttpServletRequest request) {
		return userService.updatePassword(user, code, session, request);
	}
	
	/**
	 * 检查用户邮箱是否已经注册
	 * @param email
	 * @return Boolean
	 * @author Flynn
	 * @date 2018年8月25日下午4:34:19
	 */
	@RequestMapping("/checkEmail")
	@ResponseBody
	public Boolean checkEmail(String email) {
		Boolean result = userService.checkEmail(email);
		return result;
	}
	
	/**
	 * 用户登录
	 * @param user
	 * @param session
	 * @param request
	 * @return String
	 * @author Flynn
	 * @date 2018年8月25日下午4:18:14
	 */
	@RequestMapping("/login")
	public String login(User user,HttpSession session,HttpServletRequest request) {
		User tuser = userService.login(user, session);
		if(tuser!=null) {
			return "redirect:/main";
		}
		request.setAttribute("msg", "用户名或密码错误！");
		return "forward:/index";
	}
	
	/**
	 * 注册时获取邮箱验证码
	 * @param email
	 * @param session void
	 * @author Flynn
	 * @date 2018年8月25日下午4:00:55
	 */
	@RequestMapping("/getCode")
	public void getCode(String email,HttpSession session) {
		userService.getCode(email, session);
	}
	
	/**
	 * 修改密码时获取邮箱验证码
	 * @param email
	 * @param session void
	 * @author Flynn
	 * @date 2018年8月25日下午4:33:36
	 */
	@RequestMapping("/getNewCode")
	public void getNewCode(String email,HttpSession session) {
		userService.getNewCode(email, session);
	}
	
	/**
	 * 注册用户
	 * @param user
	 * @param code
	 * @param session
	 * @param request
	 * @return String
	 * @author Flynn
	 * @date 2018年8月25日下午4:11:21
	 */
	@RequestMapping("/register")
	public String register(User user,String code,HttpSession session,HttpServletRequest request) {
		return userService.register(user, code, session, request);
	}
}
