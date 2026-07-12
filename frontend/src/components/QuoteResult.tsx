import { C, FACTOR_LABEL, T, money, signed, type Lang } from "../data/plans";
import type { QuoteResponse } from "../types/quote";

export function QuoteResult({ lang, result, error, loading }: {
  lang: Lang; result: QuoteResponse | null; error: string | null; loading: boolean;
}) {
  const t = T[lang];

  if (loading) {
    return (
      <div style={{ border: `1px dashed ${C.line}`, borderRadius: 12, padding: "48px 24px",
        textAlign: "center", color: C.muted, fontSize: 14 }}>{t.calculating}</div>
    );
  }

  if (error) {
    return (
      <div style={{ border: `1px solid ${C.debit}`, background: "#FBF0EE", borderRadius: 12, padding: "20px 22px" }}>
        <div style={{ fontSize: 13, fontWeight: 600, color: C.debit, marginBottom: 6 }}>{t.errorTitle}</div>
        <div style={{ fontSize: 13, color: C.ink, fontFamily: "monospace", lineHeight: 1.5 }}>{error}</div>
      </div>
    );
  }

  if (!result) {
    return (
      <div style={{ border: `1px dashed ${C.line}`, borderRadius: 12, padding: "48px 24px",
        textAlign: "center", color: C.muted, fontSize: 14 }}>{t.empty}</div>
    );
  }

  return (
    <div style={{ background: C.panel, border: `1px solid ${C.line}`, borderRadius: 12, overflow: "hidden" }}>
      <div style={{ padding: "8px 22px" }}>
        {result.factors.map((f, i) => {
          const isCredit = f.amount < 0;
          const isBase = f.code === "BASE_COST";
          return (
            <div key={i} style={{ display: "flex", justifyContent: "space-between", alignItems: "baseline",
              padding: "13px 0", borderBottom: i < result.factors.length - 1 ? `1px solid ${C.line}` : "none" }}>
              <span style={{ fontSize: 14 }}>{FACTOR_LABEL[f.code]?.[lang] ?? f.code}</span>
              <span style={{ fontFamily: "monospace", fontSize: 14,
                color: isCredit ? C.credit : isBase ? C.ink : C.debit }}>{signed(f.amount)} $</span>
            </div>
          );
        })}
      </div>
      <div style={{ background: C.ink, color: "#fff", padding: "20px 22px",
        display: "flex", justifyContent: "space-between", alignItems: "baseline" }}>
        <span style={{ fontSize: 12, letterSpacing: "0.14em", textTransform: "uppercase", opacity: 0.7 }}>{t.annual}</span>
        <span style={{ fontFamily: "'Georgia',serif", fontSize: 32, fontWeight: 600 }}>{money(result.totalCost)} $</span>
      </div>
    </div>
  );
}