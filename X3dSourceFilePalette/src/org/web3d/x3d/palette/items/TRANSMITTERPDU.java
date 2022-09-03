/*
Copyright (c) 1995-2021 held by the author(s) .  All rights reserved.
 
Redistribution and use in source and binary forms, with or without
modification, are permitted provided that the following conditions
are met:
 
 * Redistributions of source code must retain the above copyright
      notice, this list of conditions and the following disclaimer.
 * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer
      in the documentation and/or other materials provided with the
      distribution.
 * Neither the names of the Naval Postgraduate School (NPS)
      Modeling Virtual Environments and Simulation (MOVES) Institute
      (http://www.nps.edu and https://MovesInstitute.nps.edu)
      nor the names of its contributors may be used to endorse or
      promote products derived from this software without specific
      prior written permission.
 
THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
POSSIBILITY OF SUCH DAMAGE.
 */

package org.web3d.x3d.palette.items;

import javax.swing.text.JTextComponent;

import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * TRANSMITTERPDU.java
 * Created on 15 May 2008
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * 
 * @author Mike Bailey
 * @version $Id$
 */
public class TRANSMITTERPDU extends CommonPdu
{ 
  private SFFloat antennaLocation0,antennaLocation1,antennaLocation2;
  private SFFloat antennaLocation0Default,antennaLocation1Default,antennaLocation2Default;
  private SFInt32 antennaPatternLength, antennaPatternLengthDefault;
  private SFInt32 antennaPatternType, antennaPatternTypeDefault;
  private SFInt32 cryptoKeyID, cryptoKeyIDDefault;
  private SFInt32 cryptoSystem, cryptoSystemDefault;
  private SFInt32 frequency, frequencyDefault;
  private SFInt32 inputSource, inputSourceDefault;
  private SFInt32 lengthOfModulationParameters, lengthOfModulationParametersDefault;
  private SFInt32 modulationTypeDetail, modulationTypeDetailDefault;
  private SFInt32 modulationTypeMajor, modulationTypeMajorDefault;
  private SFInt32 modulationTypeSpreadSpectrum, modulationTypeSpreadSpectrumDefault;
  private SFInt32 modulationTypeSystem, modulationTypeSystemDefault;
  private SFFloat power,powerDefault;
  private SFInt32 radioEntityTypeCategory, radioEntityTypeCategoryDefault;
  private SFInt32 radioEntityTypeCountry, radioEntityTypeCountryDefault;
  private SFInt32 radioEntityTypeDomain, radioEntityTypeDomainDefault;
  private SFInt32 radioEntityTypeKind, radioEntityTypeKindDefault;
  private SFInt32 radioEntityTypeNomenclature, radioEntityTypeNomenclatureDefault;
  private SFInt32 radioEntityTypeNomenclatureVersion, radioEntityTypeNomenclatureVersionDefault;
  private SFFloat relativeAntennaLocation0,relativeAntennaLocation0Default;
  private SFFloat relativeAntennaLocation1,relativeAntennaLocation1Default;
  private SFFloat relativeAntennaLocation2,relativeAntennaLocation2Default;
  private SFFloat transmitFrequencyBandwidth, transmitFrequencyBandwidthDefault;
  private SFInt32 transmitState, transmitStateDefault;

  public TRANSMITTERPDU()
  {
  }

