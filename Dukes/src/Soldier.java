import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

public class Soldier{
	
    private ImageView imageView;

    private Pane layer;

    protected double x;
    protected double y;

    protected double dx;
    protected double dy;
    
	protected double cost_prod;
	protected double time_prod;
	protected double speed;
	protected double hp;
	protected double damage;
	
	protected Castle home;
	protected Castle target;
	
	private double w;
	private double h;
	
	public Soldier(Pane layer, Image image, Castle home, Castle target, double x, double y, int cost_prod
			, int time_prod, int speed, int hp, int damage) {
		
		this.layer = layer;
		this.x = x;
		this.y = y;
		
		this.cost_prod = cost_prod;
		this.time_prod = time_prod;
		this.speed = speed;
		this.hp = hp;
		this.damage = damage;
		
		this.home = home;
		this.target = target;
		
        this.imageView = new ImageView(image);
        this.imageView.relocate(x, y);
        
        this.w = image.getWidth();
        this.h = image.getHeight();
        
		addToLayer();
	}
	
	public void move(){
		if (x < target.x) {
			x += speed;
		}
		if (x > target.x) {
			x -= speed;
		}
		if (y < target.y) {
			y += speed;
		}
		if (y > target.y) {
			y -= speed;
		}
	}
	
	public void updateUI() {
        imageView.relocate(x, y);
    }
	
	public void addToLayer() {
        this.layer.getChildren().add(this.imageView);
    }

    public void removeFromLayer() {
        this.layer.getChildren().remove(this.imageView);
    }

	public double getHeight() {
		return h;
	}

	public double getWidth() {
		return w;
	}

	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
}