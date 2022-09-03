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
package org.web3d.x3d.actions.conversions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Vector;
import org.openide.filesystems.FileUtil;
import org.web3d.x3d.X3DDataObject;
import org.web3d.x3d.X3DEditorSupport;
import org.web3d.x3d.options.X3dOptions;
import xj3d.filter.CDFFilter;

/**
 * CadFilterHelper.java
 * Created on Jul 22, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * @author mike
 * @version $Id$
 */
public class CadFilterHelper
{
  public static String XJ3D_FILTERARG_LOGLEVEL = "-loglevel";
  public static String XJ3D_FILTERARG_VERSION  = "-exportVersion";
  public static String XJ3D_FILTERARG_COMPRESS = "-compressionMethod";
  public static String XJ3D_FILTERARG_QUANT    = "-quantization";
  public static String XJ3D_FILTERARG_UPGRADE  = "-upgrade";

  public static String XJ3D_FILTER_IDENTITY             = "Identity";
  public static String XJ3D_FILTER_DEFUSE_IMAGE_TEXTURE = "DEFUSEImageTexture";
  public static String XJ3D_FILTER_IFSTOTS              = "IFSToTS";
  public static String XJ3D_FILTER_IFSTOITS             = "IFSToITS";
  public static String XJ3D_FILTER_DEBUG                = "Debug";
  public static String XJ3D_FILTER_GENNORMALS           = "GenNormals";
  public static String XJ3D_FILTER_CENTER               = "Center";
  public static String XJ3D_FILTER_COMBINESHAPES        = "CombineShapes";
  public static String XJ3D_FILTER_SHORTENDEF           = "ShortenDEF";
  public static String XJ3D_FILTER_MINPROFILE           = "MinProfile";
  public static String XJ3D_FILTER_ABSSCALE             = "AbsScale";
  public static String XJ3D_FILTER_FLATTENTRANSFORM     = "FlattenTransform";
  public static String XJ3D_FILTER_FLATTENTEXTURETRANSFORM= "FlattenTextureTransform";
  public static String XJ3D_FILTER_FLATTENSELECTABLE    = "FlattenSelectable"; 
  public static String XJ3D_FILTER_INDEX                = "Index";
  public static String XJ3D_FILTER_REINDEX              = "ReIndex";
  public static String XJ3D_FILTER_TRIANGULATION        = "Triangulation";
  public static String XJ3D_FILTER_TRIANGLECOUNTINFO    = "TriangleCountInfo";
  public static String XJ3D_FILTER_MODIFYVIEWPOINT      = "ModifyViewpoint";
  public static String XJ3D_FILTER_MATERIALFILTER       = "MaterialFilter"; //*
  public static String XJ3D_FILTER_APPEARANCEFILTER     = "AppearanceFilter"; //*

