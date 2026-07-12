import { X } from "lucide-react";
import { C, T, AUTO_DEFAULTS, HOME_DEFAULTS, type Lang } from "../data/plans";
import type { QuoteMode, AutoRatingParameters, HomeRatingParameters } from "../types/quote";
import { DField } from "./Ui";

export function SettingsDrawer({ lang, mode, draft, setDraft, onClose, onSave }: {
  lang: Lang;
  mode: QuoteMode;
  draft: AutoRatingParameters | HomeRatingParameters;
  setDraft: (d: AutoRatingParameters | HomeRatingParameters) => void;
  onClose: () => void;
  onSave: () => void;
}) {
  const t = T[lang];
  const a = draft as AutoRatingParameters;
  const h = draft as HomeRatingParameters;

  return (
    <>
      <div onClick={onClose} style={{ position: "fixed", inset: 0, background: "rgba(26,31,28,0.35)", zIndex: 40 }} />
      <div style={{ position: "fixed", top: 0, right: 0, height: "100%", width: "min(420px,90vw)", background: C.paper,
        zIndex: 50, boxShadow: "-8px 0 40px rgba(0,0,0,0.15)", padding: "28px 26px", overflowY: "auto",
        animation: "slideIn .25s ease" }}>
        <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: 6 }}>
          <h3 style={{ margin: 0, fontSize: 18, fontWeight: 600 }}>{t.settings}</h3>
          <button onClick={onClose} style={{ background: "none", border: "none", cursor: "pointer", color: C.muted }}>
            <X size={20} />
          </button>
        </div>
        <div style={{ height: 1, background: C.line, margin: "16px 0 22px" }} />

        {mode === "AUTO" ? (
          <>
            <DField label={lang === "en" ? "Base cost" : "Coût de base"} v={a.baseCost} on={(x) => setDraft({ ...a, baseCost: x })} />
            <DField label={lang === "en" ? "Young driver surcharge" : "Surcharge jeune conducteur"} v={a.youngDriverSurcharge} on={(x) => setDraft({ ...a, youngDriverSurcharge: x })} />
            <DField label={lang === "en" ? "Senior driver surcharge" : "Surcharge conducteur senior"} v={a.seniorDriverSurcharge} on={(x) => setDraft({ ...a, seniorDriverSurcharge: x })} />
            <DField label={lang === "en" ? "Experienced driver discount" : "Rabais conducteur expérimenté"} v={a.experiencedDriverDiscount} on={(x) => setDraft({ ...a, experiencedDriverDiscount: x })} />
            <DField label={lang === "en" ? "Accident surcharge" : "Surcharge par accident"} v={a.accidentSurcharge} on={(x) => setDraft({ ...a, accidentSurcharge: x })} />
            <div style={{ height: 1, background: C.line, margin: "6px 0 18px" }} />
            <DField label={lang === "en" ? "Young driver max age" : "Âge max jeune conducteur"} v={a.youngDriverMaxAge} on={(x) => setDraft({ ...a, youngDriverMaxAge: x })} />
            <DField label={lang === "en" ? "Senior driver min age" : "Âge min conducteur senior"} v={a.seniorDriverMinAge} on={(x) => setDraft({ ...a, seniorDriverMinAge: x })} />
            <DField label={lang === "en" ? "Experienced min years" : "Années min expérience"} v={a.experiencedMinYears} on={(x) => setDraft({ ...a, experiencedMinYears: x })} />
          </>
        ) : (
          <>
            <DField label={lang === "en" ? "Base cost" : "Coût de base"} v={h.baseCost} on={(x) => setDraft({ ...h, baseCost: x })} />
            <DField label={lang === "en" ? "Home value rate" : "Taux valeur propriété"} v={h.homeValueRate} on={(x) => setDraft({ ...h, homeValueRate: x })} step={0.001} />
            <DField label={lang === "en" ? "Old building surcharge" : "Surcharge bâtiment ancien"} v={h.oldBuildingSurcharge} on={(x) => setDraft({ ...h, oldBuildingSurcharge: x })} />
            <DField label={lang === "en" ? "Alarm discount" : "Rabais alarme"} v={h.alarmDiscount} on={(x) => setDraft({ ...h, alarmDiscount: x })} />
            <div style={{ height: 1, background: C.line, margin: "6px 0 18px" }} />
            <DField label={lang === "en" ? "Old building threshold (yrs)" : "Seuil bâtiment ancien (ans)"} v={h.oldBuildingThresholdYears} on={(x) => setDraft({ ...h, oldBuildingThresholdYears: x })} />
          </>
        )}

        <div style={{ display: "flex", gap: 10, marginTop: 24 }}>
          <button onClick={() => setDraft(mode === "AUTO" ? { ...AUTO_DEFAULTS } : { ...HOME_DEFAULTS })}
            style={{ flex: 1, background: "transparent", border: `1px solid ${C.line}`, borderRadius: 9,
              padding: "12px", fontSize: 14, cursor: "pointer", color: C.muted }}>{t.reset}</button>
          <button onClick={onSave}
            style={{ flex: 1, background: C.accent, color: "#fff", border: "none", borderRadius: 9,
              padding: "12px", fontSize: 14, fontWeight: 600, cursor: "pointer" }}>{t.save}</button>
        </div>
      </div>
    </>
  );
}