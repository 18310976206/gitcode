package com;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import com.common.pdf.CreatPdfUtils;
import com.common.pdf.mode.XmlMode;

public class PdfTest
{
  public static void main(String[] args)
  {
    try
    {
      byte[] msg = getContent("D:\\1.txt");
      String pdfName = "";
     List<Object> lsit = CreatPdfUtils.creatPdF(msg, "Msg.properties", "GBK", "PDF.properties", pdfName);
     FileOutputStream fileOutputStream = null;
         fileOutputStream = new FileOutputStream("E:\\工作文件\\项目资料\\activity\\we-bpm\\"+lsit.get(0));
         fileOutputStream.write((byte[])lsit.get(1));
      
      }catch(Exception e){
      //TODOAuto-generatedcatchblock
        e.printStackTrace();
      }
  }

  public static ArrayList<XmlMode> getbp()
  {
    ArrayList alx = new ArrayList();

    XmlMode xm = new XmlMode();
    xm.setXmlName("listtype");
    xm.setXmlValue("回单类型");
    alx.add(xm);

    xm = new XmlMode();
    xm.setXmlName("list_no");
    xm.setXmlValue("回单号码");
    alx.add(xm);

    xm = new XmlMode();
    xm.setXmlName("Acc_Name");
    xm.setXmlValue("付款方户名");
    alx.add(xm);

    xm = new XmlMode();
    xm.setXmlName("Query_Account");
    xm.setXmlValue("付款方账号");
    alx.add(xm);

    xm = new XmlMode();
    xm.setXmlName("Query_Bank");
    xm.setXmlValue("付款方开户银行");
    alx.add(xm);

    xm = new XmlMode();
    xm.setXmlName("Name");
    xm.setXmlValue("收款方户名");
    alx.add(xm);

    xm = new XmlMode();
    xm.setXmlName("Account");
    xm.setXmlValue("收款方帐号");
    alx.add(xm);

    xm = new XmlMode();
    xm.setXmlName("Bank");
    xm.setXmlValue("收款方开户银行");
    alx.add(xm);

    xm = new XmlMode();
    xm.setXmlName("Amount");
    xm.setXmlValue("小写金额");
    alx.add(xm);

    xm = new XmlMode();
    xm.setXmlName("BigNum");
    xm.setXmlValue("大写金额");
    alx.add(xm);

    xm = new XmlMode();
    xm.setXmlName("Postscript");
    xm.setXmlValue("附言");
    alx.add(xm);

    xm = new XmlMode();
    xm.setXmlName("Usage");
    xm.setXmlValue("付款用途");
    alx.add(xm);

    xm = new XmlMode();
    xm.setXmlName("Remark");
    xm.setXmlValue("摘要");
    alx.add(xm);

    xm = new XmlMode();
    xm.setXmlName("Seq_no");
    xm.setXmlValue("交易流水号");
    alx.add(xm);

    xm = new XmlMode();
    xm.setXmlName("Validate");
    xm.setXmlValue("验证码");
    alx.add(xm);

    xm = new XmlMode();
    xm.setXmlName("TrsDate");
    xm.setXmlValue("交易日期");
    alx.add(xm);

    xm = new XmlMode();
    xm.setXmlName("TrsDateV1");
    xm.setXmlValue("打印日期");
    alx.add(xm);

    xm = new XmlMode();
    xm.setXmlName("PrintCount");
    xm.setXmlValue("0");
    alx.add(xm);
    return alx;
  }

  public static byte[] getContent(String filePath)
    throws IOException
  {
    File file = new File(filePath);
    long fileSize = file.length();
    if (fileSize > 2147483647L) {
      System.out.println("file too big...");
      return null;
    }
    FileInputStream fi = new FileInputStream(file);
    byte[] buffer = new byte[(int)fileSize];
    int offset = 0;
    int numRead = 0;
    do {
      offset += numRead;

      if (offset >= buffer.length) break;  }
    while ((numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0);

    if (offset != buffer.length) {
      throw new IOException("Could not completely read file " + file.getName());
    }
    fi.close();
    return buffer;
  }

  public static byte[] toByteArray(String filename)
    throws IOException
  {
    File f = new File(filename);
    if (!(f.exists())) {
      throw new FileNotFoundException(filename);
    }

    ByteArrayOutputStream bos = new ByteArrayOutputStream((int)f.length());
    BufferedInputStream in = null;
    try {
      in = new BufferedInputStream(new FileInputStream(f));
      int buf_size = 1024;
      byte[] buffer = new byte[buf_size];
      int len = 0;
      while (-1 != (len = in.read(buffer, 0, buf_size))) {
        bos.write(buffer, 0, len);
      }
      return bos.toByteArray();
    }
    catch (IOException e) {
    }
    finally {
      try {
        in.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      bos.close();
    }
	return null;
  }

  public static byte[] toByteArray2(String filename)
    throws IOException
  {
    File f = new File(filename);
    if (!(f.exists())) {
      throw new FileNotFoundException(filename);
    }

    FileChannel channel = null;
    FileInputStream fs = null;
    try {
      fs = new FileInputStream(f);
      channel = fs.getChannel();
      ByteBuffer byteBuffer = ByteBuffer.allocate((int)channel.size());
      while (channel.read(byteBuffer) > 0);
      return byteBuffer.array();
    }
    catch (IOException e) {
    }
    finally {
      try {
        channel.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        fs.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
	return null;
  }

  public static byte[] toByteArray3(String filename)
    throws IOException
  {
    FileChannel fc = null;
    try {
      fc = new RandomAccessFile(filename, "r").getChannel();
      MappedByteBuffer byteBuffer = fc.map(FileChannel.MapMode.READ_ONLY, 0L, fc.size()).load();
      System.out.println(byteBuffer.isLoaded());
      byte[] result = new byte[(int)fc.size()];
      if (byteBuffer.remaining() > 0)
      {
        byteBuffer.get(result, 0, byteBuffer.remaining());
      }
      return result;
    }
    catch (IOException e) {
      throw e;
    } finally {
      try {
        fc.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}