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
- **Engaging Game**: Have some fun while learning! Test your knowledge by matching flags to their
  countries. Compete globally and see how you rank on the leaderboard.
- **Offline First**: Use most features offline post the app's first use. Once it connects to the
  internet initially, it operates offline seamlessly.
- **Favorite Countries**: Quickly access your preferred countries by marking them as favorites.
- **Filter by Continent and Population Size**: Easily find countries based on continents and
  population size.

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

https://github.com/Pablit0x/NationExplorer/assets/76017191/06693236-55c2-4ea1-ac8b-6478a36653e2

## Screenshots

### Android

![Android Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/b79f6669-2d97-4ce0-9314-9291d55a7786)

![Android Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/e1b532b4-1282-4535-a955-018136a4f8ac)

![Android Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/3356596f-92e5-44e6-b059-6b538007e11c)

![Android Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/08487342-af1b-4665-b8c4-5441e03163fc)

![Android Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/ccaf76b7-0b46-4e6a-9ec4-f91c6eae3f59)

### iOS

![iOS Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/39f74495-65de-44f1-bb3c-bdd2438374fe)

![iOS Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/12e66609-361d-4f5e-97ca-02ed05da38ac)

![iOS Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/c3d2b5e8-153c-445e-8f03-90417ed8d15e)

![iOS Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/285c140f-069f-4b0f-870e-c73633f7cbcf)

![iOS Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/14df6335-1f41-4fe2-b3f8-d318b6c17cdf)

Feel free to contribute, report issues, or suggest enhancements!
