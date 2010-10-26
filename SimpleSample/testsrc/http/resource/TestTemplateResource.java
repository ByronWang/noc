package http.resource;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import java.io.Reader;
import java.nio.ByteBuffer;

import junit.framework.TestCase;

public class TestTemplateResource extends TestCase {
    
    public void testStream(){
        

        try {
            
            ByteArrayOutputStream out = new ByteArrayOutputStream(); 
            OutputStreamWriter pwriter = new OutputStreamWriter(out);
            

            pwriter.write("test");    
            pwriter.write("test");    
            pwriter.write("test");    
            pwriter.write("test");    
            pwriter.write("test");    
            pwriter.write("test");    
            pwriter.write("test");    
            pwriter.write("test");    
            pwriter.write("test");    
            pwriter.write("test");   
            pwriter.flush();

            ByteArrayInputStream in = new ByteArrayInputStream(out.toByteArray());
            Reader preader = new InputStreamReader(in);
            
            char[] buffer = new char[1024];
            
            int length = preader.read(buffer);
            
            System.out.println(new String(buffer,0,length));
           
            
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        
        
    }
}
