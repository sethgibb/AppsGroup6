package application;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import application.Model;
import java.io.IOException;
import java.time.LocalDate;


//Create an anchor pane that can store additional data.
 
public class AnchorPaneNode extends AnchorPane {
	
	Model mod = new Model();
	Label gameTime;

    // Date associated with this pane
    private LocalDate date;

    /**
     * Create a anchor pane node. Date is not assigned in the constructor.
     * @param children children of the anchor pane
     * @throws IOException 
     */
    public AnchorPaneNode(Node... children) throws IOException {
        super(children);
        // Add action handler for mouse clicked
        this.setOnMouseClicked(e -> {
        	mod.setDate(date, this);
        });
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}
