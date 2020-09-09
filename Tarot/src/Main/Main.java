// @Authors Cecile Macaire & Théo Frison 



/**
 * Cette application a été crée par Frison Théo et Macaire Cécile dans le but d'un projet crée pour le module M3105
 * Nous avons utilisé la documentation Oracle et Openclassroom pour nous aider à la réalisation de ce projet.
 */

package Main;

import Controler.Controler;
import Model.Model;
import View.View;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class Main extends Application {

	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * Lance l'apllication en relevant les exceptions
	 */
	public void start(Stage primaryStage) throws Exception {
		Model m = new Model();
		Controler c = new Controler(m);
		View v = new View(c, m);
		m.addObserver(v);
		exitApplication(primaryStage);
	}

	/**
	 * Gestion de fermeture de l'application
	 * @param primaryStage Un stage dans l'application
	 */
	private void exitApplication(Stage primaryStage) {
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				System.exit(0);
			}
		});
	}

}


