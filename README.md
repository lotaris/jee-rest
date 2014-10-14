# REST Library

## Requirements

| Dependency | Version | Description |
| ---------- | ------- | ----------- |
| NodeJS | 0.10+ | |
| grunt.cli | 0.1+ | This is a command line wrapper around grunt itself. This allows you to have a dedicated version of Grunt for each project you manage and only one command line entry point for that command tool. |

## How to setup your environment

You need to be sure that you have already installed `maven-settings-bootstrap` module. Take a look to that [page][maven-bootstrap-project] to do the setup.

Before running the `msb` command, be sure that your `~/.m2/msb.yml` file contains the following variables:

```yml
vars:
	nexus_aws_developer_password: <nexus aws developer password. No password? Ask DevOps to get it>
```

Then, you have to run the commands below:

```bash
$> cd <projectFolder>
$> msb
```

In fact, to run `Maven` commands correctly on those projects, you need to be sure that the correct `settings.xml` is used. By default, `Maven` is looking for a `settings.xml` into `.m2` directory present in your `home_dir`. Then, to override this behavior, `Maven` command can take the argument `-s<pathToTheSettingsXmlFile>`. Then, if you are using different tool or command line to run the `Maven` goals, you need to ensure that the correct `settings.xml` is used (the one present in each project).

## Ready to compile something

Follow the next steps:

1. Open the [Mock Server Library][project-repo] in Netbeans
2. You can also open the sub-modules
3. Right click on the project
4. Go to Custom menu
5. Compile

The other goals are used by the `DevOps` team to be able to release the library on `Nexus`.

## Introduction

The REST library helps you deal with HTTP requests in a REST API. It abstracts the serialization, helps mapping
errors and contains functions that make response creation easier.


## Bootstrapping the Library in a Maven Project

In a standard Maven multi-module project like we have (EAR / EJB / WAR / JAR), you'll need to setup the dependency as
follows.

The first thing to do is to add the dependency in the `dependencyManagement` section in the `<artifactIdPrefix>/pom.xml`.
You can copy/paste the following dependency definition:

```xml
<!-- Rest -->
<dependency>
	<groupId>com.forbesdigital.jee</groupId>
	<artifactId>rest</artifactId>
	<version>[[ version ]]</version>
</dependency>
```

**Note:** Replace `[[ version ]]` by the correct version you need in your project. At each version update, you can then
bump the version in here. This avoids tricky issues where different versions are defined for a same dependency.

Secondly, you'll need to put the dependency in your EJB and EJB-Test modules. (`<artifactIdPrefix>/<artifactIdPrefix>-ejb/pom.xml`
and `<artifactIdPrefix>/<artifactIdPrefix>-ejb-test/pom.xml`). This time, you will add the dependency under
`dependencies`:

```xml
<dependency>
	<groupId>com.forbesdigital.jee</groupId>
	<artifactId>rest</artifactId>
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
	<groupId>com.forbesdigital.jee</groupId>
	<artifactId>rest</artifactId>
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

## Contribute

If you want to propose improvements to that library, follow the [instructions on confluence][confluence]

## Authors

  - [Laurent Prevost][lprevost]
  - [Simon Oulevay][soulevay]


[AbstractRestApplication]: src/main/java/com/forbesdigital/jee/rest/AbstractRestApplication.java
[ExceptionMapper]: https://jersey.java.net/apidocs/2.11/jersey/javax/ws/rs/ext/ExceptionMapper.html
[getSingletons]: https://jersey.java.net/apidocs/2.11/jersey/javax/ws/rs/core/Application.html#getSingletons()
[providers]: src/main/java/com/forbesdigital/jee/rest/providers
[lprevost]: /users/lprevost
[soulevay]: /users/soulevay
[maven-bootstrap-project]: https://github.com/lotaris/maven-settings-bootstrap
[project-repo]: http://stash.aws.onlotaris.com/projects/LIB/repos/fd-jee-rest/browse
[confluence]: https://lotaris.atlassian.net/wiki/display/FDW/How+to+Work+with+Libraries
