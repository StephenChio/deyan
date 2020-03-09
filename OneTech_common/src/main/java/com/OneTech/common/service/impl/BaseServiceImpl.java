package com.OneTech.common.service.impl;

import com.OneTech.common.field.DataOperateEnum;
import com.OneTech.common.mapper.IBaseMapper;
import com.OneTech.common.service.IBaseService;
import com.OneTech.common.util.BooleanUtils;
import com.OneTech.common.util.UUIDUtils;
import com.OneTech.common.vo.LayUiPageVO;
import com.OneTech.common.vo.StatusBean;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.Date;
import java.util.List;
import java.util.Map.Entry;

/**
 * @description 通用service实现类
 * @date 2018年7月27日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
@Primary
public class BaseServiceImpl<T> implements IBaseService<T> {

	public static Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
	
	@Autowired
    private SqlSessionFactory sqlSessionFactory;
	
    @Autowired
    protected IBaseMapper<T> mapper;
    
    
    /**批量插入条数*/
    private int batchInsertSize = 1000;
    
    public IBaseMapper<T> getMapper() {
    	return mapper;
    }
    
    @Override
    @Transactional(rollbackFor= Exception.class)
    public int save(T entity) throws Exception {
        return mapper.insert(entity);
    }
    
    @Override
    @Transactional(rollbackFor= Exception.class)
	public int deleteByExample(Object example) throws Exception {
		return mapper.deleteByExample(example);
	}
	@Override
	@Transactional(rollbackFor= Exception.class)
	public int delete(T entity) throws Exception {
		return  mapper.delete(entity);
	}

	@Override
	@Transactional(rollbackFor= Exception.class)
	public int batchDelete(List<T> list) throws Exception {
    	int sign = 200;
		for(T entity:list){
			sign = mapper.delete(entity);
		}
		return sign;
	}

	/**
     * 根据Example条件更新实体`record`包含的不是null的属性值
     * @param entity
     * @param example
     * @return
     */
    @Override
    @Transactional(rollbackFor= Exception.class)
    public int updateByExampleSelective(T entity, Object example) throws Exception {
        return mapper.updateByExampleSelective(entity, example);
    }

    @Override
    @Transactional(readOnly=true)
    public List<T> select(T entity){
    	return mapper.select(entity);
    }

    @Override
    @Transactional(readOnly=true)
    public List<T> selectAll() throws Exception {
    	return mapper.selectAll();
    }

    @Transactional(readOnly=true)
    public T selectByPrimaryKey(Object key){
    	return mapper.selectByPrimaryKey(key);
    }

    @Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)
    public LayUiPageVO<T> queryByPage(JSONObject json, Class<?> clazz) throws Exception {
    	pageHelpInit(json);
    	Example example = constructorExample(json,clazz);
    	List<T> list = mapper.selectByExample(example);
    	return new LayUiPageVO<T>(list);
    }

	@Override
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)
	public T selectOne(T entity) throws Exception {
		return mapper.selectOne(entity);
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional(rollbackFor= Exception.class)
	public StatusBean<?> saveOrUpdate(JSONObject json, T destEntiry) throws Exception {
		Date date = new Date();
		T entity = (T) JSONObject.toJavaObject(json,destEntiry.getClass());
		String id = BeanUtils.getProperty(entity, "id");
		BeanUtils.setProperty(entity, "updateTime", date);
		if(!StringUtils.isEmpty(id)){
			//更新
			Example example = new Example(entity.getClass());
			example.createCriteria().andEqualTo("id", id);
			this.updateByExampleSelective(entity,example);
		}else{
			BeanUtils.setProperty(entity, "id", UUIDUtils.getRandom32());
			//保存
			BeanUtils.setProperty(entity, "createTime", date);
			this.save(entity);
		}
		/* 根据id查询对象*/
		entity = this.selectByPrimaryKey(BeanUtils.getProperty(entity, "id"));
		BeanUtils.copyProperties(destEntiry, entity);//copy转换后的对象到传入的空数据对象
		return StatusBean.successMsg();

	}
	@Override
	@Transactional(rollbackFor= Exception.class)
	public StatusBean<?> saveOrUpdate(T entity) throws Exception {
		Date date = new Date();
		String id = BeanUtils.getProperty(entity, "id");
//		SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd hh:mm:ss");
		BeanUtils.setProperty(entity, "updateTime", date);
		if(!StringUtils.isEmpty(id)){
			//更新
			Example example = new Example(entity.getClass());
			example.createCriteria().andEqualTo("id", id);
			this.updateByExampleSelective(entity,example);
		}else{
			BeanUtils.setProperty(entity, "id", UUIDUtils.getRandom32());
			//保存
			BeanUtils.setProperty(entity, "createTime", date);
			this.save(entity);
		}

		/* 根据id查询对象*/
		T destEntiry = this.selectByPrimaryKey(BeanUtils.getProperty(entity, "id"));
		if(BooleanUtils.isNotEmpty(destEntiry)) {
			BeanUtils.copyProperties(entity, destEntiry);//copy转换后的对象到传入的空数据对象
		}
		return StatusBean.successMsg();
	}

	@Override
	@Transactional(readOnly=true, propagation=Propagation.NOT_SUPPORTED)
	public List<T> selectByExample(Example example) throws Exception {
		return mapper.selectByExample(example);
	}

	@Override
	@Transactional(rollbackFor= Exception.class)
	public StatusBean<?> batchDeleteByIdArray(T entity, Iterable<?> ids) throws Exception {
		if(BooleanUtils.isNotEmpty(ids)) {
			Example example = new Example(entity.getClass());
			example.createCriteria().andIn("id", ids);
			return StatusBean.successMsg(mapper.deleteByExample(example));
		}
		return StatusBean.failMsg();
	}


	@Override
	@Transactional(rollbackFor= Exception.class)
	public void batchInsert(List<T> list) throws Exception {
		
		int startLen = 0;
		int targetLen = list.size();
		int size = targetLen;
		Boolean flag = false;
		SqlSession session = sqlSessionFactory.openSession(ExecutorType.BATCH,false);
		do {
			if(BooleanUtils.isEmpty(list)) {
				continue;
			}
			if(targetLen > batchInsertSize) {
				mapper.insertList(list.subList(startLen, startLen + batchInsertSize));
				startLen += batchInsertSize;
				targetLen = size - startLen;
				flag = true;
				session.commit();
                session.clearCache();
			}else {
				mapper.insertList(list.subList(startLen, size));
				flag = false;
				session.commit();
                session.clearCache();
			}
		} while (flag);
	}
	
	/**
	 * 分页初始化
	 * @param json
	 */
	public void pageHelpInit(JSONObject json) throws Exception {
		Integer pageNum = json.getInteger(DataOperateEnum.pageNum.toString());
    	Integer pageSize = json.getInteger(DataOperateEnum.pageSize.toString());
    	
    	if(null == pageNum){
    		pageNum = 1;
    	}
    	
    	if(null == pageSize){
    		pageSize = 10;
    	}
    	PageHelper.startPage(pageNum, pageSize);
	}
	
	
	/**
	 * @param json 通过请求参数，clazz 构造example对象
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public Example constructorExample(JSONObject json, Class<?> clazz)  throws Exception {
		String orderBy = json.getString(DataOperateEnum.orderBy.toString());
    	Example example = new Example(clazz);
    	if(!StringUtils.isEmpty(orderBy)){
    		example.setOrderByClause(orderBy);
    	}
    	Criteria criteria = example.createCriteria();
    	for(Entry<String, Object> entity : json.entrySet()){
    		Object value = entity.getValue();
    		String key = entity.getKey();
    		//排除分页,排序字段
    		if(!DataOperateEnum.pageNum.toString().equals(key) && !DataOperateEnum.pageSize.toString().equals(key) && !BooleanUtils.isEmpty(value) && !DataOperateEnum.orderBy.toString().equals(key)){
    			if(key.contains(DataOperateEnum.like_.toString())){
    				criteria.andLike(key.replace(DataOperateEnum.like_.toString(), ""), "%" + value + "%" );
    			}else if(key.contains(DataOperateEnum.sqlNotIn_.toString())) {
    				criteria.andNotIn(key.replace(DataOperateEnum.sqlNotIn_.toString(), ""), (Iterable<?>)value );
    			}else if(key.contains(DataOperateEnum.sqlIn_.toString())) {
    				criteria.andIn(key.replace(DataOperateEnum.sqlIn_.toString(), ""), (Iterable<?>)value);
    			}else{
    				criteria.andEqualTo(key, value);
    			}
    		}
    	}
    	return example;
	}
}
