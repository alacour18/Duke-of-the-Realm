import javafx.scene.image.Image;
import javafx.scene.layout.Pane;

public class Chevalier extends Soldier{
	
	public Chevalier (Pane layer, Image image, Castle home, Castle target, double x, double y) {
		super(layer,image,home,target,x,y,500,20,6,3,5);
	}
}