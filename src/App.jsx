import { useState } from "react";
import Shift from "./Shift";
import Hill from "./Hill";
import MillerRobin from "./MillerRobin";
import GCD from "./GCD";

function App() {
  const [cipher, setCipher] = useState("shift");

  return (
    <div>
      <h1>Cipher Application</h1>

      <button onClick={() => setCipher("shift")}>Shift Cipher</button>
      <button onClick={() => setCipher("hill")}>Hill Cipher</button>
      <button onClick={() => setCipher("miller-robin")}>Miller Robin</button>
      <button onClick={()=> setCipher("gcd")}>GCD Calculator</button>

      <hr />

      {cipher === "shift" && <Shift />}
      {cipher === "hill" && <Hill />}
      {cipher === "miller-robin" && <MillerRobin />}
      {cipher === "gcd" && <GCD />}
    </div>
  );
}

export default App;
