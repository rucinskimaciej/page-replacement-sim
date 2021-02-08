package sop.pageReplacement.simulator.base;

import sop.pageReplacement.common.Frame;
import sop.pageReplacement.common.Statistics;
import sop.pageReplacement.simulator.PageReplacementSimulator;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public abstract class SimulatorBase implements PageReplacementSimulator {

    protected final Set<Frame> frames;
    protected final int[] pages;
    protected final List<Boolean> hits;
    protected int currentPage;
    protected final Statistics stats;

    public SimulatorBase(int numberOfFrames, int[] pages) {
        this.pages = pages;
        this.frames = insert(numberOfFrames);
        this.hits = new LinkedList<>();
        this.stats = new Statistics(this);
    }

    public SimulatorBase(int numberOfFrames, String pages) {
        this.frames = insert(numberOfFrames);
        this.pages = mapToIntArr(pages);
        this.hits = new LinkedList<>();
        this.stats = new Statistics(this);
    }

    private Set<Frame> insert(int numberOfFrames) {
        Set<Frame> frames = new LinkedHashSet<>();
        for (int i = 0; i < numberOfFrames; i++) {
            frames.add(new Frame(i));
        }
        return frames;
    }

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
        for (int page : pages) {
            currentPage = page;
            if (currentIsPresent()) {
                frames.forEach(Frame::repeatLastPage);
                hit = true;
            } else if (frames.stream().anyMatch(frame -> frame.getPage() == null)) {
                hit = replaceFirstNullAndRepeatOther();
            } else {
                hit = replaceFrameWithAlgorithm();
            }
            hits.add(hit);
        }
    }

    private boolean replaceFirstNullAndRepeatOther() {
        Iterator<Frame> iterator = frames.iterator();
        Frame candidate;
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
        Frame frameToReplace = frames.stream()
                .filter(frame -> frame.getIndex() == index)
                .collect(Collectors.toList()).get(0);
        Integer prevPage = frameToReplace.setPage(currentPage);
        frames.stream()
                .filter(frame -> frame.getIndex() != index)
                .forEach(Frame::repeatLastPage);
        return Integer.valueOf(currentPage).equals(prevPage);
    }

    private boolean currentIsPresent() {
        return frames.stream().anyMatch(frame -> Integer.valueOf(currentPage).equals(frame.getPage()));
    }

    protected abstract boolean replaceFrameWithAlgorithm();

    private void outputToTerminal() {

        IntStream.rangeClosed(1, pages.length).forEach(this::printRow);
        separator();
        for (int page : pages) {
            printRow(page);
        }
        separator();
        for (Frame frame : frames) {
            frame.getHistory().forEach(this::printRow);
            System.out.println();
        }
        System.out.println();
        printStats();
    }

    private void separator() {
        String separator = "-".repeat(pages.length * 4);
        System.out.println("\n" + separator);
    }

    private void printStats() {
        String format = "%s : %s%n";
        System.out.printf(format, stats.getHits().getKey(), stats.getHits().getValue());
        System.out.printf(format, stats.getMisses().getKey(), stats.getMisses().getValue());

        System.out.println("------------------\nFREQUENCY:");
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
        String format = String.format("%3s ", s);
        System.out.print(format);
    }

    protected void outputToFile() {
        // todo
    }

    @Override
    public Set<Frame> getFrames() {
        return frames;
    }

    @Override
    public List<Boolean> getHits() {
        return hits;
    }
}
