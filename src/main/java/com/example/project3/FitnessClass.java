package com.example.project3;
import java.util.ArrayList;

/**
 * The FitnessClass class creates a FitnessClass object for a single fitnessClass.
 * Contains many kinds of manipulations on a single fitness class.
 * @author Sneha Balaji, Ishita Bhargava
 */
public class FitnessClass {

    private String className;
    private String instructor;
    private Time time;
    private String city;
    private ArrayList<Member> members;
    private ArrayList<Member> guests;


    /**
     * Creates a FitnessClass object with its values initialized with given values for class name, instructor, time, and city(location).
     * @param className the class name.
     * @param instructor the instructor name.
     * @param time the time of the class.
     * @param city the city of the class
     */
    public FitnessClass(String className, String instructor, Time time, String city) {
        this.className = className;
        this.instructor = instructor;
        this.time = time;
        this.city = city;
        members = new ArrayList<Member>();
        guests = new ArrayList<Member>();
    }

    /**
     * Creates a FitnessClass object with its values initialized with given values for class name, instructor, and city(location).
     * @param className the class name.
     * @param instructor the instructor name.
     * @param city the city of the class
     */
    public FitnessClass(String className, String instructor, String city) {
        this.className = className;
        this.instructor = instructor;
        this.city = city;
    }

    /**
     * Gets the class name from the instance variable className.
     * @return the className instance variable.
     */
    public String getClassName() {
        return className;
    }

    /**
     * Gets the instructor name from the instance variable instructor.
     * @return the instructor instance variable.
     */
    public String getInstructorName() {
        return instructor;
    }

    /**
     * Gets the city name from the instance variable city.
     * @return the city instance variable.
     */
    public String getCity() {
        return city;
    }

    /**
     * Gets the members arrayList from a fitness class object from the instance variable members.
     * @return the members arrayList instance variable.
     */
    public ArrayList<Member> getMembers() {
        return members;
    }

    /**
     * Gets the guests arrayList from a fitness class object from the instance variable guests.
     * @return the guests arrayList instance variable.
     */
    public ArrayList<Member> getGuests() {
        return guests;
    }

    /**
     * Gets the time from the instance variable time.
     * @return the time instance variable.
     */
    public Time getTime() {
        return time;
    }

    /**
     * Checks to see if a given member is already in the members arrayList for a class.
     * @param member given member to search for in the members arrayList.
     * @return true if Member was found, false otherwise.
     */
    public boolean memberIsAlreadyInClass(Member member) {
        if(members.isEmpty()){
            return false;
        }
        for (int i = 0; i < members.size(); i++) {
            if (member.equals(members.get(i))) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks to see if two FitnessClasses are equal.
     * @param obj given FitnessClass to check if its equal to the FitnessClass that is calling this method.
     * @return true if FitnessClasses are equal, false otherwise.
     */

    public boolean equals(Object obj) {
        if (obj instanceof FitnessClass) {
            FitnessClass fitnessClass = (FitnessClass) obj;
            if ((this.className.toUpperCase().equals(fitnessClass.className.toUpperCase())) &&
                    (this.instructor.toUpperCase().equals(fitnessClass.instructor.toUpperCase())) &&
                    (this.time == fitnessClass.time) && this.city.toUpperCase().equals(fitnessClass.city.toUpperCase())){
                return true;
            }
        }
        return false;
    }

    /**
     * Adds a member to the members arrayList for a fitness class.
     * @param member given member to add into the members arrayList.
     */
    public boolean addMember(Member member) {
        members.add(member);
        members.trimToSize();
        return true;
    }

    /**
     * Adds a guest to the guests arrayList for a fitness class.
     * @param member given member to add into the guests arrayList.
     */
    public boolean addGuest(Member member) {
        Family family = (Family) member;
        guests.add(family);
        guests.trimToSize();
        family.reduceGuestPassesAvailable();
        return true;
    }

    /**
     * Removes a member from the members arrayList from a fitness class.
     * @param member given member to remove from the members arrayList.
     */
    public boolean removeMember(Member member) {
        for (int i = 0; i < members.size(); i++) {
            if (member.equals(members.get(i))) {
                members.remove(member);
                members.trimToSize();
                return true;
            }
        }
        return false;
    }

    /**
     * Removes a member from the guests arrayList from a fitness class.
     * @param member given member to remove from the guests arrayList.
     */
    public boolean removeGuest(Member member) {
        Family family = (Family) member;
        guests.remove(family);
        guests.trimToSize();
        family.increaseGuestPassesAvailable();
        return true;
    }

    /**
     * Returns the string representation of the participants and guests for a fitness class.
     * @return String representation of the participants and guests for a fitness class.
     */
    public String printToString() {
        String returningString = "";
        if (!(this.members.isEmpty())) {
            returningString += ("- Participants -\n");
            for(int i = 0; i < this.members.size(); i++){
                String memberInstance = "Standard";
                int guestPassesAvailable = 0;
                if (this.members.get(i) instanceof Premium) {
                    memberInstance = "Premium";
                    guestPassesAvailable = ((Premium) this.members.get(i)).getNumGuestPassesAvailable();
                } else if (this.members.get(i) instanceof Family) {
                    memberInstance = "Family";
                    guestPassesAvailable = ((Family) this.members.get(i)).getNumGuestPassesAvailable();
                }
                if (memberInstance.equals("Standard")){
                    returningString += ("\t" + this.members.get(i) + "\n");
                } else {
                    returningString += ("\t" + this.members.get(i) + ", (" + memberInstance + ") Guest Passes remaining: " + guestPassesAvailable + "\n");
                }
            }
        }
        if (!(this.guests.isEmpty())){
            returningString += ("- Guests -\n");
            for(int j = 0; j < this.guests.size(); j++){
                String memberInstance = "Standard";
                int guestPassesAvailable = 0;
                if (this.guests.get(j) instanceof Premium) {
                    memberInstance = "Premium";
                    guestPassesAvailable = ((Premium) this.guests.get(j)).getNumGuestPassesAvailable();
                } else if (this.guests.get(j) instanceof Family) {
                    memberInstance = "Family";
                    guestPassesAvailable = ((Family) this.guests.get(j)).getNumGuestPassesAvailable();
                }
                if (memberInstance.equals("Premium") || memberInstance.equals("Family")) {
                    returningString += ("\t" + this.guests.get(j) + ", (" + memberInstance + ") Guest Passes remaining: " + guestPassesAvailable + "\n");
                }
            }
        }
        return returningString;
    }



    /**
     * Returns the string representation of a class, instructor name and the time of the class.
     * @return string representation of class name, instructor, and time.
     */
    @Override
    public String toString() {
        return className.toUpperCase() + " - " + instructor.toUpperCase() + ", " + time + ", " + city.toUpperCase();
    }

}

