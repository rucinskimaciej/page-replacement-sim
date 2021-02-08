package sop.pageReplacement.common;

import sop.pageReplacement.simulator.PageReplacementSimulator;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Statistics {

    private final PageReplacementSimulator simulator;
    private final Map<String, Integer> stats;
    private Map<Integer, Integer> frequency;

    public Statistics(PageReplacementSimulator simulator) {
        this.simulator = simulator;
        stats = new HashMap<>();
    }

    public Map.Entry<String, Integer> getHits() {
        if (!stats.containsKey("hits")) {
            int hitCount = (int) simulator.getHits().stream().filter(hits -> hits).count();
            stats.put("hits", hitCount);
        }
        return stats.entrySet().stream()
                .filter(es -> "hits".equalsIgnoreCase(es.getKey()))
                .collect(Collectors.toSet())
                .iterator().next();
    }

    public Map.Entry<String, Integer> getMisses() {
        if (!stats.containsKey("misses")) {
            int missCount = simulator.getHits().size() - getHits().getValue();
            stats.put("misses", missCount);
        }
        return stats.entrySet().stream()
                .filter(es -> "misses".equalsIgnoreCase(es.getKey()))
                .collect(Collectors.toSet())
                .iterator().next();
    }

    public Map<Integer, Integer> getPageFrequency() {
        if (frequency == null) {
            frequency = new HashMap<>();
            for (Frame frame : simulator.getFrames()) {
                frame.getHistory().forEach(page -> {
                    if (page != null)
                        frequency.merge(page, 1, Integer::sum);
                });
            }
        }
        return frequency;
    }

}
