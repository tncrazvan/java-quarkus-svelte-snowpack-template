import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.annotations.QuarkusMain;

@QuarkusMain
public class Starter {
    public static void main(String[] args) {
        System.out.println("Quarkus server started");
        Quarkus.run(args);
    }
}
