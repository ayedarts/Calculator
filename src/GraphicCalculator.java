import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

@SuppressWarnings("restriction")
public class GraphicCalculator extends Application {
	Calculator calc = new Calculator();
	Label resultat = new Label();

	@Override
	public void start(Stage stage) {
		stage.setTitle("Calculatrice Graphique");
		stage.setHeight(615);
		stage.setWidth(422);
		stage.show();
		resultat.setFont(new Font("Arial", 50));
		HBox affichage = new HBox();
		affichage.getChildren().add(resultat);

		HBox ligne1 = new HBox();
		ligne1.getChildren().add(b('7'));
		ligne1.getChildren().add(b('8'));
		ligne1.getChildren().add(b('9'));
		ligne1.getChildren().add(b('+'));

		HBox ligne2 = new HBox();
		ligne2.getChildren().add(b('4'));
		ligne2.getChildren().add(b('5'));
		ligne2.getChildren().add(b('6'));
		ligne2.getChildren().add(b('-'));

		HBox ligne3 = new HBox();
		ligne3.getChildren().add(b('1'));
		ligne3.getChildren().add(b('2'));
		ligne3.getChildren().add(b('3'));
		ligne3.getChildren().add(b('*'));

		HBox ligne4 = new HBox();
		ligne4.getChildren().add(b('0'));
		ligne4.getChildren().add(b('.'));
		ligne4.getChildren().add(b('C'));
		ligne4.getChildren().add(b('/'));

		HBox ligne5 = new HBox();
		ligne5.getChildren().add(b('('));
		ligne5.getChildren().add(b(')'));
		ligne5.getChildren().add(b('$'));
		ligne5.getChildren().add(b('='));

		VBox fenetre = new VBox();
		fenetre.getChildren().add(affichage);
		fenetre.getChildren().add(ligne1);
		fenetre.getChildren().add(ligne2);
		fenetre.getChildren().add(ligne3);
		fenetre.getChildren().add(ligne4);
		fenetre.getChildren().add(ligne5);

		Scene scene = new Scene(fenetre);
		scene.setOnKeyTyped(e -> {
			try {
				handlekey(e);
			} catch (ParsingException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		});
		stage.setScene(scene);
	}

	Button b(char c) {
		Button bouton = new Button("" + c);
		bouton.setMinSize(100, 100);
		bouton.setMaxSize(100, 100);
		bouton.setText("" + c);
		bouton.setOnAction(value -> {
			try {
				update(c);
			} catch (ParsingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		});
		bouton.setFont(new Font("Helvetica", 40));
		return (bouton);
	}

	void update(char c) throws ParsingException {
		calc.read(c);
		resultat.setText(calc.getResult().toString());
	}

	void handlekey(javafx.scene.input.KeyEvent e) throws ParsingException {
		char c = e.getCharacter().charAt(0);
		update(c);
	}

	public static void main(String[] args) {
		launch(args);
	}
}
