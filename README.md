# Rest API with signed endpoints
###### BNKS FINKI Project
###### Students: Dusica Jankovikj (161509), Dragana Nikolova (171085)
---
#### What is this project about
An implementation of a protocol intended to provide a simple and standard way for clients to sign messages and for a server to verify the signatures on the HTTP messages.
*Cryptographically signing HTTP messages themselves is required in order to ensure end-to-end message integrity. An added benefit of signing the HTTP message for the purposes of end-to-end message integrity is that the client can be authenticated using the same mechanism without the need for multiple round-trips.*
This project provides REST API's for authenticating users and for verifying signed messages. The main goal is message signing and verification and for than purpose the theme of the project is Auctions, where placing bids is a process which requires message signing.
A mock implementation of a Key Distribution Center (KDC) is used for secret key generation, management and distribution.
The signing of messages is done with the usage of the HMAC-SHA256 algorithm and while the verification of messages is done in Spring Boot, the signing of messages is done via the [frontend React application](https://github.com/djankovik/BNKS_frontend).
#### Implementation
##### APIs
`RegistrationAPI` - An API where users can register or log in. The credentials required are username and password. For each registered user, a HMAC secret key is generated and stored by the KDC service. When logged in, a temporary JSON Web Token (JWT)n is generated for the user.
`AuctionSignAPI` - An API where users can view the auctions and their own history of placed bids once they are logged in (have an JWT). This API can also verify any incoming signed requests for placing bids.
##### Utils
`JWTokenUtils` - Generation and validation of JW tokens.
`ReqUtils` - Dismantling each request to its composing parts. Manipulating and processing parts of interes.
`VerificationUtils` - Verification of signatures produced with the HMAC-SHA256 algorithm.

##### KDC
`KDCMockSerivceImpl` - Implementation of the basic KDC functionalities defined in `KDCService`.
