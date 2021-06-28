# DoggoExplorer
This repository contains an Android app called "Doggo Explorer" made as a final project for the CS50: Introduction to Computer Science 2020 Course on EdX.
After that, I got it adapted to meet the requirements of my academic programming course final project task.

This application is a collection of dog breeds and some basic information about each single one of them. 
The app makes use of "TheDogAPI - Dogs as a Service" for collecting some basic information about dog breeds as well as Wikimedia REST API for some more detailed descriptions.
Jsoup, Picasso, Volley, Gson are featured in this project as well. 

# Main activity 

![image](https://user-images.githubusercontent.com/61741336/123675241-c933fa00-d842-11eb-92ea-f4659d043db3.png)

The Main Activity consists of:
- a "dog of the moment" (every time we launch the app the "dog of the moment" changes) picture, its breed name, 
- two buttons: "Learn more"     to display more information about the dog of the moment               -> Clicking the button starts a new LearnMoreActivity
               "List of breeds" to list all the breeds available in the API along with their pictures -> Clicking the button starts a new DogListActivity

# LearnMore activity
![image](https://user-images.githubusercontent.com/61741336/123675392-f2548a80-d842-11eb-88e0-3cdfa21dfa7f.png)


The LearnMore Activity consists of:
- a TextView displaying the name of the breed of the dog we are interested in
- an ImageView populated with the image of the dog. The dog image url is obtained from TheDogAPI
- a ScrollView containing some additional information about the breed like: life span, origin, (...), if they were provided by the API (sometimes they are not)
- a ScrollView is additionally populated with a first paragraph of text from Wikipedia article abot this breed, which is obtained from Wikimedia REST API,
  the title of this article, which is necessary to make a Wikimedia API call is obtained by using Jsoup to look for the first result of googling "[dog name] + dog wikipedia"
  and then parsing the search results in specific way to get the access to the first result URL.

# DogList actvity

![image](https://user-images.githubusercontent.com/61741336/123677196-1fa23800-d845-11eb-8849-523e24f499cd.png)   ![image](https://user-images.githubusercontent.com/61741336/123677033-e9fd4f00-d844-11eb-9b7b-b98d1ed30f26.png)    ![image](https://user-images.githubusercontent.com/61741336/123677304-3d6f9d00-d845-11eb-9d3c-64ce9e96df60.png)  ![image](https://user-images.githubusercontent.com/61741336/123677381-54ae8a80-d845-11eb-9dc3-bf0bac17e779.png)



The DogList Activity consists of:
- A custom Menu bar that allows searching for the specific breed name
- A RecyclerView containing all the dogs in the API sorted alphabetically A-Z each one represented by a CardView containing its name, img and a favourite symbol,
 which can be clicked to set this specific dog breed as a favourite. The information about users favourite dogs is stored in a HashMap which is then saved to SharedPreferences
 when the app is closed. This way it can be restored when the app is turned on again.
 
 All the data is being stored in a list of Dog Objects which is being created in the MainActivity after the API call gets a JSONArray response.
 All API requests are handled by Volley, ImageViews are being populated with the help of Picasso library, and 
 the app icon was obtained as a free image resource made by https://www.flaticon.com/authors/dinosoftlabs from https://www.flaticon.com
