package cn.itsource.basic.util;

import java.util.ArrayList;
import java.util.List;

public class PageList<T> {
    private Long total = 0L;
    private List<T> list = new ArrayList<>();

    public PageList() {
    }

    public PageList(Long total, List<T> list) {

        this.total = total;
        this.list = list;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}
