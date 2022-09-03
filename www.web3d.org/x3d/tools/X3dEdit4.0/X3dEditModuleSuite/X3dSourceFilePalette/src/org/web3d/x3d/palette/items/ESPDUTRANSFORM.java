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
import static org.web3d.x3d.palette.X3DPaletteUtilities.escapeXmlCharacters;

import org.web3d.x3d.types.X3DTransformNode;
import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * ESPDUTRANSFORM.java
 * Created on 15 May 2008
 * 
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 * 
 * @author Mike Bailey
 * @version $Id$
 */
public class ESPDUTRANSFORM extends X3DTransformNode
{ 
  /*private*/ String  address;
  /*private*/ SFInt32   appID, appIDDefault;
  /*private*/ SFInt32   articulationParameterCount, articulationParameterCountDefault;
  /*private*/ SFInt32[] articulationParameterDesignatorArray, articulationParameterDesignatorArrayDefault;
  /*private*/ SFInt32[] articulationParameterChangeIndicatorArray, articulationParameterChangeIndicatorArrayDefault;
  /*private*/ SFInt32[] articulationParameterIdPartAttachedToArray, articulationParameterIdPartAttachedToArrayDefault;
  /*private*/ SFInt32[] articulationParameterTypeArray, articulationParameterTypeArrayDefault;
  /*private*/ SFFloat[] articulationParameterArray, articulationParameterArrayDefault;
  /*private*/ SFInt32   collisionType, collisionTypeDefault;
  /*private*/ SFInt32   deadReckoning, deadReckoningDefault;
  /*private*/ SFFloat detonationLocationX, detonationLocationXDefault;
  /*private*/ SFFloat detonationLocationY, detonationLocationYDefault;
  /*private*/ SFFloat detonationLocationZ, detonationLocationZDefault;
  /*private*/ SFFloat detonationRelativeLocationX, detonationRelativeLocationXDefault;
  /*private*/ SFFloat detonationRelativeLocationY, detonationRelativeLocationYDefault;
  /*private*/ SFFloat detonationRelativeLocationZ, detonationRelativeLocationZDefault;
  /*private*/ SFInt32   detonationResult, detonationResultDefault;
  /*private*/ boolean enabled, enabledDefault;
  /*private*/ SFInt32   entityCategory, entityCategoryDefault;
  /*private*/ SFInt32   entityCountry, entityCountryDefault;
  /*private*/ SFInt32   entityDomain, entityDomainDefault;
  /*private*/ SFInt32   entityExtra, entityExtraDefault;
  /*private*/ SFInt32   entityID, entityIDDefault;
  /*private*/ SFInt32   entityKind, entityKindDefault;
  /*private*/ SFInt32   entitySpecific, entitySpecificDefault;
  /*private*/ SFInt32   entitySubCategory, entitySubCategoryDefault;
  /*private*/ SFInt32   eventApplicationID, eventApplicationIDDefault;
  /*private*/ SFInt32   eventEntityID, eventEntityIDDefault;
  /*private*/ SFInt32   eventNumber, eventNumberDefault;
  /*private*/ SFInt32   eventSiteID, eventSiteIDDefault;
  /*private*/ boolean fired1, fired1Default;
  /*private*/ boolean fired2, fired2Default;
  /*private*/ SFInt32   fireMissionIndex, fireMissionIndexDefault;
  /*private*/ SFFloat firingRange, firingRangeDefault;
  /*private*/ SFInt32   firingRate, firingRateDefault;
  /*private*/ SFInt32   forceID, forceIDDefault;
  /*private*/ SFInt32   fuse, fuseDefault;
  /*private*/ SFFloat linearVelocityX, linearVelocityXDefault;
  /*private*/ SFFloat linearVelocityY, linearVelocityYDefault;
  /*private*/ SFFloat linearVelocityZ, linearVelocityZDefault;
  /*private*/ SFFloat linearAccelerationX, linearAccelerationXDefault;
  /*private*/ SFFloat linearAccelerationY, linearAccelerationYDefault;
  /*private*/ SFFloat linearAccelerationZ, linearAccelerationZDefault;
  /*private*/ String  marking;
  /*private*/ String  multicastRelayHost;
  /*private*/ SFInt32   multicastRelayPort, multicastRelayPortDefault;
  /*private*/ SFInt32   munitionApplicationID, munitionApplicationIDDefault;
  /*private*/ SFFloat munitionEndPointX, munitionEndPointXDefault;
  /*private*/ SFFloat munitionEndPointY, munitionEndPointYDefault;
  /*private*/ SFFloat munitionEndPointZ, munitionEndPointZDefault;
  /*private*/ SFInt32   munitionEntityID, munitionEntityIDDefault;
  /*private*/ SFInt32   munitionQuantity, munitionQuantityDefault;
  /*private*/ SFInt32   munitionSiteID, munitionSiteIDDefault;
  /*private*/ SFFloat munitionStartPointX, munitionStartPointXDefault;
  /*private*/ SFFloat munitionStartPointY, munitionStartPointYDefault;
  /*private*/ SFFloat munitionStartPointZ, munitionStartPointZDefault;
  /*private*/ String  networkMode;
  /*private*/ SFInt32   port, portDefault;
  /*private*/ SFFloat readInterval, readIntervalDefault; // SFTime
  /*private*/ SFInt32   siteID, siteIDDefault;
  /*private*/ SFInt32   warhead, warheadDefault;
  /*private*/ SFFloat writeInterval, writeIntervalDefault; // SFTime
  /*private*/ boolean rtpHeaderExpected, rtpHeaderExpecteDefault;

  public ESPDUTRANSFORM()
  {
  }

  @Override
  public String getElementName()
  {
    return ESPDUTRANSFORM_ELNAME;
  }
  
