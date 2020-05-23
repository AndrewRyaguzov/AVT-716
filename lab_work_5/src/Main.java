import java.io.*;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Program program;
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader("Configure.txt"));
            String text;
            float[] arr = new float[256];
            for (int i = 0; (text = in.readLine()) != null; i++) {
                arr[i] = Float.parseFloat(text);
                System.out.println(+arr[i]);
            }
            in.close();
            program = new Program(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]);
            while (!program.isDone) {
                program.run();
            }
            {
                try (FileWriter writer = new FileWriter("Configure.txt", false)) {
                    String[] arr1 = program.getSettings();
                    for (int i = 0; i < 6; i++){
                        writer.write(arr1[i]+"\n");
                        System.out.println(arr1[i]);}
                    writer.flush();
                    writer.close();
                } catch (IOException ex) {

                    System.out.println(ex.getMessage());
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        System.exit(0);
    }
}
