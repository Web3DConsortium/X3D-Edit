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

import javax.swing.text.AbstractDocument;
import org.web3d.x3d.options.X3dOptions;
import org.web3d.x3d.types.SceneGraphStructureNodeType;
import static org.web3d.x3d.types.X3DPrimitiveTypes.*;

/**
 * PROTOTYPE_ExtrusionCrossSection.java
 * Created on 16 June 2009
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author Don Brutzman, Mike Bailey
 * @version $Id$
 */
public class PROTOTYPE_ExtrusionCrossSection extends SceneGraphStructureNodeType
{
  private   EXTRUSION extrusion;
  protected AbstractDocument abstractDocument;       // example in EditElementAction

  public PROTOTYPE_ExtrusionCrossSection()
  {
  }

  private static String externProtoDeclare =
            "    <ExternProtoDeclare name='ExtrusionCrossSection' appinfo='Shape prototype for Extrusion node that also draws spine line plus oriented scaled cross sections at each spine point' url='\"../../Basic/course/ExtrusionCrossSectionPrototype.x3d#ExtrusionCrossSection\" \"../../../Basic/course/ExtrusionCrossSectionPrototype.x3d#ExtrusionCrossSection\" \"https://www.web3d.org/x3d/content/examples/Basic/course/ExtrusionCrossSectionPrototype.x3d#ExtrusionCrossSection\"'>\n" +
            "      <field accessType='initializeOnly' name='name' type='SFString' appinfo='Extrusion name'/>\n" +
            "      <field accessType='initializeOnly' name='crossSection' type='MFVec2f' appinfo='Extrusion field'/>\n" +
            "      <field accessType='initializeOnly' name='spine' type='MFVec3f' appinfo='Extrusion field'/>\n" +
            "      <field accessType='initializeOnly' name='scale' type='MFVec2f' appinfo='Extrusion field'/>\n" +
            "      <field accessType='initializeOnly' name='orientation' type='MFRotation' appinfo='Extrusion field'/>\n" +
            "      <field accessType='initializeOnly' name='beginCap' type='SFBool' appinfo='Extrusion field'/>\n" +
            "      <field accessType='initializeOnly' name='endCap' type='SFBool' appinfo='Extrusion field'/>\n" +
            "      <field accessType='initializeOnly' name='ccw' type='SFBool' appinfo='Extrusion field'/>\n" +
            "      <field accessType='initializeOnly' name='convex' type='SFBool' appinfo='Extrusion field'/>\n" +
            "      <field accessType='initializeOnly' name='creaseAngle' type='SFFloat' appinfo='Extrusion field'/>\n" +
            "      <field accessType='initializeOnly' name='solid' type='SFBool' appinfo='Extrusion field'/>\n" +
            "      <field accessType='inputOutput' name='spineColor' type='SFColor' appinfo='emissiveColor of spine'/>\n" +
            "      <field accessType='inputOutput' name='crossSectionColor' type='SFColor' appinfo='diffuseColor of crossSection'/>\n" +
            "      <field accessType='inputOutput' name='crossSectionTransparency' type='SFFloat' appinfo='transparency of crossSection'/>\n" +
            "      <field accessType='inputOutput' name='extrusionColor' type='SFColor' appinfo='diffuseColor of extrusion hull'/>\n" +
            "      <field accessType='inputOutput' name='extrusionTransparency' type='SFFloat' appinfo='transparency of extrusion hull'/>\n" +
            "      <field accessType='initializeOnly' name='extrusionImageTexture' type='SFNode' appinfo='Image to apply to Extrusion'/>\n" +
            "      <field accessType='initializeOnly' name='extrusionTextureTransform' type='SFNode' appinfo='TextureTransform coordinates of image applied to Extrusion'/>\n" +
            "      <field accessType='inputOnly' name='set_crossSection' type='MFVec2f' appinfo='Extrusion field'/>\n" +
            "      <field accessType='inputOnly' name='set_spine' type='MFVec3f' appinfo='Extrusion field'/>\n" +
            "      <field accessType='inputOnly' name='set_scale' type='MFVec2f' appinfo='Extrusion field'/>\n" +
            "      <field accessType='inputOnly' name='set_orientation' type='MFRotation' appinfo='Extrusion field'/>\n" +
            "      <field accessType='initializeOnly' name='traceEnabled' type='SFBool' appinfo='Whether to provide initialization trace statements showing node construction'/>\n" +
            "    </ExternProtoDeclare>\n" +
            "    <!-- Only one copy of a given ExternProtoDeclare is needed in a scene. Multiple ProtoInstance nodes can be created like the following: -->\n";

