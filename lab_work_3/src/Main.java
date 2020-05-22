public class Main {
        public static void main(String[] args) {
            Program program = new Program();
            while (!program.isDone) {
                program.run();
            }
            System.exit(0);
        }

}
