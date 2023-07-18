# RealEstate - RESTful Application for Selling Real Estates

RealEstate is a robust RESTful application designed for the purpose of selling real estates. The application revolves around three main entities: Users, Real Estates, and Credit Cards. Each user can own multiple real estates and credit cards, while each real estate and credit card is associated with only one user. To ensure a smooth user experience, access to the API endpoints is documented and implemented using Swagger.

## Features

1. **Data Transfer Objects (DTOs):** Each entity has its own Data Transfer Object (DTO) version, carefully crafted to hide unnecessary data such as sensitive identifiers. This ensures that only essential information is exposed to users.

2. **Soft Delete:** We've incorporated a soft delete functionality to safely remove objects from the database without permanently erasing data. This allows for better data management and potential data recovery.

3. **User Types:** The application caters to three distinct types of users:
   - Non-Authenticated Users: Visitors exploring the application without logging in.
   - Authenticated Users: Logged-in users with verified identities.
   - Admins: Users with elevated privileges and access to special endpoints.

## CreditCardController
### Non authenticated user
doesn't have access

### Authenticated user
- /card/all/{UserId} - retrives all own cards 
- /card/{id} - retrives ownt card with id=id
- /card/new - creates a new card from a json file
- /card/{id}/edit - edit own card with id from a json file
- /card/{id}/delete - deletes own card with id=id

### Admin

- /card/all - retrives  all cards
- /card/all/{UserId} - retrives all user cards with id=UserId 
- /card/{id} - retrives the card with id=id
- /card/new - creates a new card from a json file
- /card/{id}/edit - edit the card with id from a json file
- /card/{id}/delete - deletes the card with id=id

## UserContoller

### Non authenticated user
- /user/new - creates a new user from json file
- /user/{id} - retrives user with id=id

###  Authenticated user

- /user/{id} - retrives user with id=id
- /user/new - creates a new user
- /user/{id}/edit - edits own user account from a  json file
- /user/{id}/delete - delets own user account

  
### Admin
- /user/all/{offset}/{pageSize} - retrives  page  of  all users 
- /user/{id} - retrives user with id=id
- /user/new - creates a new user
- /user/{id}/edit - edits the user with id=id from a json file
- /user/{id}/delete - delets the user with id=id


## RealEstateContoller

### Non authenticated user, Authenticated user, Admin
- /estate/all/{offset}/{pageSize} - retrives page of all realestates 
- /estate/all/{offset}/{pageSize}/{field} - retrives page of all realestates sorted by {field}
- /estate/all/{offset}/{pageSize}/price -retrives page of all realestates whit price between requestParams leftPrice and rightPrice
- /estate/all/{offset}/{pageSize}/count_rooms -retrives page of all realestates whit count of rooms between requestParams leftCountRooms and rightCountRooms
- /estate/allBy/{userId}/{offset}/{pageSize} -retrives page of all user realestates  whith id=UserId
-  /estate/{id} - retrives real estate with id=id

###  Authenticated user
- /estate/new - creates a new real estate from  a json file
- /estate/{id}/edit - edits own real estate with id=id
- /estate/{id}delete - deletes own real estate with id=id
  
### Admin
- /estate/new - creates a new real estate from  a json file
- /estate/{id}/edit - edits the real estate with id=id
- /estate/{id}delete - deletes the real estate with id=id


