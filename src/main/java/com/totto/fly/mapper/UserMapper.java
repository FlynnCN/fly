package com.totto.fly.mapper;

import com.totto.fly.pojo.User;
import com.totto.fly.pojo.UserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
    
    /**
     * 通用动态条件查询用户
     * @param condition
     * @param startTime
     * @param endTime
     * @param queue
     * @return List<User>
     * @author Flynn
     * @date 2018年8月24日下午3:26:07
     */
    List<User> findUserByCondition(@Param(value="condition") String condition,@Param(value="startTime") String startTime,@Param(value="endTime") String endTime,@Param(value="queue") String queue);
}