package com.jipt.fileformats;

// Victor Rego
// PGM Loader, loads P2 and P5 type files
// 11-5-01


import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.MemoryImageSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class PGMLoader
{

  // PGMHeader
  static class PGMHeader
  {
    public int width  = -1;
    public int height = -1;
    public int grays = -1;
    public String type= null;

    // reads in the header info
    public Image read(String fileName) throws IOException
    {
      File file = new File(fileName);
      FileInputStream input = null;
      BufferedReader reader = null;
      boolean headerFull = false;
      String line = null;
      StringTokenizer st;
      Image image = null;

      try
      {
        input = new FileInputStream(file);
        reader = new BufferedReader(new InputStreamReader(input));

        while (!headerFull)
        {
          line = reader.readLine();
          while(line.charAt(0)=='#')
          {
            line = reader.readLine();
          }
          st = new StringTokenizer(line);
          while(st.hasMoreTokens())
          {
            if(type == null)
            {
              type = (String)st.nextToken();
            }
            else if(width == -1)
            {
              width = Integer.parseInt((String)st.nextToken());
            }
            else if(height == -1)
            {
              height = Integer.parseInt((String)st.nextToken());
            }
            else if(grays == -1)
            {
              grays = Integer.parseInt((String)st.nextToken());
            }
          }// end while(st.hasMoreTokens())

          headerFull = (width != -1 && height != -1 && grays != -1 && type != null);

        } // end while(!headerFull)

        int imageSize = width * height;
        int imageData[] = new int[imageSize];
        float grayNorm = 255/grays;

        if(type.equals("P2"))
        {
          int temp=0;
          int index=0;
          line=reader.readLine();

          while(line != null)
          {
            st = new StringTokenizer(line);
            while(st.hasMoreTokens())
            {
              temp = (int)(Integer.parseInt((String)st.nextToken()) * grayNorm);
              imageData[index] = (((temp << 16) | (temp << 8) | temp) | 0xff000000);
              index++;
            }
            line=reader.readLine();
          }

        }// end if(type.equals("P2"))

        if(type.equals("P5"))
        {
          int temp = 0;
          int index= 0;

          while(index < imageSize)
          {
            temp = (int)reader.read();
            imageData[index] = (((temp << 16) | (temp << 8) | temp) | 0xff000000);
            index++;
          }

        }//end if(type.equals("P5"))

        image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width,
                height,imageData,0,width));

      }// end try
      catch(Exception e)
      {
      }

      return(image);

    }// end public Image read(String fileName) throws IOException
  }// end static class PGMHeader


  // loads PGM filename passed by FileManager
  public static Image load(String filename)
  {
    try
    {
      FileInputStream fs = new FileInputStream(filename);
      PGMHeader pgmH = new PGMHeader();
      return(pgmH.read(filename));
    }
    catch(IOException ex)
    {
      return(null);
    }
  }// end public static Image load(String filename)

}// end public class PGMLoader
