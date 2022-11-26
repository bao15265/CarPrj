package Classes.Com;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class CarList extends java.util.ArrayList<Car> {
    Scanner sc = new Scanner(System.in);
    BrandList brandList;

    public CarList(BrandList bList) {
        brandList = bList;
    }

    public void loadFromFile(String filename) throws IOException {
        File f = new File(filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String line = reader.readLine();
            while (line != null) {
                String[] dataCar = line.split(",");
                String[] carIn = Arrays.stream(dataCar).map(String::strip).toArray(String[]::new);
                int pos = brandList.searchID(carIn[1]);
                Brand b = brandList.get(pos);
                Car car = new Car(carIn[0], b, carIn[2], carIn[3], carIn[4]);
                this.add(car);
                line = reader.readLine();
            }
        }
    }

    public void saveFromFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Car car : this) {
                String carOut = String.format("%s, %s, %s, %s, %s%n", car.carID, car.brand.brandID, car.color, car.frameID, car.engineID);
                writer.write(carOut);
            }
        }
    }

    public int searchID(String carID) {
        int N = this.size();
        for (int i = 0; i < N; i++) {
            if (carID.equalsIgnoreCase(this.get(i).carID)) {
                return i;
            }
        }
        return -1;
    }

    public int searchFrame(String fID) {
        int N = this.size();
        for (int i = 0; i < N; i++) {
            if (fID.equalsIgnoreCase(this.get(i).frameID)) {
                return i;
            }
        }
        return -1;
    }

    public int searchEngine(String eID) {
        int N = this.size();
        for (int i = 0; i < N; i++) {
            if (eID.equalsIgnoreCase(this.get(i).engineID)) {
                return i;
            }
        }
        return -1;
    }

    public boolean notBeFormat(String id, char key) {
        if (id.isBlank()) {
            return true;
        }
        if (id.length() != 6) {
            return true;
        }
        if (id.charAt(0) != key) {
            return true;
        }
        if (!id.substring(1).matches("[0-9]+")) {
            return true;
        }
        return false;
    }

    public void checkCarID(ArrayList<Car> a, Car b) {
        System.out.println("Enter car ID: ");
        b.setCarID(sc.nextLine().toUpperCase());
        while (a.stream().anyMatch(obj -> b.getCarID().equals(obj.carID))) {
            System.out.println("ID already exists in the list. Please re-enter: ");
            b.setCarID(sc.nextLine().toUpperCase());
        }
    }

    public void checkColor(Car b) {
        System.out.println("Enter color: ");
        b.setColor(sc.nextLine().toLowerCase());
        while (b.getColor().isBlank()) {
            System.out.println("The color is blank. Please re-enter: ");
            b.setColor(sc.nextLine().toLowerCase());
        }
    }

    public void checkFrameID(ArrayList<Car> a, Car b) {
        System.out.println("Enter frame ID: ");
        b.setFrameID(sc.nextLine().toUpperCase());
        while (a.stream().anyMatch(obj -> b.getFrameID().equals(obj.frameID)) || notBeFormat(b.getFrameID(), 'F')) {
            System.out.println("It must be in the <F00000> and not be duplicated. Please re-enter: ");
            b.setFrameID(sc.nextLine().toUpperCase());
        }
    }

    public void checkEngineID(ArrayList<Car> a, Car b) {
        System.out.println("Enter engine ID: ");
        b.setEngineID(sc.nextLine().toUpperCase());
        while (a.stream().anyMatch(obj -> b.getEngineID().equals(obj.engineID)) || notBeFormat(b.getEngineID(), 'E')) {
            System.out.println("It must be in the <E00000> and not be duplicated. Please re-enter: ");
            b.setEngineID(sc.nextLine().toUpperCase());
        }
    }

    public void addCar() {
        Car newCar = new Car();
        checkCarID(this, newCar);
        Brand b = brandList.getUserChoice();
        newCar.setBrand(b);
        checkColor(newCar);
        checkFrameID(this, newCar);
        checkEngineID(this, newCar);
        this.add(newCar);
    }

    public void printBasedBrandName() {
        int count = 0;
        System.out.println("Enter a part of brand name: ");
        String partOfBrandName = sc.nextLine().toUpperCase();
        count = (int) this.stream().filter(obj -> obj.brand.brandName.toUpperCase().contains(partOfBrandName))
                .peek(obj -> System.out.println(obj.screenString())).count();
        if (count == 0) {
            System.out.println("No car is detected!");
        }
    }

    public void removeCar() {
        System.out.println("Enter ID to remove: ");
        String removedID = sc.nextLine();
        int pos = searchID(removedID);
        if (pos < 0) {
            System.out.println("Not found!");
        } else {
            this.remove(pos);
        }
    }

    public void updateCar() {
        System.out.println("Enter ID to update: ");
        String updatedID = sc.nextLine();
        int pos = searchID(updatedID);
        if (pos < 0) {
            System.out.println("Not found!");
        } else {
            Brand b = brandList.getUserChoice();
            this.get(pos).setBrand(b);
            checkColor(this.get(pos));
            Car db = new Car();
            checkFrameID(this, db);
            this.get(pos).setFrameID(db.getFrameID());
            checkEngineID(this, db);
            this.get(pos).setEngineID(db.getEngineID());
        }
    }

    public void listCar() {
        this.stream().sorted().forEach(System.out::println);
    }
}
