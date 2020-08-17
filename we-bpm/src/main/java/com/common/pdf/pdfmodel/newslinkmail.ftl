<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<style type="text/css" >
      .noprt
      {
        display: none;
      }
    </style>
<style>
body{ margin:0; padding:0; padding-top:30px;font-family: SimSun}
td{
	font-size:12px;
	font-family: SimSun;
	line-height: 1.5;
}
.dzhd_logo{
	height:100px;
	font-size:26px;
}
.ziti14{ font-size:12px;}
.dzhd_table{ border-right:1px #333333 solid;border-bottom:1px #333333 solid;}
.dzhd_table th{ height:30px; font-weight:normal;  text-align:center; font-size:10px;border-left:1px #333333 solid;border-top:1px #333333 solid;}
.dzhd_table td{ height:30px; text-align:left; padding:0 5px; line-height:30px; font-size:10px;border-left:1px #333333 solid;border-top:1px #333333 solid;}
</style>
</head>
<body>
<table width="100%" height="60" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="40%" lign="left" valign="middle" class="dzhd_logo">
    <img src="biaozhi.png" height="42" />
    </td>
    <td width="60%" align="left" valign="middle" class="dzhd_logo">
    <strong><font size="3">企业网上银行电子回单</font></strong></td>
    <td  align="center" valign="middle" class="dzhd_logo"></td>
  </tr>
</table>
<table width="100%" height="30" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="50%" align="left" ><strong>电子回单号码：</strong></td>
    <td width="50%" align="right">(第${PrintCount}次补打)</td>
  </tr>
</table>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" class="dzhd_table">
  <tr>
    <td colspan="8">回单类型：</td>
  </tr>
  <#if DebitMark == "0">
  <tr>
    <th rowspan="3" colspan="1"><strong>付<br/>
    款<br/>
   方</strong>
   </th>
    <th  colspan="1" >户  名</th>
    <td  colspan="2"></td>
    <th  rowspan="3" colspan="1"><strong>收<br/>
    款<br/>
    方</strong>
    </th>
    <th  colspan="1">户  名</th>
    <td  colspan="2"></td>
  </tr>
  <tr>
    <th colspan="1">账  号</th>
    <td colspan="2"></td>
    <th colspan="1">账  号</th>
    <td colspan="2"></td>
  </tr>
  <tr>
    <th colspan="1">开户银行</th>
    <td colspan="2"></td>
    <th colspan="1">开户银行</th>
    <td colspan="2"></td>
  </tr>
  </#if>
   <#if DebitMark == "1" >
  <tr>
    <th rowspan="3" colspan="1"><strong>付<br/>
    款<br/>
   方</strong>
   </th>
    <th  colspan="1" >户  名</th>
    <td  colspan="2"></td>
    <th  rowspan="3" colspan="1"><strong>收<br/>
    款<br/>
    方</strong>
    </th>
    <th  colspan="1">户  名</th>
    <td  colspan="2"></td>
  </tr>
  <tr>
    <th colspan="1">账  号</th>
    <td colspan="2"></td>
    <th colspan="1">账  号</th>
    <td colspan="2"></td>
  </tr>
  <tr>
    <th colspan="1">开户银行</th>
    <td colspan="2"></td>
    <th colspan="1">开户银行</th>
    <td colspan="2"></td>
  </tr>
  </#if>
  <tr>
    <th colspan="2">金额</th>
    <td colspan="6">小写：${Amount}          &nbsp;    大写：人民币
    <span id="Chinese">${BigNum}</span></td>
  </tr>
  <tr>
    <th colspan="2">附言</th>
    <td colspan="6">${Postscript}</td>
  </tr>
  <tr>
   <th colspan="2" rowspan="4">
    <img src="20160427173421.png" width="120" height="120"/>
   </th>
    <th colspan="1">付款用途</th>
    <td colspan="2">${Usage}</td>
    <th colspan="1">摘要</th>
    <td colspan="2">${Remark}</td>
  </tr>
  <tr>
    <th colspan="1">交易流水号</th>
    <td colspan="5"></td>
  </tr>
  <tr>
    <th colspan="1">验证码</th>
    <td colspan="5">${Validate}</td>
  </tr>
   <tr>
    <th colspan="1">交易日期</th>
    <td colspan="2" nowrap="nowrap">${Trans_Date}</td>
    <th colspan="1">打印日期</th>
    <td colspan="2">${TrsDateV1}</td>
  </tr>
</table>
<table width="100%" height="30" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="50%" align="left">&nbsp;</td>
    <td width="50%" align="right">重要提示：此回单不具备法律效力，切勿重复记账</td>
  </tr>
</table>
</body>
</html>