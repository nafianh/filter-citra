/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gambar;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.ImageObserver;
import java.awt.image.Kernel;
import java.awt.image.RenderedImage;
import java.awt.image.WritableRaster;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

/**
 *
 * @author tami
 */

public class ClassGambarr {
    public ImageIcon SourceIcon; 
    public Image SourceImage;
    public ImageIcon ScaleIcon; 
    public Image ScaleImage;
    public Image ResultImage; 
    public Image ScaleResultImage;
    public ImageIcon ScaleResultIcon;
    public String URLImage;
    public boolean ScaledFlag=false; 
    public BufferedImage SourceBuffer; 
    public BufferedImage ResultBuffer; 
    public long sWidth;
    public long sHeight;
    
    ClassGambarr(String Url, long width, long height){
URLImage=Url;
if(width<=0||height<=0){
ScaledFlag=false;
}
else{
ScaledFlag=true;
sWidth=width;
sHeight=height;
}
}
    public ImageIcon Geticon(){
        if(!URLImage.equals("")){
            SourceIcon = new ImageIcon(URLImage);
            SourceImage = SourceIcon.getImage();
            try{
                SourceBuffer=ImageIO.read(new File(URLImage));
            }
            catch(IOException x)
                {
JOptionPane.showMessageDialog(null, x.getMessage(), "error", JOptionPane.ERROR_MESSAGE);
}
System.out.println(SourceIcon.getIconWidth());
if(ScaledFlag)
{
ScaleImage=SourceImage.getScaledInstance((int)sWidth, (int)sHeight,
Image.SCALE_DEFAULT);
ScaleIcon=new ImageIcon(ScaleImage); return ScaleIcon;
}
else
{
return SourceIcon;
}
}
else
{
return null;
}
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
    
// fungsi Grayscale
public void Grayscale(){ 
    ResultBuffer=deepCopy(SourceBuffer);
    long tWidth=ResultBuffer.getWidth(); 
    long tHeight=ResultBuffer.getHeight();
    long x,y;
    int RGB,Red,Green,Blue,Gray;
    Color tWarna; 
for(x=0;x<tWidth;x++)
{
for(y=0;y<tHeight;y++)
{
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
ScaleResultImage=ResultImage.getScaledInstance((int)sWidth, (int)sHeight,
Image.SCALE_DEFAULT);
ScaleResultIcon=new ImageIcon(ScaleResultImage);
}

// fungsi Biner
public void Biner(){ ResultBuffer=deepCopy(SourceBuffer);
long tWidth=ResultBuffer.getWidth(); long tHeight=ResultBuffer.getHeight();
long x,y;
int RGB,Red,Green,Blue,Gray;
Color tWarna; for(x=0;x<tWidth;x++)
{
for(y=0;y<tHeight;y++)
{
RGB=ResultBuffer.getRGB((int)x, (int)y);
tWarna=new Color(RGB);
Red=tWarna.getRed();
Green=tWarna.getGreen();
Blue=tWarna.getBlue();
Gray=(Red+Green+Blue)/3;
if (Gray<=128)
{
    Gray=0;
}
else
{
Gray=255;
}
tWarna=new Color(Gray,Gray,Gray);
ResultBuffer.setRGB((int)x, (int)y, tWarna.getRGB());
}
}
ResultImage=(Image) ResultBuffer;
ScaleResultImage=ResultImage.getScaledInstance((int)sWidth, (int)sHeight,
Image.SCALE_DEFAULT);
ScaleResultIcon=new ImageIcon(ScaleResultImage);
}

// fungsi Default
public void Default(){ ResultBuffer=deepCopy(SourceBuffer);
long tWidth=ResultBuffer.getWidth(); long tHeight=ResultBuffer.getHeight();
long x,y;
int RGB,Red,Green,Blue;
Color tWarna; for(x=0;x<tWidth;x++)
{
for(y=0;y<tHeight;y++)
{
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
ScaleResultImage=ResultImage.getScaledInstance((int)sWidth, (int)sHeight,
Image.SCALE_DEFAULT);
ScaleResultIcon=new ImageIcon(ScaleResultImage);
}
static BufferedImage deepCopy(BufferedImage bi){ ColorModel cm=bi.getColorModel();
boolean isAlphaPremultiplied = cm.isAlphaPremultiplied(); WritableRaster raster = bi.copyData(null);
return new BufferedImage(cm,raster,isAlphaPremultiplied,null);
}
public void SaveImage(String url)
{
File tFile= new File(url); System.out.println(url); try
{
String fileName = tFile.getCanonicalPath(); if(!fileName.endsWith(".jpeg")){
tFile=new File(fileName+".jpeg");
}
ImageIO.write(ResultBuffer, "jpeg", tFile);
System.out.println("sukses");
}
catch(IOException x){ JOptionPane.showMessageDialog(null,
x.getMessage(),"error",JOptionPane.ERROR_MESSAGE);
}
}

//fungsi brightness
public void brightness(){
    ResultBuffer=deepCopy(SourceBuffer);
    long tWidth=ResultBuffer.getWidth(); 
    long tHeight=ResultBuffer.getHeight();
    long x,y;
    int RGB,Red,Green,Blue,Gray, newRed, newGreen, newBlue;
    Color tWarna;

    for(x=0;x<tWidth;x++){
        for(y=0;y<tHeight;y++){
  RGB = ResultBuffer.getRGB((int)x, (int)y);
    tWarna = new Color(RGB);
    Red = tWarna.getRed();
    Green = tWarna.getGreen();
    Blue = tWarna.getBlue();
    newRed=Red+10;
    if(newRed>255){
        newRed=255;
    }
    newBlue=Blue+10;
    if(newBlue>255){
        newBlue = 255;
    }
    newGreen=Green+10;
    if(newGreen>255){
        newGreen=255;
    }  
}

    }
    
}

public void flipV(){
    ResultBuffer = deepCopy(SourceBuffer);
    BufferedImage imge = new BufferedImage(SourceBuffer.getWidth(), 
            SourceBuffer.getHeight(), BufferedImage.TYPE_INT_RGB);
    for(int y=0; y<SourceBuffer.getHeight();y++){
        for(int x=0; x<SourceBuffer.getWidth(); x++){
            imge.setRGB(x,y, SourceBuffer.getRGB(x, SourceBuffer.getHeight()-1-y));
            
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
    
    private static GraphicsConfiguration getDefaultConfiguration(){
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice gd = ge.getDefaultScreenDevice();
        return gd.getDefaultConfiguration();
    }

public void flipH(){
    ResultBuffer = deepCopy(SourceBuffer);
    BufferedImage imge = new BufferedImage(SourceBuffer.getWidth(), 
            SourceBuffer.getHeight(), BufferedImage.TYPE_INT_RGB);
    for(int y=0; y<SourceBuffer.getHeight();y++){
        for(int x=0; x<SourceBuffer.getWidth(); x++){
            imge.setRGB(x,y, SourceBuffer.getRGB(SourceBuffer.getWidth()-1-x,y));
            
        }
    }
    ResultImage=imge;
    ScaleResultImage=ResultImage.getScaledInstance((int)sWidth, (int)sHeight, 
            Image.SCALE_DEFAULT);
    ScaleResultIcon=new ImageIcon(ScaleResultImage);
}

public void zoomin(){
    ResultBuffer = deepCopy(SourceBuffer);
    ResultImage=(Image) ResultBuffer;
    sWidth = sWidth * 2;
    sHeight = sHeight * 2;
    ScaleResultImage=ResultImage.getScaledInstance((int)sWidth, (int)sHeight,
    Image.SCALE_DEFAULT);
    ScaleResultIcon=new ImageIcon(ScaleResultImage);
}

public void zoomout(){
    ResultBuffer=deepCopy(SourceBuffer);
    ResultImage=(Image) ResultBuffer;
    sWidth = sWidth / 2;
    sHeight = sHeight / 2;
    ScaleResultImage=ResultImage.getScaledInstance((int)sWidth, (int)sHeight,
    Image.SCALE_DEFAULT);
    ScaleResultIcon=new ImageIcon(ScaleResultImage);
    
    
    
}
    //fungsi Sharp
public void Sharpen(){
    ResultBuffer=deepCopy(SourceBuffer);
    //long tWidth=ResultBuffer.getWidth();
    //long tHeight=ResultBuffer.getHeight ();
    double factor = 1.0;
    double bias=0.0;
    float [] identityKernel = {
        0, -1, 0,
        -1, 5, -1,
        0, -1, 0};
    
    BufferedImageOp op=new ConvolveOp(new Kernel (3,3,identityKernel));
    BufferedImage filteredImage=new BufferedImage(SourceBuffer.getWidth(),SourceBuffer.getHeight(),
            SourceBuffer.getType());
    op.filter(SourceBuffer, filteredImage);
    
    ResultImage=filteredImage;
    ScaleResultImage.getScaledInstance((int)sWidth,(int)sHeight,Image.SCALE_DEFAULT);
    ScaleResultIcon=new ImageIcon(ScaleResultImage);
}
public void UnSharp (){
    ResultBuffer=deepCopy(SourceBuffer);
    //long tWidth=ResultBuffer.getWidth();
    //long tHeight=ResultBuffer.getHeight ();
    double factor = 1.0;
    double bias=0.0;
    float [] identityKernel = {
        -1, -1, -1,
        -1, 8, -1,
        -1, -1, -1};
    
    BufferedImageOp op=new ConvolveOp(new Kernel (3,3,identityKernel));
    BufferedImage filteredImage=new BufferedImage(SourceBuffer.getWidth(),SourceBuffer.getHeight(),
            SourceBuffer.getType());
    op.filter(SourceBuffer, filteredImage);
    
    ResultImage=filteredImage;
    ScaleResultImage.getScaledInstance((int)sWidth,(int)sHeight,Image.SCALE_DEFAULT);
    ScaleResultIcon=new ImageIcon(ScaleResultImage);
}
public void Blur(){
    ResultBuffer=deepCopy(SourceBuffer);
    //long tWidth=ResultBuffer.getWidth();
    //long tHeight=ResultBuffer.getHeight ();
    double factor = 1.0;
    double bias=0.0;
    float [] identityKernel = {
        1/9f, 1/9f, 1/9f,
        1/9f, 1/9f, 1/9f,
        1/9f, 1/9f, 1/9f,
    };
    BufferedImageOp op=new ConvolveOp(new Kernel (3,3,identityKernel));
    BufferedImage filteredImage=new BufferedImage(SourceBuffer.getWidth(),SourceBuffer.getHeight(),
            SourceBuffer.getType());
    op.filter(SourceBuffer, filteredImage);
    
    ResultImage=filteredImage;
    ScaleResultImage.getScaledInstance((int)sWidth,(int)sHeight,Image.SCALE_DEFAULT);
    ScaleResultIcon=new ImageIcon(ScaleResultImage);
}
public void Hightpass(){
    ResultBuffer=deepCopy(SourceBuffer);
    double factor = 1.0;
    double bias=0.0;
    float [] identityKernel = {
        -1/9, -1/9, -1/9,
        -1/9, 8/9f, -1/9,
        -1/9, -1/9, -1/9,
    };
    BufferedImageOp op=new ConvolveOp(new Kernel (3,3,identityKernel));
    BufferedImage filteredImage=new BufferedImage(SourceBuffer.getWidth(),SourceBuffer.getHeight(),
            SourceBuffer.getType());
    op.filter(SourceBuffer, filteredImage);
    
    ResultImage=filteredImage;
    ScaleResultImage.getScaledInstance((int)sWidth,(int)sHeight,Image.SCALE_DEFAULT);
    ScaleResultIcon=new ImageIcon(ScaleResultImage);
    
}
public void Gaussian(){
    ResultBuffer=deepCopy(SourceBuffer);
    double factor = 1.0;
    double bias=0.0;
    float [] identityKernel = {
        1/16f, 1/8f, 1/16f,
        1/8f, 1/4f, 1/8f,
        1/16f, 1/8f, 1/16f,
    };
    BufferedImageOp op=new ConvolveOp(new Kernel (3,3,identityKernel));
    BufferedImage filteredImage=new BufferedImage(SourceBuffer.getWidth(),SourceBuffer.getHeight(),
            SourceBuffer.getType());
    op.filter(SourceBuffer, filteredImage);
    
    ResultImage=filteredImage;
    ScaleResultImage.getScaledInstance((int)sWidth,(int)sHeight,Image.SCALE_DEFAULT);
    ScaleResultIcon=new ImageIcon(ScaleResultImage);
}
public void Emboss(){
    ResultBuffer=deepCopy(SourceBuffer);
    double factor = 1.0;
    double bias=0.0;
    float [] identityKernel = {
        -2, -1, 0,
        -1, 1, 1,
        0, 1, 2
    };
    BufferedImageOp op=new ConvolveOp(new Kernel (3,3,identityKernel));
    BufferedImage filteredImage=new BufferedImage(SourceBuffer.getWidth(),SourceBuffer.getHeight(),
            SourceBuffer.getType());
    op.filter(SourceBuffer, filteredImage);
    
    ResultImage=filteredImage;
    ScaleResultImage.getScaledInstance((int)sWidth,(int)sHeight,Image.SCALE_DEFAULT);
    ScaleResultIcon=new ImageIcon(ScaleResultImage);
}






}





