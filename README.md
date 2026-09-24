# WhiskerPal: Hinglish Talking Tom (Desktop Web App)

An interactive, browser-based virtual pet game featuring **Talking Tom** styled voice repeating, squeaky TTS, Hinglish dialogues, interactive touch zones, and desktop keyboard controls.

---

## 🚀 How to Run on Desktop

### Option 1: Live in Your Desktop Browser (Instant)
Click or open this URL in your desktop browser:
```
https://ais-dev-63h3faqfypk2ej7dkpqsr3-683250541185.asia-southeast1.run.app
```
Enjoy the full-screen layout on your monitor!

---

### Option 2: Run Locally (Offline, No Setup Required)
1. Download or copy `index.html` to your desktop computer.
2. Double-click `index.html` (or right-click → **Open with** → Chrome / Edge / Firefox / Safari).
3. The game will run immediately as a desktop web app without any installation or internet required!

---

### Option 3: Run with Node.js
```bash
npm start
# or
node server.js
```
Open `http://localhost:3000` in your desktop browser.

---

## ⌨️ Desktop Keyboard Shortcuts

| Key | Action | Description |
|---|---|---|
| **`M`** | Mic On / Off | Listen to your desktop mic and repeat in squeaky voice |
| **`1`** | Feed Milk 🥛 | Fills hunger meter |
| **`2`** | Pet Belly ❤️ | Rubs tummy & makes Tom purr |
| **`3`** | Football ⚽ | Opens football mini-game / shoots goal |
| **`4`** | Bedtime 💤 | Puts Tom to sleep or wakes him up |
| **`H`** | Slap Head 💥 | Slaps/pokes head with comic reactions |
| **`T`** | Tickle Paws 🐾 | Tickles feet and makes Tom jump |
| **`F`** | Fullscreen ⛶ | Expands to true full monitor screen |
| **`Enter`** | Say Text | Speaks typed message from chat box |
| **`Esc`** | Close Match | Closes mini-game |

---

## 🛠️ Architecture
- **Web Speech API**: Uses `window.speechSynthesis` (pitch `1.6`) and `webkitSpeechRecognition` for squeaky voice repeating.
- **Web Audio API**: Procedural sound effects synthesizer for slap impacts, purring, slurps, kicks, and stadium cheers without external media files.
- **Pure HTML5 / CSS3 / ES6**: Completely self-contained in a single `index.html` file.
