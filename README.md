# contentstack-management-java

### Implementation:

```java
 Client client = new Client.Builder().setAuthtoken("blt3cecf75b33bb2ebe").build();

 [and]

 Client client = new Client.Builder().setAuthtoken("blt3cecf75b33bb2ebe").build();
 User userInstance = client.user();
 Response response = userInstance.getUser().execute();
```
