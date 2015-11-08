package manager;

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
import model.Component;
import model.Page;
import model.PictureComponent;
import model.Slide;
import model.SlideShowComponent;
import model.VideoComponent;
import model.ePortfolio;
import model.hyperTextComponent;
import model.textComponent;

/**
 * This class uses the JSON standard to read and write slideshow data files.
 *
 * @author McKilla Gorilla & _____________
 */
public class ePortfolioFileManager {

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

    /**
     * This method saves all the data associated with a slide show to a JSON
     * file.
     *
     * @param slideShowToSave The course whose data we are saving.
     *
     * @throws IOException Thrown when there are issues writing to the JSON
     * file.
     */
    public void saveEPortfolio(ePortfolio ePortfolioToSave) throws IOException {
	StringWriter sw = new StringWriter();

	// BUILD THE SLIDES ARRAY
	JsonArray pagesJsonArray = makePageJsonArray(ePortfolioToSave.getPages());

	// NOW BUILD THE COURSE USING EVERYTHING WE'VE ALREADY MADE
	JsonObject ePortfolioJsonObject = Json.createObjectBuilder()
		.add(JSON_TITLE, ePortfolioToSave.getName())
		.add(JSON_BANNER, ePortfolioToSave.getBanner())
                .add(JSON_FOOTER, ePortfolioToSave.getFooter())
                .add(JSON_COLOR, ePortfolioToSave.getColor())
                .add(JSON_LAYOUT, ePortfolioToSave.getLayout())
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
	String jsonFilePath = "/Users/wing/Desktop" + SLASH + slideShowTitle + JSON_EXT;
	OutputStream os = new FileOutputStream(jsonFilePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(ePortfolioJsonObject);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(jsonFilePath);
	pw.write(prettyPrinted);
	pw.close();
	System.out.println(prettyPrinted);
    }

//    /**
//     * This method loads the contents of a JSON file representing a slide show
//     * into a SlideSShowModel objecct.
//     *
//     * @param slideShowToLoad The slide show to load
//     * @param jsonFilePath The JSON file to load.
//     * @throws IOException
//     */
//    public void loadSlideShow(SlideShowModel slideShowToLoad, String jsonFilePath) throws IOException {
//	// LOAD THE JSON FILE WITH ALL THE DATA
//	JsonObject json = loadJSONFile(jsonFilePath);
//
//	// NOW LOAD THE COURSE
//	slideShowToLoad.reset();
//	slideShowToLoad.setTitle(json.getString(JSON_TITLE));
//	JsonArray jsonSlidesArray = json.getJsonArray(JSON_SLIDES);
//	for (int i = 0; i < jsonSlidesArray.size(); i++) {
//	    JsonObject slideJso = jsonSlidesArray.getJsonObject(i);
//	    slideShowToLoad.addSlide(slideJso.getString(JSON_IMAGE_FILE_NAME),
//		    slideJso.getString(JSON_IMAGE_PATH),
//		    slideJso.getString(JSON_CAPTION));
//	}
//    }

//    // AND HERE ARE THE PRIVATE HELPER METHODS TO HELP THE PUBLIC ONES
//    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
//	InputStream is = new FileInputStream(jsonFilePath);
//	JsonReader jsonReader = Json.createReader(is);
//	JsonObject json = jsonReader.readObject();
//	jsonReader.close();
//	is.close();
//	return json;
//    }
//
//    private ArrayList<String> loadArrayFromJSONFile(String jsonFilePath, String arrayName) throws IOException {
//	JsonObject json = loadJSONFile(jsonFilePath);
//	ArrayList<String> items = new ArrayList();
//	JsonArray jsonArray = json.getJsonArray(arrayName);
//	for (JsonValue jsV : jsonArray) {
//	    items.add(jsV.toString());
//	}
//	return items;
//    }

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
                .add(JSON_COMPONENT_ARRAY, jA)
                .build();
        return jso;
        
    }
    
    
    private JsonArray makeSlidesJsonArray(ArrayList<Slide> slides) {
	JsonArrayBuilder jsb = Json.createArrayBuilder();
	for (Slide slide : slides) {
	    JsonObject jso = makeSlideJsonObject(slide);
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
    
    private JsonObject makeComponentJsonObject(Component component){
        JsonObject jso;
        if(component instanceof PictureComponent){
            jso = Json.createObjectBuilder()
                    .add(JSON_COMPONENT_CONTENT, ((PictureComponent)component).getContent())
                    .add(JSON_COMPONENT_TYPE,((PictureComponent)component).getType())
                    .add(JSON_COMPONENT_SRC, ((PictureComponent)component).getSrc())
                    .build();
        }
        else if(component instanceof VideoComponent){
            jso = Json.createObjectBuilder()
                    .add(JSON_COMPONENT_CONTENT, ((VideoComponent)component).getContent())
                    .add(JSON_COMPONENT_TYPE,((VideoComponent)component).getType())
                    .add(JSON_COMPONENT_SRC, ((VideoComponent)component).getSrc())
                    .build();
        }
        else if(component instanceof hyperTextComponent){
            jso = Json.createObjectBuilder()
                    .add(JSON_COMPONENT_CONTENT, ((hyperTextComponent)component).getContent())
                    .add(JSON_COMPONENT_TYPE,((hyperTextComponent)component).getType())
                    .add(JSON_COMPONENT_SRC, ((hyperTextComponent)component).getLink())
                    .build();
        }
        else if(component instanceof textComponent){
            jso = Json.createObjectBuilder()
                    .add(JSON_COMPONENT_CONTENT, ((textComponent)component).getContent())
                    .add(JSON_COMPONENT_TYPE,((textComponent)component).getType())
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
                    .build();
        }
        
        return jso;
    }
}
