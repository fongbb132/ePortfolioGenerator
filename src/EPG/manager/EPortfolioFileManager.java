package EPG.manager;

import static EPG.StartupConstants.PATH_SLIDE_SHOWS;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import EPG.model.Component;
import EPG.model.Page;
import EPG.model.ImageComponent;
import EPG.model.Slide;
import EPG.model.SlideShowComponent;
import EPG.model.VideoComponent;
import EPG.model.EPortfolio;
import EPG.model.HeadingComponent;
import EPG.model.ListComponent;
import EPG.model.hyperTextComponent;
import EPG.model.ParagraphComponent;
import EPG.model.SlideShowModel;

/**
 * This class uses the JSON standard to read and write slideshow data files.
 *
 * @author McKilla Gorilla & _____________
 */
public class EPortfolioFileManager {

    // JSON FILE READING AND WRITING CONSTANTS

    public static String JSON_TITLE = "title";
    public static String JSON_SLIDES = "slides";
    public static String JSON_IMAGE_FILE_NAME = "image_file_name";
    public static String JSON_IMAGE_PATH = "image_path";
    public static String JSON_CAPTION = "caption";
    public static String JSON_BANNER = "banner";
    public static String JSON_FOOTER = "footer";
    public static String JSON_COLOR = "color";
    public static String JSON_LAYOUT = "layout";
    public static String JSON_PAGES = "pages";
    public static String JSON_EXT = ".json";
    public static String JSON_COMPONENT_NAME = "component_name";
    public static String JSON_COMPONENT_CONTENT = "componet_content";
    public static String JSON_COMPONENT_SRC = "component_src";
    public static String JSON_COMPONENT_TYPE = "component_type";
    public static String SLASH = "/";
    public static String JSON_SLIDESHOW = "slideshow";
    public static String JSON_PAGE_TITLE = "page_title";
    public static String JSON_COMPONENT_ARRAY= "component_array";
    public static String JSON_LIST_CONTENT = "list_content";
    public static String JSON_LIST_ITEM = "list_item";
    public static String JSON_LIST_COMPONENT="List_component";
    public static String JSON_IMAGE_CAPTION="image_caption";
    public static String JSON_IMAGE_ALIGNMENT="image_alignment";
    public static String JSON_IMAGE_WIDTH="image_width";
    public static String JSON_IMAGE_HEIGHT="image_height";
    public static String JSON_IMAGE_NAME="image_name";
    public static String JSON_VIDEO_NAME = "video_name";
    public static String JSON_VIDEO_CAPTION = "video_caption";
    public static String JSON_VIDEO_ALIGNMENT = "video_alignment";
    public static String JSON_VIDEO_WIDTH = "video_width";
    public static String JSON_VIDEO_HEIGHT = "video_height";
    public static String JSON_PARAGRAPH_FONT= "paragraph_font";
            
            
  
    /**
     * This method saves all the data associated with a slide show to a JSON
     * file.
     *
     * @param slideShowToSave The course whose data we are saving.
     *
     * @throws IOException Thrown when there are issues writing to the JSON
     * file.
     */
    public void saveEPortfolio(EPortfolio ePortfolioToSave) throws IOException {
	StringWriter sw = new StringWriter();

	// BUILD THE SLIDES ARRAY
	JsonArray pagesJsonArray = makePageJsonArray(ePortfolioToSave.getPages());
        
	// NOW BUILD THE COURSE USING EVERYTHING WE'VE ALREADY MADE
	JsonObject ePortfolioJsonObject = Json.createObjectBuilder()
		.add(JSON_TITLE, ePortfolioToSave.getName())
                .add(JSON_PAGES, pagesJsonArray)
		.build();

	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);

	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(ePortfolioJsonObject);
	jsonWriter.close();

	// INIT THE WRITER
	String slideShowTitle = "" + ePortfolioToSave.getName();
        
