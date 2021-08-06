App Design Project (Zindzi Griffin) - README Template
===

# Restuarant Finder

## Table of Contents
1. [Overview](#Overview)
1. [Product Spec](#Product-Spec)
1. [Wireframes](#Wireframes)
2. [Schema](#Schema)

## Overview
### Description
Restuarant Finder simplifies the task of finding eateries so that you can spend more time dining and less time deciding. 

Finding a place to eat can be overwhelming and we want to simplify that process by providing you with restuarants near your location, filtering options, and reduce the clutter on the screen by showing you only a few options at a time.

### App Evaluation
[Evaluation of your app across the following attributes]
- **Category:** Food & Drink
- **Mobile:** Mobile App
- **Story:** Allows users to choose their favorite types of food/cuisines and use that information to display restuarants that fall into the type of food and cuisine, and select a restuarant of their choice and display the location the user selected using Google Maps API. 
- **Market:** Anyone that struggles with finding places to eat can use this app. Ability to save restuarants that they went to using this app. 
- **Habit:** People will naturally come back whenever they want to find a restuarant to eat at.
- **Scope:** This app can maybe expand to where people can leave reviews for the restuarant they visited and maybe third party app integration such as uber eats integration where people can order food.

## Product Spec

### 1. User Stories (Required and Optional)

**Required Must-have Stories**

* User can create an account.
* User can login.
* App has landscape and portrait view capibilities.
* User can save restuarants they visted by clicking the save button.
* App uses google maps API to find the location of the restuarant selected and provide directions to the restuarant.
* User can pull to refresh the page with new restuarants 
* User can pick their preferable cuisine type and display a list of restaurants on the recommendations tab
* User can save their preferences (restuarants they visted and restuarants they want to try) in a separate tab within the app.
* User can write reviews on the restuarants they visited and user can see how many characters they have typed when they write their review

**Optional Nice-to-have Stories**

* User can see deals & offers a restuarant of their choice is having.
* User can see what restuarants are POC owned
* User can see the most popular restuarants
* User can view the menu for that restuarant

### 2. Screen Archetypes

* Login Screen
   * Create an account 
   * Login in with their username and password
* Recommendation Tab
   * Contains a list of recommended restaurants
   * Contains a list of restuarants the user wants to try
* Home Screen/ Stream Screen
    * Displays a scrollable list of restuarants and their locations based on the type of food the user is interested in.
    * User can click on a restuarant they are interested in and view details about the restuarant such as whether they have indoor/outdoor dining,their menu, a section to write reviews, view other people's reviews, restuarant hours, phone number etc, and show price ranges of restaurants chosen from lowest to highest
* Search Screen
    * User can search for restuarants based on the provided location 
    * Based on the user's preferences I will recommend a restuarant

* Favorites Screen
    * Contains a list of the restuarants the user has saved upon clicking the save button

### 3. Navigation

**Tab Navigation** (Tab to Screen)

* Home Feed
* Favorites Tab
* Search Tab 
* Recommendation Tab

*Bottom Navigation

**Flow Navigation** (Screen to Screen)

* Login Screen
   * Home Screen
* Search Screen
   * Saved Preferences
   * Home Screen
* Home Screen/ Stream Screen
    * None 
* Recommendation Screen
*
* Favorites Screen
    * Home Screen
    * Search Screen

## Wireframes
[Add picture of your hand sketched wireframes in this section]
<img src="YOUR_WIREFRAME_IMAGE_URL" width=600>

### [BONUS] Digital Wireframes & Mockups
![](https://i.imgur.com/6Tko48Z.png)


### [BONUS] Interactive Prototype

## Schema 
[This section will be completed in Unit 9]
### Models
Models: Users 
| Property | Type   | Description                                 |
| -------- | ------ | ------------------------------------------- |
| author   |String  |  name of the user                           |
| image    |File    |  user's profile image.                      |
|createdAt |DateTime|  date when the user save's a restaurant upon clicking the save button|
| text     |String  |  info the user types in their saved preferences tab|
| objectID | String | unique id for the user post (default field) |

Models: List of Restaurant (Restuarant name, image (return the first image that I get), reviews, location)

| Property | Type   | Description                                 |
| -------- | ------ | ------------------------------------------- |
|restuarant name|String|  name of the restuarant                  |
| image    |File    |  image of the restuarant.                   |
| location |String  |     display location of restuarant          |
| cuisineType|String| display recommended restaurants based on preferences
| amenitites | String| display recommended restaurant based on indoor/outdoor|
| price | String | display price range using $, $, $$ on recommendation list|

### Networking
- [Add list of network requests by screen ]
Home Feed Screen:
    - (Read/GET) Fetching a list of restuarants from the yelp api for the user's feed
    -(Read/GET) Query location of each restuarant using the google maps API
    -(Read/GET)Query range of prices using the yelp api?
Profile Screen containing user's saved restuarants:
    - (Read/GET) Query logged in user object
    - (Update/PUT) Update the user's profile image
    - (Read/Get) Query saved restuarants 
    -(Update/PUT) Manually add restuarants the user wants to try
    -(Delete) Delete a saved preference
Search Screen
    -(Read/GET) Query a list of resturants using the yelp api based on what the user has searched for using the search app         functionality
- [Create basic snippets for each Parse network request]
//(Read/GET) Query all posts where user is author
// Create the ParseUser
ParseUser user = new ParseUser();
// Set core properties
user.setUsername("zindzigriffin");
user.setPassword("password123");
user.setImage(//insert image here);
user.setText("insert the text here");
user.put("phone", "650-253-0000");
// Invoke signUpInBackground
user.signUpInBackground(new SignUpCallback() {
  public void done(ParseException e) {
    if (e == null) {
      // Hooray! Let them use the app now.
    } else {
      // Sign up didn't succeed. 
    }
  }
  
 //(Update/PUT)
 ParseQuery<ParseObject> query = ParseQuery.getQuery(" ");

// Retrieve the object by id
query.getInBackground("//object id goes here", new GetCallback<ParseObject>() {
  public void done(ParseObject gameScore, ParseException e) {
    if (e == null) {
      // Now let's update it with some new data.
      user.put("", 1338);
      user.put("", true);
      user.saveInBackground();
    }
  }
});

 
- [OPTIONAL: List endpoints if using existing API such as Yelp]
