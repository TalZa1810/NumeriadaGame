import Manager.GameManager;

import static java.lang.System.exit;

public class Main {
    public static void main(String args[]){
        GameManager manager = new GameManager();
        manager.run();
        exit(0);
    }
}
