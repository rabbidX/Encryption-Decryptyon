import java.io.FileNotFoundException;
import java.util.Scanner;
import java.io.File;

public class ReadFileAndCalculate {
    public static void main(String[] args) throws FileNotFoundException {
        File file = new File("C:\\Users\\Rabbid\\Downloads\\dataset_91065.txt");
        Scanner scanner = new Scanner(file);
        int count =0;
        while (scanner.hasNext()) {
            int num = scanner.nextInt();
            if (num == 0) {
                break;
            }
            if (num % 2 == 0) {
                count++;
            }
        }
        System.out.println(count);
    }
}
