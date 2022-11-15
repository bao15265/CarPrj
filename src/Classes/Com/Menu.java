import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {
    List<String> options = new ArrayList<String>(
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

    public int int_getChoice(ArrayList<String> options) {
        int response = 0;
        Scanner sc = new Scanner(System.in);
        int N = options.size();
        for (int i = 0; i < N; i++) {
            System.out.println(String.format("%d- %s", i + 1, options.get(i)));
        }
        System.out.println(String.format("Please choose an option 1..%d:", N));
        response = sc.nextInt();
        return response;
    }

    public String ref_getChoice(ArrayList<String> options) {
        int response = 0;
        int N = options.size();
        do {
            response = int_getChoice(options);
        } while (response < 0 || response > N);
        return options.get(response - 1);
    }
}
