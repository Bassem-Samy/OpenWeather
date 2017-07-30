# Open Weather
## App Description:
The app starts with a login screen, where the user get to Login using facebook (and I've added a skip button for the sake of testing if one doesn't want to login) </br>
The user is prompted to an activity where he can see the current weather for his location or the detailed 3 hours for the next five days forecast for his current location too.
## Technical Description:
* The app is structured in MVP.
* My most valuable reference for implementing MVP in android is [Antonio Leiva's Post](https://www.dropbox.com/s/r0wy953e50q0cra/feedback-app-release.apk?dl=0)
* I've used FusedLocation apis provided by Google play services to fetch the user's current location [reference](https://developer.android.com/training/location/index.html).
* Dagger 2 is used for depenedency injection, to inject dependencies in the current weather and forecast modules.
* I've integrated fabric's crashlytics for logging crashes.
* I have added a unit test for ForecastToDaysConverter.Java to test conversion of api forecast response to hours grouped by day.
* 3rd party libraries used:RxJava/RxAndroid 2, Dagger2, Butterknife, Retrofit2 and Glide.
</br></br>You can download a running apk from this [link](https://www.dropbox.com/s/6euf868nzkalhkk/open_weather.apk?dl=0)
