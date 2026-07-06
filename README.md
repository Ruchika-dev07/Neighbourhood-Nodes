# Neighbourhood-Nodes
Interactive grid flexibility game using real SSEN data.Manage 6 UK neighbourhoods, balance supply-demand with 6 action buttons. Stability meter teaches peak shifting,load balancing &amp; battery storage through play. Kotlin + Jetpack Compose. Secondly,Interactive 3D &amp; 2D grid simulators.Explore supply-demand with swappable axes using Python+Matplotlib.

# ⚡ Neighbourhood Nodes - Grid Flexibility Game

Interactive educational platform teaching grid flexibility through mobile gameplay and data visualizations. Built using real SSEN Smart Meter data.

---

## 🎯 What This Does

**Mobile Game**: Manage 6 real UK neighbourhoods, solve power challenges using battery storage, EV charging delays, and peak shifting.

**Data Visualizations**: Interactive 3D and 2D plots exploring supply-demand dynamics with real-time flexibility strategies.

---

## 📊 Data Source

Uses **SSEN Smart Meter LV Feeder Data** with:
- Real postcodes (DD2 4TF, DD2 4QN, etc.)
- Actual household counts (7-42 properties)
- UK grid infrastructure (substations, feeders)

---

##  Android App (Kotlin)

**➡️ [View App Code](./android-app/)**

- 6 neighbourhoods with unique power challenges
- Battery stability meter (0-100%)
- 6 action buttons (Use Battery, Delay EV, Shift Peak, etc.)
- Smart decision system with instant feedback

**Tech**: Kotlin, Jetpack Compose, Material Design 3

---

## 📊 Python Visualizations

**➡️ [View 2D Scatter Plot](./visualizations/scatter_plot.py)**  
**➡️ [View 3D Plot](./visualizations/3d_plot.py)**

- Interactive axis swapping (X/Y/Z)
- Real-time flexibility actions
- 360° rotation (3D plot)
- Live stats panel

**Tech**: Python, Matplotlib, NumPy, Pandas

---

##  Quick Start

### Android App:
1. Open `android-app/` in Android Studio
2. Sync Gradle
3. Run on device/emulator

### Python Visualizations:
```bash
cd visualizations
pip install -r requirements.txt
python scatter_plot.py
python 3d_plot.py
```

---

## 🎓 Learning Outcomes

✅ Peak Shifting  
✅ Load Balancing  
✅ Battery Storage  
✅ Smart EV Charging  
✅ Renewable Integration  

---

## 🏆 Built For

Hack Pompey SSEN Flex Quest Hackathon - Making grid flexibility fun and understandable!
---
