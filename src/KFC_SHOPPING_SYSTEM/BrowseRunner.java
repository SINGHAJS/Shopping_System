package KFC_SHOPPING_SYSTEM;

/**
 *
 * @author Ajit Singh ID: 19070642
 * @author Rohit Singh ID: 17981754
 *
 */
public class BrowseRunner {

    public static void main(String[] args) {
        
        BrowseView view = new BrowseView();
        
        BrowseModel model = new BrowseModel();
        
        BrowseController controller = new BrowseController(model, view);

        model.addObserver(view);
    }

}
