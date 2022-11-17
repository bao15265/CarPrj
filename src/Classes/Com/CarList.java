package Classes.Com;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class CarList extends java.util.ArrayList<Car> {
    Scanner sc = new Scanner(System.in);
    BrandList brandList;

    public CarList(BrandList bList) {
        brandList = bList;
    }

    public boolean loadFromFile(String filename) throws IOException {
        File f = new File(filename);
        if (!f.exists()) {
            return false;
        } else {
            BufferedReader reader = new BufferedReader(new FileReader(f));
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
            reader.close();
        }
        return true;
    }

    public boolean saveFromFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Car car : this) {
            String carOut = String.format("%s, %s, %s, %s, %s%n", car.carID, car.brand.brandID, car.color, car.frameID, car.engineID);
            writer.write(carOut);
        }
        writer.close();
        return true;
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

    public void addCar() {
        Car newCar = new Car();

        System.out.println("Enter car ID: ");
        newCar.setCarID(sc.nextLine().toUpperCase());
        for (int i = 0; i < this.size(); i++) {
            while (newCar.getCarID().equals(this.get(i).carID)) {
                System.out.println("ID already exists in the list. Please re-enter: ");
                newCar.setCarID(sc.nextLine().toUpperCase());
                i = 0;
            }
        }

        Brand b = brandList.getUserChoice();
        newCar.setBrand(b);

        System.out.println("Enter color: ");
        newCar.setColor(sc.nextLine().toLowerCase());
        while (newCar.getColor().isBlank()) {
            System.out.println("The color is blank. Please re-enter: ");
            newCar.setColor(sc.nextLine().toLowerCase());
        }

        System.out.println("Enter frame ID: ");
        newCar.setFrameID(sc.nextLine().toUpperCase());
        for (int i = 0; i < this.size(); i++) {
            while (notBeFormat(newCar.getFrameID(), 'F') || newCar.getFrameID().equals(this.get(i).frameID)) {
                System.out.println("It must be in the <F00000> and not be duplicated. Please re-enter: ");
                newCar.setFrameID(sc.nextLine().toUpperCase());
                i = 0;
            }
        }

        System.out.println("Enter engine ID: ");
        newCar.setEngineID(sc.nextLine().toUpperCase());
        for (int i = 0; i < this.size(); i++) {
            while (notBeFormat(newCar.getEngineID(), 'E') || newCar.getEngineID().equals(this.get(i).engineID)) {
                System.out.println("It must be in the <E00000> and not be duplicated. Please re-enter: ");
                newCar.setEngineID(sc.nextLine().toUpperCase());
                i = 0;
            }
        }

        this.add(newCar);
    }

    public void printBasedBrandName() {
        int count = 0;
        System.out.println("Enter a part of brand name: ");
        String partOfBrandName = sc.nextLine().toUpperCase();
        for (Car c : this) {
            if (c.brand.brandName.toUpperCase().contains(partOfBrandName)) {
                System.out.println(c.screenString());
                count++;
            }
        }
        if (count == 0) {
            System.out.println("No car is detected!");
        }
    }

    public boolean removeCar() {
        System.out.println("Enter ID to remove: ");
        String removedID = sc.nextLine();
        int pos = searchID(removedID);
        if (pos < 0) {
            System.out.println("Not found!");
            return false;
        } else {
            this.remove(pos);
        }
        return true;
    }

    public boolean updateCar() {
        System.out.println("Enter ID to update: ");
        String updatedID = sc.nextLine();
        int pos = searchID(updatedID);
        if (pos < 0) {
            System.out.println("Not found!");
            return false;
        } else {
            Brand b = brandList.getUserChoice();
            this.get(pos).setBrand(b);

            System.out.println("Update color: ");
            this.get(pos).setColor(sc.nextLine().toLowerCase());
            while (this.get(pos).getColor().isBlank()) {
                System.out.println("The brand name is blank. Please re-enter: ");
                this.get(pos).setColor(sc.nextLine().toLowerCase());
            }

            System.out.println("Update frame ID: ");
            this.get(pos).setFrameID(sc.nextLine().toUpperCase());
            for (int i = 0; i < pos; i++) {
                while (notBeFormat(this.get(pos).getFrameID(), 'F') || this.get(pos).getFrameID().equals(this.get(i).frameID)) {
                    System.out.println("It must be in the <F00000> and not be duplicated. Please re-enter: ");
                    this.get(pos).setFrameID(sc.nextLine().toUpperCase());
                    i = 0;
                }
            }
            for (int i = pos + 1; i < this.size(); i++) {
                while (notBeFormat(this.get(pos).getFrameID(), 'F') || this.get(pos).getFrameID().equals(this.get(i).frameID)) {
                    System.out.println("It must be in the <F00000> and not be duplicated. Please re-enter: ");
                    this.get(pos).setFrameID(sc.nextLine().toUpperCase());
                    i = 0;
                }
            }

            System.out.println("Update engine ID: ");
            this.get(pos).setEngineID(sc.nextLine().toUpperCase());
            for (int i = 0; i < pos; i++) {
                while (notBeFormat(this.get(pos).getEngineID(), 'E') || this.get(pos).getEngineID().equals(this.get(i).engineID)) {
                    System.out.println("It must be in the <E00000> and not be duplicated. Please re-enter: ");
                    this.get(pos).setEngineID(sc.nextLine().toUpperCase());
                    i = 0;
                }
            }
            for (int i = pos + 1; i < this.size(); i++) {
                while (notBeFormat(this.get(pos).getEngineID(), 'E') || this.get(pos).getEngineID().equals(this.get(i).engineID)) {
                    System.out.println("It must be in the <E00000> and not be duplicated. Please re-enter: ");
                    this.get(pos).setEngineID(sc.nextLine().toUpperCase());
                    i = 0;
                }
            }
        }
        return true;
    }

    public void listCar() {
        Collections.sort(this);
        for (Car c : this) {
            System.out.println(c.screenString());
        }
    }
}
