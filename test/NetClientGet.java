
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

public class NetClientGet {

  public static void main(String[] args) {
         DecimalFormat dc = new DecimalFormat("##.##");
        
        double d= Double.parseDouble(dc.format(19.7528736).replace(",", "."));
        System.out.println(d);
  
  }
}
