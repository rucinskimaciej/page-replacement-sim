package sop.pageReplacement.common;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public class Frame {

    private int time;
    private final Map<Integer, Integer> history;

    public Frame() {
        this.history = new LinkedHashMap<>();
        time = 0;
    }

    public void addEmpty() {
        history.put(++time, null);
    }

    public void addPage(int page) {
        history.put(++time, page);
    }

    public Map<Integer, Integer> getHistory() {
        return Collections.unmodifiableMap(history);
    }

    public Map<Integer, Integer> getLast() {
        return Map.of(time, history.get(time));
    }

    public int getLastPage() {
        return history.get(time);
    }

    public int getTime() {
        return time;
    }
}
