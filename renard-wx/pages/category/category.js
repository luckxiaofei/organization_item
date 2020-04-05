var util = require('../../utils/util.js');
var api = require('../../config/api.js');

Page({
    data: {
        otherGoodsList: [],
        convertGoodsList: [],
        id: 0,
        channel: {},
        searchValue: '',
        categoryId: 0,
        activeTab: 0,
        page: 1,
        limit: 10
    },

    onLoad: function (options) {
        wx.showLoading({title:"加载中……"})
        // 页面初始化 options为页面跳转所带来的参数
        var that = this;
        if (options.id) {
            that.setData({
                id: parseInt(options.id)
            });
        }
        this.getCategoryInfo();
        wx.hideLoading();
    },


    getCategoryInfo: function () {
        let that = this;
        util.request(api.GoodsCategory, {
            id: this.data.id
        }).then(function (res) {
            if (res.errno == 0) {
                that.setData({
                    channel: res.data.brotherCategory,
                    categoryId: res.data.currentCategory.id,
                    activeTab: res.data.currentCategory.id,

                });
                wx.setNavigationBarTitle({
                    title: res.data.parentCategory.name
                })
                that.getGoodsList();
            } else {
                util.showErrorToast("加载失败！请联系管理员")
            }
        });
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
                        convertGoodsList: res.data.convertGoodsList,
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

        that.getGoodsList();
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
})