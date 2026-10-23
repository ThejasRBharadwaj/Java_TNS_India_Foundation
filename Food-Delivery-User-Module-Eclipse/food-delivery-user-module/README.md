# Food Delivery User Module — Spring Boot + PostgreSQL

Backend-only Java REST API for Eclipse and Postman. No frontend is required. Uses Spring Boot 3.4.3, Java 17+, Spring Data JPA, and PostgreSQL.

## 1. Create the database

- Start PostgreSQL and open pgAdmin.
- Open Query Tool on the default `postgres` database.
- Execute `database/create_database.sql` to create `food_delivery_db` (skip if it already exists).
- Default local connection: `jdbc:postgresql://localhost:5432/food_delivery_db`, username `postgres`.
- Set `DB_PASSWORD` to your PostgreSQL password. In Eclipse: Run Configurations → your application → Environment, add `DB_URL`, `DB_USERNAME`, and `DB_PASSWORD` if needed.

## 2. Import into Eclipse

1. Extract the ZIP.
2. Eclipse → File → Import → Maven → Existing Maven Projects.
3. Select the extracted `food-delivery-user-module` folder containing `pom.xml`.
4. Right-click project → Maven → Update Project.
5. Run `src/main/java/com/example/fooddelivery/FoodDeliveryApplication.java` as Java Application.
6. Confirm the console says Tomcat started on port 8080 and the application started. On first startup Hibernate creates/updates the tables. Sample food items are inserted only if the catalogue is empty.

If you previously ran the H2 version, its data is not automatically copied into PostgreSQL. PostgreSQL starts with its own database.

## 3. Postman API

Base URL: `http://localhost:8080`

| Operation | Method | Endpoint |
|---|---|---|
| Register customer | POST | `/api/users/register` |
| Login | POST | `/api/users/login` |
| View customer | GET | `/api/users/{userId}` |
| List/search food | GET | `/api/food-items` (optional `?search=pizza`) |
| Add cart item | POST | `/api/users/{userId}/cart/items` |
| View cart | GET | `/api/users/{userId}/cart` |
| Update cart quantity | PUT | `/api/users/{userId}/cart/items/{foodItemId}` |
| Remove cart item | DELETE | `/api/users/{userId}/cart/items/{foodItemId}` |
| Place order | POST | `/api/users/{userId}/orders` |
| View order history | GET | `/api/users/{userId}/orders` |
| View order | GET | `/api/users/{userId}/orders/{orderId}` |
| Cancel pending order | PATCH | `/api/users/{userId}/orders/{orderId}/cancel` |

### Register

`POST http://localhost:8080/api/users/register` with Body → raw → JSON:

```json
{
  "username": "Test Customer",
  "contactNo": "9876543210",
  "password": "TestPass12345"
}
```

Use a unique contact number for each new registration.

### Add item to cart

`POST http://localhost:8080/api/users/1/cart/items`

```json
{
  "foodItemId": 1,
  "quantity": 2
}
```

Use IDs returned by your own API/database responses.

### Place order

`POST http://localhost:8080/api/users/1/orders`

```json
{
  "deliveryAddress": "12 Example Road, Mysuru, Karnataka"
}
```

The customer must exist and the cart must contain at least one item.

Import `postman/Food-Delivery-User-Module.postman_collection.json` to use the included request collection.

## 4. Verify PostgreSQL persistence

In pgAdmin, connect to `food_delivery_db` and expand `Schemas → public → Tables`. Tables are created by Hibernate based on entities. After registering a customer or placing an order, refresh the relevant table and use Query Tool, for example:

```sql
SELECT * FROM users;
SELECT * FROM customers;
SELECT * FROM food_items;
SELECT * FROM cart_items;
SELECT * FROM food_orders;
SELECT * FROM order_items;
```

Actual table names depend on entity mappings; inspect the table list if a query reports a missing table. Stop and restart the app and verify the data remains in PostgreSQL.

## Notes

- PostgreSQL credentials must match your local installation. Do not use a production password in source control.
- `spring.jpa.hibernate.ddl-auto=update` is convenient for this case study; use migrations and `validate` for production.
- This is a case-study backend, not a production-secured deployment. Login is not full JWT-based authorization.
