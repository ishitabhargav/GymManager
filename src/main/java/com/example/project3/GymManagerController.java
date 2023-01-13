package com.example.project3;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.time.LocalDate;


/**
 * The GymManagerController class is the controller file for the Gym Manager GUI.
 * @author Sneha Balaji, Ishita Bhargava
 */
public class GymManagerController {
    public static final int NOT_FOUND = -1;
    public static final int EMPTY = 0;
    public static final int STANDARD_AND_FAMILY_MEMBERSHIP_EXPIRATION_MONTHS = 3;
    public static final int FAMILY_MEMBERSHIP_GUEST_PASS_AVAILABLE = 1;
    public static final int PREMIUM_EXPIRATION_MONTHS = 12;
    public static final int PREMIUM_MEMBERSHIP_GUEST_PASS_AVAILABLE = 3;

    @FXML
    private TextField firstName;
    @FXML
    private TextField lastName;
    @FXML
    private TextArea messageArea;
    @FXML
    private DatePicker dateOfBirth;
    @FXML
    private TextField locations;
    @FXML
    private RadioButton standard;
    @FXML
    private RadioButton family;
    @FXML
    private RadioButton premium;
    @FXML
    private TextField firstName2;
    @FXML
    private TextField lastName2;
    @FXML
    private DatePicker dateOfBirth2;
    @FXML
    private TextField locations2;
    @FXML
    private TextField instructorName;
    @FXML
    private TextField className;
    @FXML
    private RadioButton memberCheck;
    @FXML
    private RadioButton guestCheck;


    MemberDatabase database = new MemberDatabase();
    ClassSchedule classSchedule = new ClassSchedule();
    int monthsUntilExpiration;
    int numGuestPassesAvailable;
    boolean isNotInClass = false;

    Member member;

    /**
     * Checks if when a user adds/removes a Member, if the inputted information for the member is valid (helper method for addMember() and removeMember()).
     * @param selectedDate the selected date from the DatePicker.
     * @param firstName the inputted first name in the text field.
     * @param lastName the inputted last name in the text field.
     * @param locations the inputted location name in the text field.
     * @param standard a selected radio button.
     * @param premium a selected radio button.
     * @param family a selected radio button.
     * @return true if there was an error, false otherwise.
     */
    private boolean checkForErrorInInput (LocalDate selectedDate, TextField firstName, TextField lastName, TextField locations, RadioButton standard, RadioButton family, RadioButton premium) {
        boolean errorInInput = false;
        if ( firstName.getText().isEmpty() || lastName.getText().isEmpty() ){
            messageArea.appendText("Please enter your name. \n");
            errorInInput = true;
        } else if (Location.returnLocation(locations.getText().trim()) == null) {
            messageArea.appendText("Enter a valid location. \n");
            errorInInput = true;
        } else if (!(standard.isSelected() || family.isSelected() || premium.isSelected())){
            messageArea.appendText("Enter a membership. \n");
            errorInInput = true;
        } else if (selectedDate == null) {
            messageArea.appendText("Enter a valid date. \n");
            errorInInput = true;
        } else {
            Date dob = new Date (selectedDate.getMonthValue(), selectedDate.getDayOfMonth(), selectedDate.getYear());
            if (!dob.getIs18()) {
                messageArea.appendText("Enter a valid date! \n");
                errorInInput = true;
            }
        }
        return errorInInput;
    }


