package com.lauheng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lauheng.model.Userinfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 管理员信息表 Mapper 接口
 * </p>
 *
 * @author lauheng
 * @since 2022-10-11
 */
@Component("userInfoDao")
public interface UserinfoMapper extends BaseMapper<Userinfo> {

    /**
     * 根据对象属性值查询对象是否存在
     * 用户名 密码 角色
     */
    Userinfo queryUserInfoByUserNameAndPasswordAndType(Userinfo userinfo);

    /**
     * 高级查询
     */
    List<Userinfo> queryUserInfoList(Userinfo userinfo);

    /**
     * 修改密码
     */
    int  updatePasswordByIdAndNewPassword(@Param("id") Integer id,
                                          @Param("pwd") String pwd);
}
