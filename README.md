# EduRate Mobile App

EduRate is a mobile application designed to provide students with a platform to rate college professors fairly and interactively. The app is built using Flutter and integrates Firebase Firestore for user authentication, course storage, and real-time database access.

## Features

- **User Authentication**: 
  - Sign up with AUC email.
  - Login functionality with Firebase Authentication.
  - Password reset via email.

- **Course Database**: 
  - Courses are preloaded from Firebase Firestore.
  - Only administrators have access to modify the course database.

- **Navigation**:
  - Bottom navigation bar for quick access to menu, search, profile, notifications, and settings.
  - Department menu with course lists displayed dynamically from the database.

## Prerequisites

1. **Flutter**: Ensure you have Flutter installed.
   - Installation guide: [Flutter Install](https://flutter.dev/docs/get-started/install)
2. **Firebase Project**:
   - Set up a Firebase project: [Firebase Console](https://console.firebase.google.com/).
   - Enable Authentication (Email/Password) and Firestore Database.
3. **Dart SDK**: Ensure Dart SDK is installed and configured.

## Getting Started

### 1. Clone the Repository
```bash
git clone <repository-url>
cd edurate-mobile-app
```

2. Install Dependencies
Run the following to fetch all dependencies:

``` bash
flutter pub get
```

3. Configure Firebase
Download the google-services.json file (for Android) or GoogleService-Info.plist file (for iOS) from your Firebase console.
Place the file in the respective directories:
Android: android/app/
iOS: ios/Runner/


5. Run the App
Run the application on an emulator or physical device:

```bash
flutter run

```

6. Technologies Used
Flutter: Cross-platform app development framework.
Firebase Firestore: Real-time NoSQL database for storing courses and user data.
Firebase Authentication: For managing user sign-ups and logins.
Dart: Programming language for Flutter.


