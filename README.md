# wm-user-api

## Instructions

### Setup Guidelines
1. clone the repository using the code below

    >```git clone https://github.com/The-Nigeria-Development-Foundation/wealthmarket-core-api.git```
1. create a branch for your module with the "feature-" prefix e.g "feature-user-controller" on your system and on the repository online
1. checkout to your newly created branch
1. create your application-dev.properties file using the application-test.properties as a sample
1. edit the application-dev.properties file by replacing port,username,db and password to your own mysql connection and database details
1. create your sql database using the database name from 5. above e.g "wm-core-db"

---

### Testing and Usage Guidelines

1. The core API on the live server can be accessed at `http://104.248.162.83:8080/core-api`. All endpoints are relative to this base url for example authentication endpoint is given as `auth/login` so the full link is `http://104.248.162.83:8080/core-api/auth/login`

1. You can check implemented API endpoints with the swagger UI configured in the app with [http://104.248.162.83:8080/swagger-ui.html](http://104.248.162.83:8080/swagger-ui.html)

1. To use the API endpoints from the app in a front end application, we have added a list of urls in the `cors.url` property in your `application-staging.properties` to allow CORS access for your application. please make sure your app runs on any of the ports in the link below

    > `cors.url= http://localhost:4200,http://localhost:4300,http://localhost:4211`

1. All the endpoints except error, register, api-docs, swagger-ui, and auth are secured so you need to supply an authorization token to access them. register first with `http://104.248.162.83:8080/core-api/users/register` endpoint and login with `http://104.248.162.83:8080/core-api/auth/login` to get your token.

1. Set the token as a header using `Authorization` as the key and `Bearer {token}` as the value, see example below

    getting the token
    ![getting token](https://github.com/The-Nigeria-Development-Foundation/wealthmarket-core-api/blob/development/screenshot/authsample.png)

    setting the token in the header
    ![getting token](https://github.com/The-Nigeria-Development-Foundation/wealthmarket-core-api/blob/development/screenshot/AuthHeader%20sample.png)
---

### Coding Guidelines
1. Always pull and merge the development branch into your branch to get the code updates before pushing to your branch.
1. Create classes and interfaces using sentence case (capitalize each word), see examples below

    - >for **User** Entity class  ```public class User```
    - >for **UserRepository** interface ```public interface UserRepository```
    
1. Create variables and methods using camel case (first word is in small letters while the remaining words have the first letter as upper case), see examples below

    - >for **variables**   ```String name, String userId```
    - >for **methods**    ```public BaseResponse activateUser(long userId) {}```

1. All API end points should use the BaseResponse object as thier response payload, set the status code, description and data where necessary. errors gotten from exceptions would be put in the errors object during runtime, so you dont need to worry about that. see example response from getting the user types below

    ```
    {
        "statusCode": 200,
        "description": "user types found succesfully",
        "data": [
            {
            "id": 1,
            "name": "Member"
            },
            {
            "id": 2,
            "name": "Guest"
            }
        ],
        "errors": null
    }

1. set `HttpServletResponse.SC_OK (200)` as the statusCode in the response payload for all successfull and `HttpServletResponse.SC_BAD_REQUEST (400)` for all failed operations. only use `HttpServletResponse.SC_INTERNAL_SERVER_ERROR (500)` when you try to catch an internal server error during an operation.