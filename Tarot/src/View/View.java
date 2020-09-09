// @Authors Cecile Macaire & Théo Frison 


package View;

import javafx.event.ActionEvent;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javafx.scene.shape.Rectangle;

import javafx.event.EventHandler;

import Controler.Controler;
import Model.Cards;
import Model.Model;
import Model.Players;
import javafx.scene.Cursor;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.RotateTransition;
import javafx.animation.SequentialTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.scene.Node;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

public class View implements Observer {
	Controler c;
	Model m; 
	Players p = new Players();
	
	public Stage stage;
	public Scene scene;
	public Group cards;
	
	int widthWindow = 900; // largeur de la fenetre
	int heightWindow = 900; // hauteur de la fenetre
	
	private RotateTransition rotateTransitionF; // rotation des cartes de dos
	private RotateTransition rotateTransitionB; // rotation des cartes de face

	static long halfFlipDuration = 500; // durée de la rotation
	
	// Boutons PLAY / PRISE / GARDE / SANS LE CHIEN / CONTRE LE CHIEN
	Button buttonPlay = new Button("PLAY"); 
	Button buttonTake = new Button("PRISE");
	Button buttonGuard = new Button("GARDE");
	Button buttonWithout = new Button("SANS LE CHIEN");
	Button buttonAgainst = new Button("CONTRE LE CHIEN");
	
	ImageView imageBackground = new ImageView();
	
	// Listes
	ArrayList<ImageView> imagesPlayer1 = new ArrayList<ImageView>(); // images dos des cartes du joueur 1
	ArrayList<ImageView> imagesPlayer2 = new ArrayList<ImageView>(); // images dos des cartes du joueur 2
	ArrayList<ImageView> imagesPlayer3 = new ArrayList<ImageView>(); // images dos des cartes du joueur 3
	ArrayList<ImageView> imagesPlayer4 = new ArrayList<ImageView>(); // images dos des cartes du joueur 4
	ArrayList<ImageView> imagesDog = new ArrayList<ImageView>(); // images dos des cartes du chien
	ArrayList<ImageView> frontCardsPlayer1 = new ArrayList<ImageView>(); // images face des cartes du joueur 1
	ArrayList<ImageView> frontCardsDog = new ArrayList<ImageView>(); // images face des cartes du chien

