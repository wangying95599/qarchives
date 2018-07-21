/*
 * FlexPaper - The open source document viewer
 * 
 * http://flexpaper.devaldi.com
 *
 * Copyright (c) 2010 Erik Engström
 * Dual licensed under GPL v3 and commercial
 * license. More information about licensing can 
 * be found at http://flexpaper.devaldi.com/license.htm
 */

var slider;
var docViewer;
var flexPaperVars;
var params;
var FlexPaperFullScreen = false;

/**
 * Returns the document viewer component
 *
 */
var docViewer;

function getDocViewer(){
    if(docViewer)
    	return docViewer;
    else
    	docViewer = window.FlexPaperViewer_Instance.getApi();
    	
    return docViewer;
}

/**
*
* FlexPaper constructor (name of swf, name of placeholder, config)
*
*/
window.FlexPaperViewer = window.$f = function() {
	var config = arguments[2].config;
	
	window.FlexPaperViewer_Instance = flashembed(arguments[1], {
	
			src: arguments[0]+".swf",
			version: [10, 0],
			expressInstall: "js/expressinstall.swf"
		},{
			SwfFile : escape(config.SwfFile),
			Scale : config.Scale, 
			ZoomTransition : config.ZoomTransition,
			ZoomTime : config.ZoomTime,
			ZoomInterval : config.ZoomInterval,
			FitPageOnLoad : config.FitPageOnLoad,
			FitWidthOnLoad : config.FitWidthOnLoad,
			PrintEnabled : config.PrintEnabled,
			FullScreenAsMaxWindow : config.FullScreenAsMaxWindow,
			ProgressiveLoading : config.ProgressiveLoading,
			MinZoomSize : config.MinZoomSize,
			MaxZoomSize : config.MaxZoomSize,
			SearchMatchAll : config.SearchMatchAll,
			SearchServiceUrl : config.SearchServiceUrl,
			InitViewMode : config.InitViewMode,
			BitmapBasedRendering : config.BitmapBasedRendering,
			StartAtPage : config.StartAtPage,
			
			ViewModeToolsVisible : config.ViewModeToolsVisible,
			ZoomToolsVisible : config.ZoomToolsVisible,
			NavToolsVisible : config.NavToolsVisible,
			CursorToolsVisible : config.CursorToolsVisible,
			SearchToolsVisible : config.SearchToolsVisible,
			  
			localeChain : config.localeChain,
			key : config.key
	});
};

$(window).load(function() { 
	addSlider('zoomSlider');
});

/**
 * Adds the slider control to the UI
 *
 * @example addSlider( "slider1" );
 *
 * @param String id
 */
function addSlider(id){
	if (!slider) {
		slider = new Slider(id, {
			callback: function(value){
			
			},
			animation_callback: function(value){
				if (value > 0) {
					getDocViewer().sliderChange(5 * value);
				}
			}
		});
	}	
}			
	
/**
 * Handles the event of scale being changed
 *
 * @example onScaleChanged( 100 );
 *
 * @param float val
 */
function onScaleChanged(val){
	if(!slider)
		addSlider('zoomSlider');

	slider.setValue(val/5,true);
	slider.slide();
	slider.show();
	$("#txtZoomFactor").val(Math.round((val * 100)) + "%");
}

/**
 * Called when FlexPaper finishes loading
 *
 * @param object e
 */
function swfCallbackFn(e){
	if(e.success){
		docViewer = e.ref;
	}else{
		docViewer = null;
	}
}

/**
 * Handles the event of a document is in progress of loading
 *
 */
function onDocumentLoading(){
    $("#progressSpinner").show();

    if(PendingFullScreen){
	setFullScreen(true);
    }

    if(!slider){
	if(FlexPaperFullScreen){addSlider('zoomSliderFullScreen');bindEventListeners();}
    } 
}

/**
 * Handles the event of fit mode being changed
 *
 * @example onFitModeChanged("Fit Height")
 *
 * @param String mode
 */
