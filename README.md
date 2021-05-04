# contentstack-management-java

### Implementation:

coverage:


```java
    Client client = new Client.Builder().setAuthtoken("auth_token").build();

        
    Client client = new Client.Builder().setAuthtoken("auth_token").build();
    User userInstance = client.user();
    Response response = userInstance.getUser().execute();
```
