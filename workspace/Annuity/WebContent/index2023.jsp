<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<HTML>

<HEAD>
<TITLE>INTERACTIVE LIFE TABLE AND ANNUITY PROGRAM</TITLE>
</HEAD>

<BODY>
<H1 ALIGN=LEFT>Life Tables and Annuity Values</H1>
<H2>(Based on 2023 Trustees Report)</H2>
<HR>
<FORM ACTION="/Annuity/Annuity2023" method=post>


<TABLE summary="Form to input parameters for interactive annuity program.">

<TR>
<TD VALIGN=TOP>
<FIELDSET>
<!-- Alternative -->
<LEGEND style="color: blue"><STRONG>Mortality Assumption</STRONG></LEGEND>
<INPUT TYPE="RADIO" NAME="ALT" id="LowCost" VALUE="1"><LABEL for="LowCost">Low Cost</LABEL><BR>
<INPUT TYPE="RADIO" NAME="ALT" id="Intermediate" VALUE="2" CHECKED><LABEL for="Intermediate">Intermediate</LABEL><BR>
<INPUT TYPE="RADIO" NAME="ALT" id="HighCost" VALUE="3" ><LABEL for="HighCost">High Cost</LABEL>
</FIELDSET>

<BR>
<FIELDSET>

<!-- Type -->
<LEGEND style="color: blue"><STRONG>Mortality Type</STRONG></LEGEND>
<INPUT TYPE="RADIO" NAME="TYPE" VALUE="PER" id="Period"><LABEL for="Period">Period</LABEL><BR>
<INPUT TYPE="RADIO" NAME="TYPE" VALUE="COH" id="Cohort" CHECKED><LABEL for="Cohort">Cohort</LABEL><BR>
</FIELDSET>
</TD>

<TD WIDTH="6%"></TD>

<TD><!-- Annuity Type -->
<FIELDSET>
<LEGEND style="color: blue"><STRONG>Annuity Type</STRONG></LEGEND>
<INPUT TYPE="RADIO" NAME="ANT" id="Individual" VALUE="IND" CHECKED><LABEL for="Individual">Individual</LABEL><BR>

<INPUT TYPE="RADIO" NAME="ANT" id="Joint" VALUE="JOI"><LABEL for="Joint">Joint</LABEL><BR>
<INPUT TYPE="RADIO" NAME="ANT" id="Both" VALUE="BOT"><LABEL for="Both">Both</LABEL>
</FIELDSET>
</TD>

<TD WIDTH="6%"></TD>

<TD VALIGN=TOP>
<!-- Step Size -->
<P><STRONG><LABEL for="StepSize">Step Size</LABEL></STRONG><BR>
<SELECT NAME=STEP id="StepSize">
<OPTION VALUE="1" SELECTED>1
<OPTION VALUE="2">2
<OPTION VALUE="3">3
<OPTION VALUE="4">4
<OPTION VALUE="5">5
<OPTION VALUE="10">10
<OPTION VALUE="12">12
<OPTION VALUE="15">15
<OPTION VALUE="20">20
<OPTION VALUE="25">25
<OPTION VALUE="30">30
<OPTION VALUE="40">40
<OPTION VALUE="50">50
<OPTION VALUE="75">75

</SELECT></P>


<!-- Starting Year of Birth -->
<P><LABEL for="StartingYear"><STRONG>Starting Year</STRONG></LABEL><BR>
<INPUT TYPE=HIDDEN NAME=STACEN VALUE="0">
<INPUT TYPE=HIDDEN NAME=STADEC VALUE="0">
<INPUT TYPE=TEXT NAME=STAYER SIZE=5 id="StartingYear" VALUE="2023">
</P>

<!-- Ending Year of Birth -->
<P><LABEL for="EndingYear"><STRONG>Ending Year</STRONG></LABEL><BR>
<INPUT TYPE=HIDDEN NAME=ENDCEN VALUE="0">
<INPUT TYPE=HIDDEN NAME=ENDDEC VALUE="0">
<INPUT TYPE=TEXT NAME=ENDYER SIZE=5 id="EndingYear" VALUE="2023">
</P>
</TD>

<TD WIDTH="6%"></TD>

<TD>
<!-- Interest Rate -->
<P><STRONG><LABEL for="InterestRate">Interest Rate</LABEL></STRONG><BR>
<INPUT TYPE=TEXT SIZE=5 NAME="INT" id="InterestRate" VALUE="2.30"><STRONG>%</STRONG>

</P>

<TD WIDTH="6%"></TD>

<TD>

<!-- Classes and Ages to Display -->
<FIELDSET>
<LEGEND style="color: blue"><STRONG>Classes</STRONG></LEGEND>
<INPUT TYPE="CHECKBOX" NAME="MALE" id="Male" VALUE="1" CHECKED><LABEL for="Male">Male</LABEL><BR>
<INPUT TYPE="CHECKBOX" NAME="FEMALE" id="Female" VALUE="1" CHECKED><LABEL for="Female">Female</LABEL><BR>
<INPUT TYPE="CHECKBOX" NAME="UNISEX" id="Unisex" VALUE="1" CHECKED><LABEL for="Unisex">Unisex</LABEL><BR>
</FIELDSET>

<P><STRONG><LABEL for="StartingAge">Starting Age</LABEL></STRONG><BR>
<INPUT TYPE=TEXT SIZE=5 NAME="BEGINAGE" id="StartingAge" VALUE="0"><BR>
<STRONG><LABEL for="EndingAge">Ending Age</LABEL></STRONG><BR>
<INPUT TYPE=TEXT SIZE=5 NAME="ENDAGE" id="EndingAge" VALUE="119">
</P>
</TD>

</TABLE>
<HR>
<!-- Submit -->
<INPUT TYPE=SUBMIT VALUE="SUBMIT">
<INPUT TYPE="CHECKBOX" NAME="FORMAT" id="Format" VALUE="1" CHECKED><LABEL for="Format">Include titles in the output.</LABEL>
</FORM>

</BODY>

</HTML>

