$(document).ready(function() {
    $('.cursorPointer').css('cursor', 'pointer');
    $('.formValidar').validate();
    var SPMaskBehavior = function (val) {
 		  return val.replace(/\D/g, '').length === 11 ? '(00) 00000-0000' : '(00) 0000-00009';
 		},
 		spOptions = {
 		  onKeyPress: function(val, e, field, options) {
 		      field.mask(SPMaskBehavior.apply({}, arguments), options);
 		    }
 		};
 		$('#telefone').mask(SPMaskBehavior, spOptions);
		var cropperImagem = function(fieldImage){
  		$(fieldImage).cropper({
  			aspectRatio: 1 / 1,
				  minContainerWidth: 500,
				  minContainerHeight: 300,
				  dragMode: 'crop',
				  background: false,
				  preview: '.preview',
				  checkCrossOrigin: false,
				  viewMode: 1,
				  autoCrop: true,
				  minCanvasWidth: 0,
				  minCanvasHeight: 0,
				  minCropBoxWidth: 0,
				  minCropBoxHeight: 0,
				  modal: true,
				  done: function(data) {
					  console.log(data);
				  },
				  crop: function(e) {
		 		  }
	  		});
  	}
		var cropperDestroy =  function(fieldImage){
  		$(fieldImage).cropper('destroy').removeAttr('src');
  	}
		var modal = function(url){
  		var imagem = $("#fotoOriginal");
  		$(imagem).attr("src", url);
  		$("#modalFoto").on("show", function() {    // wire up the OK button to dismiss the modal when shown
  	        $("#modalFoto a.btn").on("click", function(e) {
  	            $("#modalFoto").modal('hide');     // dismiss the dialog
  	        });
  	    });
  		$("#modalFoto").on("hide", function() {    // remove the event listeners when the dialog is dismissed
  	        $("#modalFoto a.btn").off("click");
  	    });
  	    
  	    $("#modalFoto").on("hidden", function() {  // remove the actual elements from the DOM when fully hidden
  	    	$("#modalFoto").remove();
  	    });
  	    $(".custom-close").on('click', function() {
  	    	cropperDestroy($(imagem));
  	        $("#modalFoto").modal('hide');
  	    });
  	    $("#modalFoto").modal({                    // wire up the actual modal functionality and show the dialog
  	      "backdrop"  : "static",
  	      "keyboard"  : true,
  	      "show"      : true                     // ensure the modal is shown immediately
  	    });
  	    cropperImagem(imagem);
  	    $('#crop-foto').on('click', function(){
          	cropperDestroy($(imagem));
  	        $("#modalFoto").modal('hide');
          });
  	};
  	function readURL(input) {
          if (input.files && input.files[0]) {
              var reader = new FileReader();
              reader.onload = function (e) {
              	modal(e.target.result);
              }
              reader.readAsDataURL(input.files[0]);
          }
      }
      $("#anexarFoto").change(function () {
          readURL(this);
      });
      $('#crop-foto').on('click', function(){
      	var base64 = $("#fotoOriginal").cropper('getCroppedCanvas').toDataURL('image/jpeg');
      	$(fotoPreview).attr('src', base64);
      	$('#foto').attr('value', base64);
      });
});
