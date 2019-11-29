import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Piquier extends Soldier{

	public Piquier (Pane layer, Image image, Castle home, Castle target, double x, double y) {
		super(layer,image, home, target, x,y,100,5,2,1,1);
	}

}