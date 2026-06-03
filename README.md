# 🛡️ Insurance Quote Calculator

> Full-stack insurance premium estimation tool : computes auto and home quotes based on driver profile, vehicle type, and coverage options.

![Status](https://img.shields.io/badge/status-in%20development-yellow)
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.14-brightgreen)
![React](https://img.shields.io/badge/React-TypeScript-blue)
![AWS](https://img.shields.io/badge/deployed-AWS-orange)

> **Note:** The primary repository is private. This is a public mirror for portfolio visibility. Full source will be available upon project completion.

---

## What it does

Primio lets users input their driver profile, vehicle details, and desired coverage type to receive an estimated insurance premium. Business rules are modeled using domain-driven design principles, keeping pricing logic clean and independently testable.

---

## Tech Stack

| Layer | Technology |
|---|---|
| Backend | Java 21, Spring Boot 3.5.14, Maven |
| Frontend | React, TypeScript |
| Database | PostgreSQL |
| Infrastructure | Docker, AWS, CI/CD |
| Architecture | Controller / Service / Repository |

---

## Key Features

- Auto and home insurance quote estimation
- Domain-driven business rules engine (age, vehicle, region, coverage type)
- RESTful API with clean Controller/Service/Repository architecture
- React frontend with real-time quote calculation
- Dockerized and deployed on AWS
- CI/CD pipeline via GitHub Actions

---

## Example HTTP Response (Backend)

```json
{
  "premium": 845.00,
  "factors": [
    { "code": "BASE_PREMIUM", "amount": 500.00 },
    { "code": "YOUNG_DRIVER", "amount": 150.00 },
    { "code": "PAST_ACCIDENTS", "amount": 240.00 },
    { "code": "COVERAGE_MULTIPLIER", "amount": 253.50 }
  ]
}
```

## Live Demo

🔗 Coming soon — `primio.petiton.dev`

---

## Related

- [url-shortener](https://github.com/pwiseley/url-shortener) — Spring Boot + Angular, deployed on Azure
- [Floppa Marketplace](https://github.com/pwiseley/Floppa-app) — Java REST API, three-layer architecture, CI/CD
- [Portfolio](https://petiton.dev)
