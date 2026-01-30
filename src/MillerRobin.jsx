import { useState } from "react";

function Miller() {
  const [number, setNumber] = useState("");
  const [result, setResult] = useState("");

  const checkPrime = async () => {
    const res = await fetch("https://cryptography-backend-05px.onrender.com/api/miller", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ number })
    });

    const data = await res.json();
    setResult(data.result);
  };

  return (
    <div>
      <h2>Miller–Rabin Primality Test</h2>

      <input
        value={number}
        onChange={(e) => setNumber(e.target.value)}
        placeholder="Enter number"
      />

      <br /><br />
      <button onClick={checkPrime}>Check</button>

      <p><strong>Result:</strong> {result}</p>
    </div>
  );
}

export default Miller;
