// pages/userMoneyRecharge/userMoneyRecharge.js
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
Page({

    /**
     * 页面的初始数据
     */
    data: {
        money: '',
        type: '',
    },
    onLoad: function (options) {
        // 页面初始化 options为页面跳转所带来的参数
        this.setData({
            type: options.type
        });
    },
    onChange: function (event) {
        this.setData({
            money: event.detail
        })
    },

    //提现
    entPay: function () {
        let that = this;
        util.request(api.entPay, {
            money: that.data.money
        }, "post").then(function (res) {
            if (res.errno === 0) {
                wx.showToast({title: '提现成功'})
            } else {
                util.showErrorToast("提现失败");
            }
        })
    },
    //充值
    payOrder: function () {
        let that = this;
        util.request(api.OrderPrepay, {
            money: that.data.money,
            type:"CZ"
        }, 'POST').then(function (res) {
            if (res.errno === 0) {
                const payParam = res.data;
                console.log("支付过程开始");
                wx.requestPayment({
                    'timeStamp': payParam.timeStamp,
                    'nonceStr': payParam.nonceStr,
                    'package': payParam.packageValue,
                    'signType': payParam.signType,
                    'paySign': payParam.paySign,
                    'success': function (res) {
                        console.log("充值过程成功");
                        wx.showToast({
                          title: '充值成功',
                        })
                        wx.navigateTo({
                          url: '/pages/ucenter/index/index'
                        });
                    },
                    'fail': function (res) {
                        console.log("支付过程失败");
                        util.showErrorToast('充值失败');
                    },
                    'complete': function (res) {
                        console.log("充值过程结束")
                    }
                });
            }
        });

    }
})