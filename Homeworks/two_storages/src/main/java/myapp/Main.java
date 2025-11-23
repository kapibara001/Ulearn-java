package myapp;

import myapp.Sack.CargoType;

public class Main {
    public static void main(String[] args) {
        Sack_Storage sackStorage = new Sack_Storage();
        Box_Storage boxStorage = new Box_Storage();

        Sack aLotSand = new Sack(50, Sack.CargoType.SAND);
        Sack aMediumSand = new Sack(30, Sack.CargoType.SAND);
        Sack CementSack = new Sack(250, Sack.CargoType.CEMENT);
        Sack MediumCementSack = new Sack(181, Sack.CargoType.CEMENT);

        Box box1 = new Box(50, 25, 120, false); // Деревянный стол 
        Box box2 = new Box(20, 25, 40, true); // Кружка
        Box box3 = new Box(50, 15, 180, true);

        sackStorage.addSack(aLotSand);
        sackStorage.addSack(aMediumSand);
        sackStorage.addSack(CementSack);
        sackStorage.addSack(MediumCementSack);

        boxStorage.addBox(box1);
        boxStorage.addBox(box2);
        boxStorage.addBox(box3);

        // 1
        System.out.println(sackStorage.toString());

        // 2
        Order orderSack = new Order("Текст заказа Sack", false, new Sack(122, CargoType.PLASTER));
        Order VIPorderSack = new Order("Текст вип-заказа Sack", true, new Sack(121, CargoType.CEMENT));
        Order orderBox = new Order("Текст заказа Box", false, new Box(300, 22, 150, false));
        Order VIPOrderBox = new Order("Текст заказа Box", true, new Box(30, 212, 150, true));

        sackStorage.addOrder(orderSack);
        sackStorage.addVIPOrder(VIPorderSack);

        boxStorage.addOrder(orderBox);
        boxStorage.addVIPOrder(VIPOrderBox);

        // 3
        sackStorage.processOrder(); 
        
        // 4
        System.out.println(sackStorage.getSacksByTypes() + "\n");

        // 5
        System.out.println("Хрупких коробок на складе: " + boxStorage.getFragileAmount() + " шт.\n");

        // 6
        Order orderVIPHrupko = new Order("Хрупкий вип-заказ", true, new Box(100, 100, 100, true));
        boxStorage.addVIPOrder(orderVIPHrupko);

        Order orderNotHrupko = new Order("Обычный нехрукпкий заказ", false, new Box(100, 100, 100, false));
        boxStorage.addOrder(orderNotHrupko);

        // 7
        boxStorage.processOrder(); 

        // 8 
        System.out.println("Хрупких коробок на складе: " + boxStorage.getFragileAmount() + " шт.\n");
    }
}