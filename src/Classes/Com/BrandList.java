package Classes.Com;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class BrandList extends java.util.ArrayList<Brand> {
    Scanner sc = new Scanner(System.in);

    public BrandList() {

    }

    public void loadFromFile(String filename) throws IOException {
        File f = new File(filename);
        try (BufferedReader reader = new BufferedReader(new FileReader(f))) {
            String line = reader.readLine();
            while (line != null) {
                String[] dataBrand = line.split("[,:]");
                String[] brandIn = Arrays.stream(dataBrand).map(String::strip).toArray(String[]::new);
                Brand brand = new Brand(brandIn[0], brandIn[1], brandIn[2], Double.parseDouble(brandIn[3]));
                this.add(brand);
                line = reader.readLine();
            }
        }
    }

    public void saveToFile(String filename) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (Brand brand : this) {
                String brandOut = String.format("%s, %s, %s: %.3f%n", brand.brandID, brand.brandName, brand.soundBrand, brand.price);
                writer.write(brandOut);
            }
        }
    }

    public int searchID(String bID) {
        int N = this.size();
        for (int i = 0; i < N; i++) {
            if (bID.equalsIgnoreCase(this.get(i).brandID)) {
                return i;
            }
        }
        return -1;
    }

    public Brand getUserChoice() {
        Menu mnu = new Menu();
        return mnu.ref_getChoice(this);
    }

    public void checkBrandID(ArrayList<Brand> a, Brand b) {
        System.out.println("Enter brand ID: ");
        b.setBrandID(sc.nextLine().toUpperCase());
        while (a.stream().anyMatch(obj -> b.getBrandID().equals(obj.brandID))) {
            System.out.println("ID already exists in the list. Please re-enter: ");
            b.setBrandID(sc.nextLine().toUpperCase());
        }
    }

    public void checkBrandName(Brand b) {
        System.out.println("Enter brand name: ");
        b.setBrandName(sc.nextLine());
        while (b.getBrandName().isBlank()) {
            System.out.println("The brand name is blank. Please re-enter: ");
            b.setBrandName(sc.nextLine());
        }
    }

    public void checkSoundBar(Brand b) {
        System.out.println("Enter brand name: ");
        b.setSoundBrand(sc.nextLine());
        while (b.getSoundBrand().isBlank()) {
            System.out.println("The sound bar is blank. Please re-enter: ");
            b.setSoundBrand(sc.nextLine());
        }
    }

    public void checkPrice(Brand b) {
        System.out.println("Enter price: ");
        b.setPrice(sc.nextDouble());
        while (b.getPrice() <= 0) {
            System.out.println("Price must be higher than zero. Please re-enter: ");
            b.setPrice(sc.nextDouble());
        }
    }

    public void addBrand() {
        Brand newBrand = new Brand();
        checkBrandID(this, newBrand);
        checkBrandName(newBrand);
        checkSoundBar(newBrand);
        checkPrice(newBrand);
        this.add(newBrand);
    }

    public void updateBrand() {
        System.out.println("Enter ID to research: ");
        String brandID = sc.nextLine();
        int pos = searchID(brandID);
        if (pos < 0) {
            System.out.println("Not found!");
        } else {
            checkBrandName(this.get(pos));
            checkSoundBar(this.get(pos));
            checkPrice(this.get(pos));
        }
    }

    public void listBrands() {
        this.forEach(System.out::println);
    }
}
