# PostgreSQL setup

1. Install/start PostgreSQL and open pgAdmin.
2. Connect to the server (commonly localhost:5432).
3. Open Query Tool on the default `postgres` database and run `create_database.sql` (only once).
4. In Eclipse, set environment variables if your PostgreSQL credentials differ from the defaults:
   - `DB_URL=jdbc:postgresql://localhost:5432/food_delivery_db`
   - `DB_USERNAME=postgres`
   - `DB_PASSWORD=YOUR_POSTGRES_PASSWORD`
5. Run `FoodDeliveryApplication.java`. Hibernate creates/updates tables from the entity classes.
6. Refresh `food_delivery_db` > Schemas > public > Tables in pgAdmin. The application seeds sample food items only when the food_items table is empty.

Do not commit real database passwords to source control. The `postgres` password in application.properties is only a local fallback; change it or set DB_PASSWORD.
