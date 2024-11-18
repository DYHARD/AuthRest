To Start the application
mvn spring-boot:run

To signUp you can use postman or use the following cUrl
curl --location 'http://localhost:8080/auth/signup' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=77ED898B11BA1876FCEA8BBED1F33C61' \
--data-raw '{"email":"test@example.com", "password":"password"}'


To SignIn , use the credentials you used while signing Up
curl --location 'http://localhost:8080/auth/signin' \
--header 'Content-Type: application/json' \
--header 'Cookie: JSESSIONID=77ED898B11BA1876FCEA8BBED1F33C61' \
--data-raw '{"email":"test@example.com", "password":"password"}'

This will return a JWT token, which we will be using for refreshing/revoking or when we want to hit some other end point

To refresh the authorisation token use following cUrl
curl -X POST -H "Authorization: Bearer <your_token>" http://localhost:8080/auth/refresh


To Revoke the authorisation token
curl -X POST -H "Authorization: Bearer <your_token>" http://localhost:8080/auth/revoke






