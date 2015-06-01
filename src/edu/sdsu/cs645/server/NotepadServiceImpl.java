package edu.sdsu.cs645.server;
import java.io.*;
import java.util.*;
import java.text.*;
import com.google.gwt.core.client.*;
import edu.sdsu.cs645.client.NotepadService;
import edu.sdsu.cs645.shared.FieldVerifier;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * The server-side implementation of the RPC service.
 */
@SuppressWarnings("serial")
public class NotepadServiceImpl extends RemoteServiceServlet implements
    NotepadService {

  public String load() throws IllegalArgumentException {
   String path = getServletContext().getRealPath("/");
  String filename = path + "/data.txt";
 String answer = "";
  String line;
  File file = new File(path + "/data.txt");
  SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
  try{
  BufferedReader in = new BufferedReader(new FileReader(filename));
 while((line = in.readLine())!= null)
 answer += line;
  in.close();
  
  }
  catch(Exception e)
  {
  return "Failed to read File";
  }
  return sdf.format(file.lastModified())+"|"+answer;
  }
  
   public String save(String contents) throws IllegalArgumentException {
  String path = getServletContext().getRealPath("/");
  String filename = path + "/data.txt";
  String answer = "";
  String line;
  try{
  PrintWriter out = new PrintWriter(new FileWriter(filename));
  contents = contents.replace("\r\n|\n","<br/>");
  out.print(contents);
  out.close();
  
  }
  catch(Exception e)
  {
  return "Failed to save File:"+e.getMessage();
  }
  return "File Saved Successfully";
  }
 
  public String validateLogin(String s) throws IllegalArgumentException {
   if(s.trim().equals("sp2015"))
   return "ok";
   else
   return "invalid";
  }
  
  public String upload(String f) throws IllegalArgumentException {
   return f;
  }
}
