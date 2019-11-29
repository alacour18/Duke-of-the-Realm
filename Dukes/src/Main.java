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
import javafx.scene.text.Text;

public class Main extends Application{
	private Random rnd = new Random();

	private Pane playfieldLayer;
	
	private Image castleImage;
	private Image soldierImage;

	private Scene scene;
	private Input input;
	private AnimationTimer gameLoop;
	
	private String playersName[] =  {"Alice", "Hugo"};
	private Castle castleList[] = new Castle[playersName.length];
	private int castlePositionList[] = {100, 100, 800, 150, 300, 500, 850, 800}; 
	private boolean gamePause = false;
	
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
					// movement
	
					// update soldiers in scene
	
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
		castleImage = new Image(getClass().getResource("/images/castle.png").toExternalForm(), 150, 150, true, true);
		soldierImage = new Image(getClass().getResource("/images/soldier.png").toExternalForm(), 35, 50, true, true);
		
		input = new Input(scene);
		input.addListeners();
		
		createCastles(playersName);
				
		Soldier toto = new Piquier(playfieldLayer, soldierImage, castleList[0], castleList[1], 300, 300);
		
		scene.setOnMousePressed(e -> {
			Castle c;
			c = whichCastle(castleList, e.getX(), e.getY());
			System.out.println(c);
		});
	}
	
	private void createCastles(String[] playersName){
		int posX, posY;
		for(int i=0; i<playersName.length; i++) {
			String castleOwner= playersName[i];
			posX = castlePositionList[i*2];
			posY = castlePositionList[i*2 + 1];
			Castle Castle = new Castle(playfieldLayer, castleImage, posX, posY, castleOwner);	
			castleList[i] = Castle;
			System.out.println(castleList[i]);
		}
	}
	
	private Castle whichCastle (Castle[] castleList, double x, double y) { //retourne le chateau sur lequel la souris clique.
		for (int i=0 ; i< castleList.length; i++) {
			Castle currentCastle = castleList[i];
			if ((x >= currentCastle.x) && (x <= (currentCastle.x + currentCastle.w))) {
				if ((y >= currentCastle.y) && (y <= (currentCastle.y + currentCastle.h))) {
					return currentCastle;
				}
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}








