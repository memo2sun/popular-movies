#popular-movies
# popular-movies

## Project Summary
Display the most popular movies and top rated movies playing, and store favorites locally.
- Three menus that are sorted by most popular, top rated, or favorites.
- Tap on a movie poster and transition to a details screen
- View and play trailers
- Read reviews of a selected movie
- Able to mark a movie as a favorite, and store it locally.
- Favorite movie collection can be viewed offline.

<p float="left">
  <img src="assets/mainActivity.png" width="230" />
  <img src="assets/detailActivity_synopsis.png?" width="230" /> 
  <img src="assets/detailActivity_trailers.png" width="230" />
  <img src="assets/detailActivity_reviews.png" width="230" />
</p>

## Installation
1. Clone git repository.
2. Request an [API key](https://www.themoviedb.org/documentation/api).
3. Import the project and add API key.
   https://github.com/codepath/android_guides/wiki/Storing-Secret-Keys-in-Android
  - Create the resource file called res/values/secrets.xml, and replace xxxxxx with your own api key.
     ```
         <!-- Inside of `res/values/secrets.xml` -->
         <?xml version="1.0" encoding="utf-8"?>
         <resources>
               <string name="my_api_key">xxxxxx</string>
         </resources>
      ```
## Libraries
- Room
- ViewModel
- LiveData
- Rxjava with Retrofit
- Picasso
- Viewpager2