	public View(Controler c, Model m) {
		this.c = c;
		this.m = m;
		m = new Model();
		c = new Controler(m);

		stage = new Stage();
		cards = new Group();
		scene = new Scene(cards, widthWindow, heightWindow);
		
		initStage();
		
		// Image de fond
		Image background = new Image("file:./otherimg/background.jpg");
		ImageView backgroundImage = new ImageView(background);
		backgroundImage.setFitWidth(widthWindow);
		backgroundImage.setFitHeight(heightWindow);
		addStage(backgroundImage);
		
		// Ajout boutons dans la fenetre
		buttonView(buttonPlay, 350, 150, 200, 100);
		buttonView(buttonTake, 40, 150, 200, 100);
		buttonView(buttonGuard, 245, 150, 200, 100);
		buttonView(buttonWithout, 452, 150, 200, 100);
		buttonView(buttonAgainst, 660, 150, 200, 100);

		event();

		chargingCards(); //lancement de la distribution
	}
	
	
/**
 * Rotation des 18 cartes du joueur
 */
	public void turnCards() {
		ImageView cardBack;
		ImageView cardFront;

		for (int i = 0; i < 18; i++) {
			
			// recupération de toutes les cartes du joueur de dos et de face
			cardBack = imagesPlayer1.get(i);
			cardFront = frontCardsPlayer1.get(i);
			
			// rotation des cartes de dos du joueur à 90 degré
			rotateTransitionF = new RotateTransition(Duration.millis(halfFlipDuration), cardBack); 
			rotateTransitionF.setInterpolator(Interpolator.LINEAR);
			rotateTransitionF.setAxis(Rotate.Y_AXIS);
			rotateTransitionF.setFromAngle(0);
			rotateTransitionF.setToAngle(90);
			
			cardFront.setVisible(true); // on rend visible les cartes de face du joueur
			
			// rotation des cartes de face du joueur à 90 degré
			rotateTransitionB = new RotateTransition(Duration.millis(halfFlipDuration), cardFront);
			rotateTransitionB.setInterpolator(Interpolator.LINEAR);
			rotateTransitionB.setAxis(Rotate.Y_AXIS);
			rotateTransitionB.setFromAngle(-90);
			rotateTransitionB.setToAngle(0);
			
			//transition les unes apres les autres 
			SequentialTransition st = new SequentialTransition(rotateTransitionF, rotateTransitionB);
			st.play();

		}
	}
	

/**
 * Regroupe les evenements se deroulant lors de la prise ou de la garde	
 */
	public void takeOrGuard() {
		//On cache les boutons
		buttonGuard.setVisible(false);
		buttonAgainst.setVisible(false);
		buttonWithout.setVisible(false);
		buttonTake.setVisible(false);
		
		ImageView cardFront;
		ImageView cardBack;
		
		int x = 280;

		for (int index = 0; index < 6; index++) {
			cardBack = imagesDog.get(index); // on recupere les imagesView du chien associé au numéro d'index dans cardBack 
			cardBack.setOnMousePressed(null);
			cardFront = frontCardsDog.get(index);
			cardBack.toFront();
			cardFront.toFront();
			// On crée une animation -> les cartes se déplacent de x et y vers le paquet du joueur 1
			TranslateTransition t = new TranslateTransition(Duration.seconds(2), cardBack);
			t.setToX(x);
			t.setToY(350);
			x += 30;
			t.setAutoReverse(true);
			
			//Après le déplacement des cartes, on les retourne 
			rotateTransitionF = new RotateTransition(Duration.millis(halfFlipDuration), cardBack);
			rotateTransitionF.setInterpolator(Interpolator.LINEAR);
			rotateTransitionF.setAxis(Rotate.Y_AXIS);
			rotateTransitionF.setFromAngle(0);
			rotateTransitionF.setToAngle(90);

			cardFront.setLayoutX(-85);
			cardFront.setVisible(true);

			rotateTransitionB = new RotateTransition(Duration.millis(halfFlipDuration), cardFront);
			rotateTransitionB.setInterpolator(Interpolator.LINEAR);
			rotateTransitionB.setAxis(Rotate.Y_AXIS);
			rotateTransitionB.setFromAngle(-90);
			rotateTransitionB.setToAngle(0);
			
			// Animations les unes apres les autres
			SequentialTransition st = new SequentialTransition(t, rotateTransitionF, rotateTransitionB);
			st.play();
			
			// Appelle de la fonction addCardsDog -> permet de selectionner les cartes que le joueur veut mettre dans le chien
			addCardsDog(cardBack);

		}
		
		for (int index = 0; index < 18; index++) {
			// On deplace les cartes du joueurs vers la gauche pour voir toutes les cartes avec le chien en plus
			cardFront = frontCardsPlayer1.get(index);
			cardFront.toFront();
			TranslateTransition t = new TranslateTransition(Duration.seconds(2), cardFront);
			t.setToX(-89);
			t.setToY(0);
			t.setAutoReverse(true);
			t.play();
			
			// Appelle de la fonction addCardsDog1 -> permet de selectionner les cartes à mettre dans le chien
			addCardsDog1(cardFront);
		}
		
	}
	

/**
 * Lors du clic de la souris sur la carte, on ajoute une carte a l'ecart
 * @param carteFront La carte vue de face
 */
	public void addCardsDog1(ImageView carteFront) {
		Rectangle r = new Rectangle(); // rectangle pour constituer l'ecart 
		rectangleDog(r, 50, 475, 800, 150); // appelle de la méthode rectangleDog 
			
			carteFront.setOnMousePressed(new EventHandler<MouseEvent>() { // si le joueur clique sur une carte 
				public void handle(MouseEvent me) {
					dragAndDrop();
				}
			});
			
//			carteFront.setOnMouseReleased(new EventHandler<MouseEvent>(){
//				public void handle(MouseEvent me) {
//					if(r.contains(me.getSceneX(), me.getSceneY())){	
//					}
//				}
//			});
			
			
		}

/**
 * Lors du clic de la souris sur la carte, on ajoute une carte a l'ecart 	
 * @param card La carte vu de dos
 */
	public void addCardsDog(ImageView card){
		Rectangle r = new Rectangle();
		rectangleDog(r, 50, 475, 800, 150);
		card.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent me) {
				dragAndDrop();
			}
		});

	}
	
	public double getRectX(Rectangle r){ // recuperation de la coordonnée x du rectangle
		return r.getX();
	}
	
