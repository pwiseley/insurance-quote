# Insurance Quote Calculator

[Read in English](README.md)

Outil full-stack d'estimation de primes d'assurance : calcule des soumissions `auto` et `habitation` selon le `profil du conducteur`, les `caractéristiques de la propriété` et les `options de couverture`.

> [Démo en ligne](https://primio.petiton.dev)

## Sommaire

- [Technologies](#technologies)
- [Endpoints de l'API](#endpoints-de-lapi)
- [Exemples d'API](#exemples-dapi)
- [Projets connexes](#projets-connexes)

## Technologies

| Couche | Technologie |
|---|---|
| **Backend** | Java 21, Spring Boot 3.5, Maven |
| **Frontend** | React, TypeScript, Vite |
| **Base de données** | PostgreSQL |
| **Infrastructure** | Docker, Railway, CI/CD |
| **Architecture** | Controller / Service / Repository |

## Endpoints de l'API

> [Documentation de l'API (Swagger)](https://primio.petiton.dev/swagger-ui/index.html)

| Méthode | Route | Rôle |
|---|---|---|
| `POST` | `/api/quotes/auto` | Estimer une prime d'assurance auto |
| `POST` | `/api/quotes/home` | Estimer une prime d'assurance habitation |

Types de couverture : `BASIC`, `STANDARD`, `PREMIUM`.

Les soumissions auto et habitation partagent une API polymorphe reposant sur l'**héritage JPA**. Les règles de tarification forment un ensemble ordonné de facteurs de coût, détaillés dans chaque réponse. Les entrées sont vérifiées avec **Bean Validation** avant tout calcul.

## Exemples d'API

### Requête : soumission auto

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

### Réponse : `201 Created`

La réponse reprend les données du client et détaille chaque facteur de coût ayant contribué au total :

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

### Erreur : `400 Bad Request`

Une entrée invalide est rejetée avant tout calcul de prix :

```json
{
  "driverAge": "Minimum age is 16 years old.",
  "coverageType": "Coverage Type is required."
}
```

## Projets connexes

- [url-shortener](https://github.com/pwiseley/url-shortener) : Spring Boot + Angular, en ligne sur `go.petiton.dev`
- [vehicle-maintenance-tracker](https://github.com/pwiseley/vehicle-maintenance-tracker) : C# / ASP.NET Core + React, en ligne sur `vmt.petiton.dev`
- [Floppa Marketplace](https://github.com/pwiseley/Floppa-app) : API REST Java, architecture en trois couches, CI/CD
- [Portfolio](https://petiton.dev)
