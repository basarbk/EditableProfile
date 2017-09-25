## Project Structure

Editable Profile is a web application to demonstrate validation and role based json serialization on backend.

Frontend validation is intentionally skipped for fields other than `dispayName`. Other fields are validated in backend.


## Implementation Details

Backend is implemented with Spring Boot. Spring Data and Security dependencies are used. It is also using H2 database. Also using [jsoup](https://jsoup.org/) for sanitizing inputs against xss attacks. Backend codes are located under `src` folder.


Frontend is implemented with [React](https://facebook.github.io/react/). It is created with facebook's starter kit [Create react app](https://github.com/facebookincubator/create-react-app) . Used [axios](https://github.com/mzabriskie/axios) library for api calls. For UI components I used [Ant Design](https://ant.design/). Frontend codes are under `frontend` folder.

## Build & Run

This project requires `Java 8` and [maven](http://maven.apache.org/) for building it.

Frontend javascript codes are build with npm scripts. Maven is taking care of it with [frontend-maven-plugin](https://github.com/eirslett/frontend-maven-plugin)

>Frontend inclusive build would take longer time. If you don't care about npm installation and build steps, switch to the branch `frontend-prebuild`.

Execute following command to start build process
```
mvn package
```

jar file to be located under `target` folder

application can be started with following command
```
java -jar target\editable-profile-0.0.1-SNAPSHOT.jar
```

## Missing parts

Profile Picture upload part is not implemented.

Frontend does not have automated tests.

The ui works like a page navigation flow, but actually there is no navigation. It's simply updating some part of page with different component. I didn't use react-router because there were no heavy page navigation load for this app.

Also didn't use redux since there are not many components to share same state.

## Additional
If you would like to run the application in development mode you'll need to install `npm` which is coming with [nodejs](https://nodejs.org/en/).

To load frontend dependencies
```
npm install
```

run the spring boot with following command
```
mvn spring-boot:run -Pdev
```

then run the frontend server
```
npm start
```

This will open http://localhost:9876 in your browser.

With this mode, frontend will be served by webpack dev server, and all the backend calls from UI actions will be proxied to spring boot (which will running at http://localhost:8080)