package gambar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ImageObserver;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ClassGambar{
public ImageIcon SourceIcon; public Image SourceImage; 
public ImageIcon ScaleIcon; public Image ScaleImage; 
public Image ResultImage; public Image ScaleResultImage; 
public ImageIcon ScaleResultIcon; 

public String URLImage; 
public boolean ScaledFlag=false; 
public BufferedImage SourceBuffer; 
public BufferedImage ResultBuffer; public long sWidth; 
public long sHeight;
ClassGambar(String Url, long width, long height){
    URLImage=Url;
    if(width<=0 ||height <=0){
        ScaledFlag=false;
    }
    else{
        ScaledFlag=true;
        sWidth=width;
        sHeight=height;
    }
}
 
public ImageIcon GetIcon(){
    if(!URLImage.equals("")){
        SourceIcon = new ImageIcon(URLImage); SourceImage = SourceIcon.getImage(); try{
            SourceBuffer=ImageIO.read(new File(URLImage));
        }
        catch(IOException x){
            JOptionPane.showMessageDialog(null, x.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
        }
        System.out.println(SourceIcon.getIconWidth());
        if(ScaledFlag){
            ScaleImage=SourceImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
            ScaleIcon=new ImageIcon(ScaleImage); return ScaleIcon;
        }
        else{
            return SourceIcon;
        }
    }
else{
    return null;
}
}
 
public void SaveImage(String url){ 
File tFile= new File(url); 
System.out.println(url); try{
    String fileName = tFile.getCanonicalPath();
    if(!fileName.endsWith(".jpeg")){
        tFile=new File(fileName+".jpeg");
    }
    ImageIO.write(ResultBuffer, "jpeg", tFile);
    System.out.println("sukses");
}
catch(IOException x){ JOptionPane.showMessageDialog(null, x.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
}
}

public void Grayscale(){ ResultBuffer=deepCopy(SourceBuffer); 
long tWidth=ResultBuffer.getWidth(); long tHeight=ResultBuffer.getHeight(); 
long x,y; 
int RGB,Red,Green,Blue,Gray; 
Color tWarna; for(x=0;x<tWidth;x++){ 
    for(y=0;y<tHeight;y++){
        RGB=ResultBuffer.getRGB((int)x, (int)y);
        tWarna=new Color(RGB);
        Red=tWarna.getRed();
        Green=tWarna.getGreen();
        Blue=tWarna.getBlue();
        Gray=(Red+Green+Blue)/3;
        tWarna=new Color(Gray,Gray,Gray);
        ResultBuffer.setRGB((int)x, (int)y, tWarna.getRGB());
    }
}
ResultImage=(Image) ResultBuffer;
ScaleResultImage=ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
ScaleResultIcon=new ImageIcon(ScaleResultImage);
} 


public void Biner(){ ResultBuffer=deepCopy(SourceBuffer);
long tWidth=ResultBuffer.getWidth(); long tHeight=ResultBuffer.getHeight();
long x,y;
int RGB,Red,Green,Blue,Gray;
Color tWarna; for(x=0;x<tWidth;x++){
    for(y=0;y<tHeight;y++){
        RGB=ResultBuffer.getRGB((int)x, (int)y);
        tWarna=new Color(RGB);
        Red=tWarna.getRed();
        Green=tWarna.getGreen();
        Blue=tWarna.getBlue();
        Gray=(Red+Green+Blue)/3;
        if (Gray<=128){
            Gray=0;
        }
        else{
            Gray=255;
        }
        tWarna=new Color(Gray,Gray,Gray);
        ResultBuffer.setRGB((int)x, (int)y, tWarna.getRGB());
    }
} 
ResultImage=(Image) ResultBuffer;
ScaleResultImage=ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
ScaleResultIcon=new ImageIcon(ScaleResultImage);
} 

public void Default(){ ResultBuffer=deepCopy(SourceBuffer);
long tWidth=ResultBuffer.getWidth(); long tHeight=ResultBuffer.getHeight();
long x,y;
int RGB,Red,Green,Blue;
Color tWarna; for(x=0;x<tWidth;x++){
    for(y=0;y<tHeight;y++){
        RGB=ResultBuffer.getRGB((int)x, (int)y);
        tWarna=new Color(RGB);
        Red=tWarna.getRed();
        Green=tWarna.getGreen();
        Blue=tWarna.getBlue();
        tWarna=new Color(Red,Green,Blue);
        ResultBuffer.setRGB((int)x, (int)y, tWarna.getRGB());
    }
} 
ResultImage=(Image) ResultBuffer; 
ScaleResultImage=ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT); 
ScaleResultIcon=new ImageIcon(ScaleResultImage);
} 

public void Brightness(){
    ResultBuffer=deepCopy(SourceBuffer);
    long tWidth=ResultBuffer.getWidth();
    long tHeight=ResultBuffer.getHeight();
    long x,y;
    int RGB,Red,Green,Blue;
    int k = 50;
    Color tWarna;
    for(x=0;x<tWidth;x++){
        for(y=0;y<tHeight;y++){
            RGB=ResultBuffer.getRGB((int)x, (int)y);
            tWarna=new Color(RGB);
            Red=tWarna.getRed();
            Green=tWarna.getGreen();
            Blue=tWarna.getBlue();
            if ((Red>=225)||(Green>=255)||(Blue>=255)){
                Red=255;
                Green=255;
                Blue=255;
            }
            else if ((Red<=0)||(Green<=0)||(Blue<=0)){
                Red=0;
                Green=0;
                Blue=0;
            }
            else {
                Red=Red+k;
                Green=Green+k;
                Blue=Blue+k;
            }
            tWarna=new Color(Red,Green,Blue);
            ResultBuffer.setRGB((int)x, (int)y, tWarna.getRGB());
        }
    }
ResultImage=(Image) ResultBuffer;
ScaleResultImage=ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
ScaleResultIcon=new ImageIcon(ScaleResultImage);
} 

public void flipV(){
    ResultBuffer=deepCopy(SourceBuffer);
    BufferedImage imge=new BufferedImage(SourceBuffer.getWidth(), SourceBuffer.getHeight(), BufferedImage.TYPE_INT_RGB);
    for(int y=0; y<SourceBuffer.getHeight();y++){
        for(int x=0; x<SourceBuffer.getWidth(); x++){
            imge.setRGB(x, y, SourceBuffer.getRGB(x,SourceBuffer.getHeight()-1-y));
        }
    }
    ResultImage=imge;
    ScaleResultImage=ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
    ScaleResultIcon=new ImageIcon(ScaleResultImage);
}

public void flipH(){
    ResultBuffer=deepCopy(SourceBuffer);
    BufferedImage img=new BufferedImage(SourceBuffer.getWidth(), SourceBuffer.getHeight(), BufferedImage.TYPE_INT_RGB);
    for(int y=0; y<SourceBuffer.getHeight();y++){
        for(int x=0; x<SourceBuffer.getWidth(); x++){
            img.setRGB(x, y, SourceBuffer.getRGB(x,1-SourceBuffer.getWidth()-x-y));
        }
    }
    ResultImage=img;
    ScaleResultImage=ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
    ScaleResultIcon=new ImageIcon(ScaleResultImage);
}

public void zoomin(){
    ResultBuffer=deepCopy(SourceBuffer);
    ResultImage=(Image) ResultBuffer;
    sWidth=sWidth * 2;
    sHeight=sHeight * 2;
    ScaleResultImage=ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
    ScaleResultIcon=new ImageIcon(ScaleResultImage);
}

public void zoomout(){
    ResultBuffer=deepCopy(SourceBuffer);
    ResultImage=(Image) ResultBuffer;
    sWidth=sWidth / 2;
    sHeight=sHeight / 2;
    ScaleResultImage=ResultImage.getScaledInstance((int)sWidth, (int)sHeight, Image.SCALE_DEFAULT);
    ScaleResultIcon=new ImageIcon(ScaleResultImage);
}

public void rotate180(){
ResultBuffer=deepCopy(SourceBuffer);
BufferedImage imge = new BufferedImage(SourceBuffer.getWidth(),SourceBuffer.getHeight(),BufferedImage.TYPE_INT_RGB);
for (int x=0; x<SourceBuffer.getWidth();x++){
    for (int y=0; y<SourceBuffer.getHeight();y++){
       
           imge.setRGB(x, y, SourceBuffer.getRGB( SourceBuffer.getWidth()-1-x, SourceBuffer.getHeight()-1-y));
       }
}
ResultImage=imge;
ScaleResultImage=ResultImage.getScaledInstance((int)sWidth, (int)sHeight,
Image.SCALE_DEFAULT);
ScaleResultIcon=new ImageIcon(ScaleResultImage);
}

public void rotasiCitra (double derajat, ImageObserver o) {
        ImageIcon icon = new ImageIcon(SourceBuffer);
        ResultBuffer = deepCopy(SourceBuffer);
        BufferedImage blankCanvas = new BufferedImage(icon.getIconWidth(), icon.getIconHeight(), BufferedImage.TYPE_INT_RGB);
        
        Graphics2D g2 = (Graphics2D) blankCanvas.getGraphics();
        g2.rotate(Math.toRadians(derajat), icon.getIconWidth()/2, icon.getIconHeight()/2);
        g2.drawImage(SourceBuffer, 0, 0, o);
        
        
        
        ResultBuffer = blankCanvas;
        
        ScaleResultImage = ResultBuffer.getScaledInstance((int) sWidth, (int) sHeight, Image.SCALE_DEFAULT);
        ScaleResultIcon = new ImageIcon(ScaleResultImage);
        
        
    }



static BufferedImage deepCopy(BufferedImage bi){ ColorModel cm=bi.getColorModel();
boolean isAlphaPremultiplied = cm.isAlphaPremultiplied(); WritableRaster raster = bi.copyData(null);
return new BufferedImage(cm,raster,isAlphaPremultiplied,null);
}

    void SharpFilter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void SharpFilter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void SharpFilter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void SharpenFilter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void SharpenFilter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void Blur() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void Blur() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void Blur() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void Blur() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void Blur() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void Blur() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void SharpenFilter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    void SharpenFilter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

       
