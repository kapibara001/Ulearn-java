import java.util.Random;

public class Main {
    public static void main(String[] args) {
        Hospital hospital = new Hospital(5);
        System.out.println(hospital.getResult());
    }
}

class Hospital {
    private final float[] patientsTemperatures;  // храним температуры
    private final String result;                 // отчёт как поле

    public Hospital(int patientsCount) {
        if (patientsCount <= 0) {
            throw new IllegalArgumentException("Неверное количество пациентов");
        }

        this.patientsTemperatures = generatePatientsTemperatures(patientsCount);
        int healthyCount = getCountHealthy();
        double average = getAverageTemp();
        this.result = getReport(average, healthyCount);
    }

    public String getResult() {
        return result;
    }

    public float[] generatePatientsTemperatures() {
        return patientsTemperatures;
    }

    public double getAverageTemp() {
        double sum = 0;
        for (float temp : patientsTemperatures) {
            sum += temp;
        }
        return sum / patientsTemperatures.length;
    }

    public int getCountHealthy() {
        int count = 0;
        for (float temp : patientsTemperatures) {
            if (temp >= 36.2f && temp <= 36.9f) {
                count++;
            }
        }
        return count;
    }

    public String getTemperaturesToString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < patientsTemperatures.length; i++) {
            sb.append(String.format("%.1f", patientsTemperatures[i]));
            if (i < patientsTemperatures.length - 1) {
                sb.append(" ");
            }
        }
        return sb.toString();
    }

    public String getReport() {
        return result;
    }
    
    private float[] generatePatientsTemperatures(int count) {
        Random rand = new Random();
        float[] temps = new float[count];
        for (int i = 0; i < count; i++) {
            temps[i] = 32.0f + rand.nextFloat() * 8.0f;
        }
        return temps;
    }

    private String getReport(double average, int healthyCount) {
        StringBuilder sb = new StringBuilder();
        sb.append("Температуры пациентов: ").append(getTemperaturesToString()).append("\n");
        sb.append(String.format("Средняя температура: %.2f%n", average));
        sb.append("Количество здоровых: ").append(healthyCount);
        return sb.toString();
    }
}
