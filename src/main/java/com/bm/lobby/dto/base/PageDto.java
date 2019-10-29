package com.bm.lobby.dto.base;

import java.util.List;
import java.util.Map;

public class PageDto<T>  extends BaseEntity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 5475357535121896225L;

	/**
     * 总条数
     */
    private long total;

    /**
     * 总页数
     */
    private Integer totalPage;

    /**
     * 页码
     */
    private Integer curPage;

    /**
     * 页数
     */
    private Integer pageSize;

    /**
     * 数据集
     */
    private List<T> list;

    public PageDto() {

    }

    /**
     * @param total    总数
     * @param curPage  页码
     * @param pageSize 页数
     * @param list     数据
     */
    public PageDto(long total, Integer curPage, Integer pageSize, List<T> list) {
        this.total = total;
        this.totalPage = (int)(total % pageSize == 0 ? total / pageSize : total / pageSize + 1);
        this.curPage = curPage;
        this.pageSize = pageSize;
        this.list = list;
    }

    public PageDto(long total, Integer curPage, Integer pageSize, Map<String, Object> dateMap, List<T> list) {
        this.total = total;
        this.totalPage = (int)(total % pageSize == 0 ? total / pageSize : total / pageSize + 1);
        this.curPage = curPage;
        this.pageSize = pageSize;
        this.list = list;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getCurPage() {
        return curPage;
    }

    public void setCurPage(Integer curPage) {
        this.curPage = curPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

}
