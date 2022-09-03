/*
* Copyright (c) 1995-2021 held by the author(s) .  All rights reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*
*  * Redistributions of source code must retain the above copyright
*       notice, this list of conditions and the following disclaimer.
*  * Redistributions in binary form must reproduce the above copyright
*       notice, this list of conditions and the following disclaimer
*       in the documentation and/or other materials provided with the
*       distribution.
*  * Neither the names of the Naval Postgraduate School (NPS)
*       Modeling Virtual Environments and Simulation (MOVES) Institute
*       (http://www.nps.edu and https://MovesInstitute.nps.edu)
*       nor the names of its contributors may be used to endorse or
*       promote products derived from this software without specific
*       prior written permission.
*
* THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
* "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
* LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS
* FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE
* COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT,
* INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING,
* BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
* LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
* CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT
* LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN
* ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
* POSSIBILITY OF SUCH DAMAGE.
*/
package org.web3d.x3d.palette.items;

import edu.nps.moves.dis7.enumerations.*;

import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import org.web3d.x3d.types.X3DPrimitiveTypes.SFFloat;
import org.web3d.x3d.types.X3DPrimitiveTypes.SFInt32;

import static org.web3d.x3d.types.X3DSchemaData.*;
import static org.web3d.x3d.types.X3DSchemaData4.*;

/**
 * EspduTransformHelper.java
 * Created on May 19, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author mike
 * @version $Id$
 */
