package com.lauheng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lauheng.model.LeaveInfo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 在线留言管理 Mapper 接口
 * </p>
 *
 * @author lauheng
 * @since 2022-10-11
 */
@Component("leaveDao")
public interface LeaveInfoMapper extends BaseMapper<LeaveInfo> {

    List<LeaveInfo> queryLeaveInfoAll(@Param("tel") String tel);
}
