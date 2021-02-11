package sop.pageReplacement.common;

public class SecondChanceFrame extends Frame {

    private boolean secondChance;

    public SecondChanceFrame(int index) {
        super(index);
        secondChance = true;
    }

    public boolean hasSecondChance() {
        return secondChance;
    }

    public void useSecondChance() throws Exception {
        if (secondChance) secondChance = false;
        else throw new Exception("No more chances");
    }

    public void resetSecondChance() {
        secondChance = true;
    }
}
