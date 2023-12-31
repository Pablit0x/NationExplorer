# Nation Explorer - Compose Multiplatform App <img src="https://github.com/Pablit0x/NationExplorer/assets/76017191/27b397b2-ef8e-4ace-a25b-1f6c7888a848" alt="Logo" width="40" height="40">

Nation Explorer is a Compose Multiplatform offline-first mobile app (**for both iOS and Android**)
that offers an exploration of countries worldwide. Leveraging modern technologies, it provides users
with an engaging experience to discover and interact with countries across the globe.

## Features

- **Country Details**: Explore comprehensive information about countries, including flags, capital
  cities, area, languages spoken, currency, population, and timezones.
- **Interactive Maps**: View countries on maps using Google Maps for Android and Apple Maps for iOS.
- **Real-time Weather**: Get up-to-date weather updates for each country's capital city.
- **Engaging Game**: Test your knowledge by matching flags to their respective countries.
- **Online Leaderboard**: Compete globally and track your progress on the leaderboard.
- **Offline First**: Use all features offline post the app's first use, excluding live weather
  updates. Once it connects to the internet initially, it operates offline seamlessly.

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
- **Weather, Country, and Geolocation APIs**: Data
  sources([Weather API](https://api.api-ninjas.com/v1/weather), [Country API](https://restcountries.com/v3.1/all), [Geolocation API](https://api.opencagedata.com/geocode/v1/))
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

### Android

https://github.com/Pablit0x/NationExplorer/assets/76017191/25f785b6-9ceb-4940-ab3e-a7aad8b25b44

### iOS

https://github.com/Pablit0x/NationExplorer/assets/76017191/a045778a-7fe4-462e-bca7-1bb35633cb5a

## Screenshots

### Android

![Android Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/523d1e80-27a5-4887-a6ce-9265ad15e02b)
![Android Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/9ab69ca1-660a-4f9d-960e-a939d9f15f8c)
![Android Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/1bd2e3b4-92f7-4ac7-82cf-ecf3005d1e41)
![Android Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/d10755a4-bbb9-4275-9d93-ba6bbc245071)
![Android Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/15ba2157-3ed0-44d8-987e-b9debd7236c2)


### iOS

![iOS Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/6a6e4115-9936-43ad-bfdd-3bac630a4da1)
![iOS Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/eb17cb64-58d9-4d09-a09c-992637614dda)
![iOS Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/fd93a498-a395-4f80-a3bf-7eebbfd26665)
![iOS Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/a1fef6c5-9230-4b67-8e68-958bbdb1c81a)
![iOS Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/bbe846f6-fec4-4b61-bbdf-7990a559a419)

Feel free to contribute, report issues, or suggest enhancements!
