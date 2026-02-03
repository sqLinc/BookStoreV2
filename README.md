# 📚 BookStore App
Modern Android application for browsing, managing and personalizing a book collection, built using Kotlin, Jetpack Compose, Clean Architecture, Room and Firebase.
---

## ✨ Features

- Authentication (Email/Password, Google Sign-In)
- Per-user data (favorites & read books)
- Browse all available books
- Mark books as Favorite / Read
- Category filtering via Navigation Drawer
- Add & edit books (admin only)
- Image picker for book covers
- Book details screen
- Settings (theme, language, cache, account)
- Light / Dark theme support
- Offline-first support with local cache
- Optimized network usage (no unnecessary Firebase calls)

---

## 🏗 Architecture

The project is built using **Clean Architecture** with clear separation of concerns:

- **Presentation** (Compose, ViewModel)
- **Domain** (UseCases, Models)
- **Data** (Repository, Room, Firebase, DataStore)

### Layers responsibility

- **UI (Compose)** – renders state and sends events  
- **ViewModel** – manages UI state and user actions  
- **UseCase** – contains business logic and validation  
- **Repository** – orchestrates data sources  
- **Room** – local database (single source of truth)  
- **Firebase** – remote data source  
- **DataStore** – user session, theme and language settings  

UI never talks directly to Firebase — all data flows through Repository and Room.

---

## 🔁 Offline-First Data Flow

- Data is loaded from Firebase once and cached locally in Room  
- UI observes data via Flow  
- All updates are applied locally first  
- Network is used only for synchronization  
- App works without internet connection  

---

## 🛠 Tech Stack

**Language**
- Kotlin  

**UI**
- Jetpack Compose  
- Navigation Compose  

**Architecture**
- Clean Architecture  
- MVVM  
- Repository Pattern  

**Async**
- Kotlin Coroutines  
- Flow  

**Data**
- Room  
- Firebase Auth  
- Firebase Firestore  
- DataStore  

**DI**
- Hilt  

**Testing**
- Mockito  
- UseCase & ViewModel tests  

---

| Login | Book List | Details |
|------|----------|--------|
| ![](https://github.com/sqLinc/BookStoreV2/blob/main/app/src/main/java/com/example/bookstorev2/ui/theme/screenshots/login.png) | ![](https://github.com/sqLinc/BookStoreV2/blob/main/app/src/main/java/com/example/bookstorev2/ui/theme/screenshots/book_list.png) | ![](https://github.com/sqLinc/BookStoreV2/blob/main/app/src/main/java/com/example/bookstorev2/ui/theme/screenshots/detail_screen.png) |

| Add Book | Settings | Drawer |
|---------|---------|--------|
| ![](https://github.com/sqLinc/BookStoreV2/blob/main/app/src/main/java/com/example/bookstorev2/ui/theme/screenshots/add_book.png) | ![](https://github.com/sqLinc/BookStoreV2/blob/main/app/src/main/java/com/example/bookstorev2/ui/theme/screenshots/settings.png) | ![](https://github.com/sqLinc/BookStoreV2/blob/main/app/src/main/java/com/example/bookstorev2/ui/theme/screenshots/drawer_body.png) |



## 🧪 Testing

The project contains:

- Unit tests for UseCases  
- ViewModel tests for UI state  
- Mocked repositories  
- Validation and error handling tests  

Testing focuses on business logic and UI state behavior.

---

## 🧠 What I Learned

- Designing offline-first architecture  
- Building scalable data flow with Room + Firebase  
- Separating business logic using Clean Architecture  
- Working with state-driven UI in Jetpack Compose  
- Writing testable code (UseCases & ViewModels)  
- Optimizing network usage and UI updates  
- Managing per-user data and synchronization  

---
