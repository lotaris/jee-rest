# jee-rest

> The REST library helps you deal with HTTP requests in a REST API. It abstracts the serialization, helps mapping errors and contains functions that make response creation easier.

## Bootstrapping the Library in a Maven Project

In a standard Maven multi-module project like we have (EAR / EJB / WAR / JAR), you'll need to setup the dependency as
follows.

The first thing to do is to add the dependency in the `dependencyManagement` section in the `<artifactIdPrefix>/pom.xml`.
You can copy/paste the following dependency definition:

```xml
<!-- Rest -->
<dependency>
	<groupId>com.lotaris.jee</groupId>
	<artifactId>jee-rest</artifactId>
	<version>0.3.1</version>
</dependency>
```

**Note:** Replace `[[ version ]]` by the correct version you need in your project. At each version update, you can then
bump the version in here. This avoids tricky issues where different versions are defined for a same dependency.

Secondly, you'll need to put the dependency in your EJB and EJB-Test modules. (`<artifactIdPrefix>/<artifactIdPrefix>-ejb/pom.xml`
and `<artifactIdPrefix>/<artifactIdPrefix>-ejb-test/pom.xml`). This time, you will add the dependency under
`dependencies`:

```xml
<dependency>
	<groupId>com.lotaris.jee</groupId>
	<artifactId>jee-rest</artifactId>
	<scope>provided</scope>
</dependency>
```

**Note:** You will not specify the version because this already done in the parent `pom.xml` file. This means that the
version is inherited. The `<scope>` is there to manage properly the packaging and the dependencies packaged in the
different jar/war/ear files.

Finally, you need to put the dependency in your WAR and WAR-Test modules. (`<artifactIdPrefix>/<artifactIdPrefix>-war/pom.xml`
and `<artifactIdPrefix>/<artifactIdPrefix>-war-test/pom.xml`). Again, dependency goes under `dependencies`:

```xml
<dependency>
	<groupId>com.lotaris.jee</groupId>
	<artifactId>jee-rest</artifactId>
</dependency>
```

**Note:** No `<version>` for the same reason than before. No `<scope>` because we need to package the dependency in the
war.


## Bootstrapping the Library in the Code

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
  - The `retrieveMappersConfiguration()` method was added by the REST libary and provides a way of mapping exceptions to
    a specific error code.

## Contributing

* [Fork](https://help.github.com/articles/fork-a-repo)
* Create a topic branch - `git checkout -b feature`
* Push to your branch - `git push origin feature`
* Create a [pull request](http://help.github.com/pull-requests/) from your branch

Please add a changelog entry with your name for new features and bug fixes.

## License

**jee-rest** is licensed under the [MIT License](http://opensource.org/licenses/MIT).
See [LICENSE.txt](LICENSE.txt) for the full text.

[AbstractRestApplication]: src/main/java/com/lotaris/jee/rest/AbstractRestApplication.java
[ExceptionMapper]: https://jersey.java.net/apidocs/2.11/jersey/javax/ws/rs/ext/ExceptionMapper.html
[getSingletons]: https://jersey.java.net/apidocs/2.11/jersey/javax/ws/rs/core/Application.html#getSingletons()
[providers]: src/main/java/com/lotaris/jee/rest/providers
[maven-bootstrap-project]: https://github.com/lotaris/maven-settings-bootstrap
