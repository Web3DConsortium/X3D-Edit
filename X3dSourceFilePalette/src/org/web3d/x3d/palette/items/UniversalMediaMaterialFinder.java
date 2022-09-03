package org.web3d.x3d.palette.items;

import java.text.DecimalFormat;

/**
 * UniversalMediaMaterialFinder is a utility class that enables retrieval of a UniversalMediaMaterials enumeration value by providing the component library name and material number as separate strings.
 *
 * @author   Don Brutzman
 * @see      UniversalMediaMaterials
 */

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

public class UniversalMediaMaterialFinder
{
  DecimalFormat twoDigitsFormat = new DecimalFormat("00");
  
  public static final String[] MATERIAL_UNIVERSAL_MEDIA_CHOICES = {
    "ArtDeco",
    "Autumn",
    "Glass",
    "Metals",
    "Neon",
    "Rococo",
    "SantaFe",
    "Sheen",
    "Silky",
    "Spring",
    "Summer",
    "Tropical",
    "Winter"
  };
  
  public UniversalMediaMaterials getMaterial(String libraryName, int materialNumber)
  {
    String target = libraryName+twoDigitsFormat.format((long)materialNumber);
    return UniversalMediaMaterials.valueOf(target);
  }
    /** Minimalist constructor.
     * public UniversalMediaMaterialFinder ()
     * {
     * }
     *
     * /** Retrieve UniversalMediaMaterials enumeration value
     * @param libraryName     component library name:  ArtDeco Autumn Glass Metal Neon Rococo SantaFe Sheen Silky Spring Summer Tropical Winter
     * @param materialNumber  00 through 34
     */
    public UniversalMediaMaterials getMaterial (String libraryName, String materialNumber)
    {
        if ((libraryName.compareToIgnoreCase ("ArtDeco") == 0) || (libraryName.compareToIgnoreCase ("Art Deco") == 0))
        {
            if      (materialNumber.compareTo ("00") == 0) return UniversalMediaMaterials.ArtDeco00;
            else if (materialNumber.compareTo ("01") == 0) return UniversalMediaMaterials.ArtDeco01;
            else if (materialNumber.compareTo ("02") == 0) return UniversalMediaMaterials.ArtDeco02;
            else if (materialNumber.compareTo ("03") == 0) return UniversalMediaMaterials.ArtDeco03;
            else if (materialNumber.compareTo ("04") == 0) return UniversalMediaMaterials.ArtDeco04;
            else if (materialNumber.compareTo ("05") == 0) return UniversalMediaMaterials.ArtDeco05;
            else if (materialNumber.compareTo ("06") == 0) return UniversalMediaMaterials.ArtDeco06;
            else if (materialNumber.compareTo ("07") == 0) return UniversalMediaMaterials.ArtDeco07;
            else if (materialNumber.compareTo ("08") == 0) return UniversalMediaMaterials.ArtDeco08;
            else if (materialNumber.compareTo ("09") == 0) return UniversalMediaMaterials.ArtDeco09;
            else if (materialNumber.compareTo ("10") == 0) return UniversalMediaMaterials.ArtDeco10;
            else if (materialNumber.compareTo ("11") == 0) return UniversalMediaMaterials.ArtDeco11;
            else if (materialNumber.compareTo ("12") == 0) return UniversalMediaMaterials.ArtDeco12;
            else if (materialNumber.compareTo ("13") == 0) return UniversalMediaMaterials.ArtDeco13;
            else if (materialNumber.compareTo ("14") == 0) return UniversalMediaMaterials.ArtDeco14;
            else if (materialNumber.compareTo ("15") == 0) return UniversalMediaMaterials.ArtDeco15;
            else if (materialNumber.compareTo ("16") == 0) return UniversalMediaMaterials.ArtDeco16;
            else if (materialNumber.compareTo ("17") == 0) return UniversalMediaMaterials.ArtDeco17;
            else if (materialNumber.compareTo ("18") == 0) return UniversalMediaMaterials.ArtDeco18;
            else if (materialNumber.compareTo ("19") == 0) return UniversalMediaMaterials.ArtDeco19;
            else if (materialNumber.compareTo ("20") == 0) return UniversalMediaMaterials.ArtDeco20;
            else if (materialNumber.compareTo ("21") == 0) return UniversalMediaMaterials.ArtDeco21;
            else if (materialNumber.compareTo ("22") == 0) return UniversalMediaMaterials.ArtDeco22;
            else if (materialNumber.compareTo ("23") == 0) return UniversalMediaMaterials.ArtDeco23;
            else if (materialNumber.compareTo ("24") == 0) return UniversalMediaMaterials.ArtDeco24;
            else if (materialNumber.compareTo ("25") == 0) return UniversalMediaMaterials.ArtDeco25;
            else if (materialNumber.compareTo ("26") == 0) return UniversalMediaMaterials.ArtDeco26;
            else if (materialNumber.compareTo ("27") == 0) return UniversalMediaMaterials.ArtDeco27;
            else if (materialNumber.compareTo ("28") == 0) return UniversalMediaMaterials.ArtDeco28;
            else if (materialNumber.compareTo ("29") == 0) return UniversalMediaMaterials.ArtDeco29;
            else if (materialNumber.compareTo ("30") == 0) return UniversalMediaMaterials.ArtDeco30;
            else if (materialNumber.compareTo ("31") == 0) return UniversalMediaMaterials.ArtDeco31;
            else if (materialNumber.compareTo ("32") == 0) return UniversalMediaMaterials.ArtDeco32;
            else if (materialNumber.compareTo ("33") == 0) return UniversalMediaMaterials.ArtDeco33;
            else if (materialNumber.compareTo ("34") == 0) return UniversalMediaMaterials.ArtDeco34;
        }
        else if (libraryName.compareToIgnoreCase ("Autumn") == 0)
        {
            if      (materialNumber.compareTo ("00") == 0) return UniversalMediaMaterials.Autumn00;
            else if (materialNumber.compareTo ("01") == 0) return UniversalMediaMaterials.Autumn01;
            else if (materialNumber.compareTo ("02") == 0) return UniversalMediaMaterials.Autumn02;
            else if (materialNumber.compareTo ("03") == 0) return UniversalMediaMaterials.Autumn03;
            else if (materialNumber.compareTo ("04") == 0) return UniversalMediaMaterials.Autumn04;
            else if (materialNumber.compareTo ("05") == 0) return UniversalMediaMaterials.Autumn05;
            else if (materialNumber.compareTo ("06") == 0) return UniversalMediaMaterials.Autumn06;
            else if (materialNumber.compareTo ("07") == 0) return UniversalMediaMaterials.Autumn07;
            else if (materialNumber.compareTo ("08") == 0) return UniversalMediaMaterials.Autumn08;
            else if (materialNumber.compareTo ("09") == 0) return UniversalMediaMaterials.Autumn09;
            else if (materialNumber.compareTo ("10") == 0) return UniversalMediaMaterials.Autumn10;
            else if (materialNumber.compareTo ("11") == 0) return UniversalMediaMaterials.Autumn11;
            else if (materialNumber.compareTo ("12") == 0) return UniversalMediaMaterials.Autumn12;
            else if (materialNumber.compareTo ("13") == 0) return UniversalMediaMaterials.Autumn13;
            else if (materialNumber.compareTo ("14") == 0) return UniversalMediaMaterials.Autumn14;
            else if (materialNumber.compareTo ("15") == 0) return UniversalMediaMaterials.Autumn15;
            else if (materialNumber.compareTo ("16") == 0) return UniversalMediaMaterials.Autumn16;
            else if (materialNumber.compareTo ("17") == 0) return UniversalMediaMaterials.Autumn17;
            else if (materialNumber.compareTo ("18") == 0) return UniversalMediaMaterials.Autumn18;
            else if (materialNumber.compareTo ("19") == 0) return UniversalMediaMaterials.Autumn19;
            else if (materialNumber.compareTo ("20") == 0) return UniversalMediaMaterials.Autumn20;
            else if (materialNumber.compareTo ("21") == 0) return UniversalMediaMaterials.Autumn21;
            else if (materialNumber.compareTo ("22") == 0) return UniversalMediaMaterials.Autumn22;
            else if (materialNumber.compareTo ("23") == 0) return UniversalMediaMaterials.Autumn23;
            else if (materialNumber.compareTo ("24") == 0) return UniversalMediaMaterials.Autumn24;
            else if (materialNumber.compareTo ("25") == 0) return UniversalMediaMaterials.Autumn25;
            else if (materialNumber.compareTo ("26") == 0) return UniversalMediaMaterials.Autumn26;
            else if (materialNumber.compareTo ("27") == 0) return UniversalMediaMaterials.Autumn27;
            else if (materialNumber.compareTo ("28") == 0) return UniversalMediaMaterials.Autumn28;
            else if (materialNumber.compareTo ("29") == 0) return UniversalMediaMaterials.Autumn29;
            else if (materialNumber.compareTo ("30") == 0) return UniversalMediaMaterials.Autumn30;
            else if (materialNumber.compareTo ("31") == 0) return UniversalMediaMaterials.Autumn31;
            else if (materialNumber.compareTo ("32") == 0) return UniversalMediaMaterials.Autumn32;
            else if (materialNumber.compareTo ("33") == 0) return UniversalMediaMaterials.Autumn33;
            else if (materialNumber.compareTo ("34") == 0) return UniversalMediaMaterials.Autumn34;
        }
        else  if (libraryName.compareToIgnoreCase ("Glass") == 0)
        {
            if      (materialNumber.compareTo ("00") == 0) return UniversalMediaMaterials.Glass00;
            else if (materialNumber.compareTo ("01") == 0) return UniversalMediaMaterials.Glass01;
            else if (materialNumber.compareTo ("02") == 0) return UniversalMediaMaterials.Glass02;
            else if (materialNumber.compareTo ("03") == 0) return UniversalMediaMaterials.Glass03;
            else if (materialNumber.compareTo ("04") == 0) return UniversalMediaMaterials.Glass04;
            else if (materialNumber.compareTo ("05") == 0) return UniversalMediaMaterials.Glass05;
            else if (materialNumber.compareTo ("06") == 0) return UniversalMediaMaterials.Glass06;
            else if (materialNumber.compareTo ("07") == 0) return UniversalMediaMaterials.Glass07;
            else if (materialNumber.compareTo ("08") == 0) return UniversalMediaMaterials.Glass08;
            else if (materialNumber.compareTo ("09") == 0) return UniversalMediaMaterials.Glass09;
            else if (materialNumber.compareTo ("10") == 0) return UniversalMediaMaterials.Glass10;
            else if (materialNumber.compareTo ("11") == 0) return UniversalMediaMaterials.Glass11;
            else if (materialNumber.compareTo ("12") == 0) return UniversalMediaMaterials.Glass12;
            else if (materialNumber.compareTo ("13") == 0) return UniversalMediaMaterials.Glass13;
            else if (materialNumber.compareTo ("14") == 0) return UniversalMediaMaterials.Glass14;
            else if (materialNumber.compareTo ("15") == 0) return UniversalMediaMaterials.Glass15;
            else if (materialNumber.compareTo ("16") == 0) return UniversalMediaMaterials.Glass16;
            else if (materialNumber.compareTo ("17") == 0) return UniversalMediaMaterials.Glass17;
            else if (materialNumber.compareTo ("18") == 0) return UniversalMediaMaterials.Glass18;
            else if (materialNumber.compareTo ("19") == 0) return UniversalMediaMaterials.Glass19;
            else if (materialNumber.compareTo ("20") == 0) return UniversalMediaMaterials.Glass20;
            else if (materialNumber.compareTo ("21") == 0) return UniversalMediaMaterials.Glass21;
            else if (materialNumber.compareTo ("22") == 0) return UniversalMediaMaterials.Glass22;
            else if (materialNumber.compareTo ("23") == 0) return UniversalMediaMaterials.Glass23;
            else if (materialNumber.compareTo ("24") == 0) return UniversalMediaMaterials.Glass24;
            else if (materialNumber.compareTo ("25") == 0) return UniversalMediaMaterials.Glass25;
            else if (materialNumber.compareTo ("26") == 0) return UniversalMediaMaterials.Glass26;
            else if (materialNumber.compareTo ("27") == 0) return UniversalMediaMaterials.Glass27;
            else if (materialNumber.compareTo ("28") == 0) return UniversalMediaMaterials.Glass28;
            else if (materialNumber.compareTo ("29") == 0) return UniversalMediaMaterials.Glass29;
            else if (materialNumber.compareTo ("30") == 0) return UniversalMediaMaterials.Glass30;
            else if (materialNumber.compareTo ("31") == 0) return UniversalMediaMaterials.Glass31;
            else if (materialNumber.compareTo ("32") == 0) return UniversalMediaMaterials.Glass32;
            else if (materialNumber.compareTo ("33") == 0) return UniversalMediaMaterials.Glass33;
            else if (materialNumber.compareTo ("34") == 0) return UniversalMediaMaterials.Glass34;
        }
        else  if (libraryName.compareToIgnoreCase ("Metal") == 0)
        {
            if      (materialNumber.compareTo ("00") == 0) return UniversalMediaMaterials.Metals00;
            else if (materialNumber.compareTo ("01") == 0) return UniversalMediaMaterials.Metals01;
            else if (materialNumber.compareTo ("02") == 0) return UniversalMediaMaterials.Metals02;
            else if (materialNumber.compareTo ("03") == 0) return UniversalMediaMaterials.Metals03;
            else if (materialNumber.compareTo ("04") == 0) return UniversalMediaMaterials.Metals04;
            else if (materialNumber.compareTo ("05") == 0) return UniversalMediaMaterials.Metals05;
            else if (materialNumber.compareTo ("06") == 0) return UniversalMediaMaterials.Metals06;
            else if (materialNumber.compareTo ("07") == 0) return UniversalMediaMaterials.Metals07;
            else if (materialNumber.compareTo ("08") == 0) return UniversalMediaMaterials.Metals08;
            else if (materialNumber.compareTo ("09") == 0) return UniversalMediaMaterials.Metals09;
            else if (materialNumber.compareTo ("10") == 0) return UniversalMediaMaterials.Metals10;
            else if (materialNumber.compareTo ("11") == 0) return UniversalMediaMaterials.Metals11;
            else if (materialNumber.compareTo ("12") == 0) return UniversalMediaMaterials.Metals12;
            else if (materialNumber.compareTo ("13") == 0) return UniversalMediaMaterials.Metals13;
            else if (materialNumber.compareTo ("14") == 0) return UniversalMediaMaterials.Metals14;
            else if (materialNumber.compareTo ("15") == 0) return UniversalMediaMaterials.Metals15;
            else if (materialNumber.compareTo ("16") == 0) return UniversalMediaMaterials.Metals16;
            else if (materialNumber.compareTo ("17") == 0) return UniversalMediaMaterials.Metals17;
            else if (materialNumber.compareTo ("18") == 0) return UniversalMediaMaterials.Metals18;
            else if (materialNumber.compareTo ("19") == 0) return UniversalMediaMaterials.Metals19;
            else if (materialNumber.compareTo ("20") == 0) return UniversalMediaMaterials.Metals20;
            else if (materialNumber.compareTo ("21") == 0) return UniversalMediaMaterials.Metals21;
            else if (materialNumber.compareTo ("22") == 0) return UniversalMediaMaterials.Metals22;
            else if (materialNumber.compareTo ("23") == 0) return UniversalMediaMaterials.Metals23;
            else if (materialNumber.compareTo ("24") == 0) return UniversalMediaMaterials.Metals24;
            else if (materialNumber.compareTo ("25") == 0) return UniversalMediaMaterials.Metals25;
            else if (materialNumber.compareTo ("26") == 0) return UniversalMediaMaterials.Metals26;
            else if (materialNumber.compareTo ("27") == 0) return UniversalMediaMaterials.Metals27;
            else if (materialNumber.compareTo ("28") == 0) return UniversalMediaMaterials.Metals28;
            else if (materialNumber.compareTo ("29") == 0) return UniversalMediaMaterials.Metals29;
            else if (materialNumber.compareTo ("30") == 0) return UniversalMediaMaterials.Metals30;
            else if (materialNumber.compareTo ("31") == 0) return UniversalMediaMaterials.Metals31;
            else if (materialNumber.compareTo ("32") == 0) return UniversalMediaMaterials.Metals32;
            else if (materialNumber.compareTo ("33") == 0) return UniversalMediaMaterials.Metals33;
            else if (materialNumber.compareTo ("34") == 0) return UniversalMediaMaterials.Metals34;
        }
        else  if (libraryName.compareToIgnoreCase ("Neon") == 0)
        {
            if      (materialNumber.compareTo ("00") == 0) return UniversalMediaMaterials.Neon00;
            else if (materialNumber.compareTo ("01") == 0) return UniversalMediaMaterials.Neon01;
            else if (materialNumber.compareTo ("02") == 0) return UniversalMediaMaterials.Neon02;
            else if (materialNumber.compareTo ("03") == 0) return UniversalMediaMaterials.Neon03;
            else if (materialNumber.compareTo ("04") == 0) return UniversalMediaMaterials.Neon04;
            else if (materialNumber.compareTo ("05") == 0) return UniversalMediaMaterials.Neon05;
            else if (materialNumber.compareTo ("06") == 0) return UniversalMediaMaterials.Neon06;
            else if (materialNumber.compareTo ("07") == 0) return UniversalMediaMaterials.Neon07;
            else if (materialNumber.compareTo ("08") == 0) return UniversalMediaMaterials.Neon08;
            else if (materialNumber.compareTo ("09") == 0) return UniversalMediaMaterials.Neon09;
            else if (materialNumber.compareTo ("10") == 0) return UniversalMediaMaterials.Neon10;
            else if (materialNumber.compareTo ("11") == 0) return UniversalMediaMaterials.Neon11;
            else if (materialNumber.compareTo ("12") == 0) return UniversalMediaMaterials.Neon12;
            else if (materialNumber.compareTo ("13") == 0) return UniversalMediaMaterials.Neon13;
            else if (materialNumber.compareTo ("14") == 0) return UniversalMediaMaterials.Neon14;
            else if (materialNumber.compareTo ("15") == 0) return UniversalMediaMaterials.Neon15;
            else if (materialNumber.compareTo ("16") == 0) return UniversalMediaMaterials.Neon16;
            else if (materialNumber.compareTo ("17") == 0) return UniversalMediaMaterials.Neon17;
            else if (materialNumber.compareTo ("18") == 0) return UniversalMediaMaterials.Neon18;
            else if (materialNumber.compareTo ("19") == 0) return UniversalMediaMaterials.Neon19;
            else if (materialNumber.compareTo ("20") == 0) return UniversalMediaMaterials.Neon20;
            else if (materialNumber.compareTo ("21") == 0) return UniversalMediaMaterials.Neon21;
            else if (materialNumber.compareTo ("22") == 0) return UniversalMediaMaterials.Neon22;
            else if (materialNumber.compareTo ("23") == 0) return UniversalMediaMaterials.Neon23;
            else if (materialNumber.compareTo ("24") == 0) return UniversalMediaMaterials.Neon24;
            else if (materialNumber.compareTo ("25") == 0) return UniversalMediaMaterials.Neon25;
            else if (materialNumber.compareTo ("26") == 0) return UniversalMediaMaterials.Neon26;
            else if (materialNumber.compareTo ("27") == 0) return UniversalMediaMaterials.Neon27;
            else if (materialNumber.compareTo ("28") == 0) return UniversalMediaMaterials.Neon28;
            else if (materialNumber.compareTo ("29") == 0) return UniversalMediaMaterials.Neon29;
            else if (materialNumber.compareTo ("30") == 0) return UniversalMediaMaterials.Neon30;
            else if (materialNumber.compareTo ("31") == 0) return UniversalMediaMaterials.Neon31;
            else if (materialNumber.compareTo ("32") == 0) return UniversalMediaMaterials.Neon32;
            else if (materialNumber.compareTo ("33") == 0) return UniversalMediaMaterials.Neon33;
            else if (materialNumber.compareTo ("34") == 0) return UniversalMediaMaterials.Neon34;
        }
        else  if (libraryName.compareToIgnoreCase ("Rococo") == 0)
        {
            if      (materialNumber.compareTo ("00") == 0) return UniversalMediaMaterials.Rococo00;
            else if (materialNumber.compareTo ("01") == 0) return UniversalMediaMaterials.Rococo01;
            else if (materialNumber.compareTo ("02") == 0) return UniversalMediaMaterials.Rococo02;
            else if (materialNumber.compareTo ("03") == 0) return UniversalMediaMaterials.Rococo03;
            else if (materialNumber.compareTo ("04") == 0) return UniversalMediaMaterials.Rococo04;
            else if (materialNumber.compareTo ("05") == 0) return UniversalMediaMaterials.Rococo05;
            else if (materialNumber.compareTo ("06") == 0) return UniversalMediaMaterials.Rococo06;
            else if (materialNumber.compareTo ("07") == 0) return UniversalMediaMaterials.Rococo07;
            else if (materialNumber.compareTo ("08") == 0) return UniversalMediaMaterials.Rococo08;
            else if (materialNumber.compareTo ("09") == 0) return UniversalMediaMaterials.Rococo09;
            else if (materialNumber.compareTo ("10") == 0) return UniversalMediaMaterials.Rococo10;
            else if (materialNumber.compareTo ("11") == 0) return UniversalMediaMaterials.Rococo11;
            else if (materialNumber.compareTo ("12") == 0) return UniversalMediaMaterials.Rococo12;
            else if (materialNumber.compareTo ("13") == 0) return UniversalMediaMaterials.Rococo13;
            else if (materialNumber.compareTo ("14") == 0) return UniversalMediaMaterials.Rococo14;
            else if (materialNumber.compareTo ("15") == 0) return UniversalMediaMaterials.Rococo15;
            else if (materialNumber.compareTo ("16") == 0) return UniversalMediaMaterials.Rococo16;
            else if (materialNumber.compareTo ("17") == 0) return UniversalMediaMaterials.Rococo17;
            else if (materialNumber.compareTo ("18") == 0) return UniversalMediaMaterials.Rococo18;
            else if (materialNumber.compareTo ("19") == 0) return UniversalMediaMaterials.Rococo19;
            else if (materialNumber.compareTo ("20") == 0) return UniversalMediaMaterials.Rococo20;
            else if (materialNumber.compareTo ("21") == 0) return UniversalMediaMaterials.Rococo21;
            else if (materialNumber.compareTo ("22") == 0) return UniversalMediaMaterials.Rococo22;
            else if (materialNumber.compareTo ("23") == 0) return UniversalMediaMaterials.Rococo23;
            else if (materialNumber.compareTo ("24") == 0) return UniversalMediaMaterials.Rococo24;
            else if (materialNumber.compareTo ("25") == 0) return UniversalMediaMaterials.Rococo25;
            else if (materialNumber.compareTo ("26") == 0) return UniversalMediaMaterials.Rococo26;
            else if (materialNumber.compareTo ("27") == 0) return UniversalMediaMaterials.Rococo27;
            else if (materialNumber.compareTo ("28") == 0) return UniversalMediaMaterials.Rococo28;
            else if (materialNumber.compareTo ("29") == 0) return UniversalMediaMaterials.Rococo29;
            else if (materialNumber.compareTo ("30") == 0) return UniversalMediaMaterials.Rococo30;
            else if (materialNumber.compareTo ("31") == 0) return UniversalMediaMaterials.Rococo31;
            else if (materialNumber.compareTo ("32") == 0) return UniversalMediaMaterials.Rococo32;
            else if (materialNumber.compareTo ("33") == 0) return UniversalMediaMaterials.Rococo33;
            else if (materialNumber.compareTo ("34") == 0) return UniversalMediaMaterials.Rococo34;
        }
        else  if ((libraryName.compareToIgnoreCase ("SantaFe") == 0) || (libraryName.compareToIgnoreCase ("Santa Fe") == 0))
        {
            if      (materialNumber.compareTo ("00") == 0) return UniversalMediaMaterials.SantaFe00;
            else if (materialNumber.compareTo ("01") == 0) return UniversalMediaMaterials.SantaFe01;
            else if (materialNumber.compareTo ("02") == 0) return UniversalMediaMaterials.SantaFe02;
            else if (materialNumber.compareTo ("03") == 0) return UniversalMediaMaterials.SantaFe03;
            else if (materialNumber.compareTo ("04") == 0) return UniversalMediaMaterials.SantaFe04;
            else if (materialNumber.compareTo ("05") == 0) return UniversalMediaMaterials.SantaFe05;
            else if (materialNumber.compareTo ("06") == 0) return UniversalMediaMaterials.SantaFe06;
            else if (materialNumber.compareTo ("07") == 0) return UniversalMediaMaterials.SantaFe07;
            else if (materialNumber.compareTo ("08") == 0) return UniversalMediaMaterials.SantaFe08;
            else if (materialNumber.compareTo ("09") == 0) return UniversalMediaMaterials.SantaFe09;
            else if (materialNumber.compareTo ("10") == 0) return UniversalMediaMaterials.SantaFe10;
            else if (materialNumber.compareTo ("11") == 0) return UniversalMediaMaterials.SantaFe11;
            else if (materialNumber.compareTo ("12") == 0) return UniversalMediaMaterials.SantaFe12;
            else if (materialNumber.compareTo ("13") == 0) return UniversalMediaMaterials.SantaFe13;
            else if (materialNumber.compareTo ("14") == 0) return UniversalMediaMaterials.SantaFe14;
            else if (materialNumber.compareTo ("15") == 0) return UniversalMediaMaterials.SantaFe15;
            else if (materialNumber.compareTo ("16") == 0) return UniversalMediaMaterials.SantaFe16;
            else if (materialNumber.compareTo ("17") == 0) return UniversalMediaMaterials.SantaFe17;
            else if (materialNumber.compareTo ("18") == 0) return UniversalMediaMaterials.SantaFe18;
            else if (materialNumber.compareTo ("19") == 0) return UniversalMediaMaterials.SantaFe19;
            else if (materialNumber.compareTo ("20") == 0) return UniversalMediaMaterials.SantaFe20;
            else if (materialNumber.compareTo ("21") == 0) return UniversalMediaMaterials.SantaFe21;
            else if (materialNumber.compareTo ("22") == 0) return UniversalMediaMaterials.SantaFe22;
            else if (materialNumber.compareTo ("23") == 0) return UniversalMediaMaterials.SantaFe23;
            else if (materialNumber.compareTo ("24") == 0) return UniversalMediaMaterials.SantaFe24;
            else if (materialNumber.compareTo ("25") == 0) return UniversalMediaMaterials.SantaFe25;
            else if (materialNumber.compareTo ("26") == 0) return UniversalMediaMaterials.SantaFe26;
            else if (materialNumber.compareTo ("27") == 0) return UniversalMediaMaterials.SantaFe27;
            else if (materialNumber.compareTo ("28") == 0) return UniversalMediaMaterials.SantaFe28;
            else if (materialNumber.compareTo ("29") == 0) return UniversalMediaMaterials.SantaFe29;
            else if (materialNumber.compareTo ("30") == 0) return UniversalMediaMaterials.SantaFe30;
            else if (materialNumber.compareTo ("31") == 0) return UniversalMediaMaterials.SantaFe31;
            else if (materialNumber.compareTo ("32") == 0) return UniversalMediaMaterials.SantaFe32;
            else if (materialNumber.compareTo ("33") == 0) return UniversalMediaMaterials.SantaFe33;
            else if (materialNumber.compareTo ("34") == 0) return UniversalMediaMaterials.SantaFe34;
        }
        else  if (libraryName.compareToIgnoreCase ("Sheen") == 0)
        {
            if      (materialNumber.compareTo ("00") == 0) return UniversalMediaMaterials.Sheen00;
            else if (materialNumber.compareTo ("01") == 0) return UniversalMediaMaterials.Sheen01;
            else if (materialNumber.compareTo ("02") == 0) return UniversalMediaMaterials.Sheen02;
            else if (materialNumber.compareTo ("03") == 0) return UniversalMediaMaterials.Sheen03;
            else if (materialNumber.compareTo ("04") == 0) return UniversalMediaMaterials.Sheen04;
            else if (materialNumber.compareTo ("05") == 0) return UniversalMediaMaterials.Sheen05;
            else if (materialNumber.compareTo ("06") == 0) return UniversalMediaMaterials.Sheen06;
            else if (materialNumber.compareTo ("07") == 0) return UniversalMediaMaterials.Sheen07;
            else if (materialNumber.compareTo ("08") == 0) return UniversalMediaMaterials.Sheen08;
            else if (materialNumber.compareTo ("09") == 0) return UniversalMediaMaterials.Sheen09;
            else if (materialNumber.compareTo ("10") == 0) return UniversalMediaMaterials.Sheen10;
            else if (materialNumber.compareTo ("11") == 0) return UniversalMediaMaterials.Sheen11;
            else if (materialNumber.compareTo ("12") == 0) return UniversalMediaMaterials.Sheen12;
            else if (materialNumber.compareTo ("13") == 0) return UniversalMediaMaterials.Sheen13;
            else if (materialNumber.compareTo ("14") == 0) return UniversalMediaMaterials.Sheen14;
            else if (materialNumber.compareTo ("15") == 0) return UniversalMediaMaterials.Sheen15;
            else if (materialNumber.compareTo ("16") == 0) return UniversalMediaMaterials.Sheen16;
            else if (materialNumber.compareTo ("17") == 0) return UniversalMediaMaterials.Sheen17;
            else if (materialNumber.compareTo ("18") == 0) return UniversalMediaMaterials.Sheen18;
            else if (materialNumber.compareTo ("19") == 0) return UniversalMediaMaterials.Sheen19;
            else if (materialNumber.compareTo ("20") == 0) return UniversalMediaMaterials.Sheen20;
            else if (materialNumber.compareTo ("21") == 0) return UniversalMediaMaterials.Sheen21;
            else if (materialNumber.compareTo ("22") == 0) return UniversalMediaMaterials.Sheen22;
            else if (materialNumber.compareTo ("23") == 0) return UniversalMediaMaterials.Sheen23;
            else if (materialNumber.compareTo ("24") == 0) return UniversalMediaMaterials.Sheen24;
            else if (materialNumber.compareTo ("25") == 0) return UniversalMediaMaterials.Sheen25;
            else if (materialNumber.compareTo ("26") == 0) return UniversalMediaMaterials.Sheen26;
            else if (materialNumber.compareTo ("27") == 0) return UniversalMediaMaterials.Sheen27;
            else if (materialNumber.compareTo ("28") == 0) return UniversalMediaMaterials.Sheen28;
            else if (materialNumber.compareTo ("29") == 0) return UniversalMediaMaterials.Sheen29;
            else if (materialNumber.compareTo ("30") == 0) return UniversalMediaMaterials.Sheen30;
            else if (materialNumber.compareTo ("31") == 0) return UniversalMediaMaterials.Sheen31;
            else if (materialNumber.compareTo ("32") == 0) return UniversalMediaMaterials.Sheen32;
            else if (materialNumber.compareTo ("33") == 0) return UniversalMediaMaterials.Sheen33;
            else if (materialNumber.compareTo ("34") == 0) return UniversalMediaMaterials.Sheen34;
        }
        else  if (libraryName.compareToIgnoreCase ("Silky") == 0)
        {
            if      (materialNumber.compareTo ("00") == 0) return UniversalMediaMaterials.Silky00;
            else if (materialNumber.compareTo ("01") == 0) return UniversalMediaMaterials.Silky01;
            else if (materialNumber.compareTo ("02") == 0) return UniversalMediaMaterials.Silky02;
            else if (materialNumber.compareTo ("03") == 0) return UniversalMediaMaterials.Silky03;
            else if (materialNumber.compareTo ("04") == 0) return UniversalMediaMaterials.Silky04;
            else if (materialNumber.compareTo ("05") == 0) return UniversalMediaMaterials.Silky05;
            else if (materialNumber.compareTo ("06") == 0) return UniversalMediaMaterials.Silky06;
            else if (materialNumber.compareTo ("07") == 0) return UniversalMediaMaterials.Silky07;
            else if (materialNumber.compareTo ("08") == 0) return UniversalMediaMaterials.Silky08;
            else if (materialNumber.compareTo ("09") == 0) return UniversalMediaMaterials.Silky09;
            else if (materialNumber.compareTo ("10") == 0) return UniversalMediaMaterials.Silky10;
            else if (materialNumber.compareTo ("11") == 0) return UniversalMediaMaterials.Silky11;
            else if (materialNumber.compareTo ("12") == 0) return UniversalMediaMaterials.Silky12;
            else if (materialNumber.compareTo ("13") == 0) return UniversalMediaMaterials.Silky13;
            else if (materialNumber.compareTo ("14") == 0) return UniversalMediaMaterials.Silky14;
            else if (materialNumber.compareTo ("15") == 0) return UniversalMediaMaterials.Silky15;
            else if (materialNumber.compareTo ("16") == 0) return UniversalMediaMaterials.Silky16;
            else if (materialNumber.compareTo ("17") == 0) return UniversalMediaMaterials.Silky17;
            else if (materialNumber.compareTo ("18") == 0) return UniversalMediaMaterials.Silky18;
            else if (materialNumber.compareTo ("19") == 0) return UniversalMediaMaterials.Silky19;
            else if (materialNumber.compareTo ("20") == 0) return UniversalMediaMaterials.Silky20;
            else if (materialNumber.compareTo ("21") == 0) return UniversalMediaMaterials.Silky21;
            else if (materialNumber.compareTo ("22") == 0) return UniversalMediaMaterials.Silky22;
            else if (materialNumber.compareTo ("23") == 0) return UniversalMediaMaterials.Silky23;
            else if (materialNumber.compareTo ("24") == 0) return UniversalMediaMaterials.Silky24;
            else if (materialNumber.compareTo ("25") == 0) return UniversalMediaMaterials.Silky25;
            else if (materialNumber.compareTo ("26") == 0) return UniversalMediaMaterials.Silky26;
            else if (materialNumber.compareTo ("27") == 0) return UniversalMediaMaterials.Silky27;
            else if (materialNumber.compareTo ("28") == 0) return UniversalMediaMaterials.Silky28;
            else if (materialNumber.compareTo ("29") == 0) return UniversalMediaMaterials.Silky29;
            else if (materialNumber.compareTo ("30") == 0) return UniversalMediaMaterials.Silky30;
            else if (materialNumber.compareTo ("31") == 0) return UniversalMediaMaterials.Silky31;
            else if (materialNumber.compareTo ("32") == 0) return UniversalMediaMaterials.Silky32;
            else if (materialNumber.compareTo ("33") == 0) return UniversalMediaMaterials.Silky33;
            else if (materialNumber.compareTo ("34") == 0) return UniversalMediaMaterials.Silky34;
        }
        else  if (libraryName.compareToIgnoreCase ("Spring") == 0)
        {
            if      (materialNumber.compareTo ("00") == 0) return UniversalMediaMaterials.Spring00;
            else if (materialNumber.compareTo ("01") == 0) return UniversalMediaMaterials.Spring01;
            else if (materialNumber.compareTo ("02") == 0) return UniversalMediaMaterials.Spring02;
            else if (materialNumber.compareTo ("03") == 0) return UniversalMediaMaterials.Spring03;
            else if (materialNumber.compareTo ("04") == 0) return UniversalMediaMaterials.Spring04;
            else if (materialNumber.compareTo ("05") == 0) return UniversalMediaMaterials.Spring05;
            else if (materialNumber.compareTo ("06") == 0) return UniversalMediaMaterials.Spring06;
            else if (materialNumber.compareTo ("07") == 0) return UniversalMediaMaterials.Spring07;
            else if (materialNumber.compareTo ("08") == 0) return UniversalMediaMaterials.Spring08;
            else if (materialNumber.compareTo ("09") == 0) return UniversalMediaMaterials.Spring09;
            else if (materialNumber.compareTo ("10") == 0) return UniversalMediaMaterials.Spring10;
            else if (materialNumber.compareTo ("11") == 0) return UniversalMediaMaterials.Spring11;
            else if (materialNumber.compareTo ("12") == 0) return UniversalMediaMaterials.Spring12;
            else if (materialNumber.compareTo ("13") == 0) return UniversalMediaMaterials.Spring13;
            else if (materialNumber.compareTo ("14") == 0) return UniversalMediaMaterials.Spring14;
            else if (materialNumber.compareTo ("15") == 0) return UniversalMediaMaterials.Spring15;
            else if (materialNumber.compareTo ("16") == 0) return UniversalMediaMaterials.Spring16;
            else if (materialNumber.compareTo ("17") == 0) return UniversalMediaMaterials.Spring17;
            else if (materialNumber.compareTo ("18") == 0) return UniversalMediaMaterials.Spring18;
            else if (materialNumber.compareTo ("19") == 0) return UniversalMediaMaterials.Spring19;
            else if (materialNumber.compareTo ("20") == 0) return UniversalMediaMaterials.Spring20;
            else if (materialNumber.compareTo ("21") == 0) return UniversalMediaMaterials.Spring21;
            else if (materialNumber.compareTo ("22") == 0) return UniversalMediaMaterials.Spring22;
            else if (materialNumber.compareTo ("23") == 0) return UniversalMediaMaterials.Spring23;
            else if (materialNumber.compareTo ("24") == 0) return UniversalMediaMaterials.Spring24;
            else if (materialNumber.compareTo ("25") == 0) return UniversalMediaMaterials.Spring25;
            else if (materialNumber.compareTo ("26") == 0) return UniversalMediaMaterials.Spring26;
            else if (materialNumber.compareTo ("27") == 0) return UniversalMediaMaterials.Spring27;
            else if (materialNumber.compareTo ("28") == 0) return UniversalMediaMaterials.Spring28;
            else if (materialNumber.compareTo ("29") == 0) return UniversalMediaMaterials.Spring29;
            else if (materialNumber.compareTo ("30") == 0) return UniversalMediaMaterials.Spring30;
            else if (materialNumber.compareTo ("31") == 0) return UniversalMediaMaterials.Spring31;
            else if (materialNumber.compareTo ("32") == 0) return UniversalMediaMaterials.Spring32;
            else if (materialNumber.compareTo ("33") == 0) return UniversalMediaMaterials.Spring33;
            else if (materialNumber.compareTo ("34") == 0) return UniversalMediaMaterials.Spring34;
        }
        else  if (libraryName.compareToIgnoreCase ("Summer") == 0)
        {
            if      (materialNumber.compareTo ("00") == 0) return UniversalMediaMaterials.Summer00;
            else if (materialNumber.compareTo ("01") == 0) return UniversalMediaMaterials.Summer01;
            else if (materialNumber.compareTo ("02") == 0) return UniversalMediaMaterials.Summer02;
            else if (materialNumber.compareTo ("03") == 0) return UniversalMediaMaterials.Summer03;
            else if (materialNumber.compareTo ("04") == 0) return UniversalMediaMaterials.Summer04;
            else if (materialNumber.compareTo ("05") == 0) return UniversalMediaMaterials.Summer05;
            else if (materialNumber.compareTo ("06") == 0) return UniversalMediaMaterials.Summer06;
            else if (materialNumber.compareTo ("07") == 0) return UniversalMediaMaterials.Summer07;
            else if (materialNumber.compareTo ("08") == 0) return UniversalMediaMaterials.Summer08;
            else if (materialNumber.compareTo ("09") == 0) return UniversalMediaMaterials.Summer09;
            else if (materialNumber.compareTo ("10") == 0) return UniversalMediaMaterials.Summer10;
            else if (materialNumber.compareTo ("11") == 0) return UniversalMediaMaterials.Summer11;
            else if (materialNumber.compareTo ("12") == 0) return UniversalMediaMaterials.Summer12;
            else if (materialNumber.compareTo ("13") == 0) return UniversalMediaMaterials.Summer13;
            else if (materialNumber.compareTo ("14") == 0) return UniversalMediaMaterials.Summer14;
            else if (materialNumber.compareTo ("15") == 0) return UniversalMediaMaterials.Summer15;
            else if (materialNumber.compareTo ("16") == 0) return UniversalMediaMaterials.Summer16;
            else if (materialNumber.compareTo ("17") == 0) return UniversalMediaMaterials.Summer17;
            else if (materialNumber.compareTo ("18") == 0) return UniversalMediaMaterials.Summer18;
            else if (materialNumber.compareTo ("19") == 0) return UniversalMediaMaterials.Summer19;
            else if (materialNumber.compareTo ("20") == 0) return UniversalMediaMaterials.Summer20;
            else if (materialNumber.compareTo ("21") == 0) return UniversalMediaMaterials.Summer21;
            else if (materialNumber.compareTo ("22") == 0) return UniversalMediaMaterials.Summer22;
            else if (materialNumber.compareTo ("23") == 0) return UniversalMediaMaterials.Summer23;
            else if (materialNumber.compareTo ("24") == 0) return UniversalMediaMaterials.Summer24;
            else if (materialNumber.compareTo ("25") == 0) return UniversalMediaMaterials.Summer25;
            else if (materialNumber.compareTo ("26") == 0) return UniversalMediaMaterials.Summer26;
            else if (materialNumber.compareTo ("27") == 0) return UniversalMediaMaterials.Summer27;
            else if (materialNumber.compareTo ("28") == 0) return UniversalMediaMaterials.Summer28;
            else if (materialNumber.compareTo ("29") == 0) return UniversalMediaMaterials.Summer29;
            else if (materialNumber.compareTo ("30") == 0) return UniversalMediaMaterials.Summer30;
            else if (materialNumber.compareTo ("31") == 0) return UniversalMediaMaterials.Summer31;
            else if (materialNumber.compareTo ("32") == 0) return UniversalMediaMaterials.Summer32;
            else if (materialNumber.compareTo ("33") == 0) return UniversalMediaMaterials.Summer33;
            else if (materialNumber.compareTo ("34") == 0) return UniversalMediaMaterials.Summer34;
        }
        else  if (libraryName.compareToIgnoreCase ("Tropical") == 0)
        {
            if      (materialNumber.compareTo ("00") == 0) return UniversalMediaMaterials.Tropical00;
            else if (materialNumber.compareTo ("01") == 0) return UniversalMediaMaterials.Tropical01;
            else if (materialNumber.compareTo ("02") == 0) return UniversalMediaMaterials.Tropical02;
            else if (materialNumber.compareTo ("03") == 0) return UniversalMediaMaterials.Tropical03;
            else if (materialNumber.compareTo ("04") == 0) return UniversalMediaMaterials.Tropical04;
            else if (materialNumber.compareTo ("05") == 0) return UniversalMediaMaterials.Tropical05;
            else if (materialNumber.compareTo ("06") == 0) return UniversalMediaMaterials.Tropical06;
            else if (materialNumber.compareTo ("07") == 0) return UniversalMediaMaterials.Tropical07;
            else if (materialNumber.compareTo ("08") == 0) return UniversalMediaMaterials.Tropical08;
            else if (materialNumber.compareTo ("09") == 0) return UniversalMediaMaterials.Tropical09;
            else if (materialNumber.compareTo ("10") == 0) return UniversalMediaMaterials.Tropical10;
            else if (materialNumber.compareTo ("11") == 0) return UniversalMediaMaterials.Tropical11;
            else if (materialNumber.compareTo ("12") == 0) return UniversalMediaMaterials.Tropical12;
            else if (materialNumber.compareTo ("13") == 0) return UniversalMediaMaterials.Tropical13;
            else if (materialNumber.compareTo ("14") == 0) return UniversalMediaMaterials.Tropical14;
            else if (materialNumber.compareTo ("15") == 0) return UniversalMediaMaterials.Tropical15;
            else if (materialNumber.compareTo ("16") == 0) return UniversalMediaMaterials.Tropical16;
            else if (materialNumber.compareTo ("17") == 0) return UniversalMediaMaterials.Tropical17;
            else if (materialNumber.compareTo ("18") == 0) return UniversalMediaMaterials.Tropical18;
            else if (materialNumber.compareTo ("19") == 0) return UniversalMediaMaterials.Tropical19;
            else if (materialNumber.compareTo ("20") == 0) return UniversalMediaMaterials.Tropical20;
            else if (materialNumber.compareTo ("21") == 0) return UniversalMediaMaterials.Tropical21;
            else if (materialNumber.compareTo ("22") == 0) return UniversalMediaMaterials.Tropical22;
            else if (materialNumber.compareTo ("23") == 0) return UniversalMediaMaterials.Tropical23;
            else if (materialNumber.compareTo ("24") == 0) return UniversalMediaMaterials.Tropical24;
            else if (materialNumber.compareTo ("25") == 0) return UniversalMediaMaterials.Tropical25;
            else if (materialNumber.compareTo ("26") == 0) return UniversalMediaMaterials.Tropical26;
            else if (materialNumber.compareTo ("27") == 0) return UniversalMediaMaterials.Tropical27;
            else if (materialNumber.compareTo ("28") == 0) return UniversalMediaMaterials.Tropical28;
            else if (materialNumber.compareTo ("29") == 0) return UniversalMediaMaterials.Tropical29;
            else if (materialNumber.compareTo ("30") == 0) return UniversalMediaMaterials.Tropical30;
            else if (materialNumber.compareTo ("31") == 0) return UniversalMediaMaterials.Tropical31;
            else if (materialNumber.compareTo ("32") == 0) return UniversalMediaMaterials.Tropical32;
            else if (materialNumber.compareTo ("33") == 0) return UniversalMediaMaterials.Tropical33;
            else if (materialNumber.compareTo ("34") == 0) return UniversalMediaMaterials.Tropical34;
        }
        else  if (libraryName.compareToIgnoreCase ("Winter") == 0)
        {
            if      (materialNumber.compareTo ("00") == 0) return UniversalMediaMaterials.Winter00;
            else if (materialNumber.compareTo ("01") == 0) return UniversalMediaMaterials.Winter01;
            else if (materialNumber.compareTo ("02") == 0) return UniversalMediaMaterials.Winter02;
            else if (materialNumber.compareTo ("03") == 0) return UniversalMediaMaterials.Winter03;
            else if (materialNumber.compareTo ("04") == 0) return UniversalMediaMaterials.Winter04;
            else if (materialNumber.compareTo ("05") == 0) return UniversalMediaMaterials.Winter05;
            else if (materialNumber.compareTo ("06") == 0) return UniversalMediaMaterials.Winter06;
            else if (materialNumber.compareTo ("07") == 0) return UniversalMediaMaterials.Winter07;
            else if (materialNumber.compareTo ("08") == 0) return UniversalMediaMaterials.Winter08;
            else if (materialNumber.compareTo ("09") == 0) return UniversalMediaMaterials.Winter09;
            else if (materialNumber.compareTo ("10") == 0) return UniversalMediaMaterials.Winter10;
            else if (materialNumber.compareTo ("11") == 0) return UniversalMediaMaterials.Winter11;
            else if (materialNumber.compareTo ("12") == 0) return UniversalMediaMaterials.Winter12;
            else if (materialNumber.compareTo ("13") == 0) return UniversalMediaMaterials.Winter13;
            else if (materialNumber.compareTo ("14") == 0) return UniversalMediaMaterials.Winter14;
            else if (materialNumber.compareTo ("15") == 0) return UniversalMediaMaterials.Winter15;
            else if (materialNumber.compareTo ("16") == 0) return UniversalMediaMaterials.Winter16;
            else if (materialNumber.compareTo ("17") == 0) return UniversalMediaMaterials.Winter17;
            else if (materialNumber.compareTo ("18") == 0) return UniversalMediaMaterials.Winter18;
            else if (materialNumber.compareTo ("19") == 0) return UniversalMediaMaterials.Winter19;
            else if (materialNumber.compareTo ("20") == 0) return UniversalMediaMaterials.Winter20;
            else if (materialNumber.compareTo ("21") == 0) return UniversalMediaMaterials.Winter21;
            else if (materialNumber.compareTo ("22") == 0) return UniversalMediaMaterials.Winter22;
            else if (materialNumber.compareTo ("23") == 0) return UniversalMediaMaterials.Winter23;
            else if (materialNumber.compareTo ("24") == 0) return UniversalMediaMaterials.Winter24;
            else if (materialNumber.compareTo ("25") == 0) return UniversalMediaMaterials.Winter25;
            else if (materialNumber.compareTo ("26") == 0) return UniversalMediaMaterials.Winter26;
            else if (materialNumber.compareTo ("27") == 0) return UniversalMediaMaterials.Winter27;
            else if (materialNumber.compareTo ("28") == 0) return UniversalMediaMaterials.Winter28;
            else if (materialNumber.compareTo ("29") == 0) return UniversalMediaMaterials.Winter29;
            else if (materialNumber.compareTo ("30") == 0) return UniversalMediaMaterials.Winter30;
            else if (materialNumber.compareTo ("31") == 0) return UniversalMediaMaterials.Winter31;
            else if (materialNumber.compareTo ("32") == 0) return UniversalMediaMaterials.Winter32;
            else if (materialNumber.compareTo ("33") == 0) return UniversalMediaMaterials.Winter33;
            else if (materialNumber.compareTo ("34") == 0) return UniversalMediaMaterials.Winter34;
        }
        System.err.println ("UniversalMediaMaterialFinder erroneous input: " + libraryName + materialNumber + ".  UniversalMediaMaterial not found, using ArtDeco00");
        return UniversalMediaMaterials.ArtDeco00; // error condition
    }
}
