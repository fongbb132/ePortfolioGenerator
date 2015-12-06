package EPG.manager;


import java.io.IOException;
import EPG.manager.EPortfolioFileManager;
import EPG.model.Page;
import EPG.model.ImageComponent;
import EPG.model.SlideShowComponent;
import EPG.model.VideoComponent;
import EPG.model.EPortfolio;
import EPG.model.hyperTextComponent;
import EPG.model.ParagraphComponent;

public class JsonTester {
    static EPortfolio eportfolio;
    static String TEST_TITLE = "Ka Wing Fong";
    public static void main(String[] args) {
	eportfolio = new EPortfolio(TEST_TITLE,null);
        
        VideoComponent vCom = new VideoComponent("http://clips.vorwaerts-gmbh.de/VfE_html5.mp4","video");
        ImageComponent pCom = new ImageComponent("http://www3.cs.stonybrook.edu/~richard/images/personal_pics/MeAndTheKid.jpg","video");
        ParagraphComponent tCom = new ParagraphComponent("The weather today is very nice. It's not a good day to do programming");
        hyperTextComponent hCom = new hyperTextComponent("There is the link showing you the weather today.", "http://www.weather.com/");
        //SlideShowComponent sCom = new SlideShowComponent("slideShow");
        
	Page page = new Page("Site 1");
        page.addComponent(vCom);
        page.addComponent(pCom);
        page.addComponent(tCom);
        page.addComponent(hCom);
        
        Page page1 = new Page("Site 2");
        page1.addComponent(vCom);
        page1.addComponent(pCom);
        page1.addComponent(tCom);
        page1.addComponent(hCom);
	
        Page page2 = new Page("Site 3");
        page2.addComponent(vCom);
        page2.addComponent(pCom);
        page2.addComponent(tCom);
        page2.addComponent(hCom);
//        page2.addComponent(sCom);
        
        	
        Page page3 = new Page("Site 4");
        page3.addComponent(vCom);
        page3.addComponent(pCom);
        page3.addComponent(tCom);
        page3.addComponent(hCom);
//        page3.addComponent(sCom);
        
        	
        Page page4 = new Page("Site 5");
        page4.addComponent(vCom);
        page4.addComponent(pCom);
        page4.addComponent(tCom);
        page4.addComponent(hCom);
        //page4.addComponent(sCom);
        
        eportfolio.addPages(page);
        eportfolio.addPages(page1);
        eportfolio.addPages(page2);
        eportfolio.addPages(page3);
        eportfolio.addPages(page4);
        EPortfolioFileManager fileManager = new EPortfolioFileManager();
	try {
	    fileManager.saveEPortfolio(eportfolio);
	    System.out.println("SLIDE SHOW SAVED");
	}
	catch(IOException ioe) {
	    ioe.printStackTrace();
	    System.exit(-1);
	}
        
//	SlideShowModel newSlideShow = new SlideShowModel(null);
//	try {
//	    fileManager.loadSlideShow(slideShow, PATH_SLIDE_SHOWS + SLASH + TEST_TITLE + JSON_EXT);
//	    System.out.println("SLIDE SHOW LOADED");
//	}
//	catch(IOException ioe) {
//	    ioe.printStackTrace();
//	    System.exit(-1);
//	}
    }
}
