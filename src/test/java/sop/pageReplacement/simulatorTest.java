package sop.pageReplacement;

import org.junit.jupiter.api.Test;
import sop.pageReplacement.simulator.PageReplacementSimulator;
import sop.pageReplacement.simulator.fifo.FIFO;
import sop.pageReplacement.simulator.lru.LRU;
import sop.pageReplacement.simulator.optimal.Optimal;
import sop.pageReplacement.simulator.secondChance.SecondChance;

import javax.swing.*;

class simulatorTest {

    @Test
    void FIFO() {
        strategyName = "FIFO";
        input();
        sim = new FIFO(frameCount, input);
        sim.execute();
        promptEnd();
    }

    @Test
    void LRU() {
        strategyName = "LRU";
        input();
        sim = new LRU(frameCount, input);
        sim.execute();
        promptEnd();
    }

    @Test
    void OPTYMALNY() {
        strategyName = "OPTYMALNY";
        input();
        sim = new Optimal(frameCount, input);
        sim.execute();
        promptEnd();
    }

    @Test
    void DRUGIEJ_SZANSY() {
        strategyName = "DRUGIEJ SZANSY";
        input();
        sim = new SecondChance(frameCount, input);
        sim.execute();
        promptEnd();
    }










    private void promptEnd() {
        String format = "Strategia: %s\nIlość ramek: %d\nStrony: %s";
        JOptionPane.showMessageDialog(null, String.format(format, strategyName, frameCount, input));
    }

    private void input() {
        frameCount = getFrames();
        input = getInput();
    }

    private String getInput() {
        String fromDialog = JOptionPane.showInputDialog(String.format("STRATEGIA: %s%nILOŚĆ RAMEK: %d%nWpisz ciąg ramek oddzielonych spacjami, przecinkami lub ')('", strategyName, frameCount));
        if (fromDialog == null) System.exit(-1);
        return fromDialog.replace(")(", " ").replace(",", " ").replace(".", " ").trim();
    }

    private int getFrames() {
        String strFrameCount = JOptionPane.showInputDialog(String.format("Podaj ilość ramek dla strategii \"%s\":", strategyName));
        if (strFrameCount == null) System.exit(-1);
        return Integer.parseInt(strFrameCount);
    }

    private PageReplacementSimulator sim;
    private String strategyName;
    private String input;
    private int frameCount;
}