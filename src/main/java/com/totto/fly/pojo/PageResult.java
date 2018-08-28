package com.totto.fly.pojo;

import java.util.List;

public class PageResult {
	
	private long total;//返回总条数
	
	private List<?> rows;//返回数据列表
	
	public long getTotal() {
		return total;
	}
	public void setTotal(long total) {
		this.total = total;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	
}
