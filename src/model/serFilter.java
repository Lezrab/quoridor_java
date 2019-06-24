package quoridor.model;
import java.io.*;
import java.io.File;
import java.util.*;
import javax.swing.filechooser.FileFilter;


public class serFilter extends FileFilter
{
   public boolean accept(File f)
  {
       if (f.isDirectory())
         {
           return false;
         }

        String s = f.getName();

       return s.endsWith(".ser")||s.endsWith(".SER");
  }

  public String getDescription()
 {
      return "*.ser,*.SER";
 }

}
