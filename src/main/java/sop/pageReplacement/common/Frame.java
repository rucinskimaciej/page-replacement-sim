package sop.pageReplacement.common;

import java.util.LinkedList;
import java.util.List;

public class Frame {
    private Integer page;
    private final int index;

    private final List<Integer> history;

    public Frame(int index) {
        this.index = index;
        history = new LinkedList<>();
    }

    public Integer getPage() {
        return page;
    }
    /**
     *  Returns previous page
     * */
    public Integer setPage(Integer page) {
        Integer lastPage = this.page;
        this.page = page;
        history.add(page);
        return lastPage;
    }

    /**
     *  Returns previous page
     * */
    public Integer repeatLastPage() {
        history.add(page);
        return page;
    }

    public int getIndex() {
        return index;
    }

    public List<Integer> getHistory() {
        return history;
    }
}
