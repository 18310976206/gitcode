package com.common.pdf;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.xhtmlrenderer.pdf.ITextFontResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.common.pdf.mode.XmlMode;
import com.common.pdf.util.LoadConfig;
import com.common.pdf.util.UtilConfiguration;
import com.common.pdf.xml.XmlMsg;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

public class CreatPdfUtils
{
  private static Map<String, String> creatMap(ArrayList<XmlMode> alx, String MsgConfigName)
  {
    Map map = new HashMap();
    String TrsDateName = UtilConfiguration.getValueByParam("TrsDate", MsgConfigName);
    String TrsTimeName = UtilConfiguration.getValueByParam("TrsTime", MsgConfigName);
    String AmountName = UtilConfiguration.getValueByParam("Amount", MsgConfigName);
    String BigNumName = UtilConfiguration.getValueByParam("BigNum", MsgConfigName);
    String TrsDateV1Name = UtilConfiguration.getValueByParam("TrsDateV1", MsgConfigName);
    String PrintCountName = UtilConfiguration.getValueByParam("PrintCount", MsgConfigName);
    String TrsDateValue = null;
    String TrsTimeValue = null;
    String BigNumValue = null;
    String PrintCountValue = "1";

    for (XmlMode xm : alx)
    {
      if (TrsDateName.equals(xm.getXmlName())) {
        TrsDateValue = xm.getXmlValue();
      } else if (TrsTimeName.equals(xm.getXmlName())) {
        TrsTimeValue = xm.getXmlValue();
      }
      else if (AmountName.equals(xm.getXmlName())) {
        map.put(xm.getXmlName(), xm.getXmlValue());
       if( !StringUtils.isBlank(xm.getXmlValue())){
    	   BigNumValue = digitUppercase(Double.parseDouble(xm.getXmlValue()));
        }
       
      }
      else {
        if (BigNumName.equals(xm.getXmlName())) {
          continue;
        }

        if (PrintCountName.equals(xm.getXmlName())) {
          map.put(xm.getXmlName(), PrintCountValue);
        }
        else if (TrsDateV1Name.equals(xm.getXmlName()))
          map.put(xm.getXmlName(), getNowDate("yyyy-MM-dd HH:mm:ss"));
        else {
          map.put(xm.getXmlName(), xm.getXmlValue());
        }

      }

    }

    map.put(BigNumName, BigNumValue);
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
    try {
        if( !StringUtils.isBlank(TrsDateValue)){
            TrsDateValue = sdf1.format(parseDate(TrsDateValue));
        }
    }
    catch (ParseException e) {
      e.printStackTrace();
    }
    map.put(TrsDateName, TrsDateValue + " " + TrsTimeValue);

    map.put("DebitMark", "0");

    return map;
  }

  public static Date parseDate(String s)
    throws ParseException
  {
    SimpleDateFormat sdf1 = new SimpleDateFormat("yyyyMMdd");
    return sdf1.parse(s);
  }

  public static String getNowDate(String dateType)
  {
    Date date = new Date();
    SimpleDateFormat sdf1 = new SimpleDateFormat(dateType);
    return sdf1.format(date);
  }

  public static String digitUppercase(double n)
  {
    String[] fraction = { "角", "分" };
    String[] digit = { "零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖" };
    String[][] unit = { { "元", "万", "亿" }, { "", "拾", "佰", "仟" } };

    String head = (n < 0.0D) ? "负" : "";
    n = Math.abs(n);

    String s = "";
    for (int i = 0; i < fraction.length; ++i) {
      s = s + new StringBuilder(String.valueOf(digit[(int)(Math.floor(n * 10.0D * Math.pow(10.0D, i)) % 10.0D)])).append(fraction[i]).toString().replaceAll("(零.)+", "");
    }
    if (s.length() < 1) {
      s = "整";
    }
    int integerPart = (int)Math.floor(n);

    for (int i = 0; (i < unit[0].length) && (integerPart > 0); ++i) {
      String p = "";
      for (int j = 0; (j < unit[1].length) && (n > 0.0D); ++j) {
        p = digit[(integerPart % 10)] + unit[1][j] + p;
        integerPart /= 10;
      }
      s = p.replaceAll("(零.)*零$", "").replaceAll("^$", "零") + unit[0][i] + s;
    }
    return head + s.replaceAll("(零.)*零元", "元").replaceFirst("(零.)+", "").replaceAll("(零.)+", "零").replaceAll("^整$", 
      "零元整");
  }

