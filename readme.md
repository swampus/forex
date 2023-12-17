*Java17 + maven + flyway + H2 + Spring-boot
*for supporting service unavailability used fallbackcache approach (IRL required full service consume. creating init cache)

Start
```mvn spring-boot:run```
will create H2 localDB add some test data

<h3>Get Accounts</h3>
- GET /rest/api/accounts/user/{userIdentifier} - will return account
- ```http://localhost:8080/rest/api/accounts/user/3d3aa881-69ea-4ac8-9a29-53ce96b3c17b```

<h3>Get Transaction history by account Number</h3>
- GET /rest/api/transactions/history/{accountNumber}?offset=0&limit10 - GET Transaction History for account Number
- ```http://localhost:8080/rest/api/transactions/history/EUR123456789?offset=0&limit=10```

- ```http://localhost:8080/rest/api/transactions/history/EUR123456789?offset=0&limit=15```

<h3>Transfer funds</h3>

POST /rest/api/transactions/transfer
```
{
  "sourceAccountNumber": "USD234567890",
  "targetAccountNumber": "EUR234567890",
  "amount": 100.00,
  "currency": "EUR"
}
```



