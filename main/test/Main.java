import com.bookstore.ui.menu.UIManager;
import com.bookstore.util.core.Application;
import com.bookstore.util.core.ApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = Application.run("com.bookstore");
        context.getObject(UIManager.class);
    }
}
