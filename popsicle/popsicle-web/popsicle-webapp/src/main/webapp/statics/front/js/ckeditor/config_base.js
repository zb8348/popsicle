
CKEDITOR.editorConfig = function( config ) {
	// Define changes to default configuration here. For example:
	// config.language = 'fr';
	// config.uiColor = '#AADC6E';
	
	config.toolbar = 'base';
	config.toolbar_base =
	[
	 ['Bold','Italic','Underline','Strike','-','Smiley','-','TextColor','BGColor']
	];
	config.enterMode = CKEDITOR.ENTER_BR;
    config.shiftEnterMode = CKEDITOR.ENTER_BR;  
};