interface Controllable {
    void turnOn();
    void turnOff();
    boolean isOn();
}

interface Connectable {
    void connectToWifi(String network);
    void disconnectFromWifi();
    String getConnectionStatus();
}

interface EnergySaver {
    void enableEcoMode();
    void disableEcoMode();
    double getPowerConsumption();
}

class SmartLight implements Controllable, EnergySaver {
    private boolean on = false;
    private boolean onEcoMode = false;
    private double powerConsumption = 10.0;

    @Override
    public void turnOn() {
        on = true;
        System.out.println("SmartLight включен.");
    }

    @Override
    public void turnOff() {
        on = false;
        System.out.println("SmartLight выключен.");
    }

    @Override
    public boolean isOn() {
        return on;
    }

    @Override
    public void enableEcoMode() {
        onEcoMode = true;
        powerConsumption = 5.0;
        System.out.println("Режим энергосбережения включен.");
    }

    @Override
    public void disableEcoMode() {
        onEcoMode = false;
        powerConsumption = 10.0;
        System.out.println("Режим энергосбережения выключен. Напряжение 5.0В");
    }

    @Override
    public double getPowerConsumption() {
        return powerConsumption;
    }

    // public void main(String[] args) {

    // }
}

class SmartTV implements Controllable, Connectable {;
    private boolean on;
    private boolean connectWifi = true;
    private String WIFIName;

    @Override
    public void turnOn() {
        on = true;
        System.out.println("SmartTV включено.");
    }

    @Override
    public void turnOff() {
        on = false;
        System.out.println("SmartTV выключено.");
    }

    @Override
    public boolean isOn() {
        return on;
    }

    @Override
    public void connectToWifi(String WIFIN) {
        connectWifi = true;
        WIFIName = WIFIN;
        System.out.println(String.format("WIFI подключен к %s", WIFIN));
    }

    @Override
    public void disconnectFromWifi() {
        connectWifi = false;
        System.out.println("WIFI выключен.");
    }

    @Override
    public String getConnectionStatus() {
        return (connectWifi == true) ? "WIFI подключен к " + WIFIName : "WIFI отключен" ;
    }
}

class SmartThermostat implements Controllable, Connectable, EnergySaver {
    private boolean on = false;
    private boolean ecoMode = false;
    private double powerConsumption = 20.0;
    private boolean connected = false;
    private String wifiName;

    @Override
    public void turnOn() {
        on = true;
        System.out.println("SmartThermostat включён.");
    }

    @Override
    public void turnOff() {
        on = false;
        System.out.println("SmartThermostat выключен.");
    }

    @Override
    public boolean isOn() {
        return on;
    }

    @Override
    public void connectToWifi(String network) {
        connected = true;
        wifiName = network;
        System.out.println("SmartThermostat подключён к Wi-Fi: " + wifiName);
    }

    @Override
    public void disconnectFromWifi() {
        connected = false;
        wifiName = null;
        System.out.println("SmartThermostat отключён от Wi-Fi");
    }

    @Override
    public String getConnectionStatus() {
        return connected ? "Подключен к " + wifiName : "Не подключен";
    }

    @Override
    public void enableEcoMode() {
        ecoMode = true;
        powerConsumption = 10.0;
        System.out.println("SmartThermostat — эко-режим включён");
    }

    @Override
    public void disableEcoMode() {
        ecoMode = false;
        powerConsumption = 20.0;
        System.out.println("SmartThermostat — эко-режим выключен");
    }

    @Override
    public double getPowerConsumption() {
        return powerConsumption;
    }
}

class BasicLamp implements Controllable {
    private boolean on = false;

    @Override
    public void turnOn() {
        on = true;
        System.out.println("BasicLamp включена");
    }

    @Override
    public void turnOff() {
        on = false;
        System.out.println("BasicLamp выключена");
    }

    @Override
    public boolean isOn() {
        return on;
    }
}

public class Main {
    public static void main(String[] args) {
        SmartLight light = new SmartLight();
        SmartTV tv = new SmartTV();
        SmartThermostat thermostat = new SmartThermostat();
        BasicLamp lamp = new BasicLamp();

        // включение всех Controllable
        Controllable[] devices = {light, tv, thermostat, lamp};
        for (Controllable d : devices) {
            d.turnOn();
            System.out.println("Включено? " + d.isOn());
        }

        tv.connectToWifi("HomeWiFi");
        thermostat.connectToWifi("SmartHome");

        // Пример EnergySaver
        light.enableEcoMode();
        thermostat.enableEcoMode();
        System.out.println("Энергопотребление SmartLight: " + light.getPowerConsumption());
        System.out.println("Энергопотребление SmartThermostat: " + thermostat.getPowerConsumption());
    }
}