# CShop

Open source cloth web application that written in java and spring framework (still under development)

## Available APIs
    [User](#user-getbyid)<br />
    [Tag](#tag-getbyid)<br />
## User (getById)

<b>Request</b>
* Required: none
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
        {
            "id": 1,
            "name": "ROLE_USER"
        }
    ],
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
* Required: none
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
    "roles": [
        {
            "id": 1,
            "name": "ROLE_USER"
        }
    ],
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
* Required: username, password
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
        {
            "id": 1,
            "name": "ROLE_USER"
        }
    ],
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
* Required: none
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
        {
            "id": 1,
            "name": "ROLE_USER"
        }
    ],
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
* Required: none
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
        {
            "id": 1,
            "name": "ROLE_USER"
        }
    ],
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

## Tag (getById)

<b>Request</b>
* Required: none
* Method : GET
* URI : /api/tag/[TAG_ID]

<b>Response</b>
* Status: 200
* Content-Type : xml, json
* Body :

success
```
{
    "id": 1,
    "title": "Black-Ninja",
    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    "keyword": "#blackninja",
    "links": [
        {
            "rel": "self",
            "href": "/api/tag/1"
        },
        {
            "rel": "related",
            "href": "/api/tag/1/related"
        }
    ]
}
```
error
```
{
    "message": "No tag found with id 1"
}
```

## Tag (getAll)

<b>Request</b>
* Required: none
* Method : GET
* URI : /api/tag

<b>Response</b>
* Status: 200
* Content-Type : xml, json
* Body :

success
```
[{
    "id": 1,
    "title": "Black-Ninja",
    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    "keyword": "#blackninja",
    "links": [
        {
            "rel": "self",
            "href": "/api/tag/1"
        },
        {
            "rel": "related",
            "href": "/api/tag/1/related"
        }
    ]
}]
```
error
```
{
    "message": "No tag found"
}
```

## Tag (getRelatedTag)

<b>Request</b>
* Required: none
* Method : GET
* URI : /api/tag/[TAG_ID]/related

<b>Response</b>
* Status: 200
* Content-Type : xml, json
* Body :

success
```
[{
    "id": 2,
    "title": "Yellow-Pokemon",
    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    "keyword": "#yellowpokemon",
    "links": [
        {
            "rel": "self",
            "href": "/api/tag/2"
        },
        {
            "rel": "related",
            "href": "/api/tag/2/related"
        }
    ]
}]
```
error
```
{
    "message": "No tag found with id 1"
}
```

## Tag (add)

<b>Request</b>
* Required: title, description, keyword
* Method : POST
* URI : /api/tag
* Body : 

```
{
    "title": "Black-Ninja",
    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    "keyword": "#blackninja",
}
```

<b>Response</b>
* Status: 201
* Content-Type : xml, json
* Body :

```
{
    "id": 1,
    "title": "Black-Ninja",
    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    "keyword": "#blackninja",
    "links": [
        {
            "rel": "self",
            "href": "/api/tag/1"
        },
        {
            "rel": "related",
            "href": "/api/tag/1/related"
        }
    ]
}
```

## Tag (addRelatedTag)

<b>Request</b>
* Required: none;
* Method : POST
* URI : /api/tag/[TAG_ID]/related/[RELATED_TAG_ID]

<b>Response</b>
* Status: 201
* Content-Type : xml, json
* Body :

```
{
    "id": 1,
    "title": "Black-Ninja",
    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    "keyword": "#blackninja",
    "links": [
        {
            "rel": "self",
            "href": "/api/tag/1"
        },
        {
            "rel": "related",
            "href": "/api/tag/1/related"
        }
    ]
}
```

## Tag (updateById)

<b>Request</b>
* Required: none
* Method : PUT
* URI : /api/tag/[TAG_ID]
* Body :
```
{
    "title": "Black-Ninja",
    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    "keyword": "#blackninja",
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
    "title": "Black-Ninja",
    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    "keyword": "#blackninja",
    "links": [
        {
            "rel": "self",
            "href": "/api/tag/1"
        },
        {
            "rel": "related",
            "href": "/api/tag/1/related"
        }
    ]
}
```
error
```
{
    "message": "No tag found with id 1"
}
```

## Tag (deleteById)

<b>Request</b>
* Required: none
* Method : DELETE
* URI : /api/tag/[TAG_ID]

<b>Response</b>
* Status: 200
* Content-Type : xml, json
* Body :

success

```
{
    "id": 1,
    "title": "Black-Ninja",
    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    "keyword": "#blackninja",
    "links": [
        {
            "rel": "self",
            "href": "/api/tag/1"
        },
        {
            "rel": "related",
            "href": "/api/tag/1/related"
        }
    ]
}
```
error
```
{
    "message": "No tag found with id 1"
}
```

## Tag (deleteRelatedTag)

<b>Request</b>
* Required: none
* Method : DELETE
* URI : /api/tag/[TAG_ID]/related/[RELATED_TAG_ID]

<b>Response</b>
* Status: 200
* Content-Type : xml, json
* Body :

success

```
{
    "id": 1,
    "title": "Black-Ninja",
    "description": "Lorem ipsum dolor sit amet, consectetur adipiscing elit",
    "keyword": "#blackninja",
    "links": [
        {
            "rel": "self",
            "href": "/api/tag/1"
        },
        {
            "rel": "related",
            "href": "/api/tag/1/related"
        }
    ]
}
```
error
```
{
    "message": "No tag found with id 1"
}
```
