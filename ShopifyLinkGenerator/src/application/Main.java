package application;
	
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
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
	
	String siteURL;// = "https://kith.com/collections/mens-footwear-sneakers/products/new-balance-ml1500v1-navy-grey";
	String atcLink;
	String size;
	String prodID;
	String checkout;
	
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
	
	  public void getProdID(String sizeCheck) {
	        try {
	            Document document = Jsoup.connect((String)this.siteURL).get();
	            Elements elements = document.select("select.no-js > option");
	            this.prodID = elements.toString();
	            this.prodID = this.prodID.substring(this.prodID.indexOf(sizeCheck) - 16, this.prodID.indexOf(sizeCheck) - 2);
	        }
	        catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    public void initialize(URL location, ResourceBundle resources) {
	        this.createButton.setOnMouseClicked(e -> {
	            this.size = this.sizeTextField.getText();
	            this.size.toUpperCase();
	            this.size = " " + this.size + " ";
	            this.siteURL = this.productTextField.getText();
	            int start = this.siteURL.indexOf(".");
	            this.getProdID(this.size);
	            this.atcLink = String.valueOf(this.siteURL.substring(0, start += 4)) + "/cart/" + this.prodID + ":1";
	            this.atcTextField.setText(this.atcLink);
	        });
	        this.openButton.setOnMouseClicked(e -> {
	            try {
	                Desktop d = Desktop.getDesktop();
	                if (this.atcLink == null) {
	                    this.atcLink = "https://google.com";
	                }
	                d.browse(new URI(this.atcLink));
	            }
	            catch (IOException e1) {
	                e1.printStackTrace();
	            }
	            catch (URISyntaxException e1) {
	                e1.printStackTrace();
	            }
	        });
	    }
	}