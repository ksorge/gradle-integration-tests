# Gradle integrations tests

*Gradle integration tests* is a plugin which helps in the 
setup of Java integration tests in Gradle.

## Getting started

In order to use the plugin, simply include this snippet of 
code in your `build.gradle`:

```
plugins {
    id 'com.github.luzzif.gradle-integration-tests' version '1.0.0'
}
```

If you don't want to use Gradle's DSL, use the following, more 
verbose form instead:

```
buildscript {
    
    repositories {
        jcenter()
    }
    
    dependencies {
        classpath 'com.github.luzzif:gradle-integration-tests:1.0.0'
    }

}
```

## Usage

Using the plugin is seamless. Simply specify where your integration 
tests are located in relation to the project's src folder.
This is done by writing something like this:

```
integrationTests {
    location = 'foo'
}
```

With this configuration, your integration tests must be located in `src/foo`.

Another possible configuration can be used to specify the JVM-based language in 
which the tests are written, so that the plugin can pick up the correct 
source directories.

For example, with a configuration such as:

```
integrationTests {
    location = 'bar'
    language = 'kotlin'
}
```

The picked up source directory will be located at `src/bar/kotlin`. The default 
language setting is `java`.