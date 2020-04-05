var app = getApp();
var WxParse = require('../../lib/wxParse/wxParse.js');
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
Page({

    /**
     * 页面的初始数据
     */
    data: {
        userScoreHistoryList: [],
        page: 1,
        limit: 10,
        totalPages: 1
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        let that = this;
        that.getScoreHistory();
    },
    onReachBottom() {
        if (this.data.totalPages > this.data.page) {
            this.setData({
                page: this.data.page + 1
            });
        } else {
            return false;
        }
        this.getScoreHistory();
    },
    getScoreHistory: function () {
        let that = this;
        util.request(api.getScoreHistory, {
            page: that.data.page,
            limit: that.data.limit
        }).then(function (res) {
            if (res.errno === 0) {
                that.setData({
                    userScoreHistoryList: that.data.userScoreHistoryList.concat(res.data.list),
                    totalPages: res.data.pages
                });
            }
        });
    }

})