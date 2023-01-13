package com.example.project3;

/**
 * The Family class extends the Member class.
 * Contains additional manipulations, in addition to the ones provided in the Member class.
 * @author Sneha Balaji, Ishita Bhargava
 */
public class Family extends Member {

    private int numGuestPassesAvailable;

    /**
     * Creates a Family object.
     * @param fname the given first name.
     * @param lname the given last name.
     * @param dob the given date of birth.
     * @param monthsUntilExpiration the given number of months until expiration.
     * @param location the given Location.
     * @param numGuestPassesAvailable the number of guest passes available for the member with a Family membership.
     */
    public Family(String fname, String lname, Date dob, int monthsUntilExpiration, Location location,
                  int numGuestPassesAvailable) {
        super(fname, lname, dob, monthsUntilExpiration, location);
        this.numGuestPassesAvailable = numGuestPassesAvailable;
    }

    /**
     * Creates a Family object with the first name, last name, dob, monthsUntilExpiration and location.
     * @param fname the given first name.
     * @param lname the given last name.
     * @param dob the given date of birth.
     * @param monthsUntilExpiration the given months till expiration for the member.
     * @param location the given Location.
     */
    public Family(String fname, String lname, Date dob, int monthsUntilExpiration, Location location) {
        super(fname, lname, dob, monthsUntilExpiration, location);
    }

    /**
     * Creates a Family object with the first name, last name and dob.
     * @param fname the given first name.
     * @param lname the given last name.
     * @param dob the given date of birth.
     */
    public Family(String fname, String lname, Date dob) {
        super(fname, lname, dob);
    }

    /**
     * Gets the number of guest passes available for the member of a Family membership.
     * @return numGuestPassesAvailable returns the number of guest passes remaining for the member with a Family membership.
     */
    public int getNumGuestPassesAvailable() {
        return numGuestPassesAvailable;
    }

    /**
     * Gets the membership fee for the member of a Family membership.
     * @return oneTimeFee + (monthlyFee * numMonths) the calculation of a membership fee for a member of a Family membership.
     */
    @Override
    public double membershipFee() {
        double oneTimeFee = 29.99;
        double monthlyFee = 59.99;
        int numMonths = 3;
        return oneTimeFee + (monthlyFee * numMonths);
    }

    /**
     * Reduces the guest passes avaliable for a member of Family membership.
     */
    public void reduceGuestPassesAvailable() {
        numGuestPassesAvailable--;
    }

    /**
     * Increases the guest passes avaliable for a member of Family membership.
     */
    public void increaseGuestPassesAvailable() {
        numGuestPassesAvailable++;
    }
}
