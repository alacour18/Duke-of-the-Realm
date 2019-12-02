import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.scene.shape.*;

public class Main extends Application{
	private Random rnd = new Random();

	private Pane playfieldLayer;
	
	private Image castleImage;
	private Image enemycastleImage;
	private Image soldierImage;
	private Image knightImage;

	private Scene scene;
	private Input input;
	private AnimationTimer gameLoop;
	
	private String playersName[] =  {"Alice", "Hugo","Simon"};
	private Castle castleList[] = new Castle[playersName.length];
	private boolean gamePause = false;
	
	private List<Soldier> soldierList = new ArrayList<>();
	
	Group root;
	
	public void start(Stage primaryStage) {

		root = new Group();
		scene = new Scene(root, Settings.SCENE_WIDTH, Settings.SCENE_HEIGHT);
		//scene.getStylesheets().add(getClass().getResource("/css/application.css").toExternalForm());
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.show();

		// create layers
		playfieldLayer = new Pane();
		root.getChildren().add(playfieldLayer);
		
		loadGame();
		
		gameLoop = new AnimationTimer() {
			@Override
			public void handle(long now) {
				processInput(input, now);
				
				// player input
				//player.processInput();
				
				if(!gamePause) {
					//incrémentation de 1 piquier dans la réserve
					castleList[0].reserve[0]+=1;
					
					// movement
					soldierList.forEach(soldier -> soldier.move());;
					// update soldiers in scene
					soldierList.forEach(soldier -> soldier.updateUI());;
					// check if sprite can be removed
	
					// remove removables from list, layer, etc
	
					// update score, health, etc
				}
				

				// pause game
				if (input.isPause()) {
					if(gamePause) {
						gamePause = false;
					} 
					if(!gamePause){
						gamePause = true;
					}
				}
			}

			private void processInput(Input input, long now) {
				if (input.isExit()) {
					Platform.exit();
					System.exit(0);
				} 

			}

		};
		gameLoop.start();
	}

	
	private void loadGame() {
		castleImage = new Image(getClass().getResource("/images/Castle.png").toExternalForm(), 150, 150, true, true);
		enemycastleImage = new Image(getClass().getResource("/images/enemycastle.png").toExternalForm(), 150, 150, true, true);
		soldierImage = new Image(getClass().getResource("/images/soldier.png").toExternalForm(), 35, 50, true, true);
		knightImage = new Image(getClass().getResource("/images/knight.png").toExternalForm(), 35, 50, true, true);
		
		input = new Input(scene);
		input.addListeners();
		
		System.out.println(castleList[0]);
		
		createCastles(playersName, castleList);
		
		//Affichage de la barre de status.
		Rectangle status_bar = new Rectangle(0, Settings.SCENE_HEIGHT-Settings.STATUS_BAR_HEIGHT, Settings.SCENE_WIDTH, Settings.STATUS_BAR_HEIGHT);
		status_bar.setFill(Color.PEACHPUFF);
		this.playfieldLayer.getChildren().add(status_bar);
		Circle status_level = new Circle(Settings.STATUS_BAR_HEIGHT/2, Settings.SCENE_HEIGHT-Settings.STATUS_BAR_HEIGHT/2,Settings.STATUS_BAR_HEIGHT);
		status_level.setFill(Color.GOLDENROD);
		this.playfieldLayer.getChildren().add(status_level);
		
		scene.setOnMousePressed(e -> {
			
			Castle c;
			double currentx = e.getX();
			double currenty = e.getY();
			if(isCastle(castleList, currentx, currenty)) {
				c = whichCastle(castleList, currentx, currenty);
				System.out.println(c);
				
				display_status(c);
				
				//Une chance sur deux d'invoquer un piquier ou un chevalier en cliquant.
				if (rnd.nextInt()%2 == 0){
					if (castleList[0].reserve[0]>0) {
						Soldier toto = new Piquier(playfieldLayer, soldierImage, castleList[0], null);
						toto.target = c;
						soldierList.add(toto);
						castleList[0].reserve[0] -= 1;
					}
				}
				else{
					if (castleList[0].reserve[1]>0) {
						Soldier titi = new Chevalier(playfieldLayer, knightImage, castleList[0], null);
						titi.target = c;
						soldierList.add(titi);
						castleList[0].reserve[1] -= 1;
					}
				}
				
			}
			
		});
	}
	
