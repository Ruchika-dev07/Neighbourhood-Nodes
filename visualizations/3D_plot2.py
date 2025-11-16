#With Higher Rotation degree
import matplotlib.pyplot as plt
import numpy as np
from mpl_toolkits.mplot3d import Axes3D
from matplotlib.widgets import Button, Slider
import pandas as pd

# Generate sample grid data
np.random.seed(42)
hours = np.arange(0, 24, 0.5)
n_days = 7

def generate_3d_grid_data():
    data = []
    for day in range(n_days):
        for hour in hours:
            # Demand pattern
            base_demand = 30 + 15 * np.sin((hour - 6) * np.pi / 12)
            morning_peak = 10 * np.exp(-((hour - 8) ** 2) / 2)
            evening_peak = 15 * np.exp(-((hour - 19) ** 2) / 2)
            noise = np.random.normal(0, 2)
            demand = base_demand + morning_peak + evening_peak + noise
            
            # Supply (solar + wind + base)
            solar = 20 * np.exp(-((hour - 12) ** 2) / 8) if 6 < hour < 18 else 0
            wind = np.random.uniform(5, 15) if hour < 6 or hour > 20 else np.random.uniform(2, 8)
            supply = solar + wind + 30
            
            # Grid load (difference between demand and supply)
            grid_load = demand - supply
            
            # Stability
            stability = 100 - abs(grid_load) * 2
            stability = max(0, min(100, stability))
            
            # Battery usage
            battery_discharge = max(0, grid_load)
            battery_charge_level = 50 + np.random.uniform(-20, 20)
            
            # Renewable percentage
            renewable_pct = ((solar + wind) / supply) * 100 if supply > 0 else 0
            
            data.append({
                'hour': hour,
                'day': day,
                'demand': demand,
                'supply': supply,
                'grid_load': grid_load,
                'stability': stability,
                'solar': solar,
                'wind': wind,
                'battery_charge': battery_charge_level,
                'battery_discharge': battery_discharge,
                'renewable_pct': renewable_pct
            })
    
    return pd.DataFrame(data)

# Generate data
df = generate_3d_grid_data()

# Create figure
fig = plt.figure(figsize=(14, 10))
ax = fig.add_subplot(111, projection='3d')
plt.subplots_adjust(left=0.05, bottom=0.35, right=0.95, top=0.95)

# Initial axis variables
x_var = 'hour'
y_var = 'day'
z_var = 'demand'

# Create 3D scatter plot
scatter = ax.scatter(df[x_var], df[y_var], df[z_var], 
                     c=df['stability'], cmap='RdYlGn', 
                     s=30, alpha=0.7, edgecolors='black', linewidth=0.5)

cbar = plt.colorbar(scatter, ax=ax, pad=0.1, shrink=0.8)
cbar.set_label('Grid Stability (%)', fontsize=10, fontweight='bold')

ax.set_xlabel('Hour of Day', fontsize=11, fontweight='bold', labelpad=10)
ax.set_ylabel('Day', fontsize=11, fontweight='bold', labelpad=10)
ax.set_zlabel('Demand (MW)', fontsize=11, fontweight='bold', labelpad=10)
ax.set_title('⚡ 3D Grid Load Balancing Simulator\nExplore Supply-Demand Dynamics', 
             fontsize=14, fontweight='bold', color='#D32F2F', pad=20)

# Variable options
variables = {
    'Hour': 'hour',
    'Day': 'day',
    'Demand': 'demand',
    'Supply': 'supply',
    'Grid Load': 'grid_load',
    'Stability': 'stability',
    'Solar': 'solar',
    'Wind': 'wind',
    'Battery': 'battery_charge',
    'Renewable %': 'renewable_pct'
}

var_names = list(variables.keys())
current_x_idx = 0  # Hour
current_y_idx = 1  # Day
current_z_idx = 2  # Demand

# Button dimensions
button_height = 0.04
button_width = 0.08

# Create axis selection buttons
ax_x_prev = plt.axes([0.1, 0.20, button_width, button_height])
ax_x_next = plt.axes([0.19, 0.20, button_width, button_height])
btn_x_prev = Button(ax_x_prev, '◀ X', color='#BBDEFB', hovercolor='#64B5F6')
btn_x_next = Button(ax_x_next, 'X ▶', color='#BBDEFB', hovercolor='#64B5F6')

ax_y_prev = plt.axes([0.1, 0.14, button_width, button_height])
ax_y_next = plt.axes([0.19, 0.14, button_width, button_width])
btn_y_prev = Button(ax_y_prev, '◀ Y', color='#C8E6C9', hovercolor='#81C784')
btn_y_next = Button(ax_y_next, 'Y ▶', color='#C8E6C9', hovercolor='#81C784')

