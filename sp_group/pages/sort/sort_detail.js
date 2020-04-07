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
        peopleSum: 9,
        joinSum: 0,
        sortNumber: 0,
        needName: false,
        realName: false,
        sortList: {}
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
                realName: res.data.realName,
                isJoin: res.data.isJoin,
                sortNumber: res.data.sortNumber,
                sortList: res.data.sortDetailList,
            })
        });
    },

    //参与
    join: function (res) {
        var that = this;
        if (that.data.needName && !that.data.realName) {
            Dialog.alert({
                message: '前往完善姓名'
            }).then(() => {
                util.navigateTo('/pages/myInfo/myInfo')
            });
        } else {
            util.request(api.sortAddDetail, {
                subjectId: this.data.subjectId
            }).then(function (res) {
                util.showToast("参与成功");
                that.getBaseInfo()
            });
        }
    },
    goHome: function () {
        wx.switchTab({
            url: "/pages/index/index"
        })
    },
    goCreateSortSubject: function () {
        wx.navigateTo({
            url: "/pages/sort/sort"
        })
    },
    exportSort: function () {
        util.request(api.sortExprot, {
            subjectId: this.data.subjectId
        }).then(function (res) {
            wx.setClipboardData({
                data: res.data,
                success(res) {

                }
            })
        });
    },

    onShareAppMessage: function (res) {
        return {
            title: "一起来排序吧",
            path: "/pages/sort/sort_detail?subjectId=" + this.data.subjectId,
            imageUrl: "../../images/shareImage.png",
        }
    }
})
