# 📊 Grid Flexibility Visualizations

Interactive Python plots for exploring supply-demand dynamics.

---

## 📈 What's Included

**scatter_plot.py** - 2D interactive scatter plot with axis swapping  
**3d_plot.py** - 3D visualization with full rotation control

---

##  Setup

Install dependencies:
```bash
pip install matplotlib numpy pandas
```

Run scatter plot:
```bash
python scatter_plot.py
```

Run 3D plot:
```bash
python 3d_plot.py
```

---

## 🎮 Interactive Features

### Scatter Plot:
- X/Y axis swapping buttons
- Peak Shift, Add Battery, Add Solar actions
- Color-coded by stability

### 3D Plot:
- X/Y/Z axis control
- 360° rotation slider
- 5 flexibility strategies
- Live stats panel

---

## 📊 Data Simulated

- 7 days × 48 half-hour intervals
- Demand patterns (morning/evening peaks)
- Solar generation (6am-6pm)
- Wind generation (variable)
- Grid stability calculations
