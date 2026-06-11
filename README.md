# DueProcess
Hiding sensitive apps from prying eyes

# App-based (Im)plausible Deniability for Android
Confidentiality of data stored on mobile devices depends on one critical security boundary in case of physical access, the device's lockscreen. If an adversary is able to satisfy this lockscreen challenge, either through coercion (e.g. border control or customs check) or due to their close relationship to the victim (e.g. intimate partner abuse), private data is no longer protected. 

Therefore, a solution is necessary that renders secrets not only inaccessible, but allows to plausibly deny their sole existence. This [thesis](https://github.com/brnhrd/DueProcess/raw/master/docs/MScThesis_BernhardGr%C3%BCndling.pdf) proposes an app-based system that hides sensitive apps within Android's work profile, with a strong focus on usability. It introduces a lockdown mode that can be triggered inconspicuously from the device's lockscreen by entering a wrong PIN for example. Usability, security and current limitations of this approach are analyzed in detail.

## Analysis of Implementation Options
<img src="/docs/img1.png" height="300" />

We evaluated different approaches and decided to implement a Device Policy Controller app in profile owner mode, because this should result in a decent level of usability and security (see figure). 

## The Application
The app is called "Due Process".

<img src="/docs/img2.png" width="200"/> <img src="/docs/img3.png" width="200"/> <img src="/docs/img4.png" width="200"/>

It allows users to hide apps on demand, on a per-app basis or by triggering a so-called lockdown-mode, which hides all apps marked as sensitive at once. This lockdown can be triggered with a button inside the app or by deliberately failing the device's lockscreen challenge a couple of times.

The app uses the work profile feature of Android and runs inside of it. Android allows the use of a separate lockscreen for the work profile, which encrypts the data inside with a different key. 

The APK can be acquired [here](https://github.com/brnhrd/DueProcess/releases), including a guided setup after installation. This enables users to perform downloads without attribution to their person.

This repository holds its source code. Advanced users can review the source code and make changes to it, which can further increase plausible deniability. After that, the application package has to be built from the code. For recommended changes see chapter 4.1 in the thesis.

## Build Environment Requirements
To build this project and run tests, you must have the following environment variables and SDKs configured:
- **Android SDK**: Install the Android SDK (API 36). Set the `ANDROID_HOME` environment variable to your SDK path, or define `sdk.dir=/path/to/android/sdk` in your `local.properties`.
- **Java Development Kit (JDK)**: A full JDK 21 (e.g. Temurin or OpenJDK) with `javac` is required. Set the `JAVA_HOME` environment variable to your JDK path, or define `org.gradle.java.home=/path/to/jdk21` in your `gradle.properties`.
Note: A simple JRE is not sufficient to compile the codebase.

