import { useState } from "react";

function CMACUI() {
  const [key, setKey] = useState("");
  const [message, setMessage] = useState("");
  const [result, setResult] = useState("");

  const generateCMAC = async () => {
    const res = await fetch("http://localhost:3000/api/cmac", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({ key, message })
    });

    const data = await res.json();
    setResult(data.result);
  };

  return (
    <div>
      <h2>CMAC Algorithm</h2>

      <input
        placeholder="Enter key (16 bytes)"
        value={key}
        onChange={(e) => setKey(e.target.value)}
      />
      <br /><br />

      <input
        placeholder="Enter message"
        value={message}
        onChange={(e) => setMessage(e.target.value)}
      />
      <br /><br />

      <button onClick={generateCMAC}>
        Generate CMAC
      </button>

      <pre style={{ maxHeight: "400px", overflow: "auto" }}>
        {result}
      </pre>
    </div>
  );
}

export default CMACUI;
