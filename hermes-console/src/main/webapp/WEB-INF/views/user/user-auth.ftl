<div class="panel-group" id="accordion">
	<input id="${id}" name="id" type="hidden" value="${id}"/>
	<#list labels as label>
		<div class="panel panel-default">
			<div class="panel-heading clearfix">
		      <h5 class="panel-title pull-left">${label.name}</h5>
		      <input id="label${label.id}" name="label" type="hidden" value="${label.id}" class="label"/>
		        <div class="pull-right">
		        	<a href="#" data-toggle="collapse" data-parent="#accordion" data-target="#collapse${label_index}" >
		       			 <i class="fa fa-angle-double-up" style="font-size:1.5em;"></i>
		       		 </a>
		        </div>
		    </div>
		      <div id="collapse${label_index}" class="panel-collapse collapse">
			      <div class="panel-body">
				      <div class="row">
				      </div>
			      </div>
		    </div>
		</div>
	</#list>
</div>


<script type="text/javascript" charset="utf-8">
<!--
jQuery(function($) {
	
	$('#accordion .panel-heading .fa').on('click', function() {
		if ($(this).hasClass('fa-angle-double-down')) {
			$(this).removeClass('fa-angle-double-down').addClass('fa-angle-double-up');
		} else {
			$(this).removeClass('fa-angle-double-up').addClass('fa-angle-double-down');
			var labelId=$(this).closest('.panel').find('.label').val();
			var id=$('#id').val();
			var content=$(this).closest('.panel').find('.row');
			$.ajax({
			     url: "${app}/user/loadPicture/"+id+"/"+labelId,
			     success:function(data) {
			       	content.html(data); // 内容装入div中
				 }
	  		 });
		
		}
	});
});
//-->
</script>