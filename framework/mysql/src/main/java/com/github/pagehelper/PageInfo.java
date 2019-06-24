/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014-2017 abel533@gmail.com
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.github.pagehelper;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 
 *  @feture   :	    TODO		   自定义的分页结构
 *	@file_name:	    PageInfo.java
 * 	@packge:	    com.github.pagehelper
 *	@author:	    黄鹤老板 
 *  @create_time:	2018/5/27 17:40
 *	@company:		江南皮革厂
 */
public class PageInfo<T>  implements Serializable {
	private static final long serialVersionUID = 732887598033985830L;

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
	
    public PageInfo() {
    	buildPage(0,null,0,0);
    }

    public PageInfo(long rowCount,List list,int pNow,int pSize) {
        buildPage(rowCount,list,pNow,pSize);
    }

    /**
     * 包装Page对象
     *
     * @param list
     */
    public PageInfo(List<T> list) {
    	if(list==null || list.isEmpty()){
    		buildPage(0,null,0,0);
    	}else{
	    	if (list instanceof Page) {
	            Page<T> page = (Page<T>) list;
				buildPage(new Long(page.getTotal()).intValue(),page,page.getPageNum(),page.getPageSize());                       
	    	}
    	}
    }

    private void buildPage(long rowCount,List list,int pNow,int pSize){
        this.pNow = pNow;
        this.rowCount = (int)rowCount;
        this.data = list!=null?list:new ArrayList<>();
        if(rowCount > 0 && pSize > 0){
            this.pSize = data.size();
            this.pages = (int)(rowCount % pSize==0?rowCount / pSize:rowCount / pSize +1);
            this.hasNext = rowCount > pNow * pSize;
            this.hasPrevious = pNow != 1;
        }
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

	public boolean isHasNext() {
		return hasNext;
	}

	public void setHasNext(boolean hasNext) {
		this.hasNext = hasNext;
	}

	public boolean isHasPrevious() {
		return hasPrevious;
	}

	public void setHasPrevious(boolean hasPrevious) {
		this.hasPrevious = hasPrevious;
	}

	@Override
	public String toString() {
		return "PageInfo [pNow=" + pNow + ", pSize=" + pSize + ", data=" + data
				+ ", pages=" + pages + ", rowCount=" + rowCount + ", hasNext="
				+ hasNext + ", hasPrevious=" + hasPrevious + "]";
	}
 
	
	
}
