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
      color: #666;
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
textarea {
  resize: none;
}
.btntijiao {
  border-radius: 5px;
  position: relative;
  top: 0px;
}
.btnselect {
  height: 50px;
  .ivu-select-selection {
    border: none;
  }
}
[v-cloak] {
  display: none;
}
.textErr {
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
.text_line {
  display: block;
  margin-left: 5px;
  text-decoration: line-through;
  color: red;
  text-align: left;
}
.reduce {
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
<!-- 项目详情
@props
 isEdit 是否为可编辑状态
 id 编辑id
 fundInfo 预览or编辑数据
 -->
<template>
  <div class="product_layout">
    <Row class="t-c product_name">{{baseInfo.proName}}项目简介表</Row>
    <Row class="product_team">
      <p class="f-l">
        项目团队：
        <span v-if="isEdit" style="color:#313131" v-cloak>{{baseInfo.teamList}}</span>
        <Select
          v-model="model10"
          filterable
          multiple
          label-in-value
          remote
          style="width:285px"
          v-else
        >
          <Option
            v-for="item in label"
            :value="item.userName"
            :key="item.index"
            :label="item.fullName"
          >
            <span>{{item.fullName}}</span>
            <span style="float:right;color:#ccc">{{item.userName}}</span>
          </Option>
        </Select>
      </p>
      <p class="f-r">
        日期：
        <span>{{baseInfo.date}}</span>
      </p>
    </Row>
    <Row class="tab_name">项目基本信息</Row>
    <Row style="margin-top:15px">
      <table class="product_info">
        <tbody>
          <tr>
            <td class="info_key">项目名称</td>
            <td colspan="6">
                <span v-show="changeData.proName" class="text_line">
                  {{changeData.proName}}
                  <br>
                </span>
              <input type="text" v-model="baseInfo.proName" :readonly="isEdit">
            </td>
          </tr>
          <tr>
            <td class="info_key">业务模式</td>
            <td colspan="3">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.proModel" class="text_line">
                    {{changeData.proModel}}
                    <br>
                  </span>
                  {{baseInfo.proModel}}
                </div>
              </template>
              <CheckboxGroup v-model="baseInfo.proModel" v-else>
                <Checkbox label="存量债转">存量债转</Checkbox>
                <Checkbox label="新增助贷">新增助贷</Checkbox>
              </CheckboxGroup>
            </td>
            <td class="info_key">行业分类</td>
            <td colspan="2">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.category" class="text_line">
                    {{changeData.category}}
                    <br>
                  </span>
                  {{baseInfo.category}}
                </div>
              </template>
              <input type="text" v-model="baseInfo.category" v-else>
            </td>
          </tr>
          <tr>
            <td class="info_key">基础资产类型</td>
            <td colspan="3">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.assertType" class="text_line">
                    {{changeData.assertType}}
                    <br>
                  </span>
                  {{baseInfo.assertType}}
                </div>
              </template>
              <input type="text" v-model="baseInfo.assertType" v-else>
            </td>
            <td class="info_key">基础资产情况</td>
            <td colspan="3">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.assertSituation" class="text_line">
                    {{changeData.assertSituation}}
                    <br>
                  </span>
                  {{baseInfo.assertSituation}}
                </div>
              </template>
              <input type="text" v-model="baseInfo.assertSituation" v-else>
            </td>
          </tr>
          <tr>
            <td class="info_key">资产服务机构</td>
            <td colspan="6">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.assetSerAgency" class="text_line">
                    {{changeData.assetSerAgency}}
                    <br>
                  </span>
                  {{baseInfo.assetSerAgency}}
                </div>
              </template>
              <textarea v-model="baseInfo.assetSerAgency" v-else style="height:auto;"></textarea>
            </td>
          </tr>
          <tr>
            <td class="info_key">劣后投资人</td>
            <td colspan="6">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.inferiorInvestor" class="text_line">
                    {{changeData.inferiorInvestor}}
                    <br>
                  </span>
                  {{baseInfo.inferiorInvestor}}
                </div>
              </template>
              <textarea v-model="baseInfo.inferiorInvestor" v-else style="height:auto;"></textarea>
            </td>
          </tr>
          <tr>
            <td class="info_key">差额补足机构</td>
            <td colspan="6">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.diffAgency" class="text_line">
                    {{changeData.diffAgency}}
                    <br>
                  </span>
                  {{baseInfo.diffAgency}}
                </div>
              </template>
              <textarea v-model="baseInfo.diffAgency" v-else style="height:auto;"></textarea>
            </td>
          </tr>
          <tr>
            <td class="info_key">12个月内预计
              <br>合作规模(亿)
            </td>
            <td colspan="2">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.coopScale" class="text_line">
                    {{changeData.coopScale}}
                    <br>
                  </span>
                  {{baseInfo.coopScale}}
                </div>
              </template>
              <input type="tel" v-model="baseInfo.coopScale" v-else>
            </td>
            <td class="info_key">已合作规模</td>
            <td colspan="1">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.scaleCooperation" class="text_line">
                    {{changeData.scaleCooperation}}
                    <br>
                  </span>
                  {{baseInfo.scaleCooperation}}
                </div>
              </template>
              <input type="tel" v-model="baseInfo.scaleCooperation" v-else>
            </td>
            <td class="info_key">存量余额</td>
            <td colspan="1">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.stockBalance" class="text_line">
                    {{changeData.stockBalance}}
                    <br>
                  </span>
                  {{baseInfo.stockBalance}}
                </div>
              </template>
              <input type="tel" v-model="baseInfo.stockBalance" v-else>
            </td>
          </tr>
          <tr>
            <td class="info_key">是否同意框架合作</td>
            <td colspan="1">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.coopState" class="text_line">
                    {{changeData.coopState}}
                    <br>
                  </span>
                  {{baseInfo.coopState}}
                </div>
              </template>
              <RadioGroup v-model="baseInfo.coopState" v-else>
                <Radio label="是"></Radio>
                <Radio label="否"></Radio>
              </RadioGroup>
            </td>
            <td class="info_key">是否同意系统对接</td>
            <td colspan="1">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.systemAbutmentState" class="text_line">
                    {{changeData.systemAbutmentState}}
                    <br>
                  </span>
                  {{baseInfo.systemAbutmentState}}
                </div>
              </template>
              <RadioGroup v-model="baseInfo.systemAbutmentState" v-else>
                <Radio label="是"></Radio>
                <Radio label="否"></Radio>
              </RadioGroup>
            </td>
            <td class="info_key">是否需要新分享投资</td>
            <td colspan="2">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.investComment" class="text_line">
                    {{changeData.investComment}}
                    <br>
                  </span>
                  {{baseInfo.investComment}}
                </div>
              </template>
              <RadioGroup v-model="baseInfo.investComment" v-else>
                <Radio label="是"></Radio>
                <Radio label="否"></Radio>
              </RadioGroup>
            </td>
          </tr>
          <tr v-show="weshareShow">
            <td class="info_key">投资内容、
              <br>利率、期限
            </td>
            <td colspan="6">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.whetherWeshareInvest" class="text_line">
                    {{changeData.whetherWeshareInvest}}
                    <br>
                  </span>
                  {{baseInfo.whetherWeshareInvest}}
                </div>
              </template>
              <textarea v-model="baseInfo.whetherWeshareInvest" v-else style="height: auto;"></textarea>
            </td>
          </tr>
          <tr>
            <td class="info_key">原始权益人</td>
            <td colspan="3">
              <!-- <input type="text" v-model="baseInfo.originalRightsHolder" :readonly="isEdit"> -->
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.originalRightsHolder" class="text_line">
                    {{changeData.originalRightsHolder}}
                    <br>
                  </span>
                  {{baseInfo.originalRightsHolder}}
                </div>
              </template>
              <Select v-model="baseInfo.originalRightsHolder" filterable class="btnselect" v-else>
                <Option value=" ">
                  <div @click="addPepole">新增权益人</div>
                </Option>
                <Option
                  v-for="(item,index) in model9"
                  :value="item.usename"
                  :key="index"
                  :label="item.usename"
                >
                  <span>{{item.usename}}</span>
                </Option>
              </Select>
            </td>
            <td class="info_key">评级情况</td>
            <td colspan="3">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.holderRatingSituation" class="text_line">
                    {{changeData.holderRatingSituation}}
                    <br>
                  </span>
                  {{baseInfo.holderRatingSituation}}
                </div>
              </template>
              <input type="text" v-model="baseInfo.holderRatingSituation" v-else>
            </td>
          </tr>
          <tr>
            <td class="info_key">外部增信机构</td>
            <td colspan="3">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.externalEnhancementAgencies" class="text_line">
                    {{changeData.externalEnhancementAgencies}}
                    <br>
                  </span>
                  {{baseInfo.externalEnhancementAgencies}}
                </div>
              </template>
              <textarea v-model="baseInfo.externalEnhancementAgencies" v-else style="height: auto;"></textarea>
            </td>
            <td class="info_key">评级情况</td>
            <td colspan="3">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.ratingSituation" class="text_line">
                    {{changeData.ratingSituation}}
                    <br>
                  </span>
                  {{baseInfo.ratingSituation}}
                </div>
              </template>
              <input type="text" v-model="baseInfo.ratingSituation" v-else>
            </td>
          </tr>
          <tr>
            <td class="info_key" v-if="isEdit" :rowspan="layerListLength">分层结构</td>
            <td class="info_key" :rowspan="LengthRow" v-else>分层结构</td>
            <td class="info_key">分级</td>
            <td class="info_key">发行金额(万元)</td>
            <td class="info_key">占比(%)</td>
            <td class="info_key">发行利率(%)</td>
            <td class="info_key">产品期限(月)</td>
            <td class="info_key">加权平均期限(年)</td>
          </tr>
          <template v-if="isEdit">
            <!-- 分层结构做对比操作 -->
           <template v-if="showDataList">
              <tr v-for="(item,index) in layerListObj" :key="index">
              <td>
                <div class="t-l txt">
                  <span v-show="item.changelevel != item.level" class="text_line">
                    {{item.changelevel}}
                    <br>
                  </span>
                  {{item.level}}
                </div>
              </td>
              <td>
                <div class="t-l txt">
                  <span v-show="item.changeamount != item.amount" class="text_line">
                    {{item.changeamount}}
                    <br>
                  </span>
                  {{item.amount}}
                </div>
              </td>
              <td>
                <div class="t-l txt">
                  <span v-show="item.changeproportion != item.proportion" class="text_line">
                    {{item.changeproportion}}
                    <br>
                  </span>
                  {{item.proportion}}
                </div>
              </td>
              <td>
                <div class="t-l txt">
                  <span v-show="item.changeissueRate != item.issueRate" class="text_line">
                    {{item.changeissueRate}}
                    <br>
                  </span>
                  {{item.issueRate}}
                </div>
              </td>
              <td>
                <div class="t-l txt">
                  <span v-show="item.changeproTerm != item.proTerm" class="text_line">
                    {{item.changeproTerm}}
                    <br>
                  </span>
                  {{item.proTerm}}
                </div>
              </td>
              <td>
                <div class="t-l txt">
                  <span v-show="item.changeaverageTerm != item.averageTerm" class="text_line">
                    {{item.changeaverageTerm}}
                    <br>
                  </span>
                  {{item.averageTerm}}
                </div>
              </td>
            </tr>
           </template>
           <template v-else>
              <tr v-for="(item,index) in baseInfo.layerList" :key="index">
              <td>
                <div class="t-l txt">
                  {{item.level}}
                </div>
              </td>
              <td>
                <div class="t-l txt">
                  {{item.amount}}
                </div>
              </td>
              <td>
                <div class="t-l txt">
                  {{item.proportion}}
                </div>
              </td>
              <td>
                <div class="t-l txt">
                  {{item.issueRate}}
                </div>
              </td>
              <td>
                <div class="t-l txt">
                  {{item.proTerm}}
                </div>
              </td>
              <td>
                <div class="t-l txt">
                  {{item.averageTerm}}
                </div>
              </td>
            </tr>
           </template>
          </template>
          <template v-else>
            <tr v-for="(item,index) in layerList" :key="index">
              <td>
                <input type="text" v-model="item.level" :readonly="isEdit">
              </td>
              <td>
                <input type="Number" v-model="item.amount" @mousewheel.prevent :readonly="isEdit">
              </td>
              <td>
                <input type="Number" v-model="item.proportion" @mousewheel.prevent :readonly="isEdit">
              </td>
              <td>
                <input type="tel" v-model="item.issueRate" :readonly="isEdit">
              </td>
              <td>
                <input type="text" v-model="item.proTerm" :readonly="isEdit">
              </td>
              <td>
                <input type="tel" v-model="item.averageTerm" :readonly="isEdit">
              </td>
            </tr>
          </template>
          <tr>
            <td colspan="6" v-show="!isEdit">
              <div class="addImg" ref="add" @click="AddDom">
                <img src="@/assets/images/round_add.png" alt>
              </div>
              <div class="reduce" ref="reduce" @click="reduce">
                <img src="@/assets/images/round_reduce.png" alt>
              </div>
            </td>
          </tr>
          <tr>
            <td class="info_key">发行金额(含次级)
              <br>亿元
            </td>
            <td colspan="2">
                <span v-show="changeData.totalAmount" class="text_line" style="text-align: left;">
                  {{changeData.totalAmount}}
                  <br>
                </span>
              <input type="tel" v-model="baseInfo.totalAmount" :readonly="isEdit">
            </td>
            <td class="info_key">综合成本</td>
            <td colspan="1">
              <span v-show="changeData.cost" class="text_line" style="text-align: left;">
                  {{changeData.cost}}
                  <br>
                </span>
              <input type="text" v-model="baseInfo.cost" :readonly="isEdit">
            </td>
            <td class="info_key">产品期限(月)</td>
            <td colspan="1">
              <span v-show="changeData.term" class="text_line" style="text-align: left;">
                  {{changeData.term}}
                  <br>
                </span>
              <input type="tel" v-model="baseInfo.term" :readonly="isEdit">
            </td>
          </tr>
          <tr>
            <td colspan="7">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.requirements" class="text_line">
                    {{changeData.requirements}}
                    <br>
                  </span>
                  <span style="position: relative;left:50%">{{baseInfo.requirements}}</span>
                </div>
              </template>
              <RadioGroup v-model="baseInfo.requirements" v-else>
                <Radio label="静态池"></Radio>
                <Radio label="循环结构"></Radio>
              </RadioGroup>&nbsp;&nbsp;&nbsp;&nbsp;
              <span v-show="isshow">
                循环期【
                <input
                  type="text"
                  style="width:50px;text-align:center"
                  v-model="baseInfo.cyclePeriod"
                  :readonly="isEdit"
                >】+ 摊还期【
                <input
                  :readonly="isEdit"
                  type="text"
                  style="width:50px;text-align:center"
                  v-model="baseInfo.amortizationPeriod"
                >】个月
              </span>
            </td>
          </tr>
          <tr>
            <td class="info_key">分销渠道</td>
            <td colspan="6">
              <!-- <input type="text" v-model="baseInfo.channel" :readonly="isEdit"> -->
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.channel" class="text_line">
                    {{changeData.channel}}
                    <br>
                  </span>
                  {{baseInfo.channel}}
                </div>
              </template>
              <Select
                v-model="baseInfo.channel"
                style="width:100%"
                :readonly="isEdit"
                v-else
                class="channel"
              >
                <Option
                  v-for="item in model8"
                  :value="item.channel"
                  :key="item.channel"
                >{{ item.channel }}</Option>
              </Select>&nbsp;&nbsp;&nbsp;&nbsp;
              <span v-show="channel">
                理财通分【
                <input
                  type="text"
                  style="width:50px;text-align:center"
                  v-model="baseInfo.packageNum"
                  :readonly="isEdit"
                >】包发行，单包规模【
                <input
                  :readonly="isEdit"
                  type="text"
                  style="width:100px;text-align:center;minWidth:50px;"
                  v-model="baseInfo.packageScale"
                  onkeyup="this.value=this.value.replace(/[^0-9-]+/,'');"
                >】万元
              </span>
            </td>
          </tr>
          <tr>
            <td class="info_key">项目前端收入</td>
            <td colspan="3">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.frontIncome" class="text_line">
                    {{changeData.frontIncome}}
                    <br>
                  </span>
                  {{baseInfo.frontIncome}}
                </div>
              </template>
              <textarea name id rows="5" v-model="baseInfo.frontIncome" v-else></textarea>
            </td>
            <td class="info_key">项目后端收入</td>
            <td colspan="2">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.afterIncome" class="text_line">
                    {{changeData.afterIncome}}
                    <br>
                  </span>
                  {{baseInfo.afterIncome}}
                </div>
              </template>
              <textarea name id rows="5" v-model="baseInfo.afterIncome" v-else></textarea>
            </td>
          </tr>
          <tr>
            <td class="info_key">项目团队意见</td>
            <td colspan="6">
              <template v-if="isEdit">
                <div class="t-l txt">
                  <span v-show="changeData.projectTeamOpinion" class="text_line">
                    {{changeData.projectTeamOpinion}}
                    <br>
                  </span>
                  {{baseInfo.projectTeamOpinion}}
                </div>
              </template>
              <textarea
                rows="5"
                placeholder="项目亮点、项目调整、前期与腾讯业务及风控团队交流情况等"
                v-model="baseInfo.projectTeamOpinion"
                v-else
                style="height: 100px;"
              ></textarea>
            </td>
          </tr>

          <template v-if="reviewInfo">
            <tr v-for="(item,index) in baseInfo.resultList" :key="index">
              <td class="info_key">{{item.resultType}}</td>
              <td>{{item.resultState}}</td>
              <td colspan="5">{{item.result}}</td>
            </tr>
          </template>
        </tbody>
      </table>
    </Row>
    <Row class="tab_name" v-show="EnclosureShow">{{"附件列表"}}</Row>
    <Row class="tab_name" v-show="!EnclosureShow">{{isEdit ? '项目附件' : '上传附件'}}</Row>

    <div v-show="!pdfshow">
      <div
        class="file_item"
        v-for="(item,index) in baseInfo.fileList"
        v-show="!EnclosureShow"
        :key="index"
      >
        <i class="iconfont icon-file_pdf"></i>
        <template v-if="!isEdit">
          <span style>{{item.fileName}}</span>
          <i class="iconfont icon-shanchu_o" title="删除" @click="removeFile(index)"></i>
        </template>
        <template v-else>
          <a @click="displayPdf(item)">{{item.fileName}}</a>
        </template>
      </div>
    </div>
    <Row style="margin-top:15px">
      <template v-if="!isEdit">
        <Upload :before-upload="handleUpload" action :format="['pdf']">
          <Button icon="ios-cloud-upload-outline">上传</Button>
        </Upload>
        <span class="upload_tips">*仅支持PDF格式文件</span>
      </template>

      <!-- 附件列表 -->
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
    </Row>
    <div v-if="!showAll">
      <template v-if="isEdit">
        <div v-show="Exhibition">
          <Row class="tab_name">审批历史</Row>
          <div class="history_list">
            <template v-if="historyAudit.length > 0">
              <Row
                class="history_item"
                v-for="(item,index) in historyAudit"
                :key="index"
                :class="item.result== '不同意' ? 'disagree' : ''"
              >
                <Col v-if="item.resultType != '提交申请'" span="4" style='width: 300px;'>
                  审批节点：
                  <span style="color:#212121">{{item.approvalNode}}</span>
                </Col>
                <br>
                <Col
                  style="margin-top: -20px;width: 200px;"
                  v-if="item.resultType == '提交申请'"
                  span="4"
                >
                  发起人：
                  <span style="color:#212121;">{{item.operator}}</span>
                </Col>
                <Col v-if="item.resultType != '提交申请'" span="4" style="width: 180px;">
                  审批人：
                  <span style="color:#212121;">{{item.operator}}</span>
                </Col>
                <Col
                  v-if="item.resultType != '提交申请'"
                  span="4"
                  style="position: relative;top: -25px;left: 50px;"
                >
                  审批结果：
                  <span
                    style="color:#212121;display: block;position: relative;top: -25px;left: 75px;color: red;"
                    v-if="item.resultState == '不同意'"
                  >{{item.resultState}}</span>
                  <span
                    style="color:#212121;display: block;position: relative;top: -25px;left: 75px;"
                    v-else
                  >{{item.resultState}}</span>
                </Col>
                <Col
                  v-if="item.resultType != '提交申请' && item.result.length !=0"
                  span="7"
                  class="text"
                >
                  审批意见：
                  <span style="color:#212121">{{item.result}}</span>
                </Col>
                <Col v-if="item.resultType == '提交申请'" span="7" class="text">
                  备注：
                  <span style="color:#212121">{{item.result}}</span>
                </Col>
                <Col
                  style="margin-top: -20px;"
                  v-if="item.resultType == '提交申请'"
                  span="8"
                  offset="1"
                  class="data"
                >
                  发起日期：
                  <span style="color:#212121">{{item.date}}</span>
                </Col>
                <Col v-if="item.resultType != '提交申请'" span="8" offset="1" class="data" style="margin-top: -20px;">
                  审批日期：
                  <span style="color:#212121">{{item.date}}</span>
                </Col>
              </Row>
            </template>
            <Row v-else class="t-c history_item">暂无历史审批</Row>
          </div>
        </div>
        <Row class="margin-top-20" v-if="!isDetail">
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
                <td class="info_key">动态选择上会人员</td>
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
      </template>
      <Row style="margin:30px 0 0 0;text-align:right" v-else>
        <Button
          type="info"
          style="border-radius: 5px;position: relative;left: -80px;"
          @click="Preservation"
          v-show="!showResult"
        >保存数据</Button>
        <Button
          type="primary"
          class="margin-right-20;"
          style="border-radius: 5px;"
          @click="addAudit(value='modify')"
          v-show="showResult"
        >提交修改</Button>
        <Button
          type="primary"
          class="margin-right-20;"
          style="border-radius: 5px;"
          @click="addAudit(value = 'examine')"
          v-show="!showResult"
        >提交审核</Button>
      </Row>
    </div>
    <div v-else>
      <template v-if="!EnclosureShow">
        <Table :columns="Attachmentaudit" :data="AttachmentList"></Table>
      </template>
      <Row style="text-align:right;">
        <Button
          type="primary"
          class="margin-right-20 btntijiao"
          style="margin-top: 85px;"
          @click="Material"
        >提交</Button>
      </Row>
    </div>
  </div>
</template>

<script>
import axios from "axios";
import mdContract from "./contract.md.vue";
export default {
  props: {
    isEdit: {
      type: Boolean,
      default: false
    },
    isDetail: {
      type: Boolean,
      defaul: false
    },
    id: {
      type: String,
      default: ""
    },
    taskId: {
      type: String,
      default: ""
    },
    processId: {
      type: String,
      default: ""
    },
    baseInfo: {
      type: Object,
      default: function() {
        return {
          date: new Date().format("yyyy/MM/dd"), //日期
          team: "", //团队
          proName: "", //项目名称
          proModel: [], //业务模式
          category: "", //行业分类
          assertSituation: "", //基础资产情况
          assertType: "",
          assetSerAgency: "", //资产服务机构
          coopScale: "", //委托人预计全年合作规模
          coopState: "", //是否同意框架合作及系统对接
          totalAmount: "", //发行金额含次级
          cost: "", //综合成本
          term: "", //期限
          channel: "理财通", //分销渠道
          packageNum: "", //理财通分包
          packageScale: "", //单包规模
          frontIncome: "", //项目前端收入
          afterIncome: "", //项目后端收入
          originalRightsHolder: "", //原始权益人
          holderRatingSituation: "", //评级情况
          externalEnhancementAgencies: "", //外部增信机构
          ratingSituation: "", //评级情况
          cyclePeriod: "", //循环期
          requirements: "静态池", //静态池，循环结构
          amortizationPeriod: "", //摊还期
          projectTeamOpinion: "", //项目团队意见
          fileList: [],
          layerList: [],
          systemAbutmentState: "", //是否同意系统对接
          scaleCooperation: "", //已合作规模
          stockBalance: "", //存量余额
          whetherWeshareInvest: "", //投资内容、利率、期限
          coopState: "", //是否同意框架合作
          investComment: "", //是否需要新分享投资
          inferiorInvestor: "", //劣后投资人
          diffAgency: "" //差额补足机构
        };
      }
    }
  },
  computed: {
    layerListLength() {
      if (this.showDataList) {
        return this.layerListObj.length +1; 
      }else{
        return this.baseInfo.layerList ? this.baseInfo.layerList.length + 1 : 1;
      }
      
    },
    reviewInfo() {
      return this.baseInfo.resultList ? true : false;
    },
    isshow() {
      if (this.baseInfo.requirements === "循环结构") {
        return true;
      } else {
        return false;
      }
    },
    channel() {
      return this.baseInfo.channel === "理财通" ? true : false;
    },
    weshareShow() {
      return this.baseInfo.investComment === "是" ? true : false;
    }
  },
  data() {
    return {
      label: [],
      layerListObj:[],
      LengthRow: 7,
      Conference:[],//上会名单
      fixed:"",//固定名单
      optional:[],//动态名单
      Fixmember:[],//固定委员
      applyCategory:[],
      ApproverMberList:[],
      Decision: false,
      ResultData:false,
      model10: [], //项目团队名称
      Datashow: false,
      model9: [],
      model8: [],
      orderId: "",
      isreader: "",
      EnclosureShow: false, //是否显示附件列表
      pdfshow: true,
      SubId: [], //附件列表的ID
      dataTable: [], //附件列表
      status: "", //附件状态
      queryid: "",
      showAll: false,
      showDataList: false,
      Exhibition: true,
      showResult: false,
      Agreed: false,
      file: false,
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
      fileList: [], //文件集合
      historyAudit: [], //历史审批,
      checkTips: {
        // date: "日期不能为空",
        // team: "项目团队不能为空",
        // proName: "项目名称不能为空",
        // proModel: "请选择业务模式",
        // category: "行业分类不能为空",
        // coopState: "请选择是否同意框架合作及系统对接",
        // externalEnhancementAgencies: "外部增信机构不能为空",
        // ratingSituation: "评级情况不能为空",
        // assetSerAgency: "资产服务机构劣后投资人不能为空",
        coopScale: "12个月内预计合作规模应填写11位以内正整数",
        // amount: "发行金额应填写11位以内正整数",
        proportion: "占比应填写11位以内正整数",
        issueRate: "发行利率不能为空",
        proTerm: "产品期限应填写11位以内正整数",
        averageTerm: "加权平均期限应填写11位以内正整数",
        totalAmount: "发行金额(含次级)亿元应填写11位以内正整数",
        cost: "综合成本应填写11位以内正整数",
        term: "产品年限应填写11位以内正整数",
        cyclePeriod: "循环期不能为空",
        amortizationPeriod: "摊还期不能为空",
        assertType: "基础资产类型不能为空",
        assertSituation: "基础资产情况不能为空",
        originalRightsHolder: "原始权益人不能为空",
        holderRatingSituation: "评级情况不能为空",
        packageNum: "理财通分包不能为空",
        packageScale: "单包规模不能为空",
        inferiorInvestor: "劣后投资人不能为空",
        diffAgency: "差额补足机构不能为空",
        assetSerAgency: "资产服务机构不能为空"
      },
      auditStatus: "",
      auditInfo: "",
      checkStatus: true, //附件操作状态
      back: false,
      changeData: "",
      pagerData: {},
      id: "",
      value: "",
      proId: "",
      textareaVale: "",
      reviewFlase: "",
      ApproverMber: [], //选择前置审批人员
      Enclosure: [
        {
          type: "expand",
          width: 50,
          render: (h, params) => {
            if (
              params.row.backReason == null ||
              params.row.backReason == undefined
            ) {
              return h(
                "div",
                {
                  props: {
                    row: params.row
                  }
                },
                `审批意见:无`
              );
            } else {
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
                      let url = "/api/snaker/flow/attach/" + index + "/" + "-1";
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
            } else if (params.row.proAttachType == "03") {
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
            } else if (params.row.proAttachType == "04") {
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
            } else if (params.row.proAttachType == "05") {
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
          render: (h, params) => {
            return h("div", [
              h(
                "span",
                {
                  style: {
                    display: "block",
                    width: "175px",
                    marginLeft: "-15px"
                  }
                },
                params.row.updateTime
              )
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
                        params.row.optType = "1";
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
      dataTableListshow: [],
      Attachmentaudit: [
        {
          type: "expand",
          width: 50,
          render: (h, params) => {
            if (
              params.row.backReason == null ||
              params.row.backReason == undefined
            ) {
              return h(
                "div",
                {
                  props: {
                    row: params.row
                  }
                },
                `审批意见:无`
              );
            } else {
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
                      let url = "/api/snaker/flow/attach/" + index + "/" + "-1";
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
                      display: "block",
                      width: "30px"
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
            } else if (params.row.proAttachType == "03") {
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
            } else if (params.row.proAttachType == "04") {
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
            } else if (params.row.proAttachType == "05") {
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
          render: (h, params) => {
            return h("div", [
              h(
                "span",
                {
                  style: {
                    display: "block",
                    width: "175px",
                    fontSize: "12px"
                  }
                },
                params.row.updateTime
              )
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
            } else if (
              params.row.attachTaskState == "true" ||
              params.row.attachTaskState == "01"
            ) {
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
            } else if (
              params.row.attachTaskState == "false" ||
              params.row.attachTaskState == "02"
            ) {
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
                        this.$set(
                          this.AttachmentList[params.index],
                          "attachTaskState",
                          "false"
                        );
                        this.$set(
                          this.AttachmentList[params.index],
                          "Comments",
                          "01"
                        );
                        this.$Modal.confirm({
                          render: h => {
                            return h("Input", {
                              props: {
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
                          this.AttachmentList[params.index],
                          "attachTaskState",
                          "true"
                        );
                        this.$set(
                          this.AttachmentList[params.index],
                          "Comments",
                          "01"
                        );
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
                        position: "relative",
                        top: "10px",
                        left: "0px"
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
                        position: "relative",
                        top: "-10px",
                        left: "55px"
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
      AttachmentList: []
    };
  },
  name: "page-one",
  components: {
    mdContract
  },
  mounted() {
    if (this.isEdit) {
      this.getHistoryAudit();
    }
    if (!this.isEdit) {
      let UseId = localStorage.getItem("user");
      this.model10.push(UseId);
    }
    //我发起的页面点击重新发起的时候请求数据,并且做相对应的赋值操作
    if (this.$route.query.orderId) {
      this.orderId = this.$route.query.orderId;
    }
    if (this.$route.query.isreader) {
      this.isreader = this.$route.query.isreader;
    }
    if (this.$route.query.EnclosureShow) {
      this.EnclosureShow = this.$route.query.EnclosureShow;
    }
    if (this.$route.query.showAll) {
      this.showAll = this.$route.query.showAll;
    }
    if (this.$route.query.proId) {
      this.proId = this.$route.query.proId;
    }
    if (this.$route.query.back) {
      this.back = this.$route.query.back;
    }
    if (this.$route.query.id) {
      this.queryid = this.$route.query.id;
    }
    if (this.$route.query.showDataList) {
      this.showDataList = this.$route.query.showDataList;
    }
    if (!this.$route.query.Exhibition) {
      this.Exhibition = this.$route.query.Exhibition;
    }
    if (!this.$route.query.reviewFlase) {
      this.reviewFlase = this.$route.query.reviewFlase;
    }
    if (this.$route.query.Datashow) {
      this.Datashow = this.$route.query.Datashow;
    }
    if (!this.$route.query.pdfshow) {
      this.pdfshow = this.$route.query.pdfshow;
    }
    if (this.$route.query.showResult) {
      this.showResult = this.$route.query.showResult;
    }
    if (this.$route.query.Agreed) {
      this.Agreed = this.$route.query.Agreed;
    }
    if (this.$route.query.Decision) {
      this.Decision = this.$route.query.Decision;
    }
    if (this.$route.query.ResultData) {
      this.ResultData = this.$route.query.ResultData;
    }
    //选择前置审批人员取值
    axios
      .get("/api/snaker/surrogate/surrogate")
      .then(res => {
        // console.log(res.data);
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

    //被驳回的时候的时候取值
    if (this.Datashow) {
      axios
        .get("/api/snaker/flow/order/" + this.orderId)
        .then(res => {
          if (res.data.code == "000") {
            let Datalist = res.data.data;
            this.baseInfo = Datalist;
            console.log(this.baseInfo);
            if (this.baseInfo.fileList == null) {
              this.baseInfo.fileList = [];
            }
            let proModel = res.data.data.proModelList;
            if (proModel != null) {
              let proModelArr = [];
              for (let i = 0; i < proModel.length; i++) {
                proModelArr.push(proModel[i].name);
              }
              this.baseInfo.proModel = proModelArr;
            }
            let Teamlist = [];
            console.log(this.baseInfo.originalRightsHolder);

            for (let i = 0; i < this.baseInfo.teamList.length; i++) {
              Teamlist.push(this.baseInfo.teamList[i].username);
            }
            this.model10 = Teamlist;
            for (
              let index = 0;
              index < this.baseInfo.layerList.length;
              index++
            ) {
              this.layerList[index].level = this.baseInfo.layerList[
                index
              ].level;
              this.layerList[index].amount = this.baseInfo.layerList[
                index
              ].amount;
              this.layerList[index].proportion = this.baseInfo.layerList[
                index
              ].proportion;
              this.layerList[index].issueRate = this.baseInfo.layerList[
                index
              ].issueRate;
              this.layerList[index].proTerm = this.baseInfo.layerList[
                index
              ].proTerm;
              this.layerList[index].averageTerm = this.baseInfo.layerList[
                index
              ].averageTerm;
            }
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    }

    if (this.reviewFlase) {
      axios
        .get("/api/snaker/flow/order/" + this.orderId)
        .then(res => {
          if (res.data.data.cyclePeriod == "-") {
            res.data.data.cyclePeriod = "";
            res.data.data.amortizationPeriod = "";
          }
          if (res.data.data.packageNum == "-") {
            res.data.data.packageNum = "";
            res.data.data.packageScale = "";
          }
          this.id = res.data.data.id;
          this.model9.push({
            usename: res.data.data.originalRightsHolder
          });
          res.data.data.proModel = res.data.data.proModel.split(",");
          let num = res.data.data.layerList;
          let str = [];
          str = JSON.parse(res.data.data.teamList);
          for (let i = 0; i < str.length; i++) {
            this.model10.push(str[i].userName);
          }
          if (res.data.data.fileList) {
            this.baseInfo = res.data.data;
          } else {
            this.baseInfo.projectTeamOpinion = res.data.data.projectTeamOpinion;
            this.baseInfo.amortizationPeriod = res.data.data.amortizationPeriod;
            this.baseInfo.cyclePeriod = res.data.data.cyclePeriod;
            this.baseInfo.ratingSituation = res.data.data.ratingSituation;
            this.baseInfo.externalEnhancementAgencies =
              res.data.data.externalEnhancementAgencies;
            this.baseInfo.holderRatingSituation =
              res.data.data.holderRatingSituation;
            this.baseInfo.originalRightsHolder =
              res.data.data.originalRightsHolder;
            this.baseInfo.afterIncome = res.data.data.afterIncome;
            this.baseInfo.frontIncome = res.data.data.frontIncome;
            this.baseInfo.packageScale = res.data.data.packageScale;
            this.baseInfo.packageNum = res.data.data.packageNum;
            this.baseInfo.channel = res.data.data.channel;
            this.baseInfo.term = res.data.data.term;
            this.baseInfo.cost = res.data.data.cost;
            this.baseInfo.totalAmount = res.data.data.totalAmount;
            this.baseInfo.coopScale = res.data.data.coopScale;
            this.baseInfo.assetSerAgency = res.data.data.assetSerAgency;
            this.baseInfo.assertType = res.data.data.assertType;
            this.baseInfo.category = res.data.data.category;
            this.baseInfo.proModel = res.data.data.proModel;
            this.baseInfo.proName = res.data.data.proName;
            this.baseInfo.assertSituation = res.data.data.assertSituation;
            this.baseInfo.coopState = res.data.data.coopState;
            if (res.data.data.cyclePeriod == "-") {
              this.baseInfo.cyclePeriod = "";
              this.baseInfo.amortizationPeriod = "";
            }
            if (res.data.data.packageNum == "-") {
              this.baseInfo.packageNum = "";
              this.baseInfo.packageScale = "";
            }
          }
          //分层结构赋值
          for (let index = 0; index < this.layerList.length; index++) {
            if (num[index]) {
              this.layerList[index].level = num[index].level;
              this.layerList[index].amount = num[index].amount;
              this.layerList[index].proportion = num[index].proportion;
              this.layerList[index].issueRate = num[index].issueRate;
              this.layerList[index].proTerm = num[index].proTerm;
              this.layerList[index].averageTerm = num[index].averageTerm;
            }
          }
          //评审修改值
          console.log(res.data.data);
        })
        .catch(function(error) {
          console.log(error);
        });
    }
    //待提交取值
    axios
      .get("/api/snaker/surrogate/surrogate?proType=" + "ABS")
      .then(res => {
        let Num = res.data.data;
        for (var i = 0; i < Num.length; i++) {
          this.label.push({
            userName: Num[i].userName,
            fullName: Num[i].fullName,
            userId: Num[i].id
          });
        }
      })
      .catch(function(error) {
        console.log(error);
      });
    axios
      .get("/api/snaker/surrogate/originalRightsHolderList")
      .then(res => {
        let arr = [];
        arr = res.data.data;
        for (let i = 0; i < arr.length; i++) {
          this.model9.push({
            usename: arr[i]
          });
        }
      })
      .catch(function(error) {
        console.log(error);
      });
    //分销渠道部分
    axios
      .get("/api/snaker/flow/channel")
      .then(res => {
        let arr = res.data.data.split(",");
        if (res.data.code == "000") {
          for (let i = 0; i < arr.length; i++) {
            this.model8.push({
              channel: arr[i]
            });
          }
        }
      })
      .catch(function(error) {
        console.log(error);
      });

    if (this.proId) {
      //附件列表
      axios
        .get("/api/snaker/secretary/getFileForFileApproval?proId=" + this.proId)
        .then(res => {
          let Len = res.data.data;
          console.log(Len);
          for (let i = 0; i < Len.length; i++) {
            this.dataTableListshow.push({
              attachTaskId: Len[i].attachTaskId, //optType
              optType: Len[i].optType,
              fileName: Len[i].fileName,
              updateTime: Len[i].updateTime.split("T")[0],
              id: Len[i].id,
              attachTaskState: Len[i].attachTaskState,
              uploadPersonName: Len[i].uploadPersonName,
              proAttachType: Len[i].proAttachType,
              backReason: Len[i].backReason,
              filePath: Len[i].backReason,
              proId: Len[i].proId,
              uploadPersonId: Len[i].uploadPersonId,
              filePath: Len[i].filePath,
              realFileName: Len[i].realFileName,
              status: Len[i].status,
              flag: Len[i].flag
            });
            this.AttachmentList = this.dataTableListshow;
            // this.dataTable
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    }
    //待提交项目数据
    if (this.back) {
      axios
        .get("/api/snaker/flow/getProDraftById?id=" + this.queryid)
        .then(res => {
          console.log(res.data);
          if (res.data.code == "000") {
            this.model10 = [];
            let Len = res.data.data.teamList;
            console.log(res.data.data.status);

            // this.
            let proModel = res.data.data.proModelList;
            let proModelArr = [];
            if (proModel != null) {
              for (let i = 0; i < proModel.length; i++) {
                proModelArr.push(proModel[i].name);
              }
            }
            this.baseInfo = res.data.data;

            this.baseInfo.proModel = proModelArr;
            if (this.baseInfo.fileList == null) {
              this.baseInfo.fileList = [];
            }

            for (let i = 0; i < Len.length; i++) {
              this.model10.push(Len[i].username);
            }
            //处理分层结构
            for (
              let index = 0;
              index < this.baseInfo.layerList.length;
              index++
            ) {
              this.layerList[index].level = this.baseInfo.layerList[
                index
              ].level;
              this.layerList[index].amount = this.baseInfo.layerList[
                index
              ].amount;
              this.layerList[index].proportion = this.baseInfo.layerList[
                index
              ].proportion;
              this.layerList[index].issueRate = this.baseInfo.layerList[
                index
              ].issueRate;
              this.layerList[index].proTerm = this.baseInfo.layerList[
                index
              ].proTerm;
              this.layerList[index].averageTerm = this.baseInfo.layerList[
                index
              ].averageTerm;
            }
          } else {
            serviceError(data.message);
          }
          console.log(this.model10);
        })
        .catch(function(error) {
          console.log(error);
        });
    }
    //数据对比
    if (this.showDataList) {
      axios
        .get("/api/snaker/flow/order/" + this.orderId)
        .then(res => {
          if (res.data.code == "000") {
            if (res.data.data.changeData) {
              let brr = []
              let proMode = [];
              this.baseInfo = res.data.data;
              this.changeData = res.data.data.changeData;
              for (let i = 0; i < this.baseInfo.proModelList.length; i++) {
                proMode.push(this.baseInfo.proModelList[i].name);
              }
              this.baseInfo.proModel = proMode.toString();
              this.baseInfo.layerList = res.data.data.layerList
              console.log("数据对比執行");

              for (let i = 0; i < this.baseInfo.teamList.length; i++) {
                brr.push(this.baseInfo.teamList[i].fullName)
                }
                this.baseInfo.teamList = brr.toString();
                //修改以后的业务模式    业务模式的格式有问题无法取出来使用
                console.log(this.changeData);
                
                //暂时不需要显示出来
                var objLen = [];
                var LengthRow = '';
                LengthRow = this.changeData.layerList.length > this.baseInfo.layerList.length ? this.changeData.layerList.length : this.baseInfo.layerList.length;

                if (this.changeData.layerList.length > this.baseInfo.layerList.length) {
                  //存储修改前
                for (var i = 0; i < this.changeData.layerList.length; i++) {
                  objLen.push({
                      "changelevel":this.changeData.layerList[i].level ? this.changeData.layerList[i].level : "",
                      "changeamount":this.changeData.layerList[i].amount ? this.changeData.layerList[i].amount : "",
                      "changeproportion":this.changeData.layerList[i].proportion ? this.changeData.layerList[i].proportion : "",
                      "changeissueRate":this.changeData.layerList[i].issueRate ? this.changeData.layerList[i].issueRate : "",
                      "changeproTerm":this.changeData.layerList[i].proTerm ? this.changeData.layerList[i].proTerm : "",
                      "changeaverageTerm":this.changeData.layerList[i].averageTerm ? this.changeData.layerList[i].averageTerm : "",
                    })
                }
                //存储修改后
                  for (let i = 0; i < this.baseInfo.layerList.length; i++) {
                    objLen[i].level = this.baseInfo.layerList[i].level;
                    objLen[i].amount = this.baseInfo.layerList[i].amount;
                    objLen[i].proportion = this.baseInfo.layerList[i].proportion;
                    objLen[i].issueRate = this.baseInfo.layerList[i].issueRate;
                    objLen[i].proTerm = this.baseInfo.layerList[i].proTerm;
                    objLen[i].averageTerm = this.baseInfo.layerList[i].averageTerm;
                  
                  }
                }else{
                  for (var i = 0; i < this.baseInfo.layerList.length; i++) {
                  objLen.push({
                      "level":this.baseInfo.layerList[i].level ? this.baseInfo.layerList[i].level : "",
                      "amount":this.baseInfo.layerList[i].amount ? this.baseInfo.layerList[i].amount : "",
                      "proportion":this.baseInfo.layerList[i].proportion ? this.baseInfo.layerList[i].proportion : "",
                      "issueRate":this.baseInfo.layerList[i].issueRate ? this.baseInfo.layerList[i].issueRate : "",
                      "proTerm":this.baseInfo.layerList[i].proTerm ? this.baseInfo.layerList[i].proTerm : "",
                      "averageTerm":this.baseInfo.layerList[i].averageTerm ? this.baseInfo.layerList[i].averageTerm : "",
                    })
                }
                //存储修改后
                  for (let i = 0; i < this.changeData.layerList.length; i++) {
                    objLen[i].changelevel = this.changeData.layerList[i].level;
                    objLen[i].changeamount = this.changeData.layerList[i].amount;
                    objLen[i].changeproportion = this.changeData.layerList[i].proportion;
                    objLen[i].changeissueRate = this.changeData.layerList[i].issueRate;
                    objLen[i].changeproTerm = this.changeData.layerList[i].proTerm;
                    objLen[i].changeaverageTerm = this.changeData.layerList[i].averageTerm;
                  
                  }
                }
                this.layerListObj = objLen
                // console.log(this.layerListObj);
            }
          } else {
            serviceError(data.message);
          }
        })
        .catch(function(error) {
          console.log(error);
        });
    }
    if (this.Decision) {
      axios.get("/api/snaker/flow/getMeetPerson/" + "1")
      .then(res => {
        if (res.data.code === '000') {
          var Arrfixed = []
          var optionalArr = []
          var Fixmember = []
          let Arr = res.data.data
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
        this.fixed = Arrfixed.toString()
        this.Fixmember = Fixmember
        }
        
      })
    .catch(function(error) {
      console.log(error);
    });
      }
  },
  methods: {
    remoteMethod2(query) {
      let self = this;
      if (query) {
        setTimeout(() => {
          let params = {
            name: query
          };
          axios
            .get("/api/snaker/surrogate/surrogate?fullName=" + query)
            .then(res => {
              let Num = res.data.data;
              let label = [];
              // this.label = [];
              for (var i = 0; i < Num.length; i++) {
                this.label.push({
                  userName: Num[i].userName,
                  fullName: Num[i].fullName,
                  userId: Num[i].id
                });
              }
              this.label = label.concat(this.label);
            })
            .catch(function(error) {
              console.log(error);
            });
        }, 1000);
      } else {
        // this.label = [];
      }
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
    //pdf上传项目
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
          this.baseInfo.fileList.push({
            fileName: data.data.fileName,
            filePath: data.data.filePath,
            id: data.data.id,
            proAttachType: data.data.proAttachType,
            proId: data.data.proId,
            realFileName: data.data.realFileName,
            status: data.data.status,
            uploadPersonId: data.data.uploadPersonId,
            uploadPersonName: data.data.uploadPersonName
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
            proAttachType: "01",
            optType: "0",
            kind: "01"
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
            optType: "0",
            kind: "01"
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
            proAttachType: "02",
            optType: "0",
            kind: "01"
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
            optType: "0",
            kind: "01"
          });
        } else {
          serviceError(data.message);
        }
      });
      return false;
    },
    removeFile(index) {
      //删除上传附件的pdf文件
      this.baseInfo.fileList.splice(index, 1);
    },
    addAudit(value) {
      let valuecon = value;
      //项目团队的非空判断
      if (this.model10.length == 0) {
        this.$Message.error("项目团队不能为空");
        return;
      }
      let params = this.getParams();
      //处理分层结构中占比
      // console.log(params.layerList);
      // let issue = params.layerList;
      // for (let i = 0; i < issue.length; i++) {
      //   if (issue[i].proportion.indexOf("%") > 0) {
      //     this.$Message.error({
      //       content: `分层结构占比中${i + 1}条中不能包含百分号`,
      //       duration: 3
      //     });
      //     return false;
      //   }
      //   if (issue[i].amount.indexOf("，") > 0) {
      //     this.$Message.error({
      //       content: `分层结构发行金额中${i + 1}条中不能包含中文逗号`,
      //       duration: 3
      //     });
      //     return false;
      //   }
      //   if (issue[i].amount.indexOf(",") > 0) {
      //     console.log(issue[i].amount.split(",").join(""));
      //     params.layerList[i].amount = issue[i].amount.split(",").join("");
      //   }
      // }

      let teamList = [];
      //处理项目团队
      let arr = [];
      for (let i = 0; i < this.model10.length; i++) {
        for (let j = 0; j < this.label.length; j++) {
          if (this.label[j].userName == this.model10[i]) {
            arr.push(this.label[j]);
          }
        }
      }
      //处理业务模式
      let proModelArr = [];
      let proModel = this.baseInfo.proModel;
      if (proModel.length != 0) {
        for (let i = 0; i < proModel.length; i++) {
          if (proModel[i] == "存量债转") {
            proModelArr.push({
              modelId: "4",
              code: "StockDebtTransfer",
              name: "存量债转"
            });
          } else if (proModel[i] == "新增助贷") {
            proModelArr.push({
              modelId: "5",
              code: "NewLoan",
              name: "新增助贷"
            });
          } else if (proModel[i] == "数据采购") {
            proModelArr.push({
              modelId: "1",
              code: "DataPurchase",
              name: "数据采购"
            });
          } else if (proModel[i] == "新产品上线") {
            proModelArr.push({
              modelId: "2",
              code: "NewProductOnLine",
              name: "新产品上线"
            });
          } else if (proModel[i] == "外包采购") {
            proModelArr.push({
              modelId: "3",
              code: "Outsourcing",
              name: "外包采购"
            });
          }
        }
      }

      teamList = arr;
      params.teamList = JSON.stringify(arr);
      console.log(params);

      params.proModelList = proModelArr;
      params.teamList = teamList;
      params.proType = "0";
      //处理业务模式多选的问题
      params.orderId = this.orderId;
      params.proModel = null;
      //处理分销渠道的问题
      if (params.requirements == "静态池") {
        params.cyclePeriod = "-";
        params.amortizationPeriod = "-";
      } else {
        params.cyclePeriod = this.baseInfo.cyclePeriod;
        params.amortizationPeriod = this.baseInfo.amortizationPeriod;
      }

      if (params.channel == "理财通") {
        params.packageNum = this.baseInfo.packageNum;
        params.packageScale = this.baseInfo.packageScale;
      } else {
        params.packageNum = "-";
        params.packageScale = "-";
      }

      console.log(params);

      if (!this.checkParams(params)) {
        return;
      }
      // 接口的判断
      loading();
      if (valuecon === 'modify') {
        this.api.RejectaddProduct(params).then(data => {
          loadingHide();
          if (data.code == "000") {
            this.$router.push("/task/upcoming-list");
            const title = "修改成功！";
            const content = "<p>请到待办审批中提交申请！</p>";
            this.$Modal.success({
              title: title,
              content: content
            });
          } else {
            serviceError(data.message);
          }
        });
      } else {
        this.api.addProduct(params).then(data => {
          loadingHide();
          if (data.code == "000") {
            window.successNotice("提交成功");
            this.$router.push("/task/upcoming-list");
          } else {
            serviceError(data.message);
          }
        });
      }
    },
    checkParams(params) {
      for (let i in params) {
        if (this.checkTips[i]) {
          let flag = this.changeInput(params[i]);
          if (!flag) {
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
              if (!params.layerList[j][k]) {
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
          if (!params.originalRightsHolder) {
            this.$Message.error({
              content: "原始权益人不能为空，请选择一项！",
              duration: 3
            });
            return false;
          }
        }
      }
      return true;
    },
    getParams() {
      let param = JSON.parse(JSON.stringify(this.baseInfo));
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
    reset() {},
    //查询历史审批
    getHistoryAudit() {
      this.api
        .getHistoryAudit({
          id: this.id
        })
        .then(data => {
          if (data.code == "000") {
            this.historyAudit = data.data;
          } else {
            serviceError(data.message);
          }
        });
    },
    //提交审核
    auditFund() {
      //秘书落实跟进事项节点默认赋值
        if (this.ResultData) {
          this.auditStatus = "0"
        }
      if (this.auditStatus == "") {
        this.$Message.error({
          content: "请选择审批结果",
          duration: 3
        });
        return;
        if (this.auditStatus == "-1" && !this.auditInfo) {
          serviceError("审批不同意时，请填写审核意见");
          return;
        }
      }
      if (this.auditStatus.length == 0) {
        this.$Message.error({
          content: "审批结果不能为空",
          duration: 3
        });
        return false;
      }
      var Len = this.Fixmember.concat(this.applyCategory)
      let nodeList = Len;
      let params = {
        processId: this.taskId,
        orderId: this.orderId,
        taskId: this.taskId,
        resultState: this.auditStatus,
        result: this.auditInfo,
        proType: "0",
        assignee: nodeList
      };
      console.log(params);
      let Status = "";
      if (this.auditStatus == "-1") {
        Status = "不同意";
      } else if (this.auditStatus == "0") {
        Status = "同意";
      } else if (this.auditStatus == "-2") {
        Status = "有条件同意";
      }
      let title = `审批结果：${Status}<br>审批意见：<span style='display: block;word-wrap: break-word;'>${this.auditInfo}</span>`;
      this.$Modal.confirm({
        title: "请确认你的操作",
        content: title,
        onOk: () => {
          if (params.resultState == '-1' || params.resultState == '-2') {
              if (params.result  == '') {
              this.$Message.error({
              content: "审批意见不能为空",
              duration: 3
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
        onCancel: () => {}
      });
    },
    displayPdf(item) {
      if (this.status == "") {
        this.status = "-1";
      }
      let url = "/api/snaker/flow/attach/" + item.id + "/" + this.status;
      window.open("/static/pdf/web/viewer.html?file=" + url);
    },
    addPepole() {
      this.$Modal.confirm({
        render: h => {
          return h("Input", {
            props: {
              value: this.value,
              autofocus: true,
              placeholder: "请输入新增的权益人"
            },
            on: {
              input: val => {
                this.value = val;
              }
            }
          });
        },
        onOk: () => {
          if (this.value.length) {
            axios
              .post(
                "/api/snaker/surrogate/addOriginalRightsHolder?value=" +
                  this.value
              )
              .then(res => {
                if (res.status == "200") {
                  this.model9.push({
                    usename: this.value
                  });
                }
              })
              .catch(function(error) {
                console.log(error);
              });
          }
        }
      });
    },
    AddDom() {
      this.layerList.push({});
      this.LengthRow = this.layerList.length + 2;
    },
    reduce() {
      console.log(this.layerList.length);
      if (this.layerList.length == 5) {
        this.$Message.error({
          content: "已经不能在减少了哦!!!",
          duration: 3
        });
      } else {
        this.layerList.splice(-1, 1);
        this.LengthRow = this.layerList.length + 2;
      }
    },
    //保存
    Preservation() {
      if (this.model10.length == 0) {
        this.$Message.error("项目团队不能为空");
        return;
      }
      if (this.baseInfo.proName.length == 0) {
        this.$Message.error("项目名称不能为空");
        return;
      }
      let proModelArr = [];
      let proModel = this.baseInfo.proModel;
      console.log(proModel);

      if (proModel.length != 0) {
        for (let i = 0; i < proModel.length; i++) {
          if (proModel[i] == "存量债转") {
            proModelArr.push({
              modelId: "4",
              code: "StockDebtTransfer",
              name: "存量债转"
            });
          } else if (proModel[i] == "新增助贷") {
            proModelArr.push({
              modelId: "5",
              code: "NewLoan",
              name: "新增助贷"
            });
          } else if (proModel[i] == "数据采购") {
            proModelArr.push({
              modelId: "1",
              code: "DataPurchase",
              name: "数据采购"
            });
          } else if (proModel[i] == "新产品上线") {
            proModelArr.push({
              modelId: "2",
              code: "NewProductOnLine",
              name: "新产品上线"
            });
          } else if (proModel[i] == "外包采购") {
            proModelArr.push({
              modelId: "3",
              code: "Outsourcing",
              name: "外包采购"
            });
          }
        }
      }
      console.log(proModelArr);

      let arr = [];
      console.log(this.label);

      for (let i = 0; i < this.model10.length; i++) {
        for (let j = 0; j < this.label.length; j++) {
          if (this.label[j].userName == this.model10[i]) {
            arr.push({
              userId: this.label[j].userId,
              userName: this.label[j].userName,
              fullName: this.label[j].fullName
            });
          }
        }
      }
      console.log(arr);

      var name = localStorage.getItem("user");
      console.log(name);

      let params = this.getParams();
      params.teamList = arr;
      params.proType = "0";
      params.proModelList = proModelArr;
      params.proModel = params.proModel.toString();
      params.date = this.baseInfo.date;
      loading();
      this.api.savePro(params).then(data => {
        loadingHide();
        if (data.code == "000") {
          console.log(data);
          window.successNotice("保存成功");
          this.$router.push({ name: "submitted" });
        } else {
          serviceError(data.message);
        }
      });
    },
    Material() {
      let Arrlist = [];
      for (let i = 0; i < this.AttachmentList.length; i++) {
        if (this.AttachmentList[i].Comments == "01") {
          Arrlist.push(this.AttachmentList[i]);
        }
      }
      if (Arrlist.length == 0) {
        this.$Message.error({
          content: "您提交的数据为空,请您检查以后再进行操作",
          duration: 3
        });
        return;
      }
      let fileResult = { fileResult: Arrlist };
      console.log(Arrlist);

      console.log(fileResult);
      let title = "";
      for (let i = 0; i < Arrlist.length; i++) {
        if (Arrlist[i].Comments == "01") {
          if (
            Arrlist[i].backReason == true ||
            Arrlist[i].attachTaskState == "true"
          ) {
            title += Arrlist[i].fileName + " -" + "通过" + "<br>";
          } else if (
            Arrlist[i].backReason == false ||
            Arrlist[i].attachTaskState == "false"
          ) {
            title += Arrlist[i].fileName + " -" + "驳回" + "<br>";
          }
        }
      }

      this.$Modal.confirm({
        title: "请确认你的操作",
        content: title,
        style: {
          fontSize: "12px"
        },
        onOk: () => {
          //处理数据
          if (fileResult.fileResult.length > 0) {
            for (let i = 0; i < fileResult.fileResult.length; i++) {
              if (fileResult.fileResult[i].attachTaskState == "true") {
                fileResult.fileResult[i].attachTaskState = "01";
              } else if (fileResult.fileResult[i].attachTaskState == "false") {
                fileResult.fileResult[i].attachTaskState = "02";
              }
            }
          }
          console.log(fileResult);
          loading();
          this.api.submitFileApprovalResult(fileResult).then(data => {
            loadingHide();
            if (data.code == "000") {
              window.successNotice(data.message);
              this.$router.push({ name: "Nucleus" });
            } else {
              serviceError(data.message);
            }
          });
        },
        onCancel: () => {}
      });
    },
    remove(index) {
      this.dataTableListshow.splice(index, 1);
    },
    Submit() {
      let title = "";
      let SubIdArr = [];
      if (this.SubId.length == 0) {
        this.$Message.error({
          content: "您提交的内容为空",
          duration: 3
        });
        return false;
      }
      console.log(this.SubId);
      let SubId = [];
      for (let i = 0; i < this.SubId.length; i++) {
        if (this.SubId[i].kind == "01" && this.SubId[i].optType == "1") {
          SubId.push(this.SubId[i].id);
        }
      }
      console.log(SubId);

      for (let i = 0; i < this.SubId.length; i++) {
        if (this.SubId[i].id != SubId) {
          SubIdArr.push(this.SubId[i]);
        }
      }
      console.log(SubIdArr);
      if (SubIdArr.length == 0) {
        this.SubId = [];
      }
      if (SubIdArr.length == 0) {
        this.$Message.error({
          content: "您提交的内容为空",
          duration: 3
        });
        return false;
      }
      let fileList = { proId: this.proId, fileList: SubIdArr };
      console.log(fileList);
      for (let i = 0; i < fileList.fileList.length; i++) {
        if (fileList.fileList[i].optType == "0") {
          fileList.fileList[i].optType = "新增";
        } else {
          fileList.fileList[i].optType = "删除";
        }
        title +=
          fileList.fileList[i].fileName +
          " -" +
          fileList.fileList[i].optType +
          "<br>";
      }
      for (let i = 0; i < fileList.fileList.length; i++) {
        if (fileList.fileList[i].optType == "新增") {
          fileList.fileList[i].optType = "0";
        } else {
          fileList.fileList[i].optType = "1";
        }
      }
      console.log(title);
      this.$Modal.confirm({
        title: "请确认你的操作",
        content: title,
        onOk: () => {
          loading();
          this.api.addEnclosure(fileList).then(data => {
            loadingHide();
            if (data.code == "000") {
              this.$router.push({ name: "materials" });
            } else {
              serviceError(data.message);
            }
          });
        },
        onCancel: () => {}
      });
    }
  }
};
</script>





