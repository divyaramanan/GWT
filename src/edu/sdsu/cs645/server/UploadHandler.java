package edu.sdsu.cs645.server;
import java.io.*;
import java.awt.Image;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.util.Iterator;
import java.util.*;
import javax.activation.MimetypesFileTypeMap;
import javax.servlet.*;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import org.apache.commons.fileupload.*;
import org.apache.commons.fileupload.servlet.*;
import org.apache.commons.fileupload.portlet.*;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.FilenameUtils;
import com.google.gwt.user.client.Window;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.util.*;

public class UploadHandler extends HttpServlet
{
 protected void doPost(HttpServletRequest request, HttpServletResponse response)
   throws ServletException, IOException 
   {
     ServletContext servletContext = request.getSession().getServletContext();
     final String UPLOAD_DIRECTORY = getServletContext().getRealPath("/")+"/imgz";
	 
	


FileItemFactory factory = new DiskFileItemFactory();
 ServletFileUpload upload = new ServletFileUpload();
   // Create a new file upload handler
   if (ServletFileUpload.isMultipartContent(request)) {
   
    
   
   try{
   
    List<FileItem> iterator = new ServletFileUpload( new DiskFileItemFactory()).parseRequest(request);

	for(FileItem item : iterator)
       {
        if (!item.isFormField()) {
           String name = new File(item.getName()).getName();
		   String answer = "";
		   String line;
            item.write( new File(UPLOAD_DIRECTORY + File.separator + name));
           
		   try{
		     
			 String ext = name.trim().split("\\.")[1];
			 
			 
			 if(ext.equals("pdf"))
			 {
			 response.setContentType("text/html");
		     PDFTextStripper reader = new PDFTextStripper();
			 PDDocument pd =  PDDocument.load(UPLOAD_DIRECTORY + File.separator + name);
             String pageText = reader.getText(pd);
			 response.getWriter().print(pageText);
			 }
			 else
			 {
			 response.setContentType("text/html");
			 File is = new File(UPLOAD_DIRECTORY + File.separator +name);			 
             String content = "<img src =\"imgz/"+name+"\" />";
			 response.getWriter().print(content);
			
             }
			 
			 }
           catch(Exception e2)
            {
			response.setContentType("text/html");
          response.getWriter().print("text Value INNER :"+e2.getMessage()+","+UPLOAD_DIRECTORY);
            }
          }
        }
}
catch (Exception e) 
{
   response.setContentType("text/html");
   response.getWriter().print("text Value of outer catch :"+e.getMessage());
}
   }
    
   }
}
