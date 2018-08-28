package com.totto.fly.service.impl;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.totto.fly.mapper.UserMapper;
import com.totto.fly.pojo.PageResult;
import com.totto.fly.pojo.User;
import com.totto.fly.pojo.UserExample;
import com.totto.fly.pojo.UserExample.Criteria;
import com.totto.fly.service.UserService;
import com.totto.fly.util.MD5Utils;
import com.totto.fly.util.TottoResult;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	@Autowired
	private UserMapper userMapper;
	
	@Autowired
	JavaMailSenderImpl mailSender;

	/**
	 * 添加一个用户
	 * 
	 * @author Flynn
	 * @date 2018年8月24日下午3:05:06
	 */
	@Override
	public TottoResult insertUser(User user) {
		userMapper.insert(user);
		return TottoResult.ok(user);
	}

	/**
	 * 分页或全部查询用户信息
	 * 
	 * @author Flynn
	 * @date 2018年8月24日下午3:50:43
	 */
	@Override
	public TottoResult findUserByPage(Integer page, Integer rows) {
		// 为了程序的严谨性，判断非空：
		if (page == null) {
			page = 1; // 设置默认当前页
		}
		if (page <= 0) {
			page = 1;
		}
		if (rows == null) {
			rows = 10; // 设置默认每页显示的条数
		}

		UserExample example = new UserExample();//这个必须在PageHelper前
		// 1、设置分页信息，包括当前页数和每页显示的总计数
		PageHelper.startPage(page, rows);
		// 2、执行查询
		List<User> list = userMapper.selectByExample(example);
		// 3、获取分页查询后的数据
		PageInfo<User> pageInfo = new PageInfo<>(list);
		// 4、封装需要返回的分页实体
		PageResult result = new PageResult();
		// 设置获取到的总记录数total：
		result.setTotal(pageInfo.getTotal());
		// 设置数据集合rows：
		result.setRows(list);

		return TottoResult.ok(result);
	}
	
	/**
	 * 通用动态条件查询
	 * @author Flynn
	 * @date 2018年8月24日下午6:24:03
	 */
	@Override
	public TottoResult findUserByCondition(String condition, String startTime, String endTime, String queue,
			Integer page, Integer rows) {
		if (queue == null || queue.equals("")) {
			queue = "desc";// 默认排序为时间降序
		}

		// 为了程序的严谨性，判断非空：
		if (page == null) {
			page = 1; // 设置默认当前页
		}
		if (page <= 0) {
			page = 1;
		}
		if (rows == null) {
			rows = 10; // 设置默认每页显示的条数
		}

		// 1、设置分页信息，包括当前页数和每页显示的总计数
		PageHelper.startPage(page, rows);
		// 2、执行查询
		List<User> list = userMapper.findUserByCondition(condition, startTime, endTime, queue);
		// 3、获取分页查询后的数据
		PageInfo<User> pageInfo = new PageInfo<>(list);
		// 4、封装需要返回的分页实体
		PageResult result = new PageResult();
		// 设置获取到的总记录数total：
		result.setTotal(pageInfo.getTotal());
		// 设置数据集合rows：
		result.setRows(list);

		return TottoResult.ok(result);
	}
	
	/**
	 * 根据ID查询用户
	 * @author Flynn
	 * @date 2018年8月24日下午6:57:52
	 */
	@Override
	public TottoResult findUserById(Integer id) {
		User user = userMapper.selectByPrimaryKey(id);
		return TottoResult.ok(user);
	}
	
	/**
	 *  根据ID修改用户信息
	 * @author Flynn
	 * @date 2018年8月24日下午7:19:40
	 */
	@Override
	public TottoResult updateUser(User user) {
		userMapper.updateByPrimaryKey(user);
		return TottoResult.ok(user);
	}
	
	/**
	 * 根据ID删除用户
	 * @author Flynn
	 * @date 2018年8月25日下午2:47:38
	 */
	@Override
	public TottoResult deleteUserById(Integer id) {
		userMapper.deleteByPrimaryKey(id);
		return TottoResult.ok("删除"+id+"号用户成功！");
	}
	
	/**
	 * 注册用户
	 * @author Flynn
	 * @date 2018年8月25日下午3:59:32
	 */
	@Override
	public String register(User user,String code,HttpSession session,HttpServletRequest request) {
		String emailCode = (String) session.getAttribute("EmailCode");
		String email = user.getEmail();
		String password1 = user.getPassword();
		if(code!=null) {
			if(code.equals(emailCode)) {
				String password = user.getPassword();
				password = MD5Utils.md5(password);
				user.setPassword(password);
				user.setCreatetime(new Date());
				userMapper.insert(user);
				session.removeAttribute("EmailCode");
				
				SimpleMailMessage message = new SimpleMailMessage();
				message.setSubject("TOTTO用户注册成功");
				message.setText("您的登录邮箱账号是："+email+"  密码为："+password1);
				
				message.setTo(email);
				message.setFrom("1309338083@qq.com");

				mailSender.send(message);
				return "redirect:/index";
			}else {
				request.setAttribute("msg", "验证码不正确！");
				return "register";
			}
		}
		request.setAttribute("msg", "验证码为空！");
		return "register";
	}
	
	/**
	 * 获取邮箱验证码
	 * @author Flynn
	 * @date 2018年8月25日下午3:59:50
	 */
	@Override
	public TottoResult getCode(String email, HttpSession session) {
		SimpleMailMessage message = new SimpleMailMessage();
		//邮件设置
		int i=(int)(Math.random()*1000000); 
		String code = String.valueOf(i);
		message.setSubject("TOTTO系统注册验证码");
		message.setText("你的验证码为："+code);
		
		message.setTo(email);
		message.setFrom("1309338083@qq.com");

		mailSender.send(message);
		session.setAttribute("EmailCode", code);
		
		return TottoResult.ok("获取验证码成功！");
	}
	
	/**
	 * 用户登录
	 * @author Flynn
	 * @date 2018年8月25日下午4:18:34
	 */
	@Override
	public User login(User user, HttpSession session) {
		String password = user.getPassword();
		password = MD5Utils.md5(password);
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmailEqualTo(user.getEmail());
		criteria.andPasswordEqualTo(password);
		List<User> list = userMapper.selectByExample(example);
		if(list!=null && list.size()>0) {
			session.setAttribute("loginUser", list.get(0));
			return list.get(0);
		}
		return null;
	}
	
	/**
	 * 检查用户邮箱是否唯一
	 * @author Flynn
	 * @date 2018年8月25日下午4:24:20
	 */
	@Override
	public Boolean checkEmail(String email) {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmailEqualTo(email);
		List<User> list = userMapper.selectByExample(example);
		if(list!=null && list.size()>0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 修改用户密码
	 * @author Flynn
	 * @date 2018年8月25日下午4:49:30
	 */
	@Override
	public String updatePassword(User user,String code,HttpSession session,HttpServletRequest request) {
		
		String emailCode = (String) session.getAttribute("EmailCode");
		String newPassword = user.getPassword();
		if(code!=null) {
			if(code.equals(emailCode)) {
				String email = user.getEmail();
				String password = user.getPassword();
				User tuser = findByEmail(email);
				password = MD5Utils.md5(password);
				tuser.setPassword(password);
				userMapper.updateByPrimaryKey(tuser);
				
				session.removeAttribute("EmailCode");
				
				SimpleMailMessage message = new SimpleMailMessage();
				message.setSubject("TOTTO用户修改密码成功");
				message.setText("您的登录邮箱账号是："+email+"  新密码为："+newPassword);
				
				message.setTo(email);
				message.setFrom("1309338083@qq.com");

				mailSender.send(message);
				request.setAttribute("msg", "密码修改成功！");
				return "index";
			}else {
				request.setAttribute("msg", "验证码不正确！");
				return "index";
			}
		}
		request.setAttribute("msg", "验证码为空！");
		return "index";
	}
	
	/**
	 * 根据用户邮箱查找用户
	 * @author Flynn
	 * @date 2018年8月25日下午4:49:47
	 */
	@Override
	public User findByEmail(String email) {
		UserExample example = new UserExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmailEqualTo(email);
		List<User> list = userMapper.selectByExample(example);
		return list.get(0);
	}
	
	/**
	 * 修改用户密码时获取邮箱验证码
	 * @author Flynn
	 * @date 2018年8月25日下午4:50:12
	 */
	@Override
	public TottoResult getNewCode(String email, HttpSession session) {
		SimpleMailMessage message = new SimpleMailMessage();
		//邮件设置
		int i=(int)(Math.random()*1000000); 
		String code = String.valueOf(i);
		message.setSubject("TOTTO系统修改密码验证码");
		message.setText("你的验证码为："+code);
		
		message.setTo(email);
		message.setFrom("1309338083@qq.com");

		mailSender.send(message);
		session.setAttribute("EmailCode", code);
		
		return TottoResult.ok("获取验证码成功！");
	}
	
	/**
	 * 用户登出
	 * @author Flynn
	 * @date 2018年8月25日下午4:57:47
	 */
	@Override
	public TottoResult logout(HttpSession session) {
		session.removeAttribute("loginUser");
		return TottoResult.ok("用户登出成功！");
	}

}
