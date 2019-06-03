package sensor;
public class App {
    public static void main(String[] args) {
        // params: type interval
        Sensor snsr = new Sensor(args[0], args[1]);
        snsr.run();
    }
}