ax_z_prev = plt.axes([0.1, 0.08, button_width, button_height])
ax_z_next = plt.axes([0.19, 0.08, button_width, button_height])
btn_z_prev = Button(ax_z_prev, '◀ Z', color='#FFECB3', hovercolor='#FFD54F')
btn_z_next = Button(ax_z_next, 'Z ▶', color='#FFECB3', hovercolor='#FFD54F')

# Simulation action buttons
ax_balance = plt.axes([0.4, 0.20, 0.12, button_height])
ax_ev_charging = plt.axes([0.4, 0.14, 0.12, button_height])
ax_add_wind = plt.axes([0.4, 0.08, 0.12, button_height])
ax_battery_opt = plt.axes([0.4, 0.02, 0.12, button_height])
ax_reset = plt.axes([0.7, 0.14, 0.1, button_height])

btn_balance = Button(ax_balance, '⚖️ Balance Load', color='#4CAF50', hovercolor='#45a049')
btn_ev_charging = Button(ax_ev_charging, '🚗 Smart EV', color='#2196F3', hovercolor='#1976D2')
btn_add_wind = Button(ax_add_wind, '💨 Add Wind', color='#00BCD4', hovercolor='#0097A7')
btn_battery_opt = Button(ax_battery_opt, '🔋 Optimize Battery', color='#FF9800', hovercolor='#F57C00')
btn_reset = Button(ax_reset, '🔄 Reset', color='#f44336', hovercolor='#d32f2f')

# Rotation slider - NOW GOES TO 720° for multiple rotations!
ax_rotation = plt.axes([0.7, 0.08, 0.2, 0.03])
slider_rotation = Slider(ax_rotation, '🔄 Rotate', 0, 720, valinit=120, valstep=1, color='#9C27B0')

# Current axis labels
label_x = ax.text2D(0.1, 0.26, f'X: {var_names[current_x_idx]}', 
                     transform=fig.transFigure, fontsize=10, fontweight='bold')
label_y = ax.text2D(0.1, 0.23, f'Y: {var_names[current_y_idx]}', 
                     transform=fig.transFigure, fontsize=10, fontweight='bold')
label_z = ax.text2D(0.1, 0.20, f'Z: {var_names[current_z_idx]}', 
                     transform=fig.transFigure, fontsize=10, fontweight='bold')

# Info panel
info_text = ax.text2D(0.55, 0.27, 
                      '🎮 3D Grid Simulator\n'
                      'Visualize supply-demand in 3D space\n'
                      'Apply flexibility strategies!',
                      transform=fig.transFigure, fontsize=9, ha='center',
                      bbox=dict(boxstyle='round', facecolor='#E8F5E9', alpha=0.9))

# Stats panel
stats_text = ax.text2D(0.85, 0.27, '', transform=fig.transFigure, 
                       fontsize=8, verticalalignment='top',
                       bbox=dict(boxstyle='round', facecolor='#FFF9C4', alpha=0.9))

def update_plot():
    global scatter, current_x_idx, current_y_idx, current_z_idx
    
    x_name = var_names[current_x_idx]
    y_name = var_names[current_y_idx]
    z_name = var_names[current_z_idx]
    
    x_col = variables[x_name]
    y_col = variables[y_name]
    z_col = variables[z_name]
    
    ax.clear()
    
    scatter = ax.scatter(df[x_col], df[y_col], df[z_col], 
                         c=df['stability'], cmap='RdYlGn', 
                         s=30, alpha=0.7, edgecolors='black', linewidth=0.5)
    
    ax.set_xlabel(x_name, fontsize=11, fontweight='bold', labelpad=10)
    ax.set_ylabel(y_name, fontsize=11, fontweight='bold', labelpad=10)
    ax.set_zlabel(z_name, fontsize=11, fontweight='bold', labelpad=10)
    ax.set_title('⚡ 3D Grid Load Balancing Simulator\nExplore Supply-Demand Dynamics', 
                 fontsize=14, fontweight='bold', color='#D32F2F', pad=20)
    
    ax.view_init(elev=20, azim=slider_rotation.val)
    
    label_x.set_text(f'X: {x_name}')
    label_y.set_text(f'Y: {y_name}')
    label_z.set_text(f'Z: {z_name}')
    
    # Update stats
    avg_stability = df['stability'].mean()
    peak_demand = df['demand'].max()
    avg_renewable = df['renewable_pct'].mean()
    avg_grid_load = abs(df['grid_load']).mean()
    
    stats_text.set_text(
        f'📊 Grid Metrics:\n'
        f'Stability: {avg_stability:.1f}%\n'
        f'Peak: {peak_demand:.1f} MW\n'
        f'Renewable: {avg_renewable:.1f}%\n'
        f'Avg Load: {avg_grid_load:.1f} MW'
    )
    
    fig.canvas.draw_idle()

