package GoodSplash;

import java.io.*;
import java.nio.file.Files;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author brett
 */
public class TvConvertGutts extends Thread{
    /**
     * my member objects
     */
    private FileOutputStream fout;
    private FileInputStream fin;
    private int prog = 0;
    private Boolean nf;
    private File fileOut;
    private File fileIn = null;
    private final File WORKINGDIR = new File(System.getProperty("user.home") + File.separator + "Documents" + File.separator + "ConvertedTvFiles" + File.separator);
    private volatile Thread thr;
    private volatile boolean running = true;

    /**
     * file is used for
     *
     * @param tvConvertInterface
     * @param fileIn1 is the file path pass in from the button click
     * @param mode what mode does
     */
    public TvConvertGutts(TvConvertInterface tvConvertInterface, File fileIn1, String mode) {
        if(fileIn1 != null){
        fileIn = fileIn1;
        }else{
           JOptionPane.showMessageDialog(null, "Bad or No File Selection \n", "File doesnt exist", JOptionPane.WARNING_MESSAGE);           
        }
        thr = new Thread("cool") {
            @Override
            public void run() {
                while (running == true) {
                    try {
                       newWorkFileName();
                        long length = fileIn.length();
                        long counter = 1;
                        int r;
                        byte[] b = new byte[1024];
                        fin = new FileInputStream(fileIn);
                        fout = new FileOutputStream(fileOut);
                        tvConvertInterface.updateJLabel2("Creating File");
                        while ((r = fin.read(b)) != -1) {
                            counter += r;
                            fout.write(b, 0, r);
                            prog = (int) Math.round(100 * counter / length);
                            tvConvertInterface.updateJProgressbar1(prog);
                            
                        }
                        fin.close();
                        fout.close();
                        tvConvertInterface.disableStopBtn();
                        tvConvertInterface.updateJLabel2("Work File Created");
                       running = false;
                    } catch (NullPointerException | IOException ex) {
                        tvConvertInterface.disableStopBtn();
                        tvConvertInterface.updateJLabel2("File is not valid");
                        Logger.getLogger(TvConvertGutts.class.getName()).log(Level.SEVERE, null, ex);
                        tvConvertInterface.updateJProgressbar1(0);
                        running = false;
                    }
                }
            }
        };
    }

    /**
     * This is my description check and set which kicks everything of
     *
     * @param tvConvertInterface here is more
     */
    public void checkAndSet(TvConvertInterface tvConvertInterface) {
        tvConvertInterface.updateJLabel2("Check for Directory");
        try {
            checkWorkingDirectory(tvConvertInterface);
        } catch (IOException ex) {
            tvConvertInterface.updateJLabel2("No Work Directory");
            JOptionPane.showMessageDialog(null, "No Work Dir....\nLets Create it now?\n" + ex, "Directory doesnt exist", JOptionPane.CANCEL_OPTION);
            nf = new File(WORKINGDIR.toString()).mkdir();
            Logger.getLogger(TvConvertGutts.class.getName()).log(Level.SEVERE, null, ex);
        }
        thr.start();
    }

    /**
     * This is my description for check the working directory
     *
     * @param tvConvertInterface returns a boolean
     * @return Boolean
     *
     */
    private Boolean checkWorkingDirectory(TvConvertInterface tvConvertInterface) throws IOException {
        tvConvertInterface.updateJLabel2("Check for Directory");
        Files.newDirectoryStream(WORKINGDIR.toPath());
        tvConvertInterface.updateJLabel2("Directory Present");
        return nf;
    }

    private String newWorkFileName(){
        String fileInName = fileIn.getName();
        try{
        fileInName = fileInName.substring(0, fileInName.lastIndexOf("."));
        fileOut = new File(WORKINGDIR + File.separator + fileInName + ".mp4");
        }catch(NullPointerException ex){
            Logger.getLogger(TvConvertGutts.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fileInName;
    }

    public boolean terminate() throws IOException{
            fin.close();
            fout.flush();
            fout.close();
            thr.interrupt();
            System.out.println(thr.getState());
            TvConvertInterface bst =  new TvConvertInterface(null, true);
            bst.disableStopBtn();
        return false;
    }
}