/**
 * Creation d'un rectangle avec une position x, y,  une largeur et une hauteur
 * @param r Un rectangle
 * @param x Position x
 * @param y Position y
 * @param w Largeur du rectangle
 * @param h Hauteur du rectangle
 */
	public void rectangleDog(Rectangle r, int x, int y, int w, int h) {
		r.setX(x);
		r.setY(y);
		r.setWidth(w);
		r.setHeight(h);
		r.setArcHeight(15);
		r.setArcWidth(15);
		r.setFill(Color.LIGHTSTEELBLUE);
		addStage(r);
		r.setVisible(true);
	}

/**
 * Affichage des cartes au centre en un seul paquet
 */
	public void chargingCards() {
		int x = 130;
		for (Cards ca : m.getpackagePlayer1()) { // pour toutes les cartes du joueur 1 
			Image card = new Image("file:./img/79.jpg"); // creation image dos de la carte 
			ImageView imageCard = new ImageView(card);

			Image cardFront = new Image("file:./img/" + ca.getNum() + ".jpg"); // creation image face de la carte -> recuperation avec son numero
			ImageView imageCardFront = new ImageView(cardFront);

			imageCard.setX(350);
			imageCard.setY(350);
			imageCard.setPreserveRatio(true);
			imageCard.setFitHeight(100);

			addStage(imageCard); // ajout au groupe 
			
			imagesPlayer1.add(imageCard);
			frontCardsPlayer1.add(imageCardFront);
			imageCardFront.setY(700);
			imageCardFront.setX(x);
			x += 33;
			imageCardFront.setPreserveRatio(true);
			imageCardFront.setFitHeight(100);
			imageCardFront.setVisible(false);
			addStage(imageCardFront);
		}

		for (@SuppressWarnings("unused") Cards ca : m.getpackagePlayer2()) {
			Image card = new Image("file:./img/79.jpg");
			ImageView imageCard = new ImageView(card);

			imageCard.setX(350);
			imageCard.setY(350);
			imageCard.setPreserveRatio(true);
			imageCard.setFitHeight(100);

			addStage(imageCard);

			imagesPlayer2.add(imageCard);
		}

		for (@SuppressWarnings("unused") Cards ca : m.getpackagePlayer3()) {
			Image card = new Image("file:./img/79.jpg");
			ImageView imageCard = new ImageView(card);

			imageCard.setX(350);
			imageCard.setY(350);
			imageCard.setPreserveRatio(true);
			imageCard.setFitHeight(100);

			addStage(imageCard);

			imagesPlayer3.add(imageCard);
		}

		for (@SuppressWarnings("unused") Cards ca : m.getpackagePlayer4()) {
			Image card = new Image("file:./img/79.jpg");
			ImageView imageCard = new ImageView(card);

			imageCard.setX(350);
			imageCard.setY(350);
			imageCard.setPreserveRatio(true);
			imageCard.setFitHeight(100);

			addStage(imageCard);

			imagesPlayer4.add(imageCard);
		}

		for (Cards ca : m.getpackageDog()) {
			Image card = new Image("file:./img/79.jpg");
			ImageView imageCard = new ImageView(card);

			imageCard.setX(350);
			imageCard.setY(350);
			imageCard.setPreserveRatio(true);
			imageCard.setFitHeight(100);
			addStage(imageCard);

			imagesDog.add(imageCard);

			Image cardFront = new Image("file:./img/" + ca.getNum() + ".jpg");
			ImageView imageCardFront = new ImageView(cardFront);

			frontCardsDog.add(imageCardFront);
			imageCardFront.setY(700);
			imageCardFront.setX(x);
			x += 33;
			imageCardFront.setPreserveRatio(true);
			imageCardFront.setFitHeight(100);
			imageCardFront.setVisible(false);
			addStage(imageCardFront);
		}

	}

/**
 * Permet d'afficher les boutons pour l'enchere
 */
	public void showChoices() {
		buttonTake.setVisible(true);
		buttonGuard.setVisible(true);
		buttonWithout.setVisible(true);
		buttonAgainst.setVisible(true);
	}
	
