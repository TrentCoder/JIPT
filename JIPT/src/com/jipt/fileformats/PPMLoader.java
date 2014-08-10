package com.jipt.fileformats;

// Victor Rego
// PPM Loader, loads P3 and P6 type images
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

public class PPMLoader
{
  // PPMHeader
  static class PPMHeader
  {
    public int width  = -1;
    public int height = -1;
    public int colors = -1;
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
            else if(colors == -1)
            {
              colors = Integer.parseInt((String)st.nextToken());
            }
          }// end while(st.hasMoreTokens())

          headerFull = (width != -1 && height != -1 && colors != -1 && type != null);

        } // end while(!headerFull)

        int imageSize = width * height;
        int imageData[] = new int[imageSize];
        float colorNorm = 255/colors;

        if(type.equals("P3"))
        {
          int tempR=0;
          int tempG=0;
          int tempB=0;
          int index=0;
          line=reader.readLine();

          while(line != null)
          {
            st = new StringTokenizer(line);
            while(st.hasMoreTokens())
            {
              tempR = (int)(Integer.parseInt((String)st.nextToken()) * colorNorm);
              tempG = (int)(Integer.parseInt((String)st.nextToken()) * colorNorm);
              tempB = (int)(Integer.parseInt((String)st.nextToken()) * colorNorm);
              imageData[index] = (((tempR << 16) | (tempG << 8) | tempB) | 0xff000000);
              index++;
            }
            line=reader.readLine();
          }

        }// end if(type.equals("P3"))

        if(type.equals("P6"))
        {
          int tempR=0;
          int tempG=0;
          int tempB=0;
          int index=0;

          while(index < imageSize)
          {
            tempR = (int)reader.read();
            tempG = (int)reader.read();
            tempB = (int)reader.read();
            imageData[index] = (((tempR << 16) | (tempG << 8) | tempB) | 0xff000000);
            index++;
          }

        }//end if(type.equals("P6"))


        image = Toolkit.getDefaultToolkit().createImage(new MemoryImageSource(width,
                height,imageData,0,width));

      }// end try
      catch(Exception e)
      {
      }

      return(image);

    }// end public Image read(String fileName) throws IOException
  }// end static class PPMHeader


  // loads ppm filename passed by FileManager
  public static Image load(String filename)
  {
    try
    {
      FileInputStream fs = new FileInputStream(filename);
      PPMHeader ppmH = new PPMHeader();
      return(ppmH.read(filename));
    }
    catch(IOException ex)
    {
      return(null);
    }
  }// end public static Image load(String filename)

}// end public class PPMLoader
