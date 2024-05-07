# Simple Recipes application
## Introduction 

The application is a user-friendly recipe management solution. 
It allows you to browse through a wide variety of recipes, view detailed information about each recipe, and save your favorite ones for offline viewing. 
The application is designed with a clean and intuitive interface, making it easy to navigate and find the recipes you're interested in.


## Tech stack:

Programming Languages: Kotlin
Build System: Gradle
Architecture: Model-View-ViewModel (MVVM)
UI: Jetpack Compose
Dependency Injection: Koin
Networking: Retrofit
Data Persistence: Room
Version Control: Git
Image Loading: Coil
Asynchronous Programming: Kotlin Coroutines


## Notes
Application uses [clean architecture with MVVM pattern](https://developer.android.com/topic/architecture):
- Separation of concerns, domain, data and ui layers as per Google recommendations
- Dependencies are managed through the [version catalogs](https://developer.android.com/build/migrate-to-catalogs)