  @Override
  public void initialize()
  {
      // default example values
      extrusion = new EXTRUSION();

      extrusion.setCrossSection(new String[][]{{"1","1"},{"1","-1"},{"-1","-1"},{"-1","1"},{"1","1"}}); // "1 1, 1 -1, -1 -1, -1 1, 1 1"
      extrusion.setSpine(       new String[][]{{"0","0","0"},{"0","1","0"}});
      extrusion.setScale(       new String[][]{{"1","1"}});
      extrusion.setOrientation( new String[][]{{"0","1","0","0"}});
      extrusion.setBeginCap(false);
      extrusion.setEndCap(false);
      extrusion.setCcw(true);
      extrusion.setConvex(true);
      extrusion.setCreaseAngle("0");
      extrusion.setSolid(true);
  }

  @Override
  public String createBody()
  {
        StringBuilder prototypeInsertion = new StringBuilder ();
        boolean externProtoDeclareFound;

        externProtoDeclareFound = false; // TODO documentIncludesString("<ExternProtoDeclare name='ExtrusionCrossSection'");

        if (externProtoDeclareFound) // only insert ExternProtoDeclare if not already found
        {
             prototypeInsertion.append("\n");
             prototypeInsertion.append(getProtoInstance());
        }
        else
        {
             prototypeInsertion.append("\n");
             prototypeInsertion.append("    <!-- ==================== -->\n");
             prototypeInsertion.append(getExternProtoDeclare());
             prototypeInsertion.append(getProtoInstance());
             prototypeInsertion.append("    <!-- ==================== -->\n");
        }

      return prototypeInsertion.toString();
  }

    /**
     * @return the externProtoDeclareInstantiation
     */
    public String getProtoInstance()
    {
        String lineColor         = X3dOptions.getVisualizeLineColor ();
        String shapeColor        = X3dOptions.getVisualizeShapeColor();
        String shapeTransparency = X3dOptions.getVisualizeTransparency();

    return  "    <ProtoInstance name='ExtrusionCrossSection'>\n" +
            "      <fieldValue name='name' value=''/>\n" +
            "      <fieldValue name='crossSection' value='"             + extrusion.getCrossSectionString() + "'/>\n" +
            "      <fieldValue name='spine' value='"                    + extrusion.getSpineString() + "'/>\n" +
            "      <fieldValue name='scale' value='"                    + extrusion.getScaleString() + "'/>\n" +
            "      <fieldValue name='orientation' value='"              + extrusion.getOrientationString() + "'/>\n" +
            "      <fieldValue name='beginCap' value='"                 + extrusion.isBeginCap() + "'/>\n" +
            "      <fieldValue name='endCap' value='"                   + extrusion.isEndCap() + "'/>\n" +
            "      <fieldValue name='ccw' value='"                      + extrusion.isCcw() + "'/>\n" +
            "      <fieldValue name='convex' value='"                   + extrusion.isConvex() + "'/>\n" +
            "      <fieldValue name='creaseAngle' value='"              + extrusion.getCreaseAngle() + "'/>\n" +
            "      <fieldValue name='solid' value='"                    + extrusion.isSolid() + "'/>\n" +
            "      <fieldValue name='spineColor' value='"               + lineColor + "'/>\n" +
            "      <fieldValue name='crossSectionColor' value='"        + "0.5 0.5 0.5'/>\n" +
            "      <fieldValue name='crossSectionTransparency' value='" + ((new SFFloat(shapeTransparency)).getValue() / 3.0f) + "'/>\n" +
            "      <fieldValue name='extrusionColor' value='"           + shapeColor + "'/>\n" +
            "      <fieldValue name='extrusionTransparency' value='"    + shapeTransparency + "'/>\n" +
            "      <fieldValue name='traceEnabled' value='"             + "false'/>\n" +
            "    </ProtoInstance>\n" +
            "    <!-- Add any ROUTEs here that connect ProtoInstance to/from prior nodes in Scene -->\n" +
            "    <!-- Example use: https://www.web3d.org/x3d/content/examples/Basic/course/ExtrusionCrossSectionExample.x3d -->\n";
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
    return "ExtrusionCrossSection";
  }

    /**
     * @return the externProtoDeclare
     */
    public String getExternProtoDeclare() {
        return externProtoDeclare;
    }

    /**
     * @return the extrusion so that individual parameters can be set
     */
    public EXTRUSION getExtrusion() {
        return extrusion;
    }

    /**
     * @param extrusion the extrusion to set
     */
    public void setExtrusion(EXTRUSION extrusion) {
        this.extrusion = extrusion;
    }
}
