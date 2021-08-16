# Runnable Workflows Demonstrating the [cicirello/jacoco-badge-generator](https://github.com/cicirello/jacoco-badge-generator) GitHub Action

The purpose of this repository includes:
* providing a simple example of configuring the jacoco-maven-plugin,
* providing a simple example of running jacoco in GitHub Actions as part of a build, and
* providing runnable example workflows using the 
  [cicirello/jacoco-badge-generator](https://github.com/cicirello/jacoco-badge-generator) GitHub 
  Action.
  
## Why is this Repository a Template?

I made this repository a template to make it easy for someone
to use it to get started on a project. To use this to start a 
new project, click the "Use this Template" button. Depending on
which starter workflow you want to use, you might want to select 
the option to include all branches. If you are not sure, then
for now include all branches. You can always delete unneeded
branches afterwards.

You are also free to fork this repository if you want (e.g.,
if you want to contribute to it with a pull request or for some 
other reason).

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

## Where are the Test Coverage Reports?

Assuming you ran the build locally (see above), the build would have created
a `target` directory (Maven's default). The target directory is in the `.gitignore`
to ensure we don't commit any of that to the repository. 
You will find the JaCoCo reports at the following path: `target/site/jacoco`. 
Double-click the `index.html` file you see there to inspect the html version of 
the coverage report, which is most useful for a human viewer. It is the `jacoco.csv`
file, however, that 
the [cicirello/jacoco-badge-generator](https://github.com/cicirello/jacoco-badge-generator) 
GitHub Action uses, which is far simpler to parse.

## Summary of What the Workflows Do

If this section is still blank when you read this, then the workflows aren't
here yet. Coming soon.

