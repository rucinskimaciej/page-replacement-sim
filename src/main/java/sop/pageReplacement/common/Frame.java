package sop.pageReplacement.common;

import sop.pageReplacement.simulator.secondChance.SecondChance;

import java.util.LinkedList;
import java.util.List;

public class Frame {
    private Integer page;
    private final int index;
    private int recentlyUsed;
    private int timeInMemory;

    protected final List<String> history;

    public Frame(int index) {
        this.index = index;
        history = new LinkedList<>();
    }

    public Integer getPage() {
        return page;
    }

    protected void addToHistory() {
        history.add(this.page == null ? null : this.page.toString());
    }

    private Integer replace(Integer page, boolean repeat) {
        Integer lastPage = this.page;
        if (!repeat) this.page = page;
        addToHistory();
        return lastPage;
    }

    /**
     *  Returns previous page
     * */
    public Integer replace(int page) {
        timeInMemory = 1;
        recentlyUsed = 1;
        return replace(page, false);
    }

    /**
     *  Returns previous page
     * */
    public Integer repeat(int page) {
        if (this.page != null) timeInMemory++;
        if (this.page != null) {
            if (this.page.equals(page)) recentlyUsed = 1;
            else recentlyUsed++;
        }
        return replace(page, true);
    }

    public int getRecentlyUsed() {
        return recentlyUsed;
    }

    public int getTimeInMemory() {
        return timeInMemory;
    }

    public int getIndex() {
        return index;
    }

    public List<String> getHistory() {
        return history;
    }
}
