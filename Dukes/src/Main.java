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
		
		
		scene.setOnMousePressed(e -> {
			
			Castle c;
			double currentx = e.getX();
			double currenty = e.getY();
			if(isCastle(castleList, currentx, currenty)) {
				c = whichCastle(castleList, currentx, currenty);
				System.out.println(c);
				
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
	
	private void createCastles(String[] playersName, Castle[] castleList){
		int posX, posY;
		
		for(int i=0; i<playersName.length; i++) {
			String castleOwner= playersName[i];
			posX = rnd.nextInt((int)(1000-castleImage.getWidth()+1));
			posY = rnd.nextInt((int)(1000-castleImage.getHeight()+1));
			
			if(i == 0) {
				Castle PlayerCastle = new Castle(playfieldLayer, castleImage, posX, posY, castleOwner);
				castleList[0] = PlayerCastle;
			}
			else {
				
				/*while(isCastle(castleList, posX, posY)) {
					posX = rnd.nextInt((int)(1000-castleImage.getWidth()+1));
					posY = rnd.nextInt((int)(1000-castleImage.getHeight()+1));
				}*/
				Castle Castle = new Castle(playfieldLayer, enemycastleImage, posX, posY, castleOwner);	
				castleList[i] = Castle;
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
	
	public static void main(String[] args) {
		launch(args);
	}
}








