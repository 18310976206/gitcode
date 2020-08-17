package com.common.pdf.util;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

public class UtilConfiguration
{
  private static final String PATH = "/XML.properties";
  private static Properties properties;

  UtilConfiguration()
  {
    try
    {
      InputStream in = super.getClass().getResourceAsStream("/XML.properties");

      properties = new Properties();
      properties.load(in);
      in.close();
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace(); }
  }

  UtilConfiguration(String propertiesName) {
    try {
      InputStream in = super.getClass().getResourceAsStream("/" + propertiesName);

      properties = new Properties();
      properties.load(in);
      in.close();
    }
    catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static String getValueByParam(String value, String propertiesName)
  {
    if (propertiesName.equals(null)) {
      new UtilConfiguration();
    }
    else {
      new UtilConfiguration(propertiesName);
    }
    if (properties.containsKey(value)) {
      return properties.getProperty(value);
    }
    return null;
  }

  public static Set<String> getStringPropertyNames(String propertiesName)
  {
    if (propertiesName.equals(null)) {
      new UtilConfiguration();
    }
    else {
      new UtilConfiguration(propertiesName);
    }

    Set s = properties.stringPropertyNames();
    return s;
  }

  public static void main(String[] args)
  {
  }
}