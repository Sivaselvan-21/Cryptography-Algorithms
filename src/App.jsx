import { useState } from "react";
import Shift from "./Shift";
import Hill from "./Hill";
import MillerRobin from "./MillerRobin";
import GCD from "./GCD";
import Fermat from "./Fermat";
import SDES from "./SDES";
import AESKeyGen from "./AESKeyGen";
import RSA from "./RSA";
import DiffieHellman from "./DiffieHellman";


function App() {
  const [cipher, setCipher] = useState("shift");

  return (
    <div>
      <h1>Cipher Application</h1>

      <button onClick={() => setCipher("shift")}>Shift Cipher</button>
      <button onClick={() => setCipher("hill")}>Hill Cipher</button>
      <button onClick={() => setCipher("miller-robin")}>Miller Robin</button>
      <button onClick={()=> setCipher("gcd")}>GCD Calculator</button>
      <button onClick={()=> setCipher("fermat")}>Fermat's Primality Test</button>
      <button onClick={()=> setCipher("sdes")}>S-DES</button>
      <button onClick={()=> setCipher("aes")}>AES Key Expansion</button>
       <button onClick={()=> setCipher("RSA")}>RSA</button>
       <button onClick={()=> setCipher("diffie-hellman")}>Diffie-Hellman</button>
      <hr />

      {cipher === "shift" && <Shift />}
      {cipher === "hill" && <Hill />}
      {cipher === "miller-robin" && <MillerRobin />}
      {cipher === "gcd" && <GCD />}
      {cipher === "fermat" && <Fermat />}
      {cipher === "sdes" && <SDES />}
      {cipher === "aes" && <AESKeyGen />}
      {cipher === "RSA" && <RSA/>}
      {cipher === "diffie-hellman" && <DiffieHellman/>}
    </div>
  );
}

export default App;
