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
package org.web3d.x3d;

/*
 * PixelTextureGenerator.java
 * http://web.nps.navy.mil/~brutzman/Savage/Tools/Authoring/PixelTextureGenerator.java
 *
 * Created on June 10, 2004, 12:24 AM
 * Modified on June 17, 2004, 1:46 AM
 * Revised 24-26 January 2005, Don Brutzman
 * Revised 23 February 2005, Don Brutzman
 * Revised 4 January 2006, Don Brutzman
 * Revised 3 January 2007, Don Brutzman
 */

import java.awt.*;
import java.awt.image.*;
import java.io.*;

/**
 *
 * @author  Louis Gutierrez
 * @author  Don Brutzman
 * References:
 * http://java.sun.com/j2se/1.4.2/docs/api/java/awt/image/PixelGrabber.html
 * http://www.geocities.com/marcoschmidt.geo/java-image-file-code-examples.html
 */

public class PixelTextureGenerator extends Frame
{
	private Image image;
        private File outputFile;
        private FileWriter out;
        private int imageHeight, imageWidth;
        private String outputFilename;
        private StringBuffer outputPixelString = new StringBuffer();

        static String  UsageMessage   = "usage: java PixelTextureGenerator imageName.ext [outputSceneName.x3d]";

	public PixelTextureGenerator(String imageFilename, String pOutputFilename) throws IOException
	{
	  try
	  {
        String name = new String();
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		image = toolkit.getImage(imageFilename);
		MediaTracker mediaTracker = new MediaTracker(this);
		mediaTracker.addImage(image, 0);
		try
		{
			mediaTracker.waitForID(0); //makes sure image loads
		}
		catch (InterruptedException ie)
		{
			System.err.println(ie);
			return;
		}

                //string manipulation for output file and header
                name = imageFilename.substring(imageFilename.lastIndexOf("\\")+1,imageFilename.lastIndexOf("."));
                if (pOutputFilename.length() == 0)
                {
                	outputFilename = name + "PixelTexture.x3d";
                }
                else	outputFilename = pOutputFilename;

        //      System.out.println ("[outputFilename=" + outputFilename + "]");

                System.out.print ("..");
                outputFile = new File( outputFilename );
                out = new FileWriter( outputFile );
                System.out.print (".");

                imageWidth  = image.getWidth (null);
                imageHeight = image.getHeight(null);
                header(outputFilename, imageFilename, imageWidth, imageHeight);
                handlePixels(image, 0, 0, imageWidth, imageHeight);
                footer(imageWidth, imageHeight);
                System.out.println ("created " + outputFile);
	  }
	  catch (IOException e)
	  {
		 System.err.println(e);
      // return;
	  }
	}

        // modified constructor which saves output as StringBuffer
	public PixelTextureGenerator(String imageFilename)
	{
	  try
	  {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		image = toolkit.getImage(imageFilename);
		MediaTracker mediaTracker = new MediaTracker(this);
		mediaTracker.addImage(image, 0);
		try
		{
			mediaTracker.waitForID(0); //makes sure image loads
		}
		catch (InterruptedException ie)
		{
			System.err.println(ie);
			return;
		}
                imageWidth  = image.getWidth (null);
                imageHeight = image.getHeight(null);
                System.out.println ("PixelTextureGenerator sizes: width=" + imageWidth + ", height=" + imageHeight);
                
            int[] pixels = new int[imageWidth * imageHeight]; //array of pixels to grab
            PixelGrabber pg = new PixelGrabber(image, 0, 0, imageWidth, imageHeight, pixels, 0, imageWidth);
            try {
                pg.grabPixels();
            } catch (InterruptedException e) {
                System.err.println("[Error] interrupted waiting for pixels!");
                return;
            }
            if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
                System.err.println("[Error] image fetch abort or error");
                return;
            }
            //cycles through the array of pixels
            // SFImage:  Pixels are specified from left to right, bottom to top
         // for (int j = 0; j < iHeight; j++) {
            for (int j = imageHeight - 1; j >= 0; j--) {
                for (int i = 0; i < imageWidth; i++) {
                    outputPixelString.append(handleSinglePixel(i, j, pixels[j * imageWidth + i]));
                }
                // break long lines of pixels for countability and to help some parsers/editors
                outputPixelString.append("\n");
            }
          }
	  catch (IOException e)
	  {
		 System.err.println(e);
	  // return;
	  }
        }

