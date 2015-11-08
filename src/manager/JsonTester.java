
import java.io.IOException;
import manager.ePortfolioFileManager;
import model.Page;
import model.PictureComponent;
import model.SlideShowComponent;
import model.VideoComponent;
import model.ePortfolio;
import model.hyperTextComponent;
import model.textComponent;

public class JsonTester {
    static ePortfolio eportfolio;
    static String TEST_TITLE = "test1";
    public static void main(String[] args) {
	eportfolio = new ePortfolio(TEST_TITLE);
        
	// ADD THREE SLIDES
        
        VideoComponent vCom = new VideoComponent("video", "http://clips.vorwaerts-gmbh.de/VfE_html5.mp4");
        PictureComponent pCom = new PictureComponent("picture", "http://www3.cs.stonybrook.edu/~richard/images/personal_pics/MeAndTheKid.jpg");
        textComponent tCom = new textComponent("kldfsjaslnvlmbnalsmnldskfadslkf");
        hyperTextComponent hCom = new hyperTextComponent("fasdf", "yahoo.com");
        SlideShowComponent sCom = new SlideShowComponent("slideShow");
        
	sCom.addSlide("Hello","HelloPic.png","./images/");
	sCom.addSlide("Clever Comment","DopeyPic.jpg","./images/pics/");
	sCom.addSlide("Goodbye","FinalSlide.gif","./images/");
	Page page = new Page("pageName");
        page.addComponent(vCom);
        page.addComponent(pCom);
        page.addComponent(tCom);
        page.addComponent(hCom);
        page.addComponent(sCom);
        
        Page page1 = new Page("pageName1");
        page1.addComponent(vCom);
        page1.addComponent(pCom);
        page1.addComponent(tCom);
        page1.addComponent(hCom);
        page1.addComponent(sCom);
	
        Page page2 = new Page("pageName1");
        page2.addComponent(vCom);
        page2.addComponent(pCom);
        page2.addComponent(tCom);
        page2.addComponent(hCom);
        page2.addComponent(sCom);
        
        eportfolio.addPages(page);
        eportfolio.addPages(page1);
        eportfolio.addPages(page2);
        eportfolio.setBanner("png");
        eportfolio.setColor("color");
        eportfolio.setFooter("footer");
        eportfolio.setLayout("template 1");
        ePortfolioFileManager fileManager = new ePortfolioFileManager();
	try {
	    fileManager.saveEPortfolio(eportfolio);
	    System.out.println("SLIDE SHOW SAVED");
	}
	catch(IOException ioe) {
	    ioe.printStackTrace();
	    System.exit(-1);
	}
//
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
