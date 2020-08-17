 package com.common.pdf;
 
 import java.io.FileNotFoundException;
 import java.io.IOException;
 import java.io.InputStream;
 import java.util.Properties;
 import java.util.Set;
 
 public class PDFConfig
 {
   private static final String PATH = "/PDF.properties";
   private static Properties properties;
 
   public PDFConfig()
   {
     try
     {
       InputStream in = super.getClass().getResourceAsStream("/PDF.properties");
 
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
 
   public static String getValueByParam(String value) {
     new PDFConfig();
     if (properties.containsKey(value)) {
       return properties.getProperty(value);
     }
     return null;
   }
 
   public static Set<String> getStringPropertyNames()
   {
     new PDFConfig();
     Set s = properties.stringPropertyNames();
     return s;
   }
 
   public static void main(String[] args)
   {
   }
 }