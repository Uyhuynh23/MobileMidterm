# Mobile Coffee Shop App

A feature-rich mobile app for coffee ordering, built with **Kotlin** and **Android Studio** for the CS426 Midterm Project.

---

## Features

### Home
- Displays user name and loyalty card progress
- List of coffee items with category filtering
- Tap on coffee to view product details
- Bottom navigation to switch between screens

### Product Details
- Customize shot, size, ice level
- Dynamic price calculation
- Add to cart with selected customization

### Cart
- View and manage cart items
- Swipe to remove items
- View total price and checkout

### Orders
- View Ongoing and History tabs
- Manage order status
- Simple order tracking

### Rewards
- Earn points based on orders
- Stamp loyalty card: 1 stamp/order, 8 max
- Daily spin for random bonus points
- Redeem points for products

### Profile
- View and edit profile name

---

## Technical Overview

- **Language**: Kotlin  
- **Architecture**: Basic MVVM  
- **Data Persistence**: Custom `TinyDB` using `SharedPreferences` to save:
  - User accounts
  - Order/cart/reward data
  - Favorite items  
- **UI**: Material 3, ViewBinding, RecyclerView  
- **Animations**: Shake animation for rewards

---

## Data Logic

Instead of using a full backend, this app uses a lightweight approach:
- All app data is stored using keys in SharedPreferences
- User accounts are stored as serialized JSON
- Data isolation is handled using `currentUserId` as prefix for all keys (e.g., `23125047_Order`)

---

## Learning Reflection

As a student, I found the biggest challenge was understanding **SharedPreferences** and managing persistent user data. Initially, I had no idea where to start, so I followed tutorials to learn the basic structure of an Android app. Gradually, I learned how to:
- Store structured data with `TinyDB`
- Use AI tools to speed up debugging and implementation
- Build features independently

This project helped me gain confidence in:
- Self-learning
- Using Android Studio effectively
- Writing clean Kotlin code

---

## References

- [Shop App Tutorial](https://youtu.be/r-sAwsH7XUI?si=5R6W_g8eyCVaQFmq) – YouTube guide for building the main app structure  
- [Android Knowledge Channel](https://www.youtube.com/@android_knowledge) – For splash screen and RecyclerView tutorials  
- [Android Jetpack Documentation](https://developer.android.com/jetpack) – Official Android component guide  

---

## Author

- **Name**: Huỳnh Trấn Uy  
- **Student ID**: 23125047  
- **Course**: CS426 – Mobile Device Application Development  
