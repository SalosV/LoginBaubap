
# CRAFT BEER


There are several things to consider when designing and developing an Android app. These include architecture, class hierarchy, package structure and the tech stack.

I approached this application with the following knowledge in mind:

- Package by Feature
- Multi-package apps
- Clean architecture
- MVVM
- Dependency injection with Dagger hilt
- Material design
- Style code



## Demo


![myfile](https://drive.google.com/uc?export=view&id=1ePBBf-9WAJPz_hxNkyhWQ08hyQYSNV2z)

## Features

- Login


## Tech Stack

- LiveData
- ViewModel
- Dagger hilt
- Coroutines
- Lottie
- Robolectric
- Mockito
- JUnit4
- Detekt

## Documentation

**Package by Feature** 

![image](https://drive.google.com/uc?export=view&id=1iKJ-4cCXC5VXVr264gHRyeEhvsaN-uTi)

This project is organized in a package by feature structure. Everything that’s related to a feature, and only to that feature, is stored inside the same package. Code shared by two or more features is stored in separate common packages. This type of package organization has a few advantages:

- Just by looking at the package structure, you easily get a feeling for what the app does. Some people also like to call this a screaming architecture — hence the awful “screaming” joke attempt earlier.
- You end up with packages that not only have high cohesion, they’re also either loosely coupled or completely decoupled from one another. Cohesion and coupling are two very important metrics in software development that you should always consider.


**Clean architecture**

Each of these entities is in charge of certain responsibilities, which are handled in isolation. All them are interconnected through interfaces, which allows you to achieve the necessary abstraction between them.

Here’s a bit about each layer:

- Presentation: This layer’s duties consist of managing events caused by user interactions and rendering the information coming from the domain layer. You’ll be using the well-known View, ViewModel (MVVM) architecture pattern. This entity “sees” the domain layer.
- Domain: This layer is in charge of the application business logic. It’s built upon use cases and repositories — see the Repository Pattern for more information. This entity only contains Kotlin code, so testing consists of unit tests. This layer represents the most inner entity, and thus it doesn’t “see” any layer other but itself.
- Data: This layer provides data to the application (data sources). You’ll be using Retrofit for service queries. This layer “sees” the domain layer.
Using clean architectures lets you make your code more SOLID. This makes applications more flexible and scalable when implementing new functionality, and it makes testing easier.
