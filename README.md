# VK-Uploader
[![EO principles respected here](http://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/driver733/VK-Uploader)](http://www.rultor.com/p/driver733/VK-Uploader)
[![We recommend IntelliJ IDEA](http://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![Build Status](https://travis-ci.org/driver733/VK-Uploader.svg?branch=master)](https://travis-ci.org/driver733/VK-Uploader)

[![Coverage Status](https://coveralls.io/repos/github/driver733/VK-Uploader/badge.svg?branch=master)](https://coveralls.io/github/driver733/VK-Uploader?branch=master)
[![](https://tokei.rs/b1/github/driver733/VK-Uploader)](https://github.com/driver733/VK-Uploader)

[![Dependabot Status](https://api.dependabot.com/badges/status?host=github&identifier=91912426)](https://dependabot.com)

[![Code Climate (badge)](https://api.codeclimate.com/v1/badges/4bb9b9966d40e4b10c8d/maintainability)](https://codeclimate.com/github/driver733/VK-Uploader/maintainability)
[![codebeat (badge)](https://codebeat.co/badges/483007e8-a73d-4bfd-80a1-52586ba3a615)](https://codebeat.co/projects/github-com-driver733-VK-Uploader-master)
[![Codacy (badge)](https://api.codacy.com/project/badge/Grade/65288c94deac4a36bf03a80604cf1c04)](https://www.codacy.com/app/driver733/VK-Uploader?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=driver733/VK-Uploader&amp;utm_campaign=Badge_Grade)

[![PDD status](http://www.0pdd.com/svg?name=driver733/VK-Uploader)](http://www.0pdd.com/p?name=driver733/VK-Uploader)

[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/driver733/VK-Uploader/blob/master/LICENSE.txt)

**VK-Uploader** is a [true object-oriented](http://www.yegor256.com/2014/11/20/seven-virtues-of-good-object.html)
and [immutable](http://www.yegor256.com/2014/06/09/objects-should-be-immutable.html)
[VK (Вконтакте)](vk.com) group management utility. It is based on [EO](https://www.elegantobjects.org) principles:

 1. [No](https://github.com/driver733/VK-Uploader/search?l=Java&q=null) `null` usage ([why NULL is bad?](http://www.yegor256.com/2014/05/13/why-null-is-bad.html))
 2. No static methods ([why they are bad?](http://www.yegor256.com/2014/05/05/oop-alternative-to-utility-classes.html))
    * [No](https://github.com/driver733/VK-Uploader/search?q=%22public+static%22&unscoped_q=%22public+static%22) `public` `static` methods
    * [No](https://github.com/driver733/VK-Uploader/search?q=%22private+static%22&unscoped_q=%22public+static%22) `private` `static` methods
 3. [No](https://github.com/driver733/VK-Uploader/search?l=Java&q=%22%40Immutable%22) mutable classes ([why they are bad?](http://www.yegor256.com/2014/06/09/objects-should-be-immutable.html))
 4. [No](https://github.com/driver733/VK-Uploader/search?q=instanceof&unscoped_q=instanceof) `instanceof` keyword usage, type casting, or reflection ([why?](http://www.yegor256.com/2015/04/02/class-casting-is-anti-pattern.html))
 5. No code in constructors ([why?](http://www.yegor256.com/2015/05/07/ctors-must-be-code-free.html))
 6. No [getters](https://github.com/driver733/VK-Uploader/search?l=Java&q=%22get%22) and [setters](https://github.com/driver733/VK-Uploader/search?l=Java&q=%22set%22) ([why?](http://www.yegor256.com/2014/09/16/getters-and-setters-are-evil.html))
 7. [No](https://github.com/driver733/VK-Uploader/search?l=Java&q=%22implements%22) public methods without contract (interface) ([why?](https://www.yegor256.com/2014/11/20/seven-virtues-of-good-object.html#2-he-works-by-contracts))
 8. [No](https://github.com/driver733/VK-Uploader/search?l=Java&q=%22assertThat%22) statements in test methods except assertThat ([why?](http://www.yegor256.com/2017/05/17/single-statement-unit-tests.html))
 9. [No](https://github.com/driver733/VK-Uploader/search?q=%22extends%22&unscoped_q=%22extends%22) implementation inheritance ([why?](http://www.yegor256.com/2017/01/31/decorating-envelopes.html) and [why?](http://www.yegor256.com/2016/09/13/inheritance-is-procedural.html))

## Core Entities (Interfaces)

### [WallPost](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/wallpost/WallPost.java)
This interface is implemented by the classes which construct (decorate) VK WallPosts.
For example the [WallPostBase](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/wallpost/WallPostBase.java) class is a fundamental implementation of that interface, as
it encapsulates a [VK API client](https://github.com/VKCOM/vk-java-sdk/blob/master/sdk/src/main/java/com/vk/api/sdk/client/VkApiClient.java) instance and a [UserActor](https://github.com/VKCOM/vk-java-sdk/blob/master/sdk/src/main/java/com/vk/api/sdk/client/actors/UserActor.java) instance.

Other implementations of this interface, such as [WallPostWithMessage](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/wallpost/WallPostWithMessage.java) or [WallPostWithAttachments](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/wallpost/WallPostWithAttachments.java)
add content to a wall post (text and attachments, accordingly).

Also, there are some convenient implementations of that interface, such as [WallPostMusicAlbum](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/wallpost/WallPostMusicAlbum.java) or [WallPostPhotoAlbum](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/wallpost/WallPostPhotoAlbum.java),
which create a [WallPost](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/wallpost/WallPost.java) with audio (music album and a album artwork) or image (photo album) content.

A full list of classes implementing this interface can be found in the package [wallpost](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/wallpost).

In addition, the examples, which showcase how to utilize this interface and its implementations can be found in corresponding [test package](https://github.com/driver733/VK-Uploader/tree/master/src/test/java/com/driver733/vkuploader/wallpost).

### [WallPosts](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/wallpost/wallposts/WallPosts.java)
This interface is implemented by the classes which generate a series of [WallPost](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/wallpost/WallPost.java)s and combine them into a list of [ExecuteBatchQueries](https://github.com/VKCOM/vk-java-sdk/blob/master/sdk/src/main/java/com/vk/api/sdk/queries/execute/ExecuteBatchQuery.java).
After the queries are executed, the `updateProperties` method shall be called in order to cache the queries` results. (usually with properties files).

### [Posts](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/post/posts/Posts.java)
This interface is implemented by classes which create [WallPosts](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/wallpost/wallposts/WallPosts.java).
For example, the [PostableRootDir](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/post/PostableRootDir.java) class creates [WallPosts](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/wallpost/wallposts/WallPosts.java) from the provided directory.

### [Postable](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/post/Postable.java)
This interface serves as an entry point to the [WallPosts](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/wallpost/wallposts/WallPosts.java). The classes which implement this interface
create [WallPosts](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/wallpost/wallposts/WallPosts.java) using a [Posts](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/post/posts/Posts.java) instance and then post (execute the generated queries) them.

### [Entrance](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/post/Entrance.java)
Application starting point. The [EntranceDirectory](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/post/EntranceDirectory.java) class, for instance, tracks the provided folder for changes
and acts upon them (creates [WallPosts](https://github.com/driver733/VK-Uploader/blob/master/src/main/java/com/driver733/vkuploader/wallpost/wallposts/WallPosts.java) and executes them).


## Maven

1. Qulice (static analysis check)
    ```
    $ mvn clean test -DskipTests=true qulice:check
    ```
2. Unit tests
    ```
    $ mvn clean test
    ```
3. Integration Tests
    ```
    $ clean verify -Dvk.userId= -Dvk.groupId= -Dvk.token=
    ```
4. Full Pre-push validation (static analysis + unit tests + integration tests)
    ```
    $ mvn clean verify qulice:check -Dvk.userId= -Dvk.groupId= -Dvk.token=
    ```
5. Test coverage report (located in `VK-Uploader/target/site`)
    ```
    $ mvn clean test jacoco:report
    ```

For more info see [Authorization Code Flow for User Access Token](https://vk.com/dev/authcode_flow_user)

## How to contribute

Fork repository, make changes, send us a pull request. We will review
your changes and apply them to the `master` branch shortly, provided
they don't violate our quality standards. To avoid frustration, before
sending us your pull request please run full Maven build:

```
$ mvn clean install qulice:check
```

To avoid build errors use maven 3.2+.

Pay attention that our `pom.xml` inherits a lot of configuration
from [jcabi-parent](http://parent.jcabi.com).
[This article](http://www.yegor256.com/2015/02/05/jcabi-parent-maven-pom.html)
explains why it's done this way.

## Got questions?

If you have questions or general suggestions, don't hesitate to submit
a new [Github issue](https://github.com/driver733/VK-Uploader/issues/new).