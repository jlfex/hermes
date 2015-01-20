<table id="table" class="table table-striped table-hover">
	<thead>
		<tr>
				<th >债权人名称</th>
				<th >债权人编号</th>
				<th >手机号</th>
				<th >邮箱</th>
				<th >证件号码</th>
				<th >联系人</th>
				<th >担保方式</th>
				<th >来源</th>
				<th >简介</th>
				<th >操作</th>
		</tr>
	</thead>
		<#if lists.numberOfElements == 0>
		<tr>
			<td colspan="6" class="align-center"><@messages key="common.table.empty" /></td>
		</tr>
		<#else>
		<#list lists.content as l>
			<tr>
			    <td>${l.creditorName!''}</td>
				<td>${l.creditorNo!''}</td>
				<td >${l.cellphone!''}</td>
				<td >${l.mail!''}</td>
				<td >${l.certificateNo!''}</td>
				<td>${l.contacter!''}</td>
				<td>${l.assoureType!''}</td>
				<td>${l.source!''}</td>
				<td>${l.remark!''}</td>
				<td>
					<a href="#" data-url="${app}/credit/detail/${l.id}" data-target="main">查看</a>
				</td>
			</tr>
		</#list>
		</#if>
	<tbody>
</table>

<ul class="pagination" data-number="${(lists.number)!''}" data-total-pages="${(lists.totalPages)!''}"></ul>

<script type="text/javascript">
<!--
jQuery(function($) {
	$('#table a').link();
	$('.pagination').pagination({
		handler: function(elem) {
			$('#page').val(elem.data().page);
			$('#searchForm').trigger('submit');
		}
	});
});
//-->
</script>