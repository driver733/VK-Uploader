# VKMusicUploader
[![EO principles respected here](http://www.elegantobjects.org/badge.svg)](http://www.elegantobjects.org)
[![DevOps By Rultor.com](http://www.rultor.com/b/yegor256/cactoos)](http://www.rultor.com/p/yegor256/cactoos)
[![We recommend IntelliJ IDEA](http://www.elegantobjects.org/intellij-idea.svg)](https://www.jetbrains.com/idea/)

[![Build Status](https://travis-ci.org/driver733/VKMusicUploader.svg?branch=master)](https://travis-ci.org/driver733/VKMusicUploader)
[![Coverage Status](https://coveralls.io/repos/github/driver733/VKMusicUploader/badge.svg?branch=master)](https://coveralls.io/github/driver733/VKMusicUploader?branch=master)
[![](https://tokei.rs/b1/github/driver733/VKMusicUploader)](https://github.com/driver733/VKMusicUploader)

[![codebeat badge](https://codebeat.co/badges/483007e8-a73d-4bfd-80a1-52586ba3a615)](https://codebeat.co/projects/github-com-driver733-vkmusicuploader-master)
[![Codacy Badge](https://api.codacy.com/project/badge/Grade/65288c94deac4a36bf03a80604cf1c04)](https://www.codacy.com/app/driver733/VKMusicUploader?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=driver733/VKMusicUploader&amp;utm_campaign=Badge_Grade)

[![PDD status](http://www.0pdd.com/svg?name=driver733/VKMusicUploader)](http://www.0pdd.com/p?name=driver733/VKMusicUploader)

[![License](https://img.shields.io/badge/license-MIT-green.svg)](https://github.com/driver733/VKMusicUploader/blob/master/LICENSE.txt)

**VKMusicUploader** is a [true object-oriented](http://www.yegor256.com/2014/11/20/seven-virtues-of-good-object.html)
and [immutable](http://www.yegor256.com/2014/06/09/objects-should-be-immutable.html)
[VK (Вконтакте)](vk.com) group management utility. It relies on four fundamental principles:

 1. [not a single](https://github.com/driver733/VKMusicUploader/search?l=Java&q=null) `null` ([why NULL is bad?](http://www.yegor256.com/2014/05/13/why-null-is-bad.html))
 2. No static methods ([why they are bad?](http://www.yegor256.com/2014/05/05/oop-alternative-to-utility-classes.html))
    * [not a single](https://github.com/driver733/VKMusicUploader/search?q=%22public+static%22&unscoped_q=%22public+static%22) `public` `static` method
    * [not a single](https://github.com/driver733/VKMusicUploader/search?q=%22private+static%22&unscoped_q=%22public+static%22) `private` `static` method
 3. [not a single](https://github.com/driver733/VKMusicUploader/search?l=Java&q=%22%40Immutable%22) mutable class ([why they are bad?](http://www.yegor256.com/2014/06/09/objects-should-be-immutable.html))
 4. [not a single](https://github.com/driver733/VKMusicUploader/search?q=instanceof&unscoped_q=instanceof) `instanceof` keyword, type casting, or reflection ([why?](http://www.yegor256.com/2015/04/02/class-casting-is-anti-pattern.html))

## Core Entities (Interfaces)

### [WallPost](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/wallpost/WallPost.java)
This interface is implemented by the classes which construct (decorate) VK WallPosts.
For example the [WallPostBase](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/wallpost/WallPostBase.java) class is a fundamental implementation of that interface, as
it encapsulates a [VK API client](https://github.com/VKCOM/vk-java-sdk/blob/master/sdk/src/main/java/com/vk/api/sdk/client/VkApiClient.java) instance and a [UserActor](https://github.com/VKCOM/vk-java-sdk/blob/master/sdk/src/main/java/com/vk/api/sdk/client/actors/UserActor.java) instance.

Other implementations of this interface, such as [WallPostWithMessage](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/wallpost/WallPostWithMessage.java) or [WallPostWithAttachments](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/wallpost/WallPostWithAttachments.java)
add content to a wall post (text and attachments, accordingly).

Also, there are some convenient implementations of that interface, such as [WallPostMusicAlbum](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/wallpost/WallPostMusicAlbum.java) or [WallPostPhotoAlbum](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/wallpost/WallPostPhotoAlbum.java),
which create a [WallPost](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/wallpost/WallPost.java) with audio (music album and a album artwork) or image (photo album) content.

A full list of classes implementing this interface can be found in the package [wallpost](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/wallpost).

In addition, the examples, which showcase how to utilize this interface and its implementations can be found in corresponding [test package](https://github.com/driver733/VKMusicUploader/tree/master/src/test/java/com/driver733/vkmusicuploader/wallpost).

### [WallPosts](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/wallpost/wallposts/WallPosts.java)
This interface is implemented by the classes which generate a series of [WallPost](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/wallpost/WallPost.java)s and combine them into a list of [ExecuteBatchQueries](https://github.com/VKCOM/vk-java-sdk/blob/master/sdk/src/main/java/com/vk/api/sdk/queries/execute/ExecuteBatchQuery.java).
After the queries are executed, the `updateProperties` method shall be called in order to cache the queries` results. (usually with properties files).

### [Posts](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/post/posts/Posts.java)
This interface is implemented by classes which create [WallPosts](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/wallpost/wallposts/WallPosts.java).
For example, the [PostableRootDir](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/post/PostableRootDir.java) class creates [WallPosts](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/wallpost/wallposts/WallPosts.java) from the provided directory.

### [Postable](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/post/Postable.java)
This interface serves as an entry point to the [WallPosts](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/wallpost/wallposts/WallPosts.java). The classes which implement this interface
create [WallPosts](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/wallpost/wallposts/WallPosts.java) using a [Posts]() instance and then post (execute the generated queries) them.

### [Application](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/post/Application.java)
Application starting point. The [DirectoryEntrance]() class, for instance, tracks the provided folder for changes
and acts upon them (creates [WallPosts](https://github.com/driver733/VKMusicUploader/blob/master/src/main/java/com/driver733/vkmusicuploader/wallpost/wallposts/WallPosts.java) and executes them).


## Maven

1. Qulice  (static analysis check)
    * `mvn clean test -Dcredentials="Path to the credentials.properties" qulice:check`
2. Unit tests
    * `mvn clean test -Dcredentials="Path to the credentials.properties"`
3. Integration Tests
    * `mvn clean verify -Dcredentials="Path to the credentials.properties"`
4. Full Pre-push validation
    * `mvn clean verify -Dcredentials="Path to the credentials.properties" qulice:check`
5. Create test coverage report (located in `VKMusicUploader/target/site`)
    * `mvn clean test jacoco:report`


## Configuration file (`credentials.properties`)

```
vk.userId="VK USER ID"   # VK.com Group Admin User ID
vk.groupId="VK GROUP ID" # VK.com Group ID
vk.token="VK AUTH TOKEN" # VK.com auththorization token received using "Authorization Code Flow")
```
For more info see [Authorization Code Flow for User Access Token](https://vk.com/dev/authcode_flow_user)


## How to contribute

Fork repository, make changes, send us a pull request. We will review
your changes and apply them to the `master` branch shortly, provided
they don't violate our quality standards. To avoid frustration, before
sending us your pull request please run full Maven build:

```
$ mvn `clean verify -Dcredentials="Path to the credentials file" qulice:check`
```

To avoid build errors use maven 3.2+.

Pay attention that our `pom.xml` inherits a lot of configuration
from [jcabi-parent](http://parent.jcabi.com).
[This article](http://www.yegor256.com/2015/02/05/jcabi-parent-maven-pom.html)
explains why it's done this way.

## Got questions?

If you have questions or general suggestions, don't hesitate to submit
a new [Github issue](https://github.com/driver733/VKMusicUploader/issues/new).