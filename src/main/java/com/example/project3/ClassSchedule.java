package com.example.project3;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * The ClassSchedule class contains the entire fitness class schedule roster.
 * @author Sneha Balaji, Ishita Bhargava
 */
public class ClassSchedule {
    //based on the classSchedule.txt, there are 15 total classes to be added
    public static final int NOT_FOUND = -1;
    public static final int CLASS_SIZE = 15;

    public static final int INCREASE_ARRAY_SIZE = 5;
    private FitnessClass [] classes;
    private int numClasses;


    /**
     * Creates a ClassSchedule object with the FitnessClass classes array and number of classes provided in the fitness class.
     */
    public ClassSchedule() {
        classes = new FitnessClass[CLASS_SIZE];
        numClasses = 0;
    }

    /**
     * Increases the classes array capacity by 5 if full.
     */
    private void grow() {
        FitnessClass[] classes2 = new FitnessClass[classes.length + INCREASE_ARRAY_SIZE];
        for (int i = 0; i < classes.length; i++) {
            classes2[i] = classes[i];
        }
        classes = classes2;
    }

    /**
     * Gets the number of classes in the classes array from the instance variable numClasses.
     * @return the numClasses instance variable.
     */
    public int getNumClasses() {
        return numClasses;
    }

    /**
     * Gets the classes array from the FitnessClass array instance variable classes.
     * @return the classes FitnessClass array instance variable.
     */
    public FitnessClass[] getClasses() {
        return classes;
    }

    /**
     * Adds a fitnessClass to the FitnessClass array.
     * @param fitnessClass A FitnessClass to be added into the FitnessClass array.
     */
    public void add(FitnessClass fitnessClass) {
        boolean added = false;
        int i = classes.length - CLASS_SIZE;
        while (i < classes.length && !added) {
            if (classes[i] == null){
                classes[i] = fitnessClass;
                added = true;
            }
            i++;
        }
        if(added == false) {
            grow();
            classes[classes.length - INCREASE_ARRAY_SIZE] = fitnessClass;
        }
        numClasses++;
    }


    /**
     * Finds a fitnessClass from the FitnessClass array.
     * @param fitnessClass A FitnessClass to be found from the FitnessClass array.
     * @return true if fitnessClass was found, false otherwise.
     */
    public int findClass(FitnessClass fitnessClass) {
        for (int i = 0; i < numClasses; i++){
            if(classes[i].getInstructorName().equals(fitnessClass.getInstructorName().toUpperCase())
                    && classes[i].getCity().equals(fitnessClass.getCity().toUpperCase())){
                if(classes[i].getClassName().equals(fitnessClass.getClassName().toUpperCase())){
                    return i;
                }
            }
        }
        return NOT_FOUND;
    }

    /**
     * Finds the time of a class from a given fitnessClass object with no time parameter.
     * @param fitnessClass A FitnessClass to be find the time of the class.
     * @return Time of the class.
     */
    public Time findTime(FitnessClass fitnessClass) {
        Time time = null;
        for (int i = 0; i < numClasses; i++) {
            if((classes[i].getClassName().equals(fitnessClass.getClassName().toUpperCase()))
                    && (classes[i].getInstructorName().equals(fitnessClass.getInstructorName().toUpperCase()))
                    && (classes[i].getCity().equals(fitnessClass.getCity().toUpperCase()))){
                time = classes[i].getTime();
            }
        }
        return time;
    }

    /**
     * Returns the string representation of the loaded schedule from reading the classSchedule.txt file.
     * @return String representation of the loaded schedule.
     */
    public String printLoadedClassScheduleToString() {
        String returningString = "";
        for (int i = 0; i < numClasses; i++) {
            returningString += classes[i] + "\n";
        }
        return returningString;
    }

    /**
     * Returns the string representation of the full class schedule, includes, if it exists, the members and guests for each class.
     * @return String representation of the full Class Schedule.
     */
    public String printFullClassScheduleToString() {
        String returningString = "";
        for (int i = 0; i < numClasses; i++) {
            returningString += classes[i] + "\n";
            returningString += classes[i].printToString();
        }
        return returningString;
    }


    /**
     * Returns the string representation of a instructors class schedule, given the FitnessClass.
     * @param fitnessClass the given fitnessClass.
     * @return String representation of a specific instructors class schedule.
     */
    public String printInstructorsClassToString(FitnessClass fitnessClass) {
        String returningString = "";
        for (int i = 0; i < numClasses; i++){

            if(classes[i].equals(fitnessClass)){
                returningString += classes[i].printToString();
            }
        }
        return returningString;
    }

    /**
     * Loads a class schedule file to the Class Schedule.
     * @return true if the file is valid and the fitness class is added to the Class Schedule, false otherwise.
     */
    public boolean loadSchedule (File file) {
        try {
            Scanner scannerFile = new Scanner (file);
            String firstToken = scannerFile.next();
            Time time;
            while (scannerFile != null) {
                if (firstToken.isEmpty()) {
                    continue;
                }
                String className = firstToken.toUpperCase();
                String instructorName = scannerFile.next().toUpperCase();
                String timeOfDay = scannerFile.next();
                if (timeOfDay.toUpperCase().equals("AFTERNOON")) {
                    time = Time.AFTERNOON;
                } else if (timeOfDay.toUpperCase().equals("EVENING")) {
                    time = Time.EVENING;
                } else {
                    time = Time.MORNING;
                }
                Location location = Location.returnLocation(scannerFile.next().toUpperCase());
                FitnessClass fitnessClass = new FitnessClass(className, instructorName, time, location.getCity());
                add(fitnessClass);
                try {
                    firstToken = scannerFile.next();
                } catch (NoSuchElementException e) {
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
}