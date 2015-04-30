

package More;

import java.io.IOException;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/**
 * A simple splash screen.
 */
public class ScreenSplashForm extends Canvas implements Runnable {

    private Image image;
    private Display mid;
    private Form mxc;
    /**
     * The constructor attempts to load the named image and begins a timeout
     * thread. The splash screen can be dismissed with a key press,
     * a pointer press, or a timeout
     * @param offreMidlet instance of MIDlet
     */
    public ScreenSplashForm(Display m, Form mxc){
        this.mid =m;
        this.mxc = mxc;
        try{
        image = Image.createImage("/icons/icon.png");
        Thread t = new Thread(this);
        t.start();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Paints the image centered on the screen.
     */
    public void paint(Graphics g) {
        int width = getWidth();
        int height = getHeight();

        //set background color to overdraw what ever was previously displayed
        g.setColor(0x000000);
        g.fillRect(0,0, width, height);
        g.drawImage(image, width / 2, height / 2,
                Graphics.HCENTER | Graphics.VCENTER);
    }

    
    
    public void dismiss() {
        mid.setCurrent(mxc);
    }

    /**
     * Default timeout with thread
     */
    public void run() {
        try {
            Thread.sleep(3000);//set for 3 seconds
        }
        catch (InterruptedException e) {
            System.out.println("InterruptedException");
            e.printStackTrace();
        }
       dismiss();
    }

    /**
     * A key release event triggers the dismiss()
     * method to be called.
     */
    public void keyReleased(int keyCode) {
      dismiss();
    }

    /**
     * A pointer release event triggers the dismiss()
     * method to be called.
     */
    public void pointerReleased(int x, int y) {
       dismiss();
    }
}