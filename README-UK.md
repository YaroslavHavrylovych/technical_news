[![Build Status](https://travis-ci.com/YaroslavHavrylovych/technical_news.svg?branch=development)](https://travis-ci.com/github/YaroslavHavrylovych/technical_news)
[![CodeFactor](https://www.codefactor.io/repository/github/yaroslavhavrylovych/technical_news/badge)](https://www.codefactor.io/repository/github/yaroslavhavrylovych/technical_news)
[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)

# Технаріум

[![Technarium](./readme_files/technarium_logo.png)](https://play.google.com/store/apps/details?id=com.gmail.yaroslavlancelot.technarium)

*Читати [English](README.md)*

## Коротке знайомство

**Технаріум** - збірник новин (\*не тільки) із технічних ресурсів які мають від 50% українськомовного матеріалу.

\*Не тільки - у додаток до новин і статей присутні розділи пошуку роботи і технічних
подій в Україні (обидва запозичені з [DOU](https://dou.ua/?switch_lang=uk)).

Автор, або абстрактний я у вакуумі, декілька разів на день мусив перемикатись
між кількома ресурсами, щоб читати матеріал зручною мовою. Кожен ресурс окремо
не покривав усе необхідне, а далі як в тумані (с. Альф). 
Розповсюджується за ліцензією Apache 2, тому користуйтесь на здоров'я.

Декілька знімків екран із останньої версії:

![screen\_1](./readme_files/screens/screen_1.jpg)
![screen\_2](./readme_files/screens/screen_2.jpg)

На даний момент присутні наступні ресурси:
- [DOU](https://dou.ua/?switch_lang=uk)
- [Codeguida](https://codeguida.com/)
- [Pingvin Pro](https://pingvin.pro/)
- [Tokar](https://tokar.ua/)

\*\* Codeguida не надсилають (хоч обіцяли) дату публікації новини,
тому їх новини відображаються в кінці списку.

## Технології

Проєкт версії 2 є новим (кінець 2021) і я намагаюся використовувати останні джедаївських
техніки і технології:

- [~~Світлові мечі~~](https://uk.wikipedia.org/wiki/%D0%A1%D0%B2%D1%96%D1%82%D0%BB%D0%BE%D0%B2%D0%B8%D0%B9_%D0%BC%D0%B5%D1%87).
- [Навігаційний компонент](https://developer.android.com/guide/navigation/navigation-getting-started).
- [Хіль](https://developer.android.com/training/dependency-injection/hilt-android).
- [котлін](https://developer.android.com/kotlin) і його [корутини](https://kotlinlang.org/docs/reference/coroutines-overview.html).

## Архітектура

Розділ ще в написанні.

## Як зібрати проєкт?

Андроїд Студія:

1. Клонувати проєкт будь-яким зручним методом.
2. Імпортувати як gradle проєкт.
3. Запустити.

Командний рядок:

1. Імпортувати як зручно і перейти у теку з проєктом.
2. `./gradlew clean assembleDebug`
3. `find ./ -name "*.apk"`

## Корисні посилання

- [Гугл плей](https://play.google.com/store/apps/details?id=com.gmail.yaroslavlancelot.technarium).
- [Політика конфіденційності проєкту (англ)](https://github.com/YaroslavHavrylovych/technical_news/wiki/privacy_policy).
- [Ліцензія Apache версія 2 (англ)](LICENSE)

---------------------------------------
Ярослав Гаврилович
