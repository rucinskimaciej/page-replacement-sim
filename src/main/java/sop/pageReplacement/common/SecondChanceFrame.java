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

    @Override
    protected void addToHistory() {
        String output = this.getPage() == null ? null : this.getPage().toString();
        if (output != null) {
            output += String.format(" (%d)[%d]", hasSecondChance() ? 1 : 0, getTimeInMemory());
        }
        history.add(output);
    }

    @Override
    public Integer replace(int page) {
        if (!Integer.valueOf(page).equals(getPage())) resetSecondChance();
        return super.replace(page);
    }

    @Override
    public Integer repeat(int page) {
        if (Integer.valueOf(page).equals(getPage())) resetSecondChance();
        return super.repeat(page);
    }
}
