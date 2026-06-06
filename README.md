# Movie App

A modern Android application for discovering movies, watching trailers, and managing your watchlist. Built entirely with Kotlin and Jetpack Compose, this app demonstrates the use of Clean Architecture, modern Android development practices, and smooth UI/UX.

## Features

### 1. Browse Genres
Explore a comprehensive list of movie genres (Action, Comedy, Drama, etc.) acting as the main gateway to discovering content.

### 2. Discover Movies
Browse through movies within specific genres. Features seamless infinite scrolling (pagination) to ensure a smooth experience without memory issues.

### 3. Movie Details
Dive deep into specific movies to see their:
- Synopses, Ratings, and Runtimes.
- Complete Cast profiles.
- User Reviews (with expandable *Read more/less* functionality).

### 4. Custom Trailer Player
Watch official YouTube trailers directly inside the app in Fullscreen mode. Built using a custom WebView implementation of the YouTube iFrame API to bypass common YouTube Android SDK playback restrictions.

### 5. Search Movies
Find your favorite movies instantly with an interactive search bar.
- Implements `debounce` (waits 500ms after you stop typing) and `flatMapLatest` to optimize network requests and prevent API spam.
- Fully paginated search results.

### 6. Watchlist Management
Save movies to your Watchlist to keep track of what you want to watch next. Your watchlist state is preserved locally and reflected across all screens.

### 7. Dark/Light Mode Theme
Toggle between beautifully crafted light and dark themes, fully supporting Material 3 dynamic color schemes.

---

## Tech Stack

This project uses the latest Android technologies and architectural patterns:

### Core Architecture
- **Clean Architecture**: Separation of concerns into `Presentation`, `Domain`, and `Data` layers.
- **MVVM Pattern**: Model-View-ViewModel architectural pattern.
- **Dependency Injection**: [Koin](https://insert-koin.io/) for modular and lightweight DI.

### UI & Presentation
- **Jetpack Compose**: Android’s modern toolkit for building native UI declaratively.
- **Navigation Compose**: Type-safe navigation utilizing `kotlinx.serialization` and `@Serializable` routes.
- **Material Design 3**: Modern UI components and theming.

### Data & Concurrency
- **Kotlin Coroutines & Flow**: For asynchronous programming, state management, and reactive data streams.
- **Paging 3**: For efficient loading and infinite scrolling of large datasets from the network.
- **Retrofit & Ktor**: Type-safe HTTP clients for interacting with the TMDB API.

---

## Getting Started

### Prerequisites
- Android Studio Ladybug (or newer).
- JDK 17 (Corretto or JetBrains Runtime).

### Setup
1. Clone the repository.
2. Open the project in Android Studio.
3. Sync the project with Gradle files.
4. **API Key Setup**: 
   - This app relies on the [TMDB (The Movie Database) API](https://developer.themoviedb.org/docs/getting-started).
   - Ensure your TMDB API Key is added to your `local.properties` file located at the root of the project:
     ```properties
     TMDB_API_KEY=your_api_key_here
     ```
5. Build and run the app on an Emulator or a physical device.
