<#include "../Layout.ftl">
<@layout>
  <script type="text/x-template" id="iview_Content">
    <div style="width: 96% height:100%; margin:10px 2%">
      <h3>测试功能列表 </h3>
      <hr style="margin-top: 10px; margin-bottom: 10px;">
      <div style="width: 100%;margin-top: 3%">
        <Row :gutter="16">
          <i-col span="24">
            <i-button type="primary" size="large" @click="integration">
              <Icon type="ios-download-outline"></Icon>
              集成测试
            </i-button>
          </i-col>
        </Row>
        <br/>
        <Row :gutter="16">
          <i-col span="24" style="padding-left:0;padding-right:0;">
            <i-table :columns="resultConfig"
                     :data="result"
                     size="small"
                     ref="table"
                     :loading="loading"
                     border
                     stripe="true"
            ></i-table>
          </i-col>
        </Row>
        <br/>
        <Row :gutter="16">
          <i-col span="24">
            <div style="float:right">
              <template>
                <Page :total="0" show-total show-elevator :total="totalCount"
                      :page-size="pageSize"></Page>
              </template>
            </div>
          </i-col>
        </Row>
        <br/>
      </div>
  </script>

  <script type="text/javascript">
    Vue.component('conctent', {
          template: '#iview_Content',
          props: {
            resultConfig: {
              type: Array,
              require: true
            },
            result: {
              type: Array,
              require: true
            }, totalCount: {
              type: Number,
              require: true
            }
          },
          data: function () {
            return {
              totalCount: 0,
              pageSize: 12,
              loading: false

            }
          },
          methods: {
            /**
             * 展示导航条
             */
            postNavParam: function () {
              this.$emit("nav",
                  {openNav: "member_login", openMenu: ['member'], breadName_Sys: "会员中心测试", breadName_Func: "登录"});
            },
            /**
             * 单元测试项
             * */
            unitTest: function (testCase) {
              console.log(testCase);
              $.ajax({
                type: "POST",
                url: testCase,
                data: {},
                dataType: "json",
                success: function (data) {

                }
              });
            },
            /**
             * 集成测试
             * */
            integration: function () {
              $.ajax({
                type: "POST",
                url: "integration",
                data: {},
                dataType: "json",
                success: function (data) {

                }
              });
            }
          },
          /**
           * 渲染之前执行
           */
          created: function () {
            this.postNavParam();
          },
          /**
           * DOM渲染完成后执行
           *
           */
          mounted: function () {
            webSocket();
          },
          /**
           * 属性监听
           *
           */
          watch: {}
        }
    );
  </script>
</@layout>
