/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Run;

import Sprint3.realEstateMidlet;
import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;

/**
 *
 * @author FATHALLAH Wael
 */
public class LoginRun {
    
    public Runnable typeA;
    public Runnable typeB;

    public LoginRun(final String url, final String urlX) {
        typeA = new Runnable() {
            private HttpConnection hc;
            private DataInputStream dis;
            private int ch;
            StringBuffer sb = new StringBuffer();
            private Alert errorAlert;
            public void run() {
                
                try {
                hc = (HttpConnection) Connector.open(url+urlX);
                dis = new DataInputStream(hc.openDataInputStream());
                while ((ch = dis.read()) != -1) {
                    sb.append((char)ch);
                    
                }
                System.out.print(sb.toString().trim());
//                System.out.print(adminTorFIndex);
                if ("OK".equals(sb.toString().trim())) {
//                    display.setCurrent(f2);
                }else{
                    errorAlert = new Alert("Error", sb.toString().trim(), null,AlertType.ERROR);
                    errorAlert.setTimeout(3000);
                    realEstateMidlet cvv =new realEstateMidlet();
                    cvv.getDisplay().setCurrent(errorAlert);
                }
                sb = new StringBuffer();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
                
            }
        };
        typeB = new Runnable() {
            public void run() {
                
                // one can use a non-final typed variable
                // to store, which then<1>
            }
        };
    }

//    public static void main(String args[]) {
//        OnlineResourceAdapter x = new OnlineResourceAdapter();
//        new Thread(x.typeA).start(); // start A
//        new Thread(x.typeB).start(); // start B
//        // <1>can be accessed here.
//    }
//
//    public void getInformationOfTypeA(){
//        // get information of type A
//        // return the data or directly store in OnlineResourceAdapter.
//    }
//
//    public void getInformationOfTypeB(){
//        //get information of type B
//    }

}
