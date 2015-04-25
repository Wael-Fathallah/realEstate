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

package Handler;

import java.util.Vector;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import Entity.*;

/**
 *
 * @author FATHALLAH Wael
 */
public class Stat1Handler extends DefaultHandler {
  
    private Vector statss;
    String v5Tag = "close";
    String v4Tag = "close";
    String v3Tag = "close";
    String v2Tag = "close";
    String v1Tag = "close";
    
    public Stat1Handler() {
        statss = new Vector();
    }

    public Stat1[] getStat1() {
        Stat1[] statsss = new Stat1[statss.size()];
        statss.copyInto(statsss);
        return statsss;
    }
    // VARIABLES TO MAINTAIN THE PARSER'S STATE DURING PROCESSING
    private Stat1 currentstat;

    // XML EVENT PROCESSING METHODS (DEFINED BY DefaultHandler)
    // startElement is the opening part of the tag "<tagname...>"
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if (qName.equals("Vote")) {
            currentstat = new Stat1();
            
            currentstat.setV5(attributes.getValue("v5"));
            currentstat.setV4(attributes.getValue("v4"));
            currentstat.setV3(attributes.getValue("v3"));
            currentstat.setV2(attributes.getValue("v2"));
            currentstat.setV1(attributes.getValue("v1"));
         
        } else if (qName.equals("v5")) {
            v5Tag = "open";
         
        } else if (qName.equals("v4")) {
            v4Tag = "open";
         
        } else if (qName.equals("v3")) {
            v3Tag = "open";
         
        } else if (qName.equals("v2")) {
            v2Tag = "open";
        } 
         else if (qName.equals("v1")) {
            v1Tag = "open";
        } 
       
    }

    public void endElement(String uri, String localName, String qName) throws SAXException {

        if (qName.equals("Vote")) {
            // we are no longer processing a <reg.../> tag
            statss.addElement(currentstat);
            currentstat = null;
        } else if (qName.equals("v5")) {
            v5Tag = "close";            
        } else if (qName.equals("v4")) {
            v4Tag = "close";            
        } else if (qName.equals("v3")) {
            v3Tag = "close";            
        } else if (qName.equals("v2")) {
            v2Tag = "close";            
        } else if (qName.equals("v1")) {
            v1Tag = "close";            
        }
    }
    // "characters" are the text between tags

    public void characters(char[] ch, int start, int length) throws SAXException {
        // we're only interested in this inside a <phone.../> tag
        if (currentstat != null) {
            // don't forget to trim excess spaces from the ends of the string
            if (v5Tag.equals("open")) {
                currentstat.setV5(new String(ch, start, length).trim());
            } else
                if (v4Tag.equals("open")) {
                currentstat.setV4(new String(ch, start, length).trim());
            } else
                    if (v3Tag.equals("open")) {
                currentstat.setV3(new String(ch, start, length).trim());
            } else
                if (v2Tag.equals("open")) {
                currentstat.setV2(new String(ch, start, length).trim());
            } else
                    if (v1Tag.equals("open")) {
                currentstat.setV1(new String(ch, start, length).trim());
            } 
        }
    }
    
}
