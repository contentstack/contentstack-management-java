[![Contentstack](https://www.contentstack.com/docs/static/images/contentstack.png)](https://www.contentstack.com/)

## Contentstack Management Java SDK

Contentstack is a headless CMS with an API-first approach. It is a CMS that developers can use to build powerful cross-platform applications in their favorite languages. All you have to do is build your application frontend, and Contentstack will take care of the rest. [Read More](https://www.contentstack.com/).

This SDK uses the [Content Management API](https://www.contentstack.com/docs/developers/apis/content-management-api/) (CMA). The CMA is used to manage the content of your Contentstack account. This includes creating, updating, deleting, and fetching content of your account. To use the CMA, you will need to authenticate your users with a [Management Token](https://www.contentstack.com/docs/developers/create-tokens/about-management-tokens) or an [Authtoken](https://www.contentstack.com/docs/developers/apis/content-management-api/#how-to-get-authtoken). Read more about it in [Authentication](https://www.contentstack.com/docs/developers/apis/content-management-api/#authentication).

Note: By using CMA, you can execute GET requests for fetching content. However, we strongly recommend that you always use the [Content Delivery API](https://www.contentstack.com/docs/developers/apis/content-delivery-api/) to deliver content to your web or mobile properties.

### Prerequisite

You need Java 1.8 or above installed on your machine to use the Contentstack Java CMA SDK.

### Installation

Install it via maven:

```java
<dependency>
  <groupId>com.contentstack</groupId>
  <artifactId>cms</artifactId>
  <version>{version}</version>
</dependency>
```

Install it via gradle:

```java
implementation 'com.contentstack.sdk:1.0.0'
```

Get updated version from [here](https://search.maven.org/artifact/com.contentstack.cms)

To import the SDK, use the following command:

```java
import com.contentstack.cms.Contentstack;

Contentstack client = new Contentstack.Builder().build();
```

### Authentication

To use this SDK, you need to authenticate your users by using the Authtoken, credentials, or Management Token (stack-level token).

### Authtoken

An [Authtoken](https://www.contentstack.com/docs/developers/create-tokens/types-of-tokens/#authentication-tokens-authtokens-) is a read-write token used to make authorized CMA requests, and it is a **user-specific** token.

```java
Contentstack client = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build();
```

### Login

To Login to Contentstack by using credentials, you can use the following lines of code:

```java
Contentstack client = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build();
Response<ResponseBody> response = client.login("EMAIL", "PASSWORD").execute()
```

### Management Token

[Management Tokens](https://www.contentstack.com/docs/developers/create-tokens/about-management-tokens/) are **stack-level** tokens, with no users attached to them.

```java
Contentstack client = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build();
Response<ResponseBody> response = client.stack("API_KEY", "MANAGEMENT_TOKEN").contentType().execute()
```

### Contentstack Management Java SDK: 5-minute Quickstart

#### Initializing Your SDK

To use the Java CMA SDK, you need to first initialize it. To do this, use the following code:

```java
import com.contentstack.cms.Contentstack

Contentstack client = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build();
```

#### Fetch Stack Detail

Use the following lines of code to fetch your stack detail using this SDK:

```java
Contentstack client = new Contentstack.Builder().setAuthtoken(AUTHTOKEN).build();
Response<ResponseBody> response = client.stack("API_KEY").exceute()
```

#### Create Entry

To create an entry in a specific content type of a stack, use the following lines of code:

```java
Under development
```

#### Create Asset

The following lines of code can be used to upload assets to your stack:

```java
Under development
```

#### Create JSON Body

JSON Body could be created by using Map passing in the JSONObject or directly create JSONObject.
Let's suppose you have to make json like below:

```java
{
 "entry": {
  "title": "example",
  "url": "/example"
 }
}
```

Create request body like below:

```java
HashMap<String, Object> mapBody = new HashMap<>();
HashMap<String, Object> bodyContent = new HashMap<>();
bodyContent.put("title", "example");
bodyContent.put("url", "/example");
requestBody.put("entry", bodyContent);

JSONObject requestBody = new JSONObject(mapBody)
```

or
Create using JSONObject like directly:

```java
JSONObject requestBody = new JSONObject();
JSONObject bodyContent = new JSONObject();
bodyContent.put("title", "example");
bodyContent.put("url", "/example");
requestBody.put("entry", bodyContent);
```

### Helpful Links

- [Contentstack Website](https://www.contentstack.com/)
- [Official Documentation](https://contentstack.com/docs)
- [Content Management API Docs](https://www.contentstack.com/docs/developers/apis/content-management-api)

### The MIT License (MIT)

Copyright © 2012-2022 [Contentstack](https://www.contentstack.com/). All Rights Reserved

Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