/**
 * Permet de realiser la distribution 3 par 3	
 */
	public void distribution(){
		buttonPlay.setVisible(false);
		int x1 = -250;
		int y1 = 350;

		int x2 = 600;
		int y2 = -50;

		int x3 = -50;
		int y3 = -500;

		int x4 = -500;
		int y4 = -50;

		int xDog = -105;
		int yDog = 150;

		int index = 0;
		int indexDog = 0;
		
		ImageView carte1, carte2, carte3, carte4, carteDog;
		ImageView carteFront;

		while (index != 18) { // tant que l'index est different de 18, on realise les TranslateTransitions suivantes
			
			// transition pour les cartes des 4 paquets de joueurs puis on passe à l'index suivant à la suite   
			carteFront = frontCardsPlayer1.get(index);
			carte1 = imagesPlayer1.get(index);
			carteFront.setVisible(false);
			
			TranslateTransition a = new TranslateTransition(Duration.millis(600), carte1);
			a.setToX(x1);
			a.setToY(y1);
			a.setAutoReverse(true);
			a.setCycleCount(1);
			x1 += 35;

			carte2 = imagesPlayer2.get(index);

			TranslateTransition b = new TranslateTransition(Duration.millis(600), carte2);
			b.setToX(x2);
			b.setToY(y2);
			b.setAutoReverse(true);
			b.setCycleCount(1);
			y2 += 10;

			carte3 = imagesPlayer3.get(index);

			TranslateTransition c = new TranslateTransition(Duration.millis(600), carte3);
			c.setToX(x3);
			c.setToY(y3);
			c.setAutoReverse(true);
			c.setCycleCount(1);
			x3 += 10;

			carte4 = imagesPlayer4.get(index);

			TranslateTransition d = new TranslateTransition(Duration.millis(600), carte4);
			d.setToX(x4);
			d.setToY(y4);
			d.setAutoReverse(true);
			d.setCycleCount(1);
			y4 += 10;

			SequentialTransition distri1 = new SequentialTransition(a, b, c, d);

			index++;

			/*-----------------------------------------------------------------------------*/

			carteFront = frontCardsPlayer1.get(index);
			carte1 = imagesPlayer1.get(index);
			carteFront.setVisible(false);

			TranslateTransition e = new TranslateTransition(Duration.millis(600), carte1);
			e.setToX(x1);
			e.setToY(y1);
			e.setAutoReverse(true);
			e.setCycleCount(1);
			x1 += 35;

			carte2 = imagesPlayer2.get(index);

			TranslateTransition f = new TranslateTransition(Duration.millis(600), carte2);
			f.setToX(x2);
			f.setToY(y2);
			f.setAutoReverse(true);
			f.setCycleCount(1);
			y2 += 10;

			carte3 = imagesPlayer3.get(index);

			TranslateTransition g = new TranslateTransition(Duration.millis(600), carte3);
			g.setToX(x3);
			g.setToY(y3);
			g.setAutoReverse(true);
			g.setCycleCount(1);
			x3 += 10;

			carte4 = imagesPlayer4.get(index);

			TranslateTransition h = new TranslateTransition(Duration.millis(600), carte4);
			h.setToX(x4);
			h.setToY(y4);
			h.setAutoReverse(true);
			h.setCycleCount(1);
			y4 += 10;

			SequentialTransition distri2 = new SequentialTransition(e, f, g, h);

			index++;

			/*-----------------------------------------------------------------------------*/

			carteFront = frontCardsPlayer1.get(index);
			carte1 = imagesPlayer1.get(index);
			carteDog = imagesDog.get(indexDog);
			carteFront.setVisible(false);

			TranslateTransition i = new TranslateTransition(Duration.millis(600), carte1);
			i.setToX(x1);
			i.setToY(y1);
			i.setAutoReverse(true);
			i.setCycleCount(1);
			x1 += 35;

			carte2 = imagesPlayer2.get(index);

			TranslateTransition j = new TranslateTransition(Duration.millis(600), carte2);
			j.setToX(x2);
			j.setToY(y2);
			j.setAutoReverse(true);
			j.setCycleCount(1);
			y2 += 10;

			carte3 = imagesPlayer3.get(index);

			TranslateTransition k = new TranslateTransition(Duration.millis(600), carte3);
			k.setToX(x3);
			k.setToY(y3);
			k.setAutoReverse(true);
			k.setCycleCount(1);
			x3 += 10;

			carte4 = imagesPlayer4.get(index);

			TranslateTransition l = new TranslateTransition(Duration.millis(600), carte4);
			l.setToX(x4);
			l.setToY(y4);
			l.setAutoReverse(true);
			l.setCycleCount(1);
			y4 += 10;

			TranslateTransition tt = new TranslateTransition(Duration.seconds(1), carteDog);
			tt.setToX(xDog);
			tt.setToY(yDog);
			xDog += 70;
			tt.setAutoReverse(true);

			SequentialTransition distri3 = new SequentialTransition(i, j, k, l);

			index++;
			indexDog++;

			/*-----------------------------------------------------------------------------*/

			carteFront = frontCardsPlayer1.get(index);
			carte1 = imagesPlayer1.get(index);
			carteFront.setVisible(false);

			TranslateTransition m = new TranslateTransition(Duration.millis(600), carte1);
			m.setToX(x1);
			m.setToY(y1);
			m.setAutoReverse(true);
			m.setCycleCount(1);
			x1 += 35;

			carte2 = imagesPlayer2.get(index);

			TranslateTransition n = new TranslateTransition(Duration.millis(600), carte2);
			n.setToX(x2);
			n.setToY(y2);
			n.setAutoReverse(true);
			n.setCycleCount(1);
			y2 += 10;

			carte3 = imagesPlayer3.get(index);

			TranslateTransition o = new TranslateTransition(Duration.millis(600), carte3);
			o.setToX(x3);
			o.setToY(y3);
			o.setAutoReverse(true);
			o.setCycleCount(1);
			x3 += 10;

			carte4 = imagesPlayer4.get(index);

			TranslateTransition p = new TranslateTransition(Duration.millis(600), carte4);
			p.setToX(x4);
			p.setToY(y4);
			p.setAutoReverse(true);
			p.setCycleCount(1);
			y4 += 10;

			SequentialTransition distri4 = new SequentialTransition(m, n, o, p);

			index++;

			/*-----------------------------------------------------------------------------*/

			carteFront = frontCardsPlayer1.get(index);
			carte1 = imagesPlayer1.get(index);
			carteDog = imagesDog.get(indexDog);
			carteFront.setVisible(false);

			TranslateTransition q = new TranslateTransition(Duration.millis(600), carte1);
			q.setToX(x1);
			q.setToY(y1);
			q.setAutoReverse(true);
			q.setCycleCount(1);
			x1 += 35;

			carte2 = imagesPlayer2.get(index);

			TranslateTransition r = new TranslateTransition(Duration.millis(600), carte2);
			r.setToX(x2);
			r.setToY(y2);
			r.setAutoReverse(true);
			r.setCycleCount(1);
			y2 += 10;

			carte3 = imagesPlayer3.get(index);

			TranslateTransition s = new TranslateTransition(Duration.millis(600), carte3);
			s.setToX(x3);
			s.setToY(y3);
			s.setAutoReverse(true);
			s.setCycleCount(1);
			x3 += 10;

			carte4 = imagesPlayer4.get(index);

			TranslateTransition t = new TranslateTransition(Duration.millis(600), carte4);
			t.setToX(x4);
			t.setToY(y4);
			t.setAutoReverse(true);
			t.setCycleCount(1);
			y4 += 10;

			TranslateTransition ttt = new TranslateTransition(Duration.seconds(1), carteDog);
			ttt.setToX(xDog);
			ttt.setToY(yDog);
			xDog += 70;
			ttt.setAutoReverse(true);

			SequentialTransition distri5 = new SequentialTransition(q, r, s, t);

			index++;
			indexDog++;

			/*-----------------------------------------------------------------------------*/

			carteFront = frontCardsPlayer1.get(index);
			carte1 = imagesPlayer1.get(index);
			carteFront.setVisible(false);

			TranslateTransition u = new TranslateTransition(Duration.millis(600), carte1);
			u.setToX(x1);
			u.setToY(y1);
			u.setAutoReverse(true);
			u.setCycleCount(1);
			x1 += 35;

			carte2 = imagesPlayer2.get(index);

			TranslateTransition v = new TranslateTransition(Duration.millis(600), carte2);
			v.setToX(x2);
			v.setToY(y2);
			v.setAutoReverse(true);
			v.setCycleCount(1);
			y2 += 10;

			carte3 = imagesPlayer3.get(index);

			TranslateTransition w = new TranslateTransition(Duration.millis(600), carte3);
			w.setToX(x3);
			w.setToY(y3);
			w.setAutoReverse(true);
			w.setCycleCount(1);
			y3 += 10;

			carte4 = imagesPlayer4.get(index);

			TranslateTransition x = new TranslateTransition(Duration.millis(600), carte4);
			x.setToX(x4);
			x.setToY(y4);
			x.setAutoReverse(true);
			x.setCycleCount(1);
			y4 += 10;

			SequentialTransition distri6 = new SequentialTransition(u, v, w, x);

			index++;

			SequentialTransition oui = new SequentialTransition(distri1, distri2, tt, distri3, distri4, ttt,
					distri5, distri6);
			oui.play();

		}

		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(17), ae -> turnCards()));
		timeline.play(); // au bout de 17 secondes , on retourne les cartes du joueur
		Timeline choices = new Timeline(new KeyFrame(Duration.seconds(19), ae -> showChoices()));
		choices.play(); // au bout de 19 secondes, on affiche les 4 boutons pour choisir l'enchere
		
	}
	
	
