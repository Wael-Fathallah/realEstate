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

import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Graphics;

/**
 *
 * @author FATHALLAH Wael
 */
public class PieChartCanvas extends Canvas {
    int[] data;
    
    int colors[] = { 0xFF0000, 0xFFa500, 0xFFFF00, 0xadff2f, 0x32cd32 };
    
    public PieChartCanvas(int[] data) {
        this.data = data;
    }
    
    public void paint(Graphics g) {
        int width = this.getWidth();
        int height = this.getHeight();
        
        g.setColor(255, 255, 255);
        g.fillRect(0, 0, width, height);
        String starts[] = { "5 Stars", "4 Stars", "3 Stars", "2 Stars", "1 Star" };
        int x = 10;
        for (int i = 0; i < 5; i++) {
            g.setColor(colors[i]);
            
            g.fillRect(10, x, 15, 15);
            g.setColor(0x000000);
            g.drawString(starts[i], 30, x, 0);
            x+=30;
            
        }
       
        int startAngle = 0;
        
        for (int i = 0; i < 5; i++) {
            g.setColor(colors[i]);
            System.out.println(startAngle);
            g.fillArc(width-160, height-160, 150, 150, startAngle, data[i]);
            startAngle += data[i];
        }
    }
    
}