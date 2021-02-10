package kupczyk.dawid.cs50.doggoexplorer;

import java.io.Serializable;

/**
 * A class representing the Dog object. Every time a new dog JSON object is retrieved from the Dog
 * API, the new instance of Dog object is being created. In the end, all of the dog objects are
 * being stored inside an ArrayList.
 */
public class Dog implements Serializable{

    //Required class fields, every Dog needs to have those set correctly
    private final int     id;
    private final String  name;
    private final String  imageUrl;

    //Optional fields, used only for printing additional data in the LearnMoreActivity
    //They are optional since not all entries in the API provide them all
    private String  bred_for    ="";
    private String  breed_group ="";
    private String  life_span   ="";
    private String  temperament ="";
    private String  origin      ="";
    private String  weight      ="";
    private String  height      ="";

    /**
     * A basic Dog constructor, not every class field is being initialised since some of the API entries
     * doesn't contain any information about them.
     * @param id an ID of the dog equal to the dog's ID in the API
     * @param name the name of the dog breed
     * @param imageUrl an URL to the source containing the image of the dog
     */
    //A Dog object constructor
    public Dog(int id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    //Getters
    public int getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public String getLife_span() {
        return life_span;
    }
    public String getBred_for() {
        return bred_for;
    }
    public String getBreed_group() {
        return breed_group;
    }
    public String getTemperament() {
        return temperament;
    }
    public String getWeight() {
        return weight;
    }
    public String getHeight() {
        return height;
    }
    public String getOrigin() {
        return origin;
    }


    //Setters
    public void setBred_for(String bred_for) {
        this.bred_for = bred_for;
    }
    public void setBreed_group(String breed_group) {
        this.breed_group = breed_group;
    }
    public void setLife_span(String life_span) {
        this.life_span = life_span;
    }
    public void setTemperament(String temperament) {
        this.temperament = temperament;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public void setWeight(String weight) {
        this.weight = weight;
    }
    public void setHeight(String height) {
        this.height = height;
    }

}//end of Dog class