  @Override
  public void initialize()
  {
    /*String*/  address = ESPDUTRANSFORM_ATTR_ADDRESS_DFLT;
    /*SFInt32*/   appID = appIDDefault = new SFInt32(ESPDUTRANSFORM_ATTR_APPLICATIONID_DFLT,0,65535);
    /*SFInt32*/   articulationParameterCount = articulationParameterCountDefault = new SFInt32(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCOUNT_DFLT,0,78);

    /*SFInt32[]*/ articulationParameterDesignatorArray = articulationParameterDesignatorArrayDefault =
        handleIntArray(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERDESIGNATORARRAY_DFLT);

    /*SFInt32[]*/ articulationParameterChangeIndicatorArray = articulationParameterChangeIndicatorArrayDefault =
        handleIntArray(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_DFLT);
    /*SFInt32[]*/ articulationParameterIdPartAttachedToArray = articulationParameterIdPartAttachedToArrayDefault =
        handleIntArray(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERIDPARTATTACHEDTORARRAY_DFLT);

    /*SFInt32[]*/ articulationParameterTypeArray = articulationParameterTypeArrayDefault =
        handleIntArray(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERTYPEARRAY_DFLT);
    /*SFFloat[]*/ articulationParameterArray = articulationParameterArrayDefault =
        handleFloatArray(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERARRAY_DFLT);

    String[] fa = parse3(ESPDUTRANSFORM_ATTR_CENTER_DFLT);
    /*SFFloat*/ centerX = centerXDefault = new SFFloat(fa[0],null,null);
    /*SFFloat*/ centerY = centerYDefault = new SFFloat(fa[1],null,null);
    /*SFFloat*/ centerZ = centerZDefault = new SFFloat(fa[2],null,null);

    /*SFInt32*/   collisionType               = collisionTypeDefault               = new SFInt32(ESPDUTRANSFORM_ATTR_COLLISIONTYPE_DFLT,0,255);
    /*SFInt32*/   deadReckoning               = deadReckoningDefault               = new SFInt32(ESPDUTRANSFORM_ATTR_DEADRECKONING_DFLT,0,255);
    fa = parse3(ESPDUTRANSFORM_ATTR_DETONATIONLOCATION_DFLT);
    /*SFFloat*/ detonationLocationX         = detonationLocationXDefault         = new SFFloat(fa[0],null,null);
    /*SFFloat*/ detonationLocationY         = detonationLocationYDefault         = new SFFloat(fa[1],null,null);
    /*SFFloat*/ detonationLocationZ         = detonationLocationZDefault         = new SFFloat(fa[2],null,null);
    fa = parse3(ESPDUTRANSFORM_ATTR_DETONATIONRELATIVELOCATION_DFLT);
    /*SFFloat*/ detonationRelativeLocationX = detonationRelativeLocationXDefault = new SFFloat(fa[0],null,null);
    /*SFFloat*/ detonationRelativeLocationY = detonationRelativeLocationYDefault = new SFFloat(fa[1],null,null);
    /*SFFloat*/ detonationRelativeLocationZ = detonationRelativeLocationZDefault = new SFFloat(fa[2],null,null);

    /*SFInt32*/   detonationResult   = detonationResultDefault   = new SFInt32(ESPDUTRANSFORM_ATTR_DETONATIONRESULT_DFLT,0,255);
    /*boolean*/ enabled            = enabledDefault            = Boolean.parseBoolean(ESPDUTRANSFORM_ATTR_ENABLED_DFLT);
    /*SFInt32*/   entityCategory     = entityCategoryDefault     = new SFInt32(ESPDUTRANSFORM_ATTR_ENTITYCATEGORY_DFLT,0,255);
    /*SFInt32*/   entityCountry      = entityCountryDefault      = new SFInt32(ESPDUTRANSFORM_ATTR_ENTITYCOUNTRY_DFLT,0,65535);
    /*SFInt32*/   entityDomain       = entityDomainDefault       = new SFInt32(ESPDUTRANSFORM_ATTR_ENTITYDOMAIN_DFLT,0,255);
    /*SFInt32*/   entityExtra        = entityExtraDefault        = new SFInt32(ESPDUTRANSFORM_ATTR_ENTITYEXTRA_DFLT,0,255);
    /*SFInt32*/   entityID           = entityIDDefault           = new SFInt32(ESPDUTRANSFORM_ATTR_ENTITYID_DFLT,0,65535);
    /*SFInt32*/   entityKind         = entityKindDefault         = new SFInt32(ESPDUTRANSFORM_ATTR_ENTITYKIND_DFLT,0,255);
    /*SFInt32*/   entitySpecific     = entitySpecificDefault     = new SFInt32(ESPDUTRANSFORM_ATTR_ENTITYSPECIFIC_DFLT,0,255);
    /*SFInt32*/   entitySubCategory  = entitySubCategoryDefault  = new SFInt32(ESPDUTRANSFORM_ATTR_ENTITYSUBCATEGORY_DFLT,0,255);
    /*SFInt32*/   eventApplicationID = eventApplicationIDDefault = new SFInt32(ESPDUTRANSFORM_ATTR_EVENTAPPLICATIONID_DFLT,0,65535);
    /*SFInt32*/   eventEntityID      = eventEntityIDDefault      = new SFInt32(ESPDUTRANSFORM_ATTR_EVENTENTITYID_DFLT,0,65535);
    /*SFInt32*/   eventNumber        = eventNumberDefault        = new SFInt32(ESPDUTRANSFORM_ATTR_EVENTNUMBER_DFLT,0,65535);
    /*SFInt32*/   eventSiteID        = eventSiteIDDefault        = new SFInt32(ESPDUTRANSFORM_ATTR_EVENTSITEID_DFLT,0,65535);
    /*boolean*/ fired1             = fired1Default             = Boolean.parseBoolean(ESPDUTRANSFORM_ATTR_FIRED1_DFLT);
    /*boolean*/ fired2             = fired2Default             = Boolean.parseBoolean(ESPDUTRANSFORM_ATTR_FIRED2_DFLT);
    /*SFInt32*/   fireMissionIndex   = fireMissionIndexDefault   = new SFInt32(ESPDUTRANSFORM_ATTR_FIREMISSIONINDEX_DFLT,0,65535);
    /*SFFloat*/ firingRange        = firingRangeDefault        = new SFFloat(ESPDUTRANSFORM_ATTR_FIRINGRANGE_DFLT,0f,null);
    /*SFInt32*/   firingRate         = firingRateDefault         = new SFInt32(ESPDUTRANSFORM_ATTR_FIRINGRATE_DFLT,0,65535);
    /*SFInt32*/   forceID            = forceIDDefault            = new SFInt32(ESPDUTRANSFORM_ATTR_FORCEID_DFLT,0,255);
    /*SFInt32*/   fuse               = fuseDefault               = new SFInt32(ESPDUTRANSFORM_ATTR_FUSE_DFLT,0,65535);

    fa = parse3(ESPDUTRANSFORM_ATTR_LINEARVELOCITY_DFLT);
    /*SFFloat*/ linearVelocityX       = linearVelocityXDefault       = new SFFloat(fa[0],null,null);
    /*SFFloat*/ linearVelocityY       = linearVelocityYDefault       = new SFFloat(fa[1],null,null);
    /*SFFloat*/ linearVelocityZ       = linearVelocityZDefault       = new SFFloat(fa[2],null,null);
    fa = parse3(ESPDUTRANSFORM_ATTR_LINEARACCELERATION_DFLT);
    /*SFFloat*/ linearAccelerationX   = linearAccelerationXDefault   = new SFFloat(fa[0],null,null);
    /*SFFloat*/ linearAccelerationY   = linearAccelerationYDefault   = new SFFloat(fa[1],null,null);
    /*SFFloat*/ linearAccelerationZ   = linearAccelerationZDefault   = new SFFloat(fa[2],null,null);
    /*String*/  marking = ESPDUTRANSFORM_ATTR_MARKING_DFLT;
    /*String*/  multicastRelayHost    = ESPDUTRANSFORM_ATTR_MULTICASTRELAYHOST_DFLT;
    /*SFInt32*/   multicastRelayPort    = multicastRelayPortDefault    = new SFInt32(ESPDUTRANSFORM_ATTR_MULTICASTRELAYPORT_DFLT,0,null);
    /*SFInt32*/   munitionApplicationID = munitionApplicationIDDefault = new SFInt32(ESPDUTRANSFORM_ATTR_MUNITIONAPPLICATIONID_DFLT,0,65535);

    fa = parse3(ESPDUTRANSFORM_ATTR_MUNITIONENDPOINT_DFLT);
    /*SFFloat*/ munitionEndPointX   = munitionEndPointXDefault   = new SFFloat(fa[0],null,null);
    /*SFFloat*/ munitionEndPointY   = munitionEndPointYDefault   = new SFFloat(fa[1],null,null);
    /*SFFloat*/ munitionEndPointZ   = munitionEndPointZDefault   = new SFFloat(fa[2],null,null);
    /*SFInt32*/   munitionEntityID    = munitionEntityIDDefault    = new SFInt32(ESPDUTRANSFORM_ATTR_MUNITIONENTITYID_DFLT,0,65535);
    /*SFInt32*/   munitionQuantity    = munitionQuantityDefault    = new SFInt32(ESPDUTRANSFORM_ATTR_MUNITIONQUANTITY_DFLT,0,65535);
    /*SFInt32*/   munitionSiteID      = munitionSiteIDDefault      = new SFInt32(ESPDUTRANSFORM_ATTR_MUNITIONSITEID_DFLT,0,65535);
    fa = parse3(ESPDUTRANSFORM_ATTR_MUNITIONSTARTPOINT_DFLT);
    /*SFFloat*/ munitionStartPointX = munitionStartPointXDefault = new SFFloat(fa[0],null,null);
    /*SFFloat*/ munitionStartPointY = munitionStartPointYDefault = new SFFloat(fa[1],null,null);
    /*SFFloat*/ munitionStartPointZ = munitionStartPointZDefault = new SFFloat(fa[2],null,null);

    /*String*/  networkMode = ESPDUTRANSFORM_ATTR_NETWORKMODE_DFLT;
    /*SFInt32*/   port         = portDefault         = new SFInt32(ESPDUTRANSFORM_ATTR_PORT_DFLT,0,65535);
    /*SFFloat*/ readInterval = readIntervalDefault = new SFFloat(ESPDUTRANSFORM_ATTR_READINTERVAL_DFLT,0f,null);

    fa = parse4(ESPDUTRANSFORM_ATTR_ROTATION_DFLT);
    /*SFFloat*/ rotationX = rotationXDefault = new SFFloat(fa[0],null,null);
    /*SFFloat*/ rotationY = rotationYDefault = new SFFloat(fa[1],null,null);
    /*SFFloat*/ rotationZ = rotationZDefault = new SFFloat(fa[2],null,null);
    /*SFFloat*/ rotationAngle = rotationAngleDefault = new SFFloat(fa[3],null,null);
    fa = parse3(ESPDUTRANSFORM_ATTR_SCALE_DFLT);
    /*SFFloat*/ scaleX = scaleXDefault       = new SFFloat(fa[0],null,null);
    /*SFFloat*/ scaleY = scaleYDefault       = new SFFloat(fa[1],null,null);
    /*SFFloat*/ scaleZ = scaleZDefault       = new SFFloat(fa[2],null,null);
    fa = parse4(ESPDUTRANSFORM_ATTR_SCALEORIENTATION_DFLT);
    /*SFFloat*/ scaleOrientationX               = scaleOrientation0Default = new SFFloat(fa[0],null,null);
    /*SFFloat*/ scaleOrientationY               = scaleOrientation1Default = new SFFloat(fa[1],null,null);
    /*SFFloat*/ scaleOrientationZ               = scaleOrientation2Default = new SFFloat(fa[2],null,null);
    /*SFFloat*/ scaleOrientationAngle               = scaleOrientation3Default = new SFFloat(fa[3],null,null);
    /*SFInt32*/   siteID                          = siteIDDefault            = new SFInt32(ESPDUTRANSFORM_ATTR_SITEID_DFLT,0,65535);

    fa = parse3(ESPDUTRANSFORM_ATTR_TRANSLATION_DFLT);
    /*SFFloat*/ translationX  = translationXDefault  = new SFFloat(fa[0],null,null);
    /*SFFloat*/ translationY  = translationYDefault  = new SFFloat(fa[1],null,null);
    /*SFFloat*/ translationZ  = translationZDefault  = new SFFloat(fa[2],null,null);
    /*SFInt32*/   warhead       = warheadDefault       = new SFInt32(ESPDUTRANSFORM_ATTR_WARHEAD_DFLT,0,65535);
    /*SFFloat*/ writeInterval = writeIntervalDefault = new SFFloat(ESPDUTRANSFORM_ATTR_WRITEINTERVAL_DFLT,0f,null);
    fa = parse3(ESPDUTRANSFORM_ATTR_BBOXCENTER_DFLT);
    /*SFFloat*/ bboxCenterX   = bboxCenterXDefault   = new SFFloat(fa[0],null,null);
    /*SFFloat*/ bboxCenterY   = bboxCenterYDefault   = new SFFloat(fa[1],null,null);
    /*SFFloat*/ bboxCenterZ   = bboxCenterZDefault   = new SFFloat(fa[2],null,null);
    fa = parse3(ESPDUTRANSFORM_ATTR_BBOXSIZE_DFLT);
    /*SFFloat*/ bboxSizeX     = bboxSizeXDefault     = new SFFloat(fa[0],0f,null,true);
    /*SFFloat*/ bboxSizeY     = bboxSizeYDefault     = new SFFloat(fa[1],0f,null,true);
    /*SFFloat*/ bboxSizeZ     = bboxSizeZDefault     = new SFFloat(fa[2],0f,null,true);

    /*boolean*/ rtpHeaderExpected = rtpHeaderExpecteDefault = Boolean.parseBoolean(ESPDUTRANSFORM_ATTR_RTPHEADEREXPECTED_DFLT);
  }