  @Override
  public String getElementName()
  {
    return TRANSMITTERPDU_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    super.initialize();
    String[] fa = parse3(TRANSMITTERPDU_ATTR_ANTENNALOCATION_DFLT);
    antennaLocation0   = antennaLocation0Default              = new SFFloat(fa[0],null,null);
    antennaLocation1   = antennaLocation1Default              = new SFFloat(fa[1],null,null);
    antennaLocation2   = antennaLocation2Default              = new SFFloat(fa[2],null,null);
    antennaPatternLength = antennaPatternLengthDefault        = new SFInt32(TRANSMITTERPDU_ATTR_ANTENNAPATTERNLENGTH_DFLT,0,65535);
    antennaPatternType = antennaPatternTypeDefault            = new SFInt32(TRANSMITTERPDU_ATTR_ANTENNAPATTERNTYPE_DFLT,0,65535);
    cryptoKeyID = cryptoKeyIDDefault                          = new SFInt32(TRANSMITTERPDU_ATTR_CRYPTOKEYID_DFLT,0,65535);
    cryptoSystem = cryptoSystemDefault                        = new SFInt32(TRANSMITTERPDU_ATTR_CRYPTOSYSTEM_DFLT,0,65535);
    frequency = frequencyDefault                              = new SFInt32(TRANSMITTERPDU_ATTR_FREQUENCY_DFLT,0,65535);
    inputSource = inputSourceDefault                          = new SFInt32(TRANSMITTERPDU_ATTR_INPUTSOURCE_DFLT,0,255);
    lengthOfModulationParameters = lengthOfModulationParametersDefault= new SFInt32(TRANSMITTERPDU_ATTR_LENGTHOFMODULATIONPARAMETERS_DFLT,0,255);
    modulationTypeDetail = modulationTypeDetailDefault                = new SFInt32(TRANSMITTERPDU_ATTR_MODULATIONTYPEDETAIL_DFLT,0,65535);
    modulationTypeMajor = modulationTypeMajorDefault                  = new SFInt32(TRANSMITTERPDU_ATTR_MODULATIONTYPEMAJOR_DFLT,0,65535);
    modulationTypeSpreadSpectrum = modulationTypeSpreadSpectrumDefault= new SFInt32(TRANSMITTERPDU_ATTR_MODULATIONTYPESPREADSPECTRUM_DFLT,0,65535);
    modulationTypeSystem = modulationTypeSystemDefault                = new SFInt32(TRANSMITTERPDU_ATTR_MODULATIONTYPESYSTEM_DFLT,0,65535);
    
    power = powerDefault                                   = new SFFloat(TRANSMITTERPDU_ATTR_POWER_DFLT,0f,null);
    
    radioEntityTypeCategory = radioEntityTypeCategoryDefault = new SFInt32(TRANSMITTERPDU_ATTR_RADIOENTITYTYPECATEGORY_DFLT,0,255);
    radioEntityTypeCountry = radioEntityTypeCountryDefault   = new SFInt32(TRANSMITTERPDU_ATTR_RADIOENTITYTYPECOUNTRY_DFLT,0,65535);
    radioEntityTypeDomain = radioEntityTypeDomainDefault     = new SFInt32(TRANSMITTERPDU_ATTR_RADIOENTITYTYPEDOMAIN_DFLT,0,255);
    radioEntityTypeKind = radioEntityTypeKindDefault         = new SFInt32(TRANSMITTERPDU_ATTR_RADIOENTITYTYPEKIND_DFLT,0,255);
    radioEntityTypeNomenclature = radioEntityTypeNomenclatureDefault               = new SFInt32(TRANSMITTERPDU_ATTR_RADIOENTITYTYPENOMENCLATURE_DFLT,0,255);
    radioEntityTypeNomenclatureVersion = radioEntityTypeNomenclatureVersionDefault = new SFInt32(TRANSMITTERPDU_ATTR_RADIOENTITYTYPENOMENCLATUREVERSION_DFLT,0,65535);

    fa = parse3(TRANSMITTERPDU_ATTR_RELATIVEANTENNALOCATION_DFLT);
    relativeAntennaLocation0   = relativeAntennaLocation0Default = new SFFloat(fa[0],null,null);
    relativeAntennaLocation1   = relativeAntennaLocation1Default = new SFFloat(fa[1],null,null);
    relativeAntennaLocation2   = relativeAntennaLocation2Default = new SFFloat(fa[2],null,null);
    
    transmitFrequencyBandwidth = transmitFrequencyBandwidthDefault = new SFFloat(TRANSMITTERPDU_ATTR_TRANSMITFREQUENCYBANDWIDTH_DFLT,null,null);
    transmitState = transmitStateDefault                           = new   SFInt32(TRANSMITTERPDU_ATTR_TRANSMITSTATE_DFLT,0,255);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);
    org.jdom.Attribute attr;
    String[] sa;

