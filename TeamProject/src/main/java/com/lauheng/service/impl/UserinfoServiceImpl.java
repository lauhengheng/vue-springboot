package com.lauheng.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lauheng.model.LeaveInfo;
import com.lauheng.model.Userinfo;
import com.lauheng.dao.UserinfoMapper;
import com.lauheng.service.IUserinfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;

import java.util.List;

/**
 * <p>
 * 管理员信息表 服务实现类
 * </p>
 *
 * @author lauheng
 * @since 2022-10-11
 */
@Service
public class UserinfoServiceImpl extends ServiceImpl<UserinfoMapper, Userinfo> implements IUserinfoService {
    @Autowired
    private UserinfoMapper userInfoDao;
    @Override
    public  IPage<Userinfo> findListByPage(Integer page, Integer pageCount){
        IPage<Userinfo> wherePage = new Page<>(page, pageCount);
        Userinfo where = new Userinfo();

        return   baseMapper.selectPage(wherePage, Wrappers.query(where));
    }

    @Override
    public int add(Userinfo userinfo){
        return baseMapper.insert(userinfo);
    }

    @Override
    public int delete(Long id){
        return baseMapper.deleteById(id);
    }

    @Override
    public int updateData(Userinfo userinfo){
        return baseMapper.updateById(userinfo);
    }

    @Override
    public Userinfo findById(Long id){
        return  baseMapper.selectById(id);
    }

    @Override
    public Userinfo queryUserInfoByUserNameAndPasswordAndType(Userinfo userinfo) {
        return userInfoDao.queryUserInfoByUserNameAndPasswordAndType(userinfo);
    }

    @Override
    public PageInfo<Userinfo> queryList(int page, int limit, Userinfo info) {
        PageHelper.startPage(page,limit);
        List<Userinfo> activities = userInfoDao.queryUserInfoList(info);
        PageInfo<Userinfo> pageInfo=new PageInfo<>(activities);
        return pageInfo;
    }

    @Override
    public int updatePassWord(Integer id, String newPassWord) {
        return userInfoDao.updatePasswordByIdAndNewPassword(id,newPassWord);
    }
}
