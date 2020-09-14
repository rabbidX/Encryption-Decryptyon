package encryptdecrypt;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        String mode = "enc";
        int key = 0;
        String data = "";
        String in = "";
        String out = "";
        String alg = "shift";
        for (int i= 0; i < args.length; i+= 2) {
            if ("-mode".equals(args[i])) {
                mode = args[i + 1];
            } else if ("-key".equals(args[i])) {
                key = Integer.parseInt(args[i + 1]);
            } else if ("-data".equals(args[i])) {
                data = args[i + 1];
            } else if ("-in".equals(args[i])) {
                in = args[i + 1];
            } else if ("-out".equals(args[i])) {
                out = args[i + 1];
            } else if ("-alg".equals(args[i])) {
                alg = args[i + 1];
            }
        }
        if ("".equals(data)) {
            if(!"".equals(in)) {
                File fileIn = new File(in);
                try (Scanner scanner = new Scanner(fileIn)) {
                    StringBuilder dataBuilder = new StringBuilder();
                    while (scanner.hasNext()) {
                        dataBuilder.append(scanner.nextLine());
                    }
                    data = dataBuilder.toString();
                }
            }
        }
        Context context = null;
        if (("enc").equals(mode)) {
            if ("shift".equals(alg)) {
                context = new Context(new ShiftEncrypt());
            } else if ("unicode".equals(alg)){
                context = new Context(new UnicodeEncrypt());
            }
        } else if("dec".equals(mode)) {
            if ("shift".equals(alg)) {
                context = new Context(new ShiftDecrypt());
            } else if ("unicode".equals(alg)){
                context = new Context(new UnicodeDecrypt());
            }
        }

        if (context!= null) {
            context.go(data, out, key);
        }

    }

}
class Context {
    private final ChangeSymbol algorithm;

    public ChangeSymbol getAlgorithm() {
        return algorithm;
    }

    public Context(ChangeSymbol algorithm) {
        this.algorithm = algorithm;
    }

    public void go(String data, String out, int key) throws IOException {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < data.length(); i++) {
            char c = data.charAt(i);
            result.append((char) algorithm.change(c, key));
        }
        if (!"".equals(out)) {
            try (FileWriter writer = new FileWriter(out)) {
                writer.write(result.toString());
            }
        } else {
            System.out.println(result);
        }
    }
}
interface  ChangeSymbol {
    int UPPERCASE_A = 'A';
    int UPPERCASE_Z = 'Z';
    int LOWERCASE_A = 'a';
    int LOWERCASE_Z = 'z';
    int change(char c, int key);
}

class UnicodeEncrypt implements ChangeSymbol {
    @Override
    public int change(char c, int key) {
        return ((int) c) + key;
    }
}

class UnicodeDecrypt implements ChangeSymbol {
    @Override
    public int change(char c, int key) {
        return ((int) c) - key;
    }
}

class ShiftEncrypt implements ChangeSymbol {
    @Override
    public int change(char c, int key) {
        int intC = c;
        if (intC >= LOWERCASE_A && intC <= LOWERCASE_Z) {
            intC += key;
            if (intC > LOWERCASE_Z) {
                intC -= LOWERCASE_Z -LOWERCASE_A + 1;
            }
            return intC;
        } else if (intC >= UPPERCASE_A && intC <= UPPERCASE_Z) {
            intC += key;
            if (intC > UPPERCASE_Z) {
                intC -= UPPERCASE_Z -UPPERCASE_A + 1;
            }
            return intC;
        }
        else {
            return intC;
        }
    }
}

class ShiftDecrypt implements ChangeSymbol {
    @Override
    public int change(char c, int key) {
        int intC = c;
        if (intC >= LOWERCASE_A && intC <= LOWERCASE_Z) {
            intC -= key;
            if (intC < LOWERCASE_A) {
                intC += LOWERCASE_Z -LOWERCASE_A + 1;
            }
            return intC;
        } else if (intC >= UPPERCASE_A && intC <= UPPERCASE_Z) {
            intC -= key;
            if (intC < UPPERCASE_A) {
                intC += UPPERCASE_Z -UPPERCASE_A + 1;
            }
            return intC;
        }
        else {
            return intC;
        }
    }
}