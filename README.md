## Contentstack Management SDK - Java

Contentstack is a headless CMS with an API-first approach. It is a CMS that developers can use to build powerful cross-platform applications in their favorite languages. All you have to do is build your application frontend, and Contentstack will take care of the rest. [Read More](https://www.contentstack.com/). This SDK uses the [Content Management API](https://www.contentstack.com/docs/developers/apis/content-management-api/) (CMA). The CMA is used to manage the content of your Contentstack account. This includes creating, updating, deleting, and fetching content of your account.

**Note:** _By using CMA, you can execute GET requests for fetching content. However, we strongly recommend that you always use the [Content Delivery API](https://www.contentstack.com/docs/developers/apis/content-delivery-api/) to deliver content to your web or mobile properties_.

<details open>
<summary>Table of contents</summary>

- [Prerequisite](#prerequisite)
- [Installation](#installation)
- [Initialization](#initialization)
- [Authentication](#authentication)
- [Users](#users)
- [Organizations](#organizations)
- [Stacks](#stacks)
- [Content Types](#content-types)
- [Global Fields](#global-fields)
- [Entries](#entries)
- [Assets](#assets)
- [Bulk Operation](#bulk-operations)

</details>

- Others
  [Create JSON Body](##RequestBody)

## Prerequisite

To use the Contentstack management sdk for Java, you must have:

- The suitable [Java Development Environment](http://www.oracle.com/technetwork/java/javase/downloads/).
- The [Contentstack](https://www.contentstack.com) account.
- Login your account
- Get the [Contentstack](https://www.contentstack.com) credentials (API keys and Management Token) set in your stack token section.
- Should be Java 1.8 or above installed.

## Setup

To use Contentstack management sdk in our Maven project, we'll need to add the following dependency to our pom.xml:


```java
<dependency>
 <groupId>com.contentstack</groupId>
 <artifactId>cms</artifactId>
 <version>{version}</version>
</dependency>
```

Or, for a Gradle project:

```java
implementation 'com.contentstack.cms:{version}'
```

- Download the latest version [HERE](https://search.maven.org/artifact/com.contentstack.cma)

## Initialization

To use the Java CMA SDK, you need get the client first:

```java
Contentstack client = new Contentstack.Builder().setAuthtoken("AUTHTOKEN").build();
```

## Authentication

To use the CMA, you will need to authenticate your users with a [Management Token](https://www.contentstack.com/docs/developers/create-tokens/about-management-tokens) or an [Authtoken](https://www.contentstack.com/docs/developers/apis/content-management-api/#how-to-get-authtoken). Read more about it in [Authentication](https://www.contentstack.com/docs/developers/apis/content-management-api/#authentication)

- _Using authtoken_

---

An [Authtoken](https://www.contentstack.com/docs/developers/create-tokens/types-of-tokens/#authentication-tokens-authtokens-) is a read-write token used to make authorized CMA requests, and it is a **user-specific** token.

```java
Contentstack  client = new Contentstack.Builder().setAuthtoken("AUTHTOKEN").build();
```

- _Using login email id and password_

To Login to Contentstack by using credentials, you can use the following lines of code:

```java
Contentstack  client = new Contentstack.Builder().setAuthtoken("AUTHTOKEN").build();
Response<ResponseBody> response = client.login("EMAIL", "PASSWORD").execute()
```

- _Using management token_

[Management Tokens](https://www.contentstack.com/docs/developers/create-tokens/about-management-tokens/) are **stack-level** tokens, with no users attached to them.

```java
Contentstack  client = new Contentstack.Builder().setAuthtoken("AUTHTOKEN").build();
Response<ResponseBody> response = client.stack("API_KEY", "MANAGEMENT_TOKEN").contentType().execute()
```

## Users

[Users](https://www.contentstack.com/docs/developers/apis/content-management-api/#users) All accounts registered with Contentstack are known as Users. A stack can have many users with varying permissions and roles.

Access the user like below snippet:

```java
Contentstack client = new Contentstack.Builder().build();
client.login("EMAIL", "PASSWORD");
User user = client.user();
Response<ResponseBody> response = user.getUser().execute();
```

## Organizations

[Organization](https://www.contentstack.com/docs/owners-and-admins/about-organizations) is the top-level entity in the hierarchy of Contentstack, consisting of stacks and [stack](https://www.contentstack.com/docs/developers/set-up-stack/about-stack/) resources, and users. Organization allows easy management of projects as well as users within the Organization.

```java
Contentstack client = new Contentstack.Builder().build();
client.login("EMAIL", "PASSWORD");
Organization organization = contentstack.organization();
Response<ResponseBody> response = organization.getAll().execute();
```

## Stacks

A [Stack](https://www.contentstack.com/docs/developers/set-up-stack/about-stack) is a space that stores the content of a project (a web or mobile property). Within a stack, you can create content structures, content entries, users, etc. related to the project.

```java
Contentstack client = new Contentstack.Builder().build();
client.login("EMAIL", "PASSWORD");
Stack stack = contentstack.stack("APIKey");
Response<ResponseBody> response = stack.fetch().execute();
```

OR

```java
Contentstack  client = new Contentstack.Builder().setAuthtoken("AUTHTOKEN").build();
Response<ResponseBody> response = client.stack("API_KEY").exceute()
```

## Content Types

[Content type](https://www.contentstack.com/docs/developers/apis/content-management-api/#content-types) defines the structure or schema of a page or a section of your web or mobile property. To create content for your application, you are required to first create a content type, and then create entries using the content type.

```java
Contentstack contentstack = new Contentstack.Builder().build();
ContentType contentType = contentstack.contentType("API_KEY", "MANAGEMENT_TOKEN");
Response<ResponseBody> response = contentType.fetch(mapQuery).execute();
```

## Global Fields

A [Global field](https://www.contentstack.com/docs/developers/apis/content-management-api/#global-fields) is a reusable field (or group of fields) that you can define once and reuse in any content type within your stack. This eliminates the need (and thereby time and efforts) to create the same set of fields repeatedly in multiple content types.

```java
GlobalField globalField = new Contentstack.Builder().setAuthtoken(GLOBAL_AUTHTOKEN).build()
                .globalField("API_KEY", "MANAGEMENT_TOKEN");
Call<ResponseBody> response = globalField.update(GlobalFiledUid,
                "requestBody");
```

## Entries

An [entry](https://www.contentstack.com/docs/developers/apis/content-management-api/#entries) is the actual piece of content created using one of the defined content types.

```java
Entry entry = new Contentstack.Builder().setAuthtoken(authToken).build()
                .entry("API_KEY", "MANAGEMENT_TOKEN");
HashMap<String, Object> queryParams = new HashMap<>();
Response<ResponseBody> response = entry.fetch(queryParams).execute();
```

## Assets

[Assets](https://www.contentstack.com/docs/developers/apis/content-management-api/#assets) refer to all the media files (images, videos, PDFs, audio files, and so on) uploaded in your Contentstack repository for future use.

```java
Asset asset = new Contentstack.Builder().setAuthtoken("AUTHTOKEN").build().asset("API_KEY", "MANAGEMENT_TOKEN");
Map<String, Object> queryParams = new HashMap<>();
queryParams.put("folder", "folder_uid_some_example");
queryParams.put("include_folders", true);
queryParams.put("environment", "production");
queryParams.put("version", 1);
queryParams.put("include_publish_details", true);
queryParams.put("include_count", true);
queryParams.put("relative_urls", false);
queryParams.put("asc_field_uid", "created_at");
queryParams.put("desc_field_uid", 230);

Response<ResponseBody> response = asset.fetch(queryParams).execute();

```

## Bulk Operations

You can perform [bulk operations](https://www.contentstack.com/docs/developers/apis/content-management-api/#bulk-operations) such as Publish, Unpublish, and Delete on multiple entries or assets, or Change the Workflow Details of multiple entries or assets at the same time.

## Others

### Create RequestBody

- JSON Body could be created by using Map passing in the JSONObject or directly create JSONObject.
  Let's suppose you have to make json like below:

```java
{
"entry": {
 "title":  "example",
 "url":  "/example"
  }
}
```

- Create request body using HashMap like below

```java
HashMap<String, Object> mapBody = new  HashMap<>();
HashMap<String, Object> bodyContent = new  HashMap<>();
bodyContent.put("title", "example");
bodyContent.put("url", "/example");
requestBody.put("entry", bodyContent);
JSONObject  requestBody = new  JSONObject(mapBody)
```

- The other way to create RequestBody using JSONObject like below

```java
JSONObject  requestBody = new  JSONObject();
JSONObject  bodyContent = new  JSONObject();
bodyContent.put("title", "example");
bodyContent.put("url", "/example");
requestBody.put("entry", bodyContent);
```

### Documentation

- [Content Management API Docs](https://www.contentstack.com/docs/developers/apis/content-management-api/#api-reference)
- [API References](https://www.contentstack.com/docs/developers/apis/content-management-api/#api-reference)

### Other helpful Links

- [Contentstack Website](https://www.contentstack.com/)
- [Official Documentation](https://contentstack.com/docs)

### The MIT License (MIT)

Copyright © 2012-2022 [Contentstack](https://www.contentstack.com/). All Rights Reserved Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software. THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