def x_prev(event):
    global current_x_idx
    current_x_idx = (current_x_idx - 1) % len(var_names)
    update_plot()

def x_next(event):
    global current_x_idx
    current_x_idx = (current_x_idx + 1) % len(var_names)
    update_plot()

def y_prev(event):
    global current_y_idx
    current_y_idx = (current_y_idx - 1) % len(var_names)
    update_plot()

def y_next(event):
    global current_y_idx
    current_y_idx = (current_y_idx + 1) % len(var_names)
    update_plot()

def z_prev(event):
    global current_z_idx
    current_z_idx = (current_z_idx - 1) % len(var_names)
    update_plot()

def z_next(event):
    global current_z_idx
    current_z_idx = (current_z_idx + 1) % len(var_names)
    update_plot()

def balance_load(event):
    # Simulate load balancing: distribute high peaks more evenly
    for day in range(n_days):
        day_data = df[df['day'] == day]
        peak_hours = day_data[day_data['demand'] > day_data['demand'].quantile(0.8)].index
        
        for idx in peak_hours:
            df.loc[idx, 'demand'] *= 0.85  # Reduce peak by 15%
        
        # Recalculate derived values
        df.loc[day_data.index, 'grid_load'] = df.loc[day_data.index, 'demand'] - df.loc[day_data.index, 'supply']
        df.loc[day_data.index, 'stability'] = (100 - abs(df.loc[day_data.index, 'grid_load']) * 2).clip(0, 100)
    
    update_plot()
    print("⚖️ Load Balanced! Peak demand smoothed across the day.")

def smart_ev_charging(event):
    # Shift EV charging to off-peak hours (1-5 AM)
    for i in range(len(df)):
        if 1 <= df.loc[i, 'hour'] <= 5:
            df.loc[i, 'demand'] += 3  # Add EV load to off-peak
        elif 18 <= df.loc[i, 'hour'] <= 22:
            df.loc[i, 'demand'] -= 3  # Remove EV load from peak
        
        df.loc[i, 'grid_load'] = df.loc[i, 'demand'] - df.loc[i, 'supply']
        df.loc[i, 'stability'] = max(0, min(100, 100 - abs(df.loc[i, 'grid_load']) * 2))
    
    update_plot()
    print("🚗 Smart EV Charging! EVs now charge during off-peak hours.")

def add_wind_capacity(event):
    # Increase wind generation capacity
    df['wind'] *= 1.5
    df['supply'] = df['solar'] + df['wind'] + 30
    df['grid_load'] = df['demand'] - df['supply']
    df['stability'] = (100 - abs(df['grid_load']) * 2).clip(0, 100)
    df['renewable_pct'] = ((df['solar'] + df['wind']) / df['supply']) * 100
    update_plot()
    print("💨 Wind Capacity Increased! More renewable energy online.")

def optimize_battery(event):
    # Battery charges during low demand, discharges during high demand
    for i in range(len(df)):
        if df.loc[i, 'grid_load'] > 5:  # High demand
            battery_help = min(10, df.loc[i, 'battery_charge'] * 0.3)
            df.loc[i, 'supply'] += battery_help
            df.loc[i, 'battery_charge'] -= battery_help
        elif df.loc[i, 'grid_load'] < -5:  # Excess supply
            charge_amount = min(15, 100 - df.loc[i, 'battery_charge'])
            df.loc[i, 'battery_charge'] += charge_amount
        
        df.loc[i, 'grid_load'] = df.loc[i, 'demand'] - df.loc[i, 'supply']
        df.loc[i, 'stability'] = max(0, min(100, 100 - abs(df.loc[i, 'grid_load']) * 2))
    
    update_plot()
    print("🔋 Battery Optimized! Storage now helps during peak times.")

def reset_data(event):
    global df
    df = generate_3d_grid_data()
    update_plot()
    print("🔄 Data Reset to Original State.")

def on_rotate(val):
    ax.view_init(elev=20, azim=slider_rotation.val)
    fig.canvas.draw_idle()

# Connect all buttons and slider
btn_x_prev.on_clicked(x_prev)
btn_x_next.on_clicked(x_next)
btn_y_prev.on_clicked(y_prev)
btn_y_next.on_clicked(y_next)
btn_z_prev.on_clicked(z_prev)
btn_z_next.on_clicked(z_next)
btn_balance.on_clicked(balance_load)
btn_ev_charging.on_clicked(smart_ev_charging)
btn_add_wind.on_clicked(add_wind_capacity)
btn_battery_opt.on_clicked(optimize_battery)
btn_reset.on_clicked(reset_data)
slider_rotation.on_changed(on_rotate)

# Initial update
update_plot()

plt.show()