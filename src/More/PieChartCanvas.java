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

  int colors[] = { 0xFF9966, 0x3399FF, 0x66FF99, 0xCC33FF, 0x66FF99, 0xFFFF66, 0x009933,0x9933FF, 0xA9E969, 0xFF3300, 0xC675EC, 0x008800, 0x00C400 };

  public PieChartCanvas(int[] data) {
    this.data = data;
  }

  public void paint(Graphics g) {
    int width = this.getWidth();
    int height = this.getHeight();

    g.setColor(255, 255, 255);
    g.fillRect(0, 0, width, height);

    int sum = 0;
    for (int i = 0; i < data.length; i++) {
      sum += data[i];
    }
    int deltaAngle = 360 * 100 / sum / 100;
    int x = 4;
    int y = 4;
    int diameter;

    if (width > height)
      diameter = height - y * 2;
    else
      diameter = width - x * 2;

    int startAngle = 0;

    for (int i = 0; i < data.length; i++) {
      g.setColor(colors[i]);
      g.fillArc(x, y, diameter, diameter, startAngle, deltaAngle * data[i]);
      startAngle += deltaAngle * data[i];
    }
  }
}