//package com.darwinsys.io;
package org.web3d.x3d.dis.playerrecorder;

import java.io.*;

/**
 * Some simple file I-O primitives reimplemented in Java.
 * All methods are static, since there is no state.
 * @version $Id: FileIO.java,v 1.18 2004/05/30 01:39:27 ian Exp $
 */
public class FileIO
{
  /** Nobody should need to create an instance; all methods are static */
  private FileIO()
  {
    // Nothing to do
  }

  /** Copy a file from one filename to another
     * @param  inName  input file name
     * @param outName output file name
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException */
  public static void copyFile(String inName, String outName) throws FileNotFoundException, IOException
  {
    BufferedInputStream is  = new BufferedInputStream(new FileInputStream(inName));
    BufferedOutputStream os = new BufferedOutputStream(new FileOutputStream(outName));
    copyFile(is, os, true);
  }

  /** Copy a file from an opened InputStream to opened OutputStream
     * @param is  input stream
     * @param os output stream
     * @param closeOutputStream whether to close output stream upon completion
     * @throws java.io.IOException */
  public static void copyFile(InputStream is, OutputStream os, boolean closeOutputStream) throws IOException
  {
      try (is) {
          byte[] b = new byte[BLKSIZ]; // byte array read from the InputStream file
          int i;
          while ((i = is.read(b)) != -1) { // the byte read from the file
              os.write(b, 0, i);
          }
      }
      if (closeOutputStream)
          os.close();
  }

  /** Copy a file from an opened Reader to opened Writer
     * @param is  input stream
     * @param os output stream
     * @param closeOutputStream whether to close output stream upon completion
     * @throws java.io.IOException */
  public static void copyFile(Reader is, Writer os, boolean closeOutputStream) throws IOException
  {
      try (is) {
          int b;				// the byte read from the file
          Reader bis = new BufferedReader(is);
          while ((b = bis.read()) != -1) {
              os.write(b);
          }
      } // the byte read from the file
      if (closeOutputStream)
          os.close();
  }

  /** Copy a file from a filename to a PrintWriter.
     * @param inName input file name
     * @param pw output printWriter
     * @param closeOutputStream whether to close output stream upon completion
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException */
  public static void copyFile(String inName, PrintWriter pw, boolean closeOutputStream)
      throws FileNotFoundException, IOException
  {
    BufferedReader ir = new BufferedReader(new FileReader(inName));
    copyFile(ir, pw, closeOutputStream);
  }

  /** Open a file and read the first line from it.
     * @param inName input file name
     * @return line
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException */
  public static String readLine(String inName)
      throws FileNotFoundException, IOException
  {
      String line;
      try (BufferedReader is = new BufferedReader(new FileReader(inName))) {
          line = is.readLine();
      }
    return line;
  }
  /** The size of blocking to use */
  protected static final int BLKSIZ = 16384;

  /** *  Copy a data file from one filename to another, alternate method.As the name suggests, use my own buffer instead of letting
 the BufferedReader allocate and use the buffer.
     * @param  inName  input file name
     * @param outName output file name
     * @throws java.io.FileNotFoundException
     * @throws java.io.IOException
   */
  public void copyFileBuffered(String inName, String outName) throws
      FileNotFoundException, IOException
  {
      OutputStream os;
      try (InputStream is = new FileInputStream(inName)) {
          os = new FileOutputStream(outName);
          int count;		// the byte count
          byte[] b = new byte[BLKSIZ];	// the bytes read from the file
          while ((count = is.read(b)) != -1) {
              os.write(b, 0, count);
          }
      }
      os.close();
  }

  /** Read the entire contents of a Reader into a String
     * @param is  input stream Reader
     * @return 
     * @throws java.io.IOException */
  public static String readerToString(Reader is) throws IOException
  {
    StringBuilder sb = new StringBuilder();
    char[] b = new char[BLKSIZ];
    int n;

    // Read a block. If it gets any chars, append them.
    while ((n = is.read(b)) > 0) {
      sb.append(b, 0, n);
    }

    // Only construct the String object once, here.
    return sb.toString();
  }

  /** Read the content of a Stream into a String
     * @param is  input stream
     * @return resulting text
     * @throws java.io.IOException */
  public static String inputStreamToString(InputStream is)
      throws IOException
  {
    return readerToString(new InputStreamReader(is));
  }

  /** Write a String as the entire content of a File
     * @param text to write as output
     * @param fileName input file name
     * @throws java.io.IOException */
  public static void stringToFile(String text, String fileName)
      throws IOException
  {
      try (BufferedWriter os = new BufferedWriter(new FileWriter(fileName))) {
          os.write(text);
          os.flush();
      }
  }

  /** Open a BufferedReader from a named file.
     * @param fileName input file name
     * @return result
     * @throws java.io.IOException */
  public static BufferedReader openFile(String fileName)
      throws IOException
  {
    return new BufferedReader(new FileReader(fileName));
  }
}