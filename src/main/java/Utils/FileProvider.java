package Utils;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FileProvider {

   public static FileProvider fp=null;

    private FileProvider(){

    }

    public static FileProvider createInstance(){
        if(fp==null)
        return fp=new FileProvider();
        else return fp;
    }

    public File getFile(@NotNull String filename){
    if(!filename.equals("")) {
        File file=getFile(filename,Paths.get(".").toAbsolutePath().normalize().toString());
        return file;
    } else return null;
    }


    public File getFile(String filename,@NotNull String... directories){
        String directorypath="";
        for(String dir:directories){
            directorypath=dir+ File.separator;
        }
        return new File(directorypath+filename);
    }

}
