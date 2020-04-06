const app = getApp()
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
var user = require('../../utils/user.js');

Page({

    /**
     * 页面的初始数据
     */
    data: {
        userInfo: {},
        id: "123",
        subjectId: 0,
        subjectName: "主题",
        isJoin: false,
        ranking: 0,
        rankingSum: 9,
        rankingUse: 1,
        sortLists: [
            {name: "红鲤鱼与绿鲤鱼与驴", date: "01-09 16:50", sort: "第一名"},
            {name: "红鲤鱼与绿鲤鱼与驴", date: "01-09 16:50", sort: "第二名"},
            {name: "红鲤鱼与绿鲤鱼与驴", date: "01-09 16:50", sort: "第三名"},
        ]
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        var that = this;
        //获取用户信息
        wx.getUserInfo({
            success: function (res) {

                console.log(res);
                that.data.userInfo = res.userInfo;

                that.setData({
                    userInfo: that.data.userInfo
                })
            }
        })
        console.log(options)
    },

    //参与
    join: function (res) {

    },
    goHome: function () {
        wx.navigateTo({
            url: "/pages/index/index"
        })
    },


    /**
     * 用户点击右上角分享
     */
    onShareAppMessage: function (res) {
        this.shareView.toggleDialog(false);
        return {
            title: this.data.subject,
            path: "/pages/sort/sort_detail?id=" + this.data.id,
            imageUrl: "../images/shareImage.png",
        }
    },
    showShareView: function () {
        this.shareView.toggleDialog(true);
    }
})