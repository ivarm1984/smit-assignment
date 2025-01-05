Starting postgres and keycloak

docker compose up -d

# Croudsourced books lending


## Getting Started

To run the project locally:

1. Start postgresql and keycloak:
   ```bash
    docker compose up -d

2. dev profile creates keycloak realm, client and users with some books
3. Start backend
   ```bash
    ./gradlew bootRun

4. Start frontend
   ```bash
    cd frontend
    ng serve -o

5. Log in with demo users and try lending. User:Password
   * demo1:1234
   * demo2:1234