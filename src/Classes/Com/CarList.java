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
            String carOut = String.format("%s, %s, %s, %s, %s", car.carID, car.brand, car.color, car.frameID, car.engineID);
            writer.write(carOut);
        }
        return true;
    }

    public int searchID(String carID) {
        int N = this.size();
        for (int i = 0; i < N; i++) {
            if (this.get(i).carID == carID) {
                return i;
            }
        }
        return -1;
    }

    public int searchFrame(String fID) {
        int N = this.size();
        for (int i = 0; i < N; i++) {
            if (this.get(i).frameID == fID) {
                return i;
            }
        }
        return -1;
    }

    public int searchEngine(String eID) {
        int N = this.size();
        for (int i = 0; i < N; i++) {
            if (this.get(i).engineID == eID) {
                return i;
            }
        }
        return -1;
    }

    public void addCar() {
        Car newCar = new Car();
        this.add(newCar);

        System.out.println("Enter car ID: ");
        newCar.setCarID(sc.nextLine());
        for (Car car : this) {
            if (newCar.getCarID().equals(car.carID)) {
                System.out.println("ID already exists in the list. Please re-enter!");
                newCar.setCarID(sc.nextLine());
            }
        }

        Menu menu = new Menu();
        Brand b = menu.ref_getChoice(brandList);

        System.out.println("Enter color: ");
        newCar.setColor(sc.nextLine());
        while (newCar.getColor().isBlank()) {
            System.out.println("The brand name is blank. Please re-enter!");
            newCar.setColor(sc.nextLine());
        }

        System.out.println("Enter frame ID: ");
        newCar.setFrameID(sc.nextLine());
        for (Car car : this) {
            if (newCar.getFrameID().equals(car.frameID)) {
                System.out.println("It must be in the <F0000> and not be duplicated. Please re-enter!");
                newCar.setFrameID(sc.nextLine());
            }
        }

        System.out.println("Enter engine ID: ");
        newCar.setEngineID(sc.nextLine());
        for (Car car : this) {
            if (newCar.getEngineID().equals(car.engineID)) {
                System.out.println("It must be in the <E0000> and not be duplicated. Please re-enter!");
                newCar.setEngineID(sc.nextLine());
            }
        }
    }

    public void printBasedBrandName() {
        int N = this.size();
        int count = 0;
        System.out.println("Enter a part of brand name: ");
        String partOfBrandName = sc.nextLine();
        for (int i = 0; i < N; i++) {
            Car c = this.get(i);
            if (c.brand.brandName.toUpperCase().contains(partOfBrandName.toUpperCase())) {
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
            Menu menu = new Menu();
            Brand b = menu.ref_getChoice(brandList);

            System.out.println("Update color: ");
            this.get(pos).setColor(sc.nextLine());
            while (this.get(pos).getColor().isBlank()) {
                System.out.println("The brand name is blank. Please re-enter!");
                this.get(pos).setColor(sc.nextLine());
            }

            System.out.println("Update frame ID: ");
            this.get(pos).setFrameID(sc.nextLine());
            for (Car car : this) {
                if (this.get(pos).getFrameID().equals(car.frameID)) {
                    System.out.println("It must be in the <F0000> and not be duplicated. Please re-enter!");
                    this.get(pos).setFrameID(sc.nextLine());
                }
            }

            System.out.println("Update engine ID: ");
            this.get(pos).setEngineID(sc.nextLine());
            for (Car car : this) {
                if (this.get(pos).getEngineID().equals(car.engineID)) {
                    System.out.println("It must be in the <E0000> and not be duplicated. Please re-enter!");
                    this.get(pos).setEngineID(sc.nextLine());
                }
            }
        }
        return true;
    }

    public void listCar() {
        Collections.sort(this);
        int N = this.size();
        for (int i = 0; i < N; i++) {
            Car c = this.get(i);
            System.out.println(c.screenString());
        }
    }
}