/**
 * Gestion des evenements liés aux boutons	
 */
	public void event() {
		setVisible();
		// lorsqu'on appuie sur le bouton play
		buttonPlay.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				c.action(Event.PLAY); // on appelle l'evenement play du controller qui appelle cardsPlayers du model (distribution)
				distribution(); 
				// Tant que le petit sec est détecté dans un des paquet, on relance la distribution
				while(m.petitSec(m.getpackagePlayer1()) == true || m.petitSec(m.getpackagePlayer2()) == true || m.petitSec(m.getpackagePlayer3()) == true
						|| m.petitSec(m.getpackagePlayer4()) == true){
					distribution();
				}
			}
		});
		// lorsqu'on appuie sur le bouton prise
		buttonTake.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setVisible();
				takeOrGuard(); // on appelle la methode qui permet de realiser les encheres
			}
		});
		// lorsqu'on appuie sur le bouton Garde
		buttonGuard.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setVisible();
				takeOrGuard();// on appelle la methode qui permet de realiser les encheres
			}
		});
		// lorsqu'on appuie sur le bouton Contre le chien
		buttonAgainst.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setVisible(); // appel de la méthode qui cachent les boutons
						
			}
		});
		// lorsqu'on appuie sur le bouton Contre le chien -> meme chose
		buttonWithout.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				setVisible();
							}
		});

	}
	
