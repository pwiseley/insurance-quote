import { Shield, ShieldCheck, ShieldPlus } from "lucide-react";
import type { CoverageType } from "../types/quote";

export type Lang = "en" | "fr";

export const LINKS = {
  github: "https://github.com/pwiseley/insurance-quote",
  docs: "/swagger-ui.html",
};

export const PLANS: {
  key: CoverageType;
  name: string;
  mult: number;
  Icon: typeof Shield;
  perksEn: string[];
  perksFr: string[];
}[] = [
  {
    key: "BASIC", name: "Basic", mult: 1.0, Icon: Shield,
    perksEn: ["Liability", "Third-party damage", "Basic roadside assistance"],
    perksFr: ["Responsabilité civile", "Dommages à autrui", "Assistance routière de base"],
  },
  {
    key: "STANDARD", name: "Standard", mult: 1.3, Icon: ShieldCheck,
    perksEn: ["Everything in Basic", "Collision", "Theft & fire", "Replacement vehicle"],
    perksFr: ["Tout du Basic", "Collision", "Vol et incendie", "Véhicule de remplacement"],
  },
  {
    key: "PREMIUM", name: "Premium", mult: 1.6, Icon: ShieldPlus,
    perksEn: ["Everything in Standard", "All-risk", "Reduced deductible", "24/7 assistance", "New-value protection"],
    perksFr: ["Tout du Standard", "Tous risques", "Franchise réduite", "Assistance 24/7", "Valeur à neuf"],
  },
];

export const FACTOR_LABEL: Record<string, { en: string; fr: string }> = {
  BASE_COST: { en: "Base cost", fr: "Coût de base" },
  YOUNG_DRIVER: { en: "Young driver", fr: "Jeune conducteur" },
  SENIOR_DRIVER: { en: "Senior driver", fr: "Conducteur senior" },
  EXPERIENCED_DRIVER: { en: "Experienced driver", fr: "Conducteur expérimenté" },
  PAST_ACCIDENTS: { en: "Past accidents", fr: "Accidents antérieurs" },
  COVERAGE_ADJUSTMENT: { en: "Coverage adjustment", fr: "Ajustement de couverture" },
  HOME_VALUE: { en: "Property value", fr: "Valeur de la propriété" },
  OLD_BUILDING: { en: "Old building", fr: "Bâtiment ancien" },
  ALARM_DISCOUNT: { en: "Alarm system", fr: "Système d'alarme" },
};

export const AUTO_DEFAULTS = {
  baseCost: 500,
  youngDriverSurcharge: 150,
  seniorDriverSurcharge: 100,
  experiencedDriverDiscount: 80,
  accidentSurcharge: 120,
  youngDriverMaxAge: 25,
  seniorDriverMinAge: 70,
  experiencedMinYears: 10,
};

export const HOME_DEFAULTS = {
  baseCost: 800,
  homeValueRate: 0.002,
  oldBuildingSurcharge: 200,
  alarmDiscount: 100,
  oldBuildingThresholdYears: 30,
};

export const T = {
  en: {
    subtitle: "A simple tool to calculate an insurance quote", docs: "Documentation",
    heading: "Get your quote done", auto: "Auto", home: "Home",
    profile: "Driver profile", property: "Property details", coverage: "Coverage level",
    estimate: "Detailed estimate", calculate: "Calculate quote", calculating: "Calculating…",
    empty: "Fill in the details and calculate to see the price breakdown.",
    subtotalNote: "subtotal", annual: "Annual premium", settings: "Rating settings",
    save: "Save", reset: "Reset to defaults", age: "Age", exp: "Years of experience",
    acc: "Past accidents", homeValue: "Property value", yearBuilt: "Year built",
    alarm: "Alarm system", yes: "Yes", no: "No", years: "yrs", rights: "All rights reserved",
    errorTitle: "Could not calculate",
  },
  fr: {
    subtitle: "Un outil simple pour calculer une soumission d'assurance", docs: "Documentation",
    heading: "Obtenez votre soumission", auto: "Auto", home: "Habitation",
    profile: "Profil du conducteur", property: "Détails de la propriété", coverage: "Niveau de couverture",
    estimate: "Estimation détaillée", calculate: "Calculer la soumission", calculating: "Calcul en cours…",
    empty: "Remplissez les détails et calculez pour voir le détail du prix.",
    subtotalNote: "sous-total", annual: "Prime annuelle", settings: "Paramètres de tarification",
    save: "Sauvegarder", reset: "Valeurs par défaut", age: "Âge", exp: "Années d'expérience",
    acc: "Accidents antérieurs", homeValue: "Valeur de la propriété", yearBuilt: "Année de construction",
    alarm: "Système d'alarme", yes: "Oui", no: "Non", years: "ans", rights: "Tous droits réservés",
    errorTitle: "Calcul impossible",
  },
};

export const money = (n: number) =>
  n.toLocaleString("en-CA", { minimumFractionDigits: 2, maximumFractionDigits: 2 });
export const signed = (n: number) =>
  (n >= 0 ? "+" : "\u2212") + money(Math.abs(n));

export const C = {
  ink: "#1A1F1C", paper: "#FBFAF7", panel: "#FFFFFF", line: "#E4E1D9",
  accent: "#1F4D3A", accentSoft: "#EAF1EC", muted: "#6E6A60", debit: "#8A3A2E", credit: "#1F4D3A",
};