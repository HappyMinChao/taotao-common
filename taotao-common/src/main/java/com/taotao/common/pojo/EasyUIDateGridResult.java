package com.taotao.common.pojo;

import java.util.List;

public class EasyUIDateGridResult {

	private long total;
	private List<?> rows;

	public EasyUIDateGridResult() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EasyUIDateGridResult(long total, List<?> rows) {
		super();
		this.total = total;
		this.rows = rows;
	}

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

	@Override
	public String toString() {
		return "EasyUIDateGridResult [total=" + total + ", rows=" + rows + "]";
	}

}
