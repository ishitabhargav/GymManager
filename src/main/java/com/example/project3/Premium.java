package com.example.project3;

/**
 * The Premium class extends the Family class.
 * Contains additional manipulations, in addition to the ones provided in the Family class.
 * @author Sneha Balaji, Ishita Bhargava
 */
public class Premium extends Family {

    /**
     * Creates a Premium object.
     * @param fname the given first name.
     * @param lname the given last name.
     * @param dob the given date of birth.
     * @param monthsUntilExpiration the given number of months until expiration.
     * @param location the given Location.
     * @param numGuestPassesAvailable the number of guest passes available for the member with a Premium membership.
     */
    public Premium(String fname, String lname, Date dob, int monthsUntilExpiration, Location location,
                   int numGuestPassesAvailable) {
        super(fname, lname, dob, monthsUntilExpiration, location, numGuestPassesAvailable);
    }

    /**
     * Creates a Premium object with the first name, last name, dob, monthsUntilExpiration and location.
     * @param fname the given first name.
     * @param lname the given last name.
     * @param dob the given date of birth.
     * @param monthsUntilExpiration the given months till expiration for the member.
     * @param location the given Location.
     */
    public Premium(String fname, String lname, Date dob, int monthsUntilExpiration, Location location) {
        super(fname, lname, dob, monthsUntilExpiration, location);
    }

    /**
     * Creates a Premium object with the first name, last name and dob.
     * @param fname the given first name.
     * @param lname the given last name.
     * @param dob the given date of birth.
     */
    public Premium(String fname, String lname, Date dob) {
        super(fname, lname, dob);
    }

    /**
     * Gets the membership fee for the member of a Premium membership.
     * @return (monthlyFee * numMonths) the calculation of a membership fee for a member of a Premium membership.
     */
    @Override
    public double membershipFee() {
        double monthlyFee = 59.99;
        int numMonths = 11;
        return monthlyFee * numMonths;
    }
}


