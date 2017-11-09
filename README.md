# ecloth

Ecloth is a personal project. An ecommerce website for clothings.

## Project Structure
```
    .
    ├── core
    |     ├── configuration  
    |     ├── main.java 
    |     └── pom.xml
    ├── data
    |     ├── exception  
    |     ├── mapper  
    |     ├── service
    |     ├── helper
    |     └── pom.xml
    ├── model
    |     ├── entity  
    |     ├── resource
    |     └── pom.xml
    ├── web
    |     ├── controller  
    |     ├── interceptor
    |     └── pom.xml
    ├── webservice
    |     ├── item  
    |     ├── user
    |     └── pom.xml
    ├── README.md
    └── pom.xml
```
## Available APIs
    * User
    * Item
    * Attribute
    * Category
    * Tag

## User (getById)

<b>Request</b>
* Requirement: none
* Method : GET
* URI : /api/user/[USER_ID]

<b>Response</b>
* Status: 200
* Content-Type : xml, json
* Body :

success
```
{
    "id": 1,
    "username": "kelvindatu",
    "password": "123",
    "links": [
        {
            "rel": "self",
            "href": "/api/tag/1"
        }
    ]
}
```
error
```
{
    "message": "No user found with id 1"
}
```

## User (getAll)

<b>Request</b>
* Requirement: none
* Method : GET
* URI : /api/user

<b>Response</b>
* Status: 200
* Content-Type : xml, json
* Body :

success
```
[{
    "id": 1,
    "username": "kelvindatu",
    "password": "123",
    "links": [
        {
            "rel": "self",
            "href": "/api/tag/1"
        }
    ]
}]
```
error
```
{
    "message": "No user found"
}
```

## User (add)

<b>Request</b>
* Requirement: body
* Method : POST
* URI : /api/user
* Body : 

```
{
    "username": "ralenmandap",
    "password": "123"
}
```

<b>Response</b>
* Status: 201
* Content-Type : xml, json
* Body :

```
{
    "id": 2,
    "username": "ralenmandap",
    "password": "123",
    "links": [
        {
            "rel": "self",
            "href": "/api/tag/2"
        }
    ]
}
```

## User (updateById)

<b>Request</b>
* Requirement: body
* Method : PUT
* URI : /api/user/[USER_ID]
* Body :
```
{
    "username": "kelvindatu",
    "password": "456",
}
```

<b>Response</b>
* Status: 200
* Content-Type : xml, json
* Body :

success

```
{
    "id": 1,
    "username": "kelvindatu",
    "password": "456",
    "links": [
        {
            "rel": "self",
            "href": "/api/tag/1"
        }
    ]
}
```
error
```
{
    "message": "No user found with id 1"
}
```

## User (deleteById)

<b>Request</b>
* Requirement: none
* Method : DELETE
* URI : /api/user/[USER_ID]

<b>Response</b>
* Status: 200
* Content-Type : xml, json
* Body :

success

```
{
    "id": 1,
    "username": "kelvindatu",
    "password": "123",
    "links": [
        {
            "rel": "self",
            "href": "/api/tag/1"
        }
    ]
}
```
error
```
{
    "message": "No user found with id 1"
}
```

[**Item**](readme/department.md)
* [GET] getById
* [GET] getAll
* [POST] add
* [PUT] updateById
* [DELETE] deleteById
