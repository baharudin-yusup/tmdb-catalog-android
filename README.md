# The Movie DB Android App

[![Build Status](https://github.com/baharudin-yusup/the-movie-db-android/actions/workflows/main_build_release.yml/badge.svg)](https://github.com/baharudin-yusup/the-movie-db-android/actions/workflows/main_build_release.yml)

This Android application, developed in Kotlin, showcases movies and TV shows using TheMovieDB API.

## Features

- Discover popular movies and TV shows.
- View details of individual movies and TV shows.
- Search for specific movies or TV shows.
- Mark movies or TV shows as favorites.

## App Structure

- **Architecture:** Clean Architecture
- **Modular Structure:** Yes

## Dependencies

This app utilizes various libraries and frameworks for different functionalities such as navigation, network operations, coroutines, lifecycle management, dependency injection, paging, local database management, image loading, and basic UI elements.

You can find the complete list of dependencies and their usage in the `Dependencies.kt` file within the project.

## Setup

To run the app, follow these steps:

1. Clone the repository:

   ```bash
   git clone https://github.com/baharudin-yusup/the-movie-db-android.git
   ```
2. Create a `local.properties` file in the root directory of the project and specify the location of your Android SDK. Add your access tokens for development and production as follows:
   ```properties
   sdk.dir=your_sdk_dir
   DEV_ACCESS_TOKEN=your_dev_access_token_here
   PROD_ACCESS_TOKEN=your_prod_access_token_here
   ```

## GitHub Actions

This project utilizes GitHub Actions for continuous integration. The build status badge above indicates the status of the automated build process.