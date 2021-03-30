package edu.zsq.utils.page;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangsongqi
 * @date 2:00 下午 2021/3/30
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PageData<T> implements Serializable {

    public static final int DEFAULT_PAGE_NO = 1;
    public static final int DEFAULT_LIMIT = 20;

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "查询数据列表")
    private List<T> records;

    @ApiModelProperty(value = "总数")
    private long total;

    @ApiModelProperty(value = "当前页")
    private long current;

    @ApiModelProperty(value = "每页显示条数")
    private long size;

    public PageData() {
        this(null, 0, 0);
    }

    public PageData(List<T> records, long size, long total) {
        this(records, 1, size, total);
    }

    public PageData(List<T> records, long current, long size, long total) {
        if (records == null) {
            records = new ArrayList<>();
        }
        this.records = records;
        this.total = total;
        this.current = current;
        this.size = size;
    }

    public static <T> PageData<T> empty() {
        return new PageData<T>();
    }

    public static <T> PageData<T> of(List<T> records, long size, long total) {
        return new PageData<>(records, size, total);
    }

    public static <T> PageData<T> of(List<T> records, long current, long size, long total) {
        return new PageData<>(records, current, size, total);
    }

    /**
     * 自适应页号
     * @param pageNo 传参页号
     * @return 适应页号
     */
    public static int adaptPageNo(Integer pageNo) {
        return pageNo == null || pageNo < 1 ? DEFAULT_PAGE_NO : pageNo;
    }

    /**
     * 自适应分页条数
     * @param limit 传参条数
     * @return 适应条数
     */
    public static int adaptLimit(Integer limit) {
        return limit == null || limit < 1 ? DEFAULT_LIMIT : limit;
    }

    /**
     * @return 总页数
     */
    @ApiModelProperty(value = "总页数")
    public long getTotalPage() {
        return size == 0 ? 0 : (total % size == 0 ? total / size : total / size + 1);
    }

    public List<T> getRecords() {
        return records;
    }

    public PageData<T> setRecords(List<T> records) {
        this.records = records;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public PageData<T> setTotal(long total) {
        this.total = total;
        return this;
    }

    public long getSize() {
        return size;
    }

    public PageData<T> setSize(long size) {
        this.size = size;
        return this;
    }

    public long getCurrent() {
        return current;
    }

    public PageData<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    @Override
    public String toString() {
        return "PageData{" +
                "records=" + records +
                ", total=" + total +
                ", current=" + current +
                ", size=" + size +
                '}';
    }
}