/**
 *  Rend invisible les boutons	
 */
	public void setVisible(){
		buttonTake.setVisible(false);
		buttonGuard.setVisible(false);
		buttonWithout.setVisible(false);
		buttonAgainst.setVisible(false);
	}
	
/**
 * Permet de glisser et déposer les cartes du joueur dans le chien
 */
	public void dragAndDrop(){
		final int[] distance = {0,0};
		
		cards.getChildren().forEach(node -> node.setOnMousePressed(new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent mouseEvent) {
	            // record a delta distance for the drag and drop operation.
	            distance[0] = (int) (node.getLayoutX() - mouseEvent.getSceneX());
	            distance[1] = (int) (node.getLayoutY() - mouseEvent.getSceneY());
	            node.setCursor(Cursor.MOVE);
	            node.toFront();
	        }
	    }));

	    cards.getChildren().forEach(node -> node.setOnMouseDragged(new EventHandler<MouseEvent>() {
	        @Override public void handle(MouseEvent mouseEvent) {
	            node.setLayoutX(mouseEvent.getSceneX() + distance[0]);
	            node.setLayoutY(mouseEvent.getSceneY() + distance[1]);
	            node.toFront();
	        }
	    }));
	}
	
	
	
/**
 *  Texte qui s'affiche lors des evenements boutons	
 */
	public void setTexte(){
		Text t = new Text(45, 450, "Que le jeu commence !");
		t.setFont(new Font(80));
		t.setWrappingWidth(900);
		t.setTextAlignment(TextAlignment.JUSTIFY);
		addStage(t);
	}

	/**
	 * Bouton avec ses positions
	 * @param button Un bouton
	 * @param x Position x du bouton
	 * @param y Position y du bouton
	 * @param w Largeur du bouton
	 * @param h Hauteur du bouton
	 */
	public void buttonView(Button button, int x, int y, int w, int h) {
		button.setLayoutX(x);
		button.setLayoutY(y);
		button.setMinWidth(w);
		button.setMinHeight(h);
		button.setVisible(true);
		addStage(button);
	}
	
/**
 * Initialisation de la fenetre avec une taille, un nom...
 */
	public void initStage() {
		stage.setWidth(widthWindow);
		stage.setHeight(heightWindow);
		stage.setResizable(false);
		stage.setTitle("Tarot ! FRISON - MACAIRE S3C");
		stage.setScene(scene);
		stage.sizeToScene();
		stage.show();
	}
	
	/**
	 * Permet d'ajouter un element dans le groupe cards
	 * @param L'objet à ajouter dans le groupe
	 */
	public void addStage(Node o) {
		cards.getChildren().add(o);
	}

	/**
	 * Update
	 */
	@Override
	public void update(Observable arg0, Object arg1) {
	}
}
