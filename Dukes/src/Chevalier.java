import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Chevalier extends Soldier{
	
	public Chevalier (Pane layer, Image image, Castle home, Castle target) {
		super(layer,image,home,target,home.x,home.y,500,20,6,3,5);
	}
}