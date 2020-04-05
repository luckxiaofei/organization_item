const util = require('../../utils/util.js');
const api = require('../../config/api.js');
const user = require('../../utils/user.js');

//获取应用实例
const app = getApp();

Page({
    data: {
        channel: [], //一级分类
        newGoodsList: [], //滚动区商品
        convertGoodsList: [], //兑换区商品
        otherGoodsList: [], //剩余区商品
        searchValue: '',
        page: 1,
        pages: 1,
        limit: 10,

    },

    onLoad: function (options) {

    },
    //下拉
    onPullDownRefresh() {
        this.getIndexData();
        wx.stopPullDownRefresh() //停止下拉刷新
    },
    //上拉
    onReachBottom: function () {
        if (this.data.pages > this.data.page) {
            this.setData({
                page: this.data.page + 1
            });
        } else {
            return false;
        }
        this.getGoodsList()
    },
    getGoodsList: function () {
        wx.showLoading({title:"加载中……"});
        var that = this;
        util.request(api.GoodsList, {
            page: that.data.page,
            limit: that.data.limit,
            keyword: that.data.searchValue,
        })
            .then(function (res) {
                if (res.errno === 0) {
                    let otherGoodsList = that.data.otherGoodsList;
                    that.setData({
                        otherGoodsList: otherGoodsList.concat(res.data.list),
                        convertGoodsList: res.data.convertGoodsList,
                        pages: res.data.pages
                    });
                }
                wx.hideLoading();
            });
        wx.hideLoading();
    },

    searchValueCahne: function (event) {
        console.log(event.detail)
        this.setData({
            searchValue: event.detail
        })
    },
    onSearch: function () {
        this.setData({
            page: 1,
            pages: 1,
            otherGoodsList: [],
            convertGoodsList: []
        })
        this.getGoodsList();
    }
});