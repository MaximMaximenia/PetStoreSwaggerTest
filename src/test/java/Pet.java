import lombok.Data;

import java.util.ArrayList;

@Data
public class Pet {
    String name;
    String status;
    String[] photoUrls;
    Object category;
    int id;

}
