package edu.sdsu.cs645.client;
import java.io.*;
import com.google.gwt.core.client.*;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>NotepadService</code>.
 */
public interface NotepadServiceAsync {

  void save(String contents ,AsyncCallback<String> callback)throws IllegalArgumentException;
  void load(AsyncCallback<String> callback)throws IllegalArgumentException;
  void validateLogin(String s ,AsyncCallback<String> callback)throws IllegalArgumentException;
 
}
