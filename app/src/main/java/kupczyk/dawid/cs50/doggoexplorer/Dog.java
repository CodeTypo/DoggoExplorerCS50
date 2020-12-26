package kupczyk.dawid.cs50.doggoexplorer;

import java.io.Serializable;

public class Dog implements Serializable{

    private int id;
    private String name;
    private String bred_for;
    private String breed_group;
    private String life_span;
    private String temperament;
    private String origin;
    private String imageUrl;
    private String wikipediaDesc;


    public Dog(int id, String name, String imageUrl) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
