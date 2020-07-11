import org.dreambot.api.methods.Calculations;
import org.dreambot.api.methods.map.Area;
import org.dreambot.api.script.AbstractScript;
import org.dreambot.api.script.Category;
import org.dreambot.api.script.ScriptManifest;
import org.dreambot.api.utilities.Timer;
import org.dreambot.api.wrappers.interactive.GameObject;

import java.awt.*;


@ScriptManifest(author = "You", name = "My First Script", version = 1.0, description = "Simple Tea Thiever", category = Category.THIEVING)
public class Main extends AbstractScript {

    public void onStart() {
        log("lets get some fucking money how about that?");
        //TEA_STOLEN = 0;
    }
    Timer timeRan = new Timer();
    private enum State {
        Steal, Bank, Wait

    };

    private State getState() {
        GameObject stall = getGameObjects().closest("Tea Stall");
        if(!getInventory().isFull())
        return State.Bank;
        if (stall != null)
        {return State.Steal;}
        if (stall == null)
        {return State.Wait;}


        return null;
    }
    public void onExit() {
        log("wat nou jung gratis geld niet goed genoeg?");
    }

    @Override
    public int onLoop(){

        switch (getState()) {
            case Steal:
                GameObject stall = getGameObjects().closest(gameObject -> gameObject != null && gameObject.getName().equals("Tea stall"));
                if (stall != null) {
                    stall.interact("Steal-from");
                }
                break;
            case Bank:
                Area theaStall = new Area(3269,2413,3264,3392);

                if (getInventory().isFull()) {
                    getBank().openClosest();
                    getBank().depositAll("Cup of Tea");
                    getBank().close();
                    getWalking().walk(theaStall.getRandomTile());
                    sleep(Calculations.random(5000,10000));

                }

                break;
            case Wait:
                sleep(400);
                break;



        }
        return Calculations.random(500, 600);
    }
    public void onPaint(Graphics2D g){
               Font font = new Font("Arial", Font.BOLD, 14);
                g.setFont(font);
                g.setColor(Color.BLACK);
                g.drawString("Time Ran: " + timeRan.formatTime(), 60, 472);

            }
}