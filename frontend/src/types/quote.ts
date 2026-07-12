export type CoverageType = "BASIC" | "STANDARD" | "PREMIUM";
export type QuoteMode = "AUTO" | "HOME";

export interface AutoRatingParameters {
  baseCost: number;
  youngDriverSurcharge: number;
  seniorDriverSurcharge: number;
  experiencedDriverDiscount: number;
  accidentSurcharge: number;
  youngDriverMaxAge: number;
  seniorDriverMinAge: number;
  experiencedMinYears: number;
}

export interface HomeRatingParameters {
  baseCost: number;
  homeValueRate: number;
  oldBuildingSurcharge: number;
  alarmDiscount: number;
  oldBuildingThresholdYears: number;
}

export interface AutoQuoteRequest {
  type: "AUTO";
  driverAge: number;
  yearsOfExperience: number;
  pastAccidents: number;
  coverageType: CoverageType;
  parameters?: AutoRatingParameters;
}

export interface HomeQuoteRequest {
  type: "HOME";
  homeValue: number;
  yearBuilt: number;
  hasAlarm: boolean;
  coverageType: CoverageType;
  parameters?: HomeRatingParameters;
}

export interface CostFactor {
  code: string;
  amount: number;
}

export interface QuoteResponse {
  quoteId: number;
  coverageType?: CoverageType;
  totalCost: number;
  clientInput?: unknown;
  factors: CostFactor[];
}