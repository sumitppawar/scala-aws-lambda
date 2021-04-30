
How to build ?

`sbt clean assembly`

  

How it works ?

`AWS HTTP <-> AWS Lambda <-> RDS`
 `com.towncrier.Application` class is the entry point.

HTPP API

```js
POST
https://********.execute-api.ap-south-1.amazonaws.com/v1/******
```

Body will `Base64` encrypted JSON

Same `end point` is used to `login` and `events`

  

**Login** 
Request body (encrypt it with `base64`)

```js
{
"mobile": "mobile",
"pwd": "pwd"
}
```
Response will base64 encrypted token 
`tokennbkjewbvewrbvewr`
 
**Events**
Request body (encrypt it with `base64`)
```js 
{"drop": 10,"limit": 10}
```
Response (base64 encrypted json)
```js
{
"id": "Long",
"creatorId":" Long",  
"createdTime":" Long",  
"bannerImagePath":" String",  
"kind":" String",  
"thingDate":" Long",  
"hasTime":" Boolean",  
"title":" String",  
"description":" Option[String]",  
"likeCount":"Long"
}
```