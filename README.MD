# Nation Explorer - Compose Multiplatform App <img src="https://github.com/Pablit0x/NationExplorer/assets/76017191/27b397b2-ef8e-4ace-a25b-1f6c7888a848" alt="Logo" width="40" height="40">


Nation Explorer is a Compose Multiplatform offline-first mobile app (**for both iOS and Android**)
that offers an exploration of countries worldwide. Leveraging modern technologies, it provides users
with an engaging experience to discover and interact with countries across the globe.

![world](https://github.com/Pablit0x/NationExplorer/assets/76017191/f888ae2a-b096-4ed9-b9d6-4d19aa2b0fef)

## Features

- **Country Details**: Explore comprehensive information about countries, including flags, capital
  cities, area, languages spoken, currency, population, timezones and intriguing facts.
- **Interactive Maps**: View countries on maps using Google Maps for Android and Apple Maps for iOS.
- **Real-time Weather**: Get up-to-date weather updates for each country's capital city.
- **Engaging Game**: Have some fun while learning! Test your knowledge by matching flags to their countries. Compete globally and see how you rank on the leaderboard.
- **Offline First**: Use most features offline post the app's first use. Once it connects to the internet initially, it operates offline seamlessly.
- **Favorite Countries**: Quickly access your preferred countries by marking them as favorites.
- **Filter by Continent and Population Size**: Easily find countries based on continents and population size.

## Google Play

Nation Explorer - https://bit.ly/nation-explorer

## Tech Stack

- **Compose Multiplatform**: UI framework that allows building declarative UIs for multiple
  platforms (Android, iOS, Desktop, Web)
- **Koin**: Dependency Injection framework([Koin](https://github.com/InsertKoinIO/koin))
- **Voyager**: Navigation library([Voyager](https://voyager.adriel.cafe/))
- **Ktor Client & Kotlin Serialization**: Making requests and handling
  responses ([Ktor](https://github.com/ktorio/ktor))
- **Google Maps & Apple Maps**: Map
  integrations ([Google Maps](https://developers.google.com/maps/documentation/android-sdk/maps-compose), [Apple Maps](https://developer.apple.com/documentation/mapkit/))
- **Weather, Country, Geolocation and Country Tidbits APIs**: Data
  sources ([Weather API](https://api.api-ninjas.com/v1/weather), [Country API](https://restcountries.com/v3.1/all), [Geolocation API](https://api.opencagedata.com/geocode/v1/), 
[Country Tidbits](https://github.com/Pablit0x/nation_explorer_tidbits_api))
- **MongoDB Device Sync**: Online leaderboard with live
  updates ([MongoDB Device Sync](https://www.mongodb.com/atlas/app-services/device-sync))
- **MOKO MVVM**: ViewModel sharing ([MOKO MVVM](https://github.com/icerockdev/moko-mvvm))
- **Multiplatform Settings**: Persistent key-value pair
  storage ([Multiplatform Settings](https://github.com/russhwolf/multiplatform-settings))
- **KStore**: Caching for offline support ([KStore](https://github.com/xxfast/KStore))
- **Libres**: Resource sharing ([Libres](https://github.com/Skeptick/libres))
- **Napier**: Logging library ([Napier](https://github.com/AAkira/Napier))
- **Kamel**: Async media loading ([Kamel](https://github.com/Kamel-Media/Kamel))
- **Kotlin Coroutines and Flows**: Managing asynchronous tasks and sequences for responsive and
  concurrent programming

## Demo

https://github.com/Pablit0x/NationExplorer/assets/76017191/c55039d0-5a50-4295-b85d-0846d68ec04c

## Screenshots

### Android

![Android Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/4c279a34-74b3-4b6d-a868-590d1cce5966)

![Android Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/2dc487f7-f900-409f-b9f9-9eac6b1899e7)

![Android Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/23156731-9a7a-4544-ab36-362b0f0e04d0)


### iOS

![iOS Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/39f74495-65de-44f1-bb3c-bdd2438374fe)

![iOS Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/ffacf4bf-089a-4b80-a40d-98fa343585c9)

![iOS Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/14df6335-1f41-4fe2-b3f8-d318b6c17cdf)

Feel free to contribute, report issues, or suggest enhancements!
