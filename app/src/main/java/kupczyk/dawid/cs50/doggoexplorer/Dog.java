package kupczyk.dawid.cs50.doggoexplorer;

import java.io.Serializable;

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
