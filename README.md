# REST Library

## Introduction

The REST library helps you dealing with HTTP request of the REST API. It abstracts the serialization, helps mapping
errors and contains functions that make response creation easier.


## Bootstrapping the Library

In order to register a REST application, you should extend [AbstractRestApplication][AbstractRestApplication] and 
annotate it with `@ApplicationPath`.


## Error Mapping

Jersey uses an [ExceptionMapper][ExceptionMapper] to handle errors gracefully. The REST library helps you to easily
map an error code and location to a given exception. There is already [a bunch][providers], but you can also add your
own. Note that even though these providers exist in the library, you still need to register them as shown below.


## Example

Let's say you're about to create a new API. Your REST application class could look something like this:

```java
@ApplicationPath(RestPaths.APPLICATION_API)
public class MyRestApplication extends AbstractRestApplication {

	@Override
	protected String[] getPackages() {
		return new String[] { getClass().getPackage().getName() };
	}
	
	@Override
	public Set<Object> getSingletons() {
		final Set<Object> singletons = new HashSet<>(2);
		singletons.add(new JacksonJsonProvider());
		return singletons;
	}

	@Override
	protected MapperMappingDefinition[] retrieveMappersConfiguration() {
		return map(
		
			// Standard exception mappers bound to API Error codes from App
			def(CatchAllExceptionMapper.class, EApiErrorCodes.SERVER_UNEXPECTED),
			def(EOFExceptionMapper.class, EApiErrorCodes.REQUEST_END_OF_INPUT),
			def(JsonMappingExceptionMapper.class, EApiErrorCodes.REQUEST_BAD_JSON_VALUE_TYPE),
			def(JsonParseExceptionMapper.class, EApiErrorCodes.REQUEST_INVALID_JSON),
			def(NotAcceptableExceptionMapper.class, EApiErrorCodes.REQUEST_UNACCEPTABLE_MEDIA_TYPE),
			def(NotFoundExceptionMapper.class, EApiErrorCodes.REQUEST_RESOURCE_NOT_FOUND),
			
			// Custom exception mapper bound to API Error codes from App
			def(PermissionExceptionMapper.class, EApiErrorCodes.ACCESS_REQUIRED_PERMISSION_MISSING),
			def(UniqueStringExceptionMapper.class, EApiErrorCodes.SERVER_KEY_GENERATION_FAILED)
		);
	}
}
```

There are several noteworthy parts:

  - As decribed above, using `@ApplicationPath` does (among other things) set up the wiring so all resource classes
    that are annotated with `@Path` are automatically registered in the application container as REST resources.
  - In the [getSingletons()][getSingletons] method, you can set up your root resources, providers and feature instances
    as you would normally with Jersey.
  - The `retrieveMappersConfiguration()` method was added by the REST libary and provides a way of adding exceptions to
    a specific error code.


## Authors

  - [Laurent Prevost][lprevost]
  - [Simon Oulevay][soulevay]
 

[AbstractRestApplication]: src/main/java/com/forbesdigital/jee/rest/AbstractRestApplication.java
[ExceptionMapper]: https://jersey.java.net/apidocs/2.11/jersey/javax/ws/rs/ext/ExceptionMapper.html
[getSingletons]: https://jersey.java.net/apidocs/2.11/jersey/javax/ws/rs/core/Application.html#getSingletons()
[providers]: src/main/java/com/forbesdigital/jee/rest/providers
[lprevost]: /users/lprevost
[soulevay]: /users/soulevay