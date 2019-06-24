package com.hsamgle.mongodb.entity;

import java.util.ArrayList;
import java.util.List;


/**
 *
 *  @feture   :	    TODO		Mongodb 中实体分页查询的封装对象
 *	@file_name:	    PageInfo.java
 * 	@packge:	    com.hsamgle.basic.mongodb.entity
 *	@author:	    黄鹤老板
 *  @create_time:	2018/3/27 9:54
 *	@company:		江南皮革厂
 */
public final class PageInfo<T> {

	/** 当前页  */
	private int pNow;
	
	/** 当前的页包含的数据量  */
	private int pSize;
	
	/** 数据  */
	private  List<T> data;
	
	/** 总页数  */
	private int pages;

	/** 总列数  */
	private int rowCount;
	
	/** 是否有下一页 */
	private boolean hasNext;
	
	/** 是否有上一页 */
	private boolean hasPrevious;

	public PageInfo() throws Exception{
		buildPage(0,null,0,0);
	}
	
	public PageInfo(long rowCount,List<T> data,int pNow,int pSize) throws Exception{
		buildPage(new Long(rowCount).intValue(),data,pNow,pSize);
	}
	
	public int getpNow() {
		return pNow;
	}

	public void setpNow(int pNow) {
		this.pNow = pNow;
	}

	public int getpSize() {
		return pSize;
	}

	public void setpSize(int pSize) {
		this.pSize = pSize;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getRowCount() {
		return rowCount;
	}

	public void setRowCount(int rowCount) {
		this.rowCount = rowCount;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	
	private void buildPage(int rowCount,List<T> data,int pNow,int pSize) throws Exception{
		this.pNow = pNow;
		this.rowCount = rowCount;
		this.data = data!=null?data:new ArrayList<>();
		if(rowCount > 0 && pSize > 0){
			int size = data.size();
			this.pSize = size;
			this.pages = rowCount % pSize==0?rowCount / pSize:rowCount / pSize +1;
			this.hasNext = rowCount > pNow * pSize;
			this.hasPrevious = pNow != 1;
		}
	}
	
	
	@Override
	public String toString() {
		return "PageInfo [pNow=" + pNow + ", pSize=" + pSize + ", data.size= " + data.size()
				+ ", pages=" + pages + ", rowCount=" + rowCount + ", hasNext="
				+ hasNext + ", hasPrevious=" + hasPrevious + "]";
	}
	
	
}
