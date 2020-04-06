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
        subjectId: 0,
        subjectName: "主题",
        isJoin: false,
        groupNumber: 0,
        peopleSum: 9,
        joinSum: 0,
        groupDetailMap: {}
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        var that = this;
        let subjectId = options.subjectId;
        that.setData({
            subjectId: subjectId
        })

        //获取用户信息
        wx.getUserInfo({
            success: function (res) {
                that.data.userInfo = res.userInfo;
                that.setData({
                    userInfo: that.data.userInfo
                })
            }
        })

        util.request(api.getGroupDetail, {
            subjectId: subjectId,
        }).then(function (res) {
            if (res.errno === 0) {
                let peopleSum = res.data.peopleSum;
                that.setData({
                    subjectName: res.data.name,
                    peopleSum: peopleSum,
                    joinSum: res.data.groupDetailList.length,
                    groupDetailMap: res.data.groupDetailMap,
                    isJoin: res.data.isJoin,
                    groupNumber: res.data.groupNumber,
                    groupMaxNumber: res.data.groupMaxNumber
                })
                peopleSum
            } else if (res.errno === 501) {
                util.goLogin();
            } else if (res.errno === -1) {
                util.showErrorToast()
            }
        });

        console.log(options)
    },

    //参与
    join: function (res) {
        util.request(api.groupAddDetail, {
            name: this.data.subject
        }).then(function (res) {
            if (res.errno === 0) {
                util.navigateTo("/pages/group/group_detail")
            } else if (res.errno === 501) {
                util.goLogin();
            } else if (res.errno === -1) {
                util.showErrorToast()
            }
        });
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