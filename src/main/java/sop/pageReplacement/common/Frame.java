package sop.pageReplacement.common;

import java.util.LinkedList;
import java.util.List;

public class Frame {
    private Integer page;
    private final int index;
    private int recentlyUsed;

    private final List<Integer> history;

    public Frame(int index) {
        this.index = index;
        history = new LinkedList<>();
    }

    public Integer getPage() {
        return page;
    }

    private Integer replace(Integer page, boolean repeat) {
        Integer lastPage = this.page;
        if (!repeat) this.page = page;
        history.add(this.page);
        checkRecentlyUsed(page);
        return lastPage;
    }

    /**
     *  Returns previous page
     * */
    public Integer replace(int page) {
        return replace(page, false);
    }

    /**
     *  Returns previous page
     * */
    public Integer repeat(int page) {
        return replace(page, true);
    }

    private void checkRecentlyUsed(int page) {
        if (Integer.valueOf(page).equals(this.page)) recentlyUsed = 1;
        else recentlyUsed++;
    }

    public int getRecentlyUsed() {
        return recentlyUsed;
    }

    public int getIndex() {
        return index;
    }

    public List<Integer> getHistory() {
        return history;
    }
}
