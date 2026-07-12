import { C, PLANS, T, type Lang } from "../data/plans";
import type { CoverageType } from "../types/quote";

export function CoverageSelector({ lang, value, onChange }: {
  lang: Lang; value: CoverageType; onChange: (c: CoverageType) => void;
}) {
  const t = T[lang];
  return (
    <div style={{ display: "grid", gap: 11 }}>
      {PLANS.map((p) => {
        const active = value === p.key;
        const perks = lang === "en" ? p.perksEn : p.perksFr;
        const Icon = p.Icon;
        return (
          <button key={p.key} onClick={() => onChange(p.key)}
            style={{ textAlign: "left", background: active ? C.panel : "transparent",
              border: `1.5px solid ${active ? C.accent : C.line}`, borderRadius: 10,
              padding: "14px 16px", cursor: "pointer" }}>
            <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center" }}>
              <span style={{ display: "flex", alignItems: "center", gap: 9, fontWeight: 600, fontSize: 15 }}>
                <Icon size={17} color={active ? C.accent : C.muted} /> {p.name}
              </span>
              <span style={{ fontFamily: "monospace", fontSize: 13, color: active ? C.accent : C.muted }}>
                ×{p.mult.toFixed(1)} {t.subtotalNote}
              </span>
            </div>
            <div style={{ fontSize: 12, color: C.muted, marginTop: 6, lineHeight: 1.5 }}>{perks.join(" · ")}</div>
          </button>
        );
      })}
    </div>
  );
}