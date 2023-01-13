package com.example.project3;
import java.util.Calendar;

/**
 * The Date class creates a current or given date and contains many kinds of manipulations on a current or given date.
 * @author Sneha Balaji, Ishita Bhargava
 */
public class Date implements Comparable<Date> {

    public static final int QUADRENNIAL = 4;
    public static final int CENTENNIAL = 100;
    public static final int QUARTERCENTENNIAL = 400;
    public static final int FIRSTDAYOFMONTH = 1;
    public static final int LASTDAYOFMONTH31 = 31;
    public static final int LASTDAYOFMONTH30 = 30;
    public static final int DAY29OFMONTH = 29;
    public static final int DAY28OFMONTH = 28;
    public static final int JANUARY = 1;
    public static final int FEBRUARY = 2;
    public static final int MARCH = 3;
    public static final int APRIL = 4;
    public static final int MAY = 5;
    public static final int JUNE = 6;
    public static final int JULY = 7;
    public static final int AUGUST = 8;
    public static final int SEPTEMBER = 9;
    public static final int OCTOBER = 10;
    public static final int NOVEMBER = 11;
    public static final int DECEMBER = 12;
    public static final int USUALMONTHSTILEXPIRE = 3;
    public static final int UNUSUALMONTHSTILEXPIRE = 4;
    public static final int ANNUAL = 12;
    public static final int THREEMONTHSNEXTYEAR = 9;
    public static final int FOURMONTHSNEXTYEAR = 8;
    public static final int ADDAYEAR = 1;

    private int year;
    private int month;
    private int day;

    /**
     * Creates an object with today's date.
     */
    public Date() {
        Calendar todaysDate = Calendar.getInstance();
        //todaysMonth + 1 because Calendar.MONTH starts at 0 for January instead of 1
        month = todaysDate.get(Calendar.MONTH) + 1;
        day = todaysDate.get(Calendar.DAY_OF_MONTH);
        year = todaysDate.get(Calendar.YEAR);
    }

    /**
     * Takes a given date ("mm/dd/yyyy") and creates a Date object.
     * @param date the given date.
     */
    public Date(String date) {
        //searches for the first, second, and third slashes of a given date and
        // splits each value within the slashes to month, day and year.
        int firstSlash = date.indexOf("/");
        month = Integer.parseInt(date.substring(0, firstSlash));
        date = date.substring(firstSlash + 1);
        int secondSlash = date.indexOf("/");
        day = Integer.parseInt(date.substring(0, secondSlash));
        year = Integer.parseInt(date.substring(secondSlash + 1));
    }

    /**
     * Creates an object with a given month, day and year value.
     * @param month the given month.
     * @param day the given day.
     * @param year the given year.
     */
    public Date (int month, int day, int year){
        this.month = month;
        this.day = day;
        this.year = year;
    }

    /**
     * Gets the Expiration Date starting from today's date plus the number of months until expiration.
     * @param monthsUntilExpiration the number of months from today's date that the expiration date should expire.
     * @return Expiration Date.
     */
    public Date getExpirationDate(int monthsUntilExpiration) {
        Date todaysDate = new Date();
        if (monthsUntilExpiration == ANNUAL && (todaysDate.month !=FEBRUARY || todaysDate.day != DAY29OFMONTH)) {
            return new Date(todaysDate.month + "/" + todaysDate.day + "/" + (todaysDate.year + ADDAYEAR));
        } else if (monthsUntilExpiration == ANNUAL) {
            return new Date("3/1/" + (todaysDate.year + ADDAYEAR));
        } else if (todaysDate.month <= SEPTEMBER) {
            if (todaysDate.day <= LASTDAYOFMONTH30) {
                return new Date((todaysDate.month + USUALMONTHSTILEXPIRE) + "/" + todaysDate.day + "/" +
                        todaysDate.year);
            } else {
                Date potentialExpirationDate = new Date((todaysDate.month + USUALMONTHSTILEXPIRE) + "/" +
                        todaysDate.day + "/" + todaysDate.year);
                return (potentialExpirationDate.isValid()) ? potentialExpirationDate :
                        new Date((todaysDate.month + UNUSUALMONTHSTILEXPIRE) + "/1/" + todaysDate.year);
            }
        } else {
            if (todaysDate.day <= DAY28OFMONTH) {
                return new Date((todaysDate.month - THREEMONTHSNEXTYEAR) + "/" + todaysDate.day + "/" +
                        (todaysDate.year + ADDAYEAR));
            } else {
                Date potentialExpirationDate = new Date((todaysDate.month - THREEMONTHSNEXTYEAR) + "/" +
                        todaysDate.day + "/" + (todaysDate.year + ADDAYEAR));
                return (!potentialExpirationDate.isValid()) ? potentialExpirationDate :
                        new Date((todaysDate.month - FOURMONTHSNEXTYEAR) + "/1/" + (todaysDate.year + ADDAYEAR));
            }
        }
    }

