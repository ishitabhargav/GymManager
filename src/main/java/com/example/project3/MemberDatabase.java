package com.example.project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;


/**
 * The MemberDatabase class contains an array of all members in the fitness chain and various manipulations on the array.
 * @author Sneha Balaji, Ishita Bhargava
 */
public class MemberDatabase {

    public static final int NOT_FOUND = -1;
    public static final int INITIAL_SIZE = 4;
    private Member[] mlist;
    private int size;

    /**
     * Creates a MemberDatabase object with the growable Member array and size of the members in the array.
     */
    public MemberDatabase() {
        mlist = new Member[INITIAL_SIZE];
        size = 0;
    }

    /**
     * Gets the size of the member database.
     * @return the size of the member database.
     */
    public int getSize() {
        return size;
    }

    /**
     * Finds a given member in the Member array.
     * @param member given Member to search in the Member array.
     * @return index where given member is found in the Member array, -1 if the given member is not found in the array.
     */
    private int find(Member member) {
        for(int i = 0; i < mlist.length; i++) {
            if (member.equals(mlist[i])) {
                return i;
            }
        }
        return NOT_FOUND;
    }

    /**
     * Retrieves a member from the MemberDatabase given a members first name, last name, and dob.
     * @param member given Member object to retrieve.
     * @return Member from the MemberDatabase, null if the given Member does not exist.
     */
    public Member retrieveMember(Member member) {
        return (find(member) != NOT_FOUND) ? mlist[find(member)] : null;
    }


    /**
     * Checks whether a member is in the Member array.
     * @param member given Member to search in the Member array.
     * @return true if the given member is found in the Member array, false otherwise.
     */
    public boolean inDatabase(Member member) {
        return (find(member) == NOT_FOUND) ? false : true;
    }

    /**
     * Increases the Member array capacity by 4 if full.
     */
    private void grow() {
        Member[] mlist2 = new Member[mlist.length + 4];
        for(int i = 0; i < mlist.length; i++) {
            mlist2[i] = mlist[i];
        }
        mlist = mlist2;
    }

    /**
     * Adds a member to the Member array.
     * @param member A Member to be added into the Member array.
     * @return true if Member was added, false otherwise.
     */
    public boolean add(Member member) {
        boolean added = false;
        for (int i = 0; i < mlist.length; i++) {
            if (mlist[i] == null) {
                break;
            }
            if (member.equals(mlist[i])) {
                return added;
            }
        }
        int j = mlist.length - 4;
        while (j < mlist.length && !added) {
            if(mlist[j] == null){
                mlist[j] = member;
                added = true;
            }
            j++;
        }
        if(!added) {
            grow();
            mlist[mlist.length - 4] = member;
            added = true;
        }
        size++;
        return added;
    }

    /**
     * Removes a Member from the Member array.
     * @param member A Member to be removed from the Member array.
     * @return true if Member was removed, false otherwise.
     */
    public boolean remove(Member member) {
        for(int i = 0; i<mlist.length; i++) {
            if(member.equals(mlist[i])){
                mlist[i] = null;
                for(int j = i; j< mlist.length; j++){
                    if(mlist[j] != null){
                        mlist[j-1] = mlist[j];
                    }
                }
                mlist[mlist.length-1] = null;
                size--;
                return true;
            }
        }
        return false;
    }

    /**
     * Loads a member file to the member database.
     * @return true if the file is valid and the members are added to the member database, false otherwise.
     */
    public boolean loadMembers (File file) {
        try {
            Scanner scannerFile = new Scanner (file);
            String firstToken = scannerFile.next();
            while (scannerFile != null) {
                if (firstToken.isEmpty()) {
                    continue;
                }
                String firstName = firstToken;
                String lastName = scannerFile.next();
                Date dob = new Date(scannerFile.next());
                Date expire = new Date(scannerFile.next());
                Location location = Location.returnLocation(scannerFile.next().toUpperCase());
                Member member = new Member(firstName, lastName, dob, expire, location);
                add(member);
                try {
                    firstToken = scannerFile.next();
                    //catch will occur at the last scannerFile.next()
                } catch (NoSuchElementException e){
                    scannerFile.close();
                    return true;
                }
            }
        } catch (FileNotFoundException e) {
            return false;
        } catch (RuntimeException e) {
            return false;
        }
        return false;
    }

    /**
     * Returns the string representation of the members in the member database.
     * @return String representation of the members in the member database.
     */
    public String printToString() {
        String returningString = "";
        for (int i = 0; i < size; i++) {
            if (mlist[i] instanceof Premium) {
                returningString += (mlist[i] + ", (Premium) Guest-pass remaining: " + ((Premium) mlist[i]).getNumGuestPassesAvailable() + "\n");
            } else if (mlist[i] instanceof Family) {
                returningString += (mlist[i] + ", (Family) Guest-pass remaining: " + ((Family) mlist[i]).getNumGuestPassesAvailable() + "\n");
            } else {
                returningString += (mlist[i].toString() + "\n");
            }
        }
        return returningString;
    }

