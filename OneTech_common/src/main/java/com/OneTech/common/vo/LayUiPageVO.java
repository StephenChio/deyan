package com.OneTech.common.vo;

import com.github.pagehelper.PageInfo;

import java.io.Serializable;
import java.util.List;

/**
 * @description layui分页实体
 * @date 2018年7月24日
 * @author 吴森荣
 * @email wsr@basehe.com
 * @version v1.0
 */
public class LayUiPageVO<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int code;//状态，200表示成功
	
	private int pageNum;//页码
    
    private int pageSize;//每页数量
	
    private long total;//总数量
    
    private List<T> list;//分页数据

    //是否有下一页
    private boolean hasNextPage = false;
    
    //下一页
    private int nextPage;
	
    public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public long getTotal() {
		return total;
	}

	public void setTotal(long total) {
		this.total = total;
	}

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

	public LayUiPageVO(List<T> list) {
		PageInfo<T> pageInfo = new PageInfo<>(list);
		this.list = pageInfo.getList();
		this.pageNum = pageInfo.getPageNum();
		this.pageSize = pageInfo.getPageSize();
		this.total = pageInfo.getTotal();
		this.code = 200;
		this.hasNextPage = pageInfo.isHasNextPage();
		this.nextPage = pageInfo.getNextPage();
		
	}
	
    public boolean isHasNextPage() {
        return hasNextPage;
    }

    public void setHasNextPage(boolean hasNextPage) {
        this.hasNextPage = hasNextPage;
    }

    public int getNextPage() {
        return nextPage;
    }

    public void setNextPage(int nextPage) {
        this.nextPage = nextPage;
    }
}
