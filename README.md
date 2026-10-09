# Insurance Quote Calculator

[Lire en français](README.FR.md)

Full-stack insurance premium estimation tool: computes `auto` and `home` quotes based on `driver profile`, `property details`, and `coverage options`.

> [Live Demo](https://primio.petiton.dev)

## Contents

- [Tech Stack](#tech-stack)
- [API Endpoints](#api-endpoints)
- [API Examples](#api-examples)
- [Related](#related)

## Tech Stack

| Layer | Technology |
|---|---|
| **Backend** | Java 21, Spring Boot 3.5, Maven |
| **Frontend** | React, TypeScript, Vite |
| **Database** | PostgreSQL |
| **Infrastructure** | Docker, Railway, CI/CD |
| **Architecture** | Controller / Service / Repository |

## API Endpoints

> [API Docs (Swagger)](https://primio.petiton.dev/swagger-ui/index.html)

| Method | Route | Purpose |
|---|---|---|
| `POST` | `/api/quotes/auto` | Estimate an auto insurance premium |
| `POST` | `/api/quotes/home` | Estimate a home insurance premium |

Coverage types: `BASIC`, `STANDARD`, `PREMIUM`.

Auto and home quotes share a polymorphic API backed by **JPA inheritance**. Pricing rules are an ordered set of cost factors, returned itemized in every response. Inputs are checked with **Bean Validation** before any pricing runs.

## API Examples

### Request: auto quote

```http
POST /api/quotes/auto
Content-Type: application/json
```

```json
{
  "driverAge": 22,
  "yearsOfExperience": 2,
  "pastAccidents": 1,
  "coverageType": "STANDARD"
}
```

### Response: `201 Created`

The response echoes the client input and itemizes each cost factor that contributed to the total:

```json
{
  "quoteId": 1,
  "clientInput": {
    "driverAge": 22,
    "yearsOfExperience": 2,
    "pastAccidents": 1,
    "coverageType": "STANDARD"
  },
  "totalCost": 845.00,
  "factors": [
    { "code": "BASE_COST", "amount": 500.00 },
    { "code": "YOUNG_DRIVER", "amount": 150.00 },
    { "code": "PAST_ACCIDENTS", "amount": 120.00 },
    { "code": "COVERAGE_ADJUSTMENT", "amount": 75.00 }
  ]
}
```

### Error: `400 Bad Request`

Invalid input is rejected before any pricing runs:

```json
{
  "driverAge": "Minimum age is 16 years old.",
  "coverageType": "Coverage Type is required."
}
```

## Related

- [url-shortener](https://github.com/pwiseley/url-shortener): Spring Boot + Angular, live at `go.petiton.dev`
- [vehicle-maintenance-tracker](https://github.com/pwiseley/vehicle-maintenance-tracker): C# / ASP.NET Core + React, live at `vmt.petiton.dev`
- [Floppa Marketplace](https://github.com/pwiseley/Floppa-app): Java REST API, three-layer architecture, CI/CD
- [Portfolio](https://petiton.dev)
