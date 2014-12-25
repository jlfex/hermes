<div id="imageList${labelId}" class="image">
<input id="modelId" value="${labelId}" type="hidden"/>
<#list images as image>
	<div class="col-sm-6 col-md-2">
		<div class="thumbnail">
			<img alt="" src="${image.image}" style="width: 140px; height: 140px;" id="${image.id}" class="smImg"/>
		</div>
	</div>
</#list>

	<!-- Modal -->
	<div class="modal fade" id="${labelId}" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content">
	      <div class="modal-body">
			<div id="carousel-example-generic${labelId}" class="carousel slide" >
			  <!-- Wrapper for slides -->
			  <div class="carousel-inner">
			    	<#list images as image>
			    		<div id="${image.id}" class="item">
			     			 <img alt="" src="${image.image}" class="bigImg"/>
			     		</div>
			      	</#list>
			  </div>
			
			  <!-- Controls -->
			  <a class="left carousel-control" href="#carousel-example-generic${labelId}" data-slide="prev">
			    <span class="glyphicon glyphicon-chevron-left"></span>
			  </a>
			  <a class="right carousel-control" href="#carousel-example-generic${labelId}" data-slide="next">
			    <span class="glyphicon glyphicon-chevron-right"></span>
			  </a>
			</div>
	      </div>
	    </div><!-- /.modal-content -->
	  </div><!-- /.modal-dialog -->
	</div><!-- /.modal -->
</div>
<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	$("#imageList${labelId} .col-sm-6 .smImg").on('click',function(){
		$(".item").removeClass("active");
		$(this).closest(".image").find('.modal').modal('show');
		$('#'+this.id+'.item').addClass("active");
	});
});
//-->
</script>