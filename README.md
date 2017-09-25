## Project Structure

Editable Profile is a web application to demonstrate validation and role based json serialization on backend.

Frontend validation is intentionally skipped for fields other than `dispayName`. Other fields are validated in backend. `displayName` is only validated for empty value at frontend. Sanitization and length validations for this field is also done at backend.


## Implementation Details

Backend is implemented with Spring Boot. Spring Data and Security dependencies are used. It is also using H2 database. Using [jsoup](https://jsoup.org/) for sanitizing inputs against xss attacks. Backend codes are located under `src` folder.

Put the `cities.json` to resource folder. It's loaded by spring boot during initialization and storing it to database. Other static datas (gender, religion etc) are added as static data into `DataUtils`.

Frontend is implemented with [React](https://facebook.github.io/react/). It is created with facebook's starter kit [Create react app](https://github.com/facebookincubator/create-react-app) . Used [axios](https://github.com/mzabriskie/axios) library for api calls. For UI components I used [Ant Design](https://ant.design/). Frontend codes are under `frontend` folder.

User Profile pictures are loaded randomly from http://lorempixel.com . It's not tested how robust and consistent it is.

## Build

This project requires `Java 8` and [maven](http://maven.apache.org/) for building it.

Frontend is already build in this branch and added to the folder structure of spring boot project.

Execute following command to start build process
```
mvn package
```

## Running

after build is completed a jar file be generated under `target` folder

application can be started with following command
```
java -jar target\editable-profile-0.0.1-SNAPSHOT.jar
```

Open http://localhost:8080 in your browser.

## Missing parts

Profile Picture upload part is not implemented.

Frontend does not have automated tests.

The ui works like a page navigation flow, but actually there is no navigation. It's simply updating some part of page with different component. I didn't use react-router because there were no heavy page navigation load for this app.

Also didn't use redux since there are not many components to share same state.

## Issue

Added validationmessages.properties file incase i18n would be required for validation error messages. Even those messages are sent succesfully from server when running the app on STS or through `mvn spring-boot:run`, when the app is executed from the build jar file, these messages get lost and only received keys instead of values.

e.g. instead of receiving `Must be in between 2 and 256 characters`, I was seeing
`{error.field.size.limit}` . Couldn't figure out why it's behaving like that. And due to limited tme, I removed my custom error messages from my validation annotations go with the defaults.
The problem can be reproducable by checking out following commit

 `e130b489083b5dab8aa6ff94739fbe28e054f6b4`

PS: that commit does not containt prebuild frontend codes. It's building javascript also, but you see any failure regarding build process, please see the readme of master branch to check build each part separately

## Additional
If you would like to run the application in development mode 

To load frontend dependencies
```
npm install
```

run the spring boot with following command
```
mvn spring-boot:run
```

then run the frontend server
```
npm start
```

This will open http://localhost:9876 in your browser.

With this mode, frontend will be served by webpack dev server, and all the backend calls from UI actions will be proxied to spring boot (which will running at http://localhost:8080)