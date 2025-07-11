# 📦 Kepto — Zepto Delivery App Clone (Learning Project)

Welcome to **Kepto**, a learning-focused project where we explore building a modern grocery delivery app inspired by **Zepto** — built using **Kotlin, Jetpack Compose, MVVM, Firebase**, and more. This project is designed to help you (and me!) understand real-world Android app architecture while creating a smooth, user-friendly delivery experience.

---

## 🚀 What This App Does

- 🔐 OTP-based phone number authentication
- 📍 Location access using a bottom sheet (like Zepto)
- 📦 Item availability based on selected location
- 📲 Modern UI using Jetpack Compose
- 🌐 Firebase integration for auth and location storage
- 🧠 Clean architecture with Repository + ViewModel pattern

---

## 🎯 Why This Project?

This app is a **learning playground** to:
- Practice Android development using **modern best practices**
- Understand how apps like **Zepto, Blinkit** manage location + delivery flow
- Improve skills in **state management**, **permissions**, and **Compose UI**

---

## 🧱 Tech Stack

| Layer         | Tech used                          |
|---------------|------------------------------------|
| UI            | Jetpack Compose                    |
| State         | ViewModel + StateFlow              |
| Permissions   | ActivityResult API (Compose style) |
| Auth          | Firebase Authentication (OTP)      |
| Storage       | Firebase Firestore + DataStore     |
| Location      | Fused Location Provider (Google)   |
| Architecture  | MVVM + Repository pattern          |
| DI            | Hilt                               |

---

## 📸 Screenshots *(Coming soon)*

_(Add screenshots here once your UI is more complete — like location sheet, home page, etc.)_

---

## 🛠️ Features Breakdown

### 🔑 Authentication
- OTP-based login (via Firebase)
- Auto-login after verification

### 📍 Location
- Modal Bottom Sheet that asks for permission
- Prompts user to turn on GPS (if disabled)
- Fetches one-time location and saves it
- Based on location, items are loaded dynamically

### 🏠 Home
- LazyColumn of available items
- Dynamic content based on selected location

---

## 🧩 Architecture Diagram