	String jsonFilePath = "./data/EPortfolio"+ SLASH + slideShowTitle + JSON_EXT;
	OutputStream os = new FileOutputStream(jsonFilePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(ePortfolioJsonObject);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(jsonFilePath);
	pw.write(prettyPrinted);
	pw.close();
	System.out.println(prettyPrinted);
    }

    /**
     * This method loads the contents of a JSON file representing a slide show
     * into a SlideSShowModel objecct.
     *
     * @param portfolioToLoad The slide show to load
     * @param jsonFilePath The JSON file to load.
     * @throws IOException
     */
    public void loadSlideShow(SlideShowModel portfolioToLoad, String jsonFilePath) throws IOException {
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(jsonFilePath);

	// NOW LOAD THE COURSE
	portfolioToLoad.reset();
	portfolioToLoad.setTitle(json.getString(JSON_TITLE));
	JsonArray jsonSlidesArray = json.getJsonArray(JSON_SLIDES);
	for (int i = 0; i < jsonSlidesArray.size(); i++) {
	    JsonObject slideJso = jsonSlidesArray.getJsonObject(i);
	    portfolioToLoad.addSlide(slideJso.getString(JSON_IMAGE_FILE_NAME),
		    slideJso.getString(JSON_IMAGE_PATH),
		    slideJso.getString(JSON_CAPTION));
	}
    }

