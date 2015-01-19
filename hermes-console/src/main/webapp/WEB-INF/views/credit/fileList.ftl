   <div class="panel panel-primary">
        <div class="panel-heading">发售债权</div>
          <div class="panel-body">
            <form id="searchForm" method="post" action="#">
                <div class="row">
                    <div class="col-xs-2 hm-col form-group">
                        <label for="account">文件名称</label>
                        <input id="cellphone" name="" value="" class="form-control" type="text">
                    </div>
                    <div class="col-xs-2 hm-col form-group">
                        <label for="beginDate">导入日期</label>
                        <input readonly="" id="beginDate" name="beginDate" value="2015-01-06" class="form-control hasDatepicker" type="text">
                    </div>
                    <div class="col-xs-2 hm-col form-group">
                        <label for="endDate">&nbsp;</label>
                        <input readonly="" id="endDate" name="endDate" value="2015-01-06" class="form-control hasDatepicker" type="text">
                    </div>

                    <div class="col-xs-1 hm-col form-group">
                        <label>&nbsp;</label>
                        <button id="searchBtn" type="button" class="btn btn-primary btn-block">查询</button>
                        <input id="page" name="page" value="0" type="hidden">
                    </div>
                </div>
 
            </form>
        </div>

        <div id="data" style="display:block">
            <table class="table table-striped table-hover">
                <thead>
                    <tr>
                        <th class="align-center">序号</th>
                        <th class="align-center">文件名称</th>
                        <th class="align-center">文件导入结果</th>
                        <th class="align-center">导入日期</th>
                        <th class="align-center">操作人</th>
                        <th class="align-center">操作</th>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <td class="align-center">1</td> 
                        <td class="align-center">某文件.xlsx</td> 
                        <td class="align-center">成功</td> 
                        <td class="align-center">2014-10-31  12:28:23</td> 
                        <td class="align-center">admin</td> 
                        <td class="align-center">
                            <button type="button" class="btn btn-link">查看明细</button>
                        </td>    
                    </tr>

                    <tr>
                        <td class="align-center">1</td> 
                        <td class="align-center">某文件.xlsx</td> 
                        <td class="align-center">成功</td> 
                        <td class="align-center">2014-10-31  12:28:23</td> 
                        <td class="align-center">admin</td> 
                        <td class="align-center">
                            <button type="button" class="btn btn-link">查看明细</button>
                        </td>    
                    </tr>

                </tbody>
            </table>
            <div class="pull-right">
                <ul class="pagination" data-number="0" data-total-pages="4"><li class="active"><a href="#">1</a></li><li><a href="#" data-page="1">2</a></li><li><a href="#" data-page="2">3</a></li><li><a href="#" data-page="3">4</a></li></ul>
            </div>
        </div>
    </div>
    
    
    
    
    
    <!-- Modal -->
    <div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">取消</span></button>
            <h4 class="modal-title" id="myModalLabel">发售债权</h4>
          </div>
          <div class="modal-body">
                <form class="form-horizontal" role="form">
                  <div class="form-group">
                    <label for="inputName" class="col-sm-3 control-label"><span class="color_red">*</span>产品名称</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="inputName" placeholder="系统默认为借款用途，用户可修改">
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="inputName" class="col-sm-3 control-label"><span class="color_red">*</span>发售价格</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="inputName" placeholder="系统取当前时间所在期的剩余本金，不允许修改">
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="inputName" class="col-sm-3 control-label"><span class="color_red">*</span>年利率</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="inputName" placeholder="系统写入原始债权年利率">
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
                      <input type="text" class="form-control" id="inputName" placeholder="系统计算，=债权到期日-招标结束日期">
                    </div>
                    <div class="col-sm-1"><span class="vlight">天</span></div>
                  </div>

                  <div class="form-group">
                    <label for="inputName" class="col-sm-3 control-label">担保方式</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="inputName" placeholder="">
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="inputName" class="col-sm-3 control-label">资金用途</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="inputName" placeholder="">
                    </div>
                  </div>

                  <div class="form-group">
                    <label for="inputName" class="col-sm-3 control-label">产品介绍</label>
                    <div class="col-sm-8">
                      <input type="text" class="form-control" id="inputName" placeholder="">
                    </div>
                  </div>

                </form>   
          </div>
          <div class="modal-footer">
            <div class="row">
                <div class="col-sm-offset-2 col-sm-10">
                    <div class="col-xs-3"><button type="submit" class="btn btn-primary btn-block">确定</button></div>
                    <div class="col-xs-3"><button type="submit" class="btn btn-default btn-block"  data-dismiss="modal">取消</button></div>
                </div>
            </div>
          </div>
        </div>
      </div>

