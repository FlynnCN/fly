package com.totto.fly.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.totto.fly.pojo.User;
import com.totto.fly.util.TottoResult;

public interface UserService {
	
	/**
	 * 添加一个User用户
	 * @param user
	 * @return TottoResult
	 * @author Flynn
	 * @date 2018年8月24日下午2:16:45
	 */
	public TottoResult insertUser(User user);
	
	/**
	 * 分页或全部查询所有用户
	 * @return TottoResult
	 * @author Flynn
	 * @date 2018年8月24日下午3:26:59
	 */
	public TottoResult findUserByPage(Integer page, Integer rows);
	
	/**
	 * 通用动态条件查询
	 * @param condition
	 * @param startTime
	 * @param endTime
	 * @param queue
	 * @param page
	 * @param rows
	 * @return TottoResult
	 * @author Flynn
	 * @date 2018年8月24日下午6:22:38
	 */
	public TottoResult findUserByCondition(String condition,String startTime,String endTime,String queue,Integer page, Integer rows);
	
	/**
	 * 根据ID查询用户
	 * @param id
	 * @return TottoResult
	 * @author Flynn
	 * @date 2018年8月24日下午6:57:29
	 */
	public TottoResult findUserById(Integer id);
	
	/**
	 * 根据ID修改用户信息
	 * @param user
	 * @return TottoResult
	 * @author Flynn
	 * @date 2018年8月24日下午7:11:49
	 */
	public TottoResult updateUser(User user);
	
	/**
	 * 根据ID删除用户
	 * @param id
	 * @return TottoResult
	 * @author Flynn
	 * @date 2018年8月25日下午2:44:47
	 */
	public TottoResult deleteUserById(Integer id);
	
	/**
	 * 注册用户
	 * @param user
	 * @return TottoResult
	 * @author Flynn
	 * @date 2018年8月25日下午3:40:10
	 */
	public String register(User user,String code,HttpSession session,HttpServletRequest request);
	
	/**
	 * 获取邮箱验证码
	 * @param email
	 * @param session
	 * @return TottoResult
	 * @author Flynn
	 * @date 2018年8月25日下午3:57:39
	 */
	public TottoResult getCode(String email,HttpSession session);
	
	/**
	 * 用户登录
	 * @param user
	 * @param session
	 * @return User
	 * @author Flynn
	 * @date 2018年8月25日下午4:18:46
	 */
	public User login(User user,HttpSession session);
	
	/**
	 * 检查用户邮箱是否唯一
	 * @param email
	 * @return Boolean
	 * @author Flynn
	 * @date 2018年8月25日下午4:22:58
	 */
	public Boolean checkEmail(String email);
	
	/**
	 * 修改用户密码
	 * @param user
	 * @param code
	 * @param session
	 * @param request
	 * @return String
	 * @author Flynn
	 * @date 2018年8月25日下午4:50:51
	 */
	public String updatePassword(User user,String code,HttpSession session,HttpServletRequest request);
	
	/**
	 * 根据邮箱查找用户
	 * @param email
	 * @return User
	 * @author Flynn
	 * @date 2018年8月25日下午4:51:06
	 */
	public User findByEmail(String email);
	
	/**
	 * 用户修改密码时获取邮箱验证码
	 * @param email
	 * @param session
	 * @return TottoResult
	 * @author Flynn
	 * @date 2018年8月25日下午4:51:32
	 */
	public TottoResult getNewCode(String email,HttpSession session);
	
	/**
	 * 用户登出
	 * @return TottoResult
	 * @author Flynn
	 * @date 2018年8月25日下午4:55:17
	 */
	public TottoResult logout(HttpSession session);
}
