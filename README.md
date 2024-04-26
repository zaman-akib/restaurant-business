### Run the application with Docker Compose

1. `./mvnw clean package`
2. `docker-compose up --build` or
3. `docker-compose up -d`

### Stop the application with Docker Compose

`docker-compose down`

*Note: Use `sudo` if you are getting permission issues*

### Technologies
- Java
- Spring Boot 3.2.2
- Spring Data JPA
- PostgreSQL

### APIs -
- API to return the wish list of a customer - `/api/customer/{customerId}/wish-list`
- API to return the total sale amount of the current day - `/api/customer-orders/total-sale-amount/current-day`
- API to return the max sale day of a certain time range - `/api/customer-orders/max-sale-day?startDate=&endDate=`
- API to return top 5 selling items of all time (based on total sale amount) - `/api/customer-orders/top-5-sold-items-of-all-time`
- API to return top 5 selling items of the last month (based on number of sales) - `/api/customer-orders/top-5-sold-items-of-last-month`


[//]: # (- API to return the order list of the current day - `/api/customer-orders/current-day`)

[//]: # (- API to return the total sale amount of the current day - `/api/customer-orders/total-sale-amount/current-day`)

[//]: # (- API to return all the registered customer list - `/api/customer/registered/all`)

[//]: # (- API to return the entire order list of a customer - `/api/customer-orders/customer/{customerId}`)

[//]: # (- API to return the max sale day of a certain time range - `/api/customer-orders/max-sale-day?startDate=&endDate=`)

### Secret Management
All credentials should be stored in `.env` file and the run the application.