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
        activeTab: 0,
        categoryId: 0,
        page: 1,
        pages: 1,
        limit: 4,

    },

    onLoad: function (options) {
        var that = this;
        // 页面初始化 options为页面跳转所带来的参数
        if (options.goodId) {
            //这个pageId的值存在则证明首页的开启来源于用户点击来首页,同时可以通过获取到的pageId的值跳转导航到对应的详情页
            wx.navigateTo({
                url: '../goods/goods?id=' + options.goodId
            });
        }
        const scene = decodeURIComponent(options.scene)
        //用户邀请的
        if (scene) {
            //先登录
            if (!app.globalData.hasLogin) {
                wx.navigateTo({
                    url: "/pages/auth/login/login"
                });
            }
        }
        this.getIndexData();
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
    getIndexData: function () {
        wx.showLoading({
            title: '加载中',
        });
        let that = this;
        util.request(api.IndexUrl).then(function (res) {
            if (res.errno === 0) {
                let channel = that.data.channel;
                channel.push({id: 0, name: "首页"})
                that.setData({
                    newGoodsList: res.data.newGoodsList,
                    convertGoodsList: res.data.convertGoodsList,
                    otherGoodsList: res.data.otherGoodsList,
                    channel: channel.concat(res.data.channel),
                });
                wx.hideLoading();
            }
        });
        wx.hideLoading();
    },
    getGoodsList: function () {
        var that = this;
        util.request(api.GoodsList, {
            page: that.data.page,
            limit: that.data.limit,
            categoryId: that.data.categoryId,
            keyword: that.data.searchValue
        })
            .then(function (res) {
                if (res.errno === 0) {
                    let otherGoodsList = that.data.otherGoodsList;
                    that.setData({
                        otherGoodsList: otherGoodsList.concat(res.data.list),
                        convertGoodsList:res.data.convertGoodsList,
                        pages: res.data.pages
                    });
                }
            });
    },
    changeTab: function (event) {
        console.log(event.detail)
        let that = this;
        let categoryId = event.detail.name;
        that.setData({
            page: 1,
            pages: 1,
            categoryId: categoryId,
            searchValue: '',
            otherGoodsList: []
        })

        that.getGoodsList(categoryId);
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