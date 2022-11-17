package Classes.Com;

import java.io.*;
import java.util.Arrays;
import java.util.Scanner;

public class BrandList extends java.util.ArrayList<Brand> {
    Scanner sc = new Scanner(System.in);

    public BrandList() {

    }

    public boolean loadFromFile(String filename) throws IOException {
        File f = new File(filename);
        if (!f.exists()) {
            return false;
        } else {
            BufferedReader reader = new BufferedReader(new FileReader(f));
            String line = reader.readLine();
            while (line != null) {
                String[] dataBrand = line.split("[,:]");
                String[] brandIn = Arrays.stream(dataBrand).map(String::strip).toArray(String[]::new);
                Brand brand = new Brand(brandIn[0], brandIn[1], brandIn[2], Double.parseDouble(brandIn[3]));
                this.add(brand);
                line = reader.readLine();
            }
            reader.close();
        }
        return true;
    }

    public boolean saveToFile(String filename) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        for (Brand brand : this) {
            String brandOut = String.format("%s, %s, %s: %.3f%n", brand.brandID, brand.brandName, brand.soundBrand, brand.price);
            writer.write(brandOut);
        }
        writer.close();
        return true;
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

    public void addBrand() {
        Brand newBrand = new Brand();

        System.out.println("Enter brand ID: ");
        newBrand.setBrandID(sc.nextLine().toUpperCase());
        for (int i = 0; i < this.size(); i++) {
            while (newBrand.getBrandID().equals(this.get(i).brandID)) {
                System.out.println("ID already exists in the list. Please re-enter: ");
                newBrand.setBrandID(sc.nextLine().toUpperCase());
                i = 0;
            }
        }

        System.out.println("Enter brand name: ");
        newBrand.setBrandName(sc.nextLine());
        while (newBrand.getBrandName().isBlank()) {
            System.out.println("The brand name is blank. Please re-enter: ");
            newBrand.setBrandName(sc.nextLine());
        }

        System.out.println("Enter sound bar: ");
        newBrand.setSoundBrand(sc.nextLine().toUpperCase());
        while (newBrand.getSoundBrand().isBlank()) {
            System.out.println("The sound bar is blank. Please re-enter: ");
            newBrand.setSoundBrand(sc.nextLine().toUpperCase());
        }

        System.out.println("Enter price: ");
        newBrand.setPrice(sc.nextDouble());
        while (newBrand.getPrice() <= 0) {
            System.out.println("Price must be higher than zero. Please re-enter: ");
            newBrand.setPrice(sc.nextDouble());
        }

        this.add(newBrand);
    }

    public void updateBrand() {
        System.out.println("Enter ID to research: ");
        String brandID = sc.nextLine();
        int pos = searchID(brandID);
        if (pos < 0) {
            System.out.println("Not found!");
        } else {
            System.out.println("Update brand name: ");
            this.get(pos).setBrandName(sc.nextLine());
            while (this.get(pos).getBrandName().isBlank()) {
                System.out.println("The brand name is blank. Please re-enter: ");
                this.get(pos).setBrandName(sc.nextLine());
            }

            System.out.println("Update sound bar: ");
            this.get(pos).setSoundBrand(sc.nextLine().toUpperCase());
            while (this.get(pos).getSoundBrand().isBlank()) {
                System.out.println("The sound bar is blank. Please re-enter: ");
                this.get(pos).setSoundBrand(sc.nextLine().toUpperCase());
            }

            System.out.println("Update price: ");
            this.get(pos).setPrice(sc.nextDouble());
            while (this.get(pos).getPrice() <= 0) {
                System.out.println("Price must be higher zero. Please re-enter: ");
                this.get(pos).setPrice(sc.nextDouble());
            }
        }
    }

    public void listBrands() {
        for (Brand brand : this) {
            System.out.println(brand);
        }
    }
}