    attr = root.getAttribute(TRANSMITTERPDU_ATTR_ANTENNALOCATION_NAME);
    if(attr != null) {
      sa = parse3(attr.getValue());
      antennaLocation0 = new SFFloat(sa[0],null,null);
      antennaLocation1 = new SFFloat(sa[1],null,null);
      antennaLocation2 = new SFFloat(sa[2],null,null);
    }
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_ANTENNAPATTERNLENGTH_NAME);
    if(attr != null) 
      antennaPatternLength = new SFInt32(attr.getValue());
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_ANTENNAPATTERNTYPE_NAME);
    if(attr != null) 
      antennaPatternType = new SFInt32(attr.getValue());
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_CRYPTOKEYID_NAME);
    if(attr != null) 
      cryptoKeyID = new SFInt32(attr.getValue());
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_CRYPTOSYSTEM_NAME);
    if(attr != null) 
      cryptoSystem = new SFInt32(attr.getValue());
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_FREQUENCY_NAME);
    if(attr != null) 
      frequency = new SFInt32(attr.getValue());
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_INPUTSOURCE_NAME);
    if(attr != null) 
      inputSource = new SFInt32(attr.getValue());
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_LENGTHOFMODULATIONPARAMETERS_NAME);
    if(attr != null) 
      lengthOfModulationParameters = new SFInt32(attr.getValue());
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_MODULATIONTYPEDETAIL_NAME);
    if(attr != null) 
      modulationTypeDetail = new SFInt32(attr.getValue());
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_MODULATIONTYPEMAJOR_NAME);
    if(attr != null) 
      modulationTypeMajor = new SFInt32(attr.getValue());
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_MODULATIONTYPESPREADSPECTRUM_NAME);
    if(attr != null) 
      modulationTypeSpreadSpectrum = new SFInt32(attr.getValue());
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_MODULATIONTYPESYSTEM_NAME);
    if(attr != null) 
      modulationTypeSystem = new SFInt32(attr.getValue());    
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_POWER_NAME);
    if(attr != null) 
      power = new SFFloat(attr.getValue());    
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_RADIOENTITYTYPECATEGORY_NAME);
    if(attr != null) 
      radioEntityTypeCategory = new SFInt32(attr.getValue());
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_RADIOENTITYTYPECOUNTRY_NAME);
    if(attr != null) 
      radioEntityTypeCountry = new SFInt32(attr.getValue());
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_RADIOENTITYTYPEDOMAIN_NAME);
    if(attr != null) 
      radioEntityTypeDomain = new SFInt32(attr.getValue());
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_RADIOENTITYTYPEKIND_NAME);
    if(attr != null) 
      radioEntityTypeKind = new SFInt32(attr.getValue());
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_RADIOENTITYTYPENOMENCLATURE_NAME);
    if(attr != null) 
      radioEntityTypeNomenclature = new SFInt32(attr.getValue());
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_RADIOENTITYTYPENOMENCLATUREVERSION_NAME);
    if(attr != null) 
      radioEntityTypeNomenclatureVersion = new SFInt32(attr.getValue());
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_RELATIVEANTENNALOCATION_NAME);
    if(attr != null) {
      sa = parse3(attr.getValue());
      relativeAntennaLocation0   = new SFFloat(sa[0],null,null);
      relativeAntennaLocation1   = new SFFloat(sa[1],null,null);
      relativeAntennaLocation2   = new SFFloat(sa[2],null,null);
    }
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_TRANSMITFREQUENCYBANDWIDTH_NAME);
    if(attr != null) 
      transmitFrequencyBandwidth = new SFFloat(attr.getValue());
    attr = root.getAttribute(TRANSMITTERPDU_ATTR_TRANSMITSTATE_NAME);
    if(attr != null) 
      transmitState = new SFInt32(attr.getValue());     
  }

  @Override
  public String createAttributes()
  {
    StringBuffer sb = new StringBuffer();
    sb.append(super.createAttributes());

    if (TRANSMITTERPDU_ATTR_ANTENNALOCATION_REQD ||
        (!antennaLocation0.equals(antennaLocation0Default) ||
        !antennaLocation1.equals(antennaLocation1Default) ||
        !antennaLocation2.equals(antennaLocation2Default))) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_ANTENNALOCATION_NAME);
      sb.append("='");
      sb.append(antennaLocation0);
      sb.append(" ");
      sb.append(antennaLocation1);
      sb.append(" ");
      sb.append(antennaLocation2);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_ANTENNAPATTERNLENGTH_REQD || !antennaPatternLength.equals(antennaPatternLengthDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_ANTENNAPATTERNLENGTH_NAME);
      sb.append("='");
      sb.append(antennaPatternLength);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_ANTENNAPATTERNTYPE_REQD || !antennaPatternType.equals(antennaPatternTypeDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_ANTENNAPATTERNTYPE_NAME);
      sb.append("='");
      sb.append(antennaPatternType);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_CRYPTOKEYID_REQD || !cryptoKeyID.equals(cryptoKeyIDDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_CRYPTOKEYID_NAME);
      sb.append("='");
      sb.append(cryptoKeyID);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_CRYPTOSYSTEM_REQD || !cryptoSystem.equals(cryptoSystemDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_CRYPTOSYSTEM_NAME);
      sb.append("='");
      sb.append(cryptoSystem);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_FREQUENCY_REQD || !frequency.equals(frequencyDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_FREQUENCY_NAME);
      sb.append("='");
      sb.append(frequency);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_INPUTSOURCE_REQD || !inputSource.equals(inputSourceDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_INPUTSOURCE_NAME);
      sb.append("='");
      sb.append(inputSource);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_LENGTHOFMODULATIONPARAMETERS_REQD || !lengthOfModulationParameters.equals(lengthOfModulationParametersDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_LENGTHOFMODULATIONPARAMETERS_NAME);
      sb.append("='");
      sb.append(lengthOfModulationParameters);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_MODULATIONTYPEDETAIL_REQD || !modulationTypeDetail.equals(modulationTypeDetailDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_MODULATIONTYPEDETAIL_NAME);
      sb.append("='");
      sb.append(modulationTypeDetail);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_MODULATIONTYPEMAJOR_REQD || !modulationTypeMajor.equals(modulationTypeMajorDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_MODULATIONTYPEMAJOR_NAME);
      sb.append("='");
      sb.append(modulationTypeMajor);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_MODULATIONTYPESPREADSPECTRUM_REQD || !modulationTypeSpreadSpectrum.equals(modulationTypeSpreadSpectrumDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_MODULATIONTYPESPREADSPECTRUM_NAME);
      sb.append("='");
      sb.append(modulationTypeSpreadSpectrum);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_MODULATIONTYPESYSTEM_REQD || !modulationTypeSystem.equals(modulationTypeSystemDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_MODULATIONTYPESYSTEM_NAME);
      sb.append("='");
      sb.append(modulationTypeSystem);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_POWER_REQD || !power.equals(powerDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_POWER_NAME);
      sb.append("='");
      sb.append(power);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_RADIOENTITYTYPECATEGORY_REQD || !radioEntityTypeCategory.equals(radioEntityTypeCategoryDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_RADIOENTITYTYPECATEGORY_NAME);
      sb.append("='");
      sb.append(radioEntityTypeCategory);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_RADIOENTITYTYPECOUNTRY_REQD || !radioEntityTypeCountry.equals(radioEntityTypeCountryDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_RADIOENTITYTYPECOUNTRY_NAME);
      sb.append("='");
      sb.append(radioEntityTypeCountry);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_RADIOENTITYTYPEDOMAIN_REQD || !radioEntityTypeDomain.equals(radioEntityTypeDomainDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_RADIOENTITYTYPEDOMAIN_NAME);
      sb.append("='");
      sb.append(radioEntityTypeDomain);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_RADIOENTITYTYPEKIND_REQD || !radioEntityTypeKind.equals(radioEntityTypeKindDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_RADIOENTITYTYPEKIND_NAME);
      sb.append("='");
      sb.append(radioEntityTypeKind);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_RADIOENTITYTYPENOMENCLATURE_REQD || !radioEntityTypeNomenclature.equals(radioEntityTypeNomenclatureDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_RADIOENTITYTYPENOMENCLATURE_NAME);
      sb.append("='");
      sb.append(radioEntityTypeNomenclature);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_RADIOENTITYTYPENOMENCLATUREVERSION_REQD || !radioEntityTypeNomenclatureVersion.equals(radioEntityTypeNomenclatureVersionDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_RADIOENTITYTYPENOMENCLATUREVERSION_NAME);
      sb.append("='");
      sb.append(radioEntityTypeNomenclatureVersion);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_RELATIVEANTENNALOCATION_REQD ||
        (!relativeAntennaLocation0.equals(relativeAntennaLocation0Default) ||
        !relativeAntennaLocation1.equals(relativeAntennaLocation1Default) ||
        !relativeAntennaLocation2.equals(relativeAntennaLocation2Default))) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_RELATIVEANTENNALOCATION_NAME);
      sb.append("='");
      sb.append(relativeAntennaLocation0);
      sb.append(" ");
      sb.append(relativeAntennaLocation1);
      sb.append(" ");
      sb.append(relativeAntennaLocation2);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_TRANSMITFREQUENCYBANDWIDTH_REQD || !transmitFrequencyBandwidth.equals(transmitFrequencyBandwidthDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_TRANSMITFREQUENCYBANDWIDTH_NAME);
      sb.append("='");
      sb.append(transmitFrequencyBandwidth);
      sb.append("'");
    }
    if (TRANSMITTERPDU_ATTR_TRANSMITSTATE_REQD || !transmitState.equals(transmitStateDefault)) {
      sb.append(" ");
      sb.append(TRANSMITTERPDU_ATTR_TRANSMITSTATE_NAME);
      sb.append("='");
      sb.append(transmitState);
      sb.append("'");
    }
    return sb.toString();
  }

  public String getAntennaLocation0()             { return antennaLocation0.toString();}
  public String getAntennaLocation1()             { return antennaLocation1.toString();}
  public String getAntennaLocation2()             { return antennaLocation2.toString();}
  public String getAntennaPatternLength()         { return antennaPatternLength.toString();}
  public String getAntennaPatternType()           { return antennaPatternType.toString();}
  public String getCryptoKeyID()                  { return cryptoKeyID.toString();}
  public String getCryptoSystem()                 { return cryptoSystem.toString(); }
  public String getFrequency()                    { return frequency.toString(); }
  public String getInputSource()                  { return inputSource.toString(); }
  public String getLengthOfModulationParameters() { return lengthOfModulationParameters.toString(); }
  public String getModulationTypeDetail()         { return modulationTypeDetail.toString(); }
  public String getModulationTypeMajor()          { return modulationTypeMajor.toString(); }
  public String getModulationTypeSpreadSpectrum() { return modulationTypeSpreadSpectrum.toString(); }
  public String getModulationTypeSystem()         { return modulationTypeSystem.toString(); }
  public String getPower()                        { return power.toString(); }
  public String getRadioEntityTypeCategory()      { return radioEntityTypeCategory.toString(); }
  public String getRadioEntityTypeCountry()       { return radioEntityTypeCountry.toString(); }
  public String getRadioEntityTypeDomain()        { return radioEntityTypeDomain.toString(); }
  public String getRadioEntityTypeKind()          { return radioEntityTypeKind.toString(); }
  public String getRadioEntityTypeNomenclature()  { return radioEntityTypeNomenclature.toString(); }
  public String getRadioEntityTypeNomenclatureVersion() { return radioEntityTypeNomenclatureVersion.toString(); }
  public String getRelativeAntennaLocation0()     { return relativeAntennaLocation0.toString(); }
  public String getRelativeAntennaLocation1()     { return relativeAntennaLocation1.toString(); }
  public String getRelativeAntennaLocation2()     { return relativeAntennaLocation2.toString(); }
  public String getTransmitFrequencyBandwidth()   { return transmitFrequencyBandwidth.toString(); }
  public String getTransmitState()                { return transmitState.toString(); }
  
  public void setAntennaLocation0(String s)             { antennaLocation0     = new SFFloat(s,null,null);}
  public void setAntennaLocation1(String s)             { antennaLocation1     = new SFFloat(s,null,null);}
  public void setAntennaLocation2(String s)             { antennaLocation2     = new SFFloat(s,null,null);}
  public void setAntennaPatternLength(String s)         { antennaPatternLength = new SFInt32(s,0,65535);}
  public void setAntennaPatternType(String s)           { antennaPatternType   = new SFInt32(s,0,65535);}
  public void setCryptoKeyID(String s)                  { cryptoKeyID          = new SFInt32(s,0,65535); }
  public void setCryptoSystem(String s)                 { cryptoSystem         = new SFInt32(s,0,65535); }
  public void setFrequency(String s)                    { frequency            = new SFInt32(s,0,65535); }
  public void setInputSource(String s)                  { inputSource          = new SFInt32(s,0,255); }
  public void setLengthOfModulationParameters(String s) { lengthOfModulationParameters = new SFInt32(s,0,255); }
  public void setModulationTypeDetail(String s)         { modulationTypeDetail         = new SFInt32(s,0,65535); }
  public void setModulationTypeMajor(String s)          { modulationTypeMajor          = new SFInt32(s,0,65535); }
  public void setModulationTypeSpreadSpectrum(String s) { modulationTypeSpreadSpectrum = new SFInt32(s,0,65535); }
  public void setModulationTypeSystem(String s)         { modulationTypeSystem        = new SFInt32(s,0,65535); }
  public void setPower(String s)                        { power                       = new SFFloat(s,0f,null); }
  public void setRadioEntityTypeCategory(String s)      { radioEntityTypeCategory     = new SFInt32(s,0,255); }
  public void setRadioEntityTypeCountry(String s)       { radioEntityTypeCountry      = new SFInt32(s,0,65535); }
  public void setRadioEntityTypeDomain(String s)        { radioEntityTypeDomain       = new SFInt32(s,0,255); }
  public void setRadioEntityTypeKind(String s)          { radioEntityTypeKind         = new SFInt32(s,0,255); }
  public void setRadioEntityTypeNomenclature(String s)  { radioEntityTypeNomenclature = new SFInt32(s,0,255); }
  public void setRadioEntityTypeNomenclatureVersion(String s) { radioEntityTypeNomenclatureVersion = new SFInt32(s,0,65535); }
  public void setRelativeAntennaLocation0(String s)     { relativeAntennaLocation0    = new SFFloat(s,null,null); }
  public void setRelativeAntennaLocation1(String s)     { relativeAntennaLocation1    = new SFFloat(s,null,null); }
  public void setRelativeAntennaLocation2(String s)     { relativeAntennaLocation2    = new SFFloat(s,null,null); }
  public void setTransmitFrequencyBandwidth(String s)   { transmitFrequencyBandwidth  = new SFFloat(s,null,null); }
  public void setTransmitState(String s)                { transmitState               = new SFInt32(s,0,255); }
}