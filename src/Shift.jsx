import { useState } from "react";
import axios from "axios";

function Shift() {
  const [text, setText] = useState("");
  const [key, setKey] = useState(0);
  const [encrypted, setEncrypted] = useState("");
  const [decrypted, setDecrypted] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  const handleEncrypt = async () => {
    if (!text) {
      setError("Please enter some text");
      return;
    }

    setError("");
    setLoading(true);

    try {
      const response = await axios.post(
        "http://localhost:3000/api/encrypt",
        { text, key }
      );
      setEncrypted(response.data.result);
    } catch (err) {
      console.error(err);
      setError("Error encrypting text");
    } finally {
      setLoading(false);
    }
  };

  const handleDecrypt = async () => {
    if (!encrypted) {
      setError("Nothing to decrypt");
      return;
    }

    setError("");
    setLoading(true);

    try {
      const response = await axios.post(
        "http://localhost:3000/api/decrypt",
        { text: encrypted, key }
      );
      setDecrypted(response.data.result);
    } catch (err) {
      console.error(err);
      setError("Error decrypting text");
    } finally {
      setLoading(false);
    }
  };

  const handleReset = () => {
    setText("");
    setKey(0);
    setEncrypted("");
    setDecrypted("");
    setError("");
  };

  return (
    <div style={{ padding: "20px", fontFamily: "Arial" }}>
      <h2>Shift Cipher</h2>

      {error && <p style={{ color: "red" }}>{error}</p>}

      <input
        type="text"
        placeholder="Enter text"
        value={text}
        onChange={(e) => setText(e.target.value)}
        style={{ marginRight: "10px", padding: "5px" }}
      />

      <input
        type="number"
        placeholder="Enter key"
        value={key}
        onChange={(e) => setKey(Number(e.target.value))}
        style={{ marginRight: "10px", padding: "5px" }}
      />

      <div style={{ marginTop: "10px", marginBottom: "10px" }}>
        <button onClick={handleEncrypt} style={{ marginRight: "5px" }}>
          Encrypt
        </button>
        <button onClick={handleDecrypt} style={{ marginRight: "5px" }}>
          Decrypt
        </button>
        <button onClick={handleReset}>Reset</button>
      </div>

      {loading && <p>Processing...</p>}

      <div>
        <p><strong>Encrypted Text:</strong> {encrypted}</p>
        <p><strong>Decrypted Text:</strong> {decrypted}</p>
      </div>
    </div>
  );
}

export default Shift;
