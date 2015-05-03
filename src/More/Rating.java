/*
* Copyright 2015.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*      http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/

package More;

import java.io.DataInputStream;
import java.io.IOException;
import javax.microedition.io.Connector;
import javax.microedition.io.HttpConnection;
import javax.microedition.lcdui.Alert;
import javax.microedition.lcdui.AlertType;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 *
 * @author FATHALLAH Wael
 */
public class Rating extends Canvas {

    private Image image1;
    private Image image2;
    String urlX = "";
    int idOffre;
    String userId;
    public Rating(int idOffre, String userId) {
        this.idOffre=idOffre;
        this.userId=userId;
        try{
                
                image1 = Image.createImage("/icons/staron.png");
                image2 = Image.createImage("/icons/staroff.png");
              
            }
            catch(IOException e){
                e.printStackTrace();
            }
    }
        int width = this.getWidth();
        int height = this.getHeight();
        int x =0;
    public void paint(Graphics g) {
       
        
        g.setColor(255, 255, 255);
        g.fillRect(0, 0, width, height);
        g.drawImage(image2, 15+2, height/2,0);
        g.drawImage(image2, 15+38+4, height/2,0);
        g.drawImage(image2, 15+38+38+6, height/2,0);
        g.drawImage(image2, 15+38+38+38+8, height/2,0);
        g.drawImage(image2, 15+38+38+38+38+10, height/2,0);
        if (x==1)
        {
            g.drawImage(image1, 15+2, height/2,0);
        }else if(x==2){
            g.drawImage(image1, 15+2, height/2,0);
            g.drawImage(image1, 15+38+4, height/2,0);
        }
        else if(x==3){
            g.drawImage(image1, 15+2, height/2,0);
            g.drawImage(image1, 15+38+4, height/2,0);
            g.drawImage(image1, 15+38+38+6, height/2,0);
        }
        else if(x==4){
            g.drawImage(image1, 15+2, height/2,0);
            g.drawImage(image1, 15+38+4, height/2,0);
            g.drawImage(image1, 15+38+38+6, height/2,0);
            g.drawImage(image1, 15+38+38+38+8, height/2,0);
        }
        else if(x==5){
            g.drawImage(image1, 15+2, height/2,0);
            g.drawImage(image1, 15+38+4, height/2,0);
            g.drawImage(image1, 15+38+38+6, height/2,0);
            g.drawImage(image1, 15+38+38+38+8, height/2,0);
            g.drawImage(image1, 15+38+38+38+38+10, height/2,0);}
        System.out.println(width);
        
    }
    protected void keyPressed(int keyCode) {
        
          
            
            int gameAction = getGameAction(keyCode);
            String key=getKeyName(keyCode);
            if(gameAction == RIGHT && x<5){
                x+=1;
                repaint();
            }else if(gameAction == LEFT && x>1){
                x-=1;
                repaint();
            }else if(gameAction == FIRE){
                urlX="Offre/Rating/rating.php?clientID="+userId+"&offreID="+idOffre+"&note="+x;
                System.out.println(userId);
                System.out.println(idOffre);
                new Thread(new Runnable() {
                public void run() {
                    
                    if(setData()){
                        
                    }
                }
            }).start();
            }}
        
                      
    // <editor-fold defaultstate="collapsed" desc=" setData ">
    //Connexion
    HttpConnection hc;
    DataInputStream dis;
    String url = "http://localhost/Pi_MOB_DAO/";
    
    StringBuffer sb = new StringBuffer();
    int ch;
    // </editor-fold>
    boolean setData(){
        try {
            sb = new StringBuffer();
            hc = (HttpConnection) Connector.open(url+urlX);
            dis = new DataInputStream(hc.openDataInputStream());
            while ((ch = dis.read()) != -1) {
                sb.append((char)ch);
                
            }
            System.out.println(sb);
            return true;
        } catch (IOException ex) {
            
            return false;
        }
    }}
    // </editor-fold>
         