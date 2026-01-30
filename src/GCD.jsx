import { useState } from "react";

function GCD() {
  const [num1, setNum1] = useState("");
  const [num2, setNum2] = useState("");
  const [result, setResult] = useState("");

  const calculateGCD = async (e) => {
    e.preventDefault(); // prevent page reload

    const res = await fetch("https://cryptography-backend-05px.onrender.com/api/gcd", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ num1, num2 })
    });
    const data = await res.json();
    setResult(data.result);
  };

  const handleReset = () => {
    setNum1("");
    setNum2("");
    setResult("");
  };

  return (
    <div>
      <h2>Euclidean GCD Calculator</h2>

      <form onSubmit={calculateGCD}>
        <input
          type="number"
          placeholder="Enter first number"
          value={num1}
          onChange={(e) => setNum1(e.target.value)}
        />
        <input
          type="number"
          placeholder="Enter second number"
          value={num2}
          onChange={(e) => setNum2(e.target.value)}
          style={{ marginLeft: "10px" }}
        />
        <button type="submit" style={{ marginLeft: "10px" }}>
          Calculate
        </button>
        <button type="button" onClick={handleReset} style={{ marginLeft: "10px" }}>
          Reset
        </button>
      </form>

      {result && <p><strong>GCD:</strong> {result}</p>}
    </div>
  );
}

export default GCD;
