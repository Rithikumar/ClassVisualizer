package com.basic;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileSystemView;

/**
 * <p>This class is used walk through all the files and print it in tree format</p>
 * @author rithi-zstch1028
 *
 */
public class FileVisualizer {
	
	/**
	 * This method gets the number of tab spaces it must leave
	 * @param n
	 * @return
	 */
	public static String space(int n){
        String space = "";
        for(int i = 0 ; i < n ; i++){
            space += "     ";
        }
        return space;
    }
	
	/**
	 * This method checks whether the give File is folder or file, it recursively calls itself if its a folder
	 * @param folder
	 * @param num
	 */
    public static void printFiles(File folder, int num){
        System.out.println(space(num)+"|__ "+folder.getName());
        if(folder.isDirectory()){
            File[] files = folder.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    printFiles(file,num+1);
                } else
                    System.out.println(space(num+1)+"|__ "+file.getName());
            }
        }
    }

    public static void main(String[] args){
        String path = "";
        JFileChooser jfc = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        jfc.setDialogTitle("Choose a directory");
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnValue = jfc.showOpenDialog(null);
        if(returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFolder = jfc.getSelectedFile();
            if(selectedFolder.isDirectory())
                path = String.valueOf(selectedFolder.getAbsoluteFile());
        }
        System.out.println(path);
        File folder = new File(path);
        printFiles(folder,0);
    }

}
