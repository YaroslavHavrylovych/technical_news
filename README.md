# Technarium

[![Technarium](./readme_files/technarium_logo.png)](https://play.google.com/store/apps/details?id=com.gmail.yaroslavlancelot.technarium)

[![Build Status](https://travis-ci.com/YaroslavHavrylovych/technical_news.svg?branch=development)](https://travis-ci.com/github/YaroslavHavrylovych/technical_news)
[![CodeFactor](https://www.codefactor.io/repository/github/yaroslavhavrylovych/technical_news/badge)](https://www.codefactor.io/repository/github/yaroslavhavrylovych/technical_news)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

*Read in [Українська](README-UK.md)*

## A short introduction

**Technarium** - is a technical news (\*not only) aggregator,
where at least 50% of the content in Ukrainian.

\*Not only - two additional sections are openings and events (both from 
[DOU](https://dou.ua/?switch_lang=en))

The author, or an abstract me in a vacuum, was annoyed to switch a few
times per day, within multiple web resources (to have it on needed language).
Each resource was not sufficient by itself.
Apache version 2 applied, so you can freely use the project.

A few screenshots from the latest version:

![screen\_1](./readme_files/screens/screen_1.jpg)
![screen\_2](./readme_files/screens/screen_2.jpg)
![screen\_3](./readme_files/screens/screen_3.jpg)
![screen\_4](./readme_files/screens/screen_4.jpg)
![screen\_5](./readme_files/screens/screen_5.jpg)

Next resources are available:
- [DOU](https://dou.ua/?switch_lang=en)
- [Codeguida](https://codeguida.com/)
- [Pingvin Pro](https://pingvin.pro/)
- [Tokar](https://tokar.ua/)

\*\* Codeguida doesn't have the publication date (even they promised),
that's why their news are always bottom.

## Technologies

The project is very young (early 2020) and I am trying to keep it up to date
with the last Jedi techniques and technologies:

- [~~Lightsabers~~](https://en.wikipedia.org/wiki/Lightsaber).
- [Navigation component](https://developer.android.com/guide/navigation/navigation-getting-started).
- [Room](https://developer.android.com/topic/libraries/architecture/room).
- [Launch screen](https://material.io/design/communication/launch-screen.html#usage).
- [Dagger 2](https://developer.android.com/training/dependency-injection/dagger-android).
- [Robolectric](http://robolectric.org/getting-started/).
- [kotlin](https://developer.android.com/kotlin) and his [coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html).
- [MVVM](https://proandroiddev.com/mvvm-with-kotlin-android-architecture-components-dagger-2-retrofit-and-rxandroid-1a4ebb38c699) with LiveData and secular talks.

## Architecture

Still not finished.

## How to build the project?

Android studio:

1. Clone the project (any tool).
2. Import as a gradle project.
3. Run.

Command line:

1. Clone and navigate to the project's folder.
2. `./gradlew clean assembleDebug`
3. `find ./ -name "*.apk"`

## Useful links

- [GooglePlay](https://play.google.com/store/apps/details?id=com.gmail.yaroslavlancelot.technarium).
- [Privacy policy](https://github.com/YaroslavHavrylovych/technical_news/wiki/privacy_policy).
- [Apache version 2 license](LICENSE)

---------------------------------------
Yaroslav Havrylovych
