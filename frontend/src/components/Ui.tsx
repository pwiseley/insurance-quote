import { C } from "../data/plans";

export function SectionLabel({ n, text }: { n: string; text: string }) {
  return (
    <div style={{ display: "flex", alignItems: "center", gap: 10, marginBottom: 18 }}>
      <span style={{ fontFamily: "monospace", fontSize: 12, color: C.accent }}>{n}</span>
      <span style={{ fontSize: 12, letterSpacing: "0.14em", textTransform: "uppercase", color: C.muted }}>{text}</span>
      <span style={{ flex: 1, height: 1, background: C.line }} />
    </div>
  );
}

export function Field({ label, value, onChange, suffix }: {
  label: string; value: number; onChange: (n: number) => void; suffix: string;
}) {
  return (
    <div style={{ marginBottom: 16 }}>
      <label style={{ display: "block", fontSize: 13, color: C.muted, marginBottom: 6 }}>{label}</label>
      <div style={{ display: "flex", alignItems: "center", border: `1px solid ${C.line}`,
        borderRadius: 8, background: "#fff", overflow: "hidden" }}>
        <input type="number" value={value} onChange={(e) => onChange(Number(e.target.value))}
          style={{ flex: 1, border: "none", outline: "none", padding: "12px 14px",
            fontSize: 15, fontFamily: "monospace", background: "transparent", color: C.ink }} />
        {suffix && <span style={{ padding: "0 14px", fontSize: 13, color: C.muted }}>{suffix}</span>}
      </div>
    </div>
  );
}

export function DField({ label, v, on, step = 1 }: {
  label: string; v: number; on: (n: number) => void; step?: number;
}) {
  return (
    <div style={{ marginBottom: 15 }}>
      <label style={{ display: "block", fontSize: 13, color: C.muted, marginBottom: 6 }}>{label}</label>
      <input type="number" step={step} value={v} onChange={(e) => on(Number(e.target.value))}
        style={{ width: "100%", border: `1px solid ${C.line}`, borderRadius: 8, padding: "11px 13px",
          fontSize: 15, fontFamily: "monospace", background: "#fff", color: C.ink, boxSizing: "border-box" }} />
    </div>
  );
}

export function Toggle<T extends string>({ options, value, onChange, labels }: {
  options: T[]; value: T; onChange: (v: T) => void; labels?: Record<string, string>;
}) {
  return (
    <div style={{ display: "inline-flex", border: `1px solid ${C.line}`, borderRadius: 9, overflow: "hidden", background: "#fff" }}>
      {options.map((o) => (
        <button key={o} onClick={() => onChange(o)}
          style={{ border: "none", padding: "10px 30px", fontSize: 14, cursor: "pointer", fontWeight: 600,
            background: value === o ? C.accent : "transparent", color: value === o ? "#fff" : C.muted }}>
          {labels?.[o] ?? o}
        </button>
      ))}
    </div>
  );
}