/**
 * This method uses pixelGrabber to grab pixels from an image and then each pixel is
 * manipulated into a hex string and written to file.
 */
        public void handlePixels(Image img, int x, int y, int iWidth, int iHeight) throws IOException
        {
          try
          {
            out.write(iWidth + " " + iHeight + " " + "3 "); //default is 256*256*256 colors
            out.write("\n");
            int[] pixels = new int[iWidth * iHeight]; //array of pixels to grab
            PixelGrabber pg = new PixelGrabber(img, x, y, iWidth, iHeight, pixels, 0, iWidth);
            try {
                pg.grabPixels();
            } catch (InterruptedException e) {
                System.err.println("[Error] interrupted waiting for pixels!");
                return;
            }
            if ((pg.getStatus() & ImageObserver.ABORT) != 0) {
                System.err.println("[Error] image fetch abort or error");
                return;
            }
            //cycles through the array of pixels
            // SFImage:  Pixels are specified from left to right, bottom to top
         // for (int j = 0; j < iHeight; j++) {
            for (int j = iHeight - 1; j >= 0; j--) {
                for (int i = 0; i < iWidth; i++) {
                    out.write(handleSinglePixel(x+i, y+j, pixels[j * iWidth + i]));
                }
                // break long lines of pixels for countability and to help some parsers/editors
                out.write("\n");
            }
            out.flush();
          }
          catch (IOException e)
          {
          	System.err.println (e);
          }
        }

/**
 * This method translates the integer value passed by pixeGrabber into
 * alpha, red, green, and blue.  It then converts these integers to a hex
 * string.
 */
        public String handleSinglePixel(int x, int y, int pixel) throws IOException{
            //translates pixel integer
            int alpha = (pixel >> 24) & 0xff;
            int red   = (pixel >> 16) & 0xff;
            int green = (pixel >>  8) & 0xff;
            int blue  = (pixel      ) & 0xff;

            //converts to hex
            return hexConverter(red,green, blue);
        }

/**
 * This method produces a string that represents a 6 digit hex value
 * and writes it to file.
 */
        public String hexConverter(int r,int g,int b) throws IOException{
            String temp = "0x";

            temp += hexCase(r/16);
            temp += hexCase(r%16);
            temp += hexCase(g/16);
            temp += hexCase(g%16);
            temp += hexCase(b/16);
            temp += hexCase(b%16);

            //writes hex value to file
            return (temp + " ");
        }

/**
 * pThis method simply changes standard form into hex
 */
        public char hexCase(int c) {
            char out='Z';
                switch (c) {
                    case 0:   out = '0'; break;
                    case 1:   out = '1'; break;
                    case 2:  out = '2'; break;
                    case 3:  out = '3'; break;
                    case 4:  out = '4'; break;
                    case 5:  out = '5'; break;
                    case 6:  out = '6'; break;
                    case 7:  out = '7'; break;
                    case 8:  out = '8'; break;
                    case 9:  out = '9'; break;
                    case 10:  out = 'A'; break;
                    case 11:  out = 'B'; break;
                    case 12:  out = 'C'; break;
                    case 13:  out = 'D'; break;
                    case 14:  out = 'E'; break;
                    case 15:  out = 'F'; break;
                    default:  System.out.println("incorrect color value");
                }
            return out;
        }

