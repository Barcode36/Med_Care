{\rtf1\ansi\ansicpg1252\deff0\deflang1033{\fonttbl{\f0\fswiss\fcharset0 Arial;}}
{\*\generator Msftedit 5.41.15.1507;}\viewkind4\uc1\pard\f0\fs20 // jQuery Alert Dialogs Plugin\par
//\par
// Version 1.0\par
//\par
// Cory S.N. LaViska\par
// A Beautiful Site (http://abeautifulsite.net/)\par
// 29 December 2008\par
//\par
// This is an altered version form Aur\'e9lien Malisart <aurelien.malisart@gmail.com>\par
//\par
// Visit http://github.com/aurels/jquery.alerts\par
// Visit http://abeautifulsite.net/notebook/87 for more information\par
//\par
(function($) \{\par
\par
\tab $.alerts = \{\par
\par
\tab\tab // These properties can be read/written by accessing $.alerts.propertyName from your scripts at any time\par
\par
\tab\tab verticalOffset: -75,                // vertical offset of the dialog from center screen, in pixels\par
\tab\tab horizontalOffset: 0,                // horizontal offset of the dialog from center screen, in pixels/\par
\tab\tab repositionOnResize: true,           // re-centers the dialog on window resize\par
\tab\tab overlayOpacity: .01,                // transparency level of overlay\par
\tab\tab overlayColor: '#FFF',               // base color of overlay\par
\tab\tab draggable: true,                    // make the dialogs draggable (requires UI Draggables plugin)\par
\tab\tab okButton: '&nbsp;OK&nbsp;',         // text for the OK button (not used anymore in this version)\par
\tab\tab cancelButton: '&nbsp;Cancel&nbsp;', // text for the Cancel button (not used anymore in this version)\par
\tab\tab dialogClass: null,                  // if specified, this class will be applied to all dialogs\par
\par
\tab\tab // Public methods\par
\par
\tab\tab alert: function(message, ok, callback) \{\par
\tab\tab\tab $.alerts._show(message, null, ok, null, 'alert', function(result) \{\par
\tab\tab\tab\tab if( callback ) callback(result);\par
\tab\tab\tab\});\par
\tab\tab\},\par
\par
\tab\tab confirm: function(message, ok, cancel, callback) \{\par
\tab\tab\tab $.alerts._show(message, null, ok, cancel, 'confirm', function(result) \{\par
\tab\tab\tab\tab if( callback ) callback(result);\par
\tab\tab\tab\});\par
\tab\tab\},\par
\par
\tab\tab prompt: function(message, value, ok, cancel, callback) \{\par
\tab\tab\tab $.alerts._show(message, value, ok, cancel, 'prompt', function(result) \{\par
\tab\tab\tab\tab if( callback ) callback(result);\par
\tab\tab\tab\});\par
\tab\tab\},\par
\par
\tab\tab // Private methods\par
\par
\tab\tab _show: function(msg, value, ok, cancel, type, callback) \{\par
\par
\tab\tab\tab $.alerts._hide();\par
\tab\tab\tab $.alerts._overlay('show');\par
\par
\tab\tab\tab $("BODY").append(\par
\tab\tab\tab   '<div id="popup_container">' +\par
\tab\tab\tab     '<div id="popup_content">' +\par
\tab\tab\tab       '<div id="popup_message"></div>' +\par
\tab\tab\tab\tab '</div>' +\par
\tab\tab\tab   '</div>');\par
\par
\tab\tab\tab if( $.alerts.dialogClass ) $("#popup_container").addClass($.alerts.dialogClass);\par
\par
\tab\tab\tab // IE6 Fix\par
\tab\tab\tab var pos = ($.browser.msie && parseInt($.browser.version) <= 6 ) ? 'absolute' : 'fixed'; \par
\par
\tab\tab\tab $("#popup_container").css(\{\par
\tab\tab\tab\tab position: pos,\par
\tab\tab\tab\tab zIndex: 99999,\par
\tab\tab\tab\tab padding: 0,\par
\tab\tab\tab\tab margin: 0\par
\tab\tab\tab\});\par
\par
\tab\tab\tab $("#popup_content").addClass(type);\par
\tab\tab\tab $("#popup_message").text(msg);\par
\tab\tab\tab $("#popup_message").html( $("#popup_message").text().replace(/\\n/g, '<br />') );\par
\par
\tab\tab\tab $("#popup_container").css(\{\par
\tab\tab\tab\tab minWidth: $("#popup_container").outerWidth(),\par
\tab\tab\tab\tab maxWidth: $("#popup_container").outerWidth()\par
\tab\tab\tab\});\par
\par
\tab\tab\tab $.alerts._reposition();\par
\tab\tab\tab $.alerts._maintainPosition(true);\par
\par
\tab\tab\tab switch( type ) \{\par
\tab\tab\tab\tab case 'alert':\par
\tab\tab\tab\tab\tab $("#popup_message").after('<div id="popup_panel"><input type="button" value="' + ok + '" id="popup_ok" /></div>');\par
\tab\tab\tab\tab\tab $("#popup_ok").click( function() \{\par
\tab\tab\tab\tab\tab\tab $.alerts._hide();\par
\tab\tab\tab\tab\tab\tab callback(true);\par
\tab\tab\tab\tab\tab\});\par
\tab\tab\tab\tab\tab $("#popup_ok").focus().keypress( function(e) \{\par
\tab\tab\tab\tab\tab\tab if( e.keyCode == 13 || e.keyCode == 27 ) $("#popup_ok").trigger('click');\par
\tab\tab\tab\tab\tab\});\par
\tab\tab\tab\tab break;\par
\tab\tab\tab\tab case 'confirm':\par
\tab\tab\tab\tab\tab $("#popup_message").after('<div id="popup_panel"><input type="button" value="' + ok + '" id="popup_ok" /> <input type="button" value="' + cancel + '" id="popup_cancel" /></div>');\par
\tab\tab\tab\tab\tab $("#popup_ok").click( function() \{\par
\tab\tab\tab\tab\tab\tab $.alerts._hide();\par
\tab\tab\tab\tab\tab\tab if( callback ) callback(true);\par
\tab\tab\tab\tab\tab\});\par
\tab\tab\tab\tab\tab $("#popup_cancel").click( function() \{\par
\tab\tab\tab\tab\tab\tab $.alerts._hide();\par
\tab\tab\tab\tab\tab\tab if( callback ) callback(false);\par
\tab\tab\tab\tab\tab\});\par
\tab\tab\tab\tab\tab // $("#popup_ok").focus();\par
\tab\tab\tab\tab\tab $("#popup_ok, #popup_cancel").keypress( function(e) \{\par
\tab\tab\tab\tab\tab\tab if( e.keyCode == 13 ) $("#popup_ok").trigger('click');\par
\tab\tab\tab\tab\tab\tab if( e.keyCode == 27 ) $("#popup_cancel").trigger('click');\par
\tab\tab\tab\tab\tab\});\par
\tab\tab\tab\tab break;\par
\tab\tab\tab\tab case 'prompt':\par
\tab\tab\tab\tab\tab $("#popup_message").append('<br /><input type="text" size="30" id="popup_prompt" />').after('<div id="popup_panel"><input type="button" value="' + ok + '" id="popup_ok" /> <input type="button" value="' + cancel + '" id="popup_cancel" /></div>');\par
\tab\tab\tab\tab\tab $("#popup_prompt").width( $("#popup_message").width() );\par
\tab\tab\tab\tab\tab $("#popup_ok").click( function() \{\par
\tab\tab\tab\tab\tab\tab var val = $("#popup_prompt").val();\par
\tab\tab\tab\tab\tab\tab $.alerts._hide();\par
\tab\tab\tab\tab\tab\tab if( callback ) callback( val );\par
\tab\tab\tab\tab\tab\});\par
\tab\tab\tab\tab\tab $("#popup_cancel").click( function() \{\par
\tab\tab\tab\tab\tab\tab $.alerts._hide();\par
\tab\tab\tab\tab\tab\tab if( callback ) callback( null );\par
\tab\tab\tab\tab\tab\});\par
\tab\tab\tab\tab\tab $("#popup_prompt, #popup_ok, #popup_cancel").keypress( function(e) \{\par
\tab\tab\tab\tab\tab\tab if( e.keyCode == 13 ) $("#popup_ok").trigger('click');\par
\tab\tab\tab\tab\tab\tab if( e.keyCode == 27 ) $("#popup_cancel").trigger('click');\par
\tab\tab\tab\tab\tab\});\par
\tab\tab\tab\tab\tab if( value ) $("#popup_prompt").val(value);\par
\tab\tab\tab\tab\tab $("#popup_prompt").focus().select();\par
\tab\tab\tab\tab break;\par
\tab\tab\tab\}\par
\par
\tab\tab\tab // Make draggable\par
\tab\tab\tab // if( $.alerts.draggable ) \{\par
\tab\tab\tab // \tab\tab\tab\tab try \{\par
\tab\tab\tab // \tab\tab\tab\tab\tab //$("#popup_container").draggable(\{ handle: $("#popup_title") \});\par
\tab\tab\tab // \tab\tab\tab\tab\tab //$("#popup_title").css(\{ cursor: 'move' \});\par
\tab\tab\tab // \tab\tab\tab\tab\} catch(e) \{ /* requires jQuery UI draggables */ \}\par
\tab\tab\tab // \tab\tab\tab\}\par
\tab\tab\},\par
\par
\tab\tab _hide: function() \{\par
\tab\tab\tab $("#popup_container").remove();\par
\tab\tab\tab $.alerts._overlay('hide');\par
\tab\tab\tab $.alerts._maintainPosition(false);\par
\tab\tab\},\par
\par
\tab\tab _overlay: function(status) \{\par
\tab\tab\tab switch( status ) \{\par
\tab\tab\tab\tab case 'show':\par
\tab\tab\tab\tab\tab $.alerts._overlay('hide');\par
\tab\tab\tab\tab\tab $("BODY").append('<div id="popup_overlay"></div>');\par
\tab\tab\tab\tab\tab $("#popup_overlay").css(\{\par
\tab\tab\tab\tab\tab\tab position: 'absolute',\par
\tab\tab\tab\tab\tab\tab zIndex: 99998,\par
\tab\tab\tab\tab\tab\tab top: '0px',\par
\tab\tab\tab\tab\tab\tab left: '0px',\par
\tab\tab\tab\tab\tab\tab width: '100%',\par
\tab\tab\tab\tab\tab\tab height: $(document).height(),\par
\tab\tab\tab\tab\tab\tab background: $.alerts.overlayColor,\par
\tab\tab\tab\tab\tab\tab opacity: $.alerts.overlayOpacity\par
\tab\tab\tab\tab\tab\});\par
\tab\tab\tab\tab break;\par
\tab\tab\tab\tab case 'hide':\par
\tab\tab\tab\tab\tab $("#popup_overlay").remove();\par
\tab\tab\tab\tab break;\par
\tab\tab\tab\}\par
\tab\tab\},\par
\par
\tab\tab _reposition: function() \{\par
\tab\tab\tab var top = (($(window).height() / 2) - ($("#popup_container").outerHeight() / 2)) + $.alerts.verticalOffset;\par
\tab\tab\tab var left = (($(window).width() / 2) - ($("#popup_container").outerWidth() / 2)) + $.alerts.horizontalOffset;\par
\tab\tab\tab if( top < 0 ) top = 0;\par
\tab\tab\tab if( left < 0 ) left = 0;\par
\par
\tab\tab\tab // IE6 fix\par
\tab\tab\tab if( $.browser.msie && parseInt($.browser.version) <= 6 ) top = top + $(window).scrollTop();\par
\par
\tab\tab\tab $("#popup_container").css(\{\par
\tab\tab\tab\tab top: top + 'px',\par
\tab\tab\tab\tab left: left + 'px'\par
\tab\tab\tab\});\par
\tab\tab\tab $("#popup_overlay").height( $(document).height() );\par
\tab\tab\},\par
\par
\tab\tab _maintainPosition: function(status) \{\par
\tab\tab\tab if( $.alerts.repositionOnResize ) \{\par
\tab\tab\tab\tab switch(status) \{\par
\tab\tab\tab\tab\tab case true:\par
\tab\tab\tab\tab\tab\tab $(window).bind('resize', function() \{\par
\tab\tab\tab\tab\tab\tab\tab $.alerts._reposition();\par
\tab\tab\tab\tab\tab\tab\});\par
\tab\tab\tab\tab\tab break;\par
\tab\tab\tab\tab\tab case false:\par
\tab\tab\tab\tab\tab\tab $(window).unbind('resize');\par
\tab\tab\tab\tab\tab break;\par
\tab\tab\tab\tab\}\par
\tab\tab\tab\}\par
\tab\tab\}\par
\par
\tab\}\par
\par
\tab // Shortuct functions\par
\tab jAlert = function(message, ok, callback) \{\par
\tab\tab $.alerts.alert(message, ok, callback);\par
\tab\}\par
\par
\tab jConfirm = function(message, ok, cancel, callback) \{\par
\tab\tab $.alerts.confirm(message, ok, cancel, callback);\par
\tab\};\par
\par
\tab jPrompt = function(message, value, ok, cancel, callback) \{\par
\tab\tab $.alerts.prompt(message, value, ok, cancel, callback);\par
\tab\};\par
\par
\})(jQuery);\par
}
 