    /**
     * Adds a member if the add button was selected.
     */
    @FXML
    void addMember() {
        LocalDate selectedDate = dateOfBirth.getValue();
        if (checkForErrorInInput(selectedDate, firstName, lastName, locations, standard, family, premium) == false) {
            if (standard.isSelected()) {
                monthsUntilExpiration = STANDARD_AND_FAMILY_MEMBERSHIP_EXPIRATION_MONTHS;
                Member member = new Member(firstName.getText().trim(), lastName.getText().trim(), new Date (dateOfBirth.getValue().getMonthValue(),
                        dateOfBirth.getValue().getDayOfMonth(), dateOfBirth.getValue().getYear()), monthsUntilExpiration ,
                        Location.returnLocation(locations.getText().trim()));
                if (database.inDatabase(member)) {
                    messageArea.appendText(firstName.getText().trim() + " " + lastName.getText().trim() + " is already in the database. \n");
                } else {
                    database.add(member);
                    messageArea.appendText(firstName.getText().trim() + " " + lastName.getText().trim() + " added. \n");
                }
            } else if (family.isSelected()) {
                monthsUntilExpiration = STANDARD_AND_FAMILY_MEMBERSHIP_EXPIRATION_MONTHS;
                numGuestPassesAvailable = FAMILY_MEMBERSHIP_GUEST_PASS_AVAILABLE;
                Family family = new Family(firstName.getText().trim(), lastName.getText().trim(), new Date (dateOfBirth.getValue().getMonthValue(),
                        dateOfBirth.getValue().getDayOfMonth(), dateOfBirth.getValue().getYear()), monthsUntilExpiration ,
                        Location.returnLocation(locations.getText().trim()), numGuestPassesAvailable);
                if (database.inDatabase(family)) {
                    messageArea.appendText(firstName.getText().trim() + " " + lastName.getText().trim() + " is already in the database. \n");
                } else {
                    database.add(family);
                    messageArea.appendText(firstName.getText().trim() + " " + lastName.getText().trim() + " added. \n");
                }
            } else if (premium.isSelected()) {
                monthsUntilExpiration = PREMIUM_EXPIRATION_MONTHS;
                numGuestPassesAvailable = PREMIUM_MEMBERSHIP_GUEST_PASS_AVAILABLE;
                Premium premium = new Premium(firstName.getText().trim(), lastName.getText().trim(), new Date (dateOfBirth.getValue().getMonthValue(),
                        dateOfBirth.getValue().getDayOfMonth(), dateOfBirth.getValue().getYear()), monthsUntilExpiration ,
                        Location.returnLocation(locations.getText().trim()), numGuestPassesAvailable);
                if (database.inDatabase(premium)) {
                    messageArea.appendText(firstName.getText().trim() + " " + lastName.getText().trim() + " is already in the database. \n");
                } else {
                    database.add(premium);
                    messageArea.appendText(firstName.getText().trim() + " " + lastName.getText().trim() + " added. \n");
                }
            }
        }
    }

    /**
     * Removes a member if the remove button was selected.
     */
    @FXML
    void removeMember() {
        LocalDate selectedDate = dateOfBirth.getValue();
        if (checkForErrorInInput(selectedDate, firstName, lastName, locations, standard, family, premium) == false) {
            Member member = new Member(firstName.getText().trim(), lastName.getText().trim(), new Date (selectedDate.getMonthValue(), selectedDate.getDayOfMonth(), selectedDate.getYear()));
            member = database.retrieveMember(member);
            if(member == null){
                messageArea.appendText(firstName.getText().trim() + " " + lastName.getText().trim() + " is not in the database. \n");
            } else {
                database.remove(member);
                //removes the member from any classes as well as any of their guests
                for (int i = 0; i < classSchedule.getNumClasses(); i++) {
                    for (int j = 0; j < classSchedule.getClasses()[i].getMembers().size(); j++) {
                        if (classSchedule.getClasses()[i].getMembers().get(j).equals((member))) {
                            classSchedule.getClasses()[i].removeMember((member));
                        }
                    }
                    for (int j = 0; j < classSchedule.getClasses()[i].getGuests().size(); j++) {
                        if(classSchedule.getClasses()[i].getGuests().get(j).equals((member))) {
                            classSchedule.getClasses()[i].removeGuest((member));
                        }
                    }
                }
                messageArea.appendText(firstName.getText().trim() + " " + lastName.getText().trim() + " removed. \n");
            }
        }
    }


