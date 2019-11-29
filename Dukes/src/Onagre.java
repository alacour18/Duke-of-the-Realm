import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Onagre extends Soldier{
	
	public Onagre (Pane layer, Image image, Castle home, Castle target, double x, double y) {
		super(layer,image, home, target, x,y,1000,50,1,5,10);
	}
}