<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML><HEAD><TITLE>INTERACTIVE POPULATION PROGRAM</TITLE>
<BODY>
<P>
<H1 align=left>Social Security Area Population</H1>
<H3>(Based on 2021 Trustees Report)</H3>
<P></P>
<HR>

<FORM name=form1 action="/Population/Population2021" method="post">
<TABLE>
  <TBODY>
  <TR>
    <TD vAlign=top width=150><!-- Month -->
      <P><STRONG>Month Basis</STRONG><BR><INPUT type=radio CHECKED value=JAN 
      name=MONTH>January<BR><INPUT type=radio value=JUL name=MONTH>July
      <BR><INPUT type=radio value=DEC name=MONTH>December<BR><!-- Type -->
      <P><STRONG>Alternative(s)</STRONG><BR><INPUT type=checkbox value=1 
      name=ALT1>Alt I<BR><INPUT type=checkbox CHECKED value=1 name=ALT2>Alt 
      II<BR><INPUT type=checkbox value=1 name=ALT3>Alt III<BR></P>
      <TABLE>
        <TR><TH colspan="2" align="left"><STRONG>Sensitivity(ies)</STRONG><BR></TH>
        <TBODY>
        <TR>
          <TD><INPUT type=checkbox value=1 name=FER1>Fer I<BR><INPUT 
            type=checkbox value=1 name=FER3>Fer III<BR><INPUT type=checkbox 
            value=1 name=IMM1>Imm I<BR><INPUT type=checkbox value=1 
            name=IMM3>Imm III<BR><INPUT type=checkbox value=1 name=MOR1>Mor 
            I<BR><INPUT type=checkbox value=1 name=MOR3>Mor III<BR>
          <TD vAlign=top><INPUT type=checkbox value=1 name=MAR1>Mar 
            I<BR><INPUT type=checkbox value=1 name=MAR3>Mar III<BR><INPUT 
            type=checkbox value=1 name=DIV1>Div I<BR><INPUT type=checkbox 
            value=1 name=DIV3>Div III<BR>
            </TR></TABLE></TD>
    <TD width=50></TD>
    <TD vAlign=top><!-- Step Size -->
      <P><STRONG>Step Size</STRONG><BR><INPUT size=3 value=1 name=STEP> 
      </P><!-- Starting Year -->
      <P><STRONG>Starting Year</STRONG><BR>
      <TABLE>
        <TBODY>
        <TR>
          <TD><SELECT name=STACEN> <OPTION value=19>19<OPTION value=20 
              selected>20<OPTION value=21>21</OPTION></SELECT> </TD>
          <TD><SELECT name=STADEC> <OPTION value=0>0<OPTION 
              value=1>1<OPTION>1<OPTION value=2 selected>2<OPTION value=3>3<OPTION 
              value=4>4<OPTION value=5>5<OPTION value=6>6<OPTION 
              value=7>7<OPTION value=8>8<OPTION value=9>9</OPTION></SELECT> </TD>
          <TD><SELECT name=STAYER> <OPTION value=0 >0<OPTION value=1 selected>1<OPTION 
              value=2>2<OPTION value=3>3<OPTION value=4>4<OPTION 
              value=5>5<OPTION value=6>6<OPTION value=7>7<OPTION value=8
              >8<OPTION value=9>9<OPTION>9</OPTION></SELECT> 
      </TD></TR></TBODY></TABLE><!-- Ending Year -->
      <P><STRONG>Ending Year</STRONG><BR>
      <TABLE>
        <TBODY>
        <TR>
          <TD><SELECT name=ENDCEN> <OPTION value=19>19<OPTION value=20 
              selected>20<OPTION value=21>21</OPTION></SELECT> </TD>
          <TD><SELECT name=ENDDEC> <OPTION value=0>0<OPTION 
              value=1>1<OPTION value=2 selected>2<OPTION value=3>3<OPTION 
              value=4>4<OPTION value=5>5<OPTION value=6>6<OPTION 
              value=7>7<OPTION value=8>8<OPTION value=9>9</OPTION></SELECT> </TD>
          <TD><SELECT name=ENDYER> <OPTION value=0 >0<OPTION value=1 selected>1<OPTION 
              value=2>2<OPTION value=3>3<OPTION value=4>4<OPTION 
              value=5>5<OPTION value=6>6<OPTION value=7 >7<OPTION value=8 
              >8<OPTION value=9>9</OPTION></SELECT> 
      </TD></TR></TBODY></TABLE>
    <TD width=50></TD>
    <TD vAlign=top><!-- Ages -->
      <P><STRONG>Step Size</STRONG><BR><INPUT size=3 value=1 name=AGESTEP> </P><!-- Starting Age -->
      <P><STRONG>Starting Age</STRONG><BR><INPUT size=3 value=0 name=STARTAGE> 
      </P><!-- Ending Age -->
      <P><STRONG>Ending Age</STRONG><BR><INPUT size=3 value=100 name=ENDAGE> 
    </P></TD>
    <TD width=50></TD>
    <TD><!-- Classes and Ages to Display -->
      <P><STRONG>Class(es)</STRONG><BR><INPUT disabled type=checkbox CHECKED 
      value=1 name=MALE>Male<BR><INPUT disabled type=checkbox CHECKED value=1 
      name=FEMALE>Female<BR><INPUT disabled type=checkbox CHECKED value=1 
      name=SEXTOTAL>Total<BR></P>
      <P><STRONG>Marital Status</STRONG><BR><INPUT disabled type=checkbox 
      CHECKED value=1 name=SINGLE>Single<BR><INPUT disabled type=checkbox 
      CHECKED value=1 name=MARRIED>Married<BR><INPUT disabled type=checkbox 
      CHECKED value=1 name=DIVORCED>Divorced<BR><INPUT disabled type=checkbox 
      CHECKED value=1 name=WIDOWED>Widowed<BR><INPUT disabled type=checkbox 
      CHECKED value=1 name=MSTOTAL>Total<BR></P></TD></TR></TBODY></TABLE>
<HR>
<!-- Submit -->
<TABLE>
  <TBODY>
  <TR>
    <TD width=25></TD>
    <TD width=30><INPUT type=submit value=SUBMIT></TD>
    <TD width=300><INPUT type="CHECKBOX" NAME="FORMAT" id="Format" VALUE="1" CHECKED><LABEL
     for="Format">Include titles in the output.</LABEL></INPUT></TD>
    <TD width=25></TD>
    <TD width=400></TD></TR></TBODY></TABLE></FORM></BODY></HTML>
