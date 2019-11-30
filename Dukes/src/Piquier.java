import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Piquier extends Soldier{

	public Piquier (Pane layer, Image image, Castle home, Castle target) {
		super(layer,image, home, target, home.x,home.y,100,5,2,1,1);
	}

}