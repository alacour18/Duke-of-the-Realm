import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

public class Castle{
	
    private ImageView imageView;

	private Pane layer;
	
	protected double x;
    protected double y;
	
	private String owner;
	private double treasure;
	private int level;
	protected int reserve[]; //reserve[0] : nbPiquier, reserve[1] : nbChevalier, reserve[2] : nbOnagre
	private Factory factory;
	private int door;
	
	protected double w;
	protected double h;

	
	public Castle(Pane layer, Image image, double x, double y, String owner) {
		this.owner = owner;
		this.level = 1;
		this.treasure = 0;
		this.reserve = Settings.INITIAL_RESERVE;
		this.door = (int)(Math.random()*4);
		this.x = x + image.getWidth()/2;
		this.y = y + image.getHeight()/2;
		
		this.layer = layer;
		
		this.imageView = new ImageView(image);
        this.imageView.relocate(x, y);
        
        this.w = image.getWidth();
        this.h = image.getHeight();
        
		addToLayer();
		createDoor(door, image);
	}
	
	public Castle(Pane layer, double x, double y, Image image) { //neutre
		this.owner = "neutral";
		this.level = 1;
		this.treasure = (int)(Math.random()*Settings.NEUTRAL_MAX_TREASURE);
		this.reserve = random_reserve();
		this.door = (int)(Math.random()*4);

	}
	
	int[] random_reserve(){
		int tab[] = null;
		for(int i=0; i<3; i++) {
			tab[i] = (int) Math.random()*Settings.NEUTRAL_NBMAX_SOLDIER;
		}
		return tab;
	}
	
	public void createDoor(int door, Image image) {
		System.out.println("door = "+door);
		if (door == 0) { //porte au nord
			Line doorN = new Line((x-w/2+w/3), y-h/2, (x-w/2+2*w/3), y-h/2);
			doorN.setStroke(Color.RED);
			this.layer.getChildren().add(doorN);
		}
		if (door == 1) { //porte à l'est
			Line doorE = new Line((x-w/2+w), y-h/2 + h/3, (x+w/2), y-h/2 + 2*h/3);
			doorE.setStroke(Color.RED);
			this.layer.getChildren().add(doorE);
		}
		if (door == 2) { //porte au sud
			Line doorS = new Line((x-w/2+w/3), y+ h/2, (x-w/2+2*w/3), y+ h/2);
			doorS.setStroke(Color.RED);
			this.layer.getChildren().add(doorS);
		}
		if (door == 3) { //porte à l'ouest
			Line doorW = new Line(x-w/2,y -h/2+ h/3, x-w/2, y -h/2+ 2*h/3);
			doorW.setStroke(Color.RED);
			this.layer.getChildren().add(doorW);
		}
	}
	
	public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }

    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }
	
	//nextTurn()
}