    /**
     * Returns the string representation of the members sorted by county.
     * @return String representation of the members sorted by county.
     */
    public String printByCountyToString() {
        String returningString = "";
        for (int i = 0; i < size- 1; i++) {
            int minIndex = i;
            for (int j = minIndex + 1; j < size; j++) {
                if (mlist[j].getLocation().compareTo(mlist[minIndex].getLocation()) < 0) {
                    minIndex = j;
                }
            }
            Member temp = mlist[i];
            mlist[i] = mlist[minIndex];
            mlist[minIndex] = temp;
        }
        for (int i = 0; i < size; i++) {
            if (mlist[i] instanceof Premium) {
                returningString += (mlist[i] + ", (Premium) Guest-pass remaining: " + ((Premium) mlist[i]).getNumGuestPassesAvailable() + "\n");
            } else if (mlist[i] instanceof Family) {
                returningString += (mlist[i] + ", (Family) Guest-pass remaining: " + ((Family) mlist[i]).getNumGuestPassesAvailable() + "\n");
            } else {
                returningString += (mlist[i] + "\n");
            }
        }
        return returningString;
    }

    /**
     * Returns the string representation of the members sorted by expiration date.
     * @return String representation of the members sorted by expiration date.
     */
    public String printByExpirationDateToString() {
        String returningString = "";
        for (int i = 0; i < size - 1; i++) {
            int minIndex = i;
            for (int j = minIndex + 1; j < size; j++) {
                if (mlist[j].getExpire().compareTo(mlist[minIndex].getExpire()) < 0) {
                    minIndex = j;
                }
            }
            Member temp = mlist[i];
            mlist[i] = mlist[minIndex];
            mlist[minIndex] = temp;
        }
        for (int i = 0; i < size; i++) {
            if (mlist[i] instanceof Premium) {
                returningString += (mlist[i] + ", (Premium) Guest-pass remaining: " + ((Premium) mlist[i]).getNumGuestPassesAvailable() + "\n");
            } else if (mlist[i] instanceof Family) {
                returningString += (mlist[i] + ", (Family) Guest-pass remaining: " + ((Family) mlist[i]).getNumGuestPassesAvailable() + "\n");
            } else {
                returningString += (mlist[i] + "\n");
            }
        }
        return returningString;
    }

    /**
     * Returns the string representation of the members sorted by name.
     * @return String representation of the members sorted by name.
     */
    public String printByNameToString() {
        String returningString = "";
        for (int i = 0; i < size - 1; i++) {
            int minIndex = i;
            for (int j = minIndex + 1; j < size; j++) {
                if (mlist[j].compareTo(mlist[minIndex]) < 0) {
                    minIndex = j;
                }
            }
            Member temp = mlist[i];
            mlist[i] = mlist[minIndex];
            mlist[minIndex] = temp;
        }
        for (int i = 0; i < size; i++) {
            if (mlist[i] instanceof Premium) {
                returningString += (mlist[i] + ", (Premium) Guest-pass remaining: " + ((Premium) mlist[i]).getNumGuestPassesAvailable() + "\n");
            } else if (mlist[i] instanceof Family) {
                returningString += (mlist[i] + ", (Family) Guest-pass remaining: " + ((Family) mlist[i]).getNumGuestPassesAvailable() + "\n");
            } else {
                returningString += (mlist[i] + "\n");
            }
        }
        return returningString;
    }

    /**
     * Returns the string representation of the members sorted by membership fee.
     * @return String representation of the members sorted by membership fee.
     */
    public String printWithMembershipFeesToString() {
        String returningString = "";
        for (int i = 0; i < size; i++) {
            if (mlist[i] instanceof Premium) {
                returningString += (mlist[i] + ", (Premium) Guest-pass remaining: " + ((Premium) mlist[i]).getNumGuestPassesAvailable() + ", Membership fee: $" + mlist[i].membershipFee() + "\n");
            } else if (mlist[i] instanceof Family) {
                returningString += (mlist[i] + ", (Family) Guest-pass remaining: " + ((Family) mlist[i]).getNumGuestPassesAvailable() + ", Membership fee: $" + mlist[i].membershipFee() + "\n");
            } else {
                returningString += (mlist[i] + ", Membership fee: $" + mlist[i].membershipFee() + "\n");
            }
        }
        return returningString;
    }


    //testbed main method
    public static void main(String[] args) {
        MemberDatabase database1 = new MemberDatabase();

        Member member1 = new Member("Susan", "Last", new Date(), new Date("10/15/2023"), Location.SOMERVILLE);
        Member member2 = new Member("Benjamin", "Lasty", new Date("10/08/2002"), new Date("-1/06/2027"), Location.PISCATAWAY);
        Member member3 = new Member("Sally", "Last", new Date("-1/50/2050"), new Date("13/02/2020"), Location.BRIDGEWATER);
        Member member4 = new Member("Firstname", "Lastname", new Date(), new Date("10/05/2023"), Location.EDISON);
        Member member5 = new Member("Sally", "Last", new Date(), new Date("13/02/2020"), Location.BRIDGEWATER);
        Member member6 = new Member("Benjamin", "Lasty", new Date("10/08/2002"), new Date("-1/07/2027"), Location.PISCATAWAY);

        System.out.println(database1.find(member1));

        System.out.println(database1.add(member1));
        System.out.println(database1.add(member2));
        System.out.println(database1.add(member3));
        System.out.println(database1.add(member5));
        System.out.println(database1.add(member6));
        System.out.println("\n");

        System.out.println(database1.find(member1));
        System.out.println("\n");
        System.out.println(database1.find(member4));
        System.out.println("\n");

        /*
        database1.print();
        System.out.println("\n");

        database1.printByCounty();
        System.out.println("\n");

        database1.printByName();
        System.out.println("\n");
        database1.printByExpirationDate();
        System.out.println("\n");

        database1.remove(member1);
        database1.print();
        System.out.println("\n");
        database1.remove(member3);
        database1.print();
        System.out.println("\n");
        database1.remove(member6);
        database1.print();
        System.out.println("\n");

         */
    }


}