/*public*/ class EspduTransformHelper
{
  /*public*/static void initialize(ESPDUTRANSFORM espduTransform)
  {
    /*String*/  espduTransform.address = ESPDUTRANSFORM_ATTR_ADDRESS_DFLT;	
    /*SFInt32*/   espduTransform.appID = espduTransform.appIDDefault = new SFInt32(ESPDUTRANSFORM_ATTR_APPLICATIONID_DFLT,0,65535);
    /*SFInt32*/   espduTransform.articulationParameterCount = espduTransform.articulationParameterCountDefault = new SFInt32(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCOUNT_DFLT,0,78);
    
    /*SFInt32[]*/ espduTransform.articulationParameterDesignatorArray = espduTransform.articulationParameterDesignatorArrayDefault = 
        handleIntArray(espduTransform,ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERDESIGNATORARRAY_DFLT);

    /*SFInt32[]*/ espduTransform.articulationParameterChangeIndicatorArray = espduTransform.articulationParameterChangeIndicatorArrayDefault =
        handleIntArray(espduTransform,ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_DFLT);
    /*SFInt32[]*/ espduTransform.articulationParameterIdPartAttachedToArray = espduTransform.articulationParameterIdPartAttachedToArrayDefault =
        handleIntArray(espduTransform,ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERIDPARTATTACHEDTORARRAY_DFLT);
    
    /*SFInt32[]*/ espduTransform.articulationParameterTypeArray = espduTransform.articulationParameterTypeArrayDefault =
        handleIntArray(espduTransform,ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERTYPEARRAY_DFLT);
    /*SFFloat[]*/ espduTransform.articulationParameterArray = espduTransform.articulationParameterArrayDefault =
        handleFloatArray(espduTransform,ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERARRAY_DFLT);

    String[] fa = parse3(ESPDUTRANSFORM_ATTR_CENTER_DFLT);
    /*SFFloat*/ espduTransform.centerX = espduTransform.centerXDefault = new SFFloat(fa[0],null,null);
    /*SFFloat*/ espduTransform.centerY = espduTransform.centerYDefault = new SFFloat(fa[1],null,null);
    /*SFFloat*/ espduTransform.centerZ = espduTransform.centerZDefault = new SFFloat(fa[2],null,null);
    
    /*SFInt32*/   espduTransform.collisionType               = espduTransform.collisionTypeDefault               = new SFInt32(ESPDUTRANSFORM_ATTR_COLLISIONTYPE_DFLT,0,255);
    /*SFInt32*/   espduTransform.deadReckoning               = espduTransform.deadReckoningDefault               = new SFInt32(ESPDUTRANSFORM_ATTR_DEADRECKONING_DFLT,0,255);
    fa = parse3(ESPDUTRANSFORM_ATTR_DETONATIONLOCATION_DFLT);
    /*SFFloat*/ espduTransform.detonationLocationX         = espduTransform.detonationLocationXDefault         = new SFFloat(fa[0],null,null);
    /*SFFloat*/ espduTransform.detonationLocationY         = espduTransform.detonationLocationYDefault         = new SFFloat(fa[1],null,null);
    /*SFFloat*/ espduTransform.detonationLocationZ         = espduTransform.detonationLocationZDefault         = new SFFloat(fa[2],null,null);
    fa = parse3(ESPDUTRANSFORM_ATTR_DETONATIONRELATIVELOCATION_DFLT);
    /*SFFloat*/ espduTransform.detonationRelativeLocationX = espduTransform.detonationRelativeLocationXDefault = new SFFloat(fa[0],null,null);
    /*SFFloat*/ espduTransform.detonationRelativeLocationY = espduTransform.detonationRelativeLocationYDefault = new SFFloat(fa[1],null,null);
    /*SFFloat*/ espduTransform.detonationRelativeLocationZ = espduTransform.detonationRelativeLocationZDefault = new SFFloat(fa[2],null,null);
    
    /*SFInt32*/   espduTransform.detonationResult   = espduTransform.detonationResultDefault   = new SFInt32(ESPDUTRANSFORM_ATTR_DETONATIONRESULT_DFLT,0,255);
    /*boolean*/ espduTransform.enabled            = espduTransform.enabledDefault            = Boolean.parseBoolean(ESPDUTRANSFORM_ATTR_ENABLED_DFLT);
    /*SFInt32*/   espduTransform.entityCategory     = espduTransform.entityCategoryDefault     = new SFInt32(ESPDUTRANSFORM_ATTR_ENTITYCATEGORY_DFLT,0,255);
    /*SFInt32*/   espduTransform.entityCountry      = espduTransform.entityCountryDefault      = new SFInt32(ESPDUTRANSFORM_ATTR_ENTITYCOUNTRY_DFLT,0,65535);
    /*SFInt32*/   espduTransform.entityDomain       = espduTransform.entityDomainDefault       = new SFInt32(ESPDUTRANSFORM_ATTR_ENTITYDOMAIN_DFLT,0,255);
    /*SFInt32*/   espduTransform.entityExtra        = espduTransform.entityExtraDefault        = new SFInt32(ESPDUTRANSFORM_ATTR_ENTITYEXTRA_DFLT,0,255);
    /*SFInt32*/   espduTransform.entityID           = espduTransform.entityIDDefault           = new SFInt32(ESPDUTRANSFORM_ATTR_ENTITYID_DFLT,0,65535);
    /*SFInt32*/   espduTransform.entityKind         = espduTransform.entityKindDefault         = new SFInt32(ESPDUTRANSFORM_ATTR_ENTITYKIND_DFLT,0,255);
    /*SFInt32*/   espduTransform.entitySpecific     = espduTransform.entitySpecificDefault     = new SFInt32(ESPDUTRANSFORM_ATTR_ENTITYSPECIFIC_DFLT,0,255);
    /*SFInt32*/   espduTransform.entitySubCategory  = espduTransform.entitySubCategoryDefault  = new SFInt32(ESPDUTRANSFORM_ATTR_ENTITYSUBCATEGORY_DFLT,0,255);
    /*SFInt32*/   espduTransform.eventApplicationID = espduTransform.eventApplicationIDDefault = new SFInt32(ESPDUTRANSFORM_ATTR_EVENTAPPLICATIONID_DFLT,0,65535);
    /*SFInt32*/   espduTransform.eventEntityID      = espduTransform.eventEntityIDDefault      = new SFInt32(ESPDUTRANSFORM_ATTR_EVENTENTITYID_DFLT,0,65535);
    /*SFInt32*/   espduTransform.eventNumber        = espduTransform.eventNumberDefault        = new SFInt32(ESPDUTRANSFORM_ATTR_EVENTNUMBER_DFLT,0,65535);
    /*SFInt32*/   espduTransform.eventSiteID        = espduTransform.eventSiteIDDefault        = new SFInt32(ESPDUTRANSFORM_ATTR_EVENTSITEID_DFLT,0,65535);
    /*boolean*/ espduTransform.fired1             = espduTransform.fired1Default             = Boolean.parseBoolean(ESPDUTRANSFORM_ATTR_FIRED1_DFLT);
    /*boolean*/ espduTransform.fired2             = espduTransform.fired2Default             = Boolean.parseBoolean(ESPDUTRANSFORM_ATTR_FIRED2_DFLT);
    /*SFInt32*/   espduTransform.fireMissionIndex   = espduTransform.fireMissionIndexDefault   = new SFInt32(ESPDUTRANSFORM_ATTR_FIREMISSIONINDEX_DFLT,0,65535);
    /*SFFloat*/ espduTransform.firingRange        = espduTransform.firingRangeDefault        = new SFFloat(ESPDUTRANSFORM_ATTR_FIRINGRANGE_DFLT,0f,null);
    /*SFInt32*/   espduTransform.firingRate         = espduTransform.firingRateDefault         = new SFInt32(ESPDUTRANSFORM_ATTR_FIRINGRATE_DFLT,0,65535);
    /*SFInt32*/   espduTransform.forceID            = espduTransform.forceIDDefault            = new SFInt32(ESPDUTRANSFORM_ATTR_FORCEID_DFLT,0,255);
    /*SFInt32*/   espduTransform.fuse               = espduTransform.fuseDefault               = new SFInt32(ESPDUTRANSFORM_ATTR_FUSE_DFLT,0,65535);
    
    fa = parse3(ESPDUTRANSFORM_ATTR_LINEARVELOCITY_DFLT);
    /*SFFloat*/ espduTransform.linearVelocityX       = espduTransform.linearVelocityXDefault       = new SFFloat(fa[0],null,null);
    /*SFFloat*/ espduTransform.linearVelocityY       = espduTransform.linearVelocityYDefault       = new SFFloat(fa[1],null,null);
    /*SFFloat*/ espduTransform.linearVelocityZ       = espduTransform.linearVelocityZDefault       = new SFFloat(fa[2],null,null);
    fa = parse3(ESPDUTRANSFORM_ATTR_LINEARACCELERATION_DFLT);
    /*SFFloat*/ espduTransform.linearAccelerationX   = espduTransform.linearAccelerationXDefault   = new SFFloat(fa[0],null,null);
    /*SFFloat*/ espduTransform.linearAccelerationY   = espduTransform.linearAccelerationYDefault   = new SFFloat(fa[1],null,null);
    /*SFFloat*/ espduTransform.linearAccelerationZ   = espduTransform.linearAccelerationZDefault   = new SFFloat(fa[2],null,null);
    /*String*/  espduTransform.marking = ESPDUTRANSFORM_ATTR_MARKING_DFLT;
    /*String*/  espduTransform.multicastRelayHost    = ESPDUTRANSFORM_ATTR_MULTICASTRELAYHOST_DFLT;
    /*SFInt32*/   espduTransform.multicastRelayPort    = espduTransform.multicastRelayPortDefault    = new SFInt32(ESPDUTRANSFORM_ATTR_MULTICASTRELAYPORT_DFLT,0,null);
    /*SFInt32*/   espduTransform.munitionApplicationID = espduTransform.munitionApplicationIDDefault = new SFInt32(ESPDUTRANSFORM_ATTR_MUNITIONAPPLICATIONID_DFLT,0,65535);
 
    fa = parse3(ESPDUTRANSFORM_ATTR_MUNITIONENDPOINT_DFLT);
    /*SFFloat*/ espduTransform.munitionEndPointX   = espduTransform.munitionEndPointXDefault   = new SFFloat(fa[0],null,null);
    /*SFFloat*/ espduTransform.munitionEndPointY   = espduTransform.munitionEndPointYDefault   = new SFFloat(fa[1],null,null);
    /*SFFloat*/ espduTransform.munitionEndPointZ   = espduTransform.munitionEndPointZDefault   = new SFFloat(fa[2],null,null);
    /*SFInt32*/   espduTransform.munitionEntityID    = espduTransform.munitionEntityIDDefault    = new SFInt32(ESPDUTRANSFORM_ATTR_MUNITIONENTITYID_DFLT,0,65535);
    /*SFInt32*/   espduTransform.munitionQuantity    = espduTransform.munitionQuantityDefault    = new SFInt32(ESPDUTRANSFORM_ATTR_MUNITIONQUANTITY_DFLT,0,65535);
    /*SFInt32*/   espduTransform.munitionSiteID      = espduTransform.munitionSiteIDDefault      = new SFInt32(ESPDUTRANSFORM_ATTR_MUNITIONSITEID_DFLT,0,65535);
    fa = parse3(ESPDUTRANSFORM_ATTR_MUNITIONSTARTPOINT_DFLT);
    /*SFFloat*/ espduTransform.munitionStartPointX = espduTransform.munitionStartPointXDefault = new SFFloat(fa[0],null,null);
    /*SFFloat*/ espduTransform.munitionStartPointY = espduTransform.munitionStartPointYDefault = new SFFloat(fa[1],null,null);
    /*SFFloat*/ espduTransform.munitionStartPointZ = espduTransform.munitionStartPointZDefault = new SFFloat(fa[2],null,null);
    
    /*String*/  espduTransform.networkMode = ESPDUTRANSFORM_ATTR_NETWORKMODE_DFLT;
    /*SFInt32*/   espduTransform.port         = espduTransform.portDefault         = new SFInt32(ESPDUTRANSFORM_ATTR_PORT_DFLT,0,65535);
    /*SFFloat*/ espduTransform.readInterval = espduTransform.readIntervalDefault = new SFFloat(ESPDUTRANSFORM_ATTR_READINTERVAL_DFLT,0f,null);
    
    fa = parse4(ESPDUTRANSFORM_ATTR_ROTATION_DFLT);
    /*SFFloat*/ espduTransform.rotationX = espduTransform.rotationXDefault = new SFFloat(fa[0],null,null);
    /*SFFloat*/ espduTransform.rotationY = espduTransform.rotationYDefault = new SFFloat(fa[1],null,null);
    /*SFFloat*/ espduTransform.rotationZ = espduTransform.rotationZDefault = new SFFloat(fa[2],null,null);
    /*SFFloat*/ espduTransform.rotationAngle = espduTransform.rotationAngleDefault = new SFFloat(fa[3],null,null);
    fa = parse3(ESPDUTRANSFORM_ATTR_SCALE_DFLT);
    /*SFFloat*/ espduTransform.scaleX = espduTransform.scaleXDefault       = new SFFloat(fa[0],null,null);
    /*SFFloat*/ espduTransform.scaleY = espduTransform.scaleYDefault       = new SFFloat(fa[1],null,null);
    /*SFFloat*/ espduTransform.scaleZ = espduTransform.scaleZDefault       = new SFFloat(fa[2],null,null);
    fa = parse4(ESPDUTRANSFORM_ATTR_SCALEORIENTATION_DFLT);
    /*SFFloat*/ espduTransform.scaleOrientationX               = espduTransform.scaleOrientation0Default = new SFFloat(fa[0],null,null);
    /*SFFloat*/ espduTransform.scaleOrientationY               = espduTransform.scaleOrientation1Default = new SFFloat(fa[1],null,null);
    /*SFFloat*/ espduTransform.scaleOrientationZ               = espduTransform.scaleOrientation2Default = new SFFloat(fa[2],null,null);
    /*SFFloat*/ espduTransform.scaleOrientationAngle               = espduTransform.scaleOrientation3Default = new SFFloat(fa[3],null,null);
    /*SFInt32*/   espduTransform.siteID                          = espduTransform.siteIDDefault            = new SFInt32(ESPDUTRANSFORM_ATTR_SITEID_DFLT,0,65535);
    
    fa = parse3(ESPDUTRANSFORM_ATTR_TRANSLATION_DFLT);
    /*SFFloat*/ espduTransform.translationX  = espduTransform.translationXDefault  = new SFFloat(fa[0],null,null);
    /*SFFloat*/ espduTransform.translationY  = espduTransform.translationYDefault  = new SFFloat(fa[1],null,null);
    /*SFFloat*/ espduTransform.translationZ  = espduTransform.translationZDefault  = new SFFloat(fa[2],null,null);
    /*SFInt32*/   espduTransform.warhead       = espduTransform.warheadDefault       = new SFInt32(ESPDUTRANSFORM_ATTR_WARHEAD_DFLT,0,65535);
    /*SFFloat*/ espduTransform.writeInterval = espduTransform.writeIntervalDefault = new SFFloat(ESPDUTRANSFORM_ATTR_WRITEINTERVAL_DFLT,0f,null);
////    fa = parse3(ESPDUTRANSFORM_ATTR_BBOXCENTER_DFLT);
////    /*SFFloat*/ et.bboxCenterX   = et.bboxCenterXDefault   = new SFFloat(fa[0],null,null);
////    /*SFFloat*/ et.bboxCenterY   = et.bboxCenterYDefault   = new SFFloat(fa[1],null,null);
////    /*SFFloat*/ et.bboxCenterZ   = et.bboxCenterZDefault   = new SFFloat(fa[2],null,null);
////    fa = parse3(ESPDUTRANSFORM_ATTR_BBOXSIZE_DFLT);
////    /*SFFloat*/ et.bboxSizeX     = et.bboxSizeXDefault     = new SFFloat(fa[0],0f,null,true);
////    /*SFFloat*/ et.bboxSizeY     = et.bboxSizeYDefault     = new SFFloat(fa[1],0f,null,true);
////    /*SFFloat*/ et.bboxSizeZ     = et.bboxSizeZDefault     = new SFFloat(fa[2],0f,null,true);
    
    /*boolean*/ espduTransform.rtpHeaderExpected = espduTransform.rtpHeaderExpecteDefault = Boolean.parseBoolean(ESPDUTRANSFORM_ATTR_RTPHEADEREXPECTED_DFLT);    
  }
  
  /*public*/ static void initializeFromJdom(ESPDUTRANSFORM espduTransform, org.jdom.Element root)
  {
    org.jdom.Attribute attr;
    String[] sa;

    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ADDRESS_NAME);
    if (attr != null)
      espduTransform.address = attr.getValue();
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_APPLICATIONID_NAME);
    if (attr != null)
      espduTransform.appID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCOUNT_NAME);
    if (attr != null)
      espduTransform.articulationParameterCount = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERDESIGNATORARRAY_NAME);
    if (attr != null)
      espduTransform.articulationParameterDesignatorArray = handleIntArray(espduTransform, attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_NAME);
    if (attr != null)
      espduTransform.articulationParameterChangeIndicatorArray = handleIntArray(espduTransform, attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERIDPARTATTACHEDTORARRAY_NAME);
    if (attr != null)
      espduTransform.articulationParameterIdPartAttachedToArray = handleIntArray(espduTransform, attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERTYPEARRAY_NAME);
    if (attr != null)
      espduTransform.articulationParameterTypeArray = handleIntArray(espduTransform, attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERARRAY_NAME);
    if (attr != null)
      espduTransform.articulationParameterArray = handleFloatArray(espduTransform, attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_CENTER_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      espduTransform.centerX = new SFFloat(sa[0]);
      espduTransform.centerY = new SFFloat(sa[1]);
      espduTransform.centerZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_COLLISIONTYPE_NAME);
    if (attr != null)
      espduTransform.collisionType = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_DEADRECKONING_NAME);
    if (attr != null)
      espduTransform.deadReckoning = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_DETONATIONLOCATION_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      espduTransform.detonationLocationX = new SFFloat(sa[0]);
      espduTransform.detonationLocationY = new SFFloat(sa[1]);
      espduTransform.detonationLocationZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_DETONATIONRELATIVELOCATION_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      espduTransform.detonationRelativeLocationX = new SFFloat(sa[0]);
      espduTransform.detonationRelativeLocationY = new SFFloat(sa[1]);
      espduTransform.detonationRelativeLocationZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_DETONATIONRESULT_NAME);
    if (attr != null)
      espduTransform.detonationResult = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ENABLED_NAME);
    if (attr != null)
      espduTransform.enabled = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ENTITYCATEGORY_NAME);
    if (attr != null)
      espduTransform.entityCategory = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ENTITYCOUNTRY_NAME);
    if (attr != null)
      espduTransform.entityCountry = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ENTITYDOMAIN_NAME);
    if (attr != null)
      espduTransform.entityDomain = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ENTITYEXTRA_NAME);
    if (attr != null)
      espduTransform.entityExtra = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ENTITYID_NAME);
    if (attr != null)
      espduTransform.entityID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ENTITYKIND_NAME);
    if (attr != null)
      espduTransform.entityKind = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ENTITYSPECIFIC_NAME);
    if (attr != null)
      espduTransform.entitySpecific = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ENTITYSUBCATEGORY_NAME);
    if (attr != null)
      espduTransform.entitySubCategory = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_EVENTAPPLICATIONID_NAME);
    if (attr != null)
      espduTransform.eventApplicationID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_EVENTENTITYID_NAME);
    if (attr != null)
      espduTransform.eventEntityID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_EVENTNUMBER_NAME);
    if (attr != null)
      espduTransform.eventNumber = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_EVENTSITEID_NAME);
    if (attr != null)
      espduTransform.eventSiteID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_FIRED1_NAME);
    if (attr != null)
      espduTransform.fired1 = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_FIRED2_NAME);
    if (attr != null)
      espduTransform.fired2 = Boolean.parseBoolean(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_FIREMISSIONINDEX_NAME);
    if (attr != null)
      espduTransform.fireMissionIndex = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_FIRINGRANGE_NAME);
    if (attr != null)
      espduTransform.firingRange = new SFFloat(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_FIRINGRATE_NAME);
    if (attr != null)
      espduTransform.firingRate = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_FORCEID_NAME);
    if (attr != null)
      espduTransform.forceID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_FUSE_NAME);
    if (attr != null)
      espduTransform.fuse = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_LINEARVELOCITY_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      espduTransform.linearVelocityX = new SFFloat(sa[0]);
      espduTransform.linearVelocityY = new SFFloat(sa[1]);
      espduTransform.linearVelocityZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_LINEARACCELERATION_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      espduTransform.linearAccelerationX = new SFFloat(sa[0]);
      espduTransform.linearAccelerationY = new SFFloat(sa[1]);
      espduTransform.linearAccelerationZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_MARKING_NAME);
    if (attr != null)
      espduTransform.marking = attr.getValue();
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_MULTICASTRELAYHOST_NAME);
    if (attr != null)
      espduTransform.multicastRelayHost = attr.getValue();
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_MULTICASTRELAYPORT_NAME);
    if (attr != null)
      espduTransform.multicastRelayPort = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_MUNITIONAPPLICATIONID_NAME);
    if (attr != null)
      espduTransform.munitionApplicationID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_MUNITIONENDPOINT_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      espduTransform.munitionEndPointX = new SFFloat(sa[0]);
      espduTransform.munitionEndPointY = new SFFloat(sa[1]);
      espduTransform.munitionEndPointZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_MUNITIONENTITYID_NAME);
    if (attr != null)
      espduTransform.munitionEntityID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_MUNITIONQUANTITY_NAME);
    if (attr != null)
      espduTransform.munitionQuantity = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_MUNITIONSITEID_NAME);
    if (attr != null)
      espduTransform.munitionSiteID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_MUNITIONSTARTPOINT_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      espduTransform.munitionStartPointX = new SFFloat(sa[0]);
      espduTransform.munitionStartPointY = new SFFloat(sa[1]);
      espduTransform.munitionStartPointZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_NETWORKMODE_NAME);
    if (attr != null)
      espduTransform.networkMode = attr.getValue();
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_PORT_NAME);
    if (attr != null)
      espduTransform.port = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_READINTERVAL_NAME);
    if (attr != null)
      espduTransform.readInterval = new SFFloat(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_ROTATION_NAME);
    if (attr != null) {
      sa = parse4(attr.getValue());
      espduTransform.rotationX = new SFFloat(sa[0]);
      espduTransform.rotationY = new SFFloat(sa[1]);
      espduTransform.rotationZ = new SFFloat(sa[2]);
      espduTransform.rotationAngle = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_SCALE_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      espduTransform.scaleX = new SFFloat(sa[0]);
      espduTransform.scaleY = new SFFloat(sa[1]);
      espduTransform.scaleZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_SCALEORIENTATION_NAME);
    if (attr != null) {
      sa = parse4(attr.getValue());
      espduTransform.scaleOrientationX = new SFFloat(sa[0]);
      espduTransform.scaleOrientationY = new SFFloat(sa[1]);
      espduTransform.scaleOrientationZ = new SFFloat(sa[2]);
      espduTransform.scaleOrientationAngle = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_SITEID_NAME);
    if (attr != null)
      espduTransform.siteID = new SFInt32(attr.getValue());
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_TRANSLATION_NAME);
    if (attr != null) {
      sa = parse3(attr.getValue());
      espduTransform.translationX = new SFFloat(sa[0]);
      espduTransform.translationY = new SFFloat(sa[1]);
      espduTransform.translationZ = new SFFloat(sa[2]);
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_WARHEAD_NAME);
    if (attr != null) {
      espduTransform.warhead = new SFInt32(attr.getValue());
    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_WRITEINTERVAL_NAME);
    if (attr != null)
      espduTransform.writeInterval = new SFFloat(attr.getValue());
