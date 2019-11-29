import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Castle{
	
    private ImageView imageView;

	private Pane layer;
	
	protected double x;
    protected double y;
	
	private String owner;
	private double treasure;
	private int level;
	private int reserve[]; //reserve[0] : nbPiquier, reserve[1] : nbChevalier, reserve[2] : nbOnagre
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
		this.x = x;
		this.y = y;
		
		this.layer = layer;
		
		this.imageView = new ImageView(image);
        this.imageView.relocate(x, y);
        
        this.w = image.getWidth();
        this.h = image.getHeight();
		
		addToLayer();
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
	
	public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }

    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }
	
	//nextTurn()
}