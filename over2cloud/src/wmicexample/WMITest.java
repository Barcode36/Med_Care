package wmicexample;


import javax.swing.JOptionPane;
import java.lang.Exception;


public class WMITest 
{
    public static void main(String [] args) {
        try {
            jWMI WMIV=new jWMI();
          //  String model = WMIV.getWMIValue("Select Model from Win32_ComputerSystem","Model");
            String model= WMIV.getWMIValue("Select Description, Manufacturer from Win32_PnPEntity","Description, Manufacturer");
            JOptionPane.showMessageDialog(null,"This is the PC model __" + model);
        }
        catch (Exception e) {
            System.err.println("Caught exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}