const app = getApp()
var util = require('../../utils/util.js');
var api = require('../../config/api.js');
var user = require('../../utils/user.js');
import Dialog from '../../miniprogram_npm/@vant/weapp/dialog/dialog';

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
        needName: false,
        groupDetailMap: {}
    },

    /**
     * 生命周期函数--监听页面加载
     */
    onLoad: function (options) {
        console.log(options)
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
        that.getBaseInfo()
    },

    getBaseInfo: function () {
        var that = this;
        util.request(api.getGroupDetail, {
            subjectId: that.data.subjectId,
        }).then(function (res) {
            let peopleSum = res.data.peopleSum;
            that.setData({
                subjectName: res.data.name,
                peopleSum: peopleSum,
                joinSum: res.data.joinSum,
                needName: res.data.needName,
                groupDetailMap: res.data.groupDetailMap,
                isJoin: res.data.isJoin,
                groupNumber: res.data.groupNumber,
                groupMaxNumber: res.data.groupMaxNumber,
            })
        });
    },

    //参与
    join: function (res) {
        var that = this;
        if (that.needName) {
            Dialog.alert({
                message: '>>前往完善姓名'
            }).then(() => {
                // on close
            });
        }
        util.request(api.groupAddDetail, {
            subjectId: this.data.subjectId
        }).then(function (res) {
            util.showToast("参与成功");
            that.getBaseInfo()
        });
    },
    goHome: function () {
        wx.navigateTo({
            url: "/pages/index/index"
        })
    },
    goCreateGroupSubject: function () {
        wx.navigateTo({
            url: "/pages/group/group"
        })
    },
    exportGroup: function () {
        util.request(api.groupExprot, {
            subjectId: this.data.subjectId
        }).then(function (res) {
            util.showToast("参与成功");
            that.getBaseInfo()
        });
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
