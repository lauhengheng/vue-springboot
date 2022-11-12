package com.lauheng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lauheng.model.Activity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 社团活动管理 Mapper 接口
 * </p>
 *
 * @author lauheng
 * @since 2022-10-11
 */
@Component("activityDao")
public interface ActivityMapper extends BaseMapper<Activity> {

    /**
     * 支持高级查询接口
     * name:活动名称
     * tel:活动联系电话
     * userId:所属社团团长用户id
     */
    List<Activity> queryActivityInfoList(@Param("name") String name,
                                         @Param("tel") String tel,
                                         @Param("userId") Integer userId);

    /**
     * 审核记录信息
     */
    int updateStatusById(@Param("status") Integer status,
                         @Param("id") Integer id);

    /**
     * 根据teamId获取最新的社团活动信息
     */
    Activity findByTeamId(@Param("teamId") int teamId);


}
