import { useState } from "react";

function SDES() {
  const [key, setKey] = useState("");
  const [text, setText] = useState("");
  const [result, setResult] = useState("");

  const encrypt = async () => {
    const res = await fetch("http://localhost:3000/api/sdes/encrypt", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ key, text })
    });
    const data = await res.json();
    setResult(data.result);
  };

  const decrypt = async () => {
    const res = await fetch("http://localhost:3000/api/sdes/decrypt", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ key, text })
    });
    const data = await res.json();
    setResult(data.result);
  };

  return (
    <div>
      <h2>S-DES </h2>

      <input
        placeholder="10-bit Key"
        value={key}
        onChange={(e) => setKey(e.target.value)}
      />

      <br /><br />

      <input
        placeholder="8-bit Text"
        value={text}
        onChange={(e) => setText(e.target.value)}
      />

      <br /><br />

      <button onClick={encrypt}>Encrypt</button>
      <button onClick={decrypt} style={{ marginLeft: "10px" }}>
        Decrypt
      </button>

      <pre style={{ whiteSpace: "pre-wrap", textAlign: "left" }}>
        {result}
      </pre>
    </div>
  );
}

export default SDES;
