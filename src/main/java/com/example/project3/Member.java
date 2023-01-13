package com.example.project3;

/**
 * The Member class creates a Member using member(user) data and does various manipulations with
 * members.
 * @author Sneha Balaji, Ishita Bhargava
 */
public class Member implements Comparable<Member> {

    private String fname;
    private String lname;
    private Date dob;
    private Date expire;
    private Location location;


    /**
     * Creates a Member object with the required info of a member.
     * @param fname the first name of the member.
     * @param lname the last name of the member.
     * @param dob the date of birth of the member.
     * @param monthsUntilExpiration the number of months till the expiration date.
     * @param location the gym location of the member.
     */
    public Member(String fname, String lname, Date dob, int monthsUntilExpiration, Location location) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        expire = dob.getExpirationDate(monthsUntilExpiration);
        this.location = location;
    }

    /**
     * Creates a Member object with the first name, last name and dob, expiration date and location of a member.
     * @param fname the first name of the member.
     * @param lname the last name of the member.
     * @param dob the date of birth of the member.
     * @param expire the expiration date of the member.
     * @param location the gym location of the member.
     */
    public Member(String fname, String lname, Date dob, Date expire, Location location) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
        this.expire = expire;
        this.location = location;
    }

    /**
     * Creates a Member object with the first name, last name and dob of a member.
     * @param fname the first name of the member.
     * @param lname the last name of the member.
     * @param dob the date of birth of the member.
     */
    public Member(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Returns a String representation of required info of a member.
     * @return String representation of first name, last name, dob, membership expiry, and gym
     * location.
     */
    @Override
    public String toString() {
        if (this.expire.compareTo(new Date()) >= 0) {
            return fname + " " + lname + ", " + "DOB: " + dob + ", Membership expires " + expire +
                    ", Location: " + location;
        }
        return fname + " " + lname + ", " + "DOB: " + dob + ", Membership expired " + expire +
                ", Location: " + location;
    }

    /**
     * Returns the private instance variable dob
     * @return dob instance variable value
     */
    public Date getDob() {
        return dob;
    }

    /**
     * Returns the private instance variable expire
     * @return expire instance variable value.
     */
    public Date getExpire() {
        return expire;
    }

    /**
     * Returns the private instance variable location's value.
     * @return location instance variable value.
     */
    public Location getLocation() {
        return location;
    }


    /**
     * Checks if a member is equal to another member.
     * @param obj A Member object that will be checked for equality with another Member object.
     * @return true if two members are the same, false otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Member) {
            Member member = (Member)obj;
            if ((this.fname.toUpperCase().equals(member.fname.toUpperCase())) &&
                    (this.lname.toUpperCase().equals(member.lname.toUpperCase())) &&
                    (this.dob.compareTo(member.dob) == 0)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Compares two members and returns their precedence based on their names.
     * If last names are the same, returns the precedence of the first names through helperCompareTo.
     * If firstnames are the same, returns precedence of the last names through helperCompareTo.
     * @param member A Member object that will be compared with another Member.
     * @return helperCompareTo(this.lname, member.lname) if two members first name are not the same.
     * @return helperCompareTo(this.fname, member.fname) if two members last name are not the same.
     * @return 0 if the members are the same person.
     */
    @Override
    public int compareTo(Member member) {
        // if last names are the exact same, then we skip this if statement to compare first names
        // instead
        if (!this.lname.equals(member.lname)) {
            return helperCompareTo(this.lname, member.lname);
        }
        if (!this.fname.equals(member.fname)) {
            return helperCompareTo(this.fname, member.fname);
        }
        return 0;
    }

    /**
     * Computes the membership fee for the member.
     * @return oneTimeFee + (monthlyFee * numMonths) the computation for the membership fee for a standard membership.
     */
    public double membershipFee() {
        double oneTimeFee = 29.99;
        double monthlyFee = 39.99;
        int numMonths = 3;
        return oneTimeFee + (monthlyFee * numMonths);
    }

    /**
     * Compares two members first names or last names and returns their precedence.
     * @param thisName name of the first Member that is doing the comparing.
     * @param memberName name of the second Member that is being compared.
     * @return 1 if member name that is doing the comparing is larger than the member name that it
     * is being compared with.
     * @return -1 if member name that is doing the comparing is smaller than the member name that it
     * is being compared with.
     */
    private int helperCompareTo(String thisName, String memberName) {
        String shorterName = thisName;
        String longerName = memberName;
        if (thisName.length() > memberName.length()) {
            shorterName = memberName;
            longerName = thisName;
        }
        for (int i = 0; i < shorterName.length(); i++) {
            if (thisName.charAt(i) > memberName.charAt(i)) {
                return 1;
            } if (thisName.charAt(i) < memberName.charAt(i)) {
                return -1;
            }
        }
        if ((shorterName.length() != longerName.length()) && (thisName == shorterName)) {
            return -1;
        }
        return 1;
    }

    //testbed main method
    public static void main(String[] args) {
        Member member1 = new Member("First", "Last", new Date(), 3,
                Location.EDISON);
        Member member2 = new Member("Firsty", "Lasty", new Date(), 3,
                Location.BRIDGEWATER);
        Member member3 = new Member("Firsty", "Last", new Date(), 3,
                Location.PISCATAWAY);
        Member member4 = new Member("Firstname", "Lasty", new Date(), 3,
                Location.EDISON);

        //toString
        System.out.println(member1.toString());

        //equals
        System.out.println(member1.equals(member2));
        System.out.println(member2.equals(member2));

        //compareTo
        //1.
        System.out.println(member1.compareTo(member2));
        //2.
        System.out.println(member2.compareTo(member1));
        //3.
        System.out.println(member2.compareTo(member2));
        //4.
        System.out.println(member3.compareTo(member1));
        //5.
        System.out.println(member4.compareTo(member2));
    }
}


