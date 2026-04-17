import { useState } from "react";

function DiffieHellman() {
  const [p, setP] = useState("");
  const [g, setG] = useState("");
  const [a, setA] = useState("");
  const [b, setB] = useState("");
  const [result, setResult] = useState("");

  const generateKey = async () => {
    const res = await fetch("http://localhost:3000/api/dh", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ p, g, a, b })
    });

    const data = await res.json();
    setResult(data.result);
  };

  return (
    <div>
      <h2>Diffie-Hellman Algorithm</h2>

      <input
        placeholder="Enter prime number (p)"
        value={p}
        onChange={(e) => setP(e.target.value)}
      />
      <br /><br />

      <input
        placeholder="Enter primitive root (g)"
        value={g}
        onChange={(e) => setG(e.target.value)}
      />
      <br /><br />

      <input
        placeholder="Enter private key of A (a)"
        value={a}
        onChange={(e) => setA(e.target.value)}
      />
      <br /><br />

      <input
        placeholder="Enter private key of B (b)"
        value={b}
        onChange={(e) => setB(e.target.value)}
      />
      <br /><br />

      <button onClick={generateKey}>Generate Shared Key</button>

      <pre style={{ whiteSpace: "pre-wrap", textAlign: "left" }}>
        {result}
      </pre>
    </div>
  );
}

export default DiffieHellman;
