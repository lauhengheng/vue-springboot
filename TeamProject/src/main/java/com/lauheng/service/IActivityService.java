package com.lauheng.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.lauheng.model.Activity;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 社团活动管理 服务类
 * </p>
 *
 * @author lauheng
 * @since 2022-10-11
 */
public interface IActivityService extends IService<Activity> {

    /**
     * 查询社团活动管理分页数据
     *
     * @param page      页码
     * @param pageCount 每页条数
     * @return IPage<Activity>
     */
    IPage<Activity> findListByPage(Integer page, Integer pageCount);

    /**
     * 添加社团活动管理
     *
     * @param activity 社团活动管理
     * @return int
     */
    int add(Activity activity);

    /**
     * 删除社团活动管理
     *
     * @param id 主键
     * @return int
     */
    int delete(Long id);

    /**
     * 修改社团活动管理
     *
     * @param activity 社团活动管理
     * @return int
     */
    int updateData(Activity activity);

    /**
     * id查询数据
     *
     * @param id id
     * @return Activity
     */
    Activity findById(Long id);

    /**
     * 高级查询
     */
    PageInfo<Activity> queryList(int page,int limit,
                                 String name,String tel,
                                 Integer userId);

    /**
     * 根据id修改状态
     */
    boolean updateStatus(int status,int id);

    /**
     * 根据teamId获取最新的社团活动信息
     */
    Activity findByTeamId(int teamId);
}
