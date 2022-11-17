package Classes;

import Classes.Com.BrandList;
import Classes.Com.CarList;
import Classes.Com.Menu;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CarManager {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> ops = new ArrayList<>(
                List.of("List all brands",
                        "Add a new brand",
                        "Search a brand based on its ID",
                        "Update a brand",
                        "Save brands to the file, named brands.txt",
                        "List all cars in ascending order of brand names",
                        "List cars based on a part of an input brand name",
                        "Add a car",
                        "Remove a car based on its ID",
                        "Update a car based on its ID",
                        "Save cars to file, named cars.txt"));
        BrandList brandList = new BrandList();
        brandList.loadFromFile("src/brands.txt");
        CarList carList = new CarList(brandList);
        carList.loadFromFile("src/cars.txt");
        int choice;
        Menu menu = new Menu();
        do {
            System.out.println("Enter your choice: ");
            choice = menu.int_getChoice(ops);
            switch (choice) {
                case 1 -> brandList.listBrands();
                case 2 -> brandList.addBrand();
                case 3 -> {
                    System.out.println("Enter ID to search: ");
                    String id = sc.nextLine();
                    if (brandList.searchID(id) < 0) {
                        System.out.println("Not found!");
                    } else {
                        System.out.println(brandList.get(brandList.searchID(id)).toString());
                    }
                }
                case 4 -> brandList.updateBrand();
                case 5 -> brandList.saveToFile("src/brands.txt");
                case 6 -> carList.listCar();
                case 7 -> carList.printBasedBrandName();
                case 8 -> carList.addCar();
                case 9 -> carList.removeCar();
                case 10 -> carList.updateCar();
                case 11 -> carList.saveFromFile("src/cars.txt");
                default -> {
                    System.out.println("This choice doesn't exist in menu");
                    System.out.println("Press any key to exit");
                }
            }
        } while (choice > 0 && choice <= ops.size());
    }
}
