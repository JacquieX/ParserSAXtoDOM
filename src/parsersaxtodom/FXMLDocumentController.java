/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parsersaxtodom;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import javafx.scene.control.MenuBar;
import javafx.scene.layout.VBox;

/**
 *
 * @author Shihui Xiong
 */
public class FXMLDocumentController implements Initializable {
    
  
    @FXML
    private TextArea xmlTextArea;
    
    @FXML
    private TextArea parsedTextArea;
    
    @FXML
    private HBox root;
    private File file;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    




 @FXML
   private void uploadClick(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        Stage stage = (Stage) root.getScene().getWindow(); 
        
        fileChooser.getExtensionFilters().add(new ExtensionFilter("Text Files", "*.txt", "*.xml"));
        file = fileChooser.showOpenDialog(stage); 
        
        if (file != null) {
            BufferedReader br = null;
            
            String document = "";
            String line = "";
            
            try {
                br = new BufferedReader(new FileReader(file));
                
                while ((line = br.readLine()) != null ) {
                    document += line + "\n";
                }
                
                xmlTextArea.setText(document);
            } catch (FileNotFoundException ex) {
                System.out.println(ex);
            } catch (IOException ex) {
                System.out.println(ex);
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                }
            }
        }
    }
     
   @FXML
    private void parseClick(ActionEvent event) {

        try {
           Code Code = xmlLoader.load(file);
           StringBuilder codeSb = new StringBuilder();
           CodeToString(Code, codeSb, new int[] {0});
           parsedTextArea.setText(codeSb.toString());
        } catch (Exception ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
    private void CodeToString(Code rootCode, StringBuilder sb, int[] level) {
        if (rootCode == null) {
            return;
        }
        sb.append(rootCode.tag);
        if (rootCode.attrs != null) {
            for (Attri attr : rootCode.attrs) {
                sb.append("<AttributeId : ").append(attr.id).append(" AttributeValue : ").append(attr.value).append(" >");
            }
        }
        
        if (rootCode.content != null && !rootCode.content.equals("\n")) {
            sb.append(" : ").append(rootCode.content);
        }
        sb.append("\n");
        
        level[0]++;
        for (Code Code : rootCode.contains) {
            for (int i = 0; i < level[0]; i++ ) {
                sb.append("\t");
            }
            CodeToString(Code, sb, level);
        }
        level[0]--;
    }

  
    
}
