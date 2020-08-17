 package com.common.pdf.mode;
 
 public class BothParties
 {
   private String reprotBillType;
   private String billNo;
   private String acName;
   private String acNo;
   private String deptName;
   private String partnerAcName;
   private String partnerAcNo;
   private String partnerBankName;
   private String amount;
   private String bigNum;
   private String mark;
   private String payPurpose;
   private String abstracts;
   private String trsSeq;
   private String valiCode;
   private String trsDate;
   private String trsDateV1;
   private String printCount;
 
   public String getPrintCount()
   {
     return this.printCount;
   }
 
   public void setPrintCount(String printCount)
   {
     this.printCount = printCount;
   }
 
   public String getTrsDateV1()
   {
     return this.trsDateV1;
   }
 
   public void setTrsDateV1(String trsDateV1)
   {
     this.trsDateV1 = trsDateV1;
   }
 
   public String getTrsDate()
   {
     return this.trsDate;
   }
 
   public void setTrsDate(String trsDate)
   {
     this.trsDate = trsDate;
   }
 
   public String getValiCode()
   {
     return this.valiCode;
   }
 
   public void setValiCode(String valiCode)
   {
     this.valiCode = valiCode;
   }
 
   public String getTrsSeq()
   {
     return this.trsSeq;
   }
 
   public void setTrsSeq(String trsSeq)
   {
     this.trsSeq = trsSeq;
   }
 
   public String getAbstracts()
   {
     return this.abstracts;
   }
 
   public void setAbstracts(String abstracts)
   {
     this.abstracts = abstracts;
   }
 
   public String getPayPurpose()
   {
     return this.payPurpose;
   }
 
   public void setPayPurpose(String payPurpose)
   {
     this.payPurpose = payPurpose;
   }
 
   public String getMark()
   {
     return this.mark;
   }
 
   public void setMark(String mark)
   {
     this.mark = mark;
   }
 
   public String getBigNum()
   {
     return this.bigNum;
   }
 
   public void setBigNum(String bigNum)
   {
     this.bigNum = bigNum;
   }
 
   public String getAmount()
   {
     return this.amount;
   }
 
   public void setAmount(String amount)
   {
     this.amount = amount;
   }
 
   public String getPartnerBankName()
   {
     return this.partnerBankName;
   }
 
   public void setPartnerBankName(String partnerBankName)
   {
     this.partnerBankName = partnerBankName;
   }
 
   public String getPartnerAcNo()
   {
     return this.partnerAcNo;
   }
 
   public void setPartnerAcNo(String partnerAcNo)
   {
     this.partnerAcNo = partnerAcNo;
   }
 
   public String getPartnerAcName()
   {
     return this.partnerAcName;
   }
 
   public void setPartnerAcName(String partnerAcName)
   {
     this.partnerAcName = partnerAcName;
   }
 
   public String getDeptName()
   {
     return this.deptName;
   }
 
   public void setDeptName(String deptName)
   {
     this.deptName = deptName;
   }
 
   public String getAcNo()
   {
     return this.acNo;
   }
 
   public void setAcNo(String acNo)
   {
     this.acNo = acNo;
   }
 
   public String getAcName()
   {
     return this.acName;
   }
 
   public void setAcName(String acName)
   {
     this.acName = acName;
   }
 
   public String getBillNo()
   {
     return this.billNo;
   }
 
   public void setBillNo(String billNo)
   {
     this.billNo = billNo;
   }
 
   public String getReprotBillType()
   {
     return this.reprotBillType;
   }
 
   public void setReprotBillType(String reprotBillType)
   {
     this.reprotBillType = reprotBillType;
   }
 
   public String toString() {
     return "BothParties [reprotBillType=" + this.reprotBillType + ", billNo=" + 
       this.billNo + ", acName=" + this.acName + ", acNo=" + this.acNo + ", deptName=" + 
       this.deptName + ", partnerAcName=" + this.partnerAcName + ", partnerAcNo=" + 
       this.partnerAcNo + ", partnerBankName=" + this.partnerBankName + 
       ", amount=" + this.amount + ", bigNum=" + this.bigNum + ", mark=" + this.mark + 
       ", payPurpose=" + this.payPurpose + ", abstracts=" + this.abstracts + 
       ", trsSeq=" + this.trsSeq + ", valiCode=" + this.valiCode + ", trsDate=" + 
       this.trsDate + ", trsDateV1=" + this.trsDateV1 + ", printCount=" + 
       this.printCount + "]";
   }
 }