$(document).ready(function(){
// Bootstrap js
	bootstrapDropdown();
	bootstrapTooltip();
	bootstrapAlert();
	bootstrapPopover();
	bootstrapButton();
	bootstrapModal();
	bootstrapTypeahead();
// Useful js
	button();
	toggleContent();
	showHideMore();
	radioBackground();
	clearInputTextValue();
	switchGroupView();
	selectGroupInHierarchicalView();
// Provisional js	
	selectPermission();
	accessPermissionButton();
	accessPermissionTable();
	feedback();
	editRedirect();
// Useful js that makes other ones not work
	//sortable();
});


// Enabling dropdown
bootstrapDropdown = function() {
	$('.dropdown-toggle').dropdown();
};

// Enabling tooltip
bootstrapTooltip = function() {
	$('.tooltipTrigger').tooltip({
		placement: 'top'
	});
	$('.tooltipRightTrigger').tooltip({
		placement: 'right'
	});
	$('.tooltipBottomTrigger').tooltip({
		placement: 'bottom'
	});
};

// Enabling/Disabling alert
bootstrapAlert = function() {
	$(".alert").alert();
	$('.alert button.close').click(function(){
		$(this).parent.parent.alert('hide');	
	});
};

// Enabling popover
bootstrapPopover = function() {
	$('[rel=popover]').popover();
	$('[rel=popoverTop]').popover({
		placement: 'top'
	});
};

// Enabling radio buttons
bootstrapButton = function() {
	$('.radio-group').button()
}

// Modal
bootstrapModal = function() {
	$('#delete-site-space').click(function() {
		$('#modal-delete-site-space').modal();
	});
	$('.edit-permission').click(function() {
		$('#modal-permission').modal();
	});
	$('.delete-permission').click(function() {
		$('#modal-delete-permission').modal();
	});
	$('.delete-redirect').click(function() {
		$('#modal-delete-redirect').modal();
	});
	$('.select-node').click(function() {
		$('#modal-select-node').modal();
	});
	$('.add-condition').click(function() {
		$('#modal-condition').modal();
	});
	$('#create-group').click(function() {
		$('#modal-create-group').modal();
	});
	
	$('.modal button.close').click(function() {
		$(this).parent.parent.modal('hide');
	});
};

// Typeahead
bootstrapTypeahead = function() {
	$('.typeahead').typeahead()
};

// Do not move screen when clicking in a button
button = function(){
	$('button').click(function() {
		return false;
	});
};

// Toggle content 
toggleContent = function() {
	$('.toggle').click(function() {
		$(this).toggleClass('closed');
		$(this).parent().next().toggleClass('hidden-element');
	});
	if ($('aside .toggle').hasClass('closed')) {
    	alert('test');
    };
};

// Show / Hide More
showHideMore = function() {
	$('nav .more').click(function() {
		$(this).text($(this).text() == "Show more" ? "Hide more" : "Show more");
		$(this).parent().next('ul').find('.extra').toggleClass('hidden-element');	
	});
};

//Sortable
sortable = function() {
	$( ".sortable" ).sortable();
	$( ".sortable" ).disableSelection();
};


// Fixes footer at the bottom
footer = function() {
	var windowHeight = $(window).height();
	var containerRightHeight = $('#container-right').height();
	var footerFixed = windowHeight - containerRightHeight;
	if (footerFixed > 69){
		$('footer').addClass('fixed');
	};
};

// Background for selected item
radioBackground = function() {
	$('input[name=user-group]:checked').parent().addClass('active');
	$('input[name=user-group]').live("click", function () {
		$('input[name=user-group]').parent().removeClass('active');
		$('input[name=user-group]:checked').parent().addClass('active');
	});
};

// Clear input text value
clearInputTextValue = function() {
	$('.clear-input button').click(function() {
		$(this).parent().find('input').val('').focus();
	});		
};

// Switch Group View
switchGroupView = function() {
	$('#list-view').click(function() {
		$(this).addClass('active');
		$('#tree-view').removeClass('active');
		$('.window-list').removeClass('hidden-element');
		$('.window-tree').addClass('hidden-element');
		return false;
	});	
	$('#tree-view').click(function() {
		$(this).addClass('active');
		$('#list-view').removeClass('active');
		$('.window-tree').removeClass('hidden-element');
		$('.window-list').addClass('hidden-element');
		return false;
	});	
};

