package bc.liaoningu.Utils;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by BC on 2016/10/18 0018.
 */
public class SerMap implements Serializable {
    public Map<String,String> map;
    public  SerMap(){

    }

    public Map<String, String> getMap() {
        return map;
    }

    public void setMap(Map<String,String> map) {
        this.map = map;

    }
}
