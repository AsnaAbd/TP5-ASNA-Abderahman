$(document).ready(function() {

				$('table[name=mytable]').tableFilter({
				trigger : {
						event 	: "keyup",
					},
					sort : true,
					callback : function() {
					},
					notFoundElement : ".not-found"
					
				});
			});
