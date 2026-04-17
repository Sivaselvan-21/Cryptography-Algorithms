import { useState } from "react";

function AESKeyGen() {
  const [key, setKey] = useState("");
  const [result, setResult] = useState("");
  const [text, setText] = useState(""); 
  const generateKeys = async () => {
    const res = await fetch("http://localhost:3000/api/aes-keygen", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ key,text })
    });

    const data = await res.json();
    setResult(data.result);
  };

  return (
    <div>
      <h2>AES-128 bit encryption </h2>

      <input
        placeholder="Enter 32 hex characters"
        value={key}
        onChange={(e) => setKey(e.target.value)}
      />
      <br /><br />
      <input
        placeholder="Enter 32 hex characters for plaintext"
        value={text}
        onChange={(e) => setText(e.target.value)}
      />
      <br /><br />

      <button onClick={generateKeys}>
        Generate with Steps
      </button>

      <pre style={{ whiteSpace: "pre-wrap", textAlign: "left" }}>
        {result}
      </pre>
    </div>
  );
}

export default AESKeyGen;
