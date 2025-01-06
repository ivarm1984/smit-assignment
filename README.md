Starting postgres and keycloak

docker compose up -d

# Croudsourced books lending


## Getting Started

To run the project locally:

1. Start postgresql and keycloak:
   ```bash
    docker compose up keycloak_web -d
    docker compose up postgres -d

2. dev profile creates keycloak realm, client and users with some books
3. Build backend
   ```bash
    ./gradlew build

4. Start frontend and backend
   ```bash
    docker compose up -d

5. Open https://localhost:4200 and log in with demo users and try lending. User:Password
   * demo1:1234
   * demo2:1234

