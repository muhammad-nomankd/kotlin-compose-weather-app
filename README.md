# 🌦️ WeatherApp - Android (Kotlin & Jetpack Compose)

[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-blue.svg)](https://kotlinlang.org/)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-Material%203-green.svg)](https://developer.android.com/jetpack/compose)
[![Clean Architecture](https://img.shields.io/badge/Clean%20Architecture-MVVM-orange.svg)](https://developer.android.com/topic/architecture)
[![Dagger Hilt](https://img.shields.io/badge/Dagger%20Hilt-DI-blue.svg)](https://developer.android.com/training/dependency-injection/hilt-android)
[![OpenWeatherMap](https://img.shields.io/badge/OpenWeatherMap-Free%20Tier%20Ready-brightgreen.svg)](https://openweathermap.org/)
[![Unit Tests](https://img.shields.io/badge/Tests-21%20Passed-success.svg)](https://github.com/)

---

## 🚀 Overview

**WeatherApp** is a modern, responsive Android application built completely with **Jetpack Compose** and **Material 3**, following strict **Clean Architecture** and **MVVM** design principles. 

The application integrates with the **[OpenWeatherMap API](https://openweathermap.org/)** using the **Free 5-Day / 3-Hour Forecast endpoint** (`/data/2.5/forecast`), making it 100% accessible with a standard free API key without encountering `401 Unauthorized` restrictions.

---

## 📸 Screenshots

### 🏠 Home Screen

| Light Mode | Dark Mode |
|:---:|:---:|
| <img src="screenshots/home_light.png?raw=true" width="320"/> | <img src="screenshots/home_dark.png?raw=true" width="320"/> |

### 🔍 Search Screen

| Light Mode | Dark Mode |
|:---:|:---:|
| <img src="screenshots/search_light.png?raw=true" width="320"/> | <img src="screenshots/search_dark.png?raw=true" width="320"/> |

### ❤️ Favorite Cities Screen

| Light Mode | Dark Mode |
|:---:|:---:|
| <img src="screenshots/favorite_light.png?raw=true" width="320"/> | <img src="screenshots/favorite_dark.png?raw=true" width="320"/> |

### ⚙️ Settings Screen

| Light Mode | Dark Mode |
|:---:|:---:|
| <img src="screenshots/setting_light.png?raw=true" width="320"/> | <img src="screenshots/setting_dark.png?raw=true" width="320"/> |
---

## ✨ Features & Highlights

- 🌤️ **Real-Time Weather & 5-Day Forecast**: Accurate weather conditions, high/low daily temperatures, humidity, atmospheric pressure, wind speed, and sunrise/sunset times.
- 🎨 **Material 3 Design System**:
  - Full **Light Mode & Dark Mode** automatic support.
  - Adaptive glassmorphism hero weather card with dynamic linear gradients based on system theme.
  - Consistent typography scale and cohesive color tokens.
- 📜 **Full-Screen Continuous Scrolling**: Home screen uses a single top-level `LazyColumn` for fluid, full-screen scrolling.
- 🔍 **City Search**: Instant search for weather in any city worldwide with high-contrast, theme-adaptive text input.
- ❤️ **Favorite Cities**: Save, browse, and manage favorite locations stored locally using **Room Database**.
- ⚙️ **Customizable Units**: Toggle between **Metric (°C)** and **Imperial (°F)** with persistent storage using **DataStore Preferences**.
- 🛡️ **Offline & Network Error Handling**: User-friendly feedback for network timeouts, lack of internet connection, and invalid city queries.

---

## 🛠️ Tech Stack & Libraries

| Layer / Concern | Technology |
|---|---|
| **Language** | [Kotlin](https://kotlinlang.org/) 2.0+ (100% Coroutines & Flow) |
| **UI Framework** | [Jetpack Compose](https://developer.android.com/jetpack/compose) + [Material 3](https://developer.android.com/develop/ui/compose/designsystems/material3) |
| **Image Loading** | [Coil Compose](https://coil-kt.github.io/coil/compose/) |
| **Architecture** | MVVM + Clean Architecture (Presentation, Domain, Data) |
| **Dependency Injection** | [Dagger Hilt](https://dagger.dev/hilt/) + KSP |
| **Networking** | [Retrofit 2](https://square.github.io/retrofit/) + [OkHttp 3](https://square.github.io/okhttp/) + Gson |
| **Local Database** | [Room Database](https://developer.android.com/training/data-storage/room) + SQLite |
| **Key-Value Persistence** | [Jetpack DataStore Preferences](https://developer.android.com/topic/libraries/architecture/datastore) |
| **Testing** | JUnit 4, Mockito, Robolectric, Coroutines Test (`21 passed`) |

---

## 🔑 OpenWeatherMap API Setup (Free Tier)

This app is configured out-of-the-box for **OpenWeatherMap Free Plan** accounts:

1. Create a free account at [OpenWeatherMap](https://home.openweathermap.org/users/sign_up).
2. Generate your **API Key** from the [API Keys Dashboard](https://home.openweathermap.org/api_keys).
3. Open or create the `local.properties` file in the root directory of this project:

```properties
sdk.dir=/path/to/your/android/sdk
API_KEY="YOUR_OPENWEATHERMAP_API_KEY_HERE"
```

> [!NOTE]
> `local.properties` is ignored by Git, ensuring your API key remains secure and will never be committed to source control.

---

## 🚀 Getting Started

### Prerequisites
- **Android Studio**: Jellyfish / Koala / Ladybug or newer
- **JDK**: Version 11 or 17
- **Android SDK**: Minimum SDK 24 (Android 7.0+) | Target SDK 35

### Run the App
1. Clone the repository:
   ```sh
   git clone https://github.com/muhammad-nomankd/kotlin-compose-weather-app.git
   cd kotlin-compose-weather-app
   ```
2. Open the project in Android Studio.
3. Add your `API_KEY` in `local.properties`.
4. Click **Sync Project with Gradle Files**.
5. Select an Android emulator or physical device and click **Run ▶️**.

---

## 🧪 Running Tests

Execute the unit test suite:

```sh
./gradlew testDebugUnitTest
```

All 21 domain, mapper, and use case unit tests will execute and pass.

---

## 🏗️ Architecture Overview

The app strictly follows **Clean Architecture** with unidirectional data flow (UDF):

```
app/src/main/java/com/felippeneves/kotlin_compose_weather_app/
│
├── core/                  # Utilities, extensions, providers, and exceptions
├── data/
│   ├── local/             # Room Database (DAOs, Entities) & DataStore Manager
│   ├── mapper/            # API Response & Entity to Domain Model mappers
│   ├── model/             # Network response DTOs & Room entities
│   ├── remote/            # Retrofit WeatherService & Network interceptors
│   └── repository/        # Repository implementations
│
├── di/                    # Dagger Hilt dependency injection modules
├── domain/
│   ├── model/             # Core business models (Weather, WeatherItem, Temp, City)
│   ├── repository/        # Repository interfaces
│   └── usecase/           # Isolated business use cases
│
├── presentation/
│   ├── components/        # Reusable Compose UI components (TopBar, Search, Dialogs)
│   ├── features/
│   │   ├── home/          # Home screen, hero card, daily weather, metrics
│   │   ├── favorite_cities/# Saved locations list & management
│   │   ├── search/        # Search city screen
│   │   └── settings/      # Measurement unit preferences
│   └── navigation/        # Compose Navigation Graph & Destination routes
│
└── ui/theme/              # Material 3 Color Schemes, Typography, Shapes, and Theme
```

---

## 📜 License

This project is open-source and available under the [MIT License](LICENSE).
