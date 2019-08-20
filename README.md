# Money-transfer-api
A demo for money transfer api

## Motivation
This API demonstrates the implementation of the money transfer endpoint using micronaut.
This endpoint processes transfers between accounts with the same currency.

## Tech/framework used
- micronaut, lombok

## Features
- An endpoint that consumes transfer request via POST, with validation
- Transform transfer request into transaction and put into storage
- A background job running in periodically to process transactions asynchronously

### Scope that is not covered
The scope does not cover account endpoint, that decides whether a transaction is valid,  
which includes external payment network integration 
(Faster Paymetns, SEPA, SWIFT etc.). 

In this demo, a fake account client is used. 

## API Reference
- POST /api/v1/transfers
- GET /api/v1/transactions/{transactionId}

### Demo usage
- POST /api/v1/transfers
```json
{
	"requestId": "22222",
	"sourceAccountId": "3f87e3f1-ca7c-4d34-bbff-c6b810aa2fcc",
	"targetAccountId": "4000f4de-dee4-4ec4-863b-08deb5b48371",
	"amount": 30,
	"currency": "EUR",
	"reference":""
}
```
and you will see the response:
```json
{
    "transactionId": "dd6570db-c740-414b-92cb-75ecc8c63528",
    "state": "PENDING",
    "createdAt": 1566310013.091038
}
```

You can find the transaction by:

- GET /api/v1/transactions/dd6570db-c740-414b-92cb-75ecc8c63528
 
and receive the following response:
```json
{
    "id": "dd6570db-c740-414b-92cb-75ecc8c63528",
    "requestId": "22222",
    "state": "COMPLETED",
    "createdAt": 1566310013.091038,
    "updatedAt": 1566310018.646751,
    "completedAt": 1566310018.646751,
    "accountTransactions": [
        {
            "id": "e9f62852-7b96-497c-a648-ccd9ab32a37e",
            "accountId": "3f87e3f1-ca7c-4d34-bbff-c6b810aa2fcc",
            "amount": -30
        },
        {
            "id": "4fd44e78-503f-41c7-b220-2d7b25dd09f8",
            "accountId": "4000f4de-dee4-4ec4-863b-08deb5b48371",
            "amount": 30
        }
    ]
}
```

## How to use
- mvn clean package
- docker build -t money-transfer-api .
- docker run -rm --name money-transfer-api

## Future plan
Replace in-memory storage with Kafka. When posting a transfer request, 
send this request as transaction to Kafka.

Then. the kafka consumer processes the transaction event and produce the finished transaction back to the Kafka topic,
with transactionId as key and use compaction for the kafka topic. 
