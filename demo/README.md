
# Guide to galaxy

## Index
1. [Introduction](#introduction)
2. [Prerequisite](#prerequisite)
3. [Assumptions](#assumptions)
4. [Steps to run](#steps-to-run)

## Introduction

This is a simple __spring boot__ web application that determines ```answers``` based on the _input data_ and _questions_ provided in the _text file_ format.

The application uses ```thymeleaf``` as view template. 

The application can be bundled into a jar and can be packaged into a docker container for ```scalability``` and ```portablility```.

This also has registers __boot-admin__ to access the metrics with the management endpoints.

The API is documented using swagger. Swagger UI is enabled in this project. 

## Prerequisite

These are the reqired artifacts to run the application,

1. IDE
2. Java 1.8
3. Maven
4. Docker
5. Web browser


## Assumptions

The assumption is based from the __point of view__ of _galactic string data_. Thus classifiying them as the following, 

### Direct input
The input where in galacgic data is extracted directly is direct input

_eg._ ```pish is X```. It involves mapping of roman numerals against the galactic value.
1. Has to be 3 split with ```space``` seperator
2. ```" is "``` is expected in the middle
3. Galatic string before ```" is "```
4. Galatic string can be composed of _numbers, string_
5. Roman numeral after ```" is "``` is expected

### Indirect input
The input wherein it invoves galactic data and metal along with the credit. Here the galactic data needs the help of _direct input_ to resolve the information. 

_eg._ glob glob Silver is 34 Credits
1. Ends with _credits_
2. Credit value is before the text ```" credits"```
3. Value is a _number_
4. _Separator_ ```space``` should be space

### Direct question
This is the direct question where in the computaion is directly based on the _galactic data values_ obtained in the previous steps

_eg._ glob prok Gold is 57800 Credits

1. Should start with ```"how much is "```
2. End with ```" ?"```
3. Has only _galactic_ string
4. Separated by ```space```

### Indirect question
The indirect question is where the computation involves the usage of _galactic data_ and the _metal data_ obtained from the previous steps

_eg._ how many Credits is glob prok Silver ?

1. Should start with ```"how many Credits is "```
2. Ends with ```" ?"```
3. Has one or many _galactic_ string and _one metal_ string
4. Spearated by ```space```

### Irrevelant question
This is a kind of question where it is irrelevent to the context

_eg._ how much woodchuck chuck if a woodchuck could chuck wood?

1. Should end with question mark ```?```
2. Should not contain ```"how much is "``` and ```"how many Credits is "```

### Roman to deciamal convertion
1. The following scenario has not been handled,

_for eg_ ```IIIV``` returns ```6``` but the actual roman no for 6 is ```VI```, still it would work.

## Steps to run
Please follow the app setup dock for screenshots and detailed steps

### Building the application
Use maven to build the application,
```
mvn package 
```
the ```jar``` will be placed in the ```target``` folder on successful image build  

### Running the application in CLI
Run the application using,
```sh
mvn spring-boot:run
```
Access the application at http://localhost:8080/gtog/

### Building the docker image
Use docker to build the image,
```
docker build -t gtog . 
```
To show the images,
```
docker images
```
To run the image,
```
docker run -p 8080:8080 <imageid>
```

### Swagger
To access swagger at http://localhost:8080/gtog/swagger-ui.html#/file-upload-controller

### Admin

Run the spring boot admin to view metrics and data. The spring boot admin in behind the security. Use ```admin``` and ```secret``` to login.
Once the application is registerd, they the ```gtog``` will be available at

http://localhost:1111
