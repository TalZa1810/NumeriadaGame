import com.google.gson.Gson;
import logic.AAA;
import logic.Game;

/**
 * Created by talza on 28/02/2017.
 */
public class Test {

    public void foo(){
        Game.eGameMode GM = Game.eGameMode.HumanVsComputer;
        Gson gson = new Gson();
        String test2 = gson.toJson(GM);
        System.out.println(GM);
        System.out.println(test2);
        AAA aaa= new AAA();
    }

    public static void main (String[] args){
        Test m_Test= new Test();
        m_Test.foo();
    }
}