function onFitModeChanged(mode){
	$(".tbbutton_fitmode_selected").removeClass("tbbutton_fitmode_selected");
	
	if(mode == "Fit Height"){
		$("#bttnFitHeight").addClass('tbbutton_fitmode_selected');
	}else if(mode == "Fit Width"){
		$("#bttnFitWidth").addClass('tbbutton_fitmode_selected');
	}
}

/**
 * Receives messages about view mode being changed
 *
 * @example onViewModeChanged("Tile")
 *
 * @param String mode
 */
function onViewModeChanged(mode){
	$(".tbbutton_viewmode_selected").removeClass("tbbutton_viewmode_selected");
        
	if(mode=="Tile"){
		$("#bttnTileMode").addClass("tbbutton_viewmode_selected");
	}
	if(mode=="TwoPage"){
		$("#bttnTwoPage").addClass("tbbutton_viewmode_selected");
	}
	if(mode=="Portrait"){
		$("#bttnPortrait").addClass("tbbutton_viewmode_selected");
	}
}

/**
 * Receives messages about the document being loaded
 *
 * @example onDocumentLoaded( 20 );
 *
 * @param int totalPages
 */
function onDocumentLoaded(totalPages){
    $("#lblTotalPages").html(" / " + totalPages);
    $("#progressSpinner").hide();
}

/**
 * Receives error messages when a document is not loading properly
 *
 * @example onDocumentLoadedError( "Network error" );
 *
 * @param String errorMessage
 */
function onDocumentLoadedError(errMessage){

}

/**
 * Recieves progress information about the document being loaded
 *
 * @example onProgress( 100,10000 );
 *
 * @param int loaded
 * @param int total
 */
function onProgress(loadedBytes,totalBytes){

}

/**
 * Receives messages about the current page being changed
 *
 * @example onCurrentPageChanged( 10 );
 *
 * @param int pagenum
 */
function onCurrentPageChanged(pagenum){
    $("#txtPageNumber").val(pagenum);
}


$(document).ready(function() {
	bindEventListeners();
});


/**
 * Receives messages about the current cursor changed
 *
 * @example onCursorModeChanged( "TextSelectorCursor" );
 *
 * @param String cursor
 */
function onCursorModeChanged(cursor){
	$(".tbbutton_cursormode_selected").removeClass("tbbutton_cursormode_selected");
    
    if(cursor == "TextSelectorCursor"){
        $("#bttnTextSelect").addClass("tbbutton_cursormode_selected");
    }else{
        $("#bttnHandSelect").addClass("tbbutton_cursormode_selected");
    }
}

/**
 * Binds event listeners for the controls
 *
 */
function bindEventListeners(){
	
	$("#txtZoomFactor").bind('keypress', function(e) {
        if(e.keyCode==13){
        	try{
	        	var zf=$("#txtZoomFactor").val().replace("%","")/100;
	        	getDocViewer().sliderChange(zf);
        	}catch(exc){}
        }
	});

	$("#txtSearchText").bind('keypress', function(e) {
        if(e.keyCode==13){
        	searchText();
        }
	});
    
	$("#txtPageNumber").bind('keypress', function(e) {
        if(e.keyCode==13){
            try{getDocViewer().gotoPage($("#txtPageNumber").val());}catch(exc){}
        }
	});
}

/**
 * Performs a search using the text in the #txtSearchText input box
 *
 */
function searchText(){
    getDocViewer().searchText($("#txtSearchText").val());
}

/**
 * Sets the fullscreen mode on the viewer
 *
 * @example setFullScreen(true)
 *
 * @param boolean val
 */
function setFullScreen(val){
    if(val){
        $("#bttnFullScreen").addClass("tbbutton_fullscreen_selected");
        FlexPaperFullScreen = true;
    }else{
        FlexPaperFullScreen = false;
    }
}

/**
 * Handles the event of external links getting clicked in the document. 
 *
 * @example onExternalLinkClicked("http://www.google.com")
 *
 * @param String link
 */
