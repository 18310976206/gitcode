package com.common.pdf.xml;

import com.common.pdf.mode.XmlMode;
import com.common.pdf.util.UtilConfiguration;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class XmlMsg
{
  private static Log log = LogFactory.getLog(XmlMsg.class);

  public static ArrayList<XmlMode> GetXMLValues(byte[] msg, ArrayList<XmlMode> alx, String coding)
  {
    ArrayList xml = alx;

    Document doc = null;
    try
    {
      String HeadLableName = 
        UtilConfiguration.getValueByParam("head", "XML.properties");
      String DataLableName = 
        UtilConfiguration.getValueByParam("data", "XML.properties");

      doc = DocumentHelper.parseText(new String(msg, coding));

      Element rootElt = doc.getRootElement();

      log.debug("根节点：" + rootElt.getName());
      List elements = rootElt.elements();

      for (Iterator localIterator1 = elements.iterator(); localIterator1.hasNext(); )
      {
        Element itemEle;
        String lname;
        String value;
        XmlMode lab;
        Iterator<Element> localIterator3;
        Element el = (Element)localIterator1.next();

        Element recordEle = el;
        String labname = recordEle.getName();

        if (labname.equals(HeadLableName)) {
          List<Element> Headelements = recordEle.elements();

          log.debug("iters.len:");

          for (Element Headel : Headelements)
          {
            itemEle = Headel;
            lname = itemEle.getName();

            value = itemEle.getText();

            for (localIterator3 = xml.iterator(); localIterator3.hasNext(); ) { lab = (XmlMode)localIterator3.next();
              if (lab.getXmlName().equals(lname)) {
                lab.setXmlValue(value);
              }
            }
          }

        }

        if (!(labname.equals(DataLableName)))
          continue;
        List<Element> Dataelements = recordEle.elements();

        for (Element dateEl : Dataelements)
        {
          itemEle = dateEl;
          lname = itemEle.getName();

          value = itemEle.getText();

          for (localIterator3 = xml.iterator(); localIterator3.hasNext(); ) { lab = (XmlMode)localIterator3.next();
            if (lab.getXmlName().equals(lname)) {
              lab.setXmlValue(value);
            }

          }

        }

      }

    }
    catch (DocumentException e)
    {
      log.error("readStringXml 方法XML异常" + e);
    }
    catch (Exception e) {
      log.error("readStringXml 方法XML异常" + e);
    }

    return xml;
  }
}