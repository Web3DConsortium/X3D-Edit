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

import org.web3d.x3d.types.SceneGraphStructureNodeType;

/**
 * SMAL_TerrainMetadataTemplate.java
 * Created on 17 October 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Don Brutzman, Mike Bailey
 * @version $Id$
 */
public class SMAL_TerrainMetadataTemplate extends SceneGraphStructureNodeType
{
  public SMAL_TerrainMetadataTemplate()
  {
  }

  @Override
  public void initialize()
  {
  }

  @Override
  public String createBody()
  {
    return "\n" +
            "    <!-- ==================== -->\n" +
            "    <WorldInfo info='\"https://savage.nps.edu/Savage/Tools/SMAL/SavageTerrainMetadataTemplate.x3d\"' title='SavageTerrainMetadataTemplate'>\n" +
            "      <MetadataSet containerField='metadata' name='SMAL' reference='https://savage.nps.edu/Savage/Tools/SMAL/SMAL.html'>\n" +
            "        <MetadataString containerField='value' name='version' value='1.0'>\n" +
            "          <MetadataString name='appinfo' value='This attribute is the SMAL version, not the model version.'/>\n" +
            "        </MetadataString>\n" +
            "        <MetadataSet containerField='value' name='TerrainTile'>\n" +
            "          <MetadataString containerField='value' name='tileCategory' value='landTerrain'>\n" +
            "            <MetadataString name='appinfo' value='landTerrain, bathymetry, or planetarySurface'/>\n" +
            "          </MetadataString>\n" +
            "          <MetadataSet containerField='value' name='Classification'>\n" +
            "            <MetadataString name='appinfo'/>\n" +
            "            <MetadataString containerField='value' name='level' value='UNCLASSIFIED'>\n" +
            "              <MetadataString name='appinfo' value='UNCLASSIFIED, FOUO, CONFIDENTIAL, SECRET, or TOPSECRET'/>\n" +
            "            </MetadataString>\n" +
            "            <MetadataString containerField='value' name='reference'>\n" +
            "              <MetadataString name='appinfo' value='The published source of classified information, if any, contained in the Metadata.'/>\n" +
            "            </MetadataString>\n" +
            "            <MetadataString containerField='value' name='rationale'>\n" +
            "              <MetadataString name='appinfo' value='The specific element which contains the information classifying this document.'/>\n" +
            "            </MetadataString>\n" +
            "          </MetadataSet>\n" +
            "          <MetadataSet containerField='value' name='GeoOrigin'>\n" +
            "            <MetadataString containerField='value' name='geoCoords' value='N00 0.0 W00 0.0'>\n" +
            "              <MetadataString name='appinfo' value='The latitude and longitude of the origin point of the model.'/>\n" +
            "            </MetadataString>\n" +
            "            <MetadataString containerField='value' name='geoSystem' value='\"GD\" \"WE\"'>\n" +
            "              <MetadataString name='appinfo' value='Two enumerations, one for spatial reference frame, the other for earth ellipsoid e.g. [\"GD\" \"WE\"]. See X3D specification 25.2.3.'/>\n" +
            "            </MetadataString>\n" +
            "            <MetadataString containerField='value' name='rotateYUp' value='true'>\n" +
            "              <MetadataString name='appinfo' value='The axis orientation is positive Y up.'/>\n" +
            "            </MetadataString>\n" +
            "            <MetadataString containerField='value' name='geoOriginIdentifier' value='ExampleGeoOriginInstance'>\n" +
            "              <MetadataString name='appinfo' value='GeoOrigin elements must have a unique ID so that they can be referenced.'/>\n" +
            "            </MetadataString>\n" +
            "          </MetadataSet>\n" +
            "          <MetadataSet containerField='value' name='GeographicExtent'>\n" +
            "            <MetadataString name='appinfo' value='Describes the size and shape of the terrain model in two and a half dimensions; vertical extent and a polygonal shape defined by a minimum of three LatLongCoordinates.'/>\n" +
            "            <MetadataFloat containerField='value' name='area' value='0'>\n" +
            "              <MetadataString name='appinfo'/>\n" +
            "            </MetadataFloat>\n" +
            "            <MetadataFloat containerField='value' name='verticalExtent' value='0'>\n" +
            "              <MetadataString name='appinfo' value='Vertical depth of the model in meters from the lowest to the highest point on the model.'/>\n" +
            "            </MetadataFloat>\n" +
            "            <MetadataSet containerField='value' name='LatLongCoordinate'>\n" +
            "              <MetadataString name='appinfo' value='A latitude and longitude coordinate pair.'/>\n" +
            "              <MetadataString containerField='value' name='latitude' value='N00 00.0'/>\n" +
            "              <MetadataString containerField='value' name='longitude' value='W00 00.0'/>\n" +
            "              <MetadataString containerField='value' name='geoOriginReference' value='ExampleGeoOriginInstance'/>\n" +
            "            </MetadataSet>\n" +
            "            <MetadataSet containerField='value' name='LatLongCoordinate'>\n" +
            "              <MetadataString name='appinfo' value='A latitude and longitude coordinate pair.'/>\n" +
            "              <MetadataString containerField='value' name='latitude' value='N00 00.0'/>\n" +
            "              <MetadataString containerField='value' name='longitude' value='W00 00.0'/>\n" +
            "              <MetadataString containerField='value' name='geoOriginReference' value='ExampleGeoOriginInstance'/>\n" +
            "            </MetadataSet>\n" +
            "            <MetadataSet containerField='value' name='LatLongCoordinate'>\n" +
            "              <MetadataString name='appinfo' value='A latitude and longitude coordinate pair.'/>\n" +
            "              <MetadataString containerField='value' name='latitude' value='N00 00.0'/>\n" +
            "              <MetadataString containerField='value' name='longitude' value='W00 00.0'/>\n" +
            "              <MetadataString containerField='value' name='geoOriginReference' value='ExampleGeoOriginInstance'/>\n" +
            "            </MetadataSet>\n" +
            "          </MetadataSet>\n" +
            "          <MetadataSet containerField='value' name='OverlaySet'>\n" +
            "            <MetadataString name='appinfo' value='The collection point for all image file locators that are associated with this terrain.'/>\n" +
            "            <MetadataSet containerField='value' name='Classification'>\n" +
            "              <MetadataString containerField='value' name='level' value='UNCLASSIFIED'>\n" +
            "                <MetadataString name='appinfo' value='UNCLASSIFIED, FOUO, CONFIDENTIAL, SECRET, or TOPSECRET'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='reference'>\n" +
            "                <MetadataString name='appinfo' value='The published source of classified information, if any, contained in the Metadata.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='rationale'>\n" +
            "                <MetadataString name='appinfo' value='The specific element which contains the information classifying this document.'/>\n" +
            "              </MetadataString>\n" +
            "            </MetadataSet>\n" +
            "            <MetadataSet containerField='value' name='OverlaySetMap'>\n" +
            "              <MetadataString name='appinfo' value='A Map image.'/>\n" +
            "              <MetadataSet containerField='value' name='Classification'>\n" +
            "                <MetadataString containerField='value' name='level' value='UNCLASSIFIED'>\n" +
            "                  <MetadataString name='appinfo' value='UNCLASSIFIED, FOUO, CONFIDENTIAL, SECRET, or TOPSECRET'/>\n" +
            "                </MetadataString>\n" +
            "                <MetadataString containerField='value' name='reference'>\n" +
            "                  <MetadataString name='appinfo' value='The published source of classified information, if any, contained in the Metadata.'/>\n" +
            "                </MetadataString>\n" +
            "                <MetadataString containerField='value' name='rationale'>\n" +
            "                  <MetadataString name='appinfo' value='The specific element which contains the information classifying this document.'/>\n" +
            "                </MetadataString>\n" +
            "              </MetadataSet>\n" +
            "              <MetadataString containerField='value' name='fileLocationURL' value='https://savage.nps.edu/Savage/'>\n" +
            "                <MetadataString name='appinfo' value='The URL of the image file.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='centerPointLatitude' value='N00 0.0'>\n" +
            "                <MetadataString name='appinfo' value='The latitude of the center point of the image.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='centerPointLongitude' value='W00 0.0'>\n" +
            "                <MetadataString name='appinfo' value='The longitude of the center point of the image.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='northBoundLatitude' value='N00 0.0'>\n" +
            "                <MetadataString name='appinfo' value='The northernmost latitude found on the image.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='southBoundLatitude' value='N00 0.0'>\n" +
            "                <MetadataString name='appinfo' value='The southernmost latitude found on the image.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='eastBoundLongitude' value='W00 0.0'>\n" +
            "                <MetadataString name='appinfo' value='The easternmost logitude found on the image.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='westBoundLongitude' value='W00 0.0'>\n" +
            "                <MetadataString name='appinfo' value='The westernmost longitude found on the image.'/>\n" +
            "              </MetadataString>\n" +
            "            </MetadataSet>\n" +
            "            <MetadataSet containerField='value' name='OverlaySetChart'>\n" +
            "              <MetadataString name='appinfo' value='A Chart image.'/>\n" +
            "              <MetadataSet containerField='value' name='Classification'>\n" +
            "                <MetadataString containerField='value' name='level' value='UNCLASSIFIED'>\n" +
            "                  <MetadataString name='appinfo' value='UNCLASSIFIED, FOUO, CONFIDENTIAL, SECRET, or TOPSECRET'/>\n" +
            "                </MetadataString>\n" +
            "                <MetadataString containerField='value' name='reference'>\n" +
            "                  <MetadataString name='appinfo' value='The published source of classified information, if any, contained in the Metadata.'/>\n" +
            "                </MetadataString>\n" +
            "                <MetadataString containerField='value' name='rationale'>\n" +
            "                  <MetadataString name='appinfo' value='The specific element which contains the information classifying this document.'/>\n" +
            "                </MetadataString>\n" +
            "              </MetadataSet>\n" +
            "              <MetadataString containerField='value' name='fileLocationURL' value='https://savage.nps.edu/Savage/'>\n" +
            "                <MetadataString name='appinfo' value='The URL of the image file.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='centerPointLatitude' value='N00 0.0'>\n" +
            "                <MetadataString name='appinfo' value='The latitude of the center point of the image.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='centerPointLongitude' value='W00 0.0'>\n" +
            "                <MetadataString name='appinfo' value='The longitude of the center point of the image.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='northBoundLatitude' value='N00 0.0'>\n" +
            "                <MetadataString name='appinfo' value='The northernmost latitude found on the image.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='southBoundLatitude' value='N00 0.0'>\n" +
            "                <MetadataString name='appinfo' value='The southernmost latitude found on the image.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='eastBoundLongitude' value='W00 0.0'>\n" +
            "                <MetadataString name='appinfo' value='The easternmost logitude found on the image.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='westBoundLongitude' value='W00 0.0'>\n" +
            "                <MetadataString name='appinfo' value='The westernmost longitude found on the image.'/>\n" +
            "              </MetadataString>\n" +
            "            </MetadataSet>\n" +
            "            <MetadataSet containerField='value' name='OverlaySetImagery'>\n" +
            "              <MetadataString name='appinfo'/>\n" +
            "              <MetadataSet containerField='value' name='Classification'>\n" +
            "                <MetadataString containerField='value' name='level' value='UNCLASSIFIED'>\n" +
            "                  <MetadataString name='appinfo' value='UNCLASSIFIED, FOUO, CONFIDENTIAL, SECRET, or TOPSECRET'/>\n" +
            "                </MetadataString>\n" +
            "                <MetadataString containerField='value' name='reference'>\n" +
            "                  <MetadataString name='appinfo' value='The published source of classified information, if any, contained in the Metadata.'/>\n" +
            "                </MetadataString>\n" +
            "                <MetadataString containerField='value' name='rationale'>\n" +
            "                  <MetadataString name='appinfo' value='The specific element which contains the information classifying this document.'/>\n" +
            "                </MetadataString>\n" +
            "              </MetadataSet>\n" +
            "              <MetadataString containerField='value' name='fileLocationURL' value='https://savage.nps.edu/Savage/'>\n" +
            "                <MetadataString name='appinfo' value='The URL of the image file.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='centerPointLatitude' value='N00 0.0'>\n" +
            "                <MetadataString name='appinfo' value='The latitude of the center point of the image.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='centerPointLongitude' value='W00 0.0'>\n" +
            "                <MetadataString name='appinfo' value='The longitude of the center point of the image.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='northBoundLatitude' value='N00 0.0'>\n" +
            "                <MetadataString name='appinfo' value='The northernmost latitude found on the image.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='southBoundLatitude' value='N00 0.0'>\n" +
            "                <MetadataString name='appinfo' value='The southernmost latitude found on the image.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='eastBoundLongitude' value='W00 0.0'>\n" +
            "                <MetadataString name='appinfo' value='The easternmost logitude found on the image.'/>\n" +
            "              </MetadataString>\n" +
            "              <MetadataString containerField='value' name='westBoundLongitude' value='W00 0.0'>\n" +
            "                <MetadataString name='appinfo' value='The westernmost longitude found on the image.'/>\n" +
            "              </MetadataString>\n" +
            "            </MetadataSet>\n" +
            "          </MetadataSet>\n" +
            "          <MetadataSet containerField='value' name='X3DArchiveModel'>\n" +
            "            <MetadataString name='appinfo' value='This is a placeholder element which ensures the proper validation of autogenerated SMAL code.'/>\n" +
            "          </MetadataSet>\n" +
            "        </MetadataSet>\n" +
            "      </MetadataSet>\n" +
            "    </WorldInfo>\n" +
            "    <!-- ==================== -->\n";
  }

  @SuppressWarnings("unchecked")
  @Override
  /**
   * @return null, no customizer, author can simply edit ProtoInstance
   */
  public Class<? extends BaseCustomizer> getCustomizer()
  {
    return null;
  }

  @Override
  public String getElementName()
  {
    return "SMAL_TerrainMetadataTemplate";
  }
}