  public static ArrayList<Object> creatPdF(byte[] msg, String MsgPropertiesName, String coding, String PDFPropertiesName, String pdfName)
  {
    String Name = null;

    ArrayList alx = LoadConfig.GetXmlMode(MsgPropertiesName);

    alx = XmlMsg.GetXMLValues(msg, alx, coding);

    String pdfPath = System.getProperty("user.dir") + "/";
    if (pdfName.equals("")) {
      Name = getNowDate("yyyyMMddHHmmss") + ".pdf";
      System.out.println("pdfname为默认规则");
    } else {
      Name = pdfName;
      System.out.println("pdfname可配置规则");
    }

    ArrayList file = creatPdfReceipt(alx, pdfPath, MsgPropertiesName, Name);

    return file;
  }

  public static ArrayList<Object> creatPdfReceipt(ArrayList<XmlMode> alx, String PDFPath, String MsgPropertiesName, String pdfName)
  {
    ArrayList alFile = new ArrayList();

    ByteArrayOutputStream os = null;

    Configuration cfg = new Configuration();
    try
    {
      System.out.println("读取模板存放路径[" + PDFPath + "]");

      cfg.setDirectoryForTemplateLoading(new File(PDFPath + "src/main/java/com/common/pdf/pdfmodel"));
      cfg.setDefaultEncoding("UTF-8");

      Template temp = cfg.getTemplate("newslinkmail2.ftl");
      Writer writer = new StringWriter();

      temp.process(creatMap(alx, MsgPropertiesName), writer);

      String EmailText = writer.toString();

      ITextRenderer renderer = new ITextRenderer();

      ITextFontResolver fontResolver = renderer.getFontResolver();

      System.out.println("载入字体[" + PDFPath + "src/main/java/com/common/pdf/pdfmodel/" + "simsun.ttc]");

      fontResolver.addFont(PDFPath + "src/main/java/com/common/pdf/pdfmodel/" + "simsun.ttc", "Identity-H", false);
      renderer.setDocumentFromString(EmailText);
      System.out.println("载入印章[" + PDFPath + "pictures/]");
      renderer.getSharedContext().setBaseURL("file:///" + PDFPath + "pictures/");

      renderer.layout();
      os = new ByteArrayOutputStream();
      renderer.createPDF(os);

      os.flush();

      alFile.add(pdfName);
      alFile.add(os.toByteArray());
      System.out.println("生成PDF文件[" + pdfName + "]");
    }
    catch (IOException e)
    {
      System.out.println("未找到模板存放路径" + e);
    } catch (TemplateException e) {
      System.out.println("temp.process(addMap(), out)步骤异常" + e);
    } catch (Exception e) {
      System.out.println("生成dpf异常" + e);
    }
    finally {
      try {
        os.close();
      }
      catch (IOException e) {
        System.out.println("关闭流时发生异常" + e);
      }
    }

    return alFile;
  }

  public static void saveFile(String filename, byte[] data)
    throws Exception
  {
    if (data == null)
      return;
    String filepath = System.getProperty("user.dir") + "/" + filename;
    System.out.println("File:" + filepath);
    File file = new File(filepath);
    if (file.exists()) {
      file.delete();
    }
    FileOutputStream fos = new FileOutputStream(file);
    fos.write(data, 0, data.length);
    fos.flush();
    fos.close();
  }
}