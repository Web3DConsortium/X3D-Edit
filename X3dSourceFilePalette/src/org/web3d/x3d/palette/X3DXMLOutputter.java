package org.web3d.x3d.palette;

import java.io.IOException;
import java.io.Writer;
import java.util.List;
import org.jdom.Attribute;
import org.jdom.Element;
import org.jdom.Namespace;
import org.jdom.output.EscapeStrategy;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

/**
 * X3DXMLOutputter.java
 * Created on Mar 7, 2008
 *
 * MOVES Institute
 * Naval Postgraduate School, Monterey, CA, USA
 * www.nps.edu
 *
 * This is a distasteful way to get jdom to output apostrophes as the outermost
 * attribute quote character instead of double quotes; and it prevents escaping all
 * inner single and double quotes.  (Don't know if the latter will bite us later.)
 * It's distasteful because I lifted the relevant methods from the JDOM 1.0 source
 * and overrode the methods I needed to.  This might (probably will) break if JDOM is
 * ever updated.
 * 
 * @author Mike Bailey
 * @version $Id$
 */
public class X3DXMLOutputter extends XMLOutputter
{
  
  public X3DXMLOutputter(Format fmt)
  {
    super(fmt);
  }
  
  public X3DXMLOutputter()
  {
    super();
  }
  
  @Override
  protected void printAttributes(Writer out, List attributes, Element parent, XMLOutputter.NamespaceStack namespaces) throws IOException
  {
    for (int i = 0; i < attributes.size(); i++) {
      Attribute attribute = (Attribute) attributes.get(i);
      Namespace ns = attribute.getNamespace();
      if ((ns != Namespace.NO_NAMESPACE) &&
          (ns != Namespace.XML_NAMESPACE)) {
        printNamespace(out, ns, namespaces);
      }

      out.write(" ");
      printQualifiedName(out, attribute);
      out.write("=");

      out.write("'"); //out.write("\"");
      out.write(escapeAttributeEntities(attribute.getValue()));
      out.write("'"); //out.write("\"");
    }

  }

  @Override
    /**
     * This will take the pre-defined entities in XML 1.0 and
     * convert their character representation to the appropriate
     * entity reference, suitable for XML attributes.  It does not convert
     * the single quote (') because it's not necessary as the outputter
     * writes attributes surrounded by double-quotes.
     *
     * @param str <code>String</code> input to escape.
     * @return <code>String</code> with escaped content.
     */
    public String escapeAttributeEntities(String str) {
        StringBuffer buffer;
        char ch;
        String entity;
        EscapeStrategy strategy = Format.getRawFormat().getEscapeStrategy(); //currentFormat.escapeStrategy;

        buffer = null;
        for (int i = 0; i < str.length(); i++) {
            ch = str.charAt(i);
            switch(ch) {
                case '<' :
                    entity = "&lt;";
                    break;
                case '>' :
                    entity = "&gt;";
                    break;
/*
                case '\'' :
                    entity = "&apos;";
                    break;

                case '\"' :
                    entity = "&quot;";
                    break;
*/
                case '&' :
                    entity = "&amp;";
                    break;
                case '\r' :
                    entity = "&#xD;";
                    break;
                case '\t' :
                    entity = "&#x9;";
                    break;
                case '\n' :
                    entity = "&#xA;";
                    break;
                default :
                    if (strategy.shouldEscape(ch)) {
                        entity = "&#x" + Integer.toHexString(ch) + ";";
                    }
                    else {
                        entity = null;
                    }
                    break;
            }
            if (buffer == null) {
                if (entity != null) {
                    // An entity occurred, so we'll have to use StringBuffer
                    // (allocate room for it plus a few more entities).
                    buffer = new StringBuffer(str.length() + 20);
                    // Copy previous skipped characters and fall through
                    // to pickup current character
                    buffer.append(str.substring(0, i));
                    buffer.append(entity);
                }
            }
            else {
                if (entity == null) {
                    buffer.append(ch);
                }
                else {
                    buffer.append(entity);
                }
            }
        }

        // If there were any entities, return the escaped characters
        // that we put in the StringBuffer. Otherwise, just return
        // the unmodified input string.
        return (buffer == null) ? str : buffer.toString();
    }


  private void printQualifiedName(Writer out, Attribute a) throws IOException
  {
    String prefix = a.getNamespace().getPrefix();
    if ((prefix != null) && (!prefix.equals(""))) {
      out.write(prefix);
      out.write(':');
      out.write(a.getName());
    }
    else {
      out.write(a.getName());
    }
  }

  private void printNamespace(Writer out, Namespace ns,
                               NamespaceStack namespaces)
      throws IOException
  {
    String prefix = ns.getPrefix();
    String uri = ns.getURI();

    // Already printed namespace decl?
    if (uri.equals(namespaces.getURI(prefix))) {
      return;
    }

    out.write(" xmlns");
    if (!prefix.equals("")) {
      out.write(":");
      out.write(prefix);
    }
    out.write("=\"");
    out.write(uri);
    out.write("\"");
    namespaces.push(ns);
  }
}