// Select Group in Hierarchical View
selectGroupInHierarchicalView = function() {
/*
	$('.window-tree a').click(function() { 
		$('.window-tree a').parent().removeClass('active');
		$(this).parent().addClass('active');
		$(this).parent().parent().children('li.parent').find('ul').addClass('hidden-element');
		$(this).parent().parent().find('li.parent').removeClass('opened');
	});
	$('.window-tree li.parent a').click(function() { 
		$(this).next('ul').removeClass('hidden-element');
		$(this).parent('li.parent').addClass('opened');
	});	
*/
	var ulHeight = $('.window-tree div ul').height();
	if (ulHeight > 375){
		$('.window-tree div ul').addClass('scroll');
	};
	$('.window-tree a').click(function() { 
		$('.window-tree a').parent().removeClass('active');
		$(this).parent().addClass('active');
		return false;
	});
	$('.window-tree li.parent a').click(function() { 
		$(this).parent().parent().parent().children('ul').removeClass('hidden-element');
		$(this).parent().parent().parent().next('div').children('ul').removeClass('hidden-element');
		$(this).parent('li.parent').addClass('opened');
	});	
};


// STYLES BELOW HERE ARE PROVISIONAL


// Select permission in the list
selectPermission = function() {
	$('.select li span').click(function() {
		$(this).parent().parent().find('li').find('span').removeClass('selected');
		$(this).toggleClass('selected');
		$('#modal-permission .pull-right .select').addClass('enabled');
	});
	$('.select .second li span').click(function() {
		$(this).parent().parent().parent().parent().find('li').find('span').removeClass('selected');
		$(this).toggleClass('selected');
	});
	$('.select .third li span').click(function() {
		$(this).parent().parent().parent().parent().parent().parent().find('li').find('span').removeClass('selected');
		$(this).toggleClass('selected');
	});
	$('.select i').click(function() {
		$(this).toggleClass('icon-closed');
		$(this).toggleClass('icon-opened');
		$(this).next().next('ul').toggleClass('hidden-element');
	});
};

// Show and Hide button "Add Permission" in "Permission to Access"
accessPermissionButton = function() {
	$('#private').click(function() {
		$(this).parent().next('a').removeClass('hidden-element');
		$(this).parent().parent().find('.alert').removeClass('hidden-element');
	});
	$('#public').click(function() {
		$(this).parent().parent().find('a').addClass('hidden-element');
		$(this).parent().parent().find('table').addClass('hidden-element');
		$(this).parent().parent().find('.alert').addClass('hidden-element');
	});	
};	
			
// Show table in "Permission to Access"
accessPermissionTable = function() {
	$('#access-permission a').click(function() {
		$(this).parent().find('table').removeClass('hidden-element');
		$(this).parent().find('.alert').addClass('hidden-element');
	});
};

// Show Feedback
feedback = function() {
	$('.form-actions .btn-primary.save').click(function() {
		$('.alert-container.save').removeClass('hidden-element');
	});
};

// Redirect
editRedirect = function() {
	$('#add-redirect').click(function(){
		$('.initial').addClass('hidden-element');
	});
	$('#add-redirect, #container-right > table > tbody > tr:first-child td.actions > a').click(function(){
		$('#add-redirect').addClass('transparent');
		$('.form-mobile-redirect').addClass('fade-in');
		$('.form-mobile-redirect').siblings().addClass('transparent');
	});
	$('.configure-redirect').click(function(){
		$(this).parent().parent().addClass('hidden-element');
	});
	
	$('.form-mobile-redirect .btn-primary').click(function(){
		$('.form-mobile-redirect').addClass('hidden-element');
		$('.yellow').removeClass('hidden-element');
		$('.yellow').addClass('fade-in');
		$('#add-redirect').removeClass('transparent');
		$('thead[id*=redirect-summary-header]').removeClass('hidden-element');
		$('thead[id*=redirect-summary-header]').removeClass('injectable');
		$('.form-mobile-redirect').siblings().removeClass('transparent');
	});
	$('.edit-node-mapping').click(function(){
		$(this).parent().parent().addClass('hidden-element');
		$(this).parent().parent().next('tr').removeClass('hidden-element');
		return false;
	});
	$('#modal-delete-redirect .btn-primary').click(function(){
		$(this).parent().parent().modal('hide');
		$('.alert-container').removeClass('hidden-element');	
	});
	
};

/*
// Anchor animation

anchorAnimation = function() {

	$(document).ready(function() {
		$("a.anchorLink").anchorAnimate()
	});
	
	jQuery.fn.anchorAnimate = function(settings) {
	
	 	settings = jQuery.extend({
			speed : 500
		}, settings);	
		
		return this.each(function(){
			var caller = this
			$(caller).click(function (event) {	
				event.preventDefault()
				var elementClick = $(caller).attr("href")
				
				var position = $(elementClick).offset().top;
				var destination = position - 40;
				$("html:not(:animated),body:not(:animated)").animate({ scrollTop: destination}, settings.speed);
			})
		})
	}
	
};
*/



