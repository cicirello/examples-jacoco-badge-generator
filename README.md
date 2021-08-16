# examples-jacoco-badge-generator

The purpose of this repository includes:
* providing a simple example of configuring the jacoco-maven-plugin,
* providing a simple example of running jacoco in GitHub Actions as part of a build, and
* providing runnable example workflows using the 
  [cicirello/jacoco-badge-generator](https://github.com/cicirello/jacoco-badge-generator) GitHub 
  Action.

## The Maven pom.xml

The `pom.xml` in this repository builds a very simple program, a weird variation
of Hello World, with convoluted logic to artificially create a test coverage example.
If you are new to using the `jacoco-maven-plugin` then take a look at how it
is configured. It will generate all of the variations of the JaCoCo coverage
report ("html", "csv", and "xml"). It is configured to run during the test phase.

## Building Locally

To build and test locally, run either of the following command at the root
of the repository:

```Shell
mvn clean test
```
or

```Shell
mvn clean package
```

The latter will generate a jar, while the former will not. The tests
and test coverage reports run in both cases. Since we are really focused on
test coverage here, we don't really need the jar.

## Where are the Test Coverage Reports

Assuming you ran the build locally (see above), the build would have created
a `target` directory (Maven's default). The target directory is in the `.gitignore`
to ensure we don't commit any of that to the repository. 
You will find the JaCoCo reports at the following path: `target/site/jacoco`. 
Double-click the `index.html` file you see there to inspect the html version of 
the coverage report, which is most useful for a human viewer. It is the `jacoco.csv`
file, however, that 
the [cicirello/jacoco-badge-generator](https://github.com/cicirello/jacoco-badge-generator) 
GitHub Action uses, which is far simpler to parse.

