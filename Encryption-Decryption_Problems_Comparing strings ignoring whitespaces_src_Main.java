import java.util.*;
/** 27.08.20
* javadoc
*/
class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String string1 = scanner.nextLine();
        String string2 = scanner.nextLine();
        String clean1 = string1.replaceAll(" ", "").trim();
        String clean2 = string2.replaceAll(" ",  "").trim();
        System.out.println(clean1.equals(clean2));
    }
}
