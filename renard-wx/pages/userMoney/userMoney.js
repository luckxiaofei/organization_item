// pages/userMoney/userMoney.js

var util = require('../../utils/util.js');
var api = require('../../config/api.js');
var user = require('../../utils/user.js');
Page({

    /**
     * 页面的初始数据
     */
    data: {
        userMoneyHistory: [],
        onlyCommission: false,
        page: 1,
        limit: 10,
        totalPages: 1
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {

        if (!options.onlyCommission) {
            this.setData({
                onlyCommission: true
            })
        }
        this.getHistoryList()

    },
    onReachBottom() {
        if (this.data.totalPages > this.data.page) {
            this.setData({
                page: this.data.page + 1
            });
        } else {
            return false;
        }
        this.getHistoryList();
    },

    getHistoryList() {
        let that = this;
        util.request(api.userMoneyHistory, {
            onlyCommission: that.data.onlyCommission,
            page: that.data.page,
            limit: that.data.limit
        }).then(function (res) {
            if (res.errno === 0) {
                that.setData({
                    userMoneyHistory: that.data.userMoneyHistory.concat(res.data.list),
                    totalPages: res.data.pages
                });
            }
        });
    },

    goUserMoneyRecharge: function () {
        wx.navigateTo({
            url: '../userMoneyRecharge/userMoneyRecharge?type=' + 'in'
        });
    },
    goUserMoneyCash: function () {
        wx.navigateTo({
            url: '../userMoneyRecharge/userMoneyRecharge?type=' + 'out'
        });
    },

})