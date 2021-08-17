# Runnable Workflows Demonstrating the [cicirello/jacoco-badge-generator](https://github.com/cicirello/jacoco-badge-generator) GitHub Action

The purpose of this repository includes:
* providing a simple example of configuring the jacoco-maven-plugin,
* providing a simple example of running jacoco in GitHub Actions as part of a build, and
* providing runnable example workflows using the 
  [cicirello/jacoco-badge-generator](https://github.com/cicirello/jacoco-badge-generator) GitHub 
  Action.
  
## Why is this Repository a Template?

I made this repository a template to make it easy for someone
to use it to get started on a new project. To use this to start a 
new project, click the "Use this Template" button. Depending on
which starter workflow you want to use, you might want to select 
the option to include all branches. If you are not sure, then
for now include all branches. You can always delete unneeded
branches afterwards.

You are also free to fork this repository if you want (e.g.,
if you want to contribute to it with a pull request or for some 
other reason, such as just trying out the workflows).

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

The [.github/workflows](.github/workflows) directory contains several
example workflows that use
the [cicirello/jacoco-badge-generator](https://github.com/cicirello/jacoco-badge-generator) 
GitHub Action. If you are new to GitHub workflows, they must be in that directory in order 
to run. Each workflow also contains comments explaining all of the steps of each. Here is 
a brief summary of each.

### Basic case: [.github/workflows/build.yml](.github/workflows/build.yml)

This is the basic use-case, which generates both the coverage
and branches coverage badges storing them in the default directory,
.github/badges. The workflow runs on pushes, pull requests, and can
be run manually (the workflow_dispatch event). Here are the badges
it produces:

![coverage](.github/badges/jacoco.svg)
![branches coverage](.github/badges/branches.svg)

The above was inserted into this README with the following
markdown:

```markdown
![coverage](.github/badges/jacoco.svg)
![branches coverage](.github/badges/branches.svg)
```

If your main branch is protected and includes either required checks or
required reviews, then the push step will fail. To get around this, one 
option is to see GitHub's documentation on using a personal access token (PAT)
with the actions/checkout step. This is not at all an issue if your branch 
is protected, but doesn't have required checks or reviews. In that case, the 
default GITHUB_TOKEN is sufficient for the push, and a PAT is not needed.

We personally do not like the use of a PAT for circumventing required
checks or reviews. After all, if you have put those in place, you have done so
for a reason. And once you have the PAT in place in the repository, anyone
with write access to the repository can then use it in additional workflows
to bypass those protections.  Therefore, some of the other sample workflows
demonstrate alternatives that do not require additional access.

### Pull Request to Update Badges: [.github/workflows/build-pr.yml](.github/workflows/build-pr.yml)

This example is nearly identical to the basic case above. The only difference
is that instead of committing and pushing the badges, it uses
another action to generate a pull request to update the badges.

Why? Well, this is the simplest approach to dealing with a protected branch that has
required checks and/or required reviews. The drawback is that you must then
approve and merge the pull request, so this variation introduces a manual step.
However, it is more secure than introducing a PAT.

If you use this approach, and use the default directory for the badges, and the default
badge filenames, then you insert the badges into your README with the same markdown
as the previous example:

```markdown
![coverage](.github/badges/jacoco.svg)
![branches coverage](.github/badges/branches.svg)
```

### Dedicated Badges Branch: [.github/workflows/build-badges-branch.yml](.github/workflows/build-badges-branch.yml)

In the previous example, using a PR to updated badges is less than ideal since it
introduced an extra manual step. An alternative that eliminates that extra step,
and which also works with protected branches without the need for extra access
permissions, is to use a dedicated branch to store the badges. In this example,
we do just that, and the that branch has been named `badges`. If you are using this
approach in an existing project, you'll need to start by creating the `badges` branch.
This repository already has it. Once you create the `badges` branch you can delete
everything from it. In fact, it is preferable that you delete everything from the
`badges` branch.

This workflow assumes that the only purpose of the `badges` branch is to store the coverage
badges, so we'll store them at the root of that branch. The `badges` branch can be protected
if you want. Just don't add any required checks or reviews to the `badges` branch. The 
default GITHUB_TOKEN can push to protected branches as long as there are no required checks 
or required reviews.

The important thing to notice in this workflow is the pair of checkout steps at the beginning.
The first is the usual checkout step. The second checks out the `badges` branch, nesting it
within the other in the path `badges`. In this way, locally within the remainder of the
workflow run, we can access the content of the `badges` branch via the local `badges`
directory.

Here are the badges that result from this workflow:

![coverage](../badges/jacoco.svg)
![branches coverage](../badges/branches.svg)

The above badges were inserted with the following markdown:

```markdown
![coverage](../badges/jacoco.svg)
![branches coverage](../badges/branches.svg)
```

The reason for the weird `../badges` in the above links is so that
we can use relative links, but GitHub automatically assumes the
currently viewed branch of the README with relative links so we can
go up one level to back out of the current branch and then go into the
badges branch. 

