package httpd.server;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;

import junit.framework.TestCase;

public class NanoServerTest extends TestCase {
    
    
    
    
    public void testJarPath()throws Exception{
        ClassLoader cl= this.getClass().getClassLoader();// getResource("htdocs");
        URL url = cl.getResource("freemarker/core/Comment.class");
        
        if(url!=null){
            if("jar".equals(url.getProtocol())){
                System.out.println(url.openStream().getClass().getName());
                URLConnection conn = url.openConnection();
//                long  lastModified = conn.getLastModified();
//
//                FileInputStream f = new FileInputStream(url.getFile());
//                Channel c =  f.getChannel();
//                if(c!=null){
//                    System.out.println("OK");
//                }
                
                
                ByteArrayOutputStream bout = new ByteArrayOutputStream();                                
                BufferedInputStream bio = new BufferedInputStream(conn.getInputStream());
                int bufferSize = 1024;
                byte[] data=new byte[bufferSize];
                int count = -1;
                while(  (count = bio.read(data)) > 0 ){
                    bout.write(data, 0, count);
                }
                bout.flush();
                
                bio.close();
                bout.close();
                
                byte[] bb = bout.toByteArray();
                
                System.out.println(bb.length);
            }else if("file".equals(url.getProtocol())){
                File file = new File(url.getFile());
                System.out.println(file.lastModified());
                
                File lo = new File(file,"login.htm");
                if(lo.exists()){
                    System.out.println(lo.getPath() + " - " + lo.lastModified());
                }
            }
        }
        
    }
    
    
    
//    public void testStaticPath() {
//        // URL url = this.getClass().getResource("/noc/frame/Op.class");
//
//        ClassLoader cl = this.getClass().getClassLoader();
//        // if (url != null) {
//        // PrintObejct.print(URL.class, url);
//        // File file = new File(url.getFile());
//        // // if (file.exists()) {
//        // // System.out.println(file.getParent());
//        // // }
//        // }
//        //
//
//        URL url2 = cl.getResource("htdocs");
//        File file = new File(url2.getFile());
//        File f = new File(file, "login.htm");
//        System.out.println(f.getPath());
//        
//        System.out.println(f.lastModified());
//        //
//         URL url3 = cl.getResource("freemarker/cache/TemplateLoader.class");
//        // // URL url2 = cl.getResource("simple-4.1.21.jar");
//        // if (url2 != null) {
//        // // PrintObejct.print(URL.class, url2);
//        // JarURLConnection conn = (JarURLConnection)url2.openConnection();
//        // JarFile jarFile = conn.getJarFile();
//        // // cl.get
//        // // jarFile.
//        // // jarFile.entries()
//        //
//         
//         try {
//             
//             Object o = url3.getContent();
//             System.out.println(url3.getProtocol());
////            System.out.println(url3.getContent() .openConnection().getLastModified());
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
////         JarFile file1 = new JarFile(url3.getFile());
////         if (file1.exists()) {
////             Object o = file1.getName();
////         System.out.println(file1.lastModified());
////         }
//        // File appHome = file.getParentFile().getParentFile();//
//        // .getParentFile();
//        // System.out.println(appHome);
//        // System.out.println("dd - " + appHome.lastModified());
//        // System.out.println(appHome.listFiles());
//        // }
//
//    }
//
//    
//    
//    public void testJarPath()throws Exception{
//        ClassLoader cl= this.getClass().getClassLoader();// getResource("htdocs");
//        URL url = cl.getResource("htdocs");
//        
//        if(url!=null){
//            if("jar".equals(url.getProtocol())){
////                URLConnection conn = url.openConnection();
//                long  lastModified = url.openConnection().getLastModified();
//
//                File file = new File(url.getFile());
//                file.getName();
////                file.
//                System.out.println(lastModified);
//            }else if("file".equals(url.getProtocol())){
//                File file = new File(url.getFile());
//                System.out.println(file.lastModified());
//                
//                File lo = new File(file,"login.htm");
//                if(lo.exists()){
//                    System.out.println(lo.getPath() + " - " + lo.lastModified());
//                }
//            }
//        }
//        
//    }
}
