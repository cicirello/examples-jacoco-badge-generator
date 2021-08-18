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
other reason, such as just trying out the workflows). Keep in mind,
however, that GitHub by default disables workflows in forks. So if you
fork the repository for the purpose of experimenting with the action
and the provided sample workflows, then you will need to enable GitHub 
Actions via the actions tab of your fork.

## Before You Modify Anything

All of the sample workflows are configured to run on pushes and pull requests.
So if you change anything, all of the sample workflows will run. A couple of them
write the same files, so if you change any of the Java files in a way that would
change the coverage percentages or if you delete the badges so you can fully watch
the action work, then when the workflows attempt to push you 
will probably end up with conflicts. If you don't change the Java files, no conflicts will
occur because the existing badges won't change, so the workflows won't attempt to push
anything.

**Recommendation:** If you want to explore the effects of modifying anything, start by
commenting out the push and pull request events that trigger all of the workflows except 
whichever one is closest to your desired use-case).

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
we do just that, and that branch has been named `badges`. This workflow pushes the
badges to the `badges` branch. If you are using this
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

Do we really need the two checkout steps? Can't we just use one of the
other available GitHub Actions that have the ability to push to a different
branch than the one currently checkout out?  Well, the answer depends upon
whether you are using the optional feature of 
the [cicirello/jacoco-badge-generator](https://github.com/cicirello/jacoco-badge-generator) 
GitHub Action that enables you to use the action as a PR check, and to specify whether or 
not to fail the workflow run if coverage has decreased. If you are not using that
feature, then you can choose to either do it this way anyway, or to use another
approach to pushing to the `badges` branch. If you are using that feature, however,
then you will need to check out both branches in this way. This is because that
feature relies on the prior badges to determine if coverage has decreased
(e.g., it parses those badges for the prior coverage and branches coverage). 

### JSON Endpoints Instead of SVG: [.github/workflows/build-json.yml](.github/workflows/build-json.yml)

This example is a variation of the basic use-case, but instead of
directly generating the SVGs, it instead generates JSON endpoints 
in the format expected by Shields custom badge endpoint. Just like the
basic use-case, it uses the default directory `.github/badges`.

Inserting the badges into your project's README becomes a bit more complex
in this case. You must pass the URL of the JSON file to Shields as a URL
query parameter. In particular, you must pass the URL of the JSON file on 
GitHub's raw server.

Here are the badges as generated by Shields from the JSON endpoints:

![coverage](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/cicirello/examples-jacoco-badge-generator/main/.github/badges/jacoco.json)
![branches coverage](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/cicirello/examples-jacoco-badge-generator/main/.github/badges/branches.json)

The above badges were inserted with the following markdown:

```markdown
![coverage](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/cicirello/examples-jacoco-badge-generator/main/.github/badges/jacoco.json)
![branches coverage](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/cicirello/examples-jacoco-badge-generator/main/.github/badges/branches.json)
```

The general format of the markdown required is as follows:

```markdown
![coverage](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/USERNAME/REPOSITORY/BRANCHNAME/.github/badges/jacoco.json)
![branches coverage](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/USERNAME/REPOSITORY/BRANCHNAME/.github/badges/branches.json)
```

You unfortunately can't use relative paths since you need to pass the full URL to
Shields. And thus, if you have created a repository from this template or forked the
template repository, the example badges in this section are actually still derived from
the endpoints in the original template repository.

In most cases, you will probably prefer to use the default behavior that directly
generates the badges since serving the badges in that case only requires one http
request per badge, while the alternative that involves passing endpoints to Shields
involves two requests per badge (one to Shields, and then a second initiated by Shields
to your endpoint). But one advantage of using the endpoint approach is that it
gains you access to all of Shields's built-in customizations. For example, if you wanted
to use one of their alternate styles, you can select that as an additional URL parameter.

Here is an example that directs Shields to use the Shields style "for-the-badge", rather
than the default "flat" style:

![coverage](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/cicirello/examples-jacoco-badge-generator/main/.github/badges/jacoco.json&style=for-the-badge)
![branches coverage](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/cicirello/examples-jacoco-badge-generator/main/.github/badges/branches.json&style=for-the-badge)

The markdown that was used for the above badges is as follows:

```markdown
![coverage](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/cicirello/examples-jacoco-badge-generator/main/.github/badges/jacoco.json&style=for-the-badge)
![branches coverage](https://img.shields.io/endpoint?url=https://raw.githubusercontent.com/cicirello/examples-jacoco-badge-generator/main/.github/badges/branches.json&style=for-the-badge)
```

If your main branch has required checks or required reviews, then this workflow will
fail during the push step. You can easily adapt either of the previous approaches to
dealing with that to the endpoint case (e.g., either having the workflow generate a
pull request, or pushing the endpoints to a different branch). In the latter case,
you will need to adjust the markdown to pass the appropriate URL to Shields.

### JSON Endpoints from a GitHub Pages Project Site

We do not have a specific sample workflow for this case. We don't have GitHub Pages active
on this repository. However, it is a straightforward combination of the previous
two examples.

First, you will take the approach from the example of the "Dedicated Badges Branch",
but name the branch `gh-pages`. Second, go into settings for the repository and follow
GitHub's directions for enabling GitHub Pages, specifically to serve from that branch.
You might then include other content about your project in this branch such as API documentation,
etc.

Next, modify the "Dedicated Badges Branch" workflow to generate the JSON endpoints
instead of directly generating the badges, as was done in the previous example
"JSON Endpoints Instead of SVG", as well as to change the `badges-directory` to whatever
path you used on the checkout of the dedicated `gh-pages` branch.

Assuming you didn't enable a custom domain on your project site, the URL to the
root of this project site will be of the form:

```
https://USERNAME.github.io/REPOSITORY
```

If you stored the JSON files in the root of the `gh-pages` branch, then the URLs
to these files will be:

```
https://USERNAME.github.io/REPOSITORY/jacoco.json
https://USERNAME.github.io/REPOSITORY/branches.json
```

Note that it is very important that you don't use the default badges directory of
your `gh-pages` branch because directories and files that start with `.` are not
accessible from GitHub Pages.

By using GitHub Pages in combination with the JSON endpoints, we can slightly
simplify the markdown needed to have Shields generate the badges, to the following:

```markdown
![coverage](https://img.shields.io/endpoint?url=https://USERNAME.github.io/REPOSITORY/jacoco.json)
![branches coverage](https://img.shields.io/endpoint?url=https://USERNAME.github.io/REPOSITORY/branches.json)
```

