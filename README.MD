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

https://github.com/Pablit0x/NationExplorer/assets/76017191/6f116185-de96-48bc-b4e7-a81d54841a32

### iOS

https://github.com/Pablit0x/NationExplorer/assets/76017191/f65ecae9-d353-4737-ad53-f157cda0fcac

## Screenshots

### Android

![Android Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/523d1e80-27a5-4887-a6ce-9265ad15e02b)
![Android Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/7e735609-6f42-444f-88ba-066271469c4f)
![Android Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/1bd2e3b4-92f7-4ac7-82cf-ecf3005d1e41)
![Android Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/633e25b9-6ebe-442f-a653-bd03609eccdd)

### iOS

![iOS Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/6a6e4115-9936-43ad-bfdd-3bac630a4da1)
![iOS Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/40c33947-799e-4352-a43b-0c1a5746ab62)
![iOS Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/fd93a498-a395-4f80-a3bf-7eebbfd26665)
![iOS Screenshots](https://github.com/Pablit0x/NationExplorer/assets/76017191/21d303c7-8899-4ebe-908e-1c2fe5a68e42)

Feel free to contribute, report issues, or suggest enhancements!
