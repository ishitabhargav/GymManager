package com.example.project3;

/**
 * The Time enum defines the time of a fitness class in hh:mm.
 * @author Sneha Balaji, Ishita Bhargava
 */
public enum Time {

    MORNING (9, 30),
    AFTERNOON (14, 0),
    EVENING (18, 30);

    private final int hour;
    private final int minute;

    /**
     * Creates a Time object defining the hour and minute.
     * @param hour The hour field of a Time.
     * @param minute The minute field of a Time.
     */
    Time(int hour, int minute) {
        this.hour = hour;
        this.minute = minute;
    }

    /**
     * Returns the string representation of a Time (hh:mm).
     * @return String representation of hour and minute.
     */
    @Override
    public String toString() {
        return String.format("%2d", hour) + ":" + String.format("%02d", minute);
    }
}