function onExternalLinkClicked(link){
   // alert("link " + link + " clicked" );
   window.location.href = link;
}

/**
 * Receives error messages when a document has finished printed
 *
 * @example onDocumentPrinted();
 *
 */
function onDocumentPrinted(){
}

/**
 * Handles the event of text getting selected. 
 *
 * @example onTextSelected("abc")
 *
 * @param String the text selected
 */
function onTextSelected(text){
	alert("Text selected: " + text);
}

/**
 * shows FlexPaper in full screen
 *
 */
function showFullScreen() 
{
     if(FlexPaperFullScreen)
     {
     window.close();
     }
     else
     {
	 params  = 'toolbar=no, location=no, scrollbars=no, width='+screen.width;
	 params += ', height='+screen.height;
	 params += ', top=0, left=0'
	 params += ', fullscreen=yes';
	
	 nw=window.open('','windowname4', params);
	 nw.flexPaperVars = flexPaperVars;
	 nw.params = params;
     
	 var htmldata = '';
	 var flexPaperSnip = $("#FlexPaper").html();
     flexPaperSnip = flexPaperSnip.replace($("#zoomSlider").outer(),'<div id="zoomSliderFullScreen" class="slider"><div class="handle"><img src="images/sliderdot.gif"/></div></div>');
     
     htmldata += '<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">';
	 htmldata += '<html xmlns="http://www.w3.org/1999/xhtml" lang="en" xml:lang="en">';
	 htmldata += '<head>';
	 htmldata += '<link rel="stylesheet" type="text/css" href="css/quetviewer.css" />';
     htmldata += '<link rel="stylesheet" type="text/css" href="css/slider.css" />';
	 htmldata += '<scr'+'ipt type="text/javascript" src="jquery.js"></scr'+'ipt>';
	 htmldata += '<scr'+'ipt type="text/javascript" src="slider.js"></scr'+'ipt>';
     htmldata += '<scr'+'ipt type="text/javascript" src="js/flexpaper_htmlui.js"></scr'+'ipt>';
	 htmldata += '</head>';
	 htmldata += '<body>';
	 htmldata += '<div id="FlexPaper" style="width:100%;height:96%">';
	 htmldata += flexPaperSnip;
	 htmldata += '</div>';
	 htmldata += '</body>';
	 htmldata += '</html>';
	 nw.document.write(htmldata);
	 nw.PendingFullScreen = true;

	 if (window.focus) {nw.focus()}
     }
	 return false;
}

/* jQuery.fn.outerHTML = function(s) {
return (s)
? this.before(s).remove()
: jQuery("<div>").append(this.eq(0).clone()).html();
} */

/** 
 * 
 * FlexPaper embedding functionality. Based on FlashEmbed
 *
 */