/**
 * This method writes the header information to file
 */
        public void header(String outputFileName, String imageFilename, int x, int y) throws IOException{
            out.write("<?xml version='1.0' encoding='UTF-8'?>\n");
            // final DOCTYPE
            out.write("<!DOCTYPE X3D PUBLIC \"ISO//Web3D//DTD X3D 3.3//EN\" \"https://www.web3d.org/specifications/x3d-3.3.dtd\">\n");
            out.write("<X3D version='3.3' profile='Interactive'\n");
            out.write("  xmlns:xsd='http://www.w3.org/2001/XMLSchema-instance' xsd:noNamespaceSchemaLocation='https://www.web3d.org/specifications/x3d-3.3.xsd'>\n");
            out.write("  <head>\n");
            out.write("    <meta content='" + outputFileName + "' name='title'/>\n");
            out.write("    <meta content='" + imageFilename + "' name='image'/>\n");
            out.write("    <meta content='Image converted from binary format into an X3D PixelTexture.' name='description'/>\n");
            out.write("    <!-- edit default meta tag entries to document this converted scene -->\n");
            out.write("    <meta content='*enter name of original author here*' name='creator'/>\n");
            out.write("    <meta content='*if manually translating image-to-X3D, enter name of person translating here*' name='translator'/>\n");
            out.write("    <meta content='*enter date*' name='created'/>\n");
            out.write("    <meta content='*enter date*' name='translated'/>\n");
            out.write("    <meta content='*enter date*' name='modified'/>\n");
            out.write("    <meta content='*enter version here, if any*' name='version'/>\n");
            out.write("    <meta content='*enter reference citation or relative/online url here*' name='reference'/>\n");
            out.write("    <meta content='*enter additional url/bibliographic reference information here*' name='reference'/>\n");
            out.write("    <meta content='*enter copyright information here* Example:  Copyright (c) Web3D Consortium Inc. 2002*' name='rights'/>\n");
            out.write("    <meta content='*enter drawing filename/url here*' name='drawing'/>\n");
            out.write("    <meta content='*enter image filename/url here*' name='image'/>\n");
            out.write("    <meta content='*enter photo filename/url here*' name='photo'/>\n");
            out.write("    <meta content='*enter subject keywords here*' name='subject'/>\n");
            out.write("    <meta content='*enter permission statements or url here*' name='permissions'/>\n");
            out.write("    <meta content='*insert any known warnings, bugs or errors here*' name='warning'/>\n");
            out.write("    <meta content='http://*enter online url address for this file here*/" + outputFileName + "' name='identifier'/>\n");
            out.write("    <meta content='http://web.nps.navy.mil/~brutzman/Savage/Tools/Authoring/PixelTextureGenerator.java' name='generator'/>\n");
            out.write("    <meta content='http://java.sun.com/j2se/1.4.2/docs/api/java/awt/image/PixelGrabber.html' name='reference'/>\n");
            out.write("    <meta content='https://www.web3d.org/documents/specifications/19775-1/V3.3/Part01/fieldsDef.html#SFImage' name='reference'/>\n");
            out.write("    <meta content='https://www.web3d.org/documents/specifications/19775-1/V3.3/Part01/components/texturing.html#Texturecoordinates' name='reference'/>\n");
            out.write("    <meta content='https://www.web3d.org/documents/specifications/19775-1/V3.3/Part01/components/texturing.html#PixelTexture' name='reference'/>\n");
            out.write("    <meta content='https://www.web3d.org/x3d/content/examples/Vrml2.0Sourcebook/Chapter17-Textures/_pages/page13.html' name='reference'/>\n");
            out.write("  </head>\n");
            out.write("  <Scene>\n");
            out.write("    <Viewpoint position='0 0 1.5' description='PixelTexture version of image " + imageFilename + "'/>\n");
            out.write("    <!-- Apply the possibly non-square image to square geometry, then scale geometry to match original image aspect ratio -->\n");
            out.write("    <Transform translation='-0.5 " + (-0.5 * ((float)y / (float)x)) + " 0' " +
            			"scale='1 " + ((float)y / (float)x) + " 1'>\n");
            out.write("      <Anchor url='" + imageFilename + "' description='Click to view original image " + imageFilename + "' parameter='target=_blank'>\n");
            out.write("        <Shape>\n");
            out.write("          <!-- double-sided texture since solid='false' -->\n");
            out.write("          <IndexedFaceSet coordIndex='0 1 2 3' ccw='true' solid='false'>\n");
            out.write("            <Coordinate point='0 0 0, 1 0 0, 1 1 0, 0 1 0'/>\n");
            out.write("          </IndexedFaceSet>\n");
            out.write("          <Appearance>\n");
            out.write("            <Material diffuseColor='0.7 0.7 0.7'/>\n");
            out.write("            <PixelTexture image='");
            out.flush();
        }

/**
 * public void footer(int x, int y) throws IOException
 * This method writes the footer information to file
 */
        public void footer(int x, int y) throws IOException{
            out.write("'/>\n");
            out.write("          </Appearance>\n");
            out.write("        </Shape>\n");
            out.write("      </Anchor>\n");
            out.write("    </Transform>\n");
            out.write("  </Scene>\n");
            out.write("</X3D>\n");
            out.flush();
        }

/**
 * public void closeFile() throws IOException
 * This method closes the output file
 * @throws java.io.IOException
 */
        public void closeFile() throws IOException{
            out.flush();
            out.close();
            System.exit(0);
        }


	public static void main(String[] args) throws IOException
	{
		if      ((args!= null) && (args.length == 1))
		{
		    PixelTextureGenerator gen = new PixelTextureGenerator(args[0], ""); // no outputFileName
		}
		else if ((args!= null) && (args.length == 2))
		{
		    PixelTextureGenerator gen = new PixelTextureGenerator(args[0], args[1]);
		}
		else
		{
		    System.out.println (UsageMessage);
		    System.exit (-1);
		}
	}

    public StringBuffer getOutputPixelString()
    {
        return outputPixelString;
    }
    public int getImageHeight()
    {
        return imageHeight;
    }
    public int getImageWidth()
    {
        return imageWidth;
    }
}
