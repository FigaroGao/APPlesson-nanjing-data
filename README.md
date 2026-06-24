# Personal Report

**Project Title:** Explore Nanjing— A City Attractions Promotion App  
**Student Name:** Zixuan Gao  
**Student ID:** w20109966  
**Role:** Leader

## 1. Objective
To develop a simple Android app and practice version control using Git and GitHub.

## 2. Procedure
(1) Topic selection and diagram creation  
Use Figma to creat diagrams

(2) Project Initialization  
Create a new project in Android Studio, select the "Empty Views Activity" template, set the project name to "ExploreNanjing", the package name to "org.yourorg.explorenanjing", select Kotlin as the language, and set the minimum SDK to API 21.

(3) Configure Gradle and Dependencies  
Modify the `app/build.gradle.kts` file, set `compileSdk` and `targetSdk` to 34 (or 30), enable View Binding, and add the necessary dependencies, including libraries such as Retrofit, Gson, Glide, ViewPager2, Coroutines, and Timber.

(4) Set up the Application Class and Global Initialization  
Create a `MainApp` class inheriting from `Application`, `AttractionApiStore` (using Git for API management), `JsonCommentStore`, and `FavoriteHistoryStore` to implement data source and persistence management.

(5) Define Data Models  
Create `AttractionModel` and `CommentModel` data classes, using the `Parcelize` annotation to support passing data between Activities, and add necessary fields (such as `imageUrl`, `reservationUrl`, `ratingCategory`, etc.).

(6) Implement the Data Storage Layer  
Define the `AttractionStore` interface and its API implementation (`AttractionApiStore`), using Retrofit to retrieve attraction data from the network interface; implement `JsonCommentStore` (JSON persistence for comments) and `FavoriteHistoryStore` (JSON persistence for favorites and browsing history).

(7) Create Page Layout Files  
Create XML layout files for the login page, attraction list page, attraction details page, comments page, card item, comment item, and carousel item, and wrap all layout root elements within `<layout>` tags to enable View Binding.

(8) Develop Core Activities  
Implement `LoginActivity` (including automatic background image carousel), `AttractionListActivity` (supporting search, favorites, history switching, and RecyclerView display), `AttractionDetailActivity` (displaying details, background image, and reservation hyperlink), and `CommentsActivity` (supporting comment category filtering, adding, and long-press deletion).

(9) Implement Adapters  
Create `AttractionAdapter` (attraction list cards), `CommentAdapter` (comment list), and `ImageCarouselAdapter` (login page carousel).

(10) Configure AndroidManifest  
Modify `AndroidManifest.xml`, set `LoginActivity` as the launch activity, add the INTERNET permission, and register all Activities.

(11) Resolve Compilation and Binding Issues  
If you encounter issues with View Binding classes not being generated, resolve them by adding `<layout>` tags around all layout XML files and performing a Clean Project and Rebuild Project.  
Pushed the project to a GitHub repository.

(12) Fix Git Bug

## 3. Issues Encountered
(1) Gradle Sync Failure and JDK Toolchain Download Issues  
During the initial project build, Gradle sync failed with an error: unable to download JDK 11 (foojay automatic download failed), due to network restrictions in China preventing access to the foojay server.

(2) View Binding Classes Not Generated, Leading to Numerous "Unresolved reference" Errors  
After copying Activity code, Android Studio reported errors indicating that classes like ActivityXxxBinding and CardAttractionBinding (e.g., ActivityAttractionDetailBinding) were not recognized. This was because the layout XML files lacked the `<layout>` root tag.

(3) Network API Data Loading Failure or Empty Data  
Initially, when using a custom or third-party mock API, the list of attractions was empty or failed to load due to an unstable API address or mismatched format.

(4) Favorites and History Features Not Persisted  
In early testing, after clicking the heart icon or entering the details page, favorites/history were lost after switching pages or restarting the app. This was due to the lack of a persistence mechanism.

(5) Comment Category Filtering Button Logic Unclear  
The four buttons on the comments page (Excellent/Good/Ordinary/Bad) were initially used to add comment categories, but the requirement was to filter and display corresponding comments when clicked.

(6) RecyclerView Adapter Not Refreshing Promptly  
When switching between favorites/history/search, the RecyclerView sometimes did not immediately update the list or heart icon status. This was because the adapter's data source was not synchronized or `notify` was not called.

(7) When using Glide to load network images, some low-quality or timed-out images display as blank.

## 4. Solutions
(1) Gradle Sync Failure and JDK Toolchain Download Issues  
Resolved by manually specifying the path to the JDK included with Android Studio (C:\Program Files\Android\Android Studio\jbr) in Project Structure, avoiding the need for automatic dependency downloads.

(2) View Binding Classes Not Generated, Leading to Numerous "Unresolved reference" Errors  
Add the `<layout>` outer tag to all layout files, then perform Clean Project and Rebuild Project to successfully generate all Binding classes.

(3) Network API Data Loading Failure or Empty Data  
Using github and https://cdn.jsdelivr.net, adding exception handling and empty list fallback in AttractionApiStore to prevent application crashes.

(4) Favorites and History Features Not Persisted  
A new FavoriteHistoryStore class was added, using a JSON file to store the favorites Set and history List, achieving data persistence across sessions.

(5) Comment Category Filtering Button Logic Unclear  
Change the buttons to MaterialButtonToggleGroup, listen to the checked state to dynamically filter the comment list, and refresh the RecyclerView.

(6) RecyclerView Adapter Not Refreshing Promptly  
The solution involved maintaining an `allAttractions` cache in AttractionListActivity and recreating the adapter or performing partial `notify` when switching modes to ensure real-time UI response. Image loading is slow or fails.

(7) When using Glide to load network images, some low-quality or timed-out images display as blank. Solution: Add `.placeholder()` and `.error()` to the Glide call and ensure all `imageUrl` fields use valid CDN addresses (such as jsDelivr) to significantly improve the loading experience.

## 5. Conclusion
Through this experiment, we not only learned how to develop an Android app from scratch, but also gained practical experience in using Git for version control and collaborating with GitHub. Despite having no prior experience with Android Studio at the start of the semester, we successfully completed the app by consulting online resources, fully utilizing the lab sessions provided by our instructor, and using AI tools to assist with unfamiliar areas. This project enhanced our problem-solving skills, improved our understanding of software development workflows, and gave us confidence in independently handling technical challenges.
