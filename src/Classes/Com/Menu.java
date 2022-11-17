package Classes.Com;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu {
    public <E> int int_getChoice(ArrayList<E> options) {
        int response = 0;
        Scanner sc = new Scanner(System.in);
        int N = options.size();
        for (int i = 0; i < N; i++) {
            System.out.printf("%d - %s%n", i + 1, options.get(i));
        }
        System.out.printf("Please choose an option 1..%d: %n", N);
        response = sc.nextInt();
        return response;
    }

    public <E> E ref_getChoice(ArrayList<E> options) {
        int response = 0;
        int N = options.size();
        do {
            response = int_getChoice(options);
        } while (response < 0 || response > N);
        return options.get(response - 1);
    }
}
