# Vauldex Final Exam

A small full-stack demo project: a Spring Boot + Kotlin backend and a Vue 3 + Vite frontend.

This README explains what the app contains, how to run it in development and production, and how to configure the PostgreSQL database used by the backend. It also includes the provided SQL insert statements for activity types.

## Repository layout

- `backend/` — Spring Boot (Kotlin) application. Uses Gradle wrapper and Flyway for DB migrations.
- `frontend/` — Vue 3 + Vite frontend (TypeScript).
- `insert-activity-types.sql` — SQL file with example inserts for the `activity_types` table.

## Quick prerequisites

- macOS / Linux / Windows with zsh (examples use zsh/macOS syntax)
- Java JDK 21 (the backend Gradle toolchain is configured for Java 21)
- Node.js (see `frontend/package.json` engines). This project expects Node 20.x or newer. `pnpm` is recommended but `npm`/`yarn` will also work.
- Docker (optional, recommended for running PostgreSQL during development)

## Database (PostgreSQL)

The backend is configured by default to use PostgreSQL. Defaults provided in `backend/src/main/resources/application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/finalexam
spring.datasource.username=fvismanos
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
jwt.secret= b84bc89e9cd3a5f92bd597398c803c3f
```

Notes:
- Flyway is enabled and will run migrations from `classpath:db/migration` on startup.
- The default DB user is `fvismanos` and the default DB name is `finalexam`. Update the password before running in production.

### Start a local PostgreSQL for development (Docker)

Example (macOS / zsh):

```bash
docker run --name finalexam-db \
	-e POSTGRES_USER=fvismanos \
	-e POSTGRES_PASSWORD=secret \
	-e POSTGRES_DB=finalexam \
	-p 5432:5432 -d postgres:15
```

After starting the DB, update `spring.datasource.password` in `backend/src/main/resources/application.properties` to `secret` (or set via environment variable), or set the env var:

```bash
export SPRING_DATASOURCE_PASSWORD=secret
```

### Apply the included activity types SQL

The repository includes `insert-activity-types.sql` with a set of `INSERT` statements. To run it against the local DB (example using `psql`):

```bash
# if password is required, set PGPASSWORD for the command
export PGPASSWORD=secret
psql -h localhost -U fvismanos -d finalexam -f insert-activity-types.sql
```

The file `insert-activity-types.sql` contains:

```
INSERT INTO activity_types (name) values ('Activity 1');
INSERT INTO activity_types (name) values ('Activity 2');
INSERT INTO activity_types (name) values ('Activity 3');
INSERT INTO activity_types (name) values ('Activity 4');
INSERT INTO activity_types (name) values ('Activity 5');
```

## Running the backend (development)

1. Ensure Java 21 is installed and available on your PATH (or let Gradle toolchain download if supported).
2. Ensure the PostgreSQL database from above is running and `application.properties` credentials are correct.
3. From the project root:

```bash
cd backend
./gradlew bootRun
```

This will start the Spring Boot application on the configured port (default 8080).

Alternatively you can run from your IDE (IntelliJ IDEA, VS Code) by importing the Gradle project.

## Building and running backend (production)

1. Build a runnable jar:

```bash
cd backend
./gradlew clean bootJar
```

2. Run the generated jar (replace the jar name with the actual file in `build/libs`):

```bash
java -jar build/libs/*-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

You can override database properties via environment variables or command-line parameters. Examples:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://dbhost:5432/finalexam
export SPRING_DATASOURCE_USERNAME=fvismanos
export SPRING_DATASOURCE_PASSWORD=SuperSecret

# Or pass as JVM args
java -jar build/libs/*.jar --spring.datasource.password=SuperSecret
```

To override the JWT secret at runtime you can provide the property on the command line:

```bash
java -jar build/libs/*.jar --jwt.secret=YOUR_SECRET_VALUE
```

## Running the frontend

The frontend is in `frontend/` and uses Vite.

Install dependencies (use `pnpm` if available; otherwise `npm`):

```bash
cd frontend
pnpm install    # or: npm install
pnpm dev        # or: npm run dev
```

Build for production:

```bash
pnpm build      # or: npm run build
pnpm preview    # run a local preview server
```

Serving the built frontend from the backend (optional):
1. Build frontend (`pnpm build`).
2. Copy the `dist/` output into the backend static resources so Spring can serve it:

```bash
rm -rf backend/src/main/resources/static/*
cp -R frontend/dist/* backend/src/main/resources/static/
cd backend
./gradlew clean bootJar
java -jar build/libs/*.jar
```

This will let the backend serve the built frontend assets from `/`.

## Environment & configuration summary

- Backend properties file: `backend/src/main/resources/application.properties` (default values shown earlier).
- Key properties you may need to override:
	- `spring.datasource.url`
	- `spring.datasource.username`
	- `spring.datasource.password`
	- `jwt.secret` (can be provided via command-line arg `--jwt.secret=<value>`)

## Where to look in the code

- Backend main source: `backend/src/main/kotlin` (Spring controllers, services, repositories).
- Flyway DB migrations: `backend/src/main/resources/db/migration` (applied automatically at startup).
- Frontend source: `frontend/src` (Vue components, pages, services).

## Troubleshooting

- If Flyway fails, check DB connectivity and credentials.
- If the app can't connect to Postgres, confirm the container is running and port 5432 is forwarded, and that the DB user/password/database exist.
- If CORS or API errors appear when running frontend + backend separately, ensure frontend calls the correct backend URL (check `frontend/src/lib/utils.ts` or services for base URL).

## Notes

- This README focuses on local development and simple production runs. For a production deployment consider using a proper secrets manager for DB passwords and JWT secrets, and run PostgreSQL on a managed or hardened instance.
