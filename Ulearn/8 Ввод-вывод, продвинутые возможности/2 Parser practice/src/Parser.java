import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Parser {
    public static void main(String[] args) {
        String path = "movementList.csv";
        Movements movements = new Movements(path);
        
        System.out.println("Сумма расходов: " + movements.getExpenseSum() + " руб.");
        System.out.println("Сумма доходов: " + movements.getIncomeSum() + " руб.");
        System.out.println("\nРасходы по организациям:");
        
        for (String expense : movements.getListOfExpenses()) {
            System.out.println(expense);
        }
    }
}

class Operation {
    private String accountType;
    private String accountNumber;
    private String currency;
    private String date;
    private String reference;
    private String description;
    private double incomeAmount;
    private double expenseAmount;
    
    public Operation(String[] data) {
        this.accountType = data[0];
        this.accountNumber = data[1];
        this.currency = data[2];
        this.date = data[3];
        this.reference = data[4];
        this.description = data[5];
        this.incomeAmount = parseAmount(data[6]);
        this.expenseAmount = parseAmount(data[7]);
    }
    
    private double parseAmount(String amount) {
        if (amount == null || amount.trim().isEmpty()) {
            return 0.0;
        }
        amount = amount.replace(",", ".").replace("\"", "").trim();
        try {
            return Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    
    public double getIncomeAmount() {
        return incomeAmount;
    }
    
    public double getExpenseAmount() {
        return expenseAmount;
    }
    
    public String getDescription() {
        return description;
    }
}

class Movements {
    private List<Operation> operations;
    
    public Movements(String path) {
        operations = new ArrayList<>();
        try {
            List<String> lines = Files.readAllLines(Paths.get(path));
            for (int i = 1; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] data = parseCsvLine(line);
                if (data.length >= 8) {
                    operations.add(new Operation(data));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private String[] parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder current = new StringBuilder();
        
        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);
            
            if (c == '"') {
                inQuotes = !inQuotes;
            } else if (c == ',' && !inQuotes) {
                result.add(current.toString());
                current = new StringBuilder();
            } else {
                current.append(c);
            }
        }
        result.add(current.toString());
        
        return result.toArray(new String[0]);
    }
    
    public double getExpenseSum() {
        return operations.stream()
                .mapToDouble(Operation::getExpenseAmount)
                .sum();
    }
    
    public double getIncomeSum() {
        return operations.stream()
                .mapToDouble(Operation::getIncomeAmount)
                .sum();
    }
    
    private String removeBeforeDate(String description) {
        String[] parts = description.split("\\s+");
        StringBuilder result = new StringBuilder();
        boolean dateFound = false;
        
        for (String part : parts) {
            if (!dateFound && part.matches("\\d{6}")) {
                dateFound = true;
            }
            if (dateFound) {
                if (result.length() > 0) {
                    result.append(" ");
                }
                result.append(part);
            }
        }
        
        return dateFound ? result.toString() : description;
    }
    
    private String removeDateAndAfter(String description) {
        String[] parts = description.split("\\s+");

	StringBuilder result = new StringBuilder();
        
        for (String part : parts) {
            if (part.matches("\\d{2}\\.\\d{2}\\.\\d{2}")) {
                break;
            }
            if (result.length() > 0) {
                result.append(" ");
            }
            result.append(part);
        }
        
        return result.toString();
    }
    
    private String extractOrganization(String description) {
        String step1 = removeBeforeDate(description);
        String step2 = removeDateAndAfter(step1);
        
        int slashIndex = step2.indexOf('/');
        int backslashIndex = step2.indexOf('\\');
        
        if (slashIndex != -1) {
            return step2.substring(slashIndex).trim();
        } else if (backslashIndex != -1) {
            return step2.substring(backslashIndex).trim();
        }
        
        return step2.trim();
    }
    
    public ArrayList<String> getListOfExpenses() {
        Map<String, Double> expensesByOrganization = new HashMap<>();
        
        for (Operation operation : operations) {
            if (operation.getExpenseAmount() > 0) {
                String organization = extractOrganization(operation.getDescription());
                double currentAmount = expensesByOrganization.getOrDefault(organization, 0.0);
                expensesByOrganization.put(organization, currentAmount + operation.getExpenseAmount());
            }
        }
        
        ArrayList<String> result = new ArrayList<>();
        for (Map.Entry<String, Double> entry : expensesByOrganization.entrySet()) {
            String formattedAmount = String.format("%.2f", entry.getValue()).replace(".", ",");
            result.add(entry.getKey() + " —> " + formattedAmount + " руб.");
        }
        
        return result;
    }
}