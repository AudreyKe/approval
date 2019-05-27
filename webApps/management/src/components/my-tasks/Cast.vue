<template>
  <div class="product_layout">
    <Row class="t-c product_name">
            {{datainfo.proName}}项目简介表
    </Row>
    <Row class="product_team">
      <p class="f-l">
          项目团队：
          <span v-if="isDetail" style="color:#313131" v-cloak>{{team}}</span>
          <Select v-model="datainfo.teamList" filterable multiple remote style="width:285px" v-else>
              <Option v-for="item in label" :value="item.userName" :key="item.value" :label="item.fullName">
                <span>{{item.fullName}}</span>
                <span style="float:right;color:#ccc">{{item.userName}}</span>
              </Option>
          </Select>
      </p>
      <p class="f-r">
          日期：<span>{{baseInfo.date}}</span>
      </p>
    </Row>
    <Row class="tab_name">
        项目基本信息
    </Row>
    <Row style="margin-top:15px">
        <table class="product_info">
          <tbody>
            <tr>
                <td class="info_key">项目名称</td>
                <td colspan="6">
                    <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proName}}</div>
                    <input type="text" v-model="datainfo.proName" v-else>
                </td>
            </tr>
            <tr>
              <td class="info_key">投资标的</td>
              <td colspan="3">
                  <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.investmentTarget}}</div>
                  <Select filterable class="btnselect" v-model="datainfo.investmentTarget" v-else @on-change="changeList">
                  <Option v-for="(item,index) in InvestmentTargetList" :value="item.value" :key="index" :label="item.label" >
                      <span>{{item.label}}</span>
                  </Option>
              </Select>
              </td>
                    <td class="info_key">投资目的</td>
                    <td colspan="3">
                        <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.investmentPurpose}}</div>
                        <Select  filterable  class="btnselect" v-model="datainfo.investmentPurpose" v-else>
                        <Option v-for="(item,index) in InvestmentPurpose" :value="item.value" :key="index" :label="item.label" >
                            <span>{{item.label}}</span>
                        </Option>
                    </Select>
                    </td>
              </tr>
            <tr>
                  <td class="info_key">资产服务机构</td>
                  <td colspan="6">
                      <div class="t-l txt"  v-if="isDetail">{{datainfo.assetSerAgency}}</div>
                      <Select  filterable  class="btnselect" v-model="datainfo.assetSerAgency" v-else>
                        <Option value=" "><div @click="addPepole">新增合作方</div></Option>
                        <Option v-for="(item,index) in PartnersArr" :value="item.usename" :key="index" :label="item.usename" >
                            <span>{{item.usename}}</span>
                        </Option>
                    </Select>
                  </td>
              </tr>

              <tr>
              <td class="info_key">劣后投资人</td>
              <td colspan="3">
                  <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.inferiorInvestor}}</div>
                  <input type="text" v-model="datainfo.inferiorInvestor" v-else>
              </td>
              <td class="info_key">差额补足机构</td>
              <td colspan="3">
                  <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.diffAgency}}</div>
                  <input type="text" v-model="datainfo.diffAgency" v-else>
              </td>
              </tr>
              <tr>
              <td class="info_key">投资金额(亿)</td>
              <td colspan="1">
                  <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.investmentAmount}}</div>
                  <input type="number" v-model="datainfo.investmentAmount" @mousewheel.prevent v-else>
              </td>
              <td class="info_key">发行金额(亿)</td>
              <td colspan="1">
                  <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.totalAmount}}</div>
                  <input type="number" v-model="datainfo.totalAmount" @mousewheel.prevent v-else>
              </td>
              <td class="info_key">杠杆倍数</td>
              <td colspan="2">
                  <div class="t-l txt" style="color:#313131">{{leverage}}</div>
              </td>
              </tr>
               <tr>
              <td class="info_key">12个月内预计<br>合作规模(亿)</td>
              <td colspan="1">
                  <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.coopScale}}</div>
                  <input type="text" v-model="datainfo.coopScale" v-else>
              </td>
              <td class="info_key">已合作规模(亿)</td>
              <td colspan="1">
                  <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.scaleCooperation}}</div>
                  <input type="text" v-model="datainfo.scaleCooperation" v-else>
              </td>
              <td class="info_key">存量余额(亿)</td>
              <td colspan="2">
                  <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.stockBalance}}</div>
                  <input type="text" v-model="datainfo.stockBalance" v-else>
              </td>
              </tr>
              <tr>
                  <td class="info_key">投资特点</td>
                  <td colspan="3">
                      <template v-if="isDetail">
                          <div class="t-l txt" >{{datainfo.investmentCharacteristics}}</div>
                      </template>
                      <CheckboxGroup v-model="datainfo.investmentCharacteristics" v-else>
                          <Checkbox label="可循环性">可循环性</Checkbox>
                          <Checkbox label="可复制性">可复制性</Checkbox>
                      </CheckboxGroup>
                  </td>
                  <td class="info_key">退出方式</td>
                  <td colspan="3">
                      <template v-if="isDetail">
                          <div class="t-l txt" >{{datainfo.exitMode}}</div>
                      </template>
                      <RadioGroup v-model="datainfo.exitMode" v-else>
                      <Radio label="持有至到期退出">
                          <Icon type="social-apple"></Icon>
                          <span>持有至到期退出</span>
                      </Radio>
                      <Radio label="转让退出">
                          <Icon type="social-android"></Icon>
                          <span>转让退出</span>
                      </Radio>
                  </RadioGroup>
                  </td>
              </tr>
              <tr>
            <td class="info_key">是否同意框架合作</td>
            <td colspan="3">
              <template v-if="isDetail">
                <div class="t-l txt">{{datainfo.coopState}}</div>
              </template>
              <RadioGroup v-model="datainfo.coopState" v-else>
                <Radio label="是"></Radio>
                <Radio label="否"></Radio>
              </RadioGroup>
            </td>
            <td class="info_key">是否同意系统对接</td>
            <td colspan="3">
              <template v-if="isDetail">
                <div class="t-l txt">{{datainfo.systemAbutmentState}}</div>
              </template>
              <RadioGroup v-model="datainfo.systemAbutmentState" v-else>
                <Radio label="是"></Radio>
                <Radio label="否"></Radio>
              </RadioGroup>
            </td>
          </tr>
          <tr>
              <td class="info_key">原始权益人</td>
              <td colspan="3">
                  <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.originalRightsHolder}}</div>
                  <input type="text" v-model="datainfo.originalRightsHolder" v-else>
              </td>
              <td class="info_key">评级情况</td>
              <td colspan="3">
                  <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.holderRatingSituation}}</div>
                  <input type="text" v-model="datainfo.holderRatingSituation" v-else>
              </td>
              </tr>
              <tr>
              <td class="info_key">外部增信机构</td>
              <td colspan="3">
                  <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.externalEnhancementAgencies}}</div>
                  <input type="text" v-model="datainfo.externalEnhancementAgencies" v-else>
              </td>
              <td class="info_key">评级情况</td>
              <td colspan="3">
                  <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.ratingSituation}}</div>
                  <input type="text" v-model="datainfo.ratingSituation" v-else>
              </td>
              </tr>
               <tr>
                    <td class="info_key" :rowspan="LengthRow" >分层结构</td>
                    <td class="info_key">分级</td>
                    <td class="info_key">发行金额(万元)</td>
                    <td class="info_key">占比(%)</td>
                    <td class="info_key">发行利率(%)</td>
                    <td class="info_key">各级期限(月)</td>
                    <td class="info_key">加权平均期限(年)</td>
                </tr>
                  <template v-if="isDetail">
                  <tr v-for="(item,index) in datainfo.layerList" :key="index">
                      <td>
                          <input type="text" v-model="item.level" :readonly="isDetail">
                      </td>
                      <td>
                          <input type="Number" v-model="item.amount" @mousewheel.prevent :readonly="isDetail">
                      </td>
                      <td>
                          <input type="Number" v-model="item.proportion" @mousewheel.prevent :readonly="isDetail">
                      </td>
                      <td>
                          <input type="text" v-model="item.issueRate" :readonly="isDetail">
                      </td>
                      <td>
                          <input type="text" v-model="item.proTerm" :readonly="isDetail">
                      </td>
                      <td>
                          <input type="text" v-model="item.averageTerm" :readonly="isDetail">
                      </td>
                  </tr>
              </template>
                 <template v-else>
                  <tr v-for="item in layerList">
                      <td>
                          <input type="text" v-model="item.level" :readonly="isDetail">
                      </td>
                      <td>
                          <input type="Number" v-model="item.amount" @mousewheel.prevent :readonly="isDetail">
                      </td>
                      <td>
                          <input type="Number" v-model="item.proportion" @mousewheel.prevent :readonly="isDetail">
                      </td>
                      <td>
                          <input type="tel" v-model="item.issueRate" :readonly="isDetail">
                      </td>
                      <td>
                          <input type="text" v-model="item.proTerm" :readonly="isDetail">
                      </td>
                      <td>
                          <input type="tel" v-model="item.averageTerm" :readonly="isDetail">
                      </td>
                  </tr>
                </template>
                <tr>
                  <td colspan="6" v-show="!isDetail">
                  <div class="addImg" ref="add" @click="AddDom"><img src="@/assets/images/round_add.png" alt></div>
                  <div class="reduce" ref="reduce" @click="reduce"><img src="@/assets/images/round_reduce.png" alt></div>
                  </td>
                </tr>
                <tr v-if="Target">
                    <td class="info_key" :rowspan="7" >交易结构<br>（Pre-<br>ABS项目）</td>
                </tr>
                 <template v-if="Target">
                  <tr>
                    <td class="info_key" style="padding: 17px;">底层资产类型</td>
                    <td colspan="6">
                        <template v-if="isDetail">
                          <div class="t-l txt" >{{datainfo.proInfoTradeStructure.underlyingAssetsType}}</div>
                      </template>
                      <CheckboxGroup v-model="datainfo.proInfoTradeStructure.underlyingAssetsType" v-else>
                          <Checkbox label="车分期">车分期</Checkbox>
                          <Checkbox label="车抵贷">车抵贷</Checkbox>
                          <Checkbox label="房抵贷">房抵贷</Checkbox>
                          <Checkbox label="消费贷">消费贷</Checkbox>
                          <Checkbox label="个人经营贷">个人经营贷</Checkbox>
                          <Checkbox label="小微企业">小微企业</Checkbox>
                      </CheckboxGroup>
                    </td>
                  </tr>
                  <tr>
                    <td class="info_key" style="padding: 25px 0;">底层资产投放标准</td>
                    <td :colspan="this.Showcase.underlyingL" v-show="Showcase.underlyingLShow">
                     <div style="position: relative;">
                        <div style="float: right;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassingunderlying" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.proInfoTradeStructure.underlyingAssetsStandardFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style="width: 200px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;">{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileList(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
                  <td :colspan="this.Showcase.underlyingR" v-show="Showcase.underlyingRShow">
                      <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proInfoTradeStructure.underlyingAssetsStandard}}</div>
                      <textarea style="border-top: 1px solid #ccc;" v-model="datainfo.proInfoTradeStructure.underlyingAssetsStandard" v-else></textarea>
                  </td>

                  <tr>
                    <td class="info_key">各方出资金额及占比</td>
                    <td :colspan="this.Showcase.ratioL" v-show="Showcase.ratioLShow">
                     <div style="position: relative;">
                        <div style="float: right;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassingratio" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.proInfoTradeStructure.ratioAmountFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style="width: 200px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;">{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileListratioAmount(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
                  <td :colspan="this.Showcase.ratioR" v-show="Showcase.ratioRShow">
                      <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proInfoTradeStructure.ratioAmount}}</div>
                      <textarea style="border-top: 1px solid #ccc;" v-model="datainfo.proInfoTradeStructure.ratioAmount" v-else></textarea>
                  </td>
                  </tr>
                  <tr>
                    <td class="info_key">各方出资安排</td>
                    <td :colspan="this.Showcase.fundArrL" v-show="Showcase.fundArrLShow">
                     <div style="position: relative;">
                        <div style="float: right;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassingfund" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.proInfoTradeStructure.fundArrangementsFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style="width: 200px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;">{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileListfund(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
                  <td :colspan="this.Showcase.fundArrR" v-show="Showcase.fundArrRShow">
                      <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proInfoTradeStructure.fundArrangements}}</div>
                      <textarea style="border-top: 1px solid #ccc;" v-model="datainfo.proInfoTradeStructure.fundArrangements" v-else></textarea>
                  </td>
                  </tr>
                  <tr>
                    <td class="info_key">投资期限</td>
                    <td :colspan="this.Showcase.stmenL" v-show="Showcase.termInvLShow">
                     <div style="position: relative;">
                        <div style="float: right;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassingtermInvest" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.proInfoTradeStructure.termInvestmentFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style="width: 200px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;">{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileListterm(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
                  <td :colspan="this.Showcase.stmenR" v-show="Showcase.termInvRShow">
                      <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proInfoTradeStructure.termInvestment}}</div>
                      <textarea style="border-top: 1px solid #ccc;" v-model="datainfo.proInfoTradeStructure.termInvestment" v-else></textarea>
                  </td>
                  </tr>
                  <tr>
                    <td class="info_key">投资利率</td>
                    <td :colspan="this.Showcase.stmenL" v-show="Showcase.stmenLShow">
                     <div style="position: relative;">
                        <div style="float: right;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassingrateInvestment" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.proInfoTradeStructure.rateInvestmentFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style="width: 200px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;">{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileListrateInvestment(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
                  <td :colspan="this.Showcase.stmenR" v-show="Showcase.stmenRShow">
                      <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proInfoTradeStructure.rateInvestment}}</div>
                      <textarea style="border-top: 1px solid #ccc;" v-model="datainfo.proInfoTradeStructure.rateInvestment" v-else></textarea>
                  </td>
                  </tr>
                </template>
                <tr v-if="Interlayer">
                    <td class="info_key" :rowspan="9" >交易结构<br>（ABS优先、<br>夹层和劣后）</td>
                </tr>
                 <template v-if="Interlayer">
                  <tr>
                    <td class="info_key">ABS发行金<br>额、分层占比、及<br>分层评级结果</td>
                   <td :colspan="this.Showcase.ratingL" v-show="Showcase.ratingLShow">
                     <div style="position: relative;">
                        <div style="float: right;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassingratingResult" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.proInfoTradeStructure.ratingResultFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style="width: 200px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;">{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileListratingResult(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
                  <td :colspan="this.Showcase.ratingR" v-show="Showcase.ratingRShow">
                      <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proInfoTradeStructure.ratingResult}}</div>
                      <textarea style="border-top: 1px solid #ccc;" v-model="datainfo.proInfoTradeStructure.ratingResult" v-else></textarea>
                  </td>
                  </tr>
                  <tr>
                    <td class="info_key">原始权益人<br>出表与否</td>
                    <td :colspan="this.Showcase.isOriginaL" v-show="Showcase.isOriginaLShow">
                     <div style="position: relative;">
                        <div style="float: right;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassingisOriginal" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.proInfoTradeStructure.isOriginalListFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style="width: 200px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;">{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileListisOriginalarr(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
                  <td :colspan="this.Showcase.isOriginaR" v-show="Showcase.isOriginaRShow">
                      <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proInfoTradeStructure.isOriginalList}}</div>
                      <textarea style="border-top: 1px solid #ccc;" v-model="datainfo.proInfoTradeStructure.isOriginalList" v-else></textarea>
                  </td>
                  </tr>
                  <tr>
                    <td class="info_key">底层资产<br>投放标准</td>
                    <td :colspan="this.Showcase.underlyingL" v-show="Showcase.underlyingLShow">
                     <div style="position: relative;">
                        <div style="float: right;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassingunderlying" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.proInfoTradeStructure.underlyingAssetsStandardFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style="width: 200px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;">{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileList(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
                  <td :colspan="this.Showcase.underlyingR" v-show="Showcase.underlyingRShow">
                      <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proInfoTradeStructure.underlyingAssetsStandard}}</div>
                      <textarea style="border-top: 1px solid #ccc;" v-model="datainfo.proInfoTradeStructure.underlyingAssetsStandard" v-else></textarea>
                  </td>
                  </tr>
                  <tr>
                    <td class="info_key">投资期限</td>
                    <td :colspan="this.Showcase.termInvL" v-show="Showcase.termInvLShow">
                     <div style="position: relative;">
                        <div style="float: right;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassingtermInvest" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.proInfoTradeStructure.termInvestmentFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style="width: 200px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;">{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileListterm(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
                  <td :colspan="this.Showcase.termInvR" v-show="Showcase.termInvRShow">
                      <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proInfoTradeStructure.termInvestment}}</div>
                      <textarea style="border-top: 1px solid #ccc;" v-model="datainfo.proInfoTradeStructure.termInvestment" v-else></textarea>
                  </td>
                  </tr>
                  <tr>
                    <td class="info_key">投资利率</td>
                    <td :colspan="this.Showcase.stmenL" v-show="Showcase.stmenLShow">
                     <div style="position: relative;">
                        <div style="float: right;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassingrateInvestment" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.proInfoTradeStructure.rateInvestmentFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style="width: 200px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;">{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileListrateInvestment(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
                  <td :colspan="this.Showcase.stmenR" v-show="Showcase.stmenRShow">
                      <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proInfoTradeStructure.rateInvestment}}</div>
                      <textarea style="border-top: 1px solid #ccc;" v-model="datainfo.proInfoTradeStructure.rateInvestment" v-else></textarea>
                  </td>
                  </tr>
                  <tr>
                    <td class="info_key">原始权益人是否<br>履行回购义务</td>
                    <td :colspan="this.Showcase.isOriginalL" v-show="Showcase.isOriginalLShow">
                     <div style="position: relative;">
                        <div style="float: right;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassingisOriginalBuyback" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.proInfoTradeStructure.isOriginalBuybackFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style="width: 200px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;">{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileListisOriginal(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
                  <td :colspan="this.Showcase.isOriginalR" v-show="Showcase.isOriginalRShow">
                      <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proInfoTradeStructure.isOriginalBuyback}}</div>
                      <textarea style="border-top: 1px solid #ccc;" v-model="datainfo.proInfoTradeStructure.isOriginalBuyback" v-else></textarea>
                  </td>
                  </tr>
                  <tr>
                    <td class="info_key">违约事件阈值<br>及内容设定</td>
                    <td :colspan="this.Showcase.breachL" v-show="Showcase.breachLShow">
                     <div style="position: relative;">
                        <div style="float: right;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassingbreach" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span>
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.proInfoTradeStructure.breachContentFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style="width: 200px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;">{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileListContent(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
                  <td :colspan="this.Showcase.breachR" v-show="Showcase.breachRShow">
                      <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proInfoTradeStructure.breachContent}}</div>
                      <textarea style="border-top: 1px solid #ccc;" v-model="datainfo.proInfoTradeStructure.breachContent" v-else></textarea>
                  </td>
                  </tr>
                  <tr>
                    <td class="info_key">违约事件发生前<br>和发生后分配<br>顺序的比较</td>
                    <td :colspan="this.Showcase.breachCompareL" v-show="Showcase.breachCompareLShow">
                     <div style="position: relative;">
                        <div style="float: right;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassingbreachCompare" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.proInfoTradeStructure.breachCompareFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style="width: 200px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;">{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileListbreach(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
                  <td :colspan="this.Showcase.breachCompareR" v-show="Showcase.breachCompareRShow">
                      <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proInfoTradeStructure.breachCompare}}</div>
                      <textarea style="border-top: 1px solid #ccc;" v-model="datainfo.proInfoTradeStructure.breachCompare" v-else></textarea>
                  </td>
                  </tr>
                </template>
                <tr v-if="seed">
                    <td class="info_key" :rowspan="6" >交易结构<br>（种子基金项目）</td>
                </tr>
                 <template v-if="seed">
                  <tr>
                    <td class="info_key" style="padding: 17px;">基金总金额</td>
                    <td :colspan="this.Showcase.totalFundL" v-show="Showcase.totalFundLShow">
                     <div style="position: relative;">
                        <div style="float: right;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassingtotalFundAmount" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.proInfoTradeStructure.totalFundAmountFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style="width: 200px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;">{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileListtotal(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
                  <td :colspan="this.Showcase.totalFundR" v-show="Showcase.totalFundRShow">
                      <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proInfoTradeStructure.totalFundAmount}}</div>
                      <textarea style="border-top: 1px solid #ccc;" v-model="datainfo.proInfoTradeStructure.totalFundAmount" v-else></textarea>
                  </td>
                  </tr>
                  <tr>
                    <td class="info_key">种子基金占比</td>
                    <td :colspan="this.Showcase.seedFundL" v-show="Showcase.seedFundLShow">
                     <div style="position: relative;">
                        <div style="float: right;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassingseedFundProport" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.proInfoTradeStructure.seedFundProportionFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style="width: 200px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;">{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileListseed(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
                  <td :colspan="this.Showcase.seedFundR" v-show="Showcase.seedFundRShow">
                      <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proInfoTradeStructure.seedFundProportion}}</div>
                      <textarea style="border-top: 1px solid #ccc;" v-model="datainfo.proInfoTradeStructure.seedFundProportion" v-else></textarea>
                  </td>
                  </tr>
                  <tr>
                    <td class="info_key">预计收取的基金管理费</td>
                    <td :colspan="this.Showcase.estimatedL" v-show="Showcase.estimatedLShow">
                     <div style="position: relative;">
                        <div style="float: right;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassingestimatedManagement" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.proInfoTradeStructure.estimatedManagementFeeFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style="width: 200px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;">{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileListestimated(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
                  <td :colspan="this.Showcase.estimatedR" v-show="Showcase.estimatedRShow">
                      <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proInfoTradeStructure.estimatedManagementFee}}</div>
                      <textarea style="border-top: 1px solid #ccc;" v-model="datainfo.proInfoTradeStructure.estimatedManagementFee" v-else></textarea>
                  </td>
                  </tr>
                  <tr>
                    <td class="info_key">种子基金投资范围或产品</td>
                    <td :colspan="this.Showcase.scopeL" v-show="Showcase.scopeLShow">
                     <div style="position: relative;">
                        <div style="float: right;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassingscopeInvestment" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.proInfoTradeStructure.scopeInvestmentFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style="width: 200px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;">{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileListscope(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
                  <td :colspan="this.Showcase.scopeR" v-show="Showcase.scopeRShow">
                      <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proInfoTradeStructure.scopeInvestment}}</div>
                      <textarea style="border-top: 1px solid #ccc;" v-model="datainfo.proInfoTradeStructure.scopeInvestment" v-else></textarea>
                  </td>
                  </tr>
                  <tr>
                    <td class="info_key">基金风控和投资风险安排</td>
                    <td :colspan="this.Showcase.riskL" v-show="Showcase.riskLShow">
                     <div style="position: relative;">
                        <div style="float: right;" v-show="!isDetail">
                        <template>
                            <Upload :before-upload="handleUploadPassingriskArrangement" action="" :format="['pdf']">
                                <Button icon="ios-cloud-upload-outline">上传</Button>
                            </Upload>
                        </template>
                            <span style="color:red;">*仅支持PDF格式文件</span> 
                      </div>
                      <div style="float:left;text-align: left;margin-left: 15px;">
                         <div class="file_item" style="margin-left:0px;" v-for="(item,index) in datainfo.proInfoTradeStructure.riskArrangementFileList" :key="index">
                            <i class="iconfont icon-file_pdf"></i>
                            <template v-if="!isDetail">
                                <span style="width: 200px;overflow: hidden;white-space: nowrap;text-overflow: ellipsis;display: inline-block;">{{item.fileName}}</span>
                                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFileListrisk(index)"></i>
                            </template>
                            <template v-else>
                                <a @click="displayPdf(item)">{{item.fileName}}</a>
                            </template>
                        </div>
                        </div>
                     </div>
                  </td>
                  <td :colspan="this.Showcase.riskR" v-show="Showcase.riskRShow">
                      <div class="t-l txt" v-if="isDetail" style="color:#313131">{{datainfo.proInfoTradeStructure.riskArrangement}}</div>
                      <textarea style="border-top: 1px solid #ccc;" v-model="datainfo.proInfoTradeStructure.riskArrangement" v-else></textarea>
                  </td>
                  </tr>
                </template>
                <tr>
                    <td class="info_key" style="padding: 16px 5px;">
                        综合成本(元)
                    </td>
                    <td colspan="3">
                        <input type="tel" v-model="datainfo.cost" :readonly="isDetail">
                    </td>
                    <td class="info_key">
                        产品期限(年)
                    </td>
                    <td colspan="3">
                        <input type="tel" v-model="datainfo.term" :readonly="isDetail">
                    </td>
                </tr>
                <tr>
                    <td colspan="7">
                      <span v-if="isDetail">{{datainfo.requirements}}<br></span>
                        <RadioGroup v-model="datainfo.requirements" v-else>
                            <Radio label="静态池"></Radio>
                            <Radio label="循环结构"></Radio>
                        </RadioGroup>
                        <span v-show="isshow">循环期【<input type="text" style="width:50px;text-align:center" v-model="datainfo.cyclePeriod" :readonly="isDetail">】+ 摊还期【<input :readonly="isDetail" type="text" style="width:50px;text-align:center" v-model="datainfo.amortizationPeriod">】个月</span>
                    </td>
                </tr>
                <tr>
                    <td class="info_key">分销渠道</td>
                    <td colspan="6">
                        <span v-if="isDetail">{{datainfo.channel}}<br></span>
                        <Select v-model="datainfo.channel" style="width:100%" :readonly="isDetail" v-else class="channel">
                            <Option v-for="item in model8" :value="item.channel" :key="item.channel">{{ item.channel }}</Option>
                        </Select>
                        <span v-if="channel">理财通分【<input type="text" style="width:50px;text-align:center" v-model="datainfo.packageNum" :readonly="isDetail">】包发行，单包规模【<input :readonly="isDetail" type="text" style="width:100px;text-align:center;minWidth:50px;" v-model="datainfo.packageScale" onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');" >】万元</span>
                    </td>
                </tr>
                 <tr>
                        <td class="info_key">项目前端收入</td>
                        <td colspan="3">
                            <textarea name="" id="" rows="5" v-model="datainfo.frontIncome" :readonly="isDetail"></textarea>
                        </td>
                        <td class="info_key">项目后端收入</td>
                        <td colspan="2">
                            <textarea name="" id="" rows="5" v-model="datainfo.afterIncome" :readonly="isDetail"></textarea>
                        </td>
                    </tr>
          </tbody>
      
        </table>
    <div >
     <Row class="tab_name">
            {{isDetail ? '项目附件' : '上传附件'}} :
      </Row>
          <div v-show="!pdfshow">
            <div class="file_item" v-for="(item,index) in datainfo.fileList" :key="index">
            <i class="iconfont icon-file_pdf"></i>
            <template v-if="!isDetail">
                <span style>{{item.fileName}}</span>
                <i class="iconfont icon-shanchu_o" title="删除" @click="removeFile(index)"></i>
            </template>
            <template v-else>
                <a @click="displayPdf(item)">{{item.fileName}}</a>
            </template>
          </div>
        </div>
      <Row style="margin-top:15px" v-show="!isDetail">
         <template>
                <Upload :before-upload="handleUpload" action="" :format="['pdf']">
                    <Button icon="ios-cloud-upload-outline">上传</Button>
                </Upload>
                <span class="upload_tips">*仅支持PDF格式文件</span> 
            </template>
      </Row>

        <Row style="margin:30px 0 0 0;text-align:right" v-show="!detailshow">
                <Button type="info" style="border-radius: 5px;position: relative;left: -80px;" @click="Preservation">暂时保存</Button>
                <Button type="primary" class="margin-right-20;" style="border-radius: 5px;" @click="addAudit">提交申请</Button>
        </Row>
        <Row style="margin:30px 0 0 0;text-align:right" v-show="!isDetail">
                <Button type="primary" class="margin-right-20;" style="border-radius: 5px;" @click="addAudit" v-show="backshow">提交修改</Button>
        </Row>
    </div>
    </Row>
      <div v-show="Approval">
        <Row class="tab_name">审批历史</Row>
        <div class="history_list">
            <template v-if="historyAudit.length > 0">
                <Row class="history_item" v-for="(item,index) in historyAudit" :key="index" :class="item.result== '不同意' ? 'disagree' : ''">
                    <Col span="4" style="height: 24px;width: auto;">审批人：<span style="color:#212121">{{item.operator}}</span></Col>
                    <Col span="4" style="position: absolute;left: 275px;height: 24px;width: auto;">审批结果：
                    <span style="color:#212121;color: red;" v-if="item.resultState == '不同意'">{{item.resultState}}</span>
                    <span style="color:#212121" v-else>{{item.resultState}}</span>
                    </Col><br>
                    <Col span="7" style="width: auto;">审批意见：<span style="color:#212121" v-if="item.result.length !=0">{{item.result}}</span></Col>
                    <Col span="8" offset="1" style="position: absolute;top: 20px;right: 0;">审批日期：<span style="color:#212121">{{item.date}}</span></Col>
                </Row>
            </template>
            <Row v-else class="t-c history_item" >
                暂无历史审批
            </Row>
        </div>
    </div>
      <Row class="margin-top-20" v-show="Examination">
        <table class="product_info">
          <tbody>
            <tr v-show="!ResultData">
              <td class="info_key">审批结果</td>
              <td colspan="6">
                <RadioGroup v-model="auditStatus">
                  <Radio label="0">
                    <span>同意</span>
                  </Radio>
                  <Radio label="-1">
                    <span>不同意</span>
                  </Radio>
                  <Radio label="-2" v-show="Agreed">
                    <span>有条件同意</span>
                  </Radio>
                </RadioGroup>
              </td>
            </tr>
            <tr v-show="Decision">
              <td class="info_key">选择上会人员</td>
              <td colspan="6">
                <span style="display: block;text-align: left;margin-left: 20px;font-size: 12px;">{{fixed}}</span>
                  <CheckboxGroup style="text-align: left;margin-left: 250px;margin-top: -20px;" v-model="applyCategory">
                    <Checkbox v-for="item in optional" :label="item.name" :key="item.index">{{item.value}}</Checkbox>
                </CheckboxGroup>
              </td>
            </tr>
            <tr>
              <td class="info_key">审批意见</td>
              <td colspan="6">
                <textarea rows="5" v-model="auditInfo" style="height: auto;"></textarea>
              </td>
            </tr>
          </tbody>
        </table>
        <Row style="margin:-50px 0 0 0;text-align:right;">
          <Button type="primary" class="margin-right-20 btntijiao" @click="auditFund">提交</Button>
        </Row>
      </Row>

      <!-- 资料审核部门的附件列表 -->
      <div v-show="force">
      <template>
        <Table :columns="Attachmentaudit" :data="AttachmentList"></Table>
      </template>
      <Row style="text-align:right;">
        <Button type="primary" class="margin-right-20 btntijiao" style="margin-top: 85px;" @click="Material">提交</Button>
      </Row>
    </div>
       <!-- 项目附件 -->
        <template v-if="EnclosureShow">
        <Table :columns="Enclosure" :data="dataTableListshow">
          <template slot-scope="{ row }" slot="name">
            <strong>{{ row.name }}</strong>
          </template>
          <template slot-scope="{ row, index }" slot="action">
            <Button type="error" size="small" @click="remove(index)">删除</Button>
          </template>
        </Table>
      </template>
      <div v-show="EnclosureShow">
        <template>
          <Upload
            :before-upload="handleUploadsupplement"
            action
            :format="['pdf']"
            style="position: relative;top: 50px;"
          >
            <Button icon="ios-cloud-upload-outline">上传补充材料</Button>
          </Upload>
        </template>
        <template>
          <Upload
            :before-upload="handleUploadImplement"
            action
            :format="['pdf']"
            style="position: relative;left: 150px;top:2px;"
          >
            <Button icon="ios-cloud-upload-outline">上传落实材料</Button>
          </Upload>
        </template>
        <Row style="text-align:right;">
          <Button
            type="primary"
            class="margin-right-20 btntijiao"
            style="margin-top: 85px;"
            @click="Submit"
          >提交</Button>
        </Row>
      </div>
      </div>
    </Row>
  </div>
