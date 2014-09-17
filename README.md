# REST Library

## Introduction

The REST library helps you dealing with HTTP request of the REST API. It abstracts the serialization, helps mapping
errors and contains functions that make response creation easier.


## Bootstrapping the Library

In order to register a REST application, you should extend [AbstractRestApplication][AbstractRestApplication] and 
annotate it `@ApplicationPath`.


## Error Mapping

Jersey uses an [ExceptionMapper][ExceptionMapper] to handle errors gracefully. The REST library helps you to easily
map an error code and location to a given exception.


## Authors

  - [Laurent Prevost][lprevost]
 

[AbstractRestApplication]: src/main/java/com/forbesdigital/jee/rest/AbstractRestApplication.java
[ExceptionMapper]: https://jersey.java.net/apidocs/2.11/jersey/javax/ws/rs/ext/ExceptionMapper.html
[lprevost]: /users/lprevost