  public static void doFilter(X3DDataObject x3dDO, File outFile)
  {
    Vector<String> filterArgs = new Vector<>();
    filterArgs.add(XJ3D_FILTERARG_LOGLEVEL);
    filterArgs.add(X3dOptions.getCadFilterLogLevel());

    filterArgs.add(XJ3D_FILTERARG_VERSION);
    filterArgs.add(X3dOptions.getCadFilterX3dVersion());

    filterArgs.add(XJ3D_FILTERARG_COMPRESS);
    filterArgs.add(X3dOptions.getCadFilterBinaryCompressionMethod());

    filterArgs.add(XJ3D_FILTERARG_QUANT); // this is listed in GUI as in the filter, but in xj3d src as filter arg
    filterArgs.add(X3dOptions.getCadFilterFloatingPointQuantization());

    //filterArgs.add(XJ3D_FILTERARG_UPGRADE);
    //filterArgs.add(?);

    Vector<String> filters = new Vector<>();

    if (X3dOptions.getCadFiltersEnabledRadioButton()) {
      String s = X3dOptions.getCadFilterAbsScaleFactor();
      if(!s.equals(X3dOptions.CADFILTER_DEFAULT_ABS_SCALE_FACTOR)) {
        filters.add(XJ3D_FILTER_ABSSCALE);
        filters.add(s);
      }
      if (X3dOptions.getCadFilterTriangleCount())
        filters.add(XJ3D_FILTER_TRIANGLECOUNTINFO);
      //if (X3dOptions.getCadFilterEmbedProto());//filters.add(XJ3D_);
      if (X3dOptions.getCadFilterMinimumProfile())
        filters.add(XJ3D_FILTER_MINPROFILE);
      //if (X3dOptions.getCadFilterBoundingBoxes());//filters.add(XJ3D_);
      if (X3dOptions.getCadFilterIFStoITS())
        filters.add(XJ3D_FILTER_IFSTOITS);
      if (X3dOptions.getCadFilterCenterFilter())
        filters.add(XJ3D_FILTER_CENTER);
      if (X3dOptions.getCadFilterIFStoTS())
        filters.add(XJ3D_FILTER_IFSTOTS);
      if (X3dOptions.getCadFilterCombineShapes())
        filters.add(XJ3D_FILTER_COMBINESHAPES);
      if (X3dOptions.getCadFilterIndexFilter())
        filters.add(XJ3D_FILTER_INDEX);
      if (X3dOptions.getCadFilterDefuseImageTexture())
        filters.add(XJ3D_FILTER_DEFUSE_IMAGE_TEXTURE);
      if (X3dOptions.getCadFilterModifyViewpoint())
        filters.add(XJ3D_FILTER_MODIFYVIEWPOINT);
      if (X3dOptions.getCadFilterFlattenTransforms())
        filters.add(XJ3D_FILTER_FLATTENTRANSFORM);
      if (X3dOptions.getCadFilterFlattenTextureTransforms())
        filters.add(XJ3D_FILTER_FLATTENTEXTURETRANSFORM);
      if (X3dOptions.getCadFilterFlattenSelectable())
        filters.add(XJ3D_FILTER_FLATTENSELECTABLE);
      if (X3dOptions.getCadFilterDefNameShortened())
        filters.add(XJ3D_FILTER_SHORTENDEF);
      if (X3dOptions.getCadFilterGenerateNormals())
        filters.add(XJ3D_FILTER_GENNORMALS);
      if (X3dOptions.getCadFilterTriangulationFilter())
        filters.add(XJ3D_FILTER_TRIANGULATION);
      if (X3dOptions.getCadFilterReIndex())
        filters.add(XJ3D_FILTER_REINDEX);
      if (X3dOptions.getCadFilterDebug())
        filters.add(XJ3D_FILTER_DEBUG);
    }
    else
      filters.add(XJ3D_FILTER_IDENTITY);

    CDFFilter filterer = new CDFFilter();
    String[] filterArr = new String[filters.size()];
    filterArr = filters.toArray(filterArr);
    String[] argsArr = new String[filterArgs.size()];
    argsArr = filterArgs.toArray(argsArr);

    try {
      File tmpInFile = createTempFileInSitu(x3dDO);

      System.out.println("Arguments to CDFFilter.filter():");
      System.out.println("Filters:");
      for(String s : filterArr)
        System.out.println(s);
      System.out.println("Input file: "+tmpInFile.getAbsolutePath());
      System.out.println("Output file: "+outFile.getAbsolutePath());
      System.out.println("Filter arguments");
      for(String s : argsArr)
        System.out.println(s);

      filterer.filter(filterArr, tmpInFile, outFile.getAbsolutePath(), argsArr);
    }
    catch(IOException t) {
      System.out.println("Exception from CDFFilter: "+t.getLocalizedMessage());
    }
  }

  private static File lastTemp;
  /* create a temp file for cadfilter...todo, determine if really required
   */
  public static File createTempFileInSitu(X3DDataObject x3dDO) throws IOException
  {
    InputStream is = x3dDO.getLookup().lookup(X3DEditorSupport.class).getInputStream();
    String origNameWoExt = x3dDO.getPrimaryFile().getName();
    File orig = FileUtil.toFile(x3dDO.getPrimaryFile());
    File parent = orig.getParentFile();

    File temp = File.createTempFile(origNameWoExt, ".x3d", parent);
    temp.deleteOnExit();

    OutputStream os = new FileOutputStream(temp);

    int b;
    while ((b = is.read()) != -1) {
      os.write(b);
    }
    os.close();
    if(lastTemp != null)
      lastTemp.delete();
    lastTemp = temp;
    return temp;
  }
}