	private void getCoords(Castle[] castleList, int[] posList) {
		int posX, posY;
		int cpt = 0;
		int length = castleList.length;
		boolean coordsOK = false;
		while(!coordsOK) {
			posX = rnd.nextInt((int)(Settings.SCENE_WIDTH - enemycastleImage.getWidth()/2 - 5));
			posY = rnd.nextInt((int)(Settings.SCENE_WIDTH - enemycastleImage.getWidth()/2 - 5));
			posList[0] = posX;
			posList[1] = posY;
			cpt = 0;
			for(int i=0; i<length; i++) {
				if(castleList[i] != null) {
					if(Math.abs(castleList[i].x - posX) < Settings.CASTLE_SPAWN_DISTANCE || Math.abs(castleList[i].y - posY) < Settings.CASTLE_SPAWN_DISTANCE) {
						System.out.println("salut");
						break;
					}
					cpt++;
				} else {
					length -= 1;
				}
				
			}
			if(cpt == length)
				coordsOK = true;
		}
 	}
	
	private void createCastles(String[] playersName, Castle[] castleList){
		
		int posX = 0;
		int posY = 0;
		for(int i=0; i<playersName.length; i++) {
			String castleOwner= playersName[i];
			
			if(i==0){
				posX = rnd.nextInt((int)(Settings.SCENE_WIDTH - castleImage.getWidth()/2 - 5));
				posY = rnd.nextInt((int)(Settings.SCENE_HEIGHT - Settings.STATUS_BAR_HEIGHT - castleImage.getHeight()/2 - 5));
				Castle PlayerCastle = new Castle(playfieldLayer, castleImage, posX, posY, castleOwner);
				castleList[i] = PlayerCastle;
			} else {
				int pos[] = {0,0};
				getCoords(castleList, pos);
				posX = pos[0];
				posY = pos[1];
				Castle PlayerCastle = new Castle(playfieldLayer, enemycastleImage, posX, posY, castleOwner);
				castleList[i] = PlayerCastle;
			}
			System.out.println(castleList[i]);
		}
	}
	
	private Castle whichCastle (Castle[] castleList, double x, double y) { //retourne le chateau aux coordonées x, y;
		
		for (int i=0 ; i< castleList.length; i++) {
			Castle currentCastle = castleList[i];
			if ((x >= currentCastle.x-currentCastle.w/2) && (x <= (currentCastle.x + currentCastle.w/2))) {
				if ((y >= currentCastle.y-currentCastle.h/2) && (y <= (currentCastle.y + currentCastle.h/2))) {
					return currentCastle;
				}
			}
		}
		return null;
	}
	
	private boolean isCastle (Castle[] castleList, double x, double y) { //retourne true si un chateau se trouve aux coordonées x,y;
		if (whichCastle (castleList, x, y) == null) {
			return false;
		}
		else {
			return true;
		}
		
	}
	
	private void display_status (Castle c) {
		
		Text level = new Text();
		level.setText(String.valueOf(c.level));
		level.setX(Settings.STATUS_BAR_HEIGHT/2); 
		level.setY(Settings.SCENE_HEIGHT-Settings.STATUS_BAR_HEIGHT/2);
		
		Text owner = new Text();
		owner.setText(c.owner);
		owner.setX( Settings.SCENE_WIDTH/7); 
		owner.setY(Settings.SCENE_HEIGHT-Settings.STATUS_BAR_HEIGHT/2);
		
		Text piquier = new Text();
		piquier.setText(String.valueOf(c.reserve[0]));
		piquier.setX( 2* Settings.SCENE_WIDTH/7); 
		piquier.setY(Settings.SCENE_HEIGHT-Settings.STATUS_BAR_HEIGHT/2);
		
		Text chevalier = new Text();
		chevalier.setText(String.valueOf(c.reserve[1]));
		chevalier.setX( 3* Settings.SCENE_WIDTH/7); 
		chevalier.setY(Settings.SCENE_HEIGHT-Settings.STATUS_BAR_HEIGHT/2);
		
		Text onagre = new Text();
		onagre.setText(String.valueOf(c.reserve[2]));
		onagre.setX( 4* Settings.SCENE_WIDTH/7); 
		onagre.setY(Settings.SCENE_HEIGHT-Settings.STATUS_BAR_HEIGHT/2);
		
		Text revenu = new Text();
		revenu.setText("+ "+String.valueOf(c.level*10)+" f/tour");
		revenu.setX( 5* Settings.SCENE_WIDTH/7); 
		revenu.setY(Settings.SCENE_HEIGHT-Settings.STATUS_BAR_HEIGHT/2);
		
		Text treasure = new Text();
		treasure.setText(String.valueOf(c.treasure));
		treasure.setX( 6* Settings.SCENE_WIDTH/7); 
		treasure.setY(Settings.SCENE_HEIGHT-Settings.STATUS_BAR_HEIGHT/2);
		
		this.playfieldLayer.getChildren().add(owner);
		this.playfieldLayer.getChildren().add(level);
		this.playfieldLayer.getChildren().add(piquier);
		this.playfieldLayer.getChildren().add(chevalier);
		this.playfieldLayer.getChildren().add(onagre);
		this.playfieldLayer.getChildren().add(revenu);
		this.playfieldLayer.getChildren().add(treasure);
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}








