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
    exec(`java -cp java-logic HillCipher encrypt "${req.body.text}"`,
        (err, stdout) => {
            if (err) return res.json({ error: err.message });
            res.json({ result: stdout.trim() });
        }
    );
});

app.post("/api/hill/decrypt", (req, res) => {
    exec(`java -cp java-logic HillCipher decrypt "${req.body.text}"`,
        (err, stdout) => {
            if (err) return res.json({ error: err.message });
            res.json({ result: stdout.trim() });
        }
    );
});

// -------------------- Miller–Rabin --------------------
app.post("/api/miller", (req, res) => {
    exec(`java -cp java-logic MillerRobin ${req.body.number}`,
        (err, stdout) => {
            if (err) return res.json({ error: err.message });
            res.json({ result: stdout.trim() });
        }
    );
});

// -------------------- Euclidean GCD --------------------
app.post("/api/gcd", (req, res) => {
    exec(`java -cp java-logic GCD ${req.body.num1} ${req.body.num2}`,
        (err, stdout) => {
            if (err) return res.json({ error: err.message });
            res.json({ result: stdout.trim() });
        }
    );
});

// -------------------- Serve React Build --------------------
const reactBuildPath = path.join(__dirname, "dist");
app.use(express.static(reactBuildPath));

// React Router fallback
app.get("*", (req, res) => {
    res.sendFile(path.join(reactBuildPath, "index.html"));
});

// -------------------- Start Server --------------------
const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
    console.log("Server running on port", PORT);
});
