package dataStructure;

public class Time {
    private int seconds;

    public Time(int start) {
        seconds = start;
    }

    public void addTime(Time add) {
        seconds += add.seconds;
    }

    @Override
    public String toString() {
        return seconds < 60 ? String.format("%d seconds", seconds) : String.format("%.1f minutes", (double)seconds/60);
    }

    public int getSeconds() {
        return seconds;
    }
}