</template>

<script>
import axios from 'axios';
import mdContract from './contract.md.vue'
export default {
props: {
  baseInfo: {
      type: Object,
      default: function() {
        return {
          date: new Date().format("yyyy-MM-dd"), //日期
        };
      },
    }
  },
      data(){
        return {
          Conference:[],//上会名单
          fixed:"",//固定名单
          optional:[],//动态名单
          Fixmember:[],//固定委员
          applyCategory:[],
          label:[],
          LengthRow:7,
          pdfshow:false,
          ApproverMberList:[],
          ApproverMber:[],//选择前置审批人员
          force:false,
          Decision:false,
          EnclosureShow:false,
          ResultData:false,
          backshow:false,
          dataTableListshow: [],
          Enclosure: [
        {
          type: "expand",
          width: 50,
          render: (h, params) => {
          if (params.row.backReason == null || params.row.backReason == undefined) {
              return h(
          "div",
          {
            props: {
              row: params.row
            }
          },
          `审批意见:无`
        );
        }else{
          return h(
          "div",
          {
            props: {
              row: params.row
            }
          },
          `审批意见:${params.row.backReason}`
        );
        }
          }
        },
        {
          title: "附件名称",
          key: "fileName",
          width: 200,
          render: (h, params) => {
            return h("div", [
              h(
                "a",
                {
                  style: {
                    width: "115px",
                    height: "20px",
                    display: "block",
                    overflow: "hidden",
                    textOverflow: "ellipsis",
                    whiteSpace: "nowrap"
                  },
                  on: {
                    click: () => {
                      console.log(params.row.id);
                      let index = params.row.id;
                      let url = "/api/snaker/flow/attach/" + index;
                      window.open("/static/pdf/web/viewer.html?file=" + url);
                    }
                  }
                },
                params.row.fileName
              )
            ]);
          }
        },
        {
          title: "附件类型",
          key: "proAttachType",
          render: (h, params) => {
            if (params.row.proAttachType == "00") {
              return h("div", [
                h(
                  "span",
                  {
                    style: {
                      display: "block"
                    }
                  },
                  "项目立项材料"
                )
              ]);
            } else if (params.row.proAttachType == "01") {
              return h("div", [
                h(
                  "span",
                  {
                    style: {
                      display: "block"
                    }
                  },
                  "项目补充材料"
                )
              ]);
            } else if (params.row.proAttachType == "02") {
              return h("div", [
                h(
                  "span",
                  {
                    style: {
                      display: "block"
                    }
                  },
                  "项目落实材料"
                )
              ]);
            }else if(params.row.proAttachType == "03"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "通过率及累计违约率假设材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "04"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "历史数据表现对比材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "06"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "底层资产投放标准材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "07"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "各方出资金额及占比材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "08"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "各方出资安排材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "09"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "投资期限材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "10"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "投资利率材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "11"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "ABS发行金额、分层占比、及分层评级结果材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "12"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "违约事件阈值及内容设定材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "13"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "违约事件发生前和发生后分配顺序的比较材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "14"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "基金总金额材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "15"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "种子基金占比材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "16"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "预计收取的基金管理费材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "17"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "种子基金投资范围或产品材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "18"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "基金风控和投资风险安排材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "19"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "原始权益人出表与否材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "20"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "原始权益人是否履行回购义务材料"
                    )
                  ]);
                } else if(params.row.proAttachType == "05"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "其他限制性条件材料"
                    )
                  ]);
                }
          }
        },
        {
          title: "上传人",
          key: "uploadPersonName"
        },
        {
          title: "上传时间",
          key: "updateTime",
          render:(h, params) => {
          return h('div',[
          h('span', {
              style: {
                display:'block',
                width:"175px",
                marginLeft: '-15px'
              }
          },params.row.updateTime),
          ]);
        }
        },
        {
          title: "附件审批状态",
          key: "attachTaskState",
          render: (h, params) => {
            if (params.row.attachTaskState == "00") {
              return h("div", [
                h(
                  "span",
                  {
                    style: {
                      display: "block"
                    }
                  },
                  "未审核"
                )
              ]);
            } else if (params.row.attachTaskState == "01") {
              return h("div", [
                h(
                  "span",
                  {
                    style: {
                      display: "block"
                    }
                  },
                  "审核通过"
                )
              ]);
            } else if (params.row.attachTaskState == "02") {
              return h("div", [
                h(
                  "span",
                  {
                    style: {
                      display: "block"
                    }
                  },
                  "审核不通过"
                )
              ]);
            }
          }
        },
        {
          title: "操作",
          key: "operation",
          render: (h, params) => {
            if (params.row.attachTaskState != "00") {
              return h("div", [
                h(
                  "Button",
                  {
                    props: {
                      type: "error",
                      size: "small"
                    },
                    style: {
                      borderRadius: "5px",
                      marginLeft: "10px"
                    },
                    on: {
                      click: () => {
                        params.row.optType = "1"
                        this.SubId.push(params.row);
                        this.remove(params.index);
                      }
                    }
                  },
                  "删除"
                )
              ]);
            }
          }
        }
      ],
            Attachmentaudit: [
          {
            type: "expand",
            width: 50,
            render: (h, params) => {
            if (params.row.backReason == null || params.row.backReason == undefined){
            return h(
          "div",
          {
            props: {
              row: params.row
            }
          },
          `审批意见:无`
        );
        }else{
          return h(
          "div",
          {
            props: {
              row: params.row
            }
          },
          `审批意见:${params.row.backReason}`
        );
        }
            }
          },
          {
            title: "附件名称",
            key: "fileName",
            height: 200,
            render: (h, params) => {
              return h("div", [
                h(
                  "a",
                  {
                    style: {
                      width: "115px",
                      height: "20px",
                      display: "block",
                      overflow: "hidden",
                      textOverflow: "ellipsis",
                      whiteSpace: "nowrap"
                    },
                    on: {
                      click: () => {
                        console.log(params.row.id);
                        let index = params.row.id;
                        let url = "/api/snaker/flow/attach/" + index;
                        window.open("/static/pdf/web/viewer.html?file=" + url);
                      }
                    }
                  },
                  params.row.fileName
                )
              ]);
            }
          },
        {
          title: "修改类型",
          key: "optType",
            render: (h, params) => {
            if (params.row.optType == "0") {
              return h("div", [
                h(
                  "span",
                  {
                    style: {
                      display: "block"
                    }
                  },
                  "新增"
                )
              ]);
            } else if (params.row.optType == "1") {
              return h("div", [
                h(
                  "span",
                  {
                    style: {
                      display: "block"
                    }
                  },
                  "删除"
                )
              ]);
            }
          }
        },
          {
            title: "附件类型",
            key: "proAttachType",
            render: (h, params) => {
            if (params.row.proAttachType == "00") {
              return h("div", [
                h(
                  "span",
                  {
                    style: {
                      display: "block"
                    }
                  },
                  "项目立项材料"
                )
              ]);
            } else if (params.row.proAttachType == "01") {
              return h("div", [
                h(
                  "span",
                  {
                    style: {
                      display: "block"
                    }
                  },
                  "项目补充材料"
                )
              ]);
            } else if (params.row.proAttachType == "02") {
              return h("div", [
                h(
                  "span",
                  {
                    style: {
                      display: "block"
                    }
                  },
                  "项目落实材料"
                )
              ]);
            }else if(params.row.proAttachType == "03"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "通过率及累计违约率假设材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "04"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "历史数据表现对比材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "06"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "底层资产投放标准材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "07"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "各方出资金额及占比材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "08"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "各方出资安排材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "09"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "投资期限材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "10"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "投资利率材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "11"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "ABS发行金额、分层占比、及分层评级结果材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "12"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "违约事件阈值及内容设定材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "13"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "违约事件发生前和发生后分配顺序的比较材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "14"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "基金总金额材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "15"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "种子基金占比材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "16"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "预计收取的基金管理费材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "17"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "种子基金投资范围或产品材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "18"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "基金风控和投资风险安排材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "19"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "原始权益人出表与否材料"
                    )
                  ]);
                }else if(params.row.proAttachType == "20"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "原始权益人是否履行回购义务材料"
                    )
                  ]);
                } else if(params.row.proAttachType == "05"){
                   return h("div", [
                    h(
                      "span",
                      {
                        style: {
                          display: "block"
                        }
                      },
                      "其他限制性条件材料"
                    )
                  ]);
                }
          }
          },
          {
            title: "上传人",
            key: "uploadPersonName"
          },
          {
            title: "上传时间",
            key: "updateTime",
            render:(h, params) => {
              return h('div',[
                h('span', {
                    style: {
                      display:'block',
                      width:"175px",
                      fontSize: '12px'
                    }
                },params.row.updateTime),
                ]);
            }
          },
          {
            title: "附件审批状态",
            key: "attachTaskState",
            render: (h, params) => {
               if (params.row.attachTaskState == "00") {
                return h("div", [
                  h(
                    "span",
                    {
                      style: {
                        display: "block"
                      }
                    },
                    "未审核"
                  )
                ]);
              } else if (params.row.attachTaskState == "true" ||params.row.attachTaskState == "01") {
                return h("div", [
                  h(
                    "span",
                    {
                      style: {
                        display: "block"
                      }
                    },
                    "审核通过"
                  )
                ]);
              } else if (params.row.attachTaskState == "false" ||params.row.attachTaskState == "02") {
                return h("div", [
                  h(
                    "span",
                    {
                      style: {
                        display: "block"
                      }
                    },
                    "审核不通过"
                  )
                ]);
              }
            }
          },
          {
            title: "操作",
            key: "operation",
            render: (h, params) => {
              if (params.row.flag === "00") {
                return h(
                "RadioGroup",
                {
                  props: {
                    value: this.AttachmentList[params.index].attachTaskState
                  },
                  on: {
                    "on-change": status => {
                      if (status == "false") {
                        this.textareaVale = "";
                        this.$set(this.AttachmentList[params.index],"attachTaskState","false");
                        this.$set(this.AttachmentList[params.index],"Comments","01");
                        this.$Modal.confirm({
                          render: h => {
                            return h("Input", {
                              props: {
                                title: "111",
                                value: this.textareaVale,
                                autofocus: true,
                                placeholder: "请填写驳回的原因"
                              },
                              on: {
                                input: val => {
                                  this.textareaVale = val;
                                }
                              }
                            });
                          },
                          onOk: () => {
                            console.log(this.textareaVale);
                            this.$set(
                              this.AttachmentList[params.row._index],
                              "backReason",
                              this.textareaVale
                            );
                          }
                        });
                      } else {
                        this.textareaVale = "";
                        this.$set(
                          this.AttachmentList[params.index],"attachTaskState","true");
                          this.$set(this.AttachmentList[params.index],"Comments","01");
                      }
                    }
                  }
                },
                [
                  h(
                    "Radio",
                    {
                      props: {
                        label: "true"
                      },
                        style: {
                      position: 'relative',
                      top: '10px',
                      left: '0px'
                    }
                    },
                    "通过"
                  ),
                  h(
                    "Radio",
                    {
                      props: {
                        label: "false"
                      },
                      style: {
                      position: 'relative',
                      top: '-10px',
                      left: '55px'
                    }
                    },
                    "驳回"
                  )
                ]
              );
            }
            }
        }
      ],
          AttachmentList: [],
          vertical:"",
          orderId:"",
          team:"",
          Target:false,
          underlying:true,
          back:false,
          seed:false,
          Interlayer:false,
          InvestmentTargetList:[
             {
                value: '1',
                label: 'Pre-ABS'
            },
            {
                value: '2',
                label: 'ABS优先、夹层或劣后'
            },
            {
                value: '3',
                label: 'RQFII基金种子'
            },
            {
                value: '4',
                label: '其他'
            }
          ],
          InvestmentPurpose:[
             {
                value: '1',
                label: '撬动ABS业务'
            },
            {
                value: '2',
                label: '撬动外部资金方'
            },
            {
                value: '3',
                label: '满足客户的具体需求'
            },
            {
                value: '4',
                label: '满足我方的需求'
            }
          ],
          SubId:[],
          model8:[],
          isDetail:false,
          detailshow:false,
          Examination:false,
          process:false,
          Agreed:false,
          ListData:false,
          queryid:"",
          auditStatus: "",
          status:"",
          auditInfo:"",
          processId:"",
          taskId:"",
          model9:[],
          PartnersArr:[],//合作方列表
          historyAudit: [], //历史审批
          Approval:false,
          proId:"",
          layerList: [
          {
            level: "",
            amount: "",
            proportion: "",
            issueRate: "",
            proTerm: "",
            averageTerm: ""
          },
          {
            level: "",
            amount: "",
            proportion: "",
            issueRate: "",
            proTerm: "",
            averageTerm: ""
          },
          {
            level: "",
            amount: "",
            proportion: "",
            issueRate: "",
            proTerm: "",
            averageTerm: ""
          },
          {
            level: "",
            amount: "",
            proportion: "",
            issueRate: "",
            proTerm: "",
            averageTerm: ""
          },
          {
            level: "",
            amount: "",
            proportion: "",
            issueRate: "",
            proTerm: "",
            averageTerm: ""
          }
      ], //分层结构
      datainfo: {
        teamList:[],//项目团队
        proName:"",//项目名称
        investmentTarget:"",//投资标的
        investmentPurpose:"",//投资目的
        assetSerAgency:"",//资产服务机构
        inferiorInvestor:"",//劣后投资人
        diffAgency:"",//差额补足机构
        investmentAmount:"",//投资金额
        totalAmount:"",//发行金额
        leverage:"",//杠杆倍数
        coopScale:"",//12个月内预计合作规模(亿)
        stockBalance:"",//存量余额
        investmentCharacteristics:[],//投资特点
        exitMode:"",//退出方式
        coopState:"",//是否同意框架合作
        systemAbutmentState:"",//是否同意系统对接
        originalRightsHolder:"",//原始权益人
        holderRatingSituation:"",//评级情况
        externalEnhancementAgencies:"",//外部增信机构
        ratingSituation:"",//评级情况
        proInfoTradeStructure:{
          underlyingAssetsType:[],//底层资产类型 00
          underlyingAssetsStandardFileList:[], //底层资产投放标准
          underlyingAssetsStandard:"",
          ratioAmountFileList:[],//各方出资金额及占比
          ratioAmount:"",
          fundArrangementsFileList:[],//各方出资安排
          fundArrangements:"",
          termInvestmentFileList:[],//投资期限
          termInvestment:"",
          rateInvestmentFileList:[],//投资利率
          rateInvestment:"",
          ratingResultFileList:[],//ABS发行金额、分层占比、及分层评级结果
          ratingResult:"",
          isOriginalListFileList:[],//原始权益人出表与否
          isOriginalList:"",
          isOriginalBuyback:"",//原始权益人是否履行回购义务
          isOriginalBuybackFileList:[],
          breachContentFileList:[],//违约事件阈值及内容设定
          breachContent:"",
          breachCompareFileList:[],//违约事件发生前和发生后分配顺序的比较
          breachCompare:"",
          totalFundAmountFileList:[],//交易结构（种子基金项目）
          totalFundAmount:"",
          seedFundProportionFileList:[],//种子基金占比
          seedFundProportion:"",
          estimatedManagementFeeFileList:[],//预计收取的基金管理费
          estimatedManagementFee:"",
          scopeInvestmentFileList:[],//种子基金投资范围或产品
          scopeInvestment:"",
          riskArrangementFileList:[],//基金风控和投资风险安排  00
          riskArrangement:"",
        },
        cost:"",//综合成本
        term:"",//产品期限
        channel:"",//分销渠道
        packageNum:"",//理财通分
        packageScale:"",//单包规模
        frontIncome:"",//项目前端收入
        afterIncome:"",//项目后端收入
        layerList:[],
        amortizationPeriod: "", //摊还期
        cyclePeriod: "", //循环期
        requirements:'静态池',//静态池，循环结构
        fileList:[],//附件列表
      },
      checkTips: {
        proName:"项目名称不能为空不能为空",
        investmentTarget:"投资标的不能为空",
        investmentPurpose:"投资目的不能为空",
        assetSerAgency:"资产服务机构不能为空",
        inferiorInvestor:"劣后投资人不能为空",
        diffAgency:"差额补足机构不能为空",
        investmentAmount:"投资金额(亿)不能为空",
        totalAmount:"发行金额(亿)不能为空",
        // leverage:"杠杆倍数不能为空",
        coopScale:"12个月内预计合作规模(亿)不能为空",
        proName:"已合作规模(亿)不能为空",
        stockBalance:"存量余额(亿)不能为空",
        investmentCharacteristics:"投资特点不能为空",
        exitMode:"退出方式不能为空",
        coopState:"是否同意框架合作不能为空",
        systemAbutmentState:"是否同意系统对接不能为空",
        originalRightsHolder:"原始权益人不能为空",
        holderRatingSituation:"原始权益人的评级情况不能为空",
        ratingSituation:"外部增信机构的评级情况不能为空",
        externalEnhancementAgencies:"外部增信机构不能为空",
        cost:"综合成本(元)不能为空",
        term:"产品期限(年)不能为空",
        channel:"分销渠道不能为空",
        frontIncome:"项目前端收入不能为空",
        afterIncome:"项目后端收入不能为空",
      },
    Showcase:{
      underlyingL:3,
      underlyingR:3,
      underlyingLShow:true,
      underlyingRShow:true,
      ratioL:3,
      ratioR:3,
      ratioLShow:true,
      ratioRShow:true,
      fundArrL:3,
      fundArrR:3,
      fundArrLShow:true,
      fundArrRShow:true,
      termInvL:3,
      termInvR:3,
      termInvLShow:true,
      termInvRShow:true,
      stmenL:3,
      stmenR:3,
      stmenLShow:true,
      stmenRShow:true,
      ratingL:3,
      ratingR:3,
      ratingLShow:true,
      ratingRShow:true,
      isOriginaL:3,
      isOriginaR:3,
      isOriginaLShow:true,
      isOriginaRShow:true,
      isOriginalL:3,
      isOriginalR:3,
      isOriginalLShow:true,
      isOriginalRShow:true,
      breachL:3,
      breachR:3,
      breachLShow:true,
      breachRShow:true,
      breachCompareL:3,
      breachCompareR:3,
      breachCompareLShow:true,
      breachCompareRShow:true,
      totalFundL:3,
      totalFundR:3,
      totalFundLShow:true,
      totalFundRShow:true,
      seedFundL:3,
      seedFundR:3,
      seedFundLShow:true,
      seedFundRShow:true,
      estimatedL:3,
      estimatedR:3,
      estimatedLShow:true,
      estimatedRShow:true,
      scopeL:3,
      scopeR:3,
      scopeLShow:true,
      scopeRShow:true,
      riskL:3,
      riskR:3,
      riskLShow:true,
      riskRShow:true,
    }
    }
      },
      components: {
          mdContract
      },
    computed:{
      channel(){
      return this.datainfo.channel === '理财通' ? true :false;
      },
        isshow(){
      if (this.datainfo.requirements === '循环结构') {
          return true;
      } else {
          return false;
      }
    },
    leverage(){
      if (this.datainfo.investmentAmount && this.datainfo.totalAmount) {
        return (this.datainfo.totalAmount / this.datainfo.investmentAmount).toFixed(2)
      }
    }
    },
    mounted(){
    //我发起的页面点击重新发起的时候请求数据,并且做相对应的赋值操作
    if (this.$route.query.orderId) {
      this.orderId = this.$route.query.orderId
    }
    if (this.$route.query.isDetail) {
      this.isDetail = this.$route.query.isDetail
    }
     if (this.$route.query.id) {
      this.queryid = this.$route.query.id;
    }
    if (this.$route.query.detailshow) {
      this.detailshow = this.$route.query.detailshow;
    }
    if (this.$route.query.Examination) {
      this.Examination = this.$route.query.Examination
    }
    if (this.$route.query.processId) {
      this.processId = this.$route.query.processId
    }
     if (this.$route.query.taskId) {
      this.taskId = this.$route.query.taskId
    }
    if (this.$route.query.Approval) {
      this.Approval = this.$route.query.Approval
    }
    if (this.$route.query.proId) {
      this.proId = this.$route.query.proId
    }
    if (this.$route.query.pdfshow) {
      this.pdfshow = this.$route.query.pdfshow;
    }
    if (this.$route.query.Agreed) {
      this.Agreed = this.$route.query.Agreed;
    }
    if (this.$route.query.back) {
      this.back = this.$route.query.back;
    }
    if (this.$route.query.process) {
      this.process = this.$route.query.process;
    }
    if (this.$route.query.EnclosureShow) {
      this.EnclosureShow = this.$route.query.EnclosureShow;
    }
    if (this.$route.query.force) {
      this.force = this.$route.query.force;
    }
    if (this.$route.query.backshow) {
      this.backshow = this.$route.query.backshow;
    }
    if (this.$route.query.Decision) {
      this.Decision = this.$route.query.Decision;
    }
    if (this.$route.query.ListData) {
      this.ListData = this.$route.query.ListData;
    }
    if (this.$route.query.ResultData) {
      this.ResultData = this.$route.query.ResultData;
    }

    //选择前置审批人员取值
    if (this.App ||this.Examination) {
       axios
      .get("/api/snaker/surrogate/surrogate")
      .then(res => {
        console.log(res.data);
        if (res.data.code === "000") {
          let Num = res.data.data;
        for (var i = 0; i < Num.length; i++) {
          this.ApproverMberList.push({
            userName: Num[i].userName,
            fullName: Num[i].fullName,
            userId: Num[i].id
          });
        }
        }
      })
      .catch(function(error) {
        console.log(error);
      });
    }

    axios
    .get("/api/snaker/surrogate/surrogate?proType="+"VOTE")
    .then((res)=>{
      let Num = res.data.data
      if (res.data.code == "000") {
      for (var i = 0; i < Num.length; i++) {
        this.label.push({
          "userName":Num[i].userName,
          "fullName":Num[i].fullName,
          "userId":Num[i].id,
          })
        }
      }
    })
    .catch(function (error){
      console.log(error);
    });
//分销渠道部分取值
  axios.get('/api/snaker/flow/channel')
  .then((res)=>{
    if (res.data.code == '000') {
    let arr = res.data.data.split(',');
      for (let i = 0; i < arr.length; i++) {
       this.model8.push({
        'channel':arr[i]
        })
      }
    }
  })
  .catch(function(error){
  console.log(error);
})
    //项目团队默认赋值
  let UseId = localStorage.getItem("user")
  if (!this.isDetail) {
    this.datainfo.teamList.push(UseId)
  }
      if (this.$route.query.proId) {
     axios
    .get("/api/snaker/secretary/getFileForFileApproval?proId=" + this.proId)
    .then(res => {
      let Len = res.data.data;
      for (let i = 0; i < Len.length; i++) {
        this.AttachmentList.push({
          id: Len[i].id,
          proId: Len[i].proId,
          fileName: Len[i].fileName,
          filePath: Len[i].filePath,
          realFileName: Len[i].realFileName,
          proAttachType: Len[i].proAttachType,
          status: Len[i].status,
          uploadPersonName: Len[i].uploadPersonName,
          uploadPersonId: Len[i].uploadPersonId,
          createTime: Len[i].createTime.split("T")[0],
          updateTime: Len[i].updateTime.split("T")[0],
          optType: Len[i].optType,
          attachTaskState: Len[i].attachTaskState,
          backReason: Len[i].backReason,
          attachTaskId:Len[i].attachTaskId,
          flag:Len[i].flag,
        });
      }
      console.log(this.AttachmentList);
    })
    .catch(function(error) {
      console.log(error);
    });
    }

  //投决会取值
  if (this.orderId && !this.process) {
    axios.get('/api/snaker/voteMeet/proDetail/'+this.orderId)
     .then((res)=>{
       if (res.data.code == "000") {
         let Len = res.data.data.teamList
            if (res.data.data.proInfoTradeStructure == null) {
              res.data.data.proInfoTradeStructure = this.datainfo.proInfoTradeStructure
            }
          if(res.data.data.investmentCharacteristics == null){
              res.data.data.investmentCharacteristics = []
            }
         let arr = []
         let Num = res.data.data;
         this.LengthRow = Num.layerList.length+1;

         this.datainfo = res.data.data;
          for (let i = 0; i < Len.length; i++) {
            arr.push(Len[i].username)
          }
          this.team = arr;
         console.log(this.datainfo);
         
          if (this.datainfo.investmentTarget == 1) {
          this.datainfo.investmentTarget = "Pre-ABS"
          this.Target = true
          this.Interlayer = false;
          this.seed = false;
        }else if(this.datainfo.investmentTarget ==2){
          this.datainfo.investmentTarget = "ABS优先、夹层或劣后"
          this.Target = false;
          this.Interlayer = true
          this.seed = false;
        }else if(this.datainfo.investmentTarget == 3){
          this.datainfo.investmentTarget = "RQFII基金种子"
          this.Target = false;
          this.Interlayer = false;
          this.seed = true;
        }else if(this.datainfo.investmentTarget == 4){
          this.datainfo.investmentTarget = '其他'
          this.Target = false;
          this.Interlayer = false;
          this.seed = false;
        }

      if (this.datainfo.investmentPurpose == 1) {
        this.datainfo.investmentPurpose = "撬动ABS业务"
      }else if(this.datainfo.investmentPurpose ==2){
        this.datainfo.investmentPurpose = "撬动外部资金方"
      }else if(this.datainfo.investmentPurpose == 3){
        this.datainfo.investmentPurpose = "满足客户的具体需求"
      }else if(this.datainfo.investmentPurpose == 4){
        this.datainfo.investmentPurpose = '满足我方的需求'
      }

         console.log(this.datainfo);
        for (let i = 0; i < this.datainfo.teamList.length; i++) {
          arr.push(this.datainfo.teamList[i].fullName)
        }
        this.datashowList()
        console.log(this.datainfo);
        this.team = arr.toString();
        if (!this.ListData) {
         let num =this.datainfo.layerList;
          this.LengthRow = this.datainfo.layerList.length+1;
          for (let index = 0; index < this.layerList.length; index++){
             if (num[index]) {
              this.datainfo.layerList[index].level = num[index].level;
              this.datainfo.layerList[index].amount = num[index].amount;
              this.datainfo.layerList[index].proportion = num[index].proportion;
              this.datainfo.layerList[index].issueRate = num[index].issueRate;
              this.datainfo.layerList[index].proTerm = num[index].proTerm;
              this.datainfo.layerList[index].averageTerm = num[index].averageTerm;
            }
          }
        }else{
            for (let index = 0; index < this.datainfo.layerList.length; index++) {
              this.layerList[index].level = this.datainfo.layerList[index].level;
              this.layerList[index].amount = this.datainfo.layerList[index].amount;
              this.layerList[index].proportion = this.datainfo.layerList[index].proportion;
              this.layerList[index].issueRate = this.datainfo.layerList[index].issueRate;
              this.layerList[index].proTerm = this.datainfo.layerList[index].proTerm;
              this.layerList[index].averageTerm = this.datainfo.layerList[index].averageTerm;
          }
        }
       }
     })
     .catch(function (error){
       console.log(error);
     });
      }
      if (this.process) {
        axios.get('/api/snaker/voteMeet/proDetail/'+this.orderId)
     .then((res)=>{
        let arr = []
        console.log(res.data);
        if (res.data.code =="000") {
          let Len = res.data.data.teamList
          if (res.data.data.proInfoTradeStructure == null) {
              res.data.data.proInfoTradeStructure = this.datainfo.proInfoTradeStructure
            }
            if(res.data.data.investmentCharacteristics == null){
              res.data.data.investmentCharacteristics = [];
            }
          this.datainfo = res.data.data;
          
          console.log(res.data.data.fileList);
          for (let i = 0; i < Len.length; i++) {
            arr.push(Len[i].username)
          }
          // console.log(arr);
          this.datainfo.teamList = arr;
          if (this.datainfo.investmentTarget == 1) {
          this.Target = true
          this.Interlayer = false;
          this.seed = false;
        }else if(this.datainfo.investmentTarget ===2){
          this.Target = false;
          this.Interlayer = true
          this.seed = false;
        }else if(this.datainfo.investmentTarget == 3){
          this.Target = false;
          this.Interlayer = false;
          this.seed = true;
        }else if(this.datainfo.investmentTarget == 4){
          this.Target = false;
          this.Interlayer = false;
          this.seed = false;
        }
          if (res.data.data.investmentCharacteristics !='') {
            this.datainfo.investmentCharacteristics = res.data.data.investmentCharacteristics.split(",")
          }
          if (res.data.data.proInfoTradeStructure.underlyingAssetsType != '') {
            this.datainfo.proInfoTradeStructure.underlyingAssetsType = res.data.data.proInfoTradeStructure.underlyingAssetsType.split(",")
          }
          this.LengthRow = 7;
          //处理分层结构
          for (let index = 0; index < this.datainfo.layerList.length; index++) {
              this.layerList[index].level = this.datainfo.layerList[index].level;
              this.layerList[index].amount = this.datainfo.layerList[index].amount;
              this.layerList[index].proportion = this.datainfo.layerList[index].proportion;
              this.layerList[index].issueRate = this.datainfo.layerList[index].issueRate;
              this.layerList[index].proTerm = this.datainfo.layerList[index].proTerm;
              this.layerList[index].averageTerm = this.datainfo.layerList[index].averageTerm;
          }

        }else{
          serviceError(data.message);
        }
     })
     .catch(function (error){
       console.log(error);
     });
      }

  axios.get('/api/snaker/voteMeet/collaboratorList')
     .then((res)=>{
       if (res.data.code =="000") {
         let arr = res.data.data;
         for (let i = 0; i < arr.length; i++) {
           this.PartnersArr.push({
             "usename":arr[i]
           })
         }
       }
     })
     .catch(function (error){
       console.log(error);
     });
    //待提交项目取值
      if (this.back) {
      axios.get("/api/snaker/flow/getProDraftById?id="+this.queryid)
      .then(res=>{
        let arr = []
        console.log(res.data);
        if (res.data.code =="000") {
          console.log(res.data.data);
          this.datainfo.teamList = []
          this.status = res.data.data.status
          let Len = res.data.data.teamList
          this.datainfo = res.data.data;
          // this.datainfo.proId = res.data.data.id
          this.datainfo.investmentCharacteristics = res.data.data.investmentCharacteristics.split(",")
          this.datainfo.proInfoTradeStructure.underlyingAssetsType = res.data.data.proInfoTradeStructure.underlyingAssetsType.split(",")
          

        if (this.datainfo.investmentTarget == 1) {
          this.Target = true
          this.Interlayer = false;
          this.seed = false;
        }else if(this.datainfo.investmentTarget ==2){
          this.Target = false;
          this.Interlayer = true
          this.seed = false;
        }else if(this.datainfo.investmentTarget == 3){
          this.Target = false;
          this.Interlayer = false;
          this.seed = true;
        }else if(this.datainfo.investmentTarget == 4){
          this.Target = false;
          this.Interlayer = false;
          this.seed = false;
        }
          if (this.datainfo.fileList == null) {
            this.datainfo.fileList = []
          }

          for (let i = 0; i < Len.length; i++) {
            arr.push(Len[i].username)
          }
          this.datainfo.teamList = arr;
          console.log(this.datainfo);
          //处理分层结构
          for (let index = 0; index < this.datainfo.layerList.length; index++) {
              this.layerList[index].level = this.datainfo.layerList[index].level;
              this.layerList[index].amount = this.datainfo.layerList[index].amount;
              this.layerList[index].proportion = this.datainfo.layerList[index].proportion;
              this.layerList[index].issueRate = this.datainfo.layerList[index].issueRate;
              this.layerList[index].proTerm = this.datainfo.layerList[index].proTerm;
              this.layerList[index].averageTerm = this.datainfo.layerList[index].averageTerm;
          }

        }else{
          serviceError(data.message);
        }

      })
      .catch(function(error) {
        console.log(error);
      });
    }
    //待提交项目详情取值
    if (this.$route.query.wsid) {
    axios
    .get("/api/snaker/flow/getProDraftById?id=" + this.$route.query.wsid)
    .then((res)=>{
    let arr = []
    let Num = res.data.data
    if (res.data.code == "000") {
      this.datainfo = res.data.data
      this.status = res.data.data.status
      console.log(this.datainfo);
        if (this.datainfo.investmentTarget == 1) {
          this.datainfo.investmentTarget = "Pre-ABS"
          this.Target = true
          this.Interlayer = false;
          this.seed = false;
        }else if(this.datainfo.investmentTarget ==2){
          this.datainfo.investmentTarget = "ABS优先、夹层或劣后"
          this.Target = false;
          this.Interlayer = true
          this.seed = false;
        }else if(this.datainfo.investmentTarget == 3){
          this.datainfo.investmentTarget = "RQFII基金种子"
          this.Target = false;
          this.Interlayer = false;
          this.seed = true;
        }else if(this.datainfo.investmentTarget == 4){
          this.datainfo.investmentTarget = '其他'
          this.Target = false;
          this.Interlayer = false;
          this.seed = false;
        }

      if (this.datainfo.investmentPurpose == 1) {
        this.datainfo.investmentPurpose = "撬动ABS业务"
      }else if(this.datainfo.investmentPurpose ==2){
        this.datainfo.investmentPurpose = "撬动外部资金方"
      }else if(this.datainfo.investmentPurpose == 3){
        this.datainfo.investmentPurpose = "满足客户的具体需求"
      }else if(this.datainfo.investmentPurpose == 4){
        this.datainfo.investmentPurpose = '满足我方的需求'
      }
    for (let i = 0; i < this.datainfo.teamList.length; i++) {
      arr.push(this.datainfo.teamList[i].fullName)
    }
    this.team = arr.toString();
    this.LengthRow = this.datainfo.layerList.length+1;
    }
  })
    .catch(function(error) {
      console.log(error);
    });
}
    //调用历史审批
    if (this.Approval) {
        this.api
        .getHistoryAudit({
          id: this.orderId
        })
        .then(data => {
          if (data.code == "000") {
            this.historyAudit = data.data;
          } else {
            serviceError(data.message);
          }
        });
    }

  if (this.Decision) {
      axios.get("/api/snaker/flow/getMeetPerson/" + "2")
      .then(res => {
        if (res.data.code === '000') {
          var Arrfixed = []
          var optionalArr = []
          var Fixmember = []
          let Arr = res.data.data;
          console.log(Arr);
        for (var i = 0; i < Arr.length; i++) {
          this.Conference.push({
            'fullName':Arr[i].fullName,
            'userName':Arr[i].userName
          })
          if (Arr[i].type == 'mandatory') {
            Arrfixed.push(Arr[i].fullName)
            Fixmember.push(Arr[i].userName)
          }
          if (Arr[i].type == 'optional') {
            optionalArr.push({
              'index':i,
              'name':Arr[i].userName,
              'value':Arr[i].fullName
            })
          }
        }
        this.optional = optionalArr;
        this.fixed = Arrfixed.toString();
        this.Fixmember = Fixmember
        }
        
      })
    .catch(function(error) {
      console.log(error);
    });
      }
    },
  methods:{
    remoteMethod2(query) {
    let self = this;
      if (query) {
         setTimeout(() => {
        let params = {
          name: query
        }
         axios.get('/api/snaker/surrogate/surrogate?fullName='+query)
        .then((res)=>{
          let Num = res.data.data
           this.label=[]
          for (var i = 0; i < Num.length; i++) {
            this.label.push({
              "userName":Num[i].userName,
              "fullName":Num[i].fullName,
              "userId":Num[i].id,
            })
          }
        })
        .catch(function (error) {
          console.log(error);
        });
        }, 100);
      }else{
        this.label =[];
      }
  },
    //底层资产投放标准pdf接口
    handleUploadPassingunderlying(file){
       if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "06");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.proInfoTradeStructure.underlyingAssetsStandardFileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
    //各方出资金额及占比pdf接口
    handleUploadPassingratio(file){
       if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "07");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.proInfoTradeStructure.ratioAmountFileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
        //各方出资安排pdf接口
    handleUploadPassingfund(file){
       if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "08");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.proInfoTradeStructure.fundArrangementsFileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
    //投资期限pdf接口
    handleUploadPassingtermInvest(file){
       if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "09");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.proInfoTradeStructure.termInvestmentFileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
    //投资利率pdf接口
    handleUploadPassingrateInvestment(file){
       if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "10");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.proInfoTradeStructure.rateInvestmentFileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
    //ABS发行金额、分层占比、及分层评级结果pdf接口
    handleUploadPassingratingResult(file){
       if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "11");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.proInfoTradeStructure.ratingResultFileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },

    //违约事件阈值及内容设定pdf接口
    handleUploadPassingbreach(file){
       if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "12");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.proInfoTradeStructure.breachContentFileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
        //违约事件发生前和发生后分配顺序的比较
    handleUploadPassingbreachCompare(file){
       if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "13");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.proInfoTradeStructure.breachCompareFileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
            //交易结构（种子基金项目）pdf接口
    handleUploadPassingtotalFundAmount(file){
       if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "14");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.proInfoTradeStructure.totalFundAmountFileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
    //种子基金占比pdf接口
    handleUploadPassingseedFundProport(file){
       if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "15");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.proInfoTradeStructure.seedFundProportionFileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },

    //预计收取的基金管理费pdf接口
    handleUploadPassingestimatedManagement(file){
       if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "16");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.proInfoTradeStructure.estimatedManagementFeeFileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
      //种子基金投资范围或产品pdf接口
    handleUploadPassingscopeInvestment(file){
       if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "17");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.proInfoTradeStructure.scopeInvestmentFileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
    //基金风控和投资风险安排pdf接口
    handleUploadPassingriskArrangement(file){
       if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "18");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.proInfoTradeStructure.riskArrangementFileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
     //原始权益人出表与否pdf接口
    handleUploadPassingisOriginal(file){
       if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "19");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.proInfoTradeStructure.isOriginalListFileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
     //原始权益人是否履行回购义务pdf接口
    handleUploadPassingisOriginalBuyback(file){
       if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "20");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.proInfoTradeStructure.isOriginalBuybackFileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
        //pdf上传落实材料
    handleUploadImplement(file) {
      if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "02");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.dataTableListshow.push({
            id: data.data.id,
            fileName: data.data.fileName,
            filePath: data.data.filePath,
            realFileName: data.data.realFileName,
            proId: data.data.proId,
            status: data.data.status,
            proAttachType: data.data.proAttachType,
            uploadPersonId: data.data.uploadPersonId,
            uploadPersonName: data.data.uploadPersonName,
            updateTime: data.data.updateTime.split("T")[0],
            optType: "0"
          });
          this.SubId.push({
            id: data.data.id,
            fileName: data.data.fileName,
            filePath: data.data.filePath,
            realFileName: data.data.realFileName,
            proId: data.data.proId,
            status: data.data.status,
            proAttachType: data.data.proAttachType,
            uploadPersonId: data.data.uploadPersonId,
            uploadPersonName: data.data.uploadPersonName,
            updateTime: data.data.updateTime.split("T")[0],
            optType: "0"
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
    //pdf上传补充材料
    handleUploadsupplement(file) {
      if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "01");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.dataTableListshow.push({
            id: data.data.id,
            fileName: data.data.fileName,
            filePath: data.data.filePath,
            realFileName: data.data.realFileName,
            proId: data.data.proId,
            status: data.data.status,
            proAttachType: data.data.proAttachType,
            uploadPersonId: data.data.uploadPersonId,
            uploadPersonName: data.data.uploadPersonName,
            updateTime: data.data.updateTime.split("T")[0],
            optType: "0"
          });
          this.SubId.push({
            id: data.data.id,
            fileName: data.data.fileName,
            filePath: data.data.filePath,
            realFileName: data.data.realFileName,
            proId: data.data.proId,
            status: data.data.status,
            proAttachType: data.data.proAttachType,
            uploadPersonId: data.data.uploadPersonId,
            uploadPersonName: data.data.uploadPersonName,
            updateTime: data.data.updateTime.split("T")[0],
            optType: "0"
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },

  //新增分层结构
  AddDom(){
    this.layerList.push({})
    this.LengthRow = this.layerList.length+2
  },
  reduce() {
  console.log(this.layerList.length);
  if (this.layerList.length == 5) {
  this.$Message.error({
  content: "已经不能在减少了哦!!!",
  duration: 3
  });
  }else{
    this.layerList.splice(-1,1)
    this.LengthRow = this.layerList.length+2
  }
  },
  //新增合作方
    addPepole(){
      this.$Modal.confirm({
        "width":"345px",
      render: (h) => {
          return h('Input', {
              props: {
                  title:'111',
                  value: this.value,
                  autofocus: true,
                  placeholder: '请输入新增合作方'
              },
              on: {
                  input: (val) => {
                      this.value = val;
                  }
              }
          })
      },
      onOk: () => {
            if (this.value.length) {
                   axios.post('/api/snaker/voteMeet/addCollaborator?value='+this.value)
                  .then((res)=>{
                    if (res.status == '200') {
                      this.PartnersArr.push({
                        'usename':this.value
                      })
                    }
                  })
                  .catch(function(error){
                    console.log(error);
                  })
                }
              }
        });
      },
      addAudit(){
      const proType = "2"; //表示是投决

      let params = this.getParams();

      if (!this.checkParams(params)) {
        return;
      }
      console.log(this.datainfo.proInfoTradeStructure.ratingResult);
      
      if (this.datainfo.investmentTarget === "1") {
        if (this.datainfo.proInfoTradeStructure.underlyingAssetsType.length == 0) {
          this.$Message.error({
              content: "底层资产类型不能为空",
              duration: 3
            });
            return false;
        }else if(this.datainfo.proInfoTradeStructure.underlyingAssetsStandardFileList.length == 0 && this.datainfo.proInfoTradeStructure.underlyingAssetsStandard.length == 0){
          this.$Message.error({
              content: "底层资产投放标准不能为空",
              duration: 3
            });
            return false;
        }else if(this.datainfo.proInfoTradeStructure.ratioAmountFileList.length == 0 && this.datainfo.proInfoTradeStructure.ratioAmount.length == 0){
          this.$Message.error({
              content: "各方出资金额及占比不能为空",
              duration: 3
            });
            return false;
        }else if(this.datainfo.proInfoTradeStructure.fundArrangementsFileList.length == 0 && this.datainfo.proInfoTradeStructure.fundArrangements.length == 0){
          this.$Message.error({
              content: "各方出资安排不能为空",
              duration: 3
            });
            return false;
        }else if(this.datainfo.proInfoTradeStructure.termInvestmentFileList.length == 0 && this.datainfo.proInfoTradeStructure.termInvestment.length == 0){
          this.$Message.error({
              content: "投资期限不能为空",
              duration: 3
            });
            return false;
        }else if(this.datainfo.proInfoTradeStructure.rateInvestmentFileList.length == 0 && this.datainfo.proInfoTradeStructure.rateInvestment.length == 0){
          this.$Message.error({
              content: "投资利率不能为空",
              duration: 3
            });
            return false;
        }
      }else if(this.datainfo.investmentTarget === "2"){
        if (this.datainfo.proInfoTradeStructure.ratingResultFileList.length == 0 && this.datainfo.proInfoTradeStructure.ratingResult.length == 0) {
          this.$Message.error({
              content: "ABS发行金额、分层占比、及分层评级结果不能为空",
              duration: 3
            });
            return false;
        }else if(this.datainfo.proInfoTradeStructure.isOriginalListFileList.length == 0 && this.datainfo.proInfoTradeStructure.isOriginalList.length == 0){
          this.$Message.error({
              content: "原始权益人出表与否不能为空",
              duration: 3
            });
            return false;
        }else if(this.datainfo.proInfoTradeStructure.underlyingAssetsStandardFileList.length == 0 && this.datainfo.proInfoTradeStructure.underlyingAssetsStandard.length == 0){
          this.$Message.error({
              content: "底层资产投放标准不能为空",
              duration: 3
            });
            return false;
        }else if(this.datainfo.proInfoTradeStructure.termInvestmentFileList.length == 0 && this.datainfo.proInfoTradeStructure.termInvestment.length == 0){
          this.$Message.error({
              content: "投资期限不能为空",
              duration: 3
            });
            return false;
        }else if(this.datainfo.proInfoTradeStructure.rateInvestmentFileList.length == 0 && this.datainfo.proInfoTradeStructure.rateInvestment.length == 0){
          this.$Message.error({
              content: "投资利率不能为空",
              duration: 3
            });
            return false;
        }else if(this.datainfo.proInfoTradeStructure.isOriginalBuyback.length == 0 && this.datainfo.proInfoTradeStructure.isOriginalBuybackFileList.length == 0){
          this.$Message.error({
              content: "原始权益人是否履行回购义务不能为空",
              duration: 3
            });
            return false;
        }else if(this.datainfo.proInfoTradeStructure.breachContentFileList.length == 0 && this.datainfo.proInfoTradeStructure.breachContent.length == 0){
          this.$Message.error({
              content: "违约事件阈值及内容设定不能为空",
              duration: 3
            });
            return false;
        }else if(this.datainfo.proInfoTradeStructure.breachCompareFileList.length == 0 && this.datainfo.proInfoTradeStructure.breachCompare.length == 0){
          this.$Message.error({
              content: "违约事件发生前和发生后分配顺序的比较不能为空",
              duration: 3
            });
            return false;
        }
      }else if(this.datainfo.investmentTarget === "3"){
        if (this.datainfo.proInfoTradeStructure.totalFundAmountFileList.length == 0 && this.datainfo.proInfoTradeStructure.totalFundAmount.length == 0) {
          this.$Message.error({
              content: "基金总金额不能为空",
              duration: 3
            });
            return false;
        }else if(this.datainfo.proInfoTradeStructure.seedFundProportionFileList.length == 0 && this.datainfo.proInfoTradeStructure.seedFundProportion.length == 0){
          this.$Message.error({
              content: "种子基金占比不能为空",
              duration: 3
            });
            return false;
        }else if(this.datainfo.proInfoTradeStructure.estimatedManagementFeeFileList.length == 0 && this.datainfo.proInfoTradeStructure.estimatedManagementFee.length == 0){
          this.$Message.error({
              content: "预计收取的基金管理费不能为空",
              duration: 3
            });
            return false;
        }else if(this.datainfo.proInfoTradeStructure.scopeInvestmentFileList.length == 0 && this.datainfo.proInfoTradeStructure.scopeInvestment.length == 0){
          this.$Message.error({
              content: "种子基金投资范围或产品不能为空",
              duration: 3
            });
            return false;
        }else if(this.datainfo.proInfoTradeStructure.riskArrangementFileList.length == 0 && this.datainfo.proInfoTradeStructure.riskArrangement.length == 0){
          this.$Message.error({
              content: "基金风控和投资风险安排不能为空",
              duration: 3
            });
            return false;
        }
        }
      if (params.teamList.length == 0) {
        this.$Message.error("项目团队不能为空");
        return;
      }
      //处理项目团队
      let arr = [];
      for (let i = 0; i < this.datainfo.teamList.length; i++) {
        for (let j = 0; j < this.label.length; j++) {
          if (this.label[j].userName == this.datainfo.teamList[i]) {
            arr.push(this.label[j]);
          }
        }
      }
      console.log(arr);
      params.teamList = arr;
      params.investmentCharacteristics = params.investmentCharacteristics.toString();
      params.proInfoTradeStructure.underlyingAssetsType = params.proInfoTradeStructure.underlyingAssetsType.toString();
      params.proType = proType;
      params.date = this.baseInfo.date;
      params.leverage = this.leverage;
        //投资标的取值的时候去掉其他没有用到的值
        if (this.datainfo.investmentTarget == "1") {
          params.proInfoTradeStructure.ratingResultFileList = []
          params.proInfoTradeStructure.ratingResult = ""
          params.proInfoTradeStructure.isOriginalListFileList = []
          params.proInfoTradeStructure.isOriginalList = ""
          params.proInfoTradeStructure.isOriginalBuybackFileList = []
          params.proInfoTradeStructure.isOriginalBuyback = ""
          params.proInfoTradeStructure.breachContentFileList = []
          params.proInfoTradeStructure.breachContent = ""
          params.proInfoTradeStructure.breachCompareFileList = []
          params.proInfoTradeStructure.breachCompare = ""
          params.proInfoTradeStructure.totalFundAmountFileList = []
          params.proInfoTradeStructure.totalFundAmount = ""
          params.proInfoTradeStructure.seedFundProportionFileList = []
          params.proInfoTradeStructure.seedFundProportion = ""
          params.proInfoTradeStructure.estimatedManagementFeeFileList = []
          params.proInfoTradeStructure.estimatedManagementFee = ""
          params.proInfoTradeStructure.scopeInvestmentFileList = []
          params.proInfoTradeStructure.scopeInvestment = ""
          params.proInfoTradeStructure.riskArrangementFileList = []
          params.proInfoTradeStructure.riskArrangement = ""
        }else if(this.datainfo.investmentTarget == "2"){
          params.proInfoTradeStructure.ratioAmountFileList = []
          params.proInfoTradeStructure.ratioAmount = ""
          params.proInfoTradeStructure.fundArrangementsFileList = []
          params.proInfoTradeStructure.fundArrangements = ""
          params.proInfoTradeStructure.totalFundAmountFileList = []
          params.proInfoTradeStructure.totalFundAmount = ""
          params.proInfoTradeStructure.seedFundProportionFileList = []
          params.proInfoTradeStructure.seedFundProportion = ""
          params.proInfoTradeStructure.estimatedManagementFeeFileList = []
          params.proInfoTradeStructure.estimatedManagementFee = ""
          params.proInfoTradeStructure.scopeInvestmentFileList = []
          params.proInfoTradeStructure.scopeInvestment = ""
          params.proInfoTradeStructure.riskArrangementFileList = []
          params.proInfoTradeStructure.riskArrangement = ""
        }else if(this.datainfo.investmentTarget == "3"){
          params.proInfoTradeStructure.underlyingAssetsStandardFileList = []
          params.proInfoTradeStructure.underlyingAssetsStandard = ""
          params.proInfoTradeStructure.ratioAmountFileList = []
          params.proInfoTradeStructure.ratioAmount = ""
          params.proInfoTradeStructure.fundArrangementsFileList = []
          params.proInfoTradeStructure.fundArrangements = ""
          params.proInfoTradeStructure.termInvestmentFileList = []
          params.proInfoTradeStructure.termInvestment = ""
          params.proInfoTradeStructure.rateInvestmentFileList = []
          params.proInfoTradeStructure.rateInvestment = ""
          params.proInfoTradeStructure.ratingResultFileList = []
          params.proInfoTradeStructure.ratingResult = ""
          params.proInfoTradeStructure.isOriginalListFileList = []
          params.proInfoTradeStructure.isOriginalList = ""
          params.proInfoTradeStructure.isOriginalBuybackFileList = []
          params.proInfoTradeStructure.isOriginalBuyback = ""
          params.proInfoTradeStructure.breachContentFileList = []
          params.proInfoTradeStructure.breachContent = ""
          params.proInfoTradeStructure.breachCompareFileList = []
          params.proInfoTradeStructure.breachCompare = ""
        }
      console.log(params);
      // 提交
      loading();
      this.api.addProduct(params).then(data => {
        loadingHide();
        if (data.code == "000") {
          window.successNotice("提交成功");
          this.$router.push({ name: "decision" });
        } else {
          serviceError(data.message);
        }
      });
      },
       checkParams(params){
      for (let i in params){
        if (this.checkTips[i]){
          let flag = this.changeInput(params[i]);
          if (!flag){
            this.$Message.error({
              content: this.checkTips[i],
              duration: 3
            });
            return false;
          }
        }
        
        if (i === "layerList") {
          if (params.layerList.length === 0) {
            this.$Message.error({
              content: "分层结构至少需填写一条",
              duration: 3
            });
            return false;
          }
          let layerError = {
            level: "分级不能为空",
            amount: "发行金额不能为空",
            proportion: "占比应填写1-100以内的数",
            issueRate: "发行利率不能为空",
            proTerm: "产品期限应填写11位以内正整数",
            averageTerm: "加权平均期限不能为空"
          };
          let reg = /\S/;

          for (let j in params.layerList) {
            for (let k in params.layerList[j]) {
              if (!params.layerList[j][k]){
                this.$Message.error({
                  content: `第${parseInt(j) + 1}条分层结构的${layerError[k]}`,
                  duration: 3
                });
                return false;
              } else if (!reg.test(params.layerList[j][k])) {
                this.$Message.error({
                  content: `第${j + 1}条分层结构的${layerError[k]}`,
                  duration: 3
                });
              }
            }
          }
        }
      }
      return true;
    },
  changeInput(val) {
      let reg = /\S/;
      if (!val) {
        return false;
      } else {
        return true;
      }
      if (reg.test(val) && val > 0) {
        return true;
      } else {
        return false;
      }
    },
      getParams() {
      let param = JSON.parse(JSON.stringify(this.datainfo));
      param.layerList = [];
      let arr = this.layerList.slice(0);
      for (let i in arr) {
        if (
          arr[i].level ||
          arr[i].amount ||
          arr[i].proportion ||
          arr[i].issueRate ||
          arr[i].proTerm ||
          arr[i].averageTerm
        ) {
          param.layerList.push(arr[i]);
        }
      }
      return param;
    },
    //pdf上传接口
    handleUpload(file) {
      if (file.type != "application/pdf") {
        this.$Message.error("仅支持上传pdf文件");
        return false;
      }
      loading();
      let params = new FormData();
      params.append("file", file);
      params.append("proAttachType", "00");
      this.api.uploadFile(params).then(data => {
        loadingHide();
        console.log(data);
        if (data.code == "000") {
          this.datainfo.fileList.push({
            "id": data.data.id,
            "fileName": data.data.fileName,
            "filePath":data.data.filePath,
            "realFileName":data.data.realFileName,
            "proId":data.data.proId,
            "status":data.data.status,
            "proAttachType":data.data.proAttachType,
            "uploadPersonId":data.data.uploadPersonId,
            "uploadPersonName":data.data.uploadPersonName,
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
    //删除上传附件的pdf文件
      removeFile(index) {
      this.datainfo.fileList.splice(index, 1);
    },
     removeFileList(index) {
      this.datainfo.proInfoTradeStructure.underlyingAssetsStandardFileList.splice(index, 1);
    },
    removeFileListratioAmount(index) {
      this.datainfo.proInfoTradeStructure.ratioAmountFileList.splice(index, 1);
    },
    removeFileListterm(index) {
      this.datainfo.proInfoTradeStructure.termInvestmentFileList.splice(index, 1);
    },
    removeFileListrateInvestment(index) {
      this.datainfo.proInfoTradeStructure.rateInvestmentFileList.splice(index, 1);
    },
    removeFileListratingResult(index) {
      this.datainfo.proInfoTradeStructure.ratingResultFileList.splice(index, 1);
    },
    removeFileListisOriginalarr(index) {
      this.datainfo.proInfoTradeStructure.isOriginalListFileList.splice(index, 1);
    },
    removeFileListisOriginal(index) {
      this.datainfo.proInfoTradeStructure.isOriginalBuybackFileList.splice(index, 1);
    },
    removeFileListContent(index) {
      this.datainfo.proInfoTradeStructure.breachContentFileList.splice(index, 1);
    },
    removeFileListbreach(index) {
      this.datainfo.proInfoTradeStructure.breachCompareFileList.splice(index, 1);
    },
    removeFileListtotal(index) {
      this.datainfo.proInfoTradeStructure.totalFundAmountFileList.splice(index, 1);
    },
    removeFileListseed(index) {
      this.datainfo.proInfoTradeStructure.seedFundProportionFileList.splice(index, 1);
    },
    removeFileListestimated(index) {
      this.datainfo.proInfoTradeStructure.estimatedManagementFeeFileList.splice(index, 1);
    },
    removeFileListscope(index) {
      this.datainfo.proInfoTradeStructure.scopeInvestmentFileList.splice(index, 1);
    },
    removeFileListrisk(index) {
      this.datainfo.proInfoTradeStructure.riskArrangementFileList.splice(index, 1);
    },
    removeFileListfund(index) {
      this.datainfo.proInfoTradeStructure.fundArrangementsFileList.splice(index, 1);
    },
    displayPdf(item){
       if (this.status =="") {
           this.status = "-1"
         }
      let url = "/api/snaker/flow/attach/" + item.id + "/" + this.status;
      window.open("/static/pdf/web/viewer.html?file=" + url);
    },
    //保存
    Preservation() {
       if (this.datainfo.teamList.length == 0) {
        this.$Message.error("项目团队不能为空");
        return;
      }
      if (this.layerList.length == 0) {
        this.$Message.error("分层结构最少填写一行");
        return;
      }
      if (this.datainfo.proName.length == 0) {
        this.$Message.error("项目名称不能为空");
        return;
      }
      let arr = [];
        for (let i = 0; i < this.datainfo.teamList.length; i++) {
          for (let j = 0; j < this.label.length; j++) {
            if (this.label[j].userName == this.datainfo.teamList[i]) {
              arr.push({
                "userId":this.label[j].userId,
                "userName":this.label[j].userName,
                "fullName":this.label[j].fullName
              });
            }
          }
        }
        console.log(arr);
      let params = this.getParams();
      params.teamList = arr;
      params.proType = "2";
      params.date = this.baseInfo.date;
      params.investmentCharacteristics = this.datainfo.investmentCharacteristics.toString();
      params.proInfoTradeStructure.underlyingAssetsType = this.datainfo.proInfoTradeStructure.underlyingAssetsType.toString();
      console.log(params);
      
      loading();
      this.api.savePro(params).then(data => {
        loadingHide();
        if (data.code == "000") {
          console.log(data);
          window.successNotice("保存成功");
          this.$router.push({name:"submitted"});
        } else {
          serviceError(data.message);
        }
      });
    },
    //提交审核
    auditFund() {
      if (!this.Decision) {
        if (this.ResultData) {
          this.auditStatus = "0"
        }
        if (this.auditStatus == "") {
        serviceError("请选择审批结果！");
        return;
        if (this.auditStatus == "-1" && !this.auditInfo) {
          serviceError("审批不同意时，请填写审核意见");
          return;
        }
      }
      var nodeList = this.ApproverMber.toString()
      }else{
      //   if (this.ApproverMber.length == 0) {
      //   this.$Message.error({
      //     content: "选择前置审批人员不能为空",
      //     duration: 3
      //   });
      //   return false;
      // }
      if (this.auditStatus.length == 0) {
        this.$Message.error({
          content: "审批结果不能为空",
          duration: 3
        });
        return false;
      }
      var Len = this.Fixmember.concat(this.applyCategory)
      // var nodeList = Arr;
      var nodeList = Len;
      }

      let params = {
        processId: this.taskId,
        orderId: this.orderId,
        taskId: this.taskId,
        resultState: this.auditStatus,
        result: this.auditInfo,
        proType: "2",
        assignee: nodeList
      };
      console.log(params);
      
      let Status= ""
      if (this.auditStatus =="-1") {
        Status = "不同意"
      }else if(this.auditStatus =="0"){
          Status = "同意"
      }else if(this.auditStatus =="-2"){
          Status = "有条件同意"
      }
      let title =`审批结果：${Status}<br>审批意见：<span style='display: block;word-wrap: break-word;'>${this.auditInfo}</span>`;
        this.$Modal.confirm({
          title: '请确认你的操作',
          content: title,
          onOk: () => {
            //判断审批结果不同意且审批意见为空
          if (params.resultState == '-1' || params.resultState == '-2') {
            if (params.result  == '') {
              Toast({
              message: "请填写审批意见",
              position: "middle",
              duration: 5000
            });
            return false;
            }
      }
            loading();
            this.api.auditFund(params).then(data => {
            loadingHide();
            if (data.code == "000") {
              window.successNotice("审批成功");
              this.$router.push("/task/already-list");
            } else {
              serviceError(data.message);
            }
      });
          },
          onCancel: () => {
              
          }
      });
    },
    //项目附件列表提交
    Submit() {
      let title = ""
      if (this.SubId.length == 0) {
      this.$Message.error({
      content: "您提交的内容为空",
      duration: 3
      });
      return false;
      }
     console.log(this.SubId);
      let fileList = { proId: this.proId, fileList: this.SubId };
       for (let i = 0; i < fileList.fileList.length; i++) {
        if (fileList.fileList[i].optType == "0") {
          fileList.fileList[i].optType = "新增"
        }else {
          fileList.fileList[i].optType = "删除"
        }
        title +=fileList.fileList[i].fileName+" -"+fileList.fileList[i].optType+"<br>"
      }
      for (let i = 0; i < fileList.fileList.length; i++) {
        if (fileList.fileList[i].optType == "新增") {
          fileList.fileList[i].optType = "0"
        }else {
          fileList.fileList[i].optType = "1"
        }
      }
      console.log(title);
      this.$Modal.confirm({
          title: '请确认你的操作',
          content: title,
          onOk: () => {
            loading();
          this.api.addEnclosure(fileList).then(data => {
            loadingHide();
            if (data.code == "000") {
            this.$router.push({name:"materials"});
            } else {
              serviceError(data.message);
            }
              });
          },
          onCancel: () => {
              
          }
      });
    },
        //附件审核
    Material() {
      let Arrlist = []
      for (let i = 0; i < this.AttachmentList.length; i++) {
        if (this.AttachmentList[i].Comments == "01") {
          Arrlist.push(this.AttachmentList[i])
        }
      }
      if (Arrlist.length == 0) {
          this.$Message.error({
          content: "您提交的数据为空,请您检查以后再进行操作",
          duration: 3
        });
        return 
      }
      let fileResult = { fileResult: Arrlist};
      console.log(Arrlist);

      let title = ""
        for (let i = 0; i < Arrlist.length; i++) {
        if (Arrlist[i].Comments =="01") {
          if (Arrlist[i].backReason == true || Arrlist[i].attachTaskState =="true") {
          title +=Arrlist[i].fileName+" -"+"通过"+"<br>"
        }else if(Arrlist[i].backReason == false || Arrlist[i].attachTaskState =="false"){
          title +=Arrlist[i].fileName+" -"+"驳回"+"<br>"
          }
        }
      }
      console.log(title);
      
        this.$Modal.confirm({
        title: '请确认你的操作',
        content: title,
        style:{
          fontSize:"12px"
        },
          onOk: () => {
        //处理数据
        if (fileResult.fileResult.length >0){
        for (let i = 0; i < fileResult.fileResult.length; i++) {
         if (fileResult.fileResult[i].attachTaskState == "true") {
           fileResult.fileResult[i].attachTaskState = "01"
         }else if(fileResult.fileResult[i].attachTaskState == "false"){
           fileResult.fileResult[i].attachTaskState = "02"
         }
        }
      }
        loading();
        this.api.submitFileApprovalResult(fileResult).then(data => {
        loadingHide();
        if (data.code == "000") {
          window.successNotice(data.message);
          this.$router.push({name:'Nucleus'})
        } else {
          serviceError(data.message);
        }
      });
          },
          onCancel: () => {
              
          }
      });
    },
    changeList(value){
     let DataList = value
     if (DataList ==="1") {
      this.Target = true
      this.Interlayer = false;
      this.seed = false;
     }else if(DataList ==="2"){
      this.Target = false;
      this.Interlayer = true
      this.seed = false;
     }else if(DataList ==="3"){
      this.Target = false;
      this.Interlayer = false;
      this.seed = true;
     }else{
       this.Target = false;
      this.Interlayer = false;
      this.seed = false;
     }
    },
    //判断交易结构部分是否有值
    datashowList(){
      if(this.datainfo.proInfoTradeStructure.underlyingAssetsStandardFileList.length != 0 && this.datainfo.proInfoTradeStructure.underlyingAssetsStandard.length == 0){
        this.Showcase.underlyingL = 6
        this.Showcase.underlyingR = 0
        this.Showcase.underlyingLShow = true
        this.Showcase.underlyingRShow = false
      }else if(this.datainfo.proInfoTradeStructure.underlyingAssetsStandardFileList.length == 0 && this.datainfo.proInfoTradeStructure.underlyingAssetsStandard.length != 0){
        this.Showcase.underlyingL = 0
        this.Showcase.underlyingR = 6
        this.Showcase.underlyingLShow = false
        this.Showcase.underlyingRShow = true
      }
      
      if(this.datainfo.proInfoTradeStructure.ratioAmountFileList.length != 0 && this.datainfo.proInfoTradeStructure.ratioAmount.length == 0){
        this.Showcase.ratioL = 6
        this.Showcase.ratioR = 0
        this.Showcase.ratioLShow = true
        this.Showcase.ratioRShow = false
      }else if(this.datainfo.proInfoTradeStructure.ratioAmountFileList.length == 0 && this.datainfo.proInfoTradeStructure.ratioAmount.length != 0){
        this.Showcase.ratioR = 0
        this.Showcase.ratioR = 6
        this.Showcase.ratioLShow = false
        this.Showcase.ratioRShow = true
      }

      if(this.datainfo.proInfoTradeStructure.fundArrangementsFileList.length != 0 && this.datainfo.proInfoTradeStructure.fundArrangements.length == 0){
        this.Showcase.fundArrL = 6
        this.Showcase.fundArrR = 0
        this.Showcase.fundArrLShow = true
        this.Showcase.fundArrRShow = false
      }else if(this.datainfo.proInfoTradeStructure.fundArrangementsFileList.length == 0 && this.datainfo.proInfoTradeStructure.fundArrangements.length != 0){
        this.Showcase.fundArrL = 0
        this.Showcase.fundArrR = 6
        this.Showcase.fundArrLShow = false
        this.Showcase.fundArrRShow = true
      }

      if(this.datainfo.proInfoTradeStructure.termInvestmentFileList.length != 0 && this.datainfo.proInfoTradeStructure.termInvestment.length == 0){
        this.Showcase.termInvL = 6
        this.Showcase.termInvR = 0
        this.Showcase.termInvLShow = true
        this.Showcase.termInvRShow = false
      }else if(this.datainfo.proInfoTradeStructure.termInvestmentFileList.length == 0 && this.datainfo.proInfoTradeStructure.termInvestment.length != 0){
        this.Showcase.termInvL = 0
        this.Showcase.termInvR = 6
        this.Showcase.termInvLShow = false
        this.Showcase.termInvRShow = true
      }

      if(this.datainfo.proInfoTradeStructure.rateInvestmentFileList.length != 0 && this.datainfo.proInfoTradeStructure.rateInvestment.length == 0){
        this.Showcase.stmenL = 6
        this.Showcase.stmenR = 0
        this.Showcase.stmenLShow = true
        this.Showcase.stmenRShow = false
      }else if(this.datainfo.proInfoTradeStructure.rateInvestmentFileList.length == 0 && this.datainfo.proInfoTradeStructure.rateInvestment.length != 0){
        this.Showcase.stmenL = 0
        this.Showcase.stmenR = 6
        this.Showcase.stmenLShow = false
        this.Showcase.stmenRShow = true
      }
      
      if(this.datainfo.proInfoTradeStructure.breachContentFileList.length != 0 && this.datainfo.proInfoTradeStructure.breachContent.length == 0){
        this.Showcase.breachL = 6
        this.Showcase.breachR = 0
        this.Showcase.breachLShow = true
        this.Showcase.breachRShow = false
      }else if(this.datainfo.proInfoTradeStructure.breachContentFileList.length == 0 && this.datainfo.proInfoTradeStructure.breachContent.length != 0){
        this.Showcase.breachL = 0
        this.Showcase.breachR = 6
        this.Showcase.breachLShow = false
        this.Showcase.breachRShow = true
      }

      if(this.datainfo.proInfoTradeStructure.ratingResultFileList.length != 0 && this.datainfo.proInfoTradeStructure.ratingResult.length == 0){
        this.Showcase.ratingL = 6
        this.Showcase.ratingR = 0
        this.Showcase.ratingLShow = true
        this.Showcase.ratingRShow = false
      }else if(this.datainfo.proInfoTradeStructure.ratingResultFileList.length == 0 && this.datainfo.proInfoTradeStructure.ratingResult.length != 0){
        this.Showcase.ratingL = 0
        this.Showcase.ratingR = 6
        this.Showcase.ratingLShow = false
        this.Showcase.ratingRShow = true
      }

       if(this.datainfo.proInfoTradeStructure.isOriginalListFileList.length != 0 && this.datainfo.proInfoTradeStructure.isOriginalList.length == 0){
        this.Showcase.isOriginaL = 6
        this.Showcase.isOriginaR = 0
        this.Showcase.isOriginaLShow = true
        this.Showcase.isOriginaRShow = false
      }else if(this.datainfo.proInfoTradeStructure.isOriginalListFileList.length == 0 && this.datainfo.proInfoTradeStructure.isOriginalList.length != 0){
        this.Showcase.isOriginaL = 0
        this.Showcase.isOriginaR = 6
        this.Showcase.isOriginaLShow = false
        this.Showcase.isOriginaRShow = true
      }
      if(this.datainfo.proInfoTradeStructure.isOriginalBuybackFileList.length != 0 && this.datainfo.proInfoTradeStructure.isOriginalBuyback.length == 0){
        this.Showcase.isOriginalL = 6
        this.Showcase.isOriginalR = 0
        this.Showcase.isOriginalLShow = true
        this.Showcase.isOriginalRShow = false
      }else if(this.datainfo.proInfoTradeStructure.isOriginalBuybackFileList.length == 0 && this.datainfo.proInfoTradeStructure.isOriginalBuyback.length != 0){
        this.Showcase.isOriginalL = 0
        this.Showcase.isOriginalR = 6
        this.Showcase.isOriginalLShow = false
        this.Showcase.isOriginalRShow = true
      }
      if(this.datainfo.proInfoTradeStructure.breachCompareFileList.length != 0 && this.datainfo.proInfoTradeStructure.breachCompare.length == 0){
        this.Showcase.breachCompareL = 6
        this.Showcase.breachCompareR = 0
        this.Showcase.breachCompareLShow = true
        this.Showcase.breachCompareRShow = false
      }else if(this.datainfo.proInfoTradeStructure.breachCompareFileList.length == 0 && this.datainfo.proInfoTradeStructure.breachCompare.length != 0){
        this.Showcase.breachCompareL = 0
        this.Showcase.breachCompareR = 6
        this.Showcase.breachCompareLShow = false
        this.Showcase.breachCompareRShow = true
      }
      if(this.datainfo.proInfoTradeStructure.totalFundAmountFileList.length != 0 && this.datainfo.proInfoTradeStructure.totalFundAmount.length == 0){
        this.Showcase.totalFundL = 6
        this.Showcase.totalFundR = 0
        this.Showcase.totalFundLShow = true
        this.Showcase.totalFundRShow = false
      }else if(this.datainfo.proInfoTradeStructure.totalFundAmountFileList.length == 0 && this.datainfo.proInfoTradeStructure.totalFundAmount.length != 0){
        this.Showcase.totalFundL = 0
        this.Showcase.totalFundR = 6
        this.Showcase.totalFundLShow = false
        this.Showcase.totalFundRShow = true
      }
      if(this.datainfo.proInfoTradeStructure.seedFundProportionFileList.length != 0 && this.datainfo.proInfoTradeStructure.seedFundProportion.length == 0){
        this.Showcase.seedFundL = 6
        this.Showcase.seedFundR = 0
        this.Showcase.seedFundLShow = true
        this.Showcase.seedFundRShow = false
      }else if(this.datainfo.proInfoTradeStructure.seedFundProportionFileList.length == 0 && this.datainfo.proInfoTradeStructure.seedFundProportion.length != 0){
        this.Showcase.seedFundL = 0
        this.Showcase.seedFundR = 6
        this.Showcase.seedFundLShow = false
        this.Showcase.seedFundRShow = true
      }
      if(this.datainfo.proInfoTradeStructure.estimatedManagementFeeFileList.length != 0 && this.datainfo.proInfoTradeStructure.estimatedManagementFee.length == 0){
        this.Showcase.estimatedL = 6
        this.Showcase.estimatedR = 0
        this.Showcase.estimatedLShow = true
        this.Showcase.estimatedRShow = false
      }else if(this.datainfo.proInfoTradeStructure.estimatedManagementFeeFileList.length == 0 && this.datainfo.proInfoTradeStructure.estimatedManagementFee.length != 0){
        this.Showcase.estimatedL = 0
        this.Showcase.estimatedR = 6
        this.Showcase.estimatedLShow = false
        this.Showcase.estimatedRShow = true
      }
       if(this.datainfo.proInfoTradeStructure.scopeInvestmentFileList.length != 0 && this.datainfo.proInfoTradeStructure.scopeInvestment.length == 0){
        this.Showcase.scopeL = 6
        this.Showcase.scopeR = 0
        this.Showcase.scopeLShow = true
        this.Showcase.scopeRShow = false
      }else if(this.datainfo.proInfoTradeStructure.scopeInvestmentFileList.length == 0 && this.datainfo.proInfoTradeStructure.scopeInvestment.length != 0){
        this.Showcase.scopeL = 0
        this.Showcase.scopeR = 6
        this.Showcase.scopeLShow = false
        this.Showcase.scopeRShow = true
      }
      if(this.datainfo.proInfoTradeStructure.riskArrangementFileList.length != 0 && this.datainfo.proInfoTradeStructure.riskArrangement.length == 0){
        this.Showcase.riskL = 6
        this.Showcase.riskR = 0
        this.Showcase.riskLShow = true
        this.Showcase.riskRShow = false
      }else if(this.datainfo.proInfoTradeStructure.riskArrangementFileList.length == 0 && this.datainfo.proInfoTradeStructure.riskArrangement.length != 0){
        this.Showcase.riskL = 0
        this.Showcase.riskR = 6
        this.Showcase.riskLShow = false
        this.Showcase.riskRShow = true
      }
    }
  }
}
</script>

<style lang="less" scoped>
@border_color: rgba(204, 204, 204, 0.5);
@bg_color: rgba(255, 255, 255, 0.5);
.product_layout {
  padding: 40px;
  color: #212121;
  width: 1041px;
  margin: 0 auto;
  button {
    height: 40px;
    font-size: 14px;
  }
}
.product_name {
  font-size: 24px;
  font-weight: 500;
  line-height: 40px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
}
.product_team {
  margin-top: 30px;
  color: #626262;
  font-size: 16px;
  line-height: 40px;
  height: auto;
}
.tab_name {
  font-size: 16px;
  font-weight: 500;
  line-height: 23px;
  margin-top: 25px;
}
.product_info {
  width: 100%;
  border: 1px solid @border_color;
  border-collapse: collapse;
  border-spacing: 0;
  font-size: 14px;
  margin-bottom: 100px;
  tr {
    border-bottom: 1px solid @border_color;
    td {
      height: 50px;
      text-align: center;
      border-right: 1px solid #ccc;
      color: #626262;
    }
    .info_key {
      width: 174px;
      background: rgba(255, 255, 255, 0.4);
      padding: 10px 0px;
    }
    input {
      width: 100%;
      line-height: 50px;
      border: 0;
      padding: 0px 7px;
      &:focus {
        outline: #57a3f3;
      }
    }
    .txt {
      padding: 0 7px;
      color: #212121;
    }
    textarea {
      width: 100%;
      height: 95%;
      border: 0;
      padding: 5px;
      &:focus {
        outline: 0;
      }
    }
  }
  .ivu-radio-wrapper {
    margin-right: 20px;
  }
}
.upload_tips {
  color: red;
  display: inline-block;
  margin-left: 20px;
  position: absolute;
  top: 11px;
  left: 80px;
}
.file_item {
  margin-top: 10px;
  span,
  a {
    margin: 0 10px;
    vertical-align: middle;
  }
  .iconfont {
    vertical-align: middle;
    font-size: 22px;
  }
  .icon-file_pdf {
    color: #ff545c;
  }
  .icon-shanchu_o {
    color: #72808c;
    cursor: pointer;
    &:hover {
      color: red;
    }
  }
}
.history_list {
  border: 1px solid #ccc;
  margin-top: 20px;
  .history_item {
    padding: 20px 0 20px 30px;
    font-size: 16px;
    color: #818181;
    border-bottom: 1px solid #ccc;
    &:last-child {
      border: 0;
    }
  }
  .disagree {
    color: #f92c2c;
  }
}
textarea{
  resize: none;
}
.btntijiao{
  border-radius:5px;
  position: relative;
  top: 0px;
}
.btnselect{
  height: 50px;
}
[v-cloak] {
  display: none;
}
.textErr{
  width: 330px;
  height: 40px;
}
.addImg {
  width: 20px;
  height: 20px;
  margin-left: 320px;
  cursor: pointer;
  img {
    display: block;
    width: 100%;
    height: 100%;
  }
}
.reduce{
  width: 20px;
  height: 20px;
  position: absolute;
  right: 400px;
  margin-top: -20px;
  cursor: pointer;
    img {
    display: block;
    width: 100%;
    height: 100%;
  }
}
</style>
