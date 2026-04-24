import { useState } from "react";

function SHA256UI() {
  const [message, setMessage] = useState("");
  const [result, setResult] = useState("");

  const generateHash = async () => {
    const res = await fetch("http://localhost:3000/api/sha256", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ message })
    });

    const data = await res.json();
    setResult(data.result);
  };
  return (
    <div>
      <h2>SHA-256 Algorithm</h2>

      <input
        placeholder="Enter message"
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        style={{ width: "300px" }}
      />
      <br /><br />

      <button onClick={generateHash}>
        Generate SHA-256 Hash
      </button>

      <pre style={{ whiteSpace: "pre-wrap", textAlign: "left" }}>
        {result}
      </pre>
    </div>
  );
}

export default SHA256UI;
