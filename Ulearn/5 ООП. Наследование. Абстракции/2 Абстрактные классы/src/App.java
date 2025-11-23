import java.util.ArrayList;

public class App {
    public static void main(String[] args) throws Exception {
        // Monkey Maxim = new Monkey("max", 19, "brown");
        // Lion Lev = new Lion("Lev", 2, 2.15);
        // Zoo zoo = new Zoo();

        // zoo.add(Maxim);
        // zoo.add(Lev);
    }

    public abstract class Animal {
        private String name;
        private int age;

        public Animal(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() { return name; }
        public int getAge() { return age; }

        @Override
        public String toString() {
            // имя в одинарных кавычках
            return "'" + name + "', age= " + age;
        }
    }

    class Monkey extends Animal {
        private String color;

        public Monkey(String name, int age, String color) {
            super(name, age);
            this.color = color;
        }

        public String getColor() { return color; }

        @Override
        public String toString() {
            return super.toString() + ", color= " + color;
        }
    }

    class Lion extends Animal {
        private double bodyLength;

        public Lion(String name, int age, double bodyLength) {
            super(name, age);
            this.bodyLength = bodyLength;
        }

        public double getBodyLength() { return bodyLength; }

        @Override
        public String toString() {
            return super.toString() + ", bodyLength= " + bodyLength;
        }
    }

    class Zoo {
        private ArrayList<Animal> animals = new ArrayList<>();

        public void add(Animal animal) {
            animals.add(animal);
        }

        public int getSize() {
            return animals.size();
        }

        public String getReport() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < animals.size(); i++) {
                sb.append(i + 1).append(" ").append(animals.get(i).toString()).append("\n");
            }
            return sb.toString();
        }
    }

}
