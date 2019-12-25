package com.OneTech.common.service;

import com.OneTech.common.vo.LayUiPageVO;
import com.alibaba.fastjson.JSONObject;
import com.OneTech.common.vo.StatusBean;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @description 通用service接口
 * @date 2018年7月20日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
public interface IBaseService<T> {

    /**
     * 保存一个实体，null的属性也会保存，不会使用数据库默认值
     * @param entity
     * @return
     * @throws Exception
     */
    int save(T entity) throws Exception;

    /**
     * 根据example条件删除数据
     * @param example
     * @return
     * @throws Exception
     */
    int deleteByExample(Object example) throws Exception;
    /**
     * 删除一个bean
     * @param
     * @return
     * @throws Exception
     */
    int delete(T entity) throws Exception;
    /**
     * 批量根据example条件删除数据
     * @param example
     * @return
     * @throws Exception
     */
    int batchDelete(List<T> list) throws Exception;
    
    /**
     * 根据id数组删除数据
     * @param claszz
     * @param ids
     * @return
     * @throws Exception
     */
    StatusBean<?> batchDeleteByIdArray(T entity, Iterable<?> ids) throws Exception;
    /**
     * 根据example条件更新数据
     * @param entity
     * @param example
     * @return
     * @throws Exception
     */
    int updateByExampleSelective(T entity, Object example) throws Exception;

    /**
     * 分页查询
     * @param paramMap
     * @param clazz
     * @return
     * @throws Exception
     */
    LayUiPageVO<T> queryByPage(JSONObject json, Class<?> clazz) throws Exception;

    /**
     * 根据实体中的属性值进行查询，查询条件使用等号
     * @param entity
     * @return
     * @throws Exception
     */
    List<T> select(T entity) throws Exception;

    /**
     * 查询所有数据
     * @param entity
     * @return
     * @throws Exception
     */
    List<T> selectAll() throws Exception;

    /**
     * 根据主键查询数据
     * @param key
     * @return
     * @throws Exception
     */
    T selectByPrimaryKey(Object key) throws Exception;

    /**
     * 批量插入数据
     * @param list
     * @throws Exception
     */
    void batchInsert(List<T> list) throws Exception;
    
    /**
     * 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
     * @param entity
     * @return
     * @throws Exception
     */
    T selectOne(T entity) throws Exception;

    /**
     * 根据example条件查询数据
     * @param entity
     * @param example
     * @return
     * @throws Exception
     */
	List<T> selectByExample(Example example) throws Exception;
    
	/**
	 * 保存或更新数据
	 * @param json
	 * @return
	 * @throws Exception
	 */
	StatusBean<?> saveOrUpdate(JSONObject json, T entity) throws Exception;
	
	/**
	 * 保存或更新数据
	 * @return
	 * @throws Exception
	 */
	StatusBean<?> saveOrUpdate(T entity) throws Exception;
	
	
}
