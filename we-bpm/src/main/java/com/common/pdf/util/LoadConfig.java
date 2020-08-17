package com.common.pdf.util;

import java.util.ArrayList;
import java.util.Set;

import com.common.pdf.mode.XmlMode;

public class LoadConfig
{
  public static ArrayList<XmlMode> GetXmlMode(String propertiesName)
  {
    ArrayList<XmlMode> alx = new ArrayList<XmlMode>();
    Set<String> configNames = UtilConfiguration.getStringPropertyNames(propertiesName);
    for (String key : configNames) {
      XmlMode xm = new XmlMode();
      String configname = UtilConfiguration.getValueByParam(key, propertiesName);
      xm.setXmlName(configname);
      alx.add(xm);
    }

    return alx;
  }
}