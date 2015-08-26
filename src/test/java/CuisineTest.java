import org.junit.*;
import static org.junit.Assert.*;
import org.junit.Rule;

public class CuisineTest {

     @Rule
     public DatabaseRule database = new DatabaseRule();

     @Test
     public void all_emptyAtFirst() {
       assertEquals(Cuisine.all().size(), 0);
     }


 }
