import express from "express";
import cors from "cors";
import { exec } from "node:child_process";
import path from "path";
import { fileURLToPath } from "url";

const __filename = fileURLToPath(import.meta.url);
const __dirname = path.dirname(__filename);

const app = express();
app.use(cors());
app.use(express.json());

// -------------------- Shift Cipher --------------------
function runJavaShiftCipher(mode, text, key, callback) {
    const safeText = text.replaceAll('"', '\\"');
    const cmd = `java -cp java-logic Cipher ${mode} ${key} "${safeText}"`;
    exec(cmd, (error, stdout, stderr) => {
        if (error) callback(stderr || error.message);
        else callback(stdout.trim());
    });
}

app.post("/api/encrypt", (req, res) => {
    const { text, key } = req.body;
    runJavaShiftCipher("encrypt", text, key, (result) => res.json({ result }));
});

app.post("/api/decrypt", (req, res) => {
    const { text, key } = req.body;
    runJavaShiftCipher("decrypt", text, key, (result) => res.json({ result }));
});

// -------------------- Hill Cipher --------------------
app.post("/api/hill/encrypt", (req, res) => {
    const { text } = req.body;
    exec(`java -cp java-logic HillCipher encrypt "${text}"`, (err, stdout) => {
        if (err) return res.json({ error: err.message });
        res.json({ result: stdout.trim() });
    });
});

app.post("/api/hill/decrypt", (req, res) => {
    const { text } = req.body;
    exec(`java -cp java-logic HillCipher decrypt "${text}"`, (err, stdout) => {
        if (err) return res.json({ error: err.message });
        res.json({ result: stdout.trim() });
    });
});

// -------------------- Miller-Rabin --------------------
app.post("/api/miller", (req, res) => {
    const { number } = req.body;
    exec(`java -cp java-logic MillerRobin ${number}`, (err, stdout) => {
        if (err) return res.json({ error: err.message });
        res.json({ result: stdout.trim() });
    });
});

// -------------------- Euclidean GCD --------------------
app.post("/api/gcd", (req, res) => {
    const { num1, num2 } = req.body;
    exec(`java -cp java-logic GCD ${num1} ${num2}`, (err, stdout) => {
        if (err) return res.json({ error: err.message });
        res.json({ result: stdout.trim() });
    });
});

// -------------------- Serve React Build --------------------
app.use(express.static(path.join(__dirname, "dist")));

// Fallback for React Router
app.get(/.*/, (req, res) => {
    res.sendFile(path.join(reactBuildPath, "index.html"));
});

// -------------------- Start Server --------------------
app.listen(3000, () => {
    console.log("Server running at http://localhost:3000");
});
