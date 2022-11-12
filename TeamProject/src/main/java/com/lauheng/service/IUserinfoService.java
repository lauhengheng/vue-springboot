package com.lauheng.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.lauheng.model.LeaveInfo;
import com.lauheng.model.Userinfo;

/**
 * <p>
 * 管理员信息表 服务类
 * </p>
 *
 * @author lauheng
 * @since 2022-10-11
 */
public interface IUserinfoService extends IService<Userinfo> {

    /**
     * 查询管理员信息表分页数据
     *
     * @param page      页码
     * @param pageCount 每页条数
     * @return IPage<Userinfo>
     */
    IPage<Userinfo> findListByPage(Integer page, Integer pageCount);

    /**
     * 添加管理员信息表
     *
     * @param userinfo 管理员信息表
     * @return int
     */
    int add(Userinfo userinfo);

    /**
     * 删除管理员信息表
     *
     * @param id 主键
     * @return int
     */
    int delete(Long id);

    /**
     * 修改管理员信息表
     *
     * @param userinfo 管理员信息表
     * @return int
     */
    int updateData(Userinfo userinfo);

    /**
     * id查询数据
     *
     * @param id id
     * @return Userinfo
     */
    Userinfo findById(Long id);

    /**
     * 根据对象属性值查询对象是否存在
     * 用户名 密码 角色
     */
    Userinfo queryUserInfoByUserNameAndPasswordAndType(Userinfo userinfo);

    /**
     * 高级查询
     */
    PageInfo<Userinfo> queryList(int page, int limit,
                                  Userinfo info);

    /**
     * 修改密码
     */
    int updatePassWord(Integer id,String newPassWord);
}
