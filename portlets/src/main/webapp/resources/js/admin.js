$(document).ready(function(){
	init();
});

// because when we get new content through ajax, it needs to be initialized too...
$(window).load(function () {
	jsf.ajax.addOnEvent(function(data) {
		if (data.status === 'success') {
			init();
		}
	});
});

function init() {
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
}

/* =========================
 * == ADD CONDITION MODAL ==
 * =========================
 */

function addUAString(element) {
	$(element).closest('td').append($('div.tt-ua').html());
	$(element).closest('td').children('div:last').children('input').focus();
	// set opacity to 0 so it keeps its space, and remove tooltip
	// $(element).css('opacity','0');
	// $(element).removeClass('tooltipTrigger');
	// $(element).removeAttr("data-original-title");
	// $(element).removeAttr('onclick');
	$(element).hide();
	init();
}

function removeUAString(element) {
	$(element).tooltip('hide');
	// $(element).closest('div.more-condition').prev().find('button.add-user-agent-string').css('opacity','1');
	// $(element).addClass('tooltipTrigger');
	// $(element).attr('onclick', 'addUAString(this);');
	parent = $(element).closest('div.more-condition').parent();
	$(element).closest('div.more-condition').remove();
	parent.children('div:last').find('button.add-user-agent-string').show();
	init();
}

function addProperty(element) {
	$(element).closest('tbody').append($('tbody.tt-pp').html());
	$(element).closest('tbody').children('tr:last').children('input').focus();
	$(element).hide();
	init();
}

function removeProperty(element) {
	$(element).tooltip('hide');
	parent = $(element).closest('tr.more-property').parent();
	$(element).closest('tr.more-property').remove();
	parent.children('tr:last').find('button.add-property').show();
	init();
}

function propertyFields(element) {
	// Change input box(es) according to selected option
	$(element).next().html($('.tt-' + $(element).val()).html());
}

function addMappingEntry() {
	// Add a new entry
	$('#mappings-tbody').prepend($('#tt-nm').children().html());
	// And move focus to it
	$('#mappings-tbody').find("input").first().focus();
	// Make JS work on the new components
	init();
}


















// --- UNUSED (SO FAR) ---

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
	$('.alert').alert();
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
	$('.delete-site').click(function() {
		// Set proper values for site to delete
		siteName = $(this).parent().parent().children("a").text().trim();
		$('#delete-site-id-input').attr("value", siteName);
		$('#delete-site-id-text').text(siteName);
		// Forcing dropdown to close.. should be automatic
		$(this).parent().parent().removeClass("open");
		$('#modal-delete-site').modal();
	});
	$('.delete-redirect').click(function() {
		$('#modal-delete-redirect').modal();
	});
	$('#import-site').click(function() {
		$('#file').bind("change", function (e) {
			//get the file path
			var file = $('#file').val();
			//pull out the filename
			file = file.replace(/^.*\\/i, "");
			//show to user
			$('#fileName').text(file);
			$('#file').fadeOut(300, function() {
				$('#file-attachment').fadeIn(300)
			});
		});

		$('#file-remove-btn').click(function() {
			$('#file').val("");
			$('#file-attachment').fadeOut(300, function() {
				$('#file').fadeIn(300)
			});
		});

		$('#modal-import-site').modal();
		// don't be a link
		return false;
	});
	$('#btn-add-mapping').click(function() {
		addMappingEntry();
	})
	$('.select-node').click(function() {
		nodeInput = $(this).parent().children("input");
		$('#modal-select-node').modal();
		// Allow double click
		$('.radio-node').parent("label").dblclick(function() {
			$('#button-select-node').click();
		});
	});
	$('#button-select-node').click(function() {
		sNode = $(".radio-node:checked").attr("id");
		nInput = $(nodeInput).parent().children("input");
		window.setTimeout(function() {
			$(".radio-node").first().click()
			nInput.focus();
			nInput.val(sNode);
		}, 50)
		$('#modal-select-node').modal('hide');
		return false;
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
	}
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

	// Fade out summary or initial and fade in edit on "Add Redirect" button click
	$('#add-redirect').click(function(){
		$('#add-redirect').attr("disabled", "disabled");

		// fade summary out (if present)...
		$('.redirect-summary').fadeOut(300, function() {
			// .. and when done, fade config in
			$('.edit-group').fadeIn(300)
		});
		// fade initial out (if present)...
		$('.initial').fadeOut(300, function() {
			// .. and when done, fade config in
			$('.edit-group').fadeIn(300)
		});

		// clear the form
	});

	// Fade out summary and fade in edit on "Configure" link click
	$('.configure-redirect').click(function(){
		$('#add-redirect').attr("disabled", "disabled");

		// fade summary out...
		$('.redirect-summary').fadeOut(300, function() {
			// .. and when done, fade config in
			$('.edit-group').fadeIn(300)
		});		
	});
	
	// Avoid showing summary and edit when edit is loaded. maybe show modal to confirm if there are changes made ?
	$('.site-link').click(function(){
		$(".edit-group").hide();
	});

	// On "Cancel" or "Save Changes" hide the edit form and show the summary
	$('#edit_save-changes, #edit_cancel').click(function(){
		$('.edit-group').fadeOut(300, function() {
			$('.redirect-summary').fadeIn(300)
		});
		$('#add-redirect').removeAttr("disabled");
	});

	$('.edit-node-mapping').click(function(){
		$(this).parent().parent().addClass('hidden-element');
		$(this).parent().parent().nextAll('tr .hidden-element').first().removeClass('hidden-element');
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

/* Modal Import Site */


