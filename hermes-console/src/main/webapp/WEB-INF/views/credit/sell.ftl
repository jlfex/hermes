   <div class="panel panel-primary">
        <div class="panel-heading">发售债权</div>
          <div class="panel-body">
                <form class="form-horizontal" id="creditorSellForm" >
                  <div class="form-group">
                    <label for="inputName" class="col-sm-3 control-label"><span class="color_red">*</span>产品名称</label>
                    <div class="col-sm-8">
                      <input type="text" name="purpose" class="form-control" id="purpose" placeholder="系统默认为借款用途，用户可修改" value="${creditInfo.purpose!''}">
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="inputName" class="col-sm-3 control-label"><span class="color_red">*</span>发售价格</label>
                    <div class="col-sm-8">
                      <input type="text" name="amount" class="form-control" id="amount" placeholder="系统取当前时间所在期的剩余本金，不允许修改" value="${remainAmount!''}">
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="inputName" class="col-sm-3 control-label"><span class="color_red">*</span>年利率</label>
                    <div class="col-sm-8">
                      <input type="text" name="rate" class="form-control" id="rate" placeholder="系统写入原始债权年利率" value="${(creditInfo.rate)!''}">
                    </div>
                    <div class="col-sm-1"><span class="vlight">%</span></div>
                  </div>

                  <div class="form-group">
                    <label for="inputName" class="col-sm-3 control-label"><span class="color_red">*</span>招标结束日期</label>
                    <div class="col-sm-8">
                        <input readonly="" id="beginDate" name="beginDate" value="2015-01-06" class="form-control hasDatepicker" type="text">
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="inputName" class="col-sm-3 control-label"><span class="color_red">*</span>剩余期限</label>
                    <div class="col-sm-8">
                      <input type="text" name="period" class="form-control" id="period" placeholder="系统计算，=债权到期日-招标结束日期">
                    </div>
                    <div class="col-sm-1"><span class="vlight">天</span></div>
                  </div>

                  <div class="form-group">
                    <label for="inputName" class="col-sm-3 control-label">担保方式</label>
                    <div class="col-sm-8">
                      <input type="text" name="assureType" class="form-control" id="assureType" placeholder="">
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="inputName" class="col-sm-3 control-label">资金用途</label>
                    <div class="col-sm-8">
                      <input type="text" name="amountAim" class="form-control" id="amountAim" placeholder="">
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="inputName" class="col-sm-3 control-label">产品介绍</label>
                    <div class="col-sm-8">
                      <input type="text" name="produnctDesc" class="form-control" id="produnctDesc" placeholder="">
                    </div>
                  </div>

                </form>   
          </div>
     
            <div class="row">
                <div class="col-sm-offset-2 col-sm-10">
                    <div class="col-xs-3"><button type="submit" id="commitSell" class="btn btn-primary btn-block">确定</button></div>
                    <div class="col-xs-3"><button type="submit" id="cancel" class="btn btn-default btn-block"  data-dismiss="modal">取消</button></div>
                </div>
         
        </div>
      </div>
    </div> 

<script type="text/javascript">
<!--
jQuery(function($) {
	
});
//-->
</script>