    /**
     * Compares two dates and returns their precedence.
     * @param date The Date to be compared with.
     * @return 0 if dates are equal.
     * @return 1 if date that is doing the comparing is larger than the date that it is being
     * compared with.
     * @return -1 if date that is doing the comparing is smaller than the date that it is being
     * compared with.
     */
    @Override
    public int compareTo(Date date) {
        if (this.year == date.year) {
            if (this.month == date.month) {
                if (this.day == date.day) {
                    return 0;
                } else if (this.day > date.day) {
                    return 1;
                }
                return -1;
            } else if (this.month > date.month) {
                return 1;
            }
            return -1;
        } else if (this.year > date.year) {
            return 1;
        }
        return -1;
    }

    /**
     * Checks if a date is a valid calendar date.
     * Validates based on the specific month and number of days in each month.
     * @return true if the date is a valid calendar date, false otherwise.
     */
    public boolean isValid() {
        switch (month) {
            case JANUARY: case MARCH: case MAY: case JULY: case AUGUST: case OCTOBER: case DECEMBER:
                if (day >= FIRSTDAYOFMONTH && day <= LASTDAYOFMONTH31) {
                    return true;
                }
                return false;
            case APRIL: case JUNE: case SEPTEMBER: case NOVEMBER:
                if (day >= FIRSTDAYOFMONTH && day <= LASTDAYOFMONTH30) {
                    return true;
                }
                return false;
            case FEBRUARY:
                return ((year % QUARTERCENTENNIAL == 0) || (year % QUADRENNIAL == 0 && year
                        % CENTENNIAL != 0)) ?
                        (day >= FIRSTDAYOFMONTH && day <= DAY29OFMONTH) : (day >= FIRSTDAYOFMONTH && day
                        <= DAY28OFMONTH);
            default:
                return false;
        }
    }

    /**
     * Returns the String representation of a date (mm/dd/yyyy).
     * @return the String representation of a date.
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    /**
     * Checks if a given date compared to the current date was at least 18 years ago.
     * @return true if the given date was at least 18 years ago, false otherwise.
     */
    public boolean getIs18() {
        Date currentDate = new Date();
        if (currentDate.year - this.year > 18) {
            return true;
        } else if (currentDate.year - this.year == 18) {
            if (currentDate.month > this.month) {
                return true;
            } else if (currentDate.month == this.month) {
                if (currentDate.day >= this.day) {
                    return true;
                }
            }
        }
        return false;
    }

    //testbed main method
    public static void main(String[] args) {
        //Checking the date constructor
        Date date1 = new Date();
        System.out.println("This is today's date " + date1.month + "/" + date1.day + "/" + date1.year);
        //Checking the date parameterized constructor
        Date date2 = new Date("8/5/2003");
        System.out.println("This is the date " + date2.month + "/" + date2.day + "/" + date2.year);
        Date date3 = new Date("14/5/2003");
        System.out.println("This is the date " + date3.month + "/" + date3.day + "/" + date3.year);

        //Checking compareTo
        System.out.println(date1.compareTo(date2));
        System.out.println(date2.compareTo(date3));
        System.out.println(date2.compareTo(date2));

        //Checking isValid: Test Cases are used in DateTest.java (junit testing)
        //1.
        Date date = new Date("16/5/2003");
        date.isValid();
        System.out.println("**Test case #1: a date with a month > 12 is a invalid date");

        //2.
        date = new Date();
        date.isValid();
        System.out.println("**Test case #2: a date with todays date is a valid date");

        //3.
        date = new Date("2/29/2018");
        date.isValid();
        System.out.println("**Test case #3: Number of days in February for a non- leap year should be 28");

        //4.
        date = new Date("-20/15/2000");
        date.isValid();
        System.out.println("**Test case #4: The method should not accept a date with a negative month value (month < 1)");

        //5.
        date = new Date("5/70/1993");
        date.isValid();
        System.out.println("**Test case #5: The method should not accept a date with a invalid day value (day > 30 or day > 31)");

        //6.
        date = new Date("4/31/1993");
        date.isValid();
        System.out.println("**Test case #6: This method should not accept a date whose day value does not correspond with the month (month = april, day = 31)");

        //7.
        date = new Date("4/30/1993");
        date.isValid();
        System.out.println("**Test case #7: This method should accept a date whose day value corresponds with the month (month = april, day = 30)");

        //8.
        date = new Date("22/89/-1234");
        date.isValid();
        System.out.println("**Test case #8: The method should not accept a date where each field is invalid (month > 12, day> 30/31, year < 0)");

    }

}

