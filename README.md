# Android Components
This is a repository that I used as a playground to test Android Architecture components and Persistency library.

The application is composed of 2 activities following MVVM architecture and data is fetched remotely (using the [moviedb APIs](https://www.themoviedb.org/)) and also stored locally for offline use.

Each activity is controlled by a VieModel that handles the data retrieval and notifies the View about changes in a reactive way using LiveData. Network operations and data retrieval from local storage are also handled reactively using RxJava.

The two screen are:
1. A listing screen showing the top rated movies sorted by their user rating. The ViewModel in this case also handles pagination of the results;
2. A detail screen that is shown when a used clicks on a list entry.