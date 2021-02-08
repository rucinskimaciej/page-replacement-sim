package sop.pageReplacement.common;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Frame {

    private Integer time;
    private final Map<Integer, Integer> history;

    public Frame() {
        this.history = new LinkedHashMap<>();
        time = 0;
        this.addEmpty();
    }

    public void addEmpty() {
        history.put(time++, null);
    }

    public void addPage(Integer page) {
        history.put(time++, page);
    }

    Integer countRepetitionsOfLastElement() {
        Integer last = getLastPage();
        List<Integer> values = (List<Integer>) history.values();
        for (int i = values.size() - 1; i >= 0; i--) {
            if (last != (int) values.get(i)) return values.size() - i;
        }
        throw new NullPointerException("Sth went wrong - should not reach this point");
    }

    public Map<Integer, Integer> getHistory() {
        return Collections.unmodifiableMap(history);
    }

    public Map<Integer, Integer> getLast() {
        return Map.of(time, history.get(time));
    }

    public Integer getLastPage() {
        return history.get(time);
    }

    public Integer getTime() {
        return time;
    }

    public void addLast() {
        Integer last = getLastPage();
        history.put(time++, last);
    }
}
