import { FaGithub } from "react-icons/fa";
import { C, LINKS, T, type Lang } from "../data/plans";

export function Header({ lang, setLang }: { lang: Lang; setLang: (l: Lang) => void }) {
  const t = T[lang];
  return (
    <header style={{ display: "flex", justifyContent: "space-between", alignItems: "flex-start",
      paddingBottom: 22, borderBottom: `1px solid ${C.line}`, flexWrap: "wrap", gap: 16 }}>
      <div>
        <h1 style={{ margin: 0, fontSize: "clamp(24px,3.4vw,34px)", fontWeight: 800, letterSpacing: "-0.02em",
          fontFamily: "'Archivo Narrow','Arial Narrow',sans-serif", textTransform: "uppercase",
          transform: "scaleY(1.18)", transformOrigin: "left", whiteSpace: "nowrap" }}>
          Insurance Quote
        </h1>
        <div style={{ fontSize: 12, color: C.muted, marginTop: 12 }}>{t.subtitle}</div>
      </div>
      <div style={{ display: "flex", alignItems: "center", gap: 18, fontSize: 13 }}>
        <a href={LINKS.github} target="_blank" rel="noreferrer"
          style={{ color: C.ink, textDecoration: "none", display: "flex", alignItems: "center", gap: 6 }}>
          <FaGithub size={15} /> GitHub
        </a>
        <a href={LINKS.docs} target="_blank" rel="noreferrer" style={{ color: C.ink, textDecoration: "none" }}>
          {t.docs}
        </a>
        <div style={{ display: "flex", border: `1px solid ${C.line}`, borderRadius: 7, overflow: "hidden" }}>
          {(["en", "fr"] as Lang[]).map((l) => (
            <button key={l} onClick={() => setLang(l)}
              style={{ border: "none", padding: "5px 11px", fontSize: 12, cursor: "pointer",
                textTransform: "uppercase", fontWeight: 600, letterSpacing: "0.04em",
                background: lang === l ? C.ink : "transparent", color: lang === l ? "#fff" : C.muted }}>
              {l}
            </button>
          ))}
        </div>
      </div>
    </header>
  );
}