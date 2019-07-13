<#macro layout>
  <!DOCTYPE html>
  <html xmlns="http://www.w3.org/1999/xhtml">
  <head>
    <meta charset="utf-8"/>
    <title>钢银管理系统</title>
    <meta name="description" content="Dashboard"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <link rel="stylesheet" href="/iview/styles/iview.css">
    <script type="text/javascript" src="/iview/vue.js"></script>
    <script type="text/javascript" src="/iview/iview.min.js"></script>
    <script src="//a1.gystatic.com/mgt/v2/js/jquery-1.11.2.min.js"></script>
    <script src="/common/JYWLCommon.js"></script>
  </head>
  <style>
    [v-cloak] {
      display: none;
    }

    .headerMenu {
      background: #00B0EC;
      height: 50px;
      line-height: 50px;
    }

    .ivu-layout-header {
      background: #00B0EC;
      padding: 0 15px;
      height: 49px;
      line-height: 49px;
    }

    .layout {
      border: 1px solid #d7dde4;
      background: #f5f7f9;
      position: relative;
      border-radius: 4px;
      overflow: hidden;
    }

    .layout-logo {
      width: 177px;
      height: 40px;
      border-radius: 3px;
      float: left;
      position: relative;
      top: 4px;
      left: 0px;
    }

    .layout-nav {
      width: 420px;
      margin: 0 auto;
      margin-right: 20px;
    }

    .mainContent {
      margin-top: 0;
    }

    .sideNav {
      border-bottom: 1px solid #DCDEE2;
    }

    .ivu-badge-count {
      top: 0px;
      right: -30px;
    }

    body {
      font-size: 14px;
    }

    .card .ivu-card-head {
      border-bottom: 0 solid #e8eaec;
      padding: 14px 16px;
      line-height: 1;
    }

  </style>
  <body>
  <#nested />
  <script type="text/javascript">

  </script>
  <div id="app" v-cloak>
    <div class="layout">
      <Layout>
        <i-header>
          <i-menu class="headerMenu" mode="horizontal">
            <div class="layout-logo">
              <a href="//mgt.banksteel.com/" class="navbar-brand"><img style="width: 177px;height: 40px;" src="//a1.gystatic.com/mgt/v2/img/logo.png" alt=""> </a>
            </div>
            <div class="layout-nav">
              <Menu-Item name="1" style="color: #ffffff">
                <Icon type="ios-navigate"></Icon>
                <a href="//www.banksteel.com/" style="color: #ffffff">钢银首页</a>
              </Menu-Item>
              <Menu-Item name="2" style="color: #ffffff">
                <Icon type="ios-keypad"></Icon>
                <a href="//mgt.banksteel.com/manual/" style="color: #ffffff">名词解释</a>
              </Menu-Item>
              <Submenu name="3" style="color: #ffffff">
                <template slot="title">
                  <span style="color: #ffffff">您好，{{loginName}}</span>
                </template>
                <Menu-Item name="3-1"><a href="//eac.mgr.banksteel.com/employee/update-password.htm?target=http://mgt.banksteel.com/">修改密码</a></Menu-Item>
                <Menu-Item name="3-2"><a href="//eac.mgr.banksteel.com/employee/update_employee.htm">修改信息</a></Menu-Item>
                <Menu-Item name="3-3"><a href="//eac.mgr.banksteel.com/logout.htm">退出账号</a></Menu-Item>
              </Submenu>
            </div>
          </i-menu>
        </i-header>
        <Layout>
          <Sider class="mainContent" hide-trigger :style="{background: '#F7F7F7'}">
            <i-menu ref="side_menu" id="sideNavMenu" theme="light" width="auto" :active-name="navObj.openNav" :open-names="navObj.openMenu">
              <Menu-Item name="15" class="sideNav" style="font-size: 16px;">
                <a href="javascript:;">及韵物流UI测试平台</a>
              </Menu-Item>
              <Submenu name="member" class="sideNav">
                <template slot="title">
                  <Icon type="md-person" size="20"></Icon>
                  会员中心
                </template>
                <Menu-Item name="member_login" to="/member/login/signin">登录</Menu-Item>
                <Menu-Item name="member_edi" to="/member/edi/release">EDI</Menu-Item>
              </Submenu>
              <Submenu name="manager" class="sideNav">
                <template slot="title">
                  <Icon type="ios-list-box" size="20"></Icon>
                  管理后台
                </template>
                <Menu-Item name="manager_" to="">客户管理</Menu-Item>
                <Menu-Item name="manager_" to="">承运商管理</Menu-Item>
                <Menu-Item name="manager_" to="">供应商管理</Menu-Item>
                <Menu-Item name="manager_" to="">车辆管理</Menu-Item>
                <Menu-Item name="manager_" to="">司机管理</Menu-Item>
                <Menu-Item name="manager_" to="">线路管理</Menu-Item>
                <Menu-Item name="manager_" to="">委托单管理</Menu-Item>
                <Menu-Item name="manager_" to="">询价单管理</Menu-Item>
                <Menu-Item name="manager_" to="">运单管理</Menu-Item>
                <Menu-Item name="manager_" to="">承运商结算</Menu-Item>
                <Menu-Item name="manager_" to="">客户结算</Menu-Item>
                <Menu-Item name="manager_" to="">用户管理</Menu-Item>
                <Menu-Item name="manager_" to="">积分管理</Menu-Item>
                <Menu-Item name="manager_" to="">预付款管理</Menu-Item>
                <Menu-Item name="manager_" to="">报表管理</Menu-Item>
              </Submenu>
            </i-menu>
          </Sider>
          <Layout :style="{padding: '0 24px 24px'}">
            <Breadcrumb :style="{margin: '16px 0'}">
              <Breadcrumb-item>及韵物流</Breadcrumb-item>
              <Breadcrumb-item>{{navObj.breadName_Sys}}</Breadcrumb-item>
              <Breadcrumb-item>{{navObj.breadName_Func}}</Breadcrumb-item>
            </Breadcrumb>
            <i-content :style="{padding: '10px', minHeight: '280px', background: '#fff'}">
              <conctent @nav="showNav" :result-config="tableConfig" :result="tableData" :total-Count="tableCount" ref="child"></conctent>
            </i-content>
          </Layout>
        </Layout>
      </Layout>
    </div>
  </div>
  <script type="text/javascript">
    var testResultConfig = [
      {
        title: "测试点",
        key: "name"
      }, {
        title: "描述",
        key: "desc"
      }, {
        title: "测试时间",
        key: "time"
      }, {
        title: "测试结果",
        key: "result"
      }, {
        title: "备注",
        key: "remark"
      }, {
        title: "操作",
        key: "operate",
        align: "center",
        render: function (h, params) {
          return h('div', [
            h('Button', {
              props: {
                type: 'primary',
                size: 'small'
              },
              style: {
                marginRight: '5px'
              },
              on: {
                click: function () {
                  LayoutVue.alerting("执行--->" + params.row.desc + "<---测试", function () {
                    LayoutVue.$refs.child.unitTest(params.row.name);
                  });
                }
              }
            }, '执行测试')
          ])
        }
      }
    ];
    var testResult = ${Cases};

    /**
     * webSocket传值
     */
    function webSocket() {
      var websocket = null;
      //判断当前浏览器是否支持WebSocket
      if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:9996/webSocketServer");
      } else {
        alert('当前浏览器 Not support websocket')
      }
      //连接发生错误的回调方法
      websocket.onerror = function () {
        alert("WebSocket连接发生错误");
      };

      //连接成功建立的回调方法
      websocket.onopen = function () {
        console.log("WebSocket连接成功");
      };

      //接收到消息的回调方法
      websocket.onmessage = function (event) {
        var tdata = LayoutVue.tableData;//原始数据
        var cdata = JSON.parse(event.data);//当前测试数据
        if (!tdata) {
          return;
        }
        var res = [];
        for (var s = 0; s < tdata.length; s++) {
          var resObj = tdata[s];
          if (resObj.name === cdata.name) {
            resObj.time = cdata.time;
            resObj.result = cdata.result;
            resObj.remark = cdata.remark;
          }
          res.push(resObj);
        }
        LayoutVue.tableData = res;
        LayoutVue.tableCount = LayoutVue.tableData.length;
      };

      //连接关闭的回调方法
      websocket.onclose = function () {
        console.log("WebSocket连接关闭");
      };

      //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
      window.onbeforeunload = function () {
        websocket.close();
      };
    }

    /**
     * 父组件值
     */
    var LayoutVue = new Vue({
      el: '#app',
      data: {
        navObj: {},
        navBadge: {},
        loginName: "Admin",
        tableConfig: testResultConfig,
        tableData: testResult,
        tableCount: testResult.length
      },
      methods: {
        /**
         * 导航展示
         * */
        showNav: function (data) {
          this.navObj = data;
          //手动打开
          this.$nextTick(function () {
                this.$refs.side_menu.updateOpened();
              }
          );
        },
        /**
         * 警告弹框
         * @param content
         */
        warning: function (content) {
          var config = {
            title: "提示",
            content: content,
            scrollable: true,
            onOk: function () {

            },
            onCancel: function () {

            }

          };
          this.$Modal.error(config);
        },
        /**
         * 弹框
         */
        alerting: function (text, callback) {
          var config = {
            title: "提示",
            content: text,
            scrollable: true,
            onOk: callback,
            onCancel: function () {

            }
          };
          this.$Modal.info(config);
        }
      },
      /**
       * 渲染完成后执行
       */
      mounted: function () {

      },
      /**
       * 渲染之前执行
       */
      created: function () {
        console.log(this.tableConfig);
      }
    });
  </script>
  </body>
  </html>
</#macro>