package com.totto.fly.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.totto.fly.pojo.User;
import com.totto.fly.service.UserService;
import com.totto.fly.util.TottoResult;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(value = "用户管理")
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
	
	/**
	 * 用户信息录入
	 * @param user
	 * @return TottoResult
	 * @author Flynn
	 * @date 2018年8月24日下午6:27:22
	 */
	@ApiOperation(value = "用户信息录入")
	@PostMapping("/insertUser")
	@ResponseBody
	public TottoResult insertUser(@Valid @ModelAttribute User user) {
		user.setCreatetime(new Date());
		return userService.insertUser(user);
	}
	
	/**
	 * 分页查询用户信息
	 * @param page
	 * @param rows
	 * @return TottoResult
	 * @author Flynn
	 * @date 2018年8月24日下午6:27:36
	 */
	@ApiOperation(value = "分页查询用户信息")
	@GetMapping("/findUserByPage")
	@ResponseBody
	public TottoResult findUserByPage(
			@Valid @RequestParam(value = "page", required = false) @ApiParam(value = "分页当前页") Integer page,
			@Valid @RequestParam(value = "rows", required = false) @ApiParam(value = "分页每页条数") Integer rows) {
		return userService.findUserByPage(page,rows);
	}
	
	/**
	 * 通用动态条件查询用户信息
	 * @param condition
	 * @param startTime
	 * @param endTime
	 * @param queue
	 * @param page
	 * @param rows
	 * @return TottoResult
	 * @author Flynn
	 * @date 2018年8月24日下午6:27:48
	 */
	@ApiOperation(value = "通用动态条件查询用户信息")
	@GetMapping("/findUserByCondition")
	@ResponseBody
	public TottoResult findUserByCondition(
			@Valid @RequestParam(value = "condition", required = false) @ApiParam(value = "关键词模糊搜索") String condition,
			@Valid @RequestParam(value = "startTime", required = false) @ApiParam(value = "查询开始时间") String startTime,
			@Valid @RequestParam(value = "endTime", required = false) @ApiParam(value = "查询结束时间") String endTime,
			@Valid @RequestParam(value = "queue", required = false) @ApiParam(value = "排序方式") String queue,
			@Valid @RequestParam(value = "page", required = false) @ApiParam(value = "分页当前页") Integer page,
			@Valid @RequestParam(value = "rows", required = false) @ApiParam(value = "分页每页条数") Integer rows) {

		return userService.findUserByCondition(condition, startTime, endTime, queue,page,rows);
	}
	
	/**
	 * 根据ID查询用户
	 * @param id
	 * @return TottoResult
	 * @author Flynn
	 * @date 2018年8月24日下午7:11:07
	 */
	@ApiOperation(value = "根据ID查询用户")
	@GetMapping("/findUserById")
	@ResponseBody
	public TottoResult findUserById(@Valid @RequestParam(value = "id", required = true) Integer id) {
		return userService.findUserById(id);
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return TottoResult
	 * @author Flynn
	 * @date 2018年8月25日下午2:40:16
	 */
	@ApiOperation(value = "更新用户信息")
	@PostMapping("/updateUser")
	@ResponseBody
	public TottoResult updateUser(@Valid @ModelAttribute User user) {
		return userService.updateUser(user);
	}
	
	@ApiOperation(value = "根据ID删除用户")
	@GetMapping("/deleteUserById")
	public TottoResult deleteUserById(@Valid @RequestParam(value = "id", required = true) Integer id) {
		return userService.deleteUserById(id);
	}
}