    /**
     * Gets the loaded member file from member database from the import Member button.
     */
    @FXML
    void importMembersFile() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Source File for the Import");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File sourceFile = chooser.showOpenDialog(stage); //get the reference of the source file
        if (database.loadMembers(sourceFile) == false) {
            messageArea.appendText("Invalid File! \n");
        } else {
            if (database.getSize() == EMPTY) {
                messageArea.appendText("Member database is empty! \n");
            } else {
                messageArea.appendText("-list of members- \n");
                messageArea.appendText(database.printToString());
                messageArea.appendText("-end of list-\n");
            }
        }
    }

    /**
     * Prints the members in the member database.
     */
    @FXML
    void printMembers() {
        if (database.getSize() == EMPTY) {
            messageArea.appendText("Member database is empty! \n");
        } else {
            messageArea.appendText("-list of members- \n");
            messageArea.appendText(database.printToString());
            messageArea.appendText("-end of list-\n");
        }
    }

    /**
     * Prints the members in the member database by their county.
     */
    @FXML
    void printByCounty() {
        if (database.getSize() == EMPTY) {
            messageArea.appendText("Member database is empty! \n");
        } else {
            messageArea.appendText("-list of members sorted by county and zipcode- \n");
            messageArea.appendText(database.printByCountyToString());
            messageArea.appendText("-end of list-\n");
        }
    }

    /**
     * Prints the members in the member database by their expiration date.
     */
    @FXML
    void printByExpirationDate() {
        if (database.getSize() == EMPTY) {
            messageArea.appendText("Member database is empty! \n");
        } else {
            messageArea.appendText("-list of members sorted by membership expiration date- \n");
            messageArea.appendText(database.printByExpirationDateToString());
            messageArea.appendText("-end of list-\n");
        }
    }

    /**
     * Prints the members in the member database by their name.
     */
    @FXML
    void printByName() {
        if (database.getSize() == EMPTY) {
            messageArea.appendText("Member database is empty! \n");
        } else {
            messageArea.appendText("-list of members sorted by last name, and first name- \n");
            messageArea.appendText(database.printByNameToString());
            messageArea.appendText("-end of list-\n");
        }
    }


    /**
     * Prints the members in the member database by their Membership Fees.
     */
    @FXML
    void printMembershipFees() {
        if (database.getSize() == EMPTY) {
            messageArea.appendText("Member database is empty! \n");
        } else {
            messageArea.appendText("-list of members with membership fees- \n");
            messageArea.appendText(database.printWithMembershipFeesToString());
            messageArea.appendText("-end of list-\n");
        }
    }

    /**
     * Prints the class schedule that was loaded through a file.
     */
    @FXML
    void printLoadedClassSchedule() {
        messageArea.appendText("-Fitness classes loaded- \n");
        messageArea.appendText(classSchedule.printLoadedClassScheduleToString());
        messageArea.appendText("-end of class list- \n");
    }

    /**
     * Prints the full class schedule.
     */
    @FXML
    void printFullClassSchedule() {
        messageArea.appendText("-Fitness classes loaded- \n");
        messageArea.appendText(classSchedule.printFullClassScheduleToString());
        messageArea.appendText("-end of class list- \n");
    }

    /**
     * Checks if when a user adds a checks in or checks out a member/guest, if the inputted information for a member is valid (helper method for checkInToFitnessClass() checkOutOfFitnessClass()).
     * @param selectedDate the selected date from the DatePicker.
     * @param firstName the inputted first name in the text field.
     * @param lastName the inputted last name in the text field.
     * @param locations the inputted location name in the text field.
     * @param instructorName the inputted instructor name in the text field.
     * @param className the inputted class name in the text field.
     * @param memberCheck the radio button for if checking in/out a member is selected.
     * @param guestCheck the radio button for if checking in/out a guest is selected.
     * @param member the created member based on what the user entered.
     * @param fitnessClassWNoTime the created fitness class based on what the user entered.
     * @return true if there was an error, false otherwise.
     */
    private boolean checkForErrorInInput2 (LocalDate selectedDate, TextField firstName, TextField lastName, TextField locations, TextField instructorName, TextField className, RadioButton memberCheck, RadioButton guestCheck, Member member, FitnessClass fitnessClassWNoTime) {
        boolean errorInInput = false;
        if (firstName.getText().isEmpty() || lastName.getText().isEmpty() ){
            messageArea.appendText("Please enter your name. \n");
            errorInInput = true;
        } else if (Location.returnLocation(locations.getText().trim()) == null) {
            messageArea.appendText("Enter a valid location. \n");
            errorInInput = true;
        } else if (instructorName.getText().isEmpty()) {
            messageArea.appendText("Enter an instructor name. \n");
            errorInInput = true;
        } else if (className.getText().isEmpty()) {
            messageArea.appendText("Enter a class name. \n");
            errorInInput = true;
        } else if (!(memberCheck.isSelected() || guestCheck.isSelected())){
            messageArea.appendText("Select member or guest. \n");
            errorInInput = true;
        } else if (selectedDate == null) {
            messageArea.appendText("No date was entered. \n");
            errorInInput = true;
        } else if (!new Date (dateOfBirth2.getValue().getMonthValue(), dateOfBirth2.getValue().getDayOfMonth(), dateOfBirth2.getValue().getYear()).getIs18()) {
            messageArea.appendText("Select a valid date. \n");
            errorInInput = true;
        } else if (member == null) {
            messageArea.appendText(firstName.getText().trim() + " " + lastName.getText().trim() + " " + new Date(dateOfBirth2.getValue().getMonthValue(), dateOfBirth2.getValue().getDayOfMonth(), dateOfBirth2.getValue().getYear()) + " is not in the database. \n");
            errorInInput = true;
        } else if (!(instructorName.getText().trim().equalsIgnoreCase("JENNIFER") || instructorName.getText().trim().equalsIgnoreCase("KIM") || instructorName.getText().trim().equalsIgnoreCase("DAVIS") || instructorName.getText().trim().equalsIgnoreCase("DENISE") || instructorName.getText().trim().equalsIgnoreCase("EMMA"))) {
            messageArea.appendText(instructorName.getText().trim() + " - instructor does not exist. \n");
            errorInInput = true;
        } else if (new Date().compareTo(member.getExpire()) == 1) {
            messageArea.appendText(firstName.getText().trim() + " " + lastName.getText().trim() + " " + new Date(dateOfBirth2.getValue().getMonthValue(), dateOfBirth2.getValue().getDayOfMonth(), dateOfBirth2.getValue().getYear()) + " membership expired. \n");
            errorInInput = true;
        } else if (!(className.getText().trim().equalsIgnoreCase("PILATES") || className.getText().trim().equalsIgnoreCase("SPINNING") || className.getText().trim().equalsIgnoreCase("CARDIO"))) {
            messageArea.appendText(className.getText().trim() + " - class does not exist. \n");
            errorInInput = true;
        } else if (classSchedule.findClass(fitnessClassWNoTime) == NOT_FOUND) {
            messageArea.appendText(className.getText().trim() + " by " + instructorName.getText().trim() + " does not exist at " + locations.getText().trim() + "\n");
            errorInInput = true;
        }
        return errorInInput;
    }

    /**
     * Checks a Member/Guest into a fitness class if the check in button was selected.
     */
    @FXML
    void checkInToFitnessClass() {
        if (dateOfBirth2.getValue() == null) {
            member = (new Member(firstName2.getText().trim(), lastName2.getText().trim(), null));
        } else {
            member = (new Member(firstName2.getText().trim(), lastName2.getText().trim(), new Date (dateOfBirth2.getValue().getMonthValue(), dateOfBirth2.getValue().getDayOfMonth(), dateOfBirth2.getValue().getYear())));
        }
        FitnessClass fitnessClassWNoTime = new FitnessClass(className.getText().trim(), instructorName.getText().trim(), locations2.getText().trim());
        member = database.retrieveMember(member);
        if (!checkForErrorInInput2(dateOfBirth2.getValue(), firstName2, lastName2, locations2, instructorName, className, memberCheck, guestCheck, member, fitnessClassWNoTime)) {
            if (memberCheck.isSelected()) {
                if (!(member instanceof Family) && (member.getLocation() != (Location.returnLocation(locations2.getText().trim().toUpperCase())))) {
                    messageArea.appendText(firstName2.getText().trim() + " " + lastName2.getText().trim() + " checking in " + (Location.returnLocation(locations2.getText().trim().toUpperCase()) + " - standard membership location restriction. \n"));
                } else {
                    FitnessClass fitnessClassWTime = new FitnessClass(className.getText().trim(), instructorName.getText().trim(), classSchedule.findTime(fitnessClassWNoTime), locations2.getText().trim());
                    boolean isAlreadyInClassORtimeConflict = false;
                    checkTimeConflictsOrCheckIn(isAlreadyInClassORtimeConflict, fitnessClassWTime, firstName2.getText().trim(),
                            lastName2.getText().trim(), locations2.getText().trim(), member);
                }
            } else if (guestCheck.isSelected()) {
                if (!(member instanceof Family)) {
                    messageArea.appendText("Standard membership - guest check-in is not allowed. \n");
                } else if ((member instanceof Family) && (member.getLocation() != (Location.returnLocation(locations2.getText().trim().toUpperCase())))) {
                    messageArea.appendText(firstName2.getText().trim() + " " + lastName2.getText().trim() + " Guest checking in " +
                            (Location.returnLocation(locations2.getText().trim().toUpperCase()) + " - guest location restriction. \n"));
                } else if (((Family) member).getNumGuestPassesAvailable() == 0) {
                    messageArea.appendText(firstName2.getText().trim() + " " + lastName2.getText().trim() + " ran out of guest passes. \n");
                } else {
                    FitnessClass fitnessClassWTime = new FitnessClass(className.getText().trim(), instructorName.getText().trim(), classSchedule.findTime(fitnessClassWNoTime), locations2.getText().trim());
                    fitnessClassWTime.addGuest(member);
                    for (int i = 0; i < classSchedule.getNumClasses(); i++) {
                        if (classSchedule.getClasses()[i].equals(fitnessClassWTime)) {
                            classSchedule.getClasses()[i].getGuests().add(member);
                        }
                    }
                    messageArea.appendText(firstName2.getText().trim() + " " + lastName2.getText().trim() + " (guest) checked in " + fitnessClassWTime + "\n");
                    messageArea.appendText(classSchedule.printInstructorsClassToString(fitnessClassWTime));
                }
            }
        }
    }

    /**
     * Checks for time conflicts or if a member is already in the class otherwise check in the member (helper method for checkInToFitnessClass()).
     */
    void checkTimeConflictsOrCheckIn(boolean isAlreadyInClassORtimeConflict, FitnessClass fitnessClassWTime,
                                     String firstName, String lastName, String city, Member member) {
        for (int i = 0; i < classSchedule.getNumClasses(); i++) {
            if (classSchedule.getClasses()[i].memberIsAlreadyInClass(member)) {
                if (classSchedule.getClasses()[i].equals(fitnessClassWTime)) {
                    messageArea.appendText(firstName + " " + lastName + " already checked in. \n");
                    isAlreadyInClassORtimeConflict = true;
                    break;
                } else {
                    if (classSchedule.getClasses()[i].getTime() == fitnessClassWTime.getTime()) {
                        messageArea.appendText("Time conflict - " + fitnessClassWTime + ", "
                                + Location.returnLocation(city).getZipcode() + ", "
                                + Location.returnLocation(city).getCounty() + "\n");
                        isAlreadyInClassORtimeConflict = true;
                        break;
                    }
                }
            }
        }
        if (!isAlreadyInClassORtimeConflict) {
            fitnessClassWTime.addMember(member);
            for (int i = 0; i < classSchedule.getNumClasses(); i++) {
                if (classSchedule.getClasses()[i].equals(fitnessClassWTime)) {
                    classSchedule.getClasses()[i].getMembers().add(member);
                }
            }
            messageArea.appendText(firstName + " " + lastName + " checked in " + fitnessClassWTime + "\n");
            messageArea.appendText(classSchedule.printInstructorsClassToString(fitnessClassWTime) + "\n");

        }
    }

    /**
     * Checks out Member/Guest into a fitness class if the checkout button was selected.
     */
    @FXML
    void checkOutOfFitnessClass() {
        Member member = database.retrieveMember(dateOfBirth2.getValue() == null ? (new Member(firstName2.getText().trim(), lastName2.getText().trim(), null)) : (new Member(firstName2.getText().trim(), lastName2.getText().trim(), new Date (dateOfBirth2.getValue().getMonthValue(), dateOfBirth2.getValue().getDayOfMonth(), dateOfBirth2.getValue().getYear()))));
        FitnessClass fitnessClassWNoTime = new FitnessClass(className.getText().trim(), instructorName.getText().trim(), locations2.getText().trim());
        if (!checkForErrorInInput2(dateOfBirth2.getValue(), firstName2, lastName2, locations2, instructorName, className, memberCheck, guestCheck, member, fitnessClassWNoTime)) {
            if (memberCheck.isSelected()) {
                FitnessClass fitnessClassWTime = new FitnessClass(className.getText().trim(), instructorName.getText().trim(), classSchedule.findTime(fitnessClassWNoTime), locations2.getText().trim());
                for(int i = 0; i < classSchedule.getNumClasses(); i++) {
                    if (classSchedule.getClasses()[i].equals(fitnessClassWTime) && !(classSchedule.getClasses()[i].memberIsAlreadyInClass(member))) {
                        messageArea.appendText(firstName2.getText().trim() + " " + lastName2.getText().trim() + " did not check in. \n");
                        isNotInClass = true;
                        break;
                    } else if (classSchedule.getClasses()[i].equals(fitnessClassWTime)) {
                        isNotInClass = false;
                        break;
                    }
                }
                if (isNotInClass == false) {
                    fitnessClassWTime.removeMember(member);
                    for (int i = 0; i < classSchedule.getNumClasses(); i++) {
                        if (classSchedule.getClasses()[i].equals(fitnessClassWTime)) {
                            classSchedule.getClasses()[i].getMembers().remove(member);
                        }
                    }
                    messageArea.appendText(firstName2.getText().trim() + " " + lastName2.getText().trim() + " done with the class. \n");
                }
            } else if (guestCheck.isSelected()) {
                FitnessClass fitnessClassWTime = new FitnessClass(className.getText().trim(), instructorName.getText().trim(), classSchedule.findTime(fitnessClassWNoTime), locations2.getText().trim());
                if (!(member instanceof Family) || (member instanceof Premium && ((Premium) member).getNumGuestPassesAvailable() == 3) || ((member instanceof Family && !(member instanceof Premium)) && ((Family) member).getNumGuestPassesAvailable() == 1)) {
                    messageArea.appendText("There are no guests to check out. \n");
                } else {
                    fitnessClassWTime.removeGuest(member);
                    for (int i = 0; i < classSchedule.getNumClasses(); i++) {
                        if (classSchedule.getClasses()[i].equals(fitnessClassWTime)) {
                            classSchedule.getClasses()[i].getGuests().remove(member);
                        }
                    }
                    messageArea.appendText(firstName2.getText().trim() + " " + lastName2.getText().trim() + " Guest done with the class. \n");
                }
            }
        }
    }

    /**
     * Gets the loaded class schedule file from Class Schedule from the import Class Schedule button.
     */
    @FXML
    void importClassScheduleFile() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Source File for the Import");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        Stage stage = new Stage();
        File sourceFile = chooser.showOpenDialog(stage); //get the reference of the source file
        if (classSchedule.loadSchedule(sourceFile) == false) {
            messageArea.appendText("Invalid File! \n");
        } else {
            printLoadedClassSchedule();
        }
    }

}