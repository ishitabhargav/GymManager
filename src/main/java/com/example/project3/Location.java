package com.example.project3;

/**
 * The Location class initializes the 5 gym locations.
 * @author Sneha Balaji, Ishita Bhargava
 */
public enum Location {
    EDISON ("EDISON", "08837", "MIDDLESEX"),
    PISCATAWAY ("PISCATAWAY", "08854", "MIDDLESEX"),
    BRIDGEWATER ("BRIDGEWATER", "08807", "SOMERSET"),
    FRANKLIN ("FRANKLIN", "08873", "SOMERSET"),
    SOMERVILLE ("SOMERVILLE", "08876", "SOMERSET");

    private final String city;
    private final String zipcode;
    private final String county;

    /**
     * Creates a Location object with the given city, zipcode and county.
     * @param city the city of the gym location.
     * @param zipcode the zipcode of the gym location.
     * @param county the county of the gym location.
     */
    Location (String city, String zipcode, String county) {
        this.city = city;
        this.zipcode = zipcode;
        this.county = county;
    }

    /**
     * Returns a String representation of the city, zipcode and county of a location.
     * @return String representation of city, zipcode, and county.
     */
    @Override
    public String toString() {
        return city + ", " + zipcode + ", " + county;
    }

    /**
     * Returns the private instance variable city's value.
     * @return city instance variable value.
     */
    public String getCity() {
        return city;
    }

    /**
     * Returns the private instance variable zipcode value.
     * @return zipcode instance variable value.
     */
    public String getZipcode(){
        return zipcode;
    }

    /**
     * Returns the private instance variable county value.
     * @return county instance variable value.
     */
    public String getCounty(){
        return county;
    }



    /**
     * Returns the location that contains the given city.
     * @param city the given city.
     * @return Location that contains the given city, null if city is not a valid city.
     */
    public static Location returnLocation(String city) {
        if (city.toUpperCase().equals("EDISON")) {
            return EDISON;
        } else if (city.toUpperCase().equals("PISCATAWAY")) {
            return PISCATAWAY;
        } else if (city.toUpperCase().equals("BRIDGEWATER")) {
            return BRIDGEWATER;
        } else if (city.toUpperCase().equals("FRANKLIN")) {
            return FRANKLIN;
        } else if (city.toUpperCase().equals("SOMERVILLE")) {
            return SOMERVILLE;
        }
        return null;
    }

}
