package application;
	
import java.awt.Desktop;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;


public class Main extends Application implements Initializable{
	
	@FXML
	private TextField productTextField;
	
	@FXML
	private TextField sizeTextField;
	
	@FXML
	private TextField atcTextField; 
	
	@FXML
	private Button createButton;
	
	@FXML
	private Button openButton;	
	
	String url;// = "https://kith.com/collections/mens-footwear-sneakers/products/new-balance-ml1500v1-navy-grey";
	String atcURL;
	String size;
	String prodID;
	String website;
	
	@Override
	public void start(Stage primaryStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("LinkGenGUI.fxml"));
			Scene scene = new Scene(root);
			primaryStage.setTitle("Shopify Link Generator");
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
			//getProduct();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void getProduct() throws IOException {
		Document doc = Jsoup.connect(url).get();
		System.out.println(website);
		Elements elements = doc.select("select.no-js > option");
		prodID = elements.toString();
		System.out.print(elements);
		prodID = prodID.substring(prodID.indexOf(size)-16,prodID.indexOf(size)-2);
		System.out.println(prodID);
	}
	
	public void setATC() {
		atcURL = url.substring(0,url.indexOf(".")+4);
		atcURL = atcURL + "/cart/" + prodID + ":1";
		atcTextField.setText(atcURL);
	}
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		createButton.setOnMouseClicked(e -> {
			url = productTextField.getText();
			size = sizeTextField.getText();
			size = size.toUpperCase();
			size = " " + size + " ";
			System.out.println(size);
			try {
				getProduct();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			setATC();
			System.out.println(atcURL);
		});
		
		openButton.setOnMouseClicked(e -> {
			try {
				Desktop.getDesktop().browse(new URL(atcURL).toURI());
			}catch(Exception e1) {
				e1.printStackTrace();
			}
		});
		
	}
}
