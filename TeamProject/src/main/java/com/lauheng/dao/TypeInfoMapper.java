package com.lauheng.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.lauheng.model.TypeInfo;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * <p>
 * 社团分类和介绍 Mapper 接口
 * </p>
 *
 * @author lauheng
 * @since 2022-10-11
 */
@Component("typeDao")
public interface TypeInfoMapper extends BaseMapper<TypeInfo> {
    //支持高级查询
    List<TypeInfo> queryTypeList(TypeInfo info);

}