  @Override
  public void initializeFromJdom(org.jdom.Element root, JTextComponent comp)
  {
    super.initializeFromJdom(root, comp);

    org.jdom.Attribute attr;
    String[] sa;

    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ADDRESS_NAME);
    if (attr != null)
      address = attr.getValue();
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_APPLICATIONID_NAME);
    if (attr != null)
      appID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCOUNT_NAME);
    if (attr != null)
      articulationParameterCount = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERDESIGNATORARRAY_NAME);
    if (attr != null)
      articulationParameterDesignatorArray = handleIntArray(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_NAME);
    if (attr != null)
      articulationParameterChangeIndicatorArray = handleIntArray(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERIDPARTATTACHEDTORARRAY_NAME);
    if (attr != null)
      articulationParameterIdPartAttachedToArray = handleIntArray(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERTYPEARRAY_NAME);
    if (attr != null)
      articulationParameterTypeArray = handleIntArray(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERARRAY_NAME);
    if (attr != null)
      articulationParameterArray = handleFloatArray(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_CENTER_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      centerX = new SFFloat(sa[0]);
      centerY = new SFFloat(sa[1]);
      centerZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_COLLISIONTYPE_NAME);
    if (attr != null)
      collisionType = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_DEADRECKONING_NAME);
    if (attr != null)
      deadReckoning = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_DETONATIONLOCATION_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      detonationLocationX = new SFFloat(sa[0]);
      detonationLocationY = new SFFloat(sa[1]);
      detonationLocationZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_DETONATIONRELATIVELOCATION_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      detonationRelativeLocationX = new SFFloat(sa[0]);
      detonationRelativeLocationY = new SFFloat(sa[1]);
      detonationRelativeLocationZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_DETONATIONRESULT_NAME);
    if (attr != null)
      detonationResult = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ENABLED_NAME);
    if (attr != null)
      enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ENTITYCATEGORY_NAME);
    if (attr != null)
      entityCategory = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ENTITYCOUNTRY_NAME);
    if (attr != null)
      entityCountry = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ENTITYDOMAIN_NAME);
    if (attr != null)
      entityDomain = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ENTITYEXTRA_NAME);
    if (attr != null)
      entityExtra = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ENTITYID_NAME);
    if (attr != null)
      entityID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ENTITYKIND_NAME);
    if (attr != null)
      entityKind = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ENTITYSPECIFIC_NAME);
    if (attr != null)
      entitySpecific = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ENTITYSUBCATEGORY_NAME);
    if (attr != null)
      entitySubCategory = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_EVENTAPPLICATIONID_NAME);
    if (attr != null)
      eventApplicationID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_EVENTENTITYID_NAME);
    if (attr != null)
      eventEntityID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_EVENTNUMBER_NAME);
    if (attr != null)
      eventNumber = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_EVENTSITEID_NAME);
    if (attr != null)
      eventSiteID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_FIRED1_NAME);
    if (attr != null)
      fired1 = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_FIRED2_NAME);
    if (attr != null)
      fired2 = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_FIREMISSIONINDEX_NAME);
    if (attr != null)
      fireMissionIndex = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_FIRINGRANGE_NAME);
    if (attr != null)
      firingRange = new SFFloat(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_FIRINGRATE_NAME);
    if (attr != null)
      firingRate = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_FORCEID_NAME);
    if (attr != null)
      forceID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_FUSE_NAME);
    if (attr != null)
      fuse = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_LINEARVELOCITY_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      linearVelocityX = new SFFloat(sa[0]);
      linearVelocityY = new SFFloat(sa[1]);
      linearVelocityZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_LINEARACCELERATION_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      linearAccelerationX = new SFFloat(sa[0]);
      linearAccelerationY = new SFFloat(sa[1]);
      linearAccelerationZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_MARKING_NAME);
    if (attr != null)
      marking = attr.getValue();
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_MULTICASTRELAYHOST_NAME);
    if (attr != null)
      multicastRelayHost = attr.getValue();
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_MULTICASTRELAYPORT_NAME);
    if (attr != null)
      multicastRelayPort = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_MUNITIONAPPLICATIONID_NAME);
    if (attr != null)
      munitionApplicationID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_MUNITIONENDPOINT_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      munitionEndPointX = new SFFloat(sa[0]);
      munitionEndPointY = new SFFloat(sa[1]);
      munitionEndPointZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_MUNITIONENTITYID_NAME);
    if (attr != null)
      munitionEntityID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_MUNITIONQUANTITY_NAME);
    if (attr != null)
      munitionQuantity = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_MUNITIONSITEID_NAME);
    if (attr != null)
      munitionSiteID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_MUNITIONSTARTPOINT_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      munitionStartPointX = new SFFloat(sa[0]);
      munitionStartPointY = new SFFloat(sa[1]);
      munitionStartPointZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_NETWORKMODE_NAME);
    if (attr != null)
      networkMode = attr.getValue();
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_PORT_NAME);
    if (attr != null)
      port = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_READINTERVAL_NAME);
    if (attr != null)
      readInterval = new SFFloat(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ROTATION_NAME);
    if (attr != null) {
      sa = parse4(attr.getValue());
      rotationX = new SFFloat(sa[0]);
      rotationY = new SFFloat(sa[1]);
      rotationZ = new SFFloat(sa[2]);
      rotationAngle = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_SCALE_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      scaleX = new SFFloat(sa[0]);
      scaleY = new SFFloat(sa[1]);
      scaleZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_SCALEORIENTATION_NAME);
    if (attr != null) {
      sa = parse4(attr.getValue());
      scaleOrientationX = new SFFloat(sa[0]);
      scaleOrientationY = new SFFloat(sa[1]);
      scaleOrientationZ = new SFFloat(sa[2]);
      scaleOrientationAngle = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_SITEID_NAME);
    if (attr != null)
      siteID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_TRANSLATION_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      translationX = new SFFloat(sa[0]);
      translationY = new SFFloat(sa[1]);
      translationZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_WARHEAD_NAME);
    if (attr != null) {
      warhead = new SFInt32(attr.getValue());
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_WRITEINTERVAL_NAME);
    if (attr != null)
      writeInterval = new SFFloat(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_BBOXCENTER_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      bboxCenterX = new SFFloat(sa[0]);
      bboxCenterY = new SFFloat(sa[1]);
      bboxCenterZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_BBOXSIZE_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      bboxSizeX = new SFFloat(sa[0]);
      bboxSizeY = new SFFloat(sa[1]);
      bboxSizeZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_RTPHEADEREXPECTED_NAME);
    if (attr != null)
      rtpHeaderExpected = Boolean.parseBoolean(attr.getValue());
}

  @Override
  public String createAttributes()
  {
    StringBuilder sb = new StringBuilder();
    int sblen=0;
    String newLineString = "\n               ";  //15 spaces since the word ESPDUTransform is at least that long

    if(ESPDUTRANSFORM_ATTR_ADDRESS_REQD || !address.equals(ESPDUTRANSFORM_ATTR_ADDRESS_DFLT)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ADDRESS_NAME);
      sb.append("='");
      sb.append(address);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_APPLICATIONID_REQD || !appID.equals(appIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_APPLICATIONID_NAME);
      sb.append("='");
      sb.append(appID);
      sb.append("'");
    }

    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}

    if(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCOUNT_REQD || !articulationParameterCount.equals(articulationParameterCountDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCOUNT_NAME);
      sb.append("='");
      sb.append(articulationParameterCount);
      sb.append("'");
    }

    if(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERDESIGNATORARRAY_REQD || !arraysIdenticalOrNull(articulationParameterDesignatorArray,articulationParameterDesignatorArrayDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERDESIGNATORARRAY_NAME);
      sb.append("='");
      sb.append(formatIntArray(articulationParameterDesignatorArray));
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_REQD || !arraysIdenticalOrNull(articulationParameterChangeIndicatorArray,articulationParameterChangeIndicatorArrayDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_NAME);
      sb.append("='");
      sb.append(formatIntArray(articulationParameterChangeIndicatorArray));
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_REQD || !arraysIdenticalOrNull(articulationParameterIdPartAttachedToArray,articulationParameterIdPartAttachedToArrayDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERIDPARTATTACHEDTORARRAY_NAME);
      sb.append("='");
      sb.append(formatIntArray(articulationParameterIdPartAttachedToArray));
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_REQD || !arraysIdenticalOrNull(articulationParameterTypeArray,articulationParameterTypeArrayDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERTYPEARRAY_NAME);
      sb.append("='");
      sb.append(formatIntArray(articulationParameterTypeArray));
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_REQD || !arraysIdenticalOrNull(articulationParameterArray,articulationParameterArrayDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERARRAY_NAME);
      sb.append("='");
      sb.append(formatFloatArray(articulationParameterArray));
      sb.append("'");
    }

    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}

    if(ESPDUTRANSFORM_ATTR_CENTER_REQD ||
        (!centerX.equals(centerXDefault) ||
         !centerY.equals(centerYDefault) ||
         !centerZ.equals(centerZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_CENTER_NAME);
      sb.append("='");
      sb.append(centerX);
      sb.append(" ");
      sb.append(centerY);
      sb.append(" ");
      sb.append(centerZ);
      sb.append("'");
    }

    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}

    if(ESPDUTRANSFORM_ATTR_COLLISIONTYPE_REQD || !collisionType.equals(collisionTypeDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_COLLISIONTYPE_NAME);
      sb.append("='");
      sb.append(collisionType);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_DEADRECKONING_REQD || !deadReckoning.equals(deadReckoningDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_DEADRECKONING_NAME);
      sb.append("='");
      sb.append(deadReckoning);
      sb.append("'");
    }

    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}

    if(ESPDUTRANSFORM_ATTR_DETONATIONLOCATION_REQD ||
        (!detonationLocationX.equals(detonationLocationXDefault) ||
         !detonationLocationY.equals(detonationLocationYDefault) ||
         !detonationLocationZ.equals(detonationLocationZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_DETONATIONLOCATION_NAME);
      sb.append("='");
      sb.append(detonationLocationX);
      sb.append(" ");
      sb.append(detonationLocationY);
      sb.append(" ");
      sb.append(detonationLocationZ);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_DETONATIONRELATIVELOCATION_REQD ||
       (!detonationRelativeLocationX.equals(detonationRelativeLocationXDefault) ||
        !detonationRelativeLocationY.equals(detonationRelativeLocationYDefault) ||
        !detonationRelativeLocationZ.equals(detonationRelativeLocationZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_DETONATIONRELATIVELOCATION_NAME);
      sb.append("='");
      sb.append(detonationRelativeLocationX);
      sb.append(" ");
      sb.append(detonationRelativeLocationY);
      sb.append(" ");
      sb.append(detonationRelativeLocationZ);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_DETONATIONRESULT_REQD || !detonationResult.equals(detonationResultDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_DETONATIONRESULT_NAME);
      sb.append("='");
      sb.append(detonationResult);
      sb.append("'");
    }

    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}

    if(ESPDUTRANSFORM_ATTR_ENABLED_REQD || (enabled != enabledDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(enabled);
      sb.append("'");
    }

    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}

    if(ESPDUTRANSFORM_ATTR_ENTITYCATEGORY_REQD || !entityCategory.equals(entityCategoryDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENTITYCATEGORY_NAME);
      sb.append("='");
      sb.append(entityCategory);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ENTITYCOUNTRY_REQD || !entityCountry.equals(entityCountryDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENTITYCOUNTRY_NAME);
      sb.append("='");
      sb.append(entityCountry);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ENTITYDOMAIN_REQD || !entityDomain.equals(entityDomainDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENTITYDOMAIN_NAME);
      sb.append("='");
      sb.append(entityDomain);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ENTITYEXTRA_REQD || !entityExtra.equals(entityExtraDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENTITYEXTRA_NAME);
      sb.append("='");
      sb.append(entityExtra);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ENTITYID_REQD || !entityID.equals(entityIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENTITYID_NAME);
      sb.append("='");
      sb.append(entityID);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ENTITYKIND_REQD || !entityKind.equals(entityKindDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENTITYKIND_NAME);
      sb.append("='");
      sb.append(entityKind);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ENTITYSPECIFIC_REQD || !entitySpecific.equals(entitySpecificDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENTITYSPECIFIC_NAME);
      sb.append("='");
      sb.append(entitySpecific);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ENTITYSUBCATEGORY_REQD || !entitySubCategory.equals(entitySubCategoryDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENTITYSUBCATEGORY_NAME);
      sb.append("='");
      sb.append(entitySubCategory);
      sb.append("'");
    }

    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}

    if(ESPDUTRANSFORM_ATTR_EVENTAPPLICATIONID_REQD || !eventApplicationID.equals(eventApplicationIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_EVENTAPPLICATIONID_NAME);
      sb.append("='");
      sb.append(eventApplicationID);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_EVENTENTITYID_REQD || !eventEntityID.equals(eventEntityIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_EVENTENTITYID_NAME);
      sb.append("='");
      sb.append(eventEntityID);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_EVENTNUMBER_REQD || !eventNumber.equals(eventNumberDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_EVENTNUMBER_NAME);
      sb.append("='");
      sb.append(eventNumber);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_EVENTSITEID_REQD || !eventSiteID.equals(eventSiteIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_EVENTSITEID_NAME);
      sb.append("='");
      sb.append(eventSiteID);
      sb.append("'");
    }

    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}

    if(ESPDUTRANSFORM_ATTR_FIRED1_REQD || (fired1 != fired1Default)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_FIRED1_NAME);
      sb.append("='");
      sb.append(fired1);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_FIRED2_REQD || (fired2 != fired2Default)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_FIRED2_NAME);
      sb.append("='");
      sb.append(fired2);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_FIREMISSIONINDEX_REQD || !fireMissionIndex.equals(fireMissionIndexDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_FIREMISSIONINDEX_NAME);
      sb.append("='");
      sb.append(fireMissionIndex);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_FIRINGRANGE_REQD || !firingRange.equals(firingRangeDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_FIRINGRANGE_NAME);
      sb.append("='");
      sb.append(firingRange);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_FIRINGRATE_REQD || !firingRate.equals(firingRateDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_FIRINGRATE_NAME);
      sb.append("='");
      sb.append(firingRate);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_FORCEID_REQD || !forceID.equals(forceIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_FORCEID_NAME);
      sb.append("='");
      sb.append(forceID);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_FUSE_REQD || !fuse.equals(fuseDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_FUSE_NAME);
      sb.append("='");
      sb.append(fuse);
      sb.append("'");
    }

    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}

    if(ESPDUTRANSFORM_ATTR_LINEARVELOCITY_REQD ||
        (!linearVelocityX.equals(linearVelocityXDefault) ||
         !linearVelocityY.equals(linearVelocityYDefault) ||
         !linearVelocityZ.equals(linearVelocityZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_LINEARVELOCITY_NAME);
      sb.append("='");
      sb.append(linearVelocityX);
      sb.append(" ");
      sb.append(linearVelocityY);
      sb.append(" ");
      sb.append(linearVelocityZ);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_LINEARACCELERATION_REQD ||
        (!linearAccelerationX.equals(linearAccelerationXDefault) ||
         !linearAccelerationY.equals(linearAccelerationYDefault) ||
         !linearAccelerationZ.equals(linearAccelerationZDefault))) {

      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_LINEARACCELERATION_NAME);
      sb.append("='");
      sb.append(linearAccelerationX);
      sb.append(" ");
      sb.append(linearAccelerationY);
      sb.append(" ");
      sb.append(linearAccelerationZ);
      sb.append("'");
    }

    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}

    if(ESPDUTRANSFORM_ATTR_MARKING_REQD || !marking.equals(ESPDUTRANSFORM_ATTR_MARKING_DFLT)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MARKING_NAME);
      sb.append("='");
      sb.append(escapeXmlCharacters(marking));
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_MULTICASTRELAYHOST_REQD || !multicastRelayHost.equals(ESPDUTRANSFORM_ATTR_MULTICASTRELAYHOST_DFLT)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MULTICASTRELAYHOST_NAME);
      sb.append("='");
      sb.append(multicastRelayHost);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_MULTICASTRELAYPORT_REQD || !multicastRelayPort.equals(multicastRelayPortDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MULTICASTRELAYPORT_NAME);
      sb.append("='");
      sb.append(multicastRelayPort);
      sb.append("'");
    }

    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}

    if(ESPDUTRANSFORM_ATTR_MUNITIONAPPLICATIONID_REQD || !munitionApplicationID.equals(munitionApplicationIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MUNITIONAPPLICATIONID_NAME);
      sb.append("='");
      sb.append(munitionApplicationID);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_MUNITIONENDPOINT_REQD ||
      (!munitionEndPointX.equals(munitionEndPointXDefault) ||
       !munitionEndPointY.equals(munitionEndPointYDefault) ||
       !munitionEndPointZ.equals(munitionEndPointZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MUNITIONENDPOINT_NAME);
      sb.append("='");
      sb.append(munitionEndPointX);
      sb.append(" ");
      sb.append(munitionEndPointY);
      sb.append(" ");
      sb.append(munitionEndPointZ);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_MUNITIONENTITYID_REQD || !munitionEntityID.equals(munitionEntityIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MUNITIONENTITYID_NAME);
      sb.append("='");
      sb.append(munitionEntityID);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_MUNITIONQUANTITY_REQD || !munitionQuantity.equals(munitionQuantityDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MUNITIONQUANTITY_NAME);
      sb.append("='");
      sb.append(munitionQuantity);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_MUNITIONSITEID_REQD || !munitionSiteID.equals(munitionSiteIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MUNITIONSITEID_NAME);
      sb.append("='");
      sb.append(munitionSiteID);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_MUNITIONSTARTPOINT_REQD ||
       (!munitionStartPointX.equals(munitionStartPointXDefault) ||
        !munitionStartPointY.equals(munitionStartPointYDefault) ||
        !munitionStartPointZ.equals(munitionStartPointZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MUNITIONSTARTPOINT_NAME);
      sb.append("='");
      sb.append(munitionStartPointX);
      sb.append(" ");
      sb.append(munitionStartPointY);
      sb.append(" ");
      sb.append(munitionStartPointZ);
      sb.append("'");
    }

    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}

    if(ESPDUTRANSFORM_ATTR_NETWORKMODE_REQD || !networkMode.equals(ESPDUTRANSFORM_ATTR_NETWORKMODE_DFLT)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_NETWORKMODE_NAME);
      sb.append("='");
      sb.append(networkMode);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_PORT_REQD || !port.equals(portDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_PORT_NAME);
      sb.append("='");
      sb.append(port);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_READINTERVAL_REQD || !readInterval.equals(readIntervalDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_READINTERVAL_NAME);
      sb.append("='");
      sb.append(readInterval);
      sb.append("'");
    }

    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}

    if(ESPDUTRANSFORM_ATTR_ROTATION_REQD ||
        (!rotationX.equals(rotationXDefault) ||
         !rotationY.equals(rotationYDefault) ||
         !rotationZ.equals(rotationZDefault) ||
         !rotationAngle.equals(rotationAngleDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ROTATION_NAME);
      sb.append("='");
      sb.append(rotationX);
      sb.append(" ");
      sb.append(rotationY);
      sb.append(" ");
      sb.append(rotationZ);
      sb.append(" ");
      sb.append(rotationAngle);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_SCALE_REQD ||
       (!scaleX.equals(scaleXDefault) ||
        !scaleY.equals(scaleYDefault) ||
        !scaleZ.equals(scaleZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_SCALE_NAME);
      sb.append("='");
      sb.append(scaleX);
      sb.append(" ");
      sb.append(scaleY);
      sb.append(" ");
      sb.append(scaleZ);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_SCALEORIENTATION_REQD ||
       (!scaleOrientationX.equals(scaleOrientation0Default) ||
        !scaleOrientationY.equals(scaleOrientation1Default) ||
        !scaleOrientationZ.equals(scaleOrientation2Default) ||
        !scaleOrientationAngle.equals(scaleOrientation3Default))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_SCALEORIENTATION_NAME);
      sb.append("='");
      sb.append(scaleOrientationX);
      sb.append(" ");
      sb.append(scaleOrientationY);
      sb.append(" ");
      sb.append(scaleOrientationZ);
      sb.append(" ");
      sb.append(scaleOrientationAngle);
      sb.append("'");
    }

    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}

    if(ESPDUTRANSFORM_ATTR_SITEID_REQD || !siteID.equals(siteIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_SITEID_NAME);
      sb.append("='");
      sb.append(siteID);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_TRANSLATION_REQD ||
       (!translationX.equals(translationXDefault) ||
        !translationY.equals(translationYDefault) ||
        !translationZ.equals(translationZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_TRANSLATION_NAME);
      sb.append("='");
      sb.append(translationX);
      sb.append(" ");
      sb.append(translationY);
      sb.append(" ");
      sb.append(translationZ);
      sb.append("'");
    }

    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}

    if(ESPDUTRANSFORM_ATTR_WARHEAD_REQD || !warhead.equals(warheadDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_WARHEAD_NAME);
      sb.append("='");
      sb.append(warhead);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_WRITEINTERVAL_REQD || !writeInterval.equals(writeIntervalDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_WRITEINTERVAL_NAME);
      sb.append("='");
      sb.append(writeInterval);
      sb.append("'");
    }

    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}

    if(ESPDUTRANSFORM_ATTR_BBOXCENTER_REQD ||
       (!bboxCenterX.equals(bboxCenterXDefault) ||
        !bboxCenterY.equals(bboxCenterYDefault) ||
        !bboxCenterZ.equals(bboxCenterZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_BBOXCENTER_NAME);
      sb.append("='");
      sb.append(bboxCenterX);
      sb.append(" ");
      sb.append(bboxCenterY);
      sb.append(" ");
      sb.append(bboxCenterZ);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_BBOXSIZE_REQD ||
       (!bboxSizeX.equals(bboxSizeXDefault) ||
        !bboxSizeY.equals(bboxSizeYDefault) ||
        !bboxSizeZ.equals(bboxSizeZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_BBOXSIZE_NAME);
      sb.append("='");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeX);
      sb.append(" ");
      sb.append(bboxSizeX);
      sb.append("'");
    }

    if(sb.length() > sblen) {
        sb.append(newLineString);
        sblen=sb.length();
    }

    if(ESPDUTRANSFORM_ATTR_RTPHEADEREXPECTED_REQD || (rtpHeaderExpected != rtpHeaderExpecteDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_RTPHEADEREXPECTED_NAME);
      sb.append("='");
      sb.append(rtpHeaderExpected);
      sb.append("'");
    }

    return sb.toString();

  }

  public String getAddress()
  {
    return address;
  }

  public void setAddress(String s)
  {
    address = s;
  }

  public String getAppID()
  {
    return appID.toString();
  }

  public void setAppID(String s)
  {
    appID = new SFInt32(s, 0,65535);
  }

  public String getArticulationParameterCount()
  {
    return articulationParameterCount.toString();
  }

  public void setArticulationParameterCount(String s)
  {
    articulationParameterCount = new SFInt32(s,0,78);
  }

  public String[] getArticulationParameterArray()
  {
    String[][] saa = this.makeStringArray(new SFFloat[][]{articulationParameterArray});
    return saa[0];
  }

  public void setArticulationParameterArray(String s[])
  {
    articulationParameterArray = parseToSFFloatArray(s);
  }

  public String[] getArticulationParameterChangeIndicatorArray()
  {
    String[][] saa = this.makeStringArray(new SFInt32[][]{articulationParameterChangeIndicatorArray});
    return saa[0];
  }

  public void setArticulationParameterChangeIndicatorArray(String s[])
  {
    this.articulationParameterChangeIndicatorArray = parseToSFIntArray(s);
  }

  public String[] getArticulationParameterDesignatorArray()
  {
    String[][] saa = this.makeStringArray(new SFInt32[][]{articulationParameterDesignatorArray});
    return saa[0];
  }

  public void setArticulationParameterDesignatorArray(String s[])
  {
    this.articulationParameterDesignatorArray = parseToSFIntArray(s);
  }

  public String[] getArticulationParameterIdPartAttachedToArray()
  {
    String[][] saa = this.makeStringArray(new SFInt32[][]{articulationParameterIdPartAttachedToArray});
    return saa[0];
  }

  public void setArticulationParameterIdPartAttachedToArray(String s[])
  {
    this.articulationParameterIdPartAttachedToArray = parseToSFIntArray(s);
  }

  public String[] getArticulationParameterTypeArray()
  {
    String[][] saa = this.makeStringArray(new SFInt32[][]{articulationParameterTypeArray});
    return saa[0];
  }

  public void setArticulationParameterTypeArray(String s[])
  {
    this.articulationParameterTypeArray = parseToSFIntArray(s);
  }

  public String getCollisionType()
  {
    return collisionType.toString();
  }

  public void setCollisionType(String s)
  {
    collisionType = new SFInt32(s,0,255);
  }

  public String getDeadReckoning()
  {
    return deadReckoning.toString();
  }

  public void setDeadReckoning(String s)
  {
    deadReckoning = new SFInt32(s,0,255);
  }

  public String getDetonationLocationX()
  {
    return detonationLocationX.toString();
  }

  public void setDetonationLocationX(String s)
  {
    detonationLocationX = new SFFloat(s,null,null);
  }

  public String getDetonationLocationY()
  {
    return detonationLocationY.toString();
  }

  public void setDetonationLocationY(String s)
  {
    detonationLocationY = new SFFloat(s,null,null);
  }

  public String getDetonationLocationZ()
  {
    return detonationLocationZ.toString();
  }

  public void setDetonationLocationZ(String s)
  {
    detonationLocationZ = new SFFloat(s,null,null);
  }

  public String getDetonationRelativeLocationX()
  {
    return detonationRelativeLocationX.toString();
  }

  public void setDetonationRelativeLocationX(String s)
  {
    detonationRelativeLocationX = new SFFloat(s,null,null);
  }

  public String getDetonationRelativeLocationY()
  {
    return detonationRelativeLocationY.toString();
  }

  public void setDetonationRelativeLocationY(String s)
  {
    detonationRelativeLocationY = new SFFloat(s,null,null);
  }

  public String getDetonationRelativeLocationZ()
  {
    return detonationRelativeLocationZ.toString();
  }

  public void setDetonationRelativeLocationZ(String s)
  {
    detonationRelativeLocationZ = new SFFloat(s,null,null);
  }

  public String getDetonationResult()
  {
    return detonationResult.toString();
  }

  public void setDetonationResult(String s)
  {
    detonationResult = new SFInt32(s,0,255);
  }

  public String isEnabled()
  {
    return ""+enabled;
  }

  public void setEnabled(String s)
  {
    enabled = Boolean.parseBoolean(s);
  }

  public String getEntityCategory()
  {
    return entityCategory.toString();
  }

  public void setEntityCategory(String s)
  {
    entityCategory = new SFInt32(s,0,255);
  }

  public String getEntityCountry()
  {
    return entityCountry.toString();
  }

  public void setEntityCountry(String s)
  {
    entityCountry = new SFInt32(s,0,65535);
  }

  public String getEntityDomain()
  {
    return entityDomain.toString();
  }

  public void setEntityDomain(String s)
  {
    entityDomain = new SFInt32(s,0,255);
  }

  public String getEntityExtra()
  {
    return entityExtra.toString();
  }

  public void setEntityExtra(String s)
  {
    entityExtra = new SFInt32(s,0,255);
  }

  public String getEntityID()
  {
    return entityID.toString();
  }

  public void setEntityID(String s)
  {
    entityID = new SFInt32(s,0,65535);
  }

  public String getEntityKind()
  {
    return entityKind.toString();
  }

  public void setEntityKind(String s)
  {
    entityKind = new SFInt32(s,0,255);
  }

  public String getEntitySpecific()
  {
    return entitySpecific.toString();
  }

  public void setEntitySpecific(String s)
  {
    entitySpecific = new SFInt32(s,0,255);
  }

  public String getEntitySubCategory()
  {
    return entitySubCategory.toString();
  }

  public void setEntitySubCategory(String s)
  {
    entitySubCategory = new SFInt32(s,0,255);
  }

  public String getEventApplicationID()
  {
    return eventApplicationID.toString();
  }

  public void setEventApplicationID(String s)
  {
    eventApplicationID = new SFInt32(s,0,65535);
  }

  public String getEventEntityID()
  {
    return eventEntityID.toString();
  }

  public void setEventEntityID(String s)
  {
    eventEntityID = new SFInt32(s,0,65535);
  }

  public String getEventNumber()
  {
    return eventNumber.toString();
  }

  public void setEventNumber(String s)
  {
    eventNumber = new SFInt32(s,0,65535);
  }

  public String getEventSiteID()
  {
    return eventSiteID.toString();
  }

  public void setEventSiteID(String s)
  {
    eventSiteID = new SFInt32(s,0,65535);
  }

  public String getFireMissionIndex()
  {
    return fireMissionIndex.toString();
  }

  public void setFireMissionIndex(String s)
  {
    fireMissionIndex = new SFInt32(s,0,65535);
  }

  public String isFired1()
  {
    return ""+fired1;
  }

  public void setFired1(String s)
  {
    fired1 = Boolean.parseBoolean(s);
  }

  public String isFired2()
  {
    return ""+fired2;
  }

  public void setFired2(String s)
  {
    fired2 = Boolean.parseBoolean(s);
  }

  public String getFiringRange()
  {
    return firingRange.toString();
  }

  public void setFiringRange(String s)
  {
    firingRange = new SFFloat(s,0f,null);
  }

  public String getFiringRate()
  {
    return firingRate.toString();
  }

  public void setFiringRate(String s)
  {
    firingRate = new SFInt32(s,0,65535);
  }

  public String getForceID()
  {
    return forceID.toString();
  }

  public void setForceID(String s)
  {
    forceID = new SFInt32(s,0,255);
  }

  public String getFuse()
  {
    return fuse.toString();
  }

  public void setFuse(String s)
  {
    fuse = new SFInt32(s,0,65535);
  }

  public String getLinearAccelerationX()
  {
    return linearAccelerationX.toString();
  }

  public void setLinearAccelerationX(String s)
  {
    linearAccelerationX = new SFFloat(s,null,null);
  }

  public String getLinearAccelerationY()
  {
    return linearAccelerationY.toString();
  }

  public void setLinearAccelerationY(String s)
  {
    linearAccelerationY = new SFFloat(s,null,null);
  }

  public String getLinearAccelerationZ()
  {
    return linearAccelerationZ.toString();
  }

  public void setLinearAccelerationZ(String s)
  {
    linearAccelerationZ = new SFFloat(s,null,null);
  }

  public String getLinearVelocityX()
  {
    return linearVelocityX.toString();
  }

  public void setLinearVelocityX(String s)
  {
    linearVelocityX = new SFFloat(s,null,null);
  }

  public String getLinearVelocityY()
  {
    return linearVelocityY.toString();
  }

  public void setLinearVelocityY(String s)
  {
    linearVelocityY = new SFFloat(s,null,null);
  }

  public String getLinearVelocityZ()
  {
    return linearVelocityZ.toString();
  }

  public void setLinearVelocityZ(String s)
  {
    linearVelocityZ = new SFFloat(s,null,null);
  }

  public String getMarking()
  {
    return marking;
  }

  public void setMarking(String s)
  {
    marking = s;
  }

  public String getMulticastRelayHost()
  {
    return multicastRelayHost;
  }

  public void setMulticastRelayHost(String s)
  {
    this.multicastRelayHost = s;
  }

  public String getMulticastRelayPort()
  {
    return multicastRelayPort.toString();
  }

  public void setMulticastRelayPort(String s)
  {
    multicastRelayPort = new SFInt32(s,0,null);
  }

  public String getMunitionApplicationID()
  {
    return munitionApplicationID.toString();
  }

  public void setMunitionApplicationID(String s)
  {
    munitionApplicationID = new SFInt32(s,0,65535);
  }

  public String getMunitionEndPointX()
  {
    return munitionEndPointX.toString();
  }

  public void setMunitionEndPointX(String s)
  {
    munitionEndPointX = new SFFloat(s,null,null);
  }

  public String getMunitionEndPointY()
  {
    return munitionEndPointY.toString();
  }

  public void setMunitionEndPointY(String s)
  {
    munitionEndPointY = new SFFloat(s,null,null);
  }

  public String getMunitionEndPointZ()
  {
    return munitionEndPointZ.toString();
  }

  public void setMunitionEndPointZ(String s)
  {
    munitionEndPointZ = new SFFloat(s,null,null);
  }

  public String getMunitionEntityID()
  {
    return munitionEntityID.toString();
  }

  public void setMunitionEntityID(String s)
  {
    munitionEntityID = new SFInt32(s,0,65535);
  }

  public String getMunitionQuantity()
  {
    return munitionQuantity.toString();
  }

  public void setMunitionQuantity(String s)
  {
    munitionQuantity = new SFInt32(s,0,65535);
  }

  public String getMunitionSiteID()
  {
    return munitionSiteID.toString();
  }

  public void setMunitionSiteID(String s)
  {
    munitionSiteID = new SFInt32(s,0,65535);
  }

  public String getMunitionStartPointX()
  {
    return munitionStartPointX.toString();
  }

  public void setMunitionStartPointX(String s)
  {
    munitionStartPointX = new SFFloat(s,null,null);
  }

  public String getMunitionStartPointY()
  {
    return munitionStartPointY.toString();
  }

  public void setMunitionStartPointY(String s)
  {
    munitionStartPointY = new SFFloat(s,null,null);
  }

  public String getMunitionStartPointZ()
  {
    return munitionStartPointZ.toString();
  }

  public void setMunitionStartPointZ(String s)
  {
    munitionStartPointZ = new SFFloat(s,null,null);
  }

  public String getNetworkMode()
  {
    return networkMode.trim();
  }

  public void setNetworkMode(String s)
  {
    networkMode = s;
  }

  public String getPort()
  {
    return port.toString();
  }

  public void setPort(String s)
  {
    port = new SFInt32(s,0,65535);
  }

  public String getReadInterval()
  {
    return readInterval.toString();
  }

  public void setReadInterval(String s)
  {
    readInterval = new SFFloat(s,0f,null);
  }

  public String isRtpHeaderExpected()
  {
    return ""+rtpHeaderExpected;
  }

  public void setRtpHeaderExpected(String s)
  {
    rtpHeaderExpected = Boolean.parseBoolean(s);
  }

  public String getSiteID()
  {
    return siteID.toString();
  }

  public void setSiteID(String s)
  {
    siteID = new SFInt32(s,0,65535);
  }
  
  public String getWarhead()
  {
    return warhead.toString();
  }

  public void setWarhead(String s)
  {
    warhead = new SFInt32(s,0,65535);
  }

  public String getWriteInterval()
  {
    return writeInterval.toString();
  }

  public void setWriteInterval(String s)
  {
    writeInterval = new SFFloat(s,0f,null);
  }
  
  // Utility functions
  private SFFloat[] handleFloatArray(String str)
  {
    String[] sa;
    if(str == null || str.length()<=0)
      sa = new String[]{}; // empty
    else
      sa = parseX(str);
    return parseToSFFloatArray(sa);
  }

  private SFInt32[] handleIntArray(String str)
  {
    String[] sa;
    if(str == null || str.length()<=0)
      sa = new String[]{}; // empty
    else
      sa = parseX(str);
    return parseToSFIntArray(sa);
  }

