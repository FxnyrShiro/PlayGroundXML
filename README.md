# MusicApp

A simple music search application built with modern Android development tools.

## Features

*   **Search for tracks:** Find your favorite music by searching for a track title.
*   **View track details:** Click on a track to see more details, including a larger album cover.
*   **Favorites:** Add and remove tracks from your personal favorites list.
*   **Persistent Favorites:** Your favorite tracks are saved locally on your device.

## Architecture

The application follows the principles of **Clean Architecture**. The code is divided into three main layers:

*   **Presentation:** The UI layer, built with **Jetpack Compose**. It uses **MVVM (Model-View-ViewModel)** to separate UI logic from business logic.
*   **Domain:** This layer contains the core business logic of the application, encapsulated in **UseCases**.
*   **Data:** This layer is responsible for providing data to the application. It includes a **Repository** that fetches data from both a remote **(Retrofit)** and a local **(Room)** data source.

## Technologies Used

*   **Kotlin:** The programming language used for the application.
*   **Jetpack Compose:** For building the user interface.
*   **Koin:** For dependency injection.
*   **Retrofit:** For making network requests to the Deezer API.
*   **Room:** For local database storage of favorite tracks.
*   **Coil:** For loading images.
*   **Jetpack Navigation for Compose:** For navigating between screens.