(function() {
		
	var IE = document.all,
		 URL = 'http://www.adobe.com/go/getflashplayer',
		 JQUERY = typeof jQuery == 'function', 
		 RE = /(\d+)[^\d]+(\d+)[^\d]*(\d*)/,
		 GLOBAL_OPTS = { 
			// very common opts
			width: '100%',
			height: '100%',		
			id: "_" + ("" + Math.random()).slice(9),
			
			// flashembed defaults
			allowfullscreen: true,
			allowscriptaccess: 'always',
			quality: 'high',	
			
			// flashembed specific options
			version: [3, 0],
			onFail: null,
			expressInstall: null, 
			w3c: false,
			cachebusting: false  		 		 
	};
	
	// version 9 bugfix: (http://blog.deconcept.com/2006/07/28/swfobject-143-released/)
	if (window.attachEvent) {
		window.attachEvent("onbeforeunload", function() {
			__flash_unloadHandler = function() {};
			__flash_savedUnloadHandler = function() {};
		});
	}
	
	// simple extend
	function extend(to, from) {
		if (from) {
			for (var key in from) {
				if (from.hasOwnProperty(key)) {
					to[key] = from[key];
				}
			}
		} 
		return to;
	}	

	// used by asString method	
	function map(arr, func) {
		var newArr = []; 
		for (var i in arr) {
			if (arr.hasOwnProperty(i)) {
				newArr[i] = func(arr[i]);
			}
		}
		return newArr;
	}

	window.flashembed = function(root, opts, conf) {
	
		// root must be found / loaded	
		if (typeof root == 'string') {
			root = document.getElementById(root.replace("#", ""));
		}
		
		// not found
		if (!root) { return; }
		
		root.onclick = function(){return false;}
		
		if (typeof opts == 'string') {
			opts = {src: opts};	
		}

		return new Flash(root, extend(extend({}, GLOBAL_OPTS), opts), conf); 
	};	
	
	// flashembed "static" API
	var f = extend(window.flashembed, {
		
		conf: GLOBAL_OPTS,
	
		getVersion: function()  {
			var fo, ver;
			
			try {
				ver = navigator.plugins["Shockwave Flash"].description.slice(16); 
			} catch(e) {
				
				try  {
					fo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.7");
					ver = fo && fo.GetVariable("$version");
					
				} catch(err) {
                try  {
                    fo = new ActiveXObject("ShockwaveFlash.ShockwaveFlash.6");
                    ver = fo && fo.GetVariable("$version");  
                } catch(err2) { } 						
				} 
			}
			
			ver = RE.exec(ver);
			return ver ? [ver[1], ver[3]] : [0, 0];
		},
		
		asString: function(obj) { 

			if (obj === null || obj === undefined) { return null; }
			var type = typeof obj;
			if (type == 'object' && obj.push) { type = 'array'; }
			
			switch (type){  
				
				case 'string':
					obj = obj.replace(new RegExp('(["\\\\])', 'g'), '\\$1');
					
					// flash does not handle %- characters well. transforms "50%" to "50pct" (a dirty hack, I admit)
					obj = obj.replace(/^\s?(\d+\.?\d+)%/, "$1pct");
					return '"' +obj+ '"';
					
				case 'array':
					return '['+ map(obj, function(el) {
						return f.asString(el);
					}).join(',') +']'; 
					
				case 'function':
					return '"function()"';
					
				case 'object':
					var str = [];
					for (var prop in obj) {
						if (obj.hasOwnProperty(prop)) {
							str.push('"'+prop+'":'+ f.asString(obj[prop]));
						}
					}
					return '{'+str.join(',')+'}';
			}
			
			// replace ' --> "  and remove spaces
			return String(obj).replace(/\s/g, " ").replace(/\'/g, "\"");
		},
		
		getHTML: function(opts, conf) {

			opts = extend({}, opts);
			
			/******* OBJECT tag and it's attributes *******/
			var html = '<object width="' + opts.width + 
				'" height="' + opts.height + 
				'" id="' + opts.id + 
				'" name="' + opts.id + '"';
			
			if (opts.cachebusting) {
				opts.src += ((opts.src.indexOf("?") != -1 ? "&" : "?") + Math.random());		
			}			
			
			if (opts.w3c || !IE) {
				html += ' data="' +opts.src+ '" type="application/x-shockwave-flash"';		
			} else {
				html += ' classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"';	
			}
			
			html += '>'; 
			
			/******* nested PARAM tags *******/
			if (opts.w3c || IE) {
				html += '<param name="movie" value="' +opts.src+ '" />'; 	
			} 
			
			// not allowed params
			opts.width = opts.height = opts.id = opts.w3c = opts.src = null;
			opts.onFail = opts.version = opts.expressInstall = null;
			
			for (var key in opts) {
				if (opts[key]) {
					html += '<param name="'+ key +'" value="'+ opts[key] +'" />';
				}
			}	
		
			/******* FLASHVARS *******/
			var vars = "";
			
			if (conf) {
				for (var k in conf) { 
					if (conf[k]) {
						var val = conf[k]; 
						vars += k +'='+ (/function|object/.test(typeof val) ? f.asString(val) : val) + '&';
					}
				}
				vars = vars.slice(0, -1);
				html += '<param name="flashvars" value=\'' + vars + '\' />';
			}
			html += '<param name="wmode" value="Opaque">';	
			html += "</object>";	
			
			return html;				
		},
		
		isSupported: function(ver) {
			return VERSION[0] > ver[0] || VERSION[0] == ver[0] && VERSION[1] >= ver[1];			
		}		
		
	});
	
	var VERSION = f.getVersion(); 
	
	function Flash(root, opts, conf) {  
	                                                
		// version is ok
		if (f.isSupported(opts.version)) {
			root.innerHTML = f.getHTML(opts, conf);
			
		// express install
		} else if (opts.expressInstall && f.isSupported([6, 65])) {
			root.innerHTML = f.getHTML(extend(opts, {src: opts.expressInstall}), {
				MMredirectURL: location.href,
				MMplayerType: 'PlugIn',
				MMdoctitle: document.title
			});	
			
		} else {
			
			// fail #2.1 custom content inside container
			if (!root.innerHTML.replace(/\s/g, '')) {
				/* root.innerHTML = 
					"<h2>Flash version " + opts.version + " or greater is required</h2>" + 
					"<h3>" + 
						(VERSION[0] > 0 ? "Your version is " + VERSION : "You have no flash plugin installed") +
					"</h3>" + 
					
					(root.tagName == 'A' ? "<p>Click here to download latest version</p>" : 
						"<p>Download latest version from <a href='" + URL + "'>here</a></p>");
				*/
				var pageHost = ((document.location.protocol == "https:") ? "https://" :	"http://");
				
				root.innerHTML = "<a href='http://www.adobe.com/go/getflashplayer'><img src='" 
										+ pageHost + "www.adobe.com/images/shared/download_buttons/get_flash_player.gif' alt='Get Adobe Flash player' /></a>";
											
				if (root.tagName == 'A') {	
					root.onclick = function() {
						location.href = URL;
					};
				}				
			}
			
			// onFail
			if (opts.onFail) {
				var ret = opts.onFail.call(this);
				if (typeof ret == 'string') { root.innerHTML = ret; }	
			}			
		}
		
		// http://flowplayer.org/forum/8/18186#post-18593
		if (IE) {
			window[opts.id] = document.getElementById(opts.id);
		} 
		
		// API methods for callback
		extend(this, {
				
			getRoot: function() {
				return root;	
			},
			
			getOptions: function() {
				return opts;	
			},

			
			getConf: function() {
				return conf;	
			}, 
			
			getApi: function() {
				return root.firstChild;	
			}
			
		}); 
	}
	
	// setup jquery support
	if (JQUERY) {
		
		// tools version number
		jQuery.tools = jQuery.tools || {version: '1.2.5'};
		
		jQuery.tools.flashembed = {  
			conf: GLOBAL_OPTS
		};	
		
		jQuery.fn.flashembed = function(opts, conf) {		
			return this.each(function() { 
				$(this).data("flashembed", flashembed(this, opts, conf));
			});
		}; 
	} 
	
})();

this.tooltip = function(){	
	/* CONFIG */		
		xOffset = 10;
		yOffset = 20;		
	/* END CONFIG */		
	$("img.tbbutton").hover(function(e){											  
		this.t = this.title;
		this.title = "";									  
		$("body").append("<p id='tooltip'>"+ this.t +"</p>");
		$("#tooltip")
			.css("top",(e.pageY - xOffset) + "px")
			.css("left",(e.pageX + yOffset) + "px")
			.fadeIn("fast");		
    },
	function(){
		this.title = this.t;		
		$("#tooltip").remove();
    });	
	$("img.tbbutton").mousemove(function(e){
		$("#tooltip")
			.css("top",(e.pageY - xOffset) + "px")
			.css("left",(e.pageX + yOffset) + "px");
	});			
};

$(document).ready(function(){
	tooltip();
});

$.fn.outer = function(val){
    if(val){
        $(val).insertBefore(this);
        $(this).remove();
    }
    else{ return $("<div>").append($(this).clone()).html(); }
}