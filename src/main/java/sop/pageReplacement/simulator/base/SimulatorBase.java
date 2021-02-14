package sop.pageReplacement.simulator.base;

import sop.pageReplacement.common.Frame;
import sop.pageReplacement.common.Statistics;
import sop.pageReplacement.simulator.PageReplacementSimulator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class SimulatorBase<F extends Frame> implements PageReplacementSimulator {


    protected final Queue<F> fifo;
    protected final Set<F> frames;
    protected final int[] pages;
    protected final List<Boolean> hits;
    protected int currentPage;
    protected final Statistics stats;
    protected int currentIndex;

    public SimulatorBase(int numberOfFrames, int[] pages) {
        this.pages = pages;
        this.fifo = new LinkedList<>();
        this.frames = insert(numberOfFrames);
        this.hits = new LinkedList<>();
        this.stats = new Statistics<>(this);
    }

    public SimulatorBase(int numberOfFrames, String pages) {
        this.pages = mapToIntArr(pages);
        this.fifo = new LinkedList<>();
        this.frames = insert(numberOfFrames);
        this.hits = new LinkedList<>();
        this.stats = new Statistics<>(this);
    }

    protected Set<F> insert(int numberOfFrames) {
        Set<F> frames = new LinkedHashSet<>();
        for (int i = 0; i < numberOfFrames; i++) {
            F frame = newFrame(i);
            frames.add(frame);
            fifo.offer(frame);
        }
        return frames;
    }

    protected abstract F newFrame(int i);

    private int[] mapToIntArr(String pages) {
        return Arrays.stream(pages.split(" "))
                .map(Integer::parseInt)
                .mapToInt(x -> x)
                .toArray();
    }

    @Override
    public void execute() {
        processAlgorithm();
        output("t");
    }

    private void output(String output) {
        switch (output) {
            case "t": outputToTerminal();
            return;
            case "f": outputToFile();
            return;
            default: throw new IllegalArgumentException("output(String) accepts \"t\" for \"terminal output\" and \"f\" for \"file otuput\"");
        }
    }

    public void processAlgorithm() {
        boolean hit;
        for (int i = 0; i < pages.length; i++) {
            currentIndex = i;
            currentPage = pages[i];
            if (currentIsPresent()) {
                frames.forEach(frame -> frame.repeat(currentPage));
                hit = true;
            } else if (frames.stream().anyMatch(frame -> frame.getPage() == null)) {
                hit = replaceFirstNullAndRepeatOther();
            } else {
                replaceFrameWithAlgorithm();
                hit = false;
            }
            hits.add(hit);
        }
    }

    private boolean replaceFirstNullAndRepeatOther() {
        Iterator<F> iterator = frames.iterator();
        F candidate;
        int frameIndexToReplace;
        while (iterator.hasNext()) {
            candidate = iterator.next();
            if (candidate.getPage() == null) {
                frameIndexToReplace = candidate.getIndex();
                return replacePageInFrameAndRepeatOther(frameIndexToReplace);
            }
        }
        throw new NullPointerException("Something went terribly wrong - you should not be here!");
    }

    /**
     * Returns boolean for hit (true) or miss (false)
     * */
    protected boolean replacePageInFrameAndRepeatOther(int index) {
        F frameToReplace = frames.stream()
                .filter(frame -> frame.getIndex() == index)
                .collect(Collectors.toList()).get(0);
        Integer prevPage = frameToReplace.replace(currentPage);
        frames.stream()
                .filter(frame -> frame.getIndex() != index)
                .forEach(frame -> frame.repeat(currentPage));
        return Integer.valueOf(currentPage).equals(prevPage);
    }

    private boolean currentIsPresent() {
        return frames.stream().anyMatch(frame -> Integer.valueOf(currentPage).equals(frame.getPage()));
    }

    protected abstract void replaceFrameWithAlgorithm();

    private void outputToTerminal() {

        IntStream.rangeClosed(1, pages.length).forEach(this::printRow);
        separator();
        for (int page : pages) {
            printRow(page);
        }
        separator();
        for (F frame : frames) {
            frame.getHistory().forEach(this::printRow);
            System.out.println();
        }
        System.out.println();
        printStats();
    }

    private void separator() {
        String separator = "--".repeat(pages.length * 4);
        System.out.println("\n" + separator);
    }

    private void printStats() {
        String format = "%s : %s%n";
        System.out.printf(format, stats.getHits().getKey(), stats.getHits().getValue());
        System.out.printf(format, stats.getMisses().getKey(), stats.getMisses().getValue());

        System.out.println("------------------");
        System.out.printf(format, "P", "FREQ:");
        for (Map.Entry<Integer, Integer> fq : getFrequency()) {
            System.out.printf(format, fq.getKey(), fq.getValue());
        }
    }

    private List<Map.Entry<Integer,Integer>> getFrequency() {
        List<Map.Entry<Integer, Integer>> list = new ArrayList<>(stats.getPageFrequency().entrySet());
        list.sort(Comparator.comparingInt(Map.Entry::getValue));
        return list;
    }

    private void printRow(Integer i) {
        String s = i == null ? "" : i.toString();
        printRow(s);
    }

    protected void printRow(String s) {
        printRow(s, 3);
    }

    protected void printRow(String s, int colWidth) {
        s = s == null ? "" : s;
        String format = "%-" + colWidth + "s ";
        String line = String.format(format, s);
        System.out.print(line);
    }

    protected Queue<F> orderFifo() {
        Map<F, Integer> lastPageRepeats = new HashMap<>();
        frames.forEach(frame -> lastPageRepeats.put(frame,frame.getTimeInMemory()));
        return lastPageRepeats.entrySet().stream()
                .sorted((es1, es2) -> es2.getValue() - es1.getValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    protected Queue<F> orderLru() {
        Map<F, Integer> lastPageRepeats = new HashMap<>();
        frames.forEach(frame -> lastPageRepeats.put(frame,frame.getRecentlyUsed()));
        return lastPageRepeats.entrySet().stream()
                .sorted((es1, es2) -> es2.getValue() - es1.getValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toCollection(LinkedList::new));
    }

    protected void outputToFile() {
        // todo
    }

    public Set<F> getFrames() {
        return frames;
    }

    @Override
    public List<Boolean> getHits() {
        return hits;
    }
}
