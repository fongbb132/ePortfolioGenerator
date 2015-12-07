/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EPG.manager;

import static EPG.StartupConstants.PATH_EPORTFOLIO;
import static EPG.StartupConstants.PATH_SAMPLE;
import static EPG.StartupConstants.PATH_SITE;
import EPG.model.Component;
import EPG.model.EPortfolio;
import EPG.model.ImageComponent;
import EPG.model.Page;
import EPG.model.Slide;
import EPG.model.SlideShowComponent;
import EPG.model.VideoComponent;
import EPG.view.EPortfolioView;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import org.apache.commons.io.FileUtils;

/**
 *
 * @author Ka Wing Fong
 */
public class createNameSiteController {
    EPortfolioView UI;
    public createNameSiteController(EPortfolioView initUI) {
        UI = initUI;
    }
    
    public void copySampleSite() throws IOException{
        String title = UI.getEPortfolio().getName();
        
        EPortfolio portfolio = UI.getEPortfolio();
        String toPath = PATH_SITE+portfolio.getName()+"/";
        File toFile = new File(toPath);
        if(toFile.exists()){
            FileUtils.deleteDirectory(toFile);
        }
        File jsonFile = new File(PATH_EPORTFOLIO+title+".json");
        FileUtils.copyFileToDirectory(jsonFile, toFile);
        File newJsonFile = new File(toPath+title+".json");
        newJsonFile.renameTo(new File(toPath+"data.json"));
        
        for(Page p:portfolio.getPages()){
            File fromFile = new File(PATH_SAMPLE+"sample.html");
            FileUtils.copyFileToDirectory(fromFile, toFile);
            File htmlFile = new File(toPath+"sample.html");
            File temp1 = new File(toPath+p.getTitle()+".html");
            replaceWord(htmlFile.toString(),temp1.toString(),p.getTitle());
            
            File newCSSToPath = new File(toPath+p.getTitle()+"/"+"css/");
            File newJSToPath = new File(toPath+p.getTitle()+"/"+"js/");
            fromFile = new File(PATH_SAMPLE+"app.js");
            FileUtils.copyFileToDirectory(fromFile, newJSToPath);
            replaceJavaScriptWord(fromFile.toString(),newJSToPath+"/app.js",p.getPosition());
            
            File temp2;
            if(p.getColor().equals("Color 1")){
                fromFile = new File(PATH_SAMPLE+"CSS/color1.css");
                FileUtils.copyFileToDirectory(fromFile,newCSSToPath);
                temp1 = new File(newCSSToPath.toString()+"/color.css");
                System.out.println(temp1.toString());
                temp2 = new File(newCSSToPath.toString()+"/color1.css");
                temp2.renameTo(temp1);
            }else if(p.getColor().equals("Color 2")){
                fromFile = new File(PATH_SAMPLE+"CSS/color2.css");
                FileUtils.copyFileToDirectory(fromFile,newCSSToPath);
                temp1 = new File(newCSSToPath.toString()+"/color.css");
                temp2 = new File(newCSSToPath.toString()+"/color2.css");
                temp2.renameTo(temp1);
            }else if(p.getColor().equals("Color 3")){
                fromFile = new File(PATH_SAMPLE+"CSS/color3.css");
                FileUtils.copyFileToDirectory(fromFile,newCSSToPath);
                temp1 = new File(newCSSToPath.toString()+"/color.css");
                temp2 = new File(newCSSToPath.toString()+"/color3.css");
                temp2.renameTo(temp1);
            }else if(p.getColor().equals("Color 4")){
                fromFile = new File(PATH_SAMPLE+"CSS/color4.css");
                FileUtils.copyFileToDirectory(fromFile,newCSSToPath);
                temp1 = new File(newCSSToPath.toString()+"/color.css");
                temp2 = new File(newCSSToPath.toString()+"/color4.css");
                temp2.renameTo(temp1);
            }else if(p.getColor().equals("Color 5")){
                fromFile = new File(PATH_SAMPLE+"CSS/color5.css");
                FileUtils.copyFileToDirectory(fromFile,newCSSToPath);
                temp1 = new File(newCSSToPath.toString()+"/color.css");
                temp2 = new File(newCSSToPath.toString()+"/color5.css");
                temp2.renameTo(temp1);
            }
            
            
            if(p.getLayout().equals("Layout 1")){
                fromFile = new File(PATH_SAMPLE+"CSS/style1.css");
                FileUtils.copyFileToDirectory(fromFile,newCSSToPath);
                temp1 = new File(newCSSToPath.toString()+"/style.css");
                System.out.println(temp1.toString());
                temp2 = new File(newCSSToPath.toString()+"/style1.css");
                temp2.renameTo(temp1);
            }else if(p.getLayout().equals("Layout 2")){
                fromFile = new File(PATH_SAMPLE+"CSS/style2.css");
                FileUtils.copyFileToDirectory(fromFile,newCSSToPath);
                temp1 = new File(newCSSToPath.toString()+"/style.css");
                temp2 = new File(newCSSToPath.toString()+"/style2.css");
                temp2.renameTo(temp1);
            }else if(p.getLayout().equals("Layout 3")){
                fromFile = new File(PATH_SAMPLE+"CSS/style3.css");
                FileUtils.copyFileToDirectory(fromFile,newCSSToPath);
                temp1 = new File(newCSSToPath.toString()+"/style.css");
                temp2 = new File(newCSSToPath.toString()+"/style3.css");
                temp2.renameTo(temp1);
            }else if(p.getLayout().equals("Layout 4")){
                fromFile = new File(PATH_SAMPLE+"CSS/style4.css");
                FileUtils.copyFileToDirectory(fromFile,newCSSToPath);
                temp1 = new File(newCSSToPath.toString()+"/style.css");
                temp2 = new File(newCSSToPath.toString()+"/style4.css");
                temp2.renameTo(temp1);
            }else if(p.getLayout().equals("Layout 5")){
                fromFile = new File(PATH_SAMPLE+"CSS/style5.css");
                FileUtils.copyFileToDirectory(fromFile,newCSSToPath);
                temp1 = new File(newCSSToPath.toString()+"/style.css");
                temp2 = new File(newCSSToPath.toString()+"/style5.css");
                temp2.renameTo(temp1);
            }
            
             if(p.getFont().equals("Font 1")){
                fromFile = new File(PATH_SAMPLE+"CSS/font1.css");
                FileUtils.copyFileToDirectory(fromFile,newCSSToPath);
                temp1 = new File(newCSSToPath.toString()+"/font.css");
                System.out.println(temp1.toString());
                temp2 = new File(newCSSToPath.toString()+"/font1.css");
                temp2.renameTo(temp1);
            }else if(p.getFont().equals("Font 2")){
                fromFile = new File(PATH_SAMPLE+"CSS/font2.css");
                FileUtils.copyFileToDirectory(fromFile,newCSSToPath);
                temp1 = new File(newCSSToPath.toString()+"/font.css");
                temp2 = new File(newCSSToPath.toString()+"/font2.css");
                temp2.renameTo(temp1);
            }else if(p.getFont().equals("Font 3")){
                fromFile = new File(PATH_SAMPLE+"CSS/font3.css");
                FileUtils.copyFileToDirectory(fromFile,newCSSToPath);
                temp1 = new File(newCSSToPath.toString()+"/font.css");
                temp2 = new File(newCSSToPath.toString()+"/font3.css");
                temp2.renameTo(temp1);
            }else if(p.getFont().equals("Font 4")){
                fromFile = new File(PATH_SAMPLE+"CSS/font4.css");
                FileUtils.copyFileToDirectory(fromFile,newCSSToPath);
                temp1 = new File(newCSSToPath.toString()+"/font.css");
                temp2 = new File(newCSSToPath.toString()+"/font4.css");
                temp2.renameTo(temp1);
            }else if(p.getFont().equals("Font 5")){
                fromFile = new File(PATH_SAMPLE+"CSS/font5.css");
                FileUtils.copyFileToDirectory(fromFile,newCSSToPath);
                temp1 = new File(newCSSToPath.toString()+"/font.css");
                temp2 = new File(newCSSToPath.toString()+"/font5.css");
                temp2.renameTo(temp1);
            }
            
            if(!p.getBannerUrl().equals("")){
                File picture1 = new File(p.getBannerUrl()+p.getBanner());
                File imgFile1 = new File(toPath + "img/");
                FileUtils.copyFileToDirectory(picture1, imgFile1);
            }
            for(Component a: p.getComponents()){
                if(a instanceof ImageComponent){
                    ImageComponent temp = (ImageComponent)a;
                    File picture = new File(temp.getSrc());
                    File imgFile = new File(toPath + "img/");
                    FileUtils.copyFileToDirectory(picture, imgFile);
                }if(a instanceof VideoComponent){
                    VideoComponent temp = (VideoComponent)a;
                    File picture = new File(temp.getSrc()+temp.getName());
                    File imgFile = new File(toPath + "video/");
                    FileUtils.copyFileToDirectory(picture, imgFile);
                }if(a instanceof SlideShowComponent){
                    SlideShowComponent temp = (SlideShowComponent)a;
                    for(Slide slide: temp.getSlides()){
                        File picture = new File(slide.getImagePath()+slide.getImageFileName());
                        File imgFile = new File(toPath + "img/");
                        FileUtils.copyFileToDirectory(picture, imgFile);
                    }
                }
                
                
            }
        }
    }
    
    
    private void replaceWord(String fromPath,String toPath,String pageName){
        try{
            File file = new File(toPath);
            file.createNewFile();
            File inFile = new File(fromPath);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.flush();
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String line,putData;
            line = br.readLine();
            while(line!=null){
                putData = line.replace("*****", pageName);
                putData = putData.replace("js/app.js", pageName+"/js/app.js")+"\n";
                
                bw.write(putData);
                line = br.readLine();
            }
            bw.close();
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    
    private void replaceJavaScriptWord(String fromPath,String toPath,int pos){
        try{
            File file = new File(toPath);
            file.createNewFile();
            File inFile = new File(fromPath);
            FileWriter fw = new FileWriter(file);
            BufferedWriter bw = new BufferedWriter(fw);
            bw.flush();
            FileReader fr = new FileReader(inFile);
            BufferedReader br = new BufferedReader(fr);
            String line,putData;
            line = br.readLine();
            while(line!=null){
                putData = line.replace("******", pos+"")+"\n";
                
                bw.write(putData);
                line = br.readLine();
            }
            bw.close();
            br.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
