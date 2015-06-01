package edu.sdsu.cs645.client;

import edu.sdsu.cs645.shared.FieldVerifier;
import com.google.gwt.core.client.*;
import com.google.gwt.event.dom.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.client.ui.FormPanel.SubmitCompleteEvent;
import com.google.gwt.user.client.Window;



public class Notepad implements EntryPoint {
  
  private static final String SERVER_ERROR = "An error occurred while "
      + "attempting to contact the server. Please check your network "
      + "connection and try again.";
  private final String extention = ".pdf";


  
  private final NotepadServiceAsync notepadService = GWT.create(NotepadService.class);
  private RichTextArea pad;
  private HTML status;
  

  
  public void onModuleLoad() {
  status = new HTML();
  status.setStyleName("statusLine");
  buildloginPanel();
    
  }
  
  private void buildloginPanel(){
  FlowPanel loginPanel = new FlowPanel();
  loginPanel.getElement().setId("log_panel");
  final PasswordTextBox password = new PasswordTextBox();
  loginPanel.add(new HTML("<H1>Please enter your password</H1>"));
  FlowPanel textPanel = new FlowPanel();
  textPanel.add(new Label("Password"));
  textPanel.add(password);
  textPanel.setStyleName("passwordEnter");
  loginPanel.add(textPanel);
  FlowPanel bPanel = new FlowPanel();
  Button loginButton = new Button("Login");
  Button clearButton = new Button("Clear");
  loginButton.setStyleName("log_button");
  clearButton.setStyleName("log_button");
  bPanel.setStyleName("blog_panel");
  clearButton.addClickHandler(new ClickHandler(){
  public void onClick(ClickEvent e){ 
  password.setText("");
  }
  
  });
  loginButton.addClickHandler(new ClickHandler(){
  public void onClick(ClickEvent e){ 
  validateLogin(password.getText());
  }
  
  });
  bPanel.add(loginButton);
  bPanel.add(clearButton);
  loginPanel.add(bPanel);
  loginPanel.add(status);
  
  RootPanel.get().add(loginPanel);
  password.setFocus(true);
  }
  
  private void buildMainPanel(){
  FlowPanel main = new FlowPanel();
  main.add(new  HTML("<h1>Online Notepad</h1>"));
  main.add(getButtonPanel());
  pad = new RichTextArea();
  main.add(pad);
  FlowPanel statusShow = new FlowPanel();
  statusShow.add(status);
  statusShow.setStyleName("statusShow");
  main.add(statusShow);
  
  HorizontalPanel vp = new HorizontalPanel();
     vp.setStyleName("vPanel");
	Button uploadButton = new Button("Upload");
   uploadButton.setStyleName("upButton");
     final FileUpload fileUpload = new FileUpload();
	 fileUpload.setName("uploadFormElement");
	   fileUpload.setStyleName("uploadStyle");
	 final FormPanel form = new FormPanel();
	  form.setAction("/gwt028/notepad/UploadHandler");
      form.setEncoding(FormPanel.ENCODING_MULTIPART);
      form.setMethod(FormPanel.METHOD_POST);
	  
	  vp.add(new HTML("<label>Choose file to Upload:</label>"));
	  vp.add(fileUpload);
	  vp.add(uploadButton);
	  form.add(vp);
	
       
        uploadButton.addClickHandler(new ClickHandler() {
            public void onClick(ClickEvent event) {
         
		   String filename = fileUpload.getFilename();
		   String ext = filename.split("\\.")[1];
		   int ok = 0;
		   if(ext.equals("pdf")||ext.equals("png")||ext.equals("jpg")||ext.equals("jpeg")||ext.equals("bmp")||ext.equals("gif"))
		   ok = 1;
            if (filename.length() == 0) {
               status.setText("No File Specified!");
            } else {
               if(ok == 0)
               	{status.setText("Only Pdf or Image allowed");	
                }
               else				
              {
			  form.submit();
			  }		          
            }	
            }
          });
		
		  form.addSubmitCompleteHandler(new FormPanel.SubmitCompleteHandler() {
         @Override
         public void onSubmitComplete(SubmitCompleteEvent event) {
		    
            pad.setHTML(pad.getHTML()+"<br/>"+event.getResults());				
         }
      });
       
	  
  statusShow.add(form);
  
  RootPanel.get().clear();
  RootPanel.get().add(main);
  loadPanel();
  }
  
  
  
  
 private FlowPanel getButtonPanel(){
 FlowPanel p = new FlowPanel();
 Button save = new Button("save");
 Button load = new Button("load");
 Button erase = new Button("Clear");
 Button logout = new Button("Logout");
 save.setStyleName("my_button");
 load.setStyleName("my_button");
 erase.setStyleName("my_button");
 logout.setStyleName("my_button");
 save.addClickHandler(new ClickHandler(){
 public void onClick(ClickEvent e){ savePanel();}
 });
  load.addClickHandler(new ClickHandler(){
 public void onClick(ClickEvent e){ loadPanel();}
 });
 erase.addClickHandler(new ClickHandler(){
 public void onClick(ClickEvent e){ pad.setHTML("");}
 });
 logout.addClickHandler(new ClickHandler(){
 public void onClick(ClickEvent e){ 
 RootPanel.get().clear();
 buildloginPanel();
 status.setText("");
 }
 });
 p.setStyleName("button_panel");
 p.add(save);
 p.add(load);
 p.add(erase);
 p.add(logout);
 return p;
 }
 
 private void savePanel(){
 AsyncCallback callback = new AsyncCallback(){
 public void onSuccess(Object result)
 {
 status.setText((String)result);
 }
 public void onFailure (Throwable err){}
 };
 notepadService.save(pad.getHTML(),callback);
 }
 
 private void loadPanel(){
 AsyncCallback callback = new AsyncCallback(){
 public void onSuccess(Object result)
 {
 String savedResult = (String)result;
 int index = savedResult.indexOf("|");
 String display = savedResult.substring(index+1);
 String modified = savedResult.substring(0,index);
 pad.setHTML(display);
 status.setHTML("Last Modified: "+modified);
 }
 public void onFailure (Throwable err){}
 };
 notepadService.load(callback);
 }
 
 private void validateLogin(String Password){
 AsyncCallback callback = new AsyncCallback(){
 public void onSuccess(Object result)
 {
 String answer = ((String)result);
 if(answer.equals("ok")){
 status.setText("");
 buildMainPanel();
 }
 else
 status.setText("Your Password is Invalid");

 }
 public void onFailure (Throwable err){
 status.setText("Failed"+err.getMessage());
 }
 };
 notepadService.validateLogin(Password,callback);
 }
}
