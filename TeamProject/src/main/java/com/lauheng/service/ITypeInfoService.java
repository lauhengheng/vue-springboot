package com.lauheng.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;
import com.lauheng.model.TypeInfo;

/**
 * <p>
 * 社团分类和介绍 服务类
 * </p>
 *
 * @author lauheng
 * @since 2022-10-11
 */
public interface ITypeInfoService extends IService<TypeInfo> {

    /**
     * 查询社团分类和介绍分页数据
     *
     * @param page      页码
     * @param pageCount 每页条数
     * @return IPage<TypeInfo>
     */
    IPage<TypeInfo> findListByPage(Integer page, Integer pageCount);

    /**
     * 添加社团分类和介绍
     *
     * @param typeInfo 社团分类和介绍
     * @return int
     */
    int add(TypeInfo typeInfo);

    /**
     * 删除社团分类和介绍
     *
     * @param id 主键
     * @return int
     */
    int delete(Long id);

    /**
     * 修改社团分类和介绍
     *
     * @param typeInfo 社团分类和介绍
     * @return int
     */
    int updateData(TypeInfo typeInfo);

    /**
     * id查询数据
     *
     * @param id id
     * @return TypeInfo
     */
    TypeInfo findById(Long id);
    //支持分页高级查询
    PageInfo<TypeInfo> queryTypelist(int page,int pageSize,TypeInfo info);
}
