# MusicApp

A modern, reactive music search application built with the latest Android development tools. This app allows users to search for tracks, view details, and manage a local list of favorite songs.

## Features

*   **Dynamic Track Search:** Find your favorite music with a search that reacts as you type.
*   **Detailed Track View:** Get more information about a track, including a larger album cover, duration, and release date.
*   **Favorites Management:** Add and remove tracks from a personal favorites list.
*   **Persistent Storage:** Your favorite tracks are saved locally using a Room database, ensuring they are available even after the app is closed.
*   **Clean, Responsive UI:** A user interface built entirely with Jetpack Compose, featuring clear loading, error, and content states.

## Architecture

The application is built upon the principles of **Clean Architecture** to ensure a scalable, maintainable, and testable codebase. The project is separated into three distinct layers:

*   **Presentation (UI Layer):**
    *   Built entirely with **Jetpack Compose**.
    *   Follows the **MVVM (Model-View-ViewModel)** pattern.
    *   `ViewModel`s expose UI state as `StateFlow`, creating a reactive data flow from the domain layer to the UI.
    *   Handles all user interactions and observes data streams from the domain layer.

*   **Domain Layer:**
    *   Contains the core business logic of the application.
    *   Encapsulates logic within **UseCases** (e.g., `SearchTracksUseCase`, `AddTrackToFavoritesUseCase`).
    *   Defines **"Clean" Models** (`Track`, `Album`, `Artist`) that are independent of the data layer's implementation details. This was a key architectural improvement to decouple the business logic from data sources.
    *   The `Repository` interface is also defined here, following the Dependency Inversion Principle.

*   **Data Layer:**
    *   Implements the `Repository` interface from the domain layer.
    *   Responsible for abstracting data sources.
    *   Uses **Retrofit** to fetch data from the remote Deezer API.
    *   Uses **Room** to provide a local database for persisting favorite tracks.
    *   Contains **Mappers** to convert data transfer objects (DTOs) and database entities into the clean domain models.

## Key Technologies & Libraries

*   **UI:**
    *   [Jetpack Compose](https://developer.android.com/jetpack/compose): For building a modern, declarative UI.
    *   [Navigation for Compose](https://developer.android.com/jetpack/compose/navigation): For handling navigation between screens.
    *   [Coil](https://coil-kt.github.io/coil/): For efficient image loading.

*   **Architecture & Async:**
    *   [Kotlin Coroutines & Flow](https://kotlinlang.org/docs/coroutines-guide.html): For managing asynchronous operations and creating reactive data streams.
    *   [Koin](https://insert-koin.io/): For dependency injection, used to provide dependencies across all layers.

*   **Data:**
    *   [Retrofit](https://square.github.io/retrofit/): For type-safe HTTP requests to the Deezer API.
    *   [Room](https://developer.android.com/training/data-storage/room): For robust, local database persistence.
    *   [Kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization): For parsing JSON data from the API.

## System Requirements

*   **Minimum Android Version:** Android 5.0 (Lollipop), API 21
