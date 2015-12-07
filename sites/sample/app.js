var page_pos=******;
var IMG_PATH;
var ICON_PATH;
var IMG_WIDTH;
var SCALED_IMAGE_HEIGHT;
var FADE_TIME;
var SLIDESHOW_SLEEP_TIME;

// DATA FOR CURRENT SLIDE
var title;
var slides;
var currentSlide;

// TIMER FOR PLAYING SLIDESHOW
var timer;

function Slide(initImgFile, initCaption) {
    this.imgFile = initImgFile;
    this.caption = initCaption;
}


function initSlideshow() {
    IMG_PATH = "./img/";
    ICON_PATH = "icons/";
    IMG_WIDTH = 1000;
    SCALED_IMAGE_HEIGHT = 500.0;
    FADE_TIME = 1000;
    SLIDESHOW_SLEEP_TIME = 3000;
    slides = new Array();
    timer = null;
}


function initPage(v) {
    var $slideShowTitle = $('<h>',{"id":"slideshow_title"});
    $slideShowTitle.text(v.componet_content);
    var $slideShowCaption = $('<h2>',{});
    var $slideShowPicture = $('<img>',{	"id":"slide_img",
    									"src":""});
    var $prebutton = $('<input>',{"id":"previous_button",
    							"type":"image",
								"src":"icons/Previous.png",
								"onClick":"processPreviousRequest()"});
    var $playbutton = $('<input>',{"id":"play_pause_button",
    							"type":"image",
								"src":"icons/Play.png",
								"onClick":"processPlayPauseRequest()"});
    var $nextbutton = $('<input>',{"id":"next_button",
    							"type":"image",
								"src":"icons/Next.png",
								"onClick":"processNextRequest()"});
    var $slideShowControl = $('<section>',{"id":"slideshow_controls"});
   	$slideShowControl.append($slideShowTitle,$('<br/>'));
   	$slideShowControl.append($slideShowPicture);
   	$slideShowControl.append($slideShowCaption);

   	$slideShowControl.append($prebutton);
   	$slideShowControl.append($playbutton);
   	$slideShowControl.append($nextbutton);

    $slideShowTitle.html(title);
    if (currentSlide >= 0) {
	$slideShowCaption.html(slides[currentSlide].caption);
	$slideShowPicture.attr("src", IMG_PATH + slides[currentSlide].imgFile);
	$slideShowPicture.one("load", function() {
	    autoScaleImage();
	});
    }
    $(".page").append($slideShowControl);
}

function autoScaleImage() {
	var origHeight = $("#slide_img").height();
	var scaleFactor = SCALED_IMAGE_HEIGHT/origHeight;
	var origWidth = $("#slide_img").width();
	var scaledWidth = origWidth * scaleFactor;
	$("#slide_img").height(SCALED_IMAGE_HEIGHT);
	$("#slide_img").width(scaledWidth);
	var left = (IMG_WIDTH-scaledWidth)/2;
	$("#slide_img").css("left", left);
}

function fadeInCurrentSlide() {
    var filePath = IMG_PATH + slides[currentSlide].imgFile;
    $("#slide_img").fadeOut(FADE_TIME, function(){
	$(this).attr("src", filePath).bind('onreadystatechange load', function(){
	    if (this.complete) {
		$(this).fadeIn(FADE_TIME);
		$("#slide_caption").html(slides[currentSlide].caption);
		autoScaleImage();
	    }
	});
    });     
}


function loadSlideshow(slideshowData) {
    title = slideshowData.title;
    for (var i = 0; i < slideshowData.slideshow.length; i++) {
	var rawSlide = slideshowData.slideshow[i];
	var slide = new Slide(rawSlide.image_file_name, rawSlide.caption);
	slides[i] = slide;
    }
    if (slides.length > 0)
	currentSlide = 0;
    else
	currentSlide = -1;
}

function processPreviousRequest() {
    currentSlide--;
    if (currentSlide < 0)
	currentSlide = slides.length-1;
    fadeInCurrentSlide();
}

function processPlayPauseRequest() {
    if (timer === null) {
	timer = setInterval(processNextRequest, SLIDESHOW_SLEEP_TIME);
	$("#play_pause_button").attr("src", "icons/Pause.png");
    }
    else {
	clearInterval(timer);
	timer = null;
	$("#play_pause_button").attr("src", "icons/Play.png");
    }	
}

function processNextRequest() {
    currentSlide++;
    if (currentSlide >= slides.length)
	currentSlide = 0;
    fadeInCurrentSlide();
}

$.ajax({
    url: 'data.json', 
    dataType: 'json',
    type: 'get',
    cache: true,
    success: function(data){
    	$(".eportfolio_name").text(data.title);
    	$(data.pages).each(function(index,value){
    		var $page = $('<li>',{});
    		var $link = $('<a>',{"href": value.page_title+".html"});
    		$link.text(value.page_title);
    		$page.append($link);
    		$(".menu ul").append($page);

    		if(page_pos == value.page_position){
                $(".foot").text(value.footer);
                $(".banner img").attr('src',value.banner);
    			$(value.component_array).each(function(index1, value1){
    				if(value1.component_type=="v"){
    					var $video = $('<video>',{"controls":{}});
    					var $source = $('<source>', {"src":"./video/"+value1.video_name});
    					$video.append($source);
    					var $videoCaption = $('<h2>',{});
    					$videoCaption.text(value1.componet_content);
    					$(".page").append($video);
    					$(".page").append($videoCaption);
    				}
    				if(value1.component_type=="t"){
    					var $paragraph = $('<p>',{});
    					$paragraph.text(value1.componet_content);
    					$(".page").append($paragraph);
    				}
    				if(value1.component_type=="p"){
    					var $picture = $('<img>',{"src":value1.component_src});
    					var $pictureCaption = $('<h2>',{});
    					$pictureCaption.text(value1.componet_content);
    					$(".page").append($picture);
    					$(".page").append($pictureCaption);
    				}
                                if(value1.component_type =="l"){
                                    var $list = $('<ul>',{});
                                    $(value1.List_component).each(function(index3, value3){
                                        var $item = $('<li>',{});
                                        $item.text(value3.list_item);
                                        $list.append($item);
                                    });
                                    $(".page").append($list);
                                }

    				if(value1.component_type=="hl"){
    					var $text = $('<a>',{"href":value1.component_src});
    					$text.text(value1.componet_content);
    					$(".page").append($text);
    				}

    				if(value1.component_type=="s"){
    					initSlideshow()
						loadSlideshow(value1);
						initPage(value1);
    					$(value1.slideshow).each(function(index2, value2){
    					});
    				}
    			});
    		}
    	});

    }
});

var main=function(){
};

$(document).ready(main);