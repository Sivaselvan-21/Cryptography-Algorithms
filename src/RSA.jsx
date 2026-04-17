import { useState } from "react";

function RSA() {
  const [text, setText] = useState("");
  const [result, setResult] = useState("");

  const encrypt = async () => {
    const res = await fetch("http://localhost:3000/api/rsa/encrypt", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ text })
    });
    const data = await res.json();
    setResult(data.result);
  };

  const decrypt = async () => {
    const res = await fetch("http://localhost:3000/api/rsa/decrypt", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ text })
    });
    const data = await res.json();
    setResult(data.result);
  };

  return (
    <div>
      <h2>RSA Algorithm</h2>

      <input
        placeholder="Enter Number"
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

export default RSA;