//  // Not a perfect check, default value comes in as "", which gets converted to NaN.
//  // The idea is that the default values in the SchemaData file are empty strings.  When a SFInt32
//  // gets built from an empty string, its value is Float.Nan, which gets rendered as "";
//  // But with the articulation parameters, if _any_ in the array is non-default, the whole
//  // array must be written into the file, and there is no standard for filling the x3d attribute
//  // array of strings with null-equivalents.  They get turned to zeros.  To-be-continued, perhaps.
//  private static boolean containsNonDefault(SFFloat[] sa)//, String dflt)
//  {
//    for(SFFloat si : sa)
//      if(!si.toString().equals("")){//!si.equals(Float.NaN)){
//        fillNullsWithZero(sa);
//        return true;
//      }
//    return false;
//  }
//  private static boolean containsNonDefault(SFInt32[] sa)//, String dflt)
//  {
//    for(SFInt32 si : sa)
//      if(!si.toString().equals("")){//!si.equals(Float.NaN)){
//        fillNullsWithZero(sa);
//        return true;
//      }
//    return false;
//  }
//
//  private static void fillNullsWithZero(SFFloat[] sa)
//  {
//    for (int i = 0; i < sa.length; i++) {
//      if (sa[i]==null || sa[i].toString().equals("")) //Float.NaN))
//        sa[i] = new SFFloat("0");
//    }
//  }
}