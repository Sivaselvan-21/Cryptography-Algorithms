import { useState } from "react";

function Hill() {
  const [text, setText] = useState("");
   const [encrypted, setEncrypted] = useState("");
  const [decrypted, setDecrypted] = useState("");

  const encrypt = async () => {
    const res = await fetch("https://cryptography-backend-05px.onrender.com/api/hill/encrypt", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ text })
    });
    const data = await res.json();
    setEncrypted(data.result);
  };

  const decrypt = async () => {
    const res = await fetch("https://cryptography-algorithms-4.onrender.com/api/hill/decrypt", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ text:encrypted })
    });
    const data = await res.json();
    setDecrypted(data.result);
  };

  return (
    <div>
      <h2>Hill Cipher</h2>

      <input
        value={text}
        onChange={(e) => setText(e.target.value)}
        placeholder="Enter text"
      />

      <br /><br />
      <label>Key:</label>
      <p>3    3<br></br>
         2    5
      </p>
      <button onClick={encrypt} style={{ marginRight: "5px" }}>Encrypt</button>
      <button onClick={decrypt} style={{ marginRight: "5px" }}>Decrypt</button>

      <div>
        <p><strong>Encrypted Text:</strong> {encrypted}</p>
        <p><strong>Decrypted Text:</strong> {decrypted}</p>
      </div>
    </div>
  );
}

export default Hill;
