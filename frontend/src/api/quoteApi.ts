import type { AutoQuoteRequest, HomeQuoteRequest, QuoteResponse } from "../types/quote";

const API_BASE = import.meta.env.VITE_API_BASE ?? "";

async function postQuote(
  path: string,
  body: AutoQuoteRequest | HomeQuoteRequest
): Promise<QuoteResponse> {
  const res = await fetch(`${API_BASE}${path}`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(body),
  });

  if (!res.ok) {
    let detail = "";
    try {
      const data = await res.json();
      detail = data?.error ?? JSON.stringify(data);
    } catch {
      detail = res.statusText;
    }
    throw new Error(`Request failed (${res.status}): ${detail}`);
  }

  return res.json();
}

export const calculateAutoQuote = (req: AutoQuoteRequest) =>
  postQuote("/api/quotes/auto", req);

export const calculateHomeQuote = (req: HomeQuoteRequest) =>
  postQuote("/api/quotes/home", req);