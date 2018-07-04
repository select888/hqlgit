package com.focus.action.core.vo;

import java.util.List;
import java.util.Map;

public class Pager {

	private List list;
	private Integer totalCount=0;
	private Integer pageNum=1;
	private Integer numPerPage=10;
	private String orderField;
	private String orderDirection;
	private String targetType = "navTab";
	private Integer currentPage=1;
	private Integer pageNumShown=10;
	public Pager() {
	}
	
	public Pager(List list, Integer totalCount) {
		super();
		this.list = list;
		this.totalCount = totalCount;
	}

	public List getList() {
		return list;
	}
	public void setList(List list) {
		this.list = list;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}
	public Integer getPageNum() {
		if(pageNum==0)return 1;
		return pageNum-1;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
	public Integer getNumPerPage() {
		return numPerPage;
	}
	public void setNumPerPage(Integer numPerPage) {
		this.numPerPage = numPerPage;
	}
	public String getOrderField() {
		return orderField;
	}
	public void setOrderField(String orderField) {
		this.orderField = orderField;
	}
	public String getOrderDirection() {
		return orderDirection;
	}
	public void setOrderDirection(String orderDirection) {
		this.orderDirection = orderDirection;
	}
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public Integer getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	public Integer getPageNumShown() {
		return pageNumShown;
	}
	public void setPageNumShown(Integer pageNumShown) {
		this.pageNumShown = pageNumShown;
	}
	
	
	
}
