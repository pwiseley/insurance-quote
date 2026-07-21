# 🛡️ Insurance Quote Calculator

> Full-stack insurance premium estimation tool: computes auto and home quotes based on driver profile, property details, and coverage options.

### [🚀 Live Demo](https://primio.petiton.dev) · [📖 API Docs (Swagger)](https://primio.petiton.dev/swagger-ui/index.html)

![Status](https://img.shields.io/badge/status-live-brightgreen)
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5-brightgreen)
![React](https://img.shields.io/badge/React-TypeScript-blue)
![PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-blue)
![Railway](https://img.shields.io/badge/deployed-Railway-purple)

---

## What it does

Primio lets users enter a driver profile or property details, choose a coverage level, and receive an estimated insurance premium broken down by risk factor. Auto and home quotes are modeled as separate request types over a shared polymorphic API, and pricing rules are expressed as an ordered set of cost factors, keeping the business logic clean and independently testable.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 21, Spring Boot 3.5, Maven |
| Frontend | React, TypeScript, Vite |
| Database | PostgreSQL |
| Infrastructure | Docker, Railway, CI/CD |
| Architecture | Controller / Service / Repository |

---

## Key Features

- Auto and home insurance quote estimation through a single polymorphic API
- Rule-based pricing engine returning an itemized breakdown of every risk factor
- JPA inheritance modeling the shared quote domain with type-specific rating
- Bean Validation on all inputs (age bounds, positive values, required fields)
- RESTful API with clean Controller / Service / Repository layering
- React frontend with real-time quote calculation
- Interactive API documentation via Swagger / OpenAPI
- Dockerized and deployed on Railway

---

## API Endpoints

| Method | Route | Purpose |
|---|---|---|
| `POST` | `/api/quotes/auto` | Estimate an auto insurance premium |
| `POST` | `/api/quotes/home` | Estimate a home insurance premium |

Coverage types: `BASIC`, `STANDARD`, `PREMIUM`. Full interactive documentation is available at **[/swagger-ui/index.html](https://primio.petiton.dev/swagger-ui/index.html)**.

---

## API Examples

### Request — auto quote

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

### Successful response — `201 Created`

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

### Error response — `400 Bad Request` (validation failure)

Invalid input is rejected before any pricing runs:

```json
{
  "driverAge": "Minimum age is 16 years old.",
  "coverageType": "Coverage Type is required."
}
```

See the full request/response schemas at **[/swagger-ui/index.html](https://primio.petiton.dev/swagger-ui/index.html)**.

---

## Related

- [url-shortener](https://github.com/pwiseley/url-shortener): Spring Boot + Angular, live at `go.petiton.dev`
- [vehicle-maintenance-tracker](https://github.com/pwiseley/vehicle-maintenance-tracker): C# / ASP.NET Core + React, live at `vmt.petiton.dev`
- [Floppa Marketplace](https://github.com/pwiseley/Floppa-app): Java REST API, three-layer architecture, CI/CD
- [Portfolio](https://petiton.dev)
