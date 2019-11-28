package com.OneTech.common.mapper;

import tk.mybatis.mapper.annotation.RegisterMapper;
import tk.mybatis.mapper.common.Mapper;

/**
 * @description 通用Mapper接口
 * @date 2018年12月21日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
@RegisterMapper
public interface IBaseMapper<T> extends Mapper<T> ,tk.mybatis.mapper.additional.insert.InsertListMapper<T> {
	
}
