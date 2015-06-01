package edu.sdsu.cs645.client;
import java.io.*;
import com.google.gwt.core.client.*;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client-side stub for the RPC service.
 */
@RemoteServiceRelativePath("notepad")
public interface NotepadService extends RemoteService {
  
  String save(String s) throws IllegalArgumentException;
  String load() throws IllegalArgumentException;
  String validateLogin(String s) throws IllegalArgumentException;
  
}
