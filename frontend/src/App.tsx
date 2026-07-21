import { useState } from "react";
import { SlidersHorizontal } from "lucide-react";
import {
  C, T, AUTO_DEFAULTS, HOME_DEFAULTS, type Lang,
} from "./data/plans";
import type {
  QuoteMode, CoverageType, QuoteResponse,
  AutoRatingParameters, HomeRatingParameters,
} from "./types/quote";
import { calculateAutoQuote, calculateHomeQuote } from "./api/quoteApi";
import { Header } from "./components/Header";
import { QuoteResult } from "./components/QuoteResult";
import { CoverageSelector } from "./components/CoverageSelector";
import { SettingsDrawer } from "./components/SettingsDrawer";
import { SectionLabel, Field, Toggle } from "./components/Ui";

export default function App() {
  const [lang, setLang] = useState<Lang>("en");
  const [mode, setMode] = useState<QuoteMode>("AUTO");
  const [coverage, setCoverage] = useState<CoverageType>("STANDARD");

  const [autoParams, setAutoParams] = useState<AutoRatingParameters>({ ...AUTO_DEFAULTS });
  const [homeParams, setHomeParams] = useState<HomeRatingParameters>({ ...HOME_DEFAULTS });
  const [showSettings, setShowSettings] = useState(false);
  const [draft, setDraft] = useState<AutoRatingParameters | HomeRatingParameters>({ ...AUTO_DEFAULTS });

  const [age, setAge] = useState(22);
  const [exp, setExp] = useState(3);
  const [acc, setAcc] = useState(1);
  const [homeValue, setHomeValue] = useState(350000);
  const [yearBuilt, setYearBuilt] = useState(1985);
  const [alarm, setAlarm] = useState(true);

  const [result, setResult] = useState<QuoteResponse | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [loading, setLoading] = useState(false);

  const t = T[lang];

  const runQuote = async () => {
    setLoading(true);
    setError(null);
    setResult(null);
    try {
      const res =
        mode === "AUTO"
          ? await calculateAutoQuote({
              type: "AUTO", driverAge: age, yearsOfExperience: exp,
              pastAccidents: acc, coverageType: coverage, parameters: autoParams,
            })
          : await calculateHomeQuote({
              type: "HOME", homeValue, yearBuilt, hasAlarm: alarm,
              coverageType: coverage, parameters: homeParams,
            });
      setResult(res);
    } catch (e) {
      setError(e instanceof Error ? e.message : String(e));
    } finally {
      setLoading(false);
    }
  };

  const openSettings = () => {
    setDraft(mode === "AUTO" ? { ...autoParams } : { ...homeParams });
    setShowSettings(true);
  };
  const saveSettings = () => {
    if (mode === "AUTO") setAutoParams(draft as AutoRatingParameters);
    else setHomeParams(draft as HomeRatingParameters);
    setShowSettings(false);
  };

  return (
    <div style={{ minHeight: "100vh", background: C.paper, color: C.ink, fontFamily: "'Inter', system-ui, sans-serif" }}>
      <div style={{ maxWidth: 1120, margin: "0 auto", padding: "clamp(18px,4vw,40px)" }}>
        <Header lang={lang} setLang={setLang} />

        <div style={{ display: "flex", alignItems: "center", justifyContent: "center", position: "relative", margin: "40px 0 36px" }}>
          <div style={{ textAlign: "center" }}>
            <h2 style={{ margin: "0 0 18px", fontSize: "clamp(22px,3vw,30px)", fontWeight: 600, letterSpacing: "-0.02em" }}>{t.heading}</h2>
            <Toggle options={["AUTO", "HOME"] as QuoteMode[]} value={mode}
              onChange={(m) => { setMode(m); setResult(null); setError(null); }}
              labels={{ AUTO: t.auto, HOME: t.home }} />
          </div>
          <button onClick={openSettings} title={t.settings}
            style={{ position: "absolute", right: 0, background: "#fff", border: `1px solid ${C.line}`,
              borderRadius: 9, padding: "10px 12px", cursor: "pointer", color: C.ink, display: "flex", alignItems: "center" }}>
            <SlidersHorizontal size={17} />
          </button>
        </div>

        <div className="quote-grid">
          <section>
            <SectionLabel n="01" text={mode === "AUTO" ? t.profile : t.property} />
            {mode === "AUTO" ? (
              <>
                <Field label={t.age} value={age} onChange={setAge} suffix={t.years} />
                <Field label={t.exp} value={exp} onChange={setExp} suffix={t.years} />
                <Field label={t.acc} value={acc} onChange={setAcc} suffix="" />
              </>
            ) : (
              <>
                <Field label={t.homeValue} value={homeValue} onChange={setHomeValue} suffix="$" />
                <Field label={t.yearBuilt} value={yearBuilt} onChange={setYearBuilt} suffix="" />
                <div style={{ marginBottom: 16 }}>
                  <label style={{ display: "block", fontSize: 13, color: C.muted, marginBottom: 6 }}>{t.alarm}</label>
                  <div style={{ display: "flex", border: `1px solid ${C.line}`, borderRadius: 8, overflow: "hidden", width: "fit-content" }}>
                    {[true, false].map((v) => (
                      <button key={String(v)} onClick={() => setAlarm(v)}
                        style={{ border: "none", padding: "10px 24px", fontSize: 14, cursor: "pointer",
                          background: alarm === v ? C.accent : "#fff", color: alarm === v ? "#fff" : C.muted, fontWeight: 600 }}>
                        {v ? t.yes : t.no}
                      </button>
                    ))}
                  </div>
                </div>
              </>
            )}

            <div style={{ marginTop: 30 }}>
              <SectionLabel n="02" text={t.coverage} />
              <CoverageSelector lang={lang} value={coverage} onChange={setCoverage} />
            </div>

            <button onClick={runQuote} disabled={loading}
              style={{ marginTop: 26, width: "100%", background: C.accent, color: "#fff", border: "none",
                borderRadius: 10, padding: "15px", fontSize: 15, fontWeight: 600,
                cursor: loading ? "wait" : "pointer", opacity: loading ? 0.7 : 1 }}>
              {loading ? t.calculating : t.calculate}
            </button>
          </section>

          <section className="quote-result">
            <SectionLabel n="03" text={t.estimate} />
            <QuoteResult lang={lang} result={result} error={error} loading={loading} />
          </section>
        </div>

        <footer style={{ marginTop: 56, paddingTop: 20, borderTop: `1px solid ${C.line}`, fontSize: 12, color: C.muted, textAlign: "center" }}>
          © 2026 · {t.rights}
        </footer>
      </div>

      {showSettings && (
        <SettingsDrawer lang={lang} mode={mode} draft={draft} setDraft={setDraft}
          onClose={() => setShowSettings(false)} onSave={saveSettings} />
      )}

      <style>{`@keyframes slideIn { from { transform: translateX(100%);} to { transform: translateX(0);} }
        input[type=number]::-webkit-inner-spin-button { opacity: 0.3; }
        * { box-sizing: border-box; }`}</style>
    </div>
  );
}