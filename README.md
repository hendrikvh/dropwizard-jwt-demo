# Dropwizard JWT Demo
Simple app which demonstrates how to issue and validate JWT tokens using dropwizard, related to this [StackOverflow question](https://stackoverflow.com/questions/46268300/using-a-jwt-token-with-dropwizard-i-already-have-db-auth-but-am-confused-about).
## Usage:
`mvn clean install && java -jar target/dropwizard-jwt-0.1.jar server dropwizard/config.yml`

## Resources
### Login
Log in using basic auth and obtain a JWT token.

#### Example request:
`curl -u RoleOneUser:RoleOnePass -X GET --header 'Accept: application/json' 'http://localhost:8080/auth/login'`

Try it without auth:
`curl -X GET --header 'Accept: application/json' 'http://localhost:8080/auth/login'`

#### Example response:

```
{
  "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOiJSb2xlT25lIiwidXNlciI6IlJvbGVPbmVVc2VyIiwiaWF0IjoxNTA2MDI3OTMxLCJqdGkiOiJaV2RrSXAzLW43UXEyQjh1aTN6M2FRIn0.LImJsleGZLVVnb0znnenxZJvUH-XVdtW-abCqv68l-I"
}
```

Go decode the token on [jwt.io](https://jwt.io) to see the contents.

### ProtectedResourceOne:
Will accept the JWT obtained from the login above and echo some values from the JWT back.

#### Example request:
`curl -X GET --header 'Accept: application/json' --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOiJSb2xlT25lIiwidXNlciI6IlJvbGVPbmUiLCJpYXQiOjE1MDYwMjYxNDYsImp0aSI6IlhwWU5ocHFoaUZ1b1l3anZ0REE4OEEifQ.k7-kx9hkGNxv-ECtSr8OTXr-HKe26evZvo2OQhXmb8A' 'http://localhost:8080/protectedResourceOne'`

#### Example response:

```
{
  "role": "RoleOne",
  "username": "RoleOne"
}
```

### ProtectedResourceTwo:
Will not accept the JWT obtained from the login above, because it expects a different role.

#### Example request:
`curl -X GET --header 'Accept: application/json' --header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiIxIiwicm9sZXMiOiJSb2xlT25lIiwidXNlciI6IlJvbGVPbmUiLCJpYXQiOjE1MDYwMjYxNDYsImp0aSI6IlhwWU5ocHFoaUZ1b1l3anZ0REE4OEEifQ.k7-kx9hkGNxv-ECtSr8OTXr-HKe26evZvo2OQhXmb8A' 'http://localhost:8080/protectedResourceTwo'`

#### Example response:

```
{
  "code": 403,
  "message": "User not authorized."
}
```