////    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_BBOXCENTER_NAME);
////    if (attr != null) {
////      sa = parse3(attr.getValue());
////      et.bboxCenterX = new SFInt32(sa[0]);
////      et.bboxCenterY = new SFInt32(sa[1]);
////      et.bboxCenterZ = new SFInt32(sa[2]);
////    }
////    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_BBOXSIZE_NAME);
////    if (attr != null) {
////      sa = parse3(attr.getValue());
////      et.bboxSizeX = new SFInt32(sa[0]);
////      et.bboxSizeY = new SFInt32(sa[1]);
////      et.bboxSizeZ = new SFInt32(sa[2]);
////    }
    attr = root.getAttribute(ESPDUTRANSFORM_ATTR_RTPHEADEREXPECTED_NAME);
    if (attr != null)
      espduTransform.rtpHeaderExpected = Boolean.parseBoolean(attr.getValue());
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
//
//  private static void fillNullsWithZero(SFFloat[] sa)
//  {
//    for (int i = 0; i < sa.length; i++) {
//      if (sa[i].toString().equals("")) //Float.NaN))
//        sa[i] = new SFInt32("0");
//    }
//  }
  
  /*public*/static String createAttributes(ESPDUTRANSFORM espduTransform)
  {
    StringBuilder sb = new StringBuilder();
    int sblen=0;
    String newLineString = "\n               ";  //15 spaces since the word ESPDUTransform is at least that long
     
    if(ESPDUTRANSFORM_ATTR_ADDRESS_REQD || !espduTransform.address.equals(ESPDUTRANSFORM_ATTR_ADDRESS_DFLT)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ADDRESS_NAME);
      sb.append("='");
      sb.append(espduTransform.address);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_APPLICATIONID_REQD || !espduTransform.appID.equals(espduTransform.appIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_APPLICATIONID_NAME);
      sb.append("='");
      sb.append(espduTransform.appID);
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
     
    if(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCOUNT_REQD || !espduTransform.articulationParameterCount.equals(espduTransform.articulationParameterCountDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCOUNT_NAME);
      sb.append("='");
      sb.append(espduTransform.articulationParameterCount);
      sb.append("'");
    }
//    oa[ARTPARAM_DESIG_COL] = ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERDESIGNATORARRAY_DFLT;
//  oa[ARTPARAM_CHANG_COL] = ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_DFLT;
//  oa[ARTPARAM_ID_COL]    = ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERIDPARTATTACHEDTORARRAY_DFLT;
//  oa[ARTPARAM_TYP_COL]   = ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERTYPEARRAY_DFLT;
//  oa[ARTPARAM_PARAM_COL] = ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERARRAY_DFLT;

    if(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERDESIGNATORARRAY_REQD || !espduTransform.arraysIdenticalOrNull(espduTransform.articulationParameterDesignatorArray,espduTransform.articulationParameterDesignatorArrayDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERDESIGNATORARRAY_NAME);
      sb.append("='");
      sb.append(espduTransform.formatIntArray(espduTransform.articulationParameterDesignatorArray));
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_REQD || !espduTransform.arraysIdenticalOrNull(espduTransform.articulationParameterChangeIndicatorArray,espduTransform.articulationParameterChangeIndicatorArrayDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_NAME);
      sb.append("='");
      sb.append(espduTransform.formatIntArray(espduTransform.articulationParameterChangeIndicatorArray));
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_REQD || !espduTransform.arraysIdenticalOrNull(espduTransform.articulationParameterIdPartAttachedToArray,espduTransform.articulationParameterIdPartAttachedToArrayDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERIDPARTATTACHEDTORARRAY_NAME);
      sb.append("='");
      sb.append(espduTransform.formatIntArray(espduTransform.articulationParameterIdPartAttachedToArray));
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_REQD || !espduTransform.arraysIdenticalOrNull(espduTransform.articulationParameterTypeArray,espduTransform.articulationParameterTypeArrayDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERTYPEARRAY_NAME);
      sb.append("='");
      sb.append(espduTransform.formatIntArray(espduTransform.articulationParameterTypeArray));
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_REQD || !espduTransform.arraysIdenticalOrNull(espduTransform.articulationParameterArray,espduTransform.articulationParameterArrayDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERARRAY_NAME);
      sb.append("='");
      sb.append(espduTransform.formatFloatArray(espduTransform.articulationParameterArray));
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
     
    if(ESPDUTRANSFORM_ATTR_CENTER_REQD || 
        (!espduTransform.centerX.equals(espduTransform.centerXDefault) ||
         !espduTransform.centerY.equals(espduTransform.centerYDefault) ||
         !espduTransform.centerZ.equals(espduTransform.centerZDefault))) {             
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_CENTER_NAME);
      sb.append("='");
      sb.append(espduTransform.centerX);
      sb.append(" ");
      sb.append(espduTransform.centerY);
      sb.append(" ");
      sb.append(espduTransform.centerZ);
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
     
    if(ESPDUTRANSFORM_ATTR_COLLISIONTYPE_REQD || !espduTransform.collisionType.equals(espduTransform.collisionTypeDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_COLLISIONTYPE_NAME);
      sb.append("='");
      sb.append(espduTransform.collisionType);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_DEADRECKONING_REQD || !espduTransform.deadReckoning.equals(espduTransform.deadReckoningDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_DEADRECKONING_NAME);
      sb.append("='");
      sb.append(espduTransform.deadReckoning);
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
     
    if(ESPDUTRANSFORM_ATTR_DETONATIONLOCATION_REQD ||
        (!espduTransform.detonationLocationX.equals(espduTransform.detonationLocationXDefault) ||
         !espduTransform.detonationLocationY.equals(espduTransform.detonationLocationYDefault) ||
         !espduTransform.detonationLocationZ.equals(espduTransform.detonationLocationZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_DETONATIONLOCATION_NAME);
      sb.append("='");
      sb.append(espduTransform.detonationLocationX);
      sb.append(" ");
      sb.append(espduTransform.detonationLocationY);
      sb.append(" ");
      sb.append(espduTransform.detonationLocationZ);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_DETONATIONRELATIVELOCATION_REQD ||
       (!espduTransform.detonationRelativeLocationX.equals(espduTransform.detonationRelativeLocationXDefault) ||
        !espduTransform.detonationRelativeLocationY.equals(espduTransform.detonationRelativeLocationYDefault) ||
        !espduTransform.detonationRelativeLocationZ.equals(espduTransform.detonationRelativeLocationZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_DETONATIONRELATIVELOCATION_NAME);
      sb.append("='");
      sb.append(espduTransform.detonationRelativeLocationX);
      sb.append(" ");
      sb.append(espduTransform.detonationRelativeLocationY);
      sb.append(" ");
      sb.append(espduTransform.detonationRelativeLocationZ);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_DETONATIONRESULT_REQD || !espduTransform.detonationResult.equals(espduTransform.detonationResultDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_DETONATIONRESULT_NAME);
      sb.append("='");
      sb.append(espduTransform.detonationResult);
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
     
    if(ESPDUTRANSFORM_ATTR_ENABLED_REQD || (espduTransform.enabled != espduTransform.enabledDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENABLED_NAME);
      sb.append("='");
      sb.append(espduTransform.enabled);
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
     
    if(ESPDUTRANSFORM_ATTR_ENTITYCATEGORY_REQD || !espduTransform.entityCategory.equals(espduTransform.entityCategoryDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENTITYCATEGORY_NAME);
      sb.append("='");
      sb.append(espduTransform.entityCategory);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ENTITYCOUNTRY_REQD || !espduTransform.entityCountry.equals(espduTransform.entityCountryDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENTITYCOUNTRY_NAME);
      sb.append("='");
      sb.append(espduTransform.entityCountry);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ENTITYDOMAIN_REQD || !espduTransform.entityDomain.equals(espduTransform.entityDomainDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENTITYDOMAIN_NAME);
      sb.append("='");
      sb.append(espduTransform.entityDomain);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ENTITYEXTRA_REQD || !espduTransform.entityExtra.equals(espduTransform.entityExtraDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENTITYEXTRA_NAME);
      sb.append("='");
      sb.append(espduTransform.entityExtra);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ENTITYID_REQD || !espduTransform.entityID.equals(espduTransform.entityIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENTITYID_NAME);
      sb.append("='");
      sb.append(espduTransform.entityID);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ENTITYKIND_REQD || !espduTransform.entityKind.equals(espduTransform.entityKindDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENTITYKIND_NAME);
      sb.append("='");
      sb.append(espduTransform.entityKind);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ENTITYSPECIFIC_REQD || !espduTransform.entitySpecific.equals(espduTransform.entitySpecificDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENTITYSPECIFIC_NAME);
      sb.append("='");
      sb.append(espduTransform.entitySpecific);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_ENTITYSUBCATEGORY_REQD || !espduTransform.entitySubCategory.equals(espduTransform.entitySubCategoryDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ENTITYSUBCATEGORY_NAME);
      sb.append("='");
      sb.append(espduTransform.entitySubCategory);
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
     
    if(ESPDUTRANSFORM_ATTR_EVENTAPPLICATIONID_REQD || !espduTransform.eventApplicationID.equals(espduTransform.eventApplicationIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_EVENTAPPLICATIONID_NAME);
      sb.append("='");
      sb.append(espduTransform.eventApplicationID);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_EVENTENTITYID_REQD || !espduTransform.eventEntityID.equals(espduTransform.eventEntityIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_EVENTENTITYID_NAME);
      sb.append("='");
      sb.append(espduTransform.eventEntityID);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_EVENTNUMBER_REQD || !espduTransform.eventNumber.equals(espduTransform.eventNumberDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_EVENTNUMBER_NAME);
      sb.append("='");
      sb.append(espduTransform.eventNumber);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_EVENTSITEID_REQD || !espduTransform.eventSiteID.equals(espduTransform.eventSiteIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_EVENTSITEID_NAME);
      sb.append("='");
      sb.append(espduTransform.eventSiteID);
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
     
    if(ESPDUTRANSFORM_ATTR_FIRED1_REQD || (espduTransform.fired1 != espduTransform.fired1Default)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_FIRED1_NAME);
      sb.append("='");
      sb.append(espduTransform.fired1);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_FIRED2_REQD || (espduTransform.fired2 != espduTransform.fired2Default)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_FIRED2_NAME);
      sb.append("='");
      sb.append(espduTransform.fired2);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_FIREMISSIONINDEX_REQD || !espduTransform.fireMissionIndex.equals(espduTransform.fireMissionIndexDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_FIREMISSIONINDEX_NAME);
      sb.append("='");
      sb.append(espduTransform.fireMissionIndex);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_FIRINGRANGE_REQD || !espduTransform.firingRange.equals(espduTransform.firingRangeDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_FIRINGRANGE_NAME);
      sb.append("='");
      sb.append(espduTransform.firingRange);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_FIRINGRATE_REQD || !espduTransform.firingRate.equals(espduTransform.firingRateDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_FIRINGRATE_NAME);
      sb.append("='");
      sb.append(espduTransform.firingRate);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_FORCEID_REQD || !espduTransform.forceID.equals(espduTransform.forceIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_FORCEID_NAME);
      sb.append("='");
      sb.append(espduTransform.forceID);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_FUSE_REQD || !espduTransform.fuse.equals(espduTransform.fuseDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_FUSE_NAME);
      sb.append("='");
      sb.append(espduTransform.fuse);
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
     
    if(ESPDUTRANSFORM_ATTR_LINEARVELOCITY_REQD || 
        (!espduTransform.linearVelocityX.equals(espduTransform.linearVelocityXDefault) ||
         !espduTransform.linearVelocityY.equals(espduTransform.linearVelocityYDefault) ||
         !espduTransform.linearVelocityZ.equals(espduTransform.linearVelocityZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_LINEARVELOCITY_NAME);
      sb.append("='");
      sb.append(espduTransform.linearVelocityX);
      sb.append(" ");
      sb.append(espduTransform.linearVelocityY);
      sb.append(" ");
      sb.append(espduTransform.linearVelocityZ);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_LINEARACCELERATION_REQD || 
        (!espduTransform.linearAccelerationX.equals(espduTransform.linearAccelerationXDefault) ||
         !espduTransform.linearAccelerationY.equals(espduTransform.linearAccelerationYDefault) ||
         !espduTransform.linearAccelerationZ.equals(espduTransform.linearAccelerationZDefault))) {
         
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_LINEARACCELERATION_NAME);
      sb.append("='");
      sb.append(espduTransform.linearAccelerationX);
      sb.append(" ");
      sb.append(espduTransform.linearAccelerationY);
      sb.append(" ");
      sb.append(espduTransform.linearAccelerationZ);
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
     
    if(ESPDUTRANSFORM_ATTR_MARKING_REQD || !espduTransform.marking.equals(ESPDUTRANSFORM_ATTR_MARKING_DFLT)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MARKING_NAME);
      sb.append("='");
      sb.append(espduTransform.marking);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_MULTICASTRELAYHOST_REQD || !espduTransform.multicastRelayHost.equals(ESPDUTRANSFORM_ATTR_MULTICASTRELAYHOST_DFLT)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MULTICASTRELAYHOST_NAME);
      sb.append("='");
      sb.append(espduTransform.multicastRelayHost);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_MULTICASTRELAYPORT_REQD || !espduTransform.multicastRelayPort.equals(espduTransform.multicastRelayPortDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MULTICASTRELAYPORT_NAME);
      sb.append("='");
      sb.append(espduTransform.multicastRelayPort);
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
     
    if(ESPDUTRANSFORM_ATTR_MUNITIONAPPLICATIONID_REQD || !espduTransform.munitionApplicationID.equals(espduTransform.munitionApplicationIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MUNITIONAPPLICATIONID_NAME);
      sb.append("='");
      sb.append(espduTransform.munitionApplicationID);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_MUNITIONENDPOINT_REQD ||
      (!espduTransform.munitionEndPointX.equals(espduTransform.munitionEndPointXDefault) ||
       !espduTransform.munitionEndPointY.equals(espduTransform.munitionEndPointYDefault) ||
       !espduTransform.munitionEndPointZ.equals(espduTransform.munitionEndPointZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MUNITIONENDPOINT_NAME);
      sb.append("='");
      sb.append(espduTransform.munitionEndPointX);
      sb.append(" ");
      sb.append(espduTransform.munitionEndPointY);
      sb.append(" ");
      sb.append(espduTransform.munitionEndPointZ);
      sb.append("'");
    }    
    if(ESPDUTRANSFORM_ATTR_MUNITIONENTITYID_REQD || !espduTransform.munitionEntityID.equals(espduTransform.munitionEntityIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MUNITIONENTITYID_NAME);
      sb.append("='");
      sb.append(espduTransform.munitionEntityID);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_MUNITIONQUANTITY_REQD || !espduTransform.munitionQuantity.equals(espduTransform.munitionQuantityDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MUNITIONQUANTITY_NAME);
      sb.append("='");
      sb.append(espduTransform.munitionQuantity);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_MUNITIONSITEID_REQD || !espduTransform.munitionSiteID.equals(espduTransform.munitionSiteIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MUNITIONSITEID_NAME);
      sb.append("='");
      sb.append(espduTransform.munitionSiteID);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_MUNITIONSTARTPOINT_REQD ||
       (!espduTransform.munitionStartPointX.equals(espduTransform.munitionStartPointXDefault) ||
        !espduTransform.munitionStartPointY.equals(espduTransform.munitionStartPointYDefault) ||
        !espduTransform.munitionStartPointZ.equals(espduTransform.munitionStartPointZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_MUNITIONSTARTPOINT_NAME);
      sb.append("='");
      sb.append(espduTransform.munitionStartPointX);
      sb.append(" ");
      sb.append(espduTransform.munitionStartPointY);
      sb.append(" ");
      sb.append(espduTransform.munitionStartPointZ);
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
     
    if(ESPDUTRANSFORM_ATTR_NETWORKMODE_REQD || !espduTransform.networkMode.equals(ESPDUTRANSFORM_ATTR_NETWORKMODE_DFLT)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_NETWORKMODE_NAME);
      sb.append("='");
      sb.append(espduTransform.networkMode);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_PORT_REQD || !espduTransform.port.equals(espduTransform.portDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_PORT_NAME);
      sb.append("='");
      sb.append(espduTransform.port);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_READINTERVAL_REQD || !espduTransform.readInterval.equals(espduTransform.readIntervalDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_READINTERVAL_NAME);
      sb.append("='");
      sb.append(espduTransform.readInterval);
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
     
    if(ESPDUTRANSFORM_ATTR_ROTATION_REQD || 
        (!espduTransform.rotationX.equals(espduTransform.rotationXDefault) ||
         !espduTransform.rotationY.equals(espduTransform.rotationYDefault) ||
         !espduTransform.rotationZ.equals(espduTransform.rotationZDefault) ||
         !espduTransform.rotationAngle.equals(espduTransform.rotationAngleDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_ROTATION_NAME);
      sb.append("='");
      sb.append(espduTransform.rotationX);
      sb.append(" ");
      sb.append(espduTransform.rotationY);
      sb.append(" ");
      sb.append(espduTransform.rotationZ);
      sb.append(" ");
      sb.append(espduTransform.rotationAngle);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_SCALE_REQD || 
       (!espduTransform.scaleX.equals(espduTransform.scaleXDefault) ||
        !espduTransform.scaleY.equals(espduTransform.scaleYDefault) ||
        !espduTransform.scaleZ.equals(espduTransform.scaleZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_SCALE_NAME);
      sb.append("='");
      sb.append(espduTransform.scaleX);
      sb.append(" ");
      sb.append(espduTransform.scaleY);
      sb.append(" ");
      sb.append(espduTransform.scaleZ);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_SCALEORIENTATION_REQD || 
       (!espduTransform.scaleOrientationX.equals(espduTransform.scaleOrientation0Default) ||
        !espduTransform.scaleOrientationY.equals(espduTransform.scaleOrientation1Default) ||
        !espduTransform.scaleOrientationZ.equals(espduTransform.scaleOrientation2Default) ||
        !espduTransform.scaleOrientationAngle.equals(espduTransform.scaleOrientation3Default))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_SCALEORIENTATION_NAME);
      sb.append("='");
      sb.append(espduTransform.scaleOrientationX);
      sb.append(" ");
      sb.append(espduTransform.scaleOrientationY);
      sb.append(" ");
      sb.append(espduTransform.scaleOrientationZ);
      sb.append(" ");
      sb.append(espduTransform.scaleOrientationAngle);
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
     
    if(ESPDUTRANSFORM_ATTR_SITEID_REQD || !espduTransform.siteID.equals(espduTransform.siteIDDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_SITEID_NAME);
      sb.append("='");
      sb.append(espduTransform.siteID);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_TRANSLATION_REQD || 
       (!espduTransform.translationX.equals(espduTransform.translationXDefault) ||
        !espduTransform.translationY.equals(espduTransform.translationYDefault) ||
        !espduTransform.translationZ.equals(espduTransform.translationZDefault))) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_TRANSLATION_NAME);
      sb.append("='");
      sb.append(espduTransform.translationX);
      sb.append(" ");
      sb.append(espduTransform.translationY);
      sb.append(" ");
      sb.append(espduTransform.translationZ);
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
     
    if(ESPDUTRANSFORM_ATTR_WARHEAD_REQD || !espduTransform.warhead.equals(espduTransform.warheadDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_WARHEAD_NAME);
      sb.append("='");
      sb.append(espduTransform.warhead);
      sb.append("'");
    }
    if(ESPDUTRANSFORM_ATTR_WRITEINTERVAL_REQD || !espduTransform.writeInterval.equals(espduTransform.writeIntervalDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_WRITEINTERVAL_NAME);
      sb.append("='");
      sb.append(espduTransform.writeInterval);
      sb.append("'");
    }
     
    if(sb.length() > sblen) {sb.append(newLineString);sblen=sb.length();}
     
////    if(ESPDUTRANSFORM_ATTR_BBOXCENTER_REQD ||
////       (!et.bboxCenterX.equals(et.bboxCenterXDefault) ||
////        !et.bboxCenterY.equals(et.bboxCenterYDefault) ||
////        !et.bboxCenterZ.equals(et.bboxCenterZDefault))) {
////      sb.append(" ");
////      sb.append(ESPDUTRANSFORM_ATTR_BBOXCENTER_NAME);
////      sb.append("='");
////      sb.append(et.bboxCenterX);
////      sb.append(" ");
////      sb.append(et.bboxCenterY);
////      sb.append(" ");
////      sb.append(et.bboxCenterZ);
////      sb.append("'");
////    }
////    if(ESPDUTRANSFORM_ATTR_BBOXSIZE_REQD ||
////       (!et.bboxSizeX.equals(et.bboxSizeXDefault) ||
////        !et.bboxSizeY.equals(et.bboxSizeYDefault) ||
////        !et.bboxSizeZ.equals(et.bboxSizeZDefault))) {
////      sb.append(" ");
////      sb.append(ESPDUTRANSFORM_ATTR_BBOXSIZE_NAME);
////      sb.append("='");
////      sb.append(et.bboxSizeX);
////      sb.append(" ");
////      sb.append(et.bboxSizeX);
////      sb.append(" ");
////      sb.append(et.bboxSizeX);
////      sb.append("'");
////    }
     
    if(ESPDUTRANSFORM_ATTR_RTPHEADEREXPECTED_REQD || (espduTransform.rtpHeaderExpected != espduTransform.rtpHeaderExpecteDefault)) {
      sb.append(" ");
      sb.append(ESPDUTRANSFORM_ATTR_RTPHEADEREXPECTED_NAME);
      sb.append("='");
      sb.append(espduTransform.rtpHeaderExpected);
      sb.append("'");
    }
     
    if(sb.length() > sblen) 
    {
        sb.append(newLineString);
    }
           
    return sb.toString();
  }
  
  // Used by Customizer
  /*public*/static void initComponents(ESPDUTRANSFORMCustomizer customizer, ESPDUTRANSFORM espduTransform)
  {
    customizer.addressTF.setText(espduTransform.getAddress()); //String
    customizer.appIdTF.setText(espduTransform.getAppID());     //SFInt
    
//    cust.articParmCountTF.setText           (espduTrans.getArticulationParameterCount());//SFInt
//	cust.articParmDesArrayTF.setText        (espduTrans.formatStringArray(espduTrans.getArticulationParameterDesignatorArray()));//SFInt[]
//	cust.articParmChgIndArrayTF.setText     (espduTrans.formatStringArray(espduTrans.getArticulationParameterChangeIndicatorArray()));//SFInt[]
//	cust.articParmIdPartAttToArrayTF.setText(espduTrans.formatStringArray(espduTrans.getArticulationParameterIdPartAttachedToArray()));//SFInt[]
//	cust.articParmTypeField.setText         (espduTrans.formatStringArray(espduTrans.getArticulationParameterTypeArray()));//SFInt[]
//	cust.articParmArrayTF.setText           (espduTrans.formatStringArray(espduTrans.getArticulationParameterArray()));//SFFloat[]
    
    int numberArticulatedParameters = new SFInt32(espduTransform.getArticulationParameterCount()).getValue();
    DefaultTableModel mydtm = (DefaultTableModel)customizer.articParamTable.getModel();
    mydtm.setRowCount(0); // clear
    String[] desig = espduTransform.getArticulationParameterDesignatorArray();
    String[] chg   = espduTransform.getArticulationParameterChangeIndicatorArray();
    String[] id    = espduTransform.getArticulationParameterIdPartAttachedToArray();
    String[] typ   = espduTransform.getArticulationParameterTypeArray();
    String[] par   = espduTransform.getArticulationParameterArray();
    
    for(int i = 1; i<=numberArticulatedParameters; i++) {
      // start w/ 1
      Object[] oa = new Object[6];
      oa[0] = i-1;
      oa[1] = desig.length>=i?desig[i-1]:ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERDESIGNATORARRAY_DFLT;
      oa[2] = chg.length>=i  ?chg[i-1]  :ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERCHANGEINDICATORARRAY_DFLT;
      oa[3] = id.length>=i   ?id[i-1]   :ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERIDPARTATTACHEDTORARRAY_DFLT;
      oa[4] = typ.length>=i  ?typ[i-1]  :ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERTYPEARRAY_DFLT;
      oa[5] = par.length>=i  ?par[i-1]  :ESPDUTRANSFORM_ATTR_ARTICULATIONPARAMETERARRAY_DFLT;
      
      mydtm.addRow(oa);
    }

    customizer.portTF.setText(espduTransform.getPort());
    customizer.siteIdTF.setText(espduTransform.getSiteID());
    customizer.centerXTF.setText(espduTransform.getCenterX());  //SFFloat	
    customizer.centerYTF.setText(espduTransform.getCenterY());  //SFFloat	
    customizer.centerZTF.setText(espduTransform.getCenterZ());  //SFFloat	
    customizer.collisionTypeCombo.setSelectedItem(espduTransform.getCollisionType());  //SFInt
    customizer.deadReckoningTF.setText(espduTransform.getDeadReckoning());  //SFInt
    customizer.detLoc0TF.setText(espduTransform.getDetonationLocationX());  //SFFloat
    customizer.detLoc1TF.setText(espduTransform.getDetonationLocationY());  //SFFloat
    customizer.detLoc2TF.setText(espduTransform.getDetonationLocationZ());  //SFFloat
    customizer.detRelLoc0TF.setText(espduTransform.getDetonationRelativeLocationX());  //SFFloat
    customizer.detRelLoc1TF.setText(espduTransform.getDetonationRelativeLocationY());  //SFFloat
    customizer.detRelLoc2TF.setText(espduTransform.getDetonationRelativeLocationZ());  //SFFloat
    customizer.detonationResultCombo.setSelectedItem(espduTransform.getDetonationResult());  //SFInt
    customizer.enabledCB.setSelected(Boolean.parseBoolean(espduTransform.isEnabled()));   //remember selected not enabled !!!

    customizer.entCategoryTF.setText(espduTransform.getEntityCategory());  //SFInt
    
    String countryNum = espduTransform.getEntityCountry();
    Country ct;
    int countryInt=0;
    try {
      countryInt = (new SFInt32(countryNum)).getValue();
      ct = Country.values()[countryInt];
      customizer.entCountryCombo.setSelectedItem(ct);    // good data
    }
    catch (NumberFormatException fex) {
      customizer.entCountryCombo.setSelectedItem(Country.OTHER);  // not an int
    }
    catch (ArrayIndexOutOfBoundsException aex) {
      customizer.entCountryCombo.setSelectedItem("" + countryInt);  // no enum match but good int
    }

    
    String entityDomainNum = espduTransform.getEntityDomain();
    PlatformDomain ed;
    int entDomInt=0;
    try {
      entDomInt = (new SFInt32(entityDomainNum)).getValue();
      ed = PlatformDomain.values()[entDomInt];
      customizer.entDomainCombo.setSelectedItem(ed);    // good data
    }
    catch (NumberFormatException nex) {
      customizer.entDomainCombo.setSelectedItem(PlatformDomain.OTHER);  // not an int
    }
    catch (ArrayIndexOutOfBoundsException aex) {
      customizer.entDomainCombo.setSelectedItem("" + entDomInt);  // no enum match but good int
    }

    customizer.entExtraTF.setText(espduTransform.getEntityExtra());  //SFInt
    customizer.entityIDTF.setText(espduTransform.getEntityID());  //SFInt
    
    String entKindNum = espduTransform.getEntityKind();
    EntityKind ek;
    int entKindInt = 0;
    try {
      entKindInt = (new SFInt32(entKindNum)).getValue();
      ek = EntityKind.values()[entKindInt];
      customizer.entKindCombo.setSelectedItem(ek);    // good data
    }
    catch (NumberFormatException nex) {
      customizer.entKindCombo.setSelectedItem(EntityKind.OTHER);  // not an int
    }
    catch (ArrayIndexOutOfBoundsException aex) {
      customizer.entKindCombo.setSelectedItem("" + entKindInt);  // no enum match but good int
    }
    
    customizer.entSpecificTF.setText(espduTransform.getEntitySpecific());
    customizer.entSubCategoryTF.setText(espduTransform.getEntitySubCategory());
    
    customizer.evAppIDTF.setText(espduTransform.getEventApplicationID());//SFInt
    customizer.evEntIDTF.setText(espduTransform.getEventEntityID());//SFInt
    customizer.evNumTF.setText(espduTransform.getEventNumber());//SFInt
    customizer.evSiteIDTF.setText(espduTransform.getEventSiteID()); //SFInt
    customizer.fired1TF.setText(espduTransform.isFired1()); // boolean
    customizer.fired2TF.setText(espduTransform.isFired2()); // boolean
    customizer.fireMissionIdxTF.setText(espduTransform.getFireMissionIndex());
    customizer.firingRangeTF.setText(espduTransform.getFiringRange());
    customizer.firingRateTF.setText(espduTransform.getFiringRate());
    
    String forceIDNum = espduTransform.getForceID();
    ForceID fid;
    int fidInt = 0;
    try {
      fidInt = (new SFInt32(forceIDNum)).getValue();
      fid = ForceID.values()[fidInt];
      customizer.forceIDCombo.setSelectedItem(fid);
    }
    catch (NumberFormatException nex) {
      customizer.forceIDCombo.setSelectedItem(ForceID.OTHER); // not an int
    }
    catch (ArrayIndexOutOfBoundsException aex) {
      customizer.forceIDCombo.setSelectedItem("" + fidInt); // no enum match but good int
    }

    customizer.fuseTF.setText(espduTransform.getFuse()); // SFInt32
    
    customizer.linVel0TF.setText(espduTransform.getLinearVelocityX()); //SFFloat
    customizer.linVel1TF.setText(espduTransform.getLinearVelocityY()); //SFFloat
    customizer.linVel2TF.setText(espduTransform.getLinearVelocityZ()); //SFFloat
    customizer.linAccel0TF.setText(espduTransform.getLinearAccelerationX()); //SFFloat
    customizer.linAccel1TF.setText(espduTransform.getLinearAccelerationY()); //SFFloat
    customizer.linAccel2TF.setText(espduTransform.getLinearAccelerationZ()); //SFFloat
    customizer.markingTF.setText(espduTransform.getMarking()); //String

    customizer.mcastRelayHostTF.setText(espduTransform.getMulticastRelayHost()); //String
    customizer.mcastRelayPort.setText(espduTransform.getMulticastRelayPort()); // SFInt32
    
    customizer.munAppIDTF.setText(espduTransform.getMunitionApplicationID()); //SFInt
    customizer.munEndPoint0TF.setText(espduTransform.getMunitionEndPointX()); //SFFloat
    customizer.munEndPoint1TF.setText(espduTransform.getMunitionEndPointY()); //SFFloat
    customizer.munEndPoint2TF.setText(espduTransform.getMunitionEndPointZ()); //SFFloat
    customizer.munEntityIDTF.setText(espduTransform.getMunitionEntityID()); //SFInt
    customizer.munitionQuantityTF.setText(espduTransform.getMunitionQuantity()); //SFInt
    customizer.munSiteIDTF.setText(espduTransform.getMunitionSiteID()); // SFInt32
    customizer.munStartPoint0TF.setText(espduTransform.getMunitionStartPointX()); // SFFloat
    customizer.munStartPoint1TF.setText(espduTransform.getMunitionStartPointY()); // SFFloat
    customizer.munStartPoint2TF.setText(espduTransform.getMunitionStartPointZ()); // SFFloat
    customizer.networkModeComboBox.setSelectedItem(espduTransform.getNetworkMode()); // String
    customizer.portTF.setText(espduTransform.getPort()); //SFInt
    customizer.readIntervalTF.setText(espduTransform.getReadInterval()); //SFFloat

    customizer.rotation0TF.setText(espduTransform.getRotationX());// SFFloat
    customizer.rotation1TF.setText(espduTransform.getRotationY());// SFFloat
    customizer.rotation2TF.setText(espduTransform.getRotationZ());// SFFloat
    customizer.rotation3TF.setText(espduTransform.getRotationAngle());// SFFloat
    customizer.scale0Tf.setText(espduTransform.getScaleX()); // SFFloat
    customizer.scale1TF.setText(espduTransform.getScaleY()); // SFFloat
    customizer.scale2TF.setText(espduTransform.getScaleZ()); // SFFloat
    customizer.scaleOrientation0TF.setText(espduTransform.getScaleOrientationX()); // SFFloat
    customizer.scaleOrientation1TF.setText(espduTransform.getScaleOrientationY()); // SFFloat
    customizer.scaleOrientation2TF.setText(espduTransform.getScaleOrientationZ()); // SFFloat
    customizer.scaleOrientation3TF.setText(espduTransform.getScaleOrientationAngle()); // SFFloat

    customizer.siteIdTF.setText(espduTransform.getSiteID()); //SFInt
    customizer.translation0TF.setText(espduTransform.getTranslationX()); //SFFloat
    customizer.translation1TF.setText(espduTransform.getTranslationY()); //SFFloat
    customizer.translation2TF.setText(espduTransform.getTranslationZ()); //SFFloat

    customizer.warheadComboBox.setSelectedItem(espduTransform.getWarhead()); // SFInt32
    customizer.writeIntervalTF.setText(espduTransform.getWriteInterval()); //SFFloat
    
    customizer.bboxCenterXTF.setText(espduTransform.getBboxCenterX()); // SFFloat
    customizer.bboxCenterYTF.setText(espduTransform.getBboxCenterY()); // SFFloat
    customizer.bboxCenterZTF.setText(espduTransform.getBboxCenterZ()); // SFFloat
    customizer.bboxSizeXTF.setText(espduTransform.getBboxSizeX()); // SFFloat
    customizer.bboxSizeYTF.setText(espduTransform.getBboxSizeY()); // SFFloat
    customizer.bboxSizeZTF.setText(espduTransform.getBboxSizeZ()); // SFFloat

    customizer.rtpHdrExpCB.setSelected(Boolean.parseBoolean(espduTransform.isRtpHeaderExpected()));                
  }
  
  private static String[] makeSA(JTextField tf)
  {
    String[] sa = tf.getText().trim().split("\\s+");
    if(sa != null && (sa.length<=0 || sa[0].length()<=0))
      sa = new String[0];
    return sa;   
  }
  
  /*public*/ static void unloadInput(ESPDUTRANSFORMCustomizer cust, ESPDUTRANSFORM espduTransform)
  {
    espduTransform.setAddress(cust.addressTF.getText().trim());
    espduTransform.setAppID(cust.appIdTF.getText().trim());
    
    int nrows = cust.articParamTable.getModel().getRowCount();
    espduTransform.setArticulationParameterCount(""+nrows);
    
    String[] desig = new String[nrows];
    String[] chang = new String[nrows];
    String[] idpar = new String[nrows];
    String[] typ   = new String[nrows];
    String[] parm = new String[nrows];
    for(int i=0;i<nrows;i++) {
      desig[i] = (String) cust.articParamTable.getValueAt(i, 1);
      chang[i] = (String) cust.articParamTable.getValueAt(i, 2);
      idpar[i] = (String) cust.articParamTable.getValueAt(i, 3);
      typ[i]   = (String) cust.articParamTable.getValueAt(i, 4);
      parm[i]  = (String) cust.articParamTable.getValueAt(i, 5);
    }
    espduTransform.setArticulationParameterDesignatorArray(desig);
    espduTransform.setArticulationParameterChangeIndicatorArray(chang);
    espduTransform.setArticulationParameterIdPartAttachedToArray(idpar);
    espduTransform.setArticulationParameterTypeArray(typ);
    espduTransform.setArticulationParameterArray(parm);
    
    espduTransform.setCenterX(cust.centerXTF.getText().trim());
    espduTransform.setCenterY(cust.centerYTF.getText().trim());
    espduTransform.setCenterZ(cust.centerZTF.getText().trim());
    espduTransform.setCollisionType(cust.collisionTypeCombo.getSelectedItem().toString().trim());
    espduTransform.setDeadReckoning(cust.deadReckoningTF.getText().trim());
    espduTransform.setDetonationLocationX(cust.detLoc0TF.getText().trim());
    espduTransform.setDetonationLocationY(cust.detLoc1TF.getText().trim());
    espduTransform.setDetonationLocationZ(cust.detLoc2TF.getText().trim());    
    espduTransform.setDetonationRelativeLocationX(cust.detRelLoc0TF.getText().trim());
    espduTransform.setDetonationRelativeLocationY(cust.detRelLoc1TF.getText().trim());
    espduTransform.setDetonationRelativeLocationZ(cust.detRelLoc2TF.getText().trim());
    espduTransform.setDetonationResult(cust.detonationResultCombo.getSelectedItem().toString().trim());
    espduTransform.setEnabled(""+cust.enabledCB.isSelected());
    
    espduTransform.setEntityCategory(cust.entCategoryTF.getText().trim());
    
    Object o = cust.entCountryCombo.getSelectedItem();
    if(o instanceof Country)
      espduTransform.setEntityCountry(""+((Country)o).getValue());
    else
      espduTransform.setEntityCountry(o.toString());
    
    o = cust.entDomainCombo.getSelectedItem();
    if(o instanceof PlatformDomain)
      espduTransform.setEntityDomain(""+((PlatformDomain)o).getValue());
    else
      espduTransform.setEntityDomain(o.toString());

    espduTransform.setEntityExtra(cust.entExtraTF.getText().trim());
    espduTransform.setEntityID(cust.entityIDTF.getText().trim());
    
    o = cust.entKindCombo.getSelectedItem();
    if(o instanceof EntityKind)
      espduTransform.setEntityKind(""+((EntityKind)o).getValue());
    else
      espduTransform.setEntityKind(o.toString());

    espduTransform.setEntitySpecific(cust.entSpecificTF.getText().trim());
    espduTransform.setEntitySubCategory(cust.entSubCategoryTF.getText().trim());
    
    espduTransform.setEventApplicationID(cust.evAppIDTF.getText().trim());
    espduTransform.setEventEntityID(cust.evEntIDTF.getText().trim());
    espduTransform.setEventNumber(cust.evNumTF.getText().trim());
    espduTransform.setEventSiteID(cust.evSiteIDTF.getText().trim());
    
    espduTransform.setFired1(cust.fired1TF.getText().trim());
    espduTransform.setFired2(cust.fired2TF.getText().trim());
    espduTransform.setFireMissionIndex(cust.fireMissionIdxTF.getText().trim());
    espduTransform.setFiringRange(cust.firingRangeTF.getText().trim());
    espduTransform.setFiringRate(cust.firingRateTF.getText().trim());
    
    o = cust.forceIDCombo.getSelectedItem();
    if(o instanceof ForceID)
      espduTransform.setForceID(""+((ForceID)o).getValue());
    else
      espduTransform.setForceID(o.toString());

    espduTransform.setFuse(cust.fuseTF.getText().trim());
    
    espduTransform.setLinearVelocityX(cust.linVel0TF.getText().trim());
    espduTransform.setLinearVelocityY(cust.linVel1TF.getText().trim());
    espduTransform.setLinearVelocityZ(cust.linVel2TF.getText().trim());
    espduTransform.setLinearAccelerationX(cust.linAccel0TF.getText().trim());
    espduTransform.setLinearAccelerationY(cust.linAccel1TF.getText().trim());
    espduTransform.setLinearAccelerationZ(cust.linAccel2TF.getText().trim());
    espduTransform.setMarking(cust.markingTF.getText().trim());
    
    espduTransform.setMulticastRelayHost(cust.mcastRelayHostTF.getText().trim());
    espduTransform.setMulticastRelayPort(cust.mcastRelayPort.getText().trim());
    
    espduTransform.setMunitionApplicationID(cust.munAppIDTF.getText().trim());
    espduTransform.setMunitionEndPointX(cust.munEndPoint0TF.getText().trim());
    espduTransform.setMunitionEndPointY(cust.munEndPoint1TF.getText().trim());
    espduTransform.setMunitionEndPointZ(cust.munEndPoint2TF.getText().trim());
    espduTransform.setMunitionEntityID(cust.munEntityIDTF.getText().trim());
    espduTransform.setMunitionQuantity(cust.munitionQuantityTF.getText().trim());
    espduTransform.setMunitionSiteID(cust.munSiteIDTF.getText().trim());
    espduTransform.setMunitionStartPointX(cust.munStartPoint0TF.getText().trim());
    espduTransform.setMunitionStartPointY(cust.munStartPoint1TF.getText().trim());
    espduTransform.setMunitionStartPointZ(cust.munStartPoint2TF.getText().trim());
    espduTransform.setNetworkMode((String)cust.networkModeComboBox.getSelectedItem());
        
    espduTransform.setPort(cust.portTF.getText().trim());
    espduTransform.setReadInterval(cust.readIntervalTF.getText().trim());
    
    espduTransform.setRotationX(cust.rotation0TF.getText().trim());
    espduTransform.setRotationY(cust.rotation1TF.getText().trim());
    espduTransform.setRotationZ(cust.rotation2TF.getText().trim());
    espduTransform.setRotationAngle(cust.rotation3TF.getText().trim());
    espduTransform.setScaleX(cust.scale0Tf.getText().trim());
    espduTransform.setScaleY(cust.scale1TF.getText().trim());
    espduTransform.setScaleZ(cust.scale2TF.getText().trim());
    espduTransform.setScaleOrientationX(cust.scaleOrientation0TF.getText().trim());
    espduTransform.setScaleOrientationY(cust.scaleOrientation1TF.getText().trim());
    espduTransform.setScaleOrientationZ(cust.scaleOrientation2TF.getText().trim());
    espduTransform.setScaleOrientationAngle(cust.scaleOrientation3TF.getText().trim());
        
    espduTransform.setSiteID(cust.siteIdTF.getText().trim());
    
    espduTransform.setTranslationX(cust.translation0TF.getText().trim());
    espduTransform.setTranslationY(cust.translation1TF.getText().trim());
    espduTransform.setTranslationZ(cust.translation2TF.getText().trim());
    
    espduTransform.setWarhead(cust.warheadComboBox.getSelectedItem().toString().trim());
    espduTransform.setWriteInterval(cust.writeIntervalTF.getText().trim());
    
    espduTransform.setBboxCenterX(cust.bboxCenterXTF.getText().trim());
    espduTransform.setBboxCenterY(cust.bboxCenterYTF.getText().trim());
    espduTransform.setBboxCenterZ(cust.bboxCenterZTF.getText().trim());
    espduTransform.setBboxSizeX(cust.bboxSizeXTF.getText().trim());
    espduTransform.setBboxSizeY(cust.bboxSizeYTF.getText().trim());
    espduTransform.setBboxSizeZ(cust.bboxSizeZTF.getText().trim());
    
    espduTransform.setRtpHeaderExpected(""+cust.rtpHdrExpCB.isSelected());
  }
  
  // Utility functions
  private static SFFloat[] handleFloatArray(BaseX3DElement el,String str)
  {
    String[] sa;
    if(str == null || str.length()<=0)
      sa = new String[]{}; // empty 
    else
      sa = parseX(str);
    return el.parseToSFFloatArray(sa);
  }
  
  private static SFInt32[] handleIntArray(BaseX3DElement el,String str)
  {
    String[] sa;
    if(str == null || str.length()<=0)
      sa = new String[]{}; // empty 
    else
      sa = parseX(str);
    return el.parseToSFIntArray(sa);
  }
  
}