    // AND HERE ARE THE PRIVATE HELPER METHODS TO HELP THE PUBLIC ONES
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }

    private ArrayList<String> loadArrayFromJSONFile(String jsonFilePath, String arrayName) throws IOException {
	JsonObject json = loadJSONFile(jsonFilePath);
	ArrayList<String> items = new ArrayList();
	JsonArray jsonArray = json.getJsonArray(arrayName);
	for (JsonValue jsV : jsonArray) {
	    items.add(jsV.toString());
	}
	return items;
    }

    private JsonArray makeComponentJsonArray(List<Component> components) {
	JsonArrayBuilder jsb = Json.createArrayBuilder();
	for (Component c:components) {
	    JsonObject jso = makeComponentJsonObject(c);
	    jsb.add(jso);
	}
	JsonArray jA = jsb.build();
	return jA;
    }

    private JsonArray makePageJsonArray(List<Page> pages){
	JsonArrayBuilder jsb = Json.createArrayBuilder();
	for (Page p:pages) {
	    JsonObject jso = makePageJsonObject(p);
	    jsb.add(jso);
	}
	JsonArray jA = jsb.build();
	return jA;
        
    }
    private JsonObject makePageJsonObject(Page page) {
        JsonArray jA = makeComponentJsonArray(page.getComponents());
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_PAGE_TITLE, page.getTitle())
		.add(JSON_BANNER, page.getBanner())
                .add(JSON_FOOTER, page.getFooter())
                .add(JSON_COLOR, page.getColor())
                .add(JSON_LAYOUT, page.getLayout())
                .add(JSON_COMPONENT_ARRAY, jA)
                .build();
        return jso;
        
    }
    
    
    private JsonArray makeSlidesJsonArray(List<Slide> slides) {
	JsonArrayBuilder jsb = Json.createArrayBuilder();
	for (Slide slide: slides) {
	    JsonObject jso = makeSlideJsonObject(slide);
	    jsb.add(jso);
	}
	JsonArray jA = jsb.build();
	return jA;
    }
    
    private JsonArray makeListArray(List<String> list){
	JsonArrayBuilder jsb = Json.createArrayBuilder();
	for (String a:list) {
	    JsonObject jso = makeListJsonObject(a);
	    jsb.add(jso);
	}
	JsonArray jA = jsb.build();
	return jA;
    }
    
    private JsonObject makeSlideJsonObject(Slide s){
	JsonObject jso = Json.createObjectBuilder()
		.add(JSON_IMAGE_FILE_NAME, s.getImageFileName())
		.add(JSON_IMAGE_PATH, s.getImagePath())
		.add(JSON_CAPTION, s.getCaption())
		.build();
	return jso;
    }
    
    private JsonObject makeListJsonObject(String a) {
        JsonObject jso = Json.createObjectBuilder()
                .add(JSON_LIST_ITEM, a)
                .build();
        return jso;
    }
    

    private JsonObject makeComponentJsonObject(Component component){
        JsonObject jso;
        if(component instanceof ImageComponent){
            jso = Json.createObjectBuilder()
                    .add(JSON_COMPONENT_CONTENT, ((ImageComponent)component).getContent())
                    .add(JSON_COMPONENT_TYPE,((ImageComponent)component).getType())
                    .add(JSON_COMPONENT_SRC, ((ImageComponent)component).getSrc())
                    .add(JSON_IMAGE_NAME, ((ImageComponent)component).getName())
                    .add(JSON_IMAGE_CAPTION, ((ImageComponent)component).getCaption())
                    .add(JSON_IMAGE_ALIGNMENT, ((ImageComponent)component).getAlignment())
                    .add(JSON_IMAGE_WIDTH, ((ImageComponent)component).getWidth())
                    .add(JSON_IMAGE_HEIGHT, ((ImageComponent)component).getHeight())
                    .build();
        }else if(component instanceof ListComponent){
            jso = Json.createObjectBuilder()
                    .add(JSON_LIST_CONTENT,((ListComponent)component).getContent())
                    .add(JSON_COMPONENT_TYPE, ((ListComponent)component).getType())
                    .add(JSON_LIST_COMPONENT,this.makeListArray(((ListComponent)component).getList()))
                    .build();
                    
        }
        else if(component instanceof VideoComponent){
            jso = Json.createObjectBuilder()
                    .add(JSON_COMPONENT_CONTENT, ((VideoComponent)component).getContent())
                    .add(JSON_COMPONENT_TYPE,((VideoComponent)component).getType())
                    .add(JSON_COMPONENT_SRC, ((VideoComponent)component).getSrc())
                    .add(JSON_VIDEO_NAME, ((VideoComponent)component).getName())
                    .add(JSON_VIDEO_CAPTION, ((VideoComponent)component).getCaption())
                    .add(JSON_VIDEO_ALIGNMENT, ((VideoComponent)component).getAlignment())
                    .add(JSON_VIDEO_WIDTH, ((VideoComponent)component).getWidth())
                    .add(JSON_VIDEO_HEIGHT, ((VideoComponent)component).getHeight())
                    .build();
        }
        else if(component instanceof hyperTextComponent){
            jso = Json.createObjectBuilder()
                    .add(JSON_COMPONENT_CONTENT, ((hyperTextComponent)component).getContent())
                    .add(JSON_COMPONENT_TYPE,((hyperTextComponent)component).getType())
                    .add(JSON_COMPONENT_SRC, ((hyperTextComponent)component).getLink())
                    .build();
        }
        else if(component instanceof ParagraphComponent){
            jso = Json.createObjectBuilder()
                    .add(JSON_COMPONENT_CONTENT, ((ParagraphComponent)component).getContent())
                    .add(JSON_COMPONENT_TYPE,((ParagraphComponent)component).getType())
                    .add(JSON_PARAGRAPH_FONT,((ParagraphComponent)component).getFont())
                    .build();
        }
        else if(component instanceof SlideShowComponent){
            jso = Json.createObjectBuilder()
                    .add(JSON_COMPONENT_CONTENT, ((SlideShowComponent)component).getTitle())
                    .add(JSON_COMPONENT_TYPE,((SlideShowComponent)component).getType())
                    .add(JSON_SLIDESHOW, this.makeSlidesJsonArray(((SlideShowComponent)component).getSlides()))
                    .build();
        }
        else{
            jso = Json.createObjectBuilder()
                    .add(JSON_COMPONENT_CONTENT, (component).getContent())
                    .add(JSON_COMPONENT_TYPE, (component).getType())
                    .build();
        }
        return jso;
    }

    public void saveSlideShow(EPortfolio slideShow) {
        
    }

    
    public void loadPortfolio(EPortfolio portfolioToLoad, File absoluteFile) throws IOException {
        JsonObject json = loadJSONFile(absoluteFile);

	// NOW LOAD THE COURSE
	portfolioToLoad.reset();
	portfolioToLoad.setName(json.getString(JSON_TITLE));
	JsonArray jsonPagesArray = json.getJsonArray(JSON_PAGES);
	for (int i = 0; i < jsonPagesArray.size(); i++) {
	    JsonObject pageJso = jsonPagesArray.getJsonObject(i);
            Page temp = new Page(pageJso.getString(JSON_PAGE_TITLE));
            temp.setColor(pageJso.getString(JSON_COLOR));
            temp.setLayout(pageJso.getString(JSON_LAYOUT));
            temp.setBanner(pageJso.getString(JSON_BANNER));
            temp.setFooter(pageJso.getString(JSON_FOOTER));
            JsonArray componentArray = pageJso.getJsonArray(JSON_COMPONENT_ARRAY);
            for(int a = 0; a< componentArray.size();a++){
                JsonObject componentJso = componentArray.getJsonObject(a);
                if(componentJso.getString(JSON_COMPONENT_TYPE).equals("h")){
                    HeadingComponent h = new HeadingComponent(componentJso.getString(JSON_COMPONENT_CONTENT));
                    temp.addComponent(h);
                }
                else if(componentJso.getString(JSON_COMPONENT_TYPE).equals("l")){
                    ListComponent l = new ListComponent("");
                    JsonArray listArray = componentJso.getJsonArray(JSON_LIST_COMPONENT);
                    for(int b = 0; b<listArray.size();b++){
                        JsonObject listJso = listArray.getJsonObject(b);
                        l.addList(listJso.getString(JSON_LIST_ITEM));
                    }
                    temp.addComponent(l);
                }
                else if(componentJso.getString(JSON_COMPONENT_TYPE).equals("p")){
                    ImageComponent image = new ImageComponent("");
                    image.setAlignment(componentJso.getString(JSON_IMAGE_ALIGNMENT));
                    image.setCaption(componentJso.getString(JSON_IMAGE_CAPTION));
                    int height = componentJso.getInt(JSON_IMAGE_HEIGHT);
                    int width = componentJso.getInt(JSON_IMAGE_WIDTH);
                    System.out.println(height + " "+ width);
                    image.setHeight(height);
                    image.setWidth(width);
                    image.setSrc(componentJso.getString(JSON_COMPONENT_SRC));
                    temp.addComponent(image);
                }
                else if(componentJso.getString(JSON_COMPONENT_TYPE).equals("v")){
                    VideoComponent video = new VideoComponent("");
                    video.setAlignment(componentJso.getString(JSON_VIDEO_ALIGNMENT));
                    video.setCaption(componentJso.getString(JSON_VIDEO_CAPTION));
                    video.setHeight(componentJso.getInt(JSON_VIDEO_HEIGHT));
                    video.setWidth(componentJso.getInt(JSON_VIDEO_WIDTH));
                    video.setSrc(componentJso.getString(JSON_COMPONENT_SRC));
                    video.setName(componentJso.getString(JSON_VIDEO_NAME));
                    temp.addComponent(video);
                }
                else if(componentJso.getString(JSON_COMPONENT_TYPE).equals("t")){
                    ParagraphComponent p = new ParagraphComponent("");
                    String font = componentJso.getString("paragraph_font");
                    System.out.println(font);
                    p.setFont(componentJso.getString("paragraph_font"));
                    p.setContent(componentJso.getString(JSON_COMPONENT_CONTENT));
                    temp.addComponent(p);
                }
            }
            portfolioToLoad.addPages(temp);
	}
    }

    private JsonObject loadJSONFile(File absoluteFile) throws IOException{
       	InputStream is = new FileInputStream(absoluteFile